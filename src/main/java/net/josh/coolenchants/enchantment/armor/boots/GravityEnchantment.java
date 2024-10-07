package net.josh.coolenchants.enchantment.armor.boots;

import net.josh.coolenchants.enchantment.armor.boots.doublejump.DoubleJumpEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.*;
import net.minecraft.entity.EquipmentSlot;

public class GravityEnchantment extends Enchantment {
    public GravityEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot... slotTypes) {
        super(weight, target, slotTypes);
    }

    @Override
    public boolean isTreasure(){
        return true;
    }
    @Override
    public int getMinPower(int level) {
        return 30;
    }

    @Override
    public int getMaxPower(int level) {
        return 90;
    }
    @Override
    public int getMaxLevel() {
        return 1;
    }
    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return true;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return true;
    }
    @Override
    public boolean canAccept(Enchantment other) {
        if (other instanceof DoubleJumpEnchantment) {
            return false;
        }
        return super.canAccept(other);
    }
}