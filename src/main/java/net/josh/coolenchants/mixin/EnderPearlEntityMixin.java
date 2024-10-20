package net.josh.coolenchants.mixin;


import net.josh.coolenchants.CoolEnchants;
import net.josh.coolenchants.ModUtils;
import net.josh.coolenchants.enchantment.ModEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.EndermiteEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(EnderPearlEntity.class)
public class EnderPearlEntityMixin {

//..........................SAFE PEARL PART 2 (other half in enderpearlitemmixin)....................

    EnderPearlEntity pearl = (EnderPearlEntity) (Object) this;
    Random random = new Random();

    /**
     * @author
     * @reason
     */
    @Overwrite
    public void onEntityHit(EntityHitResult entityHitResult) {
        //super.onEntityHit(entityHitResult);
        //ModUtils.activepearl = true;
        entityHitResult.getEntity().damage(pearl.getDamageSources().thrown(pearl, pearl.getOwner()),
                EnchantmentHelper.getLevel(ModEnchantments.SAFE_PEARL,
                        ((PlayerEntity)pearl.getOwner()).getEquippedStack(EquipmentSlot.FEET))*2);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {

    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    public void onCollision(HitResult hitResult) {
        //super.onCollision(hitResult);
        //ModUtils.activepearl = true;
        for (int i = 0; i < 32; ++i) {
            pearl.getWorld().addParticle(
                    ParticleTypes.PORTAL,
                    pearl.getX(),
                    pearl.getY() + random.nextDouble() * 2.0,
                    pearl.getZ(),
                    random.nextGaussian(),
                    0.0,
                    random.nextGaussian());
        }
        if (!pearl.getWorld().isClient && !pearl.isRemoved()) {
            Entity entity = pearl.getOwner();
            if (entity instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)entity;
                if (serverPlayerEntity.networkHandler.isConnectionOpen() &&
                        serverPlayerEntity.getWorld() == pearl.getWorld() &&
                        !serverPlayerEntity.isSleeping()) {
                    EndermiteEntity endermiteEntity;
                    if (random.nextFloat() < 0.05f &&
                            pearl.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING) &&
                            (endermiteEntity = EntityType.ENDERMITE.create(pearl.getWorld())) != null &&
                            !ModUtils.isWearingEnchantedBoots((PlayerEntity) entity, ModEnchantments.SAFE_PEARL)) {
                        endermiteEntity.refreshPositionAndAngles(entity.getX(), entity.getY(), entity.getZ(), entity.getYaw(), entity.getPitch());
                        pearl.getWorld().spawnEntity(endermiteEntity);
                    }
                    if (entity.hasVehicle()) {
                        serverPlayerEntity.requestTeleportAndDismount(pearl.getX(), pearl.getY(), pearl.getZ());
                    } else {
                        entity.requestTeleport(pearl.getX(), pearl.getY(), pearl.getZ());
                    }
                    entity.onLanding();
                    if (!ModUtils.isWearingEnchantedBoots( (PlayerEntity) entity, ModEnchantments.SAFE_PEARL)) {
                        entity.damage(pearl.getDamageSources().fall(), 5.0f);
                    }
                }
            } else if (entity != null) {
                entity.requestTeleport(pearl.getX(), pearl.getY(), pearl.getZ());
                entity.onLanding();
            }
            pearl.discard();
        }
    }
}
