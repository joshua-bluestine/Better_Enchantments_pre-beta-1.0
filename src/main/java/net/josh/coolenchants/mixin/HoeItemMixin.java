package net.josh.coolenchants.mixin;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.josh.coolenchants.BlockPosNBTUtil;
import net.josh.coolenchants.CoolEnchants;
import net.josh.coolenchants.enchantment.ModEnchantments;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
@Mixin(HoeItem.class)
public class HoeItemMixin {

/*
green thumb (part two of three, others are farmlandblockmixin and clientplayerrightclickmixin)
 */
    @Shadow
    protected static final Map<Block, Pair<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>>> TILLING_ACTIONS = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Pair.of(HoeItem::canTillFarmland, HoeItem.createTillAction(Blocks.FARMLAND.getDefaultState())), Blocks.DIRT_PATH, Pair.of(HoeItem::canTillFarmland, HoeItem.createTillAction(Blocks.FARMLAND.getDefaultState())), Blocks.DIRT, Pair.of(HoeItem::canTillFarmland, HoeItem.createTillAction(Blocks.FARMLAND.getDefaultState())), Blocks.COARSE_DIRT, Pair.of(HoeItem::canTillFarmland, HoeItem.createTillAction(Blocks.DIRT.getDefaultState())), Blocks.ROOTED_DIRT, Pair.of(itemUsageContext -> true, HoeItem.createTillAndDropAction(Blocks.DIRT.getDefaultState(), Items.HANGING_ROOTS))));
//........................GREEN THUMB............................
/**
 * @author
 * @reason
 */
@Overwrite
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos blockPos;
        World world = context.getWorld();
        Pair<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>> pair = TILLING_ACTIONS.get(world.getBlockState(blockPos = context.getBlockPos()).getBlock());
        if (pair == null) {
            return ActionResult.PASS;
        }
        Predicate<ItemUsageContext> predicate = pair.getFirst();
        Consumer<ItemUsageContext> consumer = pair.getSecond();
        if (predicate.test(context)) {
            PlayerEntity playerEntity = context.getPlayer();
            if (EnchantmentHelper.getLevel(ModEnchantments.GREEN_THUMB, playerEntity.getMainHandStack()) > 0) {
                BlockPosNBTUtil.writeBlockPosToList(blockPos, CoolEnchants.some);
                Random random = playerEntity.getWorld().getRandom();
                for (int i = 0; i < 20; i++) {
                    double d = random.nextGaussian() * 0.16D;
                    double e = random.nextGaussian() * 0.16D;
                    double f = random.nextGaussian() * 0.16D;
                    playerEntity.getWorld().addParticle(ParticleTypes.HAPPY_VILLAGER,
                            blockPos.getX() + 0.5 + random.nextGaussian()*0.3,
                            0.5 + blockPos.getY()+1+random.nextGaussian() * 0.1D,
                            blockPos.getZ() + 0.5 + random.nextGaussian()*0.3, d, e, f);
                }

            }
            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
            if (!world.isClient) {
                consumer.accept(context);
                if (playerEntity != null) {
                    context.getStack().damage(1, playerEntity, p -> p.sendToolBreakStatus(context.getHand()));
                }
            }
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }
}
