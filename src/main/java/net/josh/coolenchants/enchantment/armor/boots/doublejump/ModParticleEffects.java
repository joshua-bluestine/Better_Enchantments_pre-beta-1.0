package net.josh.coolenchants.enchantment.armor.boots.doublejump;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.josh.coolenchants.CoolEnchants;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Random;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ModParticleEffects {
    private final static Random random = new Random();

    public static void play(PlayerEntity player,
                            @Nullable SoundEvent sound,
                            ParticleEffect particleType,
                            int total,
                            double yOffset,
                            double xvel,
                            double yvel,
                            double zvel) {
        play(player, player, sound, particleType, total, yOffset, xvel, yvel, zvel);
    }

    public static void play(
            PlayerEntity localPlayer,
            LivingEntity effectPlayer,
            @Nullable SoundEvent sound,
            ParticleEffect particleType,
            int total,
            double yOffset,
            double xvel,
            double yvel,
            double zvel) {
        boolean xrand = false;
        boolean yrand = false;
        boolean zrand = false;
        boolean yOffsetRand = false;
        if (xvel == 20) {
            xrand = true;
        }
        if (yvel == 20) {
            yrand = true;
        }
        if (zvel == 20) {
            zrand = true;
        }
        if (yOffset == 20) {
            yOffsetRand = true;
        }
        World world = localPlayer.getEntityWorld();
        if (sound != null) {
            world.playSound(localPlayer, effectPlayer.getBlockPos(), sound, SoundCategory.PLAYERS, 0.4f, 1);
        }
        for (int i = 0; i < total; ++i) {
            if (xrand)
                xvel = random.nextGaussian() * 0.02D;
            if (yrand)
                yvel = random.nextGaussian() * 0.02D;
            if (zrand)
                zvel = random.nextGaussian() * 0.02D;
            if (yOffsetRand)
                yOffset = random.nextGaussian() + 1;
            world.addParticle(particleType, effectPlayer.getParticleX(1.0D), effectPlayer.getY() + yOffset, effectPlayer.getParticleZ(1.0D), xvel, yvel, zvel);
        }
        PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
        passedData.writeUuid(localPlayer.getUuid());
        ClientPlayNetworking.send(CoolEnchants.C2S_DO_DOUBLEJUMP, passedData);
    }


    public static void play(
            PlayerEntity localPlayer,
            LivingEntity effectPlayer,
            ServerWorld world,
            @Nullable SoundEvent sound,
            ParticleEffect particleType,
            int total,
            double yOffset,
            double xvel,
            double yvel,
            double zvel) {
        boolean xrand = false;
        boolean yrand = false;
        boolean zrand = false;
        boolean yOffsetRand = false;
        if (xvel == 20) {
            xrand = true;
        }
        if (yvel == 20) {
            yrand = true;
        }
        if (zvel == 20) {
            zrand = true;
        }
        if (yOffset == 20) {
            yOffsetRand = true;
        }

        World localWorld = localPlayer.getEntityWorld();
        if (sound != null) {
            localWorld.playSound(localPlayer, effectPlayer.getBlockPos(), sound, SoundCategory.PLAYERS, 0.4f, 1);
        }
        for (int i = 0; i < total; ++i) {
            if (xrand)
                xvel = random.nextGaussian() * 0.02D;
            if (yrand)
                yvel = random.nextGaussian() * 0.02D;
            if (zrand)
                zvel = random.nextGaussian() * 0.02D;
            if (yOffsetRand)
                yOffset = random.nextGaussian() + 1;
            world.spawnParticles(particleType, effectPlayer.getParticleX(1.0D), effectPlayer.getY() + yOffset, effectPlayer.getParticleZ(1.0D), 1, xvel, yvel, zvel, 0);

            //world.addParticle(particleType, effectPlayer.getParticleX(1.0D), effectPlayer.getY()+yOffset, effectPlayer.getParticleZ(1.0D), xvel, yvel, zvel);
        }
        PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
        passedData.writeUuid(localPlayer.getUuid());
        ClientPlayNetworking.send(CoolEnchants.C2S_DO_DOUBLEJUMP, passedData);
    }

    public static void play(
            PlayerEntity localPlayer,
            ServerWorld world,
            @Nullable SoundEvent sound,
            ParticleEffect particleType,
            int total,
            double yOffset,
            double xvel,
            double yvel,
            double zvel) {
        boolean xrand = false;
        boolean yrand = false;
        boolean zrand = false;
        boolean yOffsetRand = false;
        if (xvel == 20) {
            xrand = true;
        }
        if (yvel == 20) {
            yrand = true;
        }
        if (zvel == 20) {
            zrand = true;
        }
        if (yOffset == 20) {
            yOffsetRand = true;
        }

        World localWorld = localPlayer.getEntityWorld();
        if (sound != null) {
            localWorld.playSound(localPlayer, localPlayer.getBlockPos(), sound, SoundCategory.PLAYERS, 0.4f, 1);
        }
        for (int i = 0; i < total; ++i) {
            if (xrand)
                xvel = random.nextGaussian() * 0.02D;
            if (yrand)
                yvel = random.nextGaussian() * 0.02D;
            if (zrand)
                zvel = random.nextGaussian() * 0.02D;
            if (yOffsetRand)
                yOffset = random.nextGaussian() + 1;
            world.spawnParticles(particleType, localPlayer.getParticleX(1.0D), localPlayer.getY() + yOffset, localPlayer.getParticleZ(1.0D), 1, xvel, yvel, zvel, 0);

            //world.addParticle(particleType, localPlayer.getParticleX(1.0D), localPlayer.getY()+yOffset, localPlayer.getParticleZ(1.0D), xvel, yvel, zvel);
        }
        PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
        passedData.writeUuid(localPlayer.getUuid());
        ClientPlayNetworking.send(CoolEnchants.C2S_DO_DOUBLEJUMP, passedData);
    }

    public static void play(
            PlayerEntity localPlayer,
            LivingEntity effectPlayer,
            ServerWorld world,
            @Nullable SoundEvent sound,
            ParticleEffect particleType,
            int total,
            double yOffset,
            double xvel,
            double yvel,
            double zvel,
            double speed) {
        boolean xrand = false;
        boolean yrand = false;
        boolean zrand = false;
        boolean yOffsetRand = false;
        if (xvel == 20) {
            xrand = true;
        }
        if (yvel == 20) {
            yrand = true;
        }
        if (zvel == 20) {
            zrand = true;
        }
        if (yOffset == 20) {
            yOffsetRand = true;
        }

        World localWorld = localPlayer.getEntityWorld();
        if (sound != null) {
            localWorld.playSound(localPlayer, effectPlayer.getBlockPos(), sound, SoundCategory.PLAYERS, 0.4f, 1);
        }
        for (int i = 0; i < total; ++i) {
            if (xrand)
                xvel = random.nextGaussian() * 0.02D;
            if (yrand)
                yvel = random.nextGaussian() * 0.02D;
            if (zrand)
                zvel = random.nextGaussian() * 0.02D;
            if (yOffsetRand)
                yOffset = random.nextGaussian() + 1;
            world.spawnParticles(particleType, effectPlayer.getParticleX(1.0D), effectPlayer.getY() + yOffset, effectPlayer.getParticleZ(1.0D), 1, xvel, yvel, zvel, speed);

            //world.addParticle(particleType, effectPlayer.getParticleX(1.0D), effectPlayer.getY()+yOffset, effectPlayer.getParticleZ(1.0D), xvel, yvel, zvel);
        }
        PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
        passedData.writeUuid(localPlayer.getUuid());
        ClientPlayNetworking.send(CoolEnchants.C2S_DO_DOUBLEJUMP, passedData);
    }

    public static void play(
            PlayerEntity localPlayer,
            ServerWorld world,
            @Nullable SoundEvent sound,
            ParticleEffect particleType,
            int total,
            double yOffset,
            double xvel,
            double yvel,
            double zvel,
            double speed) {
        boolean xrand = false;
        boolean yrand = false;
        boolean zrand = false;
        boolean yOffsetRand = false;
        if (xvel == 20) {
            xrand = true;
        }
        if (yvel == 20) {
            yrand = true;
        }
        if (zvel == 20) {
            zrand = true;
        }
        if (yOffset == 20) {
            yOffsetRand = true;
        }

        World localWorld = localPlayer.getEntityWorld();
        if (sound != null) {
            localWorld.playSound(localPlayer, localPlayer.getBlockPos(), sound, SoundCategory.PLAYERS, 0.4f, 1);
        }
        for (int i = 0; i < total; ++i) {
            if (xrand)
                xvel = random.nextGaussian() * 0.02D;
            if (yrand)
                yvel = random.nextGaussian() * 0.02D;
            if (zrand)
                zvel = random.nextGaussian() * 0.02D;
            if (yOffsetRand)
                yOffset = random.nextGaussian() + 1;
            world.spawnParticles(particleType, localPlayer.getParticleX(1.0D), localPlayer.getY() + yOffset, localPlayer.getParticleZ(1.0D), 1, xvel, yvel, zvel, speed);

            //world.addParticle(particleType, localPlayer.getParticleX(1.0D), localPlayer.getY()+yOffset, localPlayer.getParticleZ(1.0D), xvel, yvel, zvel);
        }
        PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
        passedData.writeUuid(localPlayer.getUuid());
        ClientPlayNetworking.send(CoolEnchants.C2S_DO_DOUBLEJUMP, passedData);
    }
}