package net.josh.coolenchants.mixin;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(StatusEffect.class)
public class StatusEffectMixin {
    StatusEffect boobies = (StatusEffect) (Object) this;
    /**
     * @author
     * @reason
     */
    @Overwrite
    public void applyInstantEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
        if (boobies == StatusEffects.INSTANT_HEALTH && !target.isUndead() || boobies == StatusEffects.INSTANT_DAMAGE && target.isUndead()) {
            if (!(source instanceof AreaEffectCloudEntity)) {
                int i = (int) (proximity * (double) (4 << amplifier) + 0.5);
                target.heal(i);
            } else {
                int i = (int)(proximity * (double)(6 << amplifier) + 0.5);
                target.damage(target.getDamageSources().magic(), i);
            }
        } else if (boobies == StatusEffects.INSTANT_DAMAGE && !target.isUndead() || boobies == StatusEffects.INSTANT_HEALTH && target.isUndead()) {
            int i = (int)(proximity * (double)(6 << amplifier) + 0.5);
            if (source == null) {
                target.damage(target.getDamageSources().magic(), i);
            } else {
                target.damage(target.getDamageSources().indirectMagic(source, attacker), i);
            }
        } else {
            boobies.applyUpdateEffect(target, amplifier);
        }
    }
}
