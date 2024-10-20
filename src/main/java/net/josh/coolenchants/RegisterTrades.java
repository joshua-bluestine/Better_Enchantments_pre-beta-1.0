package net.josh.coolenchants;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.josh.coolenchants.enchantment.ModEnchantments;
import net.josh.coolenchants.item.ModItems;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;

public class RegisterTrades {


    public static void registerTrades() {
        ItemStack snowbook = new ItemStack(Items.BOOK, 7);
        snowbook.addEnchantment(ModEnchantments.SNOWBALL,1);

//................................PROJECTILE GUY..........................

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 1,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.SNOWBALL, 16),
                            new ItemStack(Items.BOOK, 1),
                            EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(ModEnchantments.SNOWBALL, 1)),
                            6, 100, 0.02f));
                });
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 2,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.BLAZE_ROD, 4),
                            new ItemStack(Items.BOOK, 1),
                            EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(ModEnchantments.BLAZE_SHOOTER, 1)),
                            6, 50, 0.02f));
                });
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 2,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.GHAST_TEAR, 4),
                            new ItemStack(Items.BOOK, 1),
                            EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(ModEnchantments.FIRE_SHOOTER, 1)),
                            6, 50, 0.02f));
                });
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 3,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.NETHER_STAR, 1),
                            new ItemStack(Items.BOOK, 1),
                            EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(ModEnchantments.WITHER_SKULL, 1)),
                            6, 12, 0.02f));
                });
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 3,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.DRAGON_BREATH, 1),
                            new ItemStack(Items.BOOK, 1),
                            EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(ModEnchantments.DRAGON_SHOOTER, 1)),
                            6, 12, 0.02f));
                });
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 3,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.SCULK_CATALYST, 1),
                            new ItemStack(Items.BOOK, 1),
                            EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(ModEnchantments.WARDEN_SHOOTER, 1)),
                            6, 12, 0.02f));
                });

//..............................TIME GUY................................

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.CLERIC, 1,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 16),
                            new ItemStack(Items.BOOK, 1),
                            EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(ModEnchantments.TIME_CHANGE, 1)),
                            6, 100, 0.02f));
                });
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.CLERIC, 2,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 32),
                            new ItemStack(Items.BOOK, 1),
                            EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(ModEnchantments.FREEZE, 1)),
                            6, 50, 0.02f));
                });
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.CLERIC, 3,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.GHAST_TEAR, 4),
                            new ItemStack(Items.BOOK, 1),
                            EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(ModEnchantments.CHRONOMANCY, 1)),
                            6, 50, 0.02f));
                });
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.CLERIC, 4,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.SCULK_CATALYST, 1),
                            new ItemStack(Items.BOOK, 1),
                            EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(ModEnchantments.CHRONO_PAUSE, 1)),
                            6, 12, 0.02f));
                });

    }
}