package net.josh.coolenchants.mixin;

import net.josh.coolenchants.BlockPosNBTUtil;
import net.josh.coolenchants.CoolEnchants;
import net.josh.coolenchants.ModUtils;
import net.josh.coolenchants.enchantment.ModEnchantments;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.client.model.ModelUtil;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOfferList;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.ArrayList;
import java.util.List;

@Mixin(FarmlandBlock.class)
public class FarmlandBlockMixin {
/*
green thumb (part one of three, others are clientplayerightclickmixin and hoeitemmixin)
 */
/**
 * @author
 * @reason
 */
@Overwrite
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int moisture = state.get(FarmlandBlock.MOISTURE);

        PlayerEntity player = world.getPlayers().get(0);
        if (isWaterNearby(world, pos) || world.hasRain(pos.up()) || BlockPosNBTUtil.isBlockPosInList(pos, CoolEnchants.some)) {
            if (moisture < 7) {
                world.setBlockState(pos, state.with(FarmlandBlock.MOISTURE, 7), Block.NOTIFY_LISTENERS);
            }
        } else if (moisture > 0) {
            world.setBlockState(pos, state.with(FarmlandBlock.MOISTURE, moisture - 1), Block.NOTIFY_LISTENERS);
        } else if (!hasCrop(world, pos)) {
            FarmlandBlock.setToDirt(null, state, world, pos);
        }
    }

    private boolean isWaterNearby(WorldView world, BlockPos pos) {
        for (BlockPos blockPos : BlockPos.iterate(pos.add(-4, 0, -4), pos.add(4, 1, 4))) {
            if (world.getFluidState(blockPos).isIn(FluidTags.WATER)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasCrop(BlockView world, BlockPos pos) {
        return world.getBlockState(pos.up()).isIn(BlockTags.MAINTAINS_FARMLAND);
    }

    private boolean isBlockInList(BlockPos pos, NbtList list) {
        for (int i = 0; i < list.size(); i++) {
            NbtCompound blockPosTag = list.getCompound(i);
            int x = blockPosTag.getInt("x");
            int y = blockPosTag.getInt("y");
            int z = blockPosTag.getInt("z");
            BlockPos storedPos = new BlockPos(x, y, z);
            if (pos.equals(storedPos)) {
                return true;
            }
        }
        return false;
    }
}
