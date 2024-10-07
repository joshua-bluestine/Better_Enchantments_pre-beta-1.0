package net.josh.coolenchants.enchantment.armor.boots;

import net.josh.coolenchants.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FrostedIceBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import static net.josh.coolenchants.block.CooledLavaBlock.AGE;

public class LavaWalkerEnchantment extends Enchantment {
    public int counter = 0;
    public LavaWalkerEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot... slotTypes) {
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
    public boolean isAvailableForEnchantedBookOffer() {
        return false;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return false;
    }
    /*

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return false;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return false;
    }


     */
    public static void freezeWater(LivingEntity entity, World world, BlockPos blockPos, int level) {
        if (!entity.isOnGround()) {
            return;
        }
        BlockState blockState = ModBlocks.COOLED_LAVA.getDefaultState().with(AGE,0);
        int i = Math.min(16, 2 + level);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (BlockPos blockPos2 : BlockPos.iterate(blockPos.add(-i, -1, -i), blockPos.add(i, -1, i))) {
            BlockState blockState3;
            if (!blockPos2.isWithinDistance(entity.getPos(), (double)i)) continue;
            mutable.set(blockPos2.getX(), blockPos2.getY() + 1, blockPos2.getZ());
            BlockState blockState2 = world.getBlockState(mutable);
            if (!blockState2.isAir() || (blockState3 = world.getBlockState(blockPos2)) != Blocks.LAVA.getDefaultState() || !blockState.canPlaceAt(world, blockPos2) || !world.canPlace(blockState, blockPos2, ShapeContext.absent())) continue;
            world.setBlockState(blockPos2, blockState);
            world.scheduleBlockTick(blockPos2, ModBlocks.COOLED_LAVA, MathHelper.nextInt(entity.getRandom(), 60, 120));
        }
    }
    /*
    public static void freezeWater(LivingEntity entity, World world, BlockPos blockPos, int level) {
        if (!entity.isOnGround()) {
            return;
        }
        //BlockState blockState = ModBlocks.COOLED_LAVA.getDefaultState();
        BlockState blockState = Blocks.FROSTED_ICE.getDefaultState();
        int i = Math.min(16, 2 + level);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (BlockPos blockPos2 : BlockPos.iterate(blockPos.add(-i, -1, -i), blockPos.add(i, -1, i))) {
            BlockState blockState3;
            if (!blockPos2.isWithinDistance(entity.getPos(), (double)i)) continue;
            mutable.set(blockPos2.getX(), blockPos2.getY() + 1, blockPos2.getZ());
            BlockState blockState2 = world.getBlockState(mutable);
            if (!blockState2.isAir() || (blockState3 = world.getBlockState(blockPos2)).getBlock() != Blocks.LAVA || !blockState.canPlaceAt(world, blockPos2) || !world.canPlace(blockState, blockPos2, ShapeContext.absent())) continue;
            world.setBlockState(blockPos2, blockState);
            world.scheduleBlockTick(blockPos2, Blocks.FROSTED_ICE, MathHelper.nextInt(entity.getRandom(), 60, 120));
        }
    }

     */
}
//            if (!blockState2.isAir() || (blockState3 = world.getBlockState(blockPos2)) != FrostedIceBlock.getMeltedState() || !blockState.canPlaceAt(world, blockPos2) || !world.canPlace(blockState, blockPos2, ShapeContext.absent())) continue;
//((LivingEntity) target).setStatusEffect(new StatusEffectInstance(StatusEffects.POISON,100),null);

//((LivingEntity) target).setOnFire(true);
//((LivingEntity) target).setOnFireFor(2);

//EntityType.LIGHTNING_BOLT.spawn(world, target.getBlockPos(), SpawnReason.TRIGGERED);

//movementSpeedAttribute = ((LivingEntity) target).getAttributes();