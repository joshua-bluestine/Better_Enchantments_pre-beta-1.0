package net.josh.coolenchants.enchantment.sword;

import net.josh.coolenchants.effect.ModEffects;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class FreezesMobsEnchantment extends Enchantment {
    public int counter = 0;
    public FreezesMobsEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot... slotTypes) {
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
    public int getMaxLevel(){return 3;}

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level){

        if (target instanceof LivingEntity && !user.getWorld().isClient) {

            ServerWorld world = (ServerWorld) user.getWorld();
            BlockPos position = target.getBlockPos();
            if (target instanceof LivingEntity) {
                if (level == 1) {
                    //((LivingEntity) target).setMovementSpeed(2);
                    ((LivingEntity) target).setStatusEffect(new StatusEffectInstance(ModEffects.TIME_FREEZE_EFFECT, 10, 10, true, false), null);
                }
                if (level == 2) {
                    ((LivingEntity) target).setStatusEffect(new StatusEffectInstance(ModEffects.TIME_FREEZE_EFFECT, 20, 10, true, false), null);
                }
                if (level == 3){
                    ((LivingEntity) target).setStatusEffect(new StatusEffectInstance(ModEffects.TIME_FREEZE_EFFECT, 30, 10, true, false), null);
                }
            }
        }
        super.onTargetDamaged(user, target, level);
    }
}
