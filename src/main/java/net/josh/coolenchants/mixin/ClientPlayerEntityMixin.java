package net.josh.coolenchants.mixin;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.josh.coolenchants.CoolEnchants;
import net.josh.coolenchants.effect.ModEffects;
import net.josh.coolenchants.enchantment.armor.boots.doublejump.DoubleJumpEffect;
import net.josh.coolenchants.enchantment.sword.DeathMarkEnchantment;
import net.josh.coolenchants.enchantment.sword.deathmark.DeathMarkEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.network.PacketByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.network.ClientPlayerEntity;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {


    @Shadow public abstract boolean damage(DamageSource source, float amount);

    @Shadow protected abstract void applyDamage(DamageSource source, float amount);

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void tickMovement(CallbackInfo info) {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
        if (DeathMarkEnchantment.duration > 0 && DeathMarkEnchantment.michael.getType() != EntityType.ENDER_DRAGON &&
                DeathMarkEnchantment.michael.getType() != EntityType.WARDEN &&
                DeathMarkEnchantment.michael.getType() != EntityType.ELDER_GUARDIAN &&
                DeathMarkEnchantment.michael.getType() != EntityType.WITHER) {
            DeathMarkEnchantment.duration -= 1;
            if (((LivingEntity) DeathMarkEnchantment.michael).getHealth() <= 0){
                DeathMarkEnchantment.duration = 0;
            }
            if (DeathMarkEnchantment.duration == 0){
                DeathMarkEffect.play((LivingEntity) DeathMarkEnchantment.michael, player);
                PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
                passedData.writeUuid(player.getUuid());
                ClientPlayNetworking.send(CoolEnchants.DM_EFFECT, passedData);

                DeathMarkEnchantment.onDeath(DeathMarkEnchantment.michael);
        }
        }

        }
}