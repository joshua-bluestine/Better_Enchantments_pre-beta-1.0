package net.josh.coolenchants.enchantment.sword;

import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import static net.minecraft.block.FluidBlock.LEVEL;

public class LavaSpawnerEnchantment extends Enchantment {
    public int counter = 0;
    public LavaSpawnerEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot... slotTypes) {
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
    public void onTargetDamaged(LivingEntity user, Entity target, int level){

        if (target instanceof LivingEntity && !user.getWorld().isClient) {

            ServerWorld world = (ServerWorld) user.getWorld();
            BlockPos position = target.getBlockPos();
            if (target instanceof LivingEntity) {
                // ((LivingEntity) target).setOnFire(true);

                world.setBlockState(target.getBlockPos(), Blocks.LAVA.getDefaultState().with(LEVEL, 1));
            }
        }
        super.onTargetDamaged(user, target, level);
    }
}
