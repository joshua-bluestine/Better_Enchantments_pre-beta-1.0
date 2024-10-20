package net.josh.coolenchants.mixin;

import net.josh.coolenchants.IEntityDataSaver;
import net.josh.coolenchants.ModUtils;
import net.josh.coolenchants.data.NecromancerOwnerData;
import net.josh.coolenchants.data.ThirstData;
import net.josh.coolenchants.effect.ModEffects;
import net.josh.coolenchants.enchantment.ModEnchantments;
import net.josh.coolenchants.entity.ModEntities;
import net.josh.coolenchants.entity.custom.NecromancerEntity;
import net.josh.coolenchants.world.dimension.ModDimensions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Method;
import java.util.Objects;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    int counter = 0;
    @Shadow
    public abstract void tick();

    private static Method tickStatusEffectsMethod;

    static {
        try {
            tickStatusEffectsMethod = LivingEntity.class.getDeclaredMethod("tickStatusEffects");
            tickStatusEffectsMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public int max = 20;

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void onTick(CallbackInfo info) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (!Objects.equals(ModUtils.removedEntity, "")){
            if (entity.getUuidAsString().contains(ModUtils.removedEntity)){
                if (!entity.getWorld().isClient()) {
                    System.out.println("test2");

                    ServerWorld serverWorld2 = (ServerWorld) entity.getWorld();
                    Random random = serverWorld2.getRandom();
                    for (int i = 1; i < 20; ++i) {
                        serverWorld2.spawnParticles(ParticleTypes.GLOW,
                                entity.getParticleX(1.0D),
                                entity.getY() + 1,
                                entity.getParticleZ(1.0D),
                                1,
                                random.nextGaussian() * 0.2D,
                                random.nextGaussian() * 0.2D,
                                random.nextGaussian() * 0.2D,
                                0.0);
                    }


                    RegistryKey<World> registryKey = entity.getWorld().getRegistryKey() == ModDimensions.ASTRAL_PLANE_LEVEL_KEY ? World.OVERWORLD : ModDimensions.ASTRAL_PLANE_LEVEL_KEY;
                    ServerWorld serverWorld = ((ServerWorld) entity.getWorld()).getServer().getWorld(registryKey);


                    Vec3d currentPosition = entity.getPos();
                    Vec3d astralPosition = new Vec3d(currentPosition.x, 0, currentPosition.z);
                    BlockPos portalPos = new BlockPos((int) astralPosition.x, (int) astralPosition.y, (int) astralPosition.z);
                    ModUtils.safeTeleport(serverWorld, portalPos, entity);
                    ModUtils.removedEntity = "";
                }
            }
        }
        if (ModUtils.chronoPause2) {
            if (!(entity instanceof PlayerEntity)) {
                PlayerEntity closestPlayer = entity.getWorld().getClosestPlayer(entity, max);
                if (closestPlayer != null && ModUtils.isWearingEnchantedHelmet(closestPlayer, ModEnchantments.CHRONO_PAUSE)) {
                    info.cancel();
                    return;
                }
            }
        }

        if (ModUtils.timeFreeze) {
            RegistryEntry<StatusEffect> timeFreezeEffectEntry = Registries.STATUS_EFFECT.getEntry(ModEffects.TIME_FREEZE_EFFECT);

            if (entity.hasStatusEffect(ModEffects.TIME_FREEZE_EFFECT)) {
                try {
                    tickStatusEffectsMethod.invoke(entity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                info.cancel();
                return;
            }
        }
    }
}
