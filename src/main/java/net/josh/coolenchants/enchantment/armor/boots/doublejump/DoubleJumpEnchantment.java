package net.josh.coolenchants.enchantment.armor.boots.doublejump;

import net.josh.coolenchants.enchantment.ModEnchantments;
import net.josh.coolenchants.enchantment.armor.boots.GravityEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.MendingEnchantment;
import net.minecraft.entity.EquipmentSlot;

public class DoubleJumpEnchantment extends Enchantment {

    public DoubleJumpEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot... slotTypes) {
        super(weight, target, slotTypes);
    }
    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean isTreasure(){
        return false;
    }
    @Override
    public int getMinPower(int level) {
        return 15 + (level - 1) * 9;
    }

    @Override
    public int getMaxPower(int level) {
        return super.getMinPower(level) + 50;
    }
    @Override
    public boolean canAccept(Enchantment other) {
        if (other instanceof GravityEnchantment) {
            return false;
        }
        return super.canAccept(other);
    }
    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return true;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return true;
    }
}
