package net.josh.coolenchants.effect;

import net.josh.coolenchants.CoolEnchants;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class FakeDamageEffect extends StatusEffect {

    public FakeDamageEffect(StatusEffectCategory type, int color) {
        super(type, color);
    }
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {

        }
    }
