package net.josh.coolenchants.enchantment.misc;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class KibbleEnchantment extends Enchantment {

    public KibbleEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot... slotTypes) {
        super(weight, target, slotTypes);
    }

    @Override
    public int getMinPower(int level) {
        return 1 + (level - 1) * 9;
    }

    @Override
    public int getMaxPower(int level) {
        return super.getMinPower(level) + 50;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }
    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return false;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return false;
    }
    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() == Items.COOKED_BEEF ||
                stack.getItem() == Items.COOKED_CHICKEN ||
                stack.getItem() == Items.COOKED_COD ||
                stack.getItem() == Items.COOKED_MUTTON ||
                stack.getItem() == Items.COOKED_RABBIT ||
                stack.getItem() == Items.COOKED_PORKCHOP ||
                stack.getItem() == Items.COOKED_SALMON;
    }
}
