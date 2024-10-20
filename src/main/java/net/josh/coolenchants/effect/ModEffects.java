package net.josh.coolenchants.effect;

import net.josh.coolenchants.CoolEnchants;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEffects {


    public static StatusEffect registerEffects(String name, StatusEffect effect) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(CoolEnchants.MOD_ID, name), effect);
    }

    public static void registerModEffects() {

        SPEED2 = registerEffects(
                "speed2",
                new Speed2Effect(StatusEffectCategory.BENEFICIAL, 3402751)
                        .addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,
                                "91AEAA56-376B-4498-935B-2F7F68070635",
                                0.8f,
                                EntityAttributeModifier.Operation.MULTIPLY_TOTAL));

        CREEPER_NULL = registerEffects(
                "creeper_null",
                new CreeperNullEffect(StatusEffectCategory.NEUTRAL,0xFF0000));
        DEATH_MARK_EFFECT = registerEffects(
                "death_mark",
                new DeathMarkLiveEffect(StatusEffectCategory.NEUTRAL,2039587));
        TIME_FREEZE_EFFECT = registerEffects(
                "time_freeze",
                new TimeFreezeEffect(StatusEffectCategory.NEUTRAL,0xFF0000));
        LIGHTNING_EFFECT = registerEffects(
                "lighting_effect",
                new LightningEffect(StatusEffectCategory.NEUTRAL,0xDD2300));
        FAKE_DAMAGE = registerEffects(
                "fake_damage",
                new LightningEffect(StatusEffectCategory.HARMFUL,0xDD2300));
        System.out.println("Registering Effects for " + CoolEnchants.MOD_ID);

    }
    public static StatusEffect SPEED2;
    public static StatusEffect CREEPER_NULL;
    public static StatusEffect LIGHTNING_EFFECT;
    public static StatusEffect DEATH_MARK_EFFECT;
    public static StatusEffect TIME_FREEZE_EFFECT;
    public static StatusEffect FAKE_DAMAGE;
}