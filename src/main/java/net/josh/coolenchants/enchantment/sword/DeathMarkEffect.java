package net.josh.coolenchants.enchantment.sword;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Random;

import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;

public class DeathMarkEffect {
    private final static Random random = new Random();

    public static void play(LivingEntity target, PlayerEntity player) {

        play(player, target);
    }

    public static void play(PlayerEntity localPlayer, LivingEntity target) {
        World world = localPlayer.getEntityWorld();
        //world.playSound(localPlayer, target.getBlockPos(), SoundEvents.ENTITY_TURTLE_SHAMBLE, SoundCategory.PLAYERS, 0.4f, 1);

        for(int i = 0; i < 5; ++i) {
            double d = random.nextGaussian() * 0.02D;
            double e = random.nextGaussian() * 0.02D;
            double f = random.nextGaussian() * 0.02D;
            world.addParticle(ParticleTypes.ASH, target.getParticleX(1.0D), target.getY()+1, target.getParticleZ(1.0D), d, e, f);
        }
    }
}