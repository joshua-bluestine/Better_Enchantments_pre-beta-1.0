package net.josh.coolenchants.enchantment.sword;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class ReanimatorEnchantment extends Enchantment {
    public static int duration = 0;
    public static int duration2 = 0;
    public static Entity michael;
    public ReanimatorEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot... slotTypes) {
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

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level){
        michael = target;
        LivingEntity john = (LivingEntity) target;
        if (target instanceof LivingEntity && !user.getWorld().isClient) {

            if (john.getGroup() == EntityGroup.UNDEAD && john.getHealth() <= 0) {
                ((ZombieEntity) john).convertTo(EntityType.VILLAGER, true);
                ServerWorld world = (ServerWorld) user.getWorld();
                BlockPos position = target.getBlockPos();
                //EntityType.VILLAGER.spawn((ServerWorld) target.getWorld(), target.getBlockPos(), SpawnReason.TRIGGERED);

            }
        }
        super.onTargetDamaged(user, target, level);
    }
}
