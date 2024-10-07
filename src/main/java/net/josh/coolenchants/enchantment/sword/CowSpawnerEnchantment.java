package net.josh.coolenchants.enchantment.sword;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

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
                // ((LivingEntity) target).setOnFire(true);
                if (level == 1) {
                    EntityType.COW.spawn(world, target.getBlockPos(), SpawnReason.TRIGGERED);
                }
                if (level == 2) {
                    EntityType.COW.spawn(world, target.getBlockPos(), SpawnReason.TRIGGERED);
                    EntityType.COW.spawn(world, target.getBlockPos(), SpawnReason.TRIGGERED);
                }
                if (level == 3) {
                    EntityType.COW.spawn(world, target.getBlockPos(), SpawnReason.TRIGGERED);
                    EntityType.COW.spawn(world, target.getBlockPos(), SpawnReason.TRIGGERED);
                    EntityType.COW.spawn(world, target.getBlockPos(), SpawnReason.TRIGGERED);
                }
            }
        }
            super.onTargetDamaged(user, target, level);

    }
}
