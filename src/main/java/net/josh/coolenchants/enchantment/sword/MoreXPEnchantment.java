package net.josh.coolenchants.enchantment.sword;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class MoreXPEnchantment extends Enchantment {
    public int counter = 0;
    public MoreXPEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot... slotTypes) {
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
        return false;
    }
    @Override
    public int getMaxLevel(){return 5;}

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level){

        if (target instanceof LivingEntity && !user.getWorld().isClient) {

            ServerWorld world = (ServerWorld) user.getWorld();
            BlockPos position = target.getBlockPos();
            if (target instanceof LivingEntity) {
                if (((LivingEntity) target).getHealth() <= 0) {

                    for (int i = 0; i <= level; i++)
                        EntityType.EXPERIENCE_ORB.spawn((ServerWorld) user.getWorld(), user.getBlockPos(), SpawnReason.TRIGGERED);
                }
            }
        }
        super.onTargetDamaged(user, target, level);
    }
}
