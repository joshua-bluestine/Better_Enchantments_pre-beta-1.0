package net.josh.coolenchants.enchantment.tools.axe;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;

public class ChopEnchantment extends Enchantment {
    public int counter = 0;
    public ChopEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot... slotTypes) {
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
        return false;
    }
    @Override
    public int getMaxLevel(){return 2;}
    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof AxeItem;
    }
    @Override
    public boolean canAccept(Enchantment other) {
        return super.canAccept(other) && other != Enchantments.SILK_TOUCH;
    }
}

//((LivingEntity) target).setStatusEffect(new StatusEffectInstance(StatusEffects.POISON,100),null);

//((LivingEntity) target).setOnFire(true);
//((LivingEntity) target).setOnFireFor(2);

//EntityType.LIGHTNING_BOLT.spawn(world, target.getBlockPos(), SpawnReason.TRIGGERED);

//movementSpeedAttribute = ((LivingEntity) target).getAttributes();