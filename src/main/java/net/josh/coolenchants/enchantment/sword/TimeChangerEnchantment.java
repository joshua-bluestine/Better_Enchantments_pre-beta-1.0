package net.josh.coolenchants.enchantment.sword;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class TimeChangerEnchantment extends Enchantment {
    public int counter = 0;
    public TimeChangerEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot... slotTypes) {
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
        return false;
    }

}

//((LivingEntity) target).setStatusEffect(new StatusEffectInstance(StatusEffects.POISON,100),null);

//((LivingEntity) target).setOnFire(true);
//((LivingEntity) target).setOnFireFor(2);

//EntityType.LIGHTNING_BOLT.spawn(world, target.getBlockPos(), SpawnReason.TRIGGERED);

//movementSpeedAttribute = ((LivingEntity) target).getAttributes();