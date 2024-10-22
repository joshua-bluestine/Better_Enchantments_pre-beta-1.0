package net.josh.coolenchants.enchantment.sword;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

public class CowSpawnerEnchantment extends Enchantment {
    public int counter = 0;
    public CowSpawnerEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot... slotTypes) {
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
    public boolean isAvailableForEnchantedBookOffer() {
        return false;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return false;
    }
    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {

        if (target instanceof LivingEntity && !user.getWorld().isClient && ((LivingEntity) target).getHealth() <= 0) {

            ServerWorld world = (ServerWorld) user.getWorld();
            BlockPos position = target.getBlockPos();
            if (target instanceof LivingEntity) {
                Random random = world.getRandom();
                // ((LivingEntity) target).setOnFire(true);
                if (level == 1) {
                    if (random.nextDouble() > 0.8) {
                        EntityType.COW.spawn(world, target.getBlockPos(), SpawnReason.TRIGGERED);
                    }
                }
                if (level == 2) {
                    if (random.nextDouble() > 0.6) {
                        EntityType.COW.spawn(world, target.getBlockPos(), SpawnReason.TRIGGERED);
                    }
                }
                if (level == 3) {
                    if (random.nextDouble() > 0.4) {
                        EntityType.COW.spawn(world, target.getBlockPos(), SpawnReason.TRIGGERED);
                    }
                    if (random.nextDouble() > 0.8) {
                        EntityType.COW.spawn(world, target.getBlockPos(), SpawnReason.TRIGGERED);
                    }
                }
            }
        }
            super.onTargetDamaged(user, target, level);

    }
}
