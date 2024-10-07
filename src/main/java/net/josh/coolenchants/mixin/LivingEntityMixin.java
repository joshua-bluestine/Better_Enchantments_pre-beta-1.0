package net.josh.coolenchants.mixin;

import net.josh.coolenchants.ModUtils;
import net.josh.coolenchants.enchantment.ModEnchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow
    public abstract void tick();
    public int max = 20;
    /*
    @Inject(method = "baseTick", at = @At("HEAD"), cancellable = true)
    private void onTick5(CallbackInfo info) {
        if (ModUtils.chronoPause) {
            Entity entity = (Entity) (Object) this;
            if (!(entity instanceof PlayerEntity)) {
                PlayerEntity closestPlayer = entity.getWorld().getClosestPlayer(entity, max);
                if (closestPlayer != null && ModUtils.isWearingEnchantedHelmet(closestPlayer, ModEnchantments.CHRONO_PAUSE)) {
                    // Cancel the tick if conditions are met
                    info.cancel();
                    return;
                }
            }
        }
    }

    @Inject(method = "lookAt", at = @At("HEAD"), cancellable = true)
    private void onTick2(CallbackInfo info) {
        if (ModUtils.chronoPause) {
            Entity entity = (Entity) (Object) this;
            if (!(entity instanceof PlayerEntity)) {
                PlayerEntity closestPlayer = entity.getWorld().getClosestPlayer(entity, max);
                if (closestPlayer != null && ModUtils.isWearingEnchantedHelmet(closestPlayer, ModEnchantments.CHRONO_PAUSE)) {
                    // Cancel the tick if conditions are met
                    info.cancel();
                    return;
                }
            }
        }
    }
    @Inject(method = "changeLookDirection", at = @At("HEAD"), cancellable = true)
    private void onTick3(CallbackInfo info) {
        if (ModUtils.chronoPause) {
            Entity entity = (Entity) (Object) this;
            if (!(entity instanceof PlayerEntity)) {
                PlayerEntity closestPlayer = entity.getWorld().getClosestPlayer(entity, max);
                if (closestPlayer != null && ModUtils.isWearingEnchantedHelmet(closestPlayer, ModEnchantments.CHRONO_PAUSE)) {
                    // Cancel the tick if conditions are met
                    info.cancel();
                    return;
                }
            }
        }
    }
    @Inject(method = "move", at = @At("HEAD"), cancellable = true)
    private void onTick4(CallbackInfo info) {
        if (ModUtils.chronoPause) {
            Entity entity = (Entity) (Object) this;
            if (!(entity instanceof PlayerEntity)) {

                PlayerEntity closestPlayer = entity.getWorld().getClosestPlayer(entity, max);
                if (closestPlayer != null && ModUtils.isWearingEnchantedHelmet(closestPlayer, ModEnchantments.CHRONO_PAUSE)) {
                    // Cancel the tick if conditions are met
                    info.cancel();
                    return;
                }
            }
        }
    }

     */
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void onTick(CallbackInfo info) {
        if (ModUtils.chronoPause) {
            Entity entity = (Entity) (Object) this;
            if (!(entity instanceof PlayerEntity)) {

                PlayerEntity closestPlayer = entity.getWorld().getClosestPlayer(entity, max);
                if (closestPlayer != null && ModUtils.isWearingEnchantedHelmet(closestPlayer, ModEnchantments.CHRONO_PAUSE)) {
                    // Cancel the tick if conditions are met
                    info.cancel();
                    return;
                }
            }
        }
        // Original tick logic will be called if not canceled
    }




}
