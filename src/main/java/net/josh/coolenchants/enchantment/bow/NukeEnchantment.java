package net.josh.coolenchants.enchantment.bow;

import net.josh.coolenchants.CoolEnchants;
import net.josh.coolenchants.enchantment.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NukeEnchantment extends Enchantment {
    public NukeEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot... slotTypes) {
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
            ServerWorld world = (ServerWorld) user.getWorld();
            BlockPos position = target.getBlockPos();
            if (target instanceof LivingEntity && CoolEnchants.bowHit == 1) {
                float explosionPower = 16f; // Adjust explosion power for level 3
                float explosionRadius = 16f;
                explosionPower *= EnchantmentHelper.getLevel(ModEnchantments.NUKE,user.getMainHandStack());
                explosionRadius *= EnchantmentHelper.getLevel(ModEnchantments.NUKE,user.getMainHandStack());

                // Adjust explosion radius for level 3
                target.getWorld().createExplosion(null, target.getX(), target.getBodyY(0.125), target.getZ(), explosionRadius, false, World.ExplosionSourceType.TNT);
                spawnCustomParticles(world, target.getX(), target.getY(), target.getZ(), explosionRadius);
                CoolEnchants.bowHit = 0;
            }
        }
        super.onTargetDamaged(user, target, level);

    }


    private void spawnCustomParticles(ServerWorld world, double x, double y, double z, float radius) {
        // You can adjust these values to customize the particle effects
        int particleCount = (int) (radius * 50); // Increase particle count based on explosion radius
        for (int i = 0; i < particleCount; i++) {
            double offsetX = world.random.nextGaussian() * radius;
            double offsetY = world.random.nextGaussian() * radius;
            double offsetZ = world.random.nextGaussian() * radius;
            world.spawnParticles(ParticleTypes.EXPLOSION, x + offsetX, y + offsetY, z + offsetZ, 1, 0, 0, 0, 0);
        }
    }
}
