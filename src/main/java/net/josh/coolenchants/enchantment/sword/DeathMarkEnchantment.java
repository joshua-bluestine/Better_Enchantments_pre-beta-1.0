package net.josh.coolenchants.enchantment.sword;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.josh.coolenchants.CoolEnchants;
import net.josh.coolenchants.effect.ModEffects;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class DeathMarkEnchantment extends Enchantment {
    public int counter = 0;
    public static int duration = 0;
    public static StatusEffectInstance deathtype;
    public static Entity michael;
    public DeathMarkEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot... slotTypes) {
        super(weight, target, slotTypes);
    }
    @Override
    public int getMinPower(int level) {
        return 12 + (level - 1) * 9;
    }

    @Override
    public int getMaxPower(int level) {
        return super.getMinPower(level) + 50;
    }

    @Override
    public boolean isTreasure() {
        return true;
    }
    @Override
    public int getMaxLevel(){return 2;}

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return false;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return false;
    }


    public static void onDeath(Entity target){
        ((LivingEntity) target).setStatusEffect(deathtype, null);
    }
    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (target instanceof LivingEntity && !user.getWorld().isClient) {
            if (duration == 0) {
                michael = target;
            } else {
                target = michael;
            }
                LivingEntity john = (LivingEntity) target;
                if (john.getGroup() != EntityGroup.UNDEAD) {
                    deathtype = new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, 1, 1000);
                    ServerWorld world = (ServerWorld) user.getWorld();
                    BlockPos position = target.getBlockPos();
                    if (target instanceof LivingEntity) {
                        if (level == 1 && duration == 0) {
                            duration = 50;
                            ((LivingEntity) target).setStatusEffect(new StatusEffectInstance(ModEffects.DEATH_MARK_EFFECT, 200, 10, false, true), null);
                        } else if (level == 2) {
                            duration = 25;
                            ((LivingEntity) target).setStatusEffect(new StatusEffectInstance(ModEffects.DEATH_MARK_EFFECT, 200, 10, false, true), null);
                        }
                    }
                } else {
                    deathtype = new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1, 1000);
                    ServerWorld world = (ServerWorld) user.getWorld();
                    BlockPos position = target.getBlockPos();
                    if (target instanceof LivingEntity) {
                        if (level == 1 && duration == 0) {
                            duration = 100;
                            ((LivingEntity) target).setStatusEffect(new StatusEffectInstance(ModEffects.DEATH_MARK_EFFECT, 200, 10, false, true), null);
                        } else if (level == 2) {
                            duration = 50;
                            ((LivingEntity) target).setStatusEffect(new StatusEffectInstance(ModEffects.DEATH_MARK_EFFECT, 200, 10, false, true), null);
                        }
                    }
                }


        }
            super.onTargetDamaged(user, target, level);

    }
}
