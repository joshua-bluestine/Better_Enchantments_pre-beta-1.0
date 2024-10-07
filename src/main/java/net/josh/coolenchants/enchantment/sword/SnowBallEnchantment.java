package net.josh.coolenchants.enchantment.sword;

import net.josh.coolenchants.enchantment.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class SnowBallEnchantment extends Enchantment {

    public SnowBallEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot... slotTypes) {
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
    public int getMaxLevel(){return 1;}

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return false;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return true;
    }

    @Override
    public boolean canAccept(Enchantment other) {
        if (other == ModEnchantments.BLAZE_SHOOTER ||
                other == ModEnchantments.DRAGON_SHOOTER ||
                other == ModEnchantments.WITHER_SKULL ||
                other == ModEnchantments.FIRE_SHOOTER ||
                other == ModEnchantments.WARDEN_SHOOTER
        ){
            return false;
        }
        return true;
    }

}
