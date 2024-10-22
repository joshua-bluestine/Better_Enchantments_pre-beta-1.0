package net.josh.coolenchants.effect;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.server.world.ServerWorld;
import org.apache.logging.log4j.core.jmx.Server;

public class LightningEffect extends StatusEffect {

    public LightningEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity != null) {
            if (entity.getWorld() instanceof ServerWorld) {
                EntityType.LIGHTNING_BOLT.spawn((ServerWorld) entity.getWorld(), entity.getBlockPos(), SpawnReason.TRIGGERED);
            }
        }
    }

    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        if (this == ModEffects.LIGHTNING_EFFECT) {
            int i = 50 >> amplifier;
            if (i > 0) {
                return duration % i == 0;
            }
            return true;
        }
        return false;
    }
}
