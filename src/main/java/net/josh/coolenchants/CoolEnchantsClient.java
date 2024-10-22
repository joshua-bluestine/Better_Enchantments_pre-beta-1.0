package net.josh.coolenchants;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.josh.coolenchants.client.*;
//import net.josh.coolenchants.client.NecromancerHudOverlay;
import net.josh.coolenchants.enchantment.armor.boots.doublejump.ModParticleEffects;
import net.josh.coolenchants.entity.ModEntities;
//import net.josh.coolenchants.entity.client.ModModelLayers;
//import net.josh.coolenchants.entity.client.NecromancerModel;
import net.josh.coolenchants.entity.client.NecromancerRenderer;
import net.josh.coolenchants.client.ChronoSaveHudOverlay;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

import java.util.UUID;

public class CoolEnchantsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.NECROMANCER, NecromancerRenderer::new);

        ClientPlayNetworking.registerGlobalReceiver(CoolEnchants.S2C_PLAY_EFFECTS_PACKET_ID,
                (client, handler, buf, responseSender) -> {
                    UUID effectPlayerUuid = buf.readUuid();
                    client.execute(() -> {
                        PlayerEntity effectPlayer = client.player.getEntityWorld().getPlayerByUuid(effectPlayerUuid);
                        if (effectPlayer != null) {
                            ModParticleEffects.play(client.player, effectPlayer, null, null, 1, 0, 0, 0, 0);
                        }
                    });
                });
        ClientPlayNetworking.registerGlobalReceiver(CoolEnchants.SB_PLAY_EFFECTS_PACKET_ID,
                (client, handler, buf, responseSender) -> {
                    UUID effectPlayerUuid = buf.readUuid();
                    client.execute(() -> {
                        PlayerEntity effectPlayer = client.player.getEntityWorld().getPlayerByUuid(effectPlayerUuid);
                        if (effectPlayer != null) {
                            ModParticleEffects.play(client.player, effectPlayer, null, null, 1, 0, 0, 0, 0);
                        }
                    });
                });
        HudRenderCallback.EVENT.register(new ChronoPauseHudOverlay());
        HudRenderCallback.EVENT.register(new ChronoSaveHudOverlay());
        HudRenderCallback.EVENT.register(new NecromancerHudOverlay());
        HudRenderCallback.EVENT.register(new DeflectHudOverlay());
        HudRenderCallback.EVENT.register(new UntouchableHudOverlay());
        HudRenderCallback.EVENT.register(new AstralBlinkHudOverlay());
        //EntityRendererRegistry.register(EntityType.CREEPER, (context) -> new HealthRenderer(context));

    }




}
