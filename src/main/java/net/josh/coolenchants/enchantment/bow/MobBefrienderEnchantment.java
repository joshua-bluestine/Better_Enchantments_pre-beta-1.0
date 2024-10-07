package net.josh.coolenchants.enchantment.bow;

import net.josh.coolenchants.explosion.TargetExplosion;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MobBefrienderEnchantment extends Enchantment {
    public MobBefrienderEnchantment(Enchantment.Rarity weight, EnchantmentTarget target, EquipmentSlot... slotTypes) {
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
        if (target instanceof LivingEntity && !user.getWorld().isClient) {

            ServerWorld world = (ServerWorld) user.getWorld();
            BlockPos position = target.getBlockPos();
            if (target instanceof LivingEntity) {
                if (level == 1) {
                    TargetExplosion explosion = new TargetExplosion(target.getWorld(), target, target.getX(), target.getBodyY(0.0625), target.getZ(), 2.0f, null);
                    explosion.collectBlocksAndDamageEntities();
                    explosion.affectWorld(false);                }
                if (level == 2) {
                    TargetExplosion explosion = new TargetExplosion(target.getWorld(), target, target.getX(), target.getBodyY(0.0625), target.getZ(), 4.0f, null);
                    explosion.collectBlocksAndDamageEntities();
                    explosion.affectWorld(false);                }
                if (level == 3) {
                    TargetExplosion explosion = new TargetExplosion(target.getWorld(), target, target.getX(), target.getBodyY(0.0625), target.getZ(), 8.0f, null);
                    explosion.collectBlocksAndDamageEntities();
                    explosion.affectWorld(false);                }

            }
        }
        super.onTargetDamaged(user, target, level);

    }
}

