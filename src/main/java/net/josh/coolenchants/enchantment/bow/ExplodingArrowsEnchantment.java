package net.josh.coolenchants.enchantment.bow;

import net.josh.coolenchants.CoolEnchants;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ExplodingArrowsEnchantment extends Enchantment {
    public ExplodingArrowsEnchantment(Enchantment.Rarity weight, EnchantmentTarget target, EquipmentSlot... slotTypes) {
        super(weight, target, slotTypes);
    }

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
            ServerWorld world = (ServerWorld) user.getWorld();
            BlockPos position = target.getBlockPos();
            if (target instanceof LivingEntity && CoolEnchants.bowHit == 1) {
                float explosionPower = 1.0f;
                float explosionRadius = 1.0f;

                if (level == 2){
                    explosionPower = 4.0f;
                    explosionRadius = 2.0f;
                }
                if (level == 3){
                    explosionPower = 8.0f;
                    explosionRadius = 4.0f;
                }
                target.getWorld().createExplosion(null, target.getX(), target.getBodyY(0.125), target.getZ(), explosionRadius, false, World.ExplosionSourceType.NONE);
                spawnCustomParticles(world, target.getX(), target.getY(), target.getZ(), explosionRadius);
                CoolEnchants.bowHit = 0;
            }
        }
        super.onTargetDamaged(user, target, level);
    }

    private void spawnCustomParticles(ServerWorld world, double x, double y, double z, float radius) {
        int particleCount = (int) (radius * 50);
        for (int i = 0; i < particleCount; i++) {
            double offsetX = world.random.nextGaussian() * radius;
            double offsetY = world.random.nextGaussian() * radius;
            double offsetZ = world.random.nextGaussian() * radius;
            world.spawnParticles(ParticleTypes.EXPLOSION, x + offsetX, y + offsetY, z + offsetZ, 1, 0, 0, 0, 0);
        }
    }
}
