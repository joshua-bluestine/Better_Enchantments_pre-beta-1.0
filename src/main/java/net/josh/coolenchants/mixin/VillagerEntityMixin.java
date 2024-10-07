package net.josh.coolenchants.mixin;

import net.josh.coolenchants.ModUtils;
import net.josh.coolenchants.enchantment.ModEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin {
    TradeOfferList offers2 = new TradeOfferList();
    int counter = 0;
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<TradeOfferList> trades = new ArrayList<TradeOfferList>();
    //NbtList names = new NbtList();
   //NbtList trades = new NbtList();
    PlayerEntity player;

    private static final String ORIGINAL_TRADES_KEY = "OriginalTrades";

    @Inject(method = "interactMob", at = @At("HEAD"))
    private void onInteractMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        VillagerEntity villager = (VillagerEntity) (Object) this;
        if (!player.getWorld().isClient()) {
            if (ModUtils.isWearingEnchantedArmor(player, ModEnchantments.PERSUADE)) {
                applyDiscountToTrades(villager, player);
            } else {
                resetTradePrices(villager, player);
            }
        }
    }
    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci){
        VillagerEntity villager = (VillagerEntity) (Object) this;
        if (!villager.getWorld().isClient()) {
            if (villager.hasCustomer()){
                counter = 1;
                player = villager.getCustomer();
            }
            if (!villager.hasCustomer() && counter == 1){
                counter = 0;
                resetTradePrices(villager, player);
            }
        }
    }

    private void applyDiscountToTrades(VillagerEntity villager, PlayerEntity player) {
        if (!names.contains(villager.getEntityName())) {
            TradeOfferList offers = villager.getOffers();
            TradeOfferList offers2 = new TradeOfferList();
            counter = getMaxEnchantmentLevel(player);
            offers2.addAll(offers);
            trades.add(offers2);
            names.add(villager.getEntityName());

            for (int i = 0; i < offers.size(); i++) {
                TradeOffer offer = offers.get(i);
                ItemStack stack = offer.getAdjustedFirstBuyItem().copy();

                int originalCount = stack.getCount();
                int enchantmentLevel = getMaxEnchantmentLevel(player);
                int discountedCount = (int) Math.ceil(originalCount * (1 - (0.05 * enchantmentLevel)));

                stack.setCount(discountedCount);

                // Create a new TradeOffer with the modified stack
                TradeOffer modifiedOffer = new TradeOffer(stack, offer.getSecondBuyItem(), offer.getSellItem(), offer.getMaxUses(), offer.getMerchantExperience(), offer.getPriceMultiplier());
                offers.set(i, modifiedOffer);
            }
        }
    }

    private void resetTradePrices(VillagerEntity villager, PlayerEntity player) {

        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).equals(villager.getEntityName())) {
                TradeOfferList offers = villager.getOffers();
                for (int j = 0; j < trades.get(i).size(); j++) {
                    offers.set(j, trades.get(i).get(j));
                }
                names.remove(i);
                trades.remove(i);
            }
        }
    }

    private int getMaxEnchantmentLevel(PlayerEntity player) {
        int maxLevel = 0;
        for (ItemStack stack : player.getArmorItems()) {
            int level = EnchantmentHelper.getLevel(ModEnchantments.PERSUADE, stack);
            maxLevel += level;
        }
        return maxLevel;
    }

}
