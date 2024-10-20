package net.josh.coolenchants.mixin;

import net.josh.coolenchants.IEntityDataSaver;
import net.josh.coolenchants.ModUtils;
import net.josh.coolenchants.data.WolfData;
import net.josh.coolenchants.enchantment.ModEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(WolfEntity.class)
public abstract class WolfMixin {
/*
kibble
 */
    private static final String NBT_HEALTH_BOOST_KEY = "HealthBoost";
    private static final String NBT_CURRENT_HEALTH_KEY = "CurrentHealth";
    private float healthBoost = 0.0f;
    private boolean checked = false;
    private int counter = 0;
    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void tick(CallbackInfo ci){
        if (!checked){
            WolfEntity wolf = (WolfEntity) (Object) this;
            if (wolf.getHealth() != WolfData.getCurrentHealth((IEntityDataSaver) wolf) &&
                    WolfData.getCurrentHealth((IEntityDataSaver) wolf) != 0) {
                wolf.setHealth(WolfData.getCurrentHealth(((IEntityDataSaver) wolf)));
            }
            checked = true;
        }
        if (counter < 50){
            counter++;
        } else {
            counter = 0;
            WolfEntity wolf = (WolfEntity) (Object) this;
            WolfData.setCurrentHealth(((IEntityDataSaver) wolf), (int) wolf.getHealth());
        }

    }



    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    private void onInteractMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        WolfEntity wolf = (WolfEntity) (Object) this;
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();

        if (wolf.isTamed() && !wolf.getWorld().isClient()) {
            if (wolf.isBreedingItem(itemStack) && wolf.getHealth() < wolf.getMaxHealth()) {
                if (ModUtils.isHoldingEnchantedWeapon(player, ModEnchantments.KIBBLE)) {
                    float newHealthBoost =
                            EnchantmentHelper.getLevel(ModEnchantments.KIBBLE, player.getMainHandStack()) * 4;

                    if (wolf.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getValue() <
                            newHealthBoost +
                                    wolf.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getBaseValue()) {

                        Objects.requireNonNull(wolf.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).
                                addPersistentModifier(
                                new EntityAttributeModifier("Health Boost", newHealthBoost,
                                        EntityAttributeModifier.Operation.ADDITION));
                        player.sendMessage(Text.literal(Float.toString(wolf.getMaxHealth())));

                        this.healthBoost = newHealthBoost;
                        WolfData.addHealth(((IEntityDataSaver) wolf), (int) newHealthBoost);
                        //wolf.writeCustomDataToNbt(wolf.writeNbt(new NbtCompound())); // Forces NBT to update
                    }
                }

                if (!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }
                player.sendMessage(Text.literal(Float.toString(wolf.getHealth())));

                wolf.heal(item.getFoodComponent().getHunger());
                player.sendMessage(Text.literal(Float.toString(wolf.getHealth())));
                wolf.writeCustomDataToNbt(wolf.writeNbt(new NbtCompound())); // Forces NBT to update
                //cir.setReturnValue(ActionResult.SUCCESS);
            }
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    private void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putFloat(NBT_HEALTH_BOOST_KEY, this.healthBoost);
        WolfEntity wolf = (WolfEntity) (Object) this;
        nbt.putFloat(NBT_CURRENT_HEALTH_KEY, wolf.getHealth());
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    private void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains(NBT_HEALTH_BOOST_KEY)) {
            this.healthBoost = nbt.getFloat(NBT_HEALTH_BOOST_KEY);
        }
        WolfEntity wolf = (WolfEntity) (Object) this;
        if (nbt.contains(NBT_CURRENT_HEALTH_KEY)) {
            wolf.setHealth(nbt.getFloat(NBT_CURRENT_HEALTH_KEY));
        }
    }
}
