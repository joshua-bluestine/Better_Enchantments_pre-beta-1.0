package net.josh.coolenchants.enchantment.sword;

import net.josh.coolenchants.effect.ModEffects;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class CreeperNullifierEnchantment extends Enchantment {
    public int counter = 0;
    public CreeperNullifierEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot... slotTypes) {
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
    public int getMaxLevel(){return 1;}
    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return false;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return false;
    }
    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (target instanceof LivingEntity && !user.getWorld().isClient) {

            ServerWorld world = (ServerWorld) user.getWorld();
            BlockPos position = target.getBlockPos();

            if (target instanceof LivingEntity) {


                if (target.getType() == EntityType.CREEPER) {
                    ((CreeperEntity) target).setFuseSpeed(-10000);
                    ((LivingEntity) target).setStatusEffect(new StatusEffectInstance(ModEffects.CREEPER_NULL, 500, 10, true, false), null);
                }

            }
        }
            super.onTargetDamaged(user, target, level);

    }
}
