package net.josh.coolenchants.mixin;

import net.josh.coolenchants.ModUtils;
import net.josh.coolenchants.enchantment.ModEnchantments;
import net.josh.coolenchants.event.BlockBreakHandler2;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

import static net.minecraft.block.LeavesBlock.DISTANCE;
import static net.minecraft.block.LeavesBlock.PERSISTENT;

@Mixin(LeavesBlock.class)
public class LeavesBlockMixin {

    @Shadow
    public static final IntProperty DISTANCE = Properties.DISTANCE_1_7;
    @Shadow
    public static final BooleanProperty PERSISTENT = Properties.PERSISTENT;

    LeavesBlock obama = (LeavesBlock) (Object) this;
    /**
     * @author
     * @reason
     */
    @Overwrite
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(PERSISTENT) == false && state.get(DISTANCE) == 7){
            if (world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 50, true) != null) {
                if (ModUtils.isHoldingEnchantedWeapon(
                        Objects.requireNonNull(world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 50, true)),
                        ModEnchantments.GOLDEN_APPLE)) {
                    if (world.getRandom().nextFloat() > 0.95) {
                        BlockBreakHandler2.dropOne(world, pos, Items.GOLDEN_APPLE);
                    }
                }
            }
            LeavesBlock.dropStacks(state, world, pos);
            world.removeBlock(pos, false);
        }
    }
}
