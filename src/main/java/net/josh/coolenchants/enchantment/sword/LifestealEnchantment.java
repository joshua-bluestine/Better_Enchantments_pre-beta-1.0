package net.josh.coolenchants.enchantment.sword;

import net.josh.coolenchants.enchantment.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class LifestealEnchantment extends Enchantment {
    public static int counter = 0;
    public static double xv = 0;
    public static double zv = 0;
    public static boolean xless = false;
    public static boolean zless = false;
    public static LivingEntity johnson = null;

    public LifestealEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot... slotTypes) {
        super(weight, target, slotTypes);
    }
    @Override
    public int getMinPower(int level) {
        return 20 + (level - 1) * 9;
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
    public int getMaxLevel(){return 3;}

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level){



        if (target instanceof LivingEntity && !user.getWorld().isClient) {

            ServerWorld world = (ServerWorld) user.getWorld();
            BlockPos position = target.getBlockPos();
            if (target instanceof LivingEntity) {
                if (((LivingEntity) target).getHealth() <= 0) {
                    johnson = (LivingEntity) target;
                    int particleCount = (int) 5*(EnchantmentHelper.getLevel(ModEnchantments.LIFESTEAL,user.getMainHandStack())); // Increase particle count based on explosion radius
                    for (int i = 0; i < particleCount; i++) {

                        if (user.getX() < target.getX()){
                            xless = true;
                            xv = -(world.random.nextGaussian() * 0.04D);
                        } else {
                            xless = false;
                            xv = world.random.nextGaussian() * 0.04D;
                        }
                        if (user.getZ() < target.getZ()){
                            zless = true;
                            zv = -(world.random.nextGaussian() * 0.04D);
                        } else {
                            zless = false;
                            zv = world.random.nextGaussian() * 0.04D;
                        }
                        counter = 1;
                        //user.getWorld().addParticle(ParticleTypes.HEART, target.getParticleX(1.0D), target.getY(), target.getParticleZ(1.0D), xv, 0, zv);

                    }
                    user.heal(level);
                }
            }
        }
        super.onTargetDamaged(user, target, level);
    }
}
