package net.josh.coolenchants.enchantment.sword;

import net.josh.coolenchants.ModUtils;
import net.josh.coolenchants.enchantment.ModEnchantments;
import net.josh.coolenchants.world.dimension.ModDimensions;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class AstralRemoveEnchantment extends Enchantment {
    public int counter = 0;
    public AstralRemoveEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot... slotTypes) {
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
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {

    }
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
