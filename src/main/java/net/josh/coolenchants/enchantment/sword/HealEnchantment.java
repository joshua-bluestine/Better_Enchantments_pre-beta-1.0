package net.josh.coolenchants.enchantment.sword;

import net.josh.coolenchants.enchantment.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;

public class HealEnchantment extends Enchantment {
    public int counter = 0;
    public HealEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot... slotTypes) {
        super(weight, target, slotTypes);
    }
    @Override
    public int getMinPower(int level) {
        return 12 + (level - 1) * 9;
    }

    @Override
    public int getMaxPower(int level) {
        return super.getMinPower(level) + 50;
    }

    @Override
    public boolean isTreasure() {
        return true;
    }
    @Override
    public int getMaxLevel(){return 3;}

    @Override
    public boolean canAccept(Enchantment other) {
        if (other == ModEnchantments.COW_SPAWNER ||
                other == ModEnchantments.LIGHTNING_STRIKER ||
                other == ModEnchantments.DEATH_MARK ||
                other == ModEnchantments.FLOATER ||
                other == ModEnchantments.LIFESTEAL ||
                other == ModEnchantments.POISON_ENCHANT ||
                other == ModEnchantments.WITHER ||
                other == ModEnchantments.REANIMATOR ||
                other == ModEnchantments.FREEZE ||
                other == ModEnchantments.CREEPER_EXPLODER ||
                other == ModEnchantments.CREEPER_NULLIFIER ||
                other == ModEnchantments.DINNERBONE ||
                other == ModEnchantments.EXPLODING_ARROWS ||
                other == ModEnchantments.TIME_CHANGE
        ){
            return false;
        }
        return true;

    }

}
