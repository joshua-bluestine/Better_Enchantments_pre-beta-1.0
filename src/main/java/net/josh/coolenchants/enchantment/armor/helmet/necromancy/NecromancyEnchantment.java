package net.josh.coolenchants.enchantment.armor.helmet.necromancy;

import net.josh.coolenchants.enchantment.armor.helmet.AstralTeleportEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class NecromancyEnchantment extends Enchantment {

    public NecromancyEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot... slotTypes) {
        super(weight, target, slotTypes);
    }
    @Override
    public int getMinPower(int level) {
        return 24 + (level - 1) * 9;
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
    public int getMaxLevel(){return 5;}
    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return false;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return false;
    }

    @Override
    public boolean canAccept(Enchantment other) {
        if (other instanceof AstralTeleportEnchantment) {
            return false;
        }
        return super.canAccept(other);
    }
}