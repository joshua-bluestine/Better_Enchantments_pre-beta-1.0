package net.josh.coolenchants.enchantment.sword;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.util.List;

public class WitherEnchantment extends Enchantment {
    public WitherEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot... slotTypes) {
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
    public int getMaxLevel() {
        return 3;
    }
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
            if (level == 1) {
                ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 200, 1));
            } else if (level == 2) {
                ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 200, 5));
                // Create a 5x5 box around the target
                BlockPos targetPos = target.getBlockPos();
                Box area = new Box(targetPos.add(-2, -2, -2), targetPos.add(2, 2, 2));
                List<LivingEntity> entities = ((ServerWorld) user.getWorld()).getEntitiesByClass(LivingEntity.class, area, e -> e != target);

                // Apply poison effect to all entities in the 5x5 area
                for (LivingEntity entity : entities) {
                    if (!(entity instanceof PlayerEntity))
                        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 200, 5));
                }
            } else if (level == 3) {
                ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 200, 10));

                // Create a 5x5 box around the target
                BlockPos targetPos = target.getBlockPos();
                Box area = new Box(targetPos.add(-4, -4, -4), targetPos.add(4, 4, 4));

                List<LivingEntity> entities = ((ServerWorld) user.getWorld()).getEntitiesByClass(LivingEntity.class, area, e -> e != target);

                // Apply poison effect to all entities in the 5x5 area
                for (LivingEntity entity : entities) {
                    if (!(entity instanceof PlayerEntity))
                        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 200, 10));
                }
            }
        }
        super.onTargetDamaged(user, target, level);
    }
}
