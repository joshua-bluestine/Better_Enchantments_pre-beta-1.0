package net.josh.coolenchants.mixin;

import net.josh.coolenchants.ModUtils;
import net.josh.coolenchants.effect.ModEffects;
import net.josh.coolenchants.enchantment.ModEnchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.CreeperIgniteGoal;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreeperEntity.class)
public class CreeperEntityMixin {
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void onTick(CallbackInfo info) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (ModUtils.chronoPause2) {
            if (!(entity instanceof PlayerEntity)) {

                PlayerEntity closestPlayer = entity.getWorld().getClosestPlayer(entity, 20);
                if (closestPlayer != null && ModUtils.isWearingEnchantedHelmet(closestPlayer, ModEnchantments.CHRONO_PAUSE)) {
                    info.cancel();
                    return;
                }
            }
        }
        if (ModUtils.timeFreeze) {
            if (entity.hasStatusEffect(ModEffects.TIME_FREEZE_EFFECT)) {
                info.cancel();
                return;
            }
        }
    }
}
