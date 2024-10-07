package net.josh.coolenchants.enchantment.bow;

import net.josh.coolenchants.CoolEnchants;
import net.josh.coolenchants.explosion.TargetExplosion;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;

public class CreeperExploderEnchantment extends Enchantment {
    public CreeperExploderEnchantment(Enchantment.Rarity weight, EnchantmentTarget target, EquipmentSlot... slotTypes) {
        super(weight, target, slotTypes);
    }

    //this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), (float)this.explosionRadius * f, World.ExplosionSourceType.MOB);
    public static int counter = 0;
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
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (target instanceof LivingEntity && !user.getWorld().isClient && CoolEnchants.bowHit == 1) {

            ServerWorld world = (ServerWorld) user.getWorld();
            BlockPos position = target.getBlockPos();
            if (target instanceof CreeperEntity) {
                    ((CreeperEntity) target).setFuseSpeed(10000);
            }
            if (((LivingEntity) target).getHealth() <= 0){
                TargetExplosion explosion = new TargetExplosion(target.getWorld(), target, target.getX(), target.getBodyY(0.0625), target.getZ(), 2.0f, null);
                explosion.collectBlocksAndDamageEntities();
                explosion.affectWorld(false);
                //target.getWorld().createExplosion(target, target.getX(), target.getBodyY(0.0625), target.getZ(), 2.0f, World.ExplosionSourceType.MOB);
            }
        }
        super.onTargetDamaged(user, target, level);

    }
}

