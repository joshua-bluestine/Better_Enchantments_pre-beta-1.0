package net.josh.coolenchants.event;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.josh.coolenchants.IEntityDataSaver;
import net.josh.coolenchants.ModLootTableModifiers;
import net.josh.coolenchants.ModUtils;
import net.josh.coolenchants.data.SpleefData;
import net.josh.coolenchants.enchantment.ModEnchantments;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class BlockBreakHandler2 implements PlayerBlockBreakEvents.After {
    private static final Identifier ACACIA
            = new Identifier("minecraft", "blocks/acacia_log");
    public static Random r = new Random();
    int tunnelCounter = 0;
    int check;

    @Override
    public void afterBlockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) {
        if (!player.getAbilities().creativeMode) {

            boolean hasExcavate = false;
            boolean hasSilkTouch = false;
            if (!player.getMainHandStack().isEmpty()) {
                hasExcavate = ModUtils.isHoldingEnchantedWeapon(player, ModEnchantments.EXCAVATE);
                hasSilkTouch = ModUtils.isHoldingEnchantedWeapon(player, Enchantments.SILK_TOUCH);
            } else {
                hasExcavate = false;
                hasSilkTouch = false;
            }
            silkTouchExcavateCheck(world, pos, hasExcavate, hasSilkTouch, state);

            breaksWithForage(world, player, pos, state);
            if (state.getBlock() instanceof CropBlock){
                breaksWithHarvest(world, player, pos, state);

                if (ModUtils.isHoldingEnchantedWeapon(player, ModEnchantments.SCYTHE)) {
                    for (BlockPos poop : getWheat(world, pos)) {
                        world.breakBlock(poop, true, player);
                        breaksWithHarvest(world, player, poop, world.getBlockState(poop));
                    }
                }
            }

            if (state.getBlock() instanceof PillarBlock) {
                breaksWithChop(world, player, pos, state);
                breakswithTimber(world, player, pos, state);
            }

            if ((state.getBlock() == Blocks.SAND ||
                    state.getBlock() == Blocks.DIRT ||
                    state.getBlock() == Blocks.GRASS_BLOCK ||
                    state.getBlock() == Blocks.GRAVEL)){

                breaksWithSpleef(world, player, pos);
            }

            doTunnel(player, world, pos, tunnelCounter, hasExcavate, hasSilkTouch);

        }
    }
    public static void breaksWithForage (World world, PlayerEntity player, BlockPos pos, BlockState state){
        int check;
        if((state.getBlock()==Blocks.GRASS ||
            state.getBlock()==Blocks.TALL_GRASS)&&
            ModUtils.isHoldingEnchantedWeapon(player,ModEnchantments.FORAGE)) {

        if (EnchantmentHelper.getLevel(ModEnchantments.FORAGE, player.getEquippedStack(EquipmentSlot.MAINHAND)) == 1) {
            check = r.nextInt(12);
            if (r.nextGaussian() > 0.85) {
                if (check > 8) {
                    dropSome(world, pos, Items.POTATO, r.nextInt(2) + 1);
                } else if (check > 4) {
                    dropSome(world, pos, Items.CARROT, r.nextInt(2) + 1);
                } else if (check > 0) {
                    dropSome(world, pos, Items.BEETROOT_SEEDS, r.nextInt(2) + 1);
                } else {
                    dropSome(world, pos, Items.POISONOUS_POTATO, r.nextInt(2) + 1);
                }
            }
        } else {
            check = r.nextInt(21);
            if (r.nextGaussian() > 0.9) {
                if (check > 19) {
                    dropSome(world, pos, Items.SWEET_BERRIES, r.nextInt(2) + 1);
                } else if (check > 18) {
                    dropSome(world, pos, Items.BEETROOT_SEEDS, r.nextInt(2) + 1);
                } else if (check > 16) {
                    dropSome(world, pos, Items.CARROT, r.nextInt(2) + 1);
                } else if (check > 14) {
                    dropSome(world, pos, Items.BEETROOT_SEEDS, r.nextInt(2) + 1);
                } else if (check > 10) {
                    dropSome(world, pos, Items.MELON_SEEDS, r.nextInt(2) + 1);
                } else if (check > 6) {
                    dropSome(world, pos, Items.PUMPKIN_SEEDS, r.nextInt(2) + 1);
                } else if (check > 2) {
                    dropSome(world, pos, Items.COCOA_BEANS, r.nextInt(2) + 1);
                } else if (check > 1) {
                    dropSome(world, pos, Items.GOLDEN_CARROT, r.nextInt(2) + 1);
                } else if (check > 0) {
                    dropSome(world, pos, Items.GLISTERING_MELON_SLICE, r.nextInt(2) + 1);
                }
            }
        }
    }
}
    public void breaksWithChop (World world, PlayerEntity player, BlockPos pos, BlockState state){
        if (ModUtils.isHoldingEnchantedWeapon(player, ModEnchantments.CHOP)) {
            if (EnchantmentHelper.getLevel(ModEnchantments.CHOP,
                    player.getEquippedStack(EquipmentSlot.MAINHAND)) == 1) {
                if (!world.isClient && state.getBlock() == Blocks.ACACIA_LOG) {
                    dropEight(world, pos, Items.ACACIA_PLANKS);
                } else if (!world.isClient && state.getBlock() == Blocks.OAK_LOG) {
                    dropEight(world, pos, Items.OAK_PLANKS);
                } else if (!world.isClient && state.getBlock() == Blocks.SPRUCE_LOG) {
                    dropEight(world, pos, Items.SPRUCE_PLANKS);
                } else if (!world.isClient && state.getBlock() == Blocks.BIRCH_LOG) {
                    dropEight(world, pos, Items.BIRCH_PLANKS);
                } else if (!world.isClient && state.getBlock() == Blocks.DARK_OAK_LOG) {
                    dropEight(world, pos, Items.DARK_OAK_PLANKS);
                } else if (!world.isClient && state.getBlock() == Blocks.MANGROVE_LOG) {
                    dropEight(world, pos, Items.MANGROVE_PLANKS);
                } else if (!world.isClient && state.getBlock() == Blocks.JUNGLE_LOG) {
                    dropEight(world, pos, Items.JUNGLE_PLANKS);
                } else if (!world.isClient && state.getBlock() == Blocks.CHERRY_LOG) {
                    dropEight(world, pos, Items.CHERRY_PLANKS);
                } else if (!world.isClient && state.getBlock() == Blocks.WARPED_STEM) {
                    dropEight(world, pos, Items.WARPED_PLANKS);
                } else if (!world.isClient && state.getBlock() == Blocks.CRIMSON_STEM) {
                    dropEight(world, pos, Items.CRIMSON_PLANKS);
                }
            } else if (!world.isClient && (
                    state.getBlock() == Blocks.ACACIA_LOG ||
                            state.getBlock() == Blocks.OAK_LOG ||
                            state.getBlock() == Blocks.SPRUCE_LOG ||
                            state.getBlock() == Blocks.BIRCH_LOG ||
                            state.getBlock() == Blocks.DARK_OAK_LOG ||
                            state.getBlock() == Blocks.MANGROVE_LOG ||
                            state.getBlock() == Blocks.JUNGLE_LOG ||
                            state.getBlock() == Blocks.CHERRY_LOG ||
                            state.getBlock() == Blocks.WARPED_STEM ||
                            state.getBlock() == Blocks.CRIMSON_STEM
            )) {
                dropEight(world, pos, Items.STICK);
                dropEight(world, pos, Items.STICK);
                dropEight(world, pos, Items.STICK);
            }
        } else if (!ModUtils.isHoldingEnchantedWeapon(player, ModEnchantments.CHOP)) {
            if (!world.isClient && state.getBlock() == Blocks.ACACIA_LOG) {
                dropOne(world, pos, Items.ACACIA_LOG);
            } else if (!world.isClient && state.getBlock() == Blocks.OAK_LOG) {
                dropOne(world, pos, Items.OAK_LOG);
            } else if (!world.isClient && state.getBlock() == Blocks.SPRUCE_LOG) {
                dropOne(world, pos, Items.SPRUCE_LOG);
            } else if (!world.isClient && state.getBlock() == Blocks.BIRCH_LOG) {
                dropOne(world, pos, Items.BIRCH_LOG);
            } else if (!world.isClient && state.getBlock() == Blocks.DARK_OAK_LOG) {
                dropOne(world, pos, Items.DARK_OAK_LOG);
            } else if (!world.isClient && state.getBlock() == Blocks.MANGROVE_LOG) {
                dropOne(world, pos, Items.MANGROVE_LOG);
            } else if (!world.isClient && state.getBlock() == Blocks.JUNGLE_LOG) {
                dropOne(world, pos, Items.JUNGLE_LOG);
            } else if (!world.isClient && state.getBlock() == Blocks.CHERRY_LOG) {
                dropOne(world, pos, Items.CHERRY_LOG);
            } else if (!world.isClient && state.getBlock() == Blocks.WARPED_STEM) {
                dropOne(world, pos, Items.WARPED_STEM);
            } else if (!world.isClient && state.getBlock() == Blocks.CRIMSON_STEM) {
                dropOne(world, pos, Items.CRIMSON_STEM);
            }
        }
    }
    public void breakswithTimber (World world, PlayerEntity player, BlockPos pos, BlockState state){
        if (ModUtils.isHoldingEnchantedWeapon(player, ModEnchantments.TIMBER)) {
            if (state.getBlock() == Blocks.OAK_LOG ||
                    state.getBlock() == Blocks.SPRUCE_LOG ||
                    state.getBlock() == Blocks.BIRCH_LOG ||
                    state.getBlock() == Blocks.JUNGLE_LOG ||
                    state.getBlock() == Blocks.ACACIA_LOG ||
                    state.getBlock() == Blocks.DARK_OAK_LOG ||
                    state.getBlock() == Blocks.MANGROVE_LOG ||
                    state.getBlock() == Blocks.CHERRY_LOG) {
                BlockPos position = pos.up();
                while (world.getBlockState(position) == state) {
                    world.breakBlock(position, true);
                    if (ModUtils.isHoldingEnchantedWeapon(player, ModEnchantments.CHOP)) {
                        breaksWithChop(world, player, position, state);
                    } else {
                        dropOne(world, position, state.getBlock().asItem());
                    }
                    position = position.up();
                }
            }
        }
    }
    public void breaksWithSpleef (World world, PlayerEntity player, BlockPos pos) {
        if (ModUtils.isHoldingEnchantedWeapon(player, ModEnchantments.SPLEEF)) {
            SpleefData.setSpleef((IEntityDataSaver) player, pos);
            for (BlockPos greg = pos; greg.getY() > (pos.getY() - 50); greg = greg.down()) {
                SpleefData.addSpleef((IEntityDataSaver) player, world.getBlockState(greg));
                world.setBlockState(greg, Blocks.AIR.getDefaultState());
            }
        }
    }
    public void dropFour (World world, BlockPos pos, Item item){
            ItemStack drop = new ItemStack(item, 4);
            double x = pos.getX() + 0.5;
            double y = pos.getY() + 0.5;
            double z = pos.getZ() + 0.5;
            ItemEntity itemEntity = new ItemEntity((ServerWorld) world, x, y, z, drop);
            world.spawnEntity(itemEntity);
        }
    public void dropEight (World world, BlockPos pos, Item item){
            ItemStack drop = new ItemStack(item, 8);
            double x = pos.getX() + 0.5;
            double y = pos.getY() + 0.5;
            double z = pos.getZ() + 0.5;
            ItemEntity itemEntity = new ItemEntity((ServerWorld) world, x, y, z, drop);
            world.spawnEntity(itemEntity);
        }
    public static void dropOne(World world, BlockPos pos, Item item){
            ItemStack drop = new ItemStack(item, 1);
            double x = pos.getX() + 0.5;
            double y = pos.getY() + 0.5;
            double z = pos.getZ() + 0.5;
            ItemEntity itemEntity = new ItemEntity((ServerWorld) world, x, y, z, drop);
            world.spawnEntity(itemEntity);
        }
    public static void dropSome(World world, BlockPos pos, Item item, int amount){
        ItemStack drop = new ItemStack(item, amount);
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.5;
        double z = pos.getZ() + 0.5;
        ItemEntity itemEntity = new ItemEntity((ServerWorld) world, x, y, z, drop);
        world.spawnEntity(itemEntity);
    }
    private static void doTunnel(PlayerEntity player, World world, BlockPos pos, int tunnelCounter, boolean hasExcavate, boolean hasSilkTouch) {
        if (ModUtils.isHoldingEnchantedWeapon(player, ModEnchantments.TUNNEL)) {
                int level = EnchantmentHelper.getLevel(ModEnchantments.TUNNEL, player.getMainHandStack());

                BlockPos pos2 = pos;

                switch (player.getHorizontalFacing()) {
                    case EAST -> pos2 = pos.east();
                    case WEST -> pos2 = pos.west();
                    case NORTH -> pos2 = pos.north();
                    case SOUTH -> pos2 = pos.south();
                }

                while (player.getMainHandStack().isSuitableFor(world.getBlockState(pos2)) &&
                        tunnelCounter < level) {

                    tunnelCounter++;
                    System.out.println(tunnelCounter);
                    System.out.println(world.getBlockState(pos2).getBlock().toString());
                    if (tunnelCounter == 1) {
                        tunnel3(player, world, pos, level, hasExcavate, hasSilkTouch);
                    }
                    silkTouchExcavateCheck(world, pos2, hasExcavate, hasSilkTouch,world.getBlockState(pos2));

                    world.breakBlock(pos2, true);
                    tunnel3(player, world, pos2, level, hasExcavate, hasSilkTouch);
                    switch (player.getHorizontalFacing()) {
                        case WEST -> pos2 = pos2.west();
                        case EAST -> pos2 = pos2.east();
                        case NORTH -> pos2 = pos2.north();
                        case SOUTH -> pos2 = pos2.south();

                    }
                }
                tunnelCounter = 0;
            }
    }
    private static void tunnel3(PlayerEntity player, World world, BlockPos pos2, int level, boolean hasExcavate, boolean hasSilkTouch) {
        switch (player.getHorizontalFacing()) {
            case WEST:
                if (level == 5) {
                    if (player.getMainHandStack().isSuitableFor(world.getBlockState(pos2.up()))) {
                        silkTouchExcavateCheck(world, pos2.up(), hasExcavate, hasSilkTouch);
                        world.breakBlock(pos2.up(), true);
                    }
                    if (player.getMainHandStack().isSuitableFor(world.getBlockState(pos2.down()))) {
                        silkTouchExcavateCheck(world, pos2.down(), hasExcavate, hasSilkTouch);
                        world.breakBlock(pos2.down(), true);
                    }
                    if (player.getMainHandStack().isSuitableFor(world.getBlockState(pos2.south()))) {
                        silkTouchExcavateCheck(world, pos2.south(), hasExcavate, hasSilkTouch);
                        world.breakBlock(pos2.south(), true);
                    }
                    if (player.getMainHandStack().isSuitableFor(world.getBlockState(pos2.south().down()))) {
                        silkTouchExcavateCheck(world, pos2.south().down(), hasExcavate, hasSilkTouch);
                        world.breakBlock(pos2.south().down(), true);
                    }
                    if (player.getMainHandStack().isSuitableFor(world.getBlockState(pos2.south().up()))) {
                        silkTouchExcavateCheck(world, pos2.south().up(), hasExcavate, hasSilkTouch);
                        world.breakBlock(pos2.south().up(), true);
                    }
                    if (player.getMainHandStack().isSuitableFor(world.getBlockState(pos2.north()))) {
                        silkTouchExcavateCheck(world, pos2.north(), hasExcavate, hasSilkTouch);
                        world.breakBlock(pos2.north(), true);
                    }
                    if (player.getMainHandStack().isSuitableFor(world.getBlockState(pos2.north().down()))) {
                        silkTouchExcavateCheck(world, pos2.north().down(), hasExcavate, hasSilkTouch);
                        world.breakBlock(pos2.north().down(), true);
                    }
                    if (player.getMainHandStack().isSuitableFor(world.getBlockState(pos2.north().up()))) {
                        silkTouchExcavateCheck(world, pos2.north().up(), hasExcavate, hasSilkTouch);
                        world.breakBlock(pos2.north().up(), true);
                    }
                }
                pos2 = pos2.west();
                break;
            case EAST:
                if (level == 5) {
                    if (player.getMainHandStack().isSuitableFor(world.getBlockState(pos2.up()))) {
                        silkTouchExcavateCheck(world, pos2.up(), hasExcavate, hasSilkTouch);
                        world.breakBlock(pos2.up(), true);
                    }
                    if (player.getMainHandStack().isSuitableFor(world.getBlockState(pos2.down()))) {
                        silkTouchExcavateCheck(world, pos2.down(), hasExcavate, hasSilkTouch);
                        world.breakBlock(pos2.down(), true);
                    }
                    if (player.getMainHandStack().isSuitableFor(world.getBlockState(pos2.south()))) {
                        silkTouchExcavateCheck(world, pos2.south(), hasExcavate, hasSilkTouch);
                        world.breakBlock(pos2.south(), true);
                    }
                    if (player.getMainHandStack().isSuitableFor(world.getBlockState(pos2.south().down()))) {
                        silkTouchExcavateCheck(world, pos2.south().down(), hasExcavate, hasSilkTouch);
                        world.breakBlock(pos2.south().down(), true);
                    }
                    if (player.getMainHandStack().isSuitableFor(world.getBlockState(pos2.south().up()))) {
                        silkTouchExcavateCheck(world, pos2.south().up(), hasExcavate, hasSilkTouch);
                        world.breakBlock(pos2.south().up(), true);
                    }
                    if (player.getMainHandStack().isSuitableFor(world.getBlockState(pos2.north()))) {
                        silkTouchExcavateCheck(world, pos2.north(), hasExcavate, hasSilkTouch);
                        world.breakBlock(pos2.north(), true);
                    }
                    if (player.getMainHandStack().isSuitableFor(world.getBlockState(pos2.north().down()))) {
                        silkTouchExcavateCheck(world, pos2.north().down(), hasExcavate, hasSilkTouch);
                        world.breakBlock(pos2.north().down(), true);
                    }
                    if (player.getMainHandStack().isSuitableFor(world.getBlockState(pos2.north().up()))) {
                        silkTouchExcavateCheck(world, pos2.north().up(), hasExcavate, hasSilkTouch);
                        world.breakBlock(pos2.north().up(), true);
                    }
                }
                pos2 = pos2.east();
                break;
            case SOUTH:
                if (level == 5) {
                    if (player.getMainHandStack().isSuitableFor(world.getBlockState(pos2.up()))) {
                        silkTouchExcavateCheck(world, pos2.up(), hasExcavate, hasSilkTouch);
                        world.breakBlock(pos2.up(), true);
                    }
                    if (player.getMainHandStack().isSuitableFor(world.getBlockState(pos2.down()))) {
                        silkTouchExcavateCheck(world, pos2.down(), hasExcavate, hasSilkTouch);
                        world.breakBlock(pos2.down(), true);
                    }
                    if (player.getMainHandStack().isSuitableFor(world.getBlockState(pos2.east()))) {
                        silkTouchExcavateCheck(world, pos2.east(), hasExcavate, hasSilkTouch);
                        world.breakBlock(pos2.east(), true);
                    }
                    if (player.getMainHandStack().isSuitableFor(world.getBlockState(pos2.east().down()))) {
                        silkTouchExcavateCheck(world, pos2.east().down(), hasExcavate, hasSilkTouch);
                        world.breakBlock(pos2.east().down(), true);
                    }
                    if (player.getMainHandStack().isSuitableFor(world.getBlockState(pos2.east().up()))) {
                        silkTouchExcavateCheck(world, pos2.east().up(), hasExcavate, hasSilkTouch);
                        world.breakBlock(pos2.east().up(), true);
                    }
                    if (player.getMainHandStack().isSuitableFor(world.getBlockState(pos2.west()))) {
                        silkTouchExcavateCheck(world, pos2.west(), hasExcavate, hasSilkTouch);
                        world.breakBlock(pos2.west(), true);
                    }
                    if (player.getMainHandStack().isSuitableFor(world.getBlockState(pos2.west().down()))) {
                        silkTouchExcavateCheck(world, pos2.west().down(), hasExcavate, hasSilkTouch);
                        world.breakBlock(pos2.west().down(), true);
                    }
                    if (player.getMainHandStack().isSuitableFor(world.getBlockState(pos2.west().up()))) {
                        silkTouchExcavateCheck(world, pos2.west().up(), hasExcavate, hasSilkTouch);
                        world.breakBlock(pos2.west().up(), true);
                    }
                }
                pos2 = pos2.south();
                break;
            case NORTH:
                if (level == 5) {
                    if (player.getMainHandStack().isSuitableFor(world.getBlockState(pos2.up()))) {
                        silkTouchExcavateCheck(world, pos2.up(), hasExcavate, hasSilkTouch);
                        world.breakBlock(pos2.up(), true);
                    }
                    if (player.getMainHandStack().isSuitableFor(world.getBlockState(pos2.down()))) {
                        silkTouchExcavateCheck(world, pos2.down(), hasExcavate, hasSilkTouch);
                        world.breakBlock(pos2.down(), true);
                    }
                    if (player.getMainHandStack().isSuitableFor(world.getBlockState(pos2.east()))) {
                        silkTouchExcavateCheck(world, pos2.east(), hasExcavate, hasSilkTouch);
                        world.breakBlock(pos2.east(), true);
                    }
                    if (player.getMainHandStack().isSuitableFor(world.getBlockState(pos2.east().down()))) {
                        silkTouchExcavateCheck(world, pos2.east().down(), hasExcavate, hasSilkTouch);
                        world.breakBlock(pos2.east().down(), true);
                    }
                    if (player.getMainHandStack().isSuitableFor(world.getBlockState(pos2.east().up()))) {
                        silkTouchExcavateCheck(world, pos2.east().up(), hasExcavate, hasSilkTouch);
                        world.breakBlock(pos2.east().up(), true);
                    }
                    if (player.getMainHandStack().isSuitableFor(world.getBlockState(pos2.west()))) {
                        silkTouchExcavateCheck(world, pos2.west(), hasExcavate, hasSilkTouch);
                        world.breakBlock(pos2.west(), true);
                    }
                    if (player.getMainHandStack().isSuitableFor(world.getBlockState(pos2.west().down()))) {
                        silkTouchExcavateCheck(world, pos2.west().down(), hasExcavate, hasSilkTouch);
                        world.breakBlock(pos2.west().down(), true);
                    }
                    if (player.getMainHandStack().isSuitableFor(world.getBlockState(pos2.west().up()))) {
                        silkTouchExcavateCheck(world, pos2.west().up(), hasExcavate, hasSilkTouch);
                        world.breakBlock(pos2.west().up(), true);
                    }
                }
                pos2 = pos2.north();
                break;
            default:
                break;
        }
    }
    private static List<BlockPos> getWheat(World world, BlockPos startPos) {
        List<BlockPos> positions = new ArrayList<>();
        int radius = 4;
        for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos pos = startPos.add(x, 0, z);
                    BlockState state = world.getBlockState(pos);
                    if (state.getBlock() instanceof CropBlock) {
                        if (((CropBlock) state.getBlock()).getAge(state) == ((CropBlock) state.getBlock()).getMaxAge()) {
                            positions.add(pos);
                        }
                    }
                }
            }
        return positions;
    }
    public static void breaksWithHarvest(World world, PlayerEntity player, BlockPos pos, BlockState state) {
        int check;
        if (state.getBlock() instanceof CropBlock) {
            if (((CropBlock) state.getBlock()).getAge(state) == ((CropBlock) state.getBlock()).getMaxAge()) {
                if (state.getBlock() == Blocks.WHEAT &&
                        ModUtils.isHoldingEnchantedWeapon(player, ModEnchantments.HARVEST)) {
                    check = r.nextInt(12);
                    if (check > 8) {
                        dropSome(world, pos, Items.WHEAT, 3);
                    } else if (check > 4) {
                        dropSome(world, pos, Items.WHEAT, 2);
                    } else {
                        dropSome(world, pos, Items.WHEAT, 1);
                    }
                } else if (state.getBlock() == Blocks.BEETROOTS &&
                        ModUtils.isHoldingEnchantedWeapon(player, ModEnchantments.HARVEST)) {
                    check = r.nextInt(12);
                    if (check > 8) {
                        dropSome(world, pos, Items.BEETROOT, 3);
                    } else if (check > 4) {
                        dropSome(world, pos, Items.BEETROOT, 2);
                    } else {
                        dropSome(world, pos, Items.BEETROOT, 1);
                    }
                } else if (state.getBlock() == Blocks.CARROTS &&
                        ModUtils.isHoldingEnchantedWeapon(player, ModEnchantments.HARVEST)) {
                    check = r.nextInt(12);
                    if (check > 8) {
                        dropSome(world, pos, Items.CARROT, 3);
                    } else if (check > 4) {
                        dropSome(world, pos, Items.CARROT, 2);
                    } else {
                        dropSome(world, pos, Items.CARROT, 1);
                    }
                } else if (state.getBlock() == Blocks.POTATOES &&
                        ModUtils.isHoldingEnchantedWeapon(player, ModEnchantments.HARVEST)) {
                    check = r.nextInt(12);
                    if (check > 8) {
                        dropSome(world, pos, Items.POTATO, 3);
                    } else if (check > 4) {
                        dropSome(world, pos, Items.POTATO, 2);
                    } else {
                        dropSome(world, pos, Items.POTATO, 1);
                    }
                }
            }
        }
    }
    public static void silkTouchExcavateCheck(World world, BlockPos pos2, boolean hasExcavate, boolean hasSilkTouch, BlockState state){
        if (!hasSilkTouch) {
            if (!hasExcavate){
                System.out.println(state.getBlock().getLootTableId().toString());
                if (ModLootTableModifiers.excavateBlocks.contains(state.getBlock().getLootTableId().toString())) {
                    System.out.println("its one of them");
                    switch (state.getBlock().getLootTableId().toString()) {
                        case "minecraft:blocks/grass_block", "minecraft:blocks/dirt_path":
                            dropOne(world, pos2, Items.DIRT);
                            break;
                        case "minecraft:blocks/gravel":
                            if (world.getRandom().nextGaussian() <= 0.9) {
                                dropOne(world, pos2, Items.GRAVEL);
                            } else {
                                dropOne(world, pos2, Items.FLINT);
                            }
                            break;
                        case "minecraft:blocks/stone":
                            dropOne(world, pos2, Items.COBBLESTONE);
                            break;
                        case "minecraft:blocks/deepslate":
                            dropOne(world, pos2, Items.COBBLED_DEEPSLATE);
                            break;
                        default:
                            dropOne(world, pos2, state.getBlock().asItem());
                            break;
                    }
                }
            }
        }
    }
    public static void silkTouchExcavateCheck(World world, BlockPos pos2, boolean hasExcavate, boolean hasSilkTouch){
        BlockState state = world.getBlockState(pos2);
        if (!hasSilkTouch) {
            if (!hasExcavate){
                System.out.println(state.getBlock().getLootTableId().toString());
                if (ModLootTableModifiers.excavateBlocks.contains(state.getBlock().getLootTableId().toString())) {
                    System.out.println("its one of them");
                    switch (state.getBlock().getLootTableId().toString()) {
                        case "minecraft:blocks/grass_block", "minecraft:blocks/dirt_path":
                            dropOne(world, pos2, Items.DIRT);
                            break;
                        case "minecraft:blocks/gravel":
                            if (world.getRandom().nextGaussian() <= 0.9) {
                                dropOne(world, pos2, Items.GRAVEL);
                            } else {
                                dropOne(world, pos2, Items.FLINT);
                            }
                            break;
                        case "minecraft:blocks/stone":
                            dropOne(world, pos2, Items.COBBLESTONE);
                            break;
                        case "minecraft:blocks/deepslate":
                            dropOne(world, pos2, Items.COBBLED_DEEPSLATE);
                            break;
                        default:
                            dropOne(world, pos2, state.getBlock().asItem());
                            break;
                    }
                }
            }
        }
    }
}