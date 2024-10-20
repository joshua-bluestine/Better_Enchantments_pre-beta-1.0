package net.josh.coolenchants;

import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.fabricmc.fabric.api.loot.v2.FabricLootTableBuilder;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.josh.coolenchants.enchantment.ModEnchantments;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.*;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ModLootTableModifiers {
    public static List<String> excavateBlocks = new ArrayList<>();
    public static final Identifier ACACIA
            = new Identifier("minecraft", "blocks/acacia_log");

    public static final Identifier BIRCH
            = new Identifier("minecraft", "blocks/birch_log");
    public static final Identifier OAK
            = new Identifier("minecraft", "blocks/oak_log");
    public static final Identifier JUNGLE
            = new Identifier("minecraft", "blocks/jungle_log");
    public static final Identifier DARK
            = new Identifier("minecraft", "blocks/dark_oak_log");
    public static final Identifier SPRUCE
            = new Identifier("minecraft", "blocks/spruce_log");
    public static final Identifier MANGROVE
            = new Identifier("minecraft", "blocks/mangrove_log");
    public static final Identifier CHERRY
            = new Identifier("minecraft", "blocks/cherry_log");
    public static final Identifier CRIMSON
            = new Identifier("minecraft", "blocks/crimson_log");
    public static final Identifier WARPED
            = new Identifier("minecraft", "blocks/warped_log");

    public static final Identifier IGLOO_STRUCTURE_CHEST_ID
            = new Identifier("minecraft", "chests/igloo_chest");
    public static final Identifier CREEPER_ID
            = new Identifier("minecraft", "entities/creeper");
    public static int counter = 0;
    public static int counter2 = 0;
    public static final Identifier DIRT
            = new Identifier("minecraft", "blocks/dirt");
    public static final Identifier GRASS_BLOCK
            = new Identifier("minecraft", "blocks/grass_block");
    public static final Identifier COARSE_DIRT
            = new Identifier("minecraft", "blocks/coarse_dirt");
    public static final Identifier ROOTED_DIRT
            = new Identifier("minecraft", "blocks/rooted_dirt");
    public static final Identifier END_STONE
            = new Identifier("minecraft", "blocks/end_stone");
    public static final Identifier CALCITE
            = new Identifier("minecraft", "blocks/calcite");
    public static final Identifier NETHERRACK
            = new Identifier("minecraft", "blocks/netherrack");
    public static final Identifier BASALT
            = new Identifier("minecraft", "blocks/basalt");
    public static final Identifier BLACKSTONE
            = new Identifier("minecraft", "blocks/blackstone");
    public static final Identifier SOUL_SAND
            = new Identifier("minecraft", "blocks/soul_sand");
    public static final Identifier SOUL_SOIL
            = new Identifier("minecraft", "blocks/soul_soil");
    public static final Identifier DIRT_PATH
            = new Identifier("minecraft", "blocks/dirt_path");

    public static final Identifier GRAVEL
            = new Identifier("minecraft", "blocks/gravel");
    public static final Identifier SAND
            = new Identifier("minecraft", "blocks/sand");
    public static final Identifier SANDSTONE
            = new Identifier("minecraft", "blocks/sandstone");
    public static final Identifier STONE
            = new Identifier("minecraft", "blocks/stone");
    public static final Identifier DEEPSLATE
            = new Identifier("minecraft", "blocks/deepslate");
    public static final Identifier ANDESITE
            = new Identifier("minecraft", "blocks/andesite");

    public static final Identifier DIORITE
            = new Identifier("minecraft", "blocks/diorite");
    public static final Identifier GRANITE
            = new Identifier("minecraft", "blocks/granite");
    public static final Identifier TUFF
            = new Identifier("minecraft", "blocks/tuff");
    public static final Identifier PILLAGER_CHEST
            = new Identifier("minecraft", "chests/pillager_outpost");
    public static final Identifier ANCIENT_CITY
            = new Identifier("minecraft", "chests/ruined_portal");



    public static final Identifier ZOMBIE
            = new Identifier("minecraft", "entities/zombie");
    public static final Identifier SKELETON
            = new Identifier("minecraft", "entities/skeleton");


    public static void modifyLootTablesChests() {
        LootTableLoadingCallback.EVENT.register((resourceManager, manager, id, supplier, setter) -> {
            if (PILLAGER_CHEST.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(1f)) // Adjust the chance as needed
                        .with(ItemEntry.builder(Items.BOOK).
                                apply(EnchantRandomlyLootFunction.create().add(ModEnchantments.PERSUADE)))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 1)).build());
                supplier.withPool(poolBuilder.build());
            }
        });


    }


    public static void modifyLootTablesMining() {
        LootTableLoadingCallback.EVENT.register(((resourceManager, manager, id, supplier, setter) -> {

            if (DIRT.equals(id)) {
                excavateBlocks.add(id.toString());
                setter.set(LootTable.EMPTY);
            }
            if (ROOTED_DIRT.equals(id)) {
                excavateBlocks.add(id.toString());
                setter.set(LootTable.EMPTY);
            }
            if (COARSE_DIRT.equals(id)) {
                excavateBlocks.add(id.toString());
                setter.set(LootTable.EMPTY);
            }
            if (END_STONE.equals(id)) {
                excavateBlocks.add(id.toString());
                setter.set(LootTable.EMPTY);
            }
            if (CALCITE.equals(id)) {
                excavateBlocks.add(id.toString());
                setter.set(LootTable.EMPTY);
            }
            if (NETHERRACK.equals(id)) {
                excavateBlocks.add(id.toString());
                setter.set(LootTable.EMPTY);
            }
            if (BASALT.equals(id)) {
                excavateBlocks.add(id.toString());
                setter.set(LootTable.EMPTY);
            }
            if (BLACKSTONE.equals(id)) {
                excavateBlocks.add(id.toString());
                setter.set(LootTable.EMPTY);
            }
            if (SOUL_SAND.equals(id)) {
                excavateBlocks.add(id.toString());
                setter.set(LootTable.EMPTY);
            }
            if (SOUL_SOIL.equals(id)) {
                excavateBlocks.add(id.toString());
                setter.set(LootTable.EMPTY);
            }
            if (DIRT_PATH.equals(id)) {
                excavateBlocks.add(id.toString());
                setter.set(LootTable.EMPTY);
            }

            if (GRASS_BLOCK.equals(id)) {
                excavateBlocks.add(id.toString());
                setter.set(LootTable.EMPTY);
            }
            if (GRAVEL.equals(id)) {
                excavateBlocks.add(id.toString());
                setter.set(LootTable.EMPTY);
            }
            if (SAND.equals(id)) {
                excavateBlocks.add(id.toString());
                setter.set(LootTable.EMPTY);
            }
            if (SANDSTONE.equals(id)) {
                excavateBlocks.add(id.toString());
                setter.set(LootTable.EMPTY);
            }
            if (STONE.equals(id)) {
                excavateBlocks.add(id.toString());
                setter.set(LootTable.EMPTY);
            }
            if (DEEPSLATE.equals(id)) {
                excavateBlocks.add(id.toString());
                setter.set(LootTable.EMPTY);
            }
            if (ANDESITE.equals(id)) {
                excavateBlocks.add(id.toString());
                setter.set(LootTable.EMPTY);
            }
            if (DIORITE.equals(id)) {
                excavateBlocks.add(id.toString());
                setter.set(LootTable.EMPTY);
            }
            if (GRANITE.equals(id)) {

                excavateBlocks.add(id.toString());
                setter.set(LootTable.EMPTY);
            }
            if (TUFF.equals(id)) {
                excavateBlocks.add(id.toString());
                setter.set(LootTable.EMPTY);
            }
        }));
    }
        public static void modifyLootTablesWood2() {
            LootTableLoadingCallback.EVENT.register(((resourceManager, manager, id, supplier, setter) -> {
                if (ACACIA.equals(id)) {
                    FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(1.00f)) // Drops 35% of the time
                            .with(ItemEntry.builder(Items.ACACIA_LOG))
                            .withFunction(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());

                    LootTable emptyLoot = LootTable.EMPTY;
                    setter.set(emptyLoot);
                    // supplier.withPool(poolBuilder.build());
                }
                if (BIRCH.equals(id)) {

                    FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(1.00f)) // Drops 35% of the time
                            .with(ItemEntry.builder(Items.BIRCH_LOG))
                            .withFunction(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());

                    LootTable emptyLoot = LootTable.EMPTY;
                    setter.set(emptyLoot);
                    // supplier.withPool(poolBuilder.build());
                }
                if (SPRUCE.equals(id)) {

                    FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(1f)) // Drops 35% of the time
                            .with(ItemEntry.builder(Items.SPRUCE_LOG))
                            .withFunction(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
                    LootTable emptyLoot = LootTable.EMPTY;
                    setter.set(emptyLoot);
                    // supplier.withPool(poolBuilder.build());
                }
                if (OAK.equals(id)) {

                    FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(1f)) // Drops 35% of the time
                            .with(ItemEntry.builder(Items.OAK_LOG))
                            .withFunction(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
                    LootTable emptyLoot = LootTable.EMPTY;
                    setter.set(emptyLoot);
                    //supplier.withPool(poolBuilder.build());
                }
                if (JUNGLE.equals(id)) {

                    FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(1f)) // Drops 35% of the time
                            .with(ItemEntry.builder(Items.JUNGLE_LOG))
                            .withFunction(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
                    LootTable emptyLoot = LootTable.EMPTY;
                    setter.set(emptyLoot);
                    //supplier.withPool(poolBuilder.build());
                }
                if (DARK.equals(id)) {

                    FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(1f)) // Drops 35% of the time
                            .with(ItemEntry.builder(Items.DARK_OAK_LOG))
                            .withFunction(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
                    LootTable emptyLoot = LootTable.EMPTY;
                    setter.set(emptyLoot);
                    //supplier.withPool(poolBuilder.build());
                }
                if (MANGROVE.equals(id)) {

                    FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(1f)) // Drops 35% of the time
                            .with(ItemEntry.builder(Items.MANGROVE_LOG))
                            .withFunction(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
                    LootTable emptyLoot = LootTable.EMPTY;
                    setter.set(emptyLoot);
                    //supplier.withPool(poolBuilder.build());
                }
                if (CHERRY.equals(id)) {

                    FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(1f)) // Drops 35% of the time
                            .with(ItemEntry.builder(Items.CHERRY_LOG))
                            .withFunction(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
                    LootTable emptyLoot = LootTable.EMPTY;
                    setter.set(emptyLoot);
                    //supplier.withPool(poolBuilder.build());
                }
                if (CRIMSON.equals(id)) {

                    FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(1f)) // Drops 35% of the time
                            .with(ItemEntry.builder(Items.CRIMSON_STEM))
                            .withFunction(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
                    LootTable emptyLoot = LootTable.EMPTY;
                    setter.set(emptyLoot);
                    // supplier.withPool(poolBuilder.build());
                }
                if (WARPED.equals(id)) {

                    FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(1f)) // Drops 35% of the time
                            .with(ItemEntry.builder(Items.WARPED_STEM))
                            .withFunction(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
                    LootTable emptyLoot = LootTable.EMPTY;
                    setter.set(emptyLoot);
                    //supplier.withPool(poolBuilder.build());
                }

            }));
        }
    }



/*
     public static void modifyLootTablesWood() {
        LootTableEvents.MODIFY.register(((resourceManager, lootManager, id, tableBuilder, source) -> {
            if(ACACIA.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(1.00f)) // Drops 35% of the time
                        .with(ItemEntry.builder(Items.ACACIA_PLANKS))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4.0f, 4.0f)).build());

               // LootTable emptyLoot = LootTable.EMPTY;
                //setter.set(emptyLoot);

                tableBuilder.pool(poolBuilder.build());
                System.out.println(counter);
                counter =1;
            }
            if(BIRCH.equals(id)) {

                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(1.00f)) // Drops 35% of the time
                        .with(ItemEntry.builder(Items.BIRCH_PLANKS))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4.0f, 4.0f)).build());

                //LootTable emptyLoot = LootTable.EMPTY;
                //setter.set(emptyLoot);
                tableBuilder.pool(poolBuilder.build());
                counter =1;

            }
            if(SPRUCE.equals(id)) {

                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(1f)) // Drops 35% of the time
                        .with(ItemEntry.builder(Items.SPRUCE_PLANKS))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4.0f, 4.0f)).build());
                LootTable emptyLoot = LootTable.EMPTY;
               // setter.set(emptyLoot);
                tableBuilder.pool(poolBuilder.build());
                counter =1;

            }
            if(OAK.equals(id)) {

                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(1f)) // Drops 35% of the time
                        .with(ItemEntry.builder(Items.OAK_PLANKS))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4.0f, 4.0f)).build());
                LootTable emptyLoot = LootTable.EMPTY;
                //setter.set(emptyLoot);
                tableBuilder.pool(poolBuilder.build());
                counter =1;

            }
            if(JUNGLE.equals(id)) {

                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(1f)) // Drops 35% of the time
                        .with(ItemEntry.builder(Items.JUNGLE_PLANKS))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4.0f, 4.0f)).build());
                LootTable emptyLoot = LootTable.EMPTY;
                //setter.set(emptyLoot);
                tableBuilder.pool(poolBuilder.build());
                counter =1;

            }
            if(DARK.equals(id)) {

                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(1f)) // Drops 35% of the time
                        .with(ItemEntry.builder(Items.DARK_OAK_PLANKS))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4.0f, 4.0f)).build());
                LootTable emptyLoot = LootTable.EMPTY;
                //setter.set(emptyLoot);
                tableBuilder.pool(poolBuilder.build());
                counter =1;

            }
            if(MANGROVE.equals(id)) {

                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(1f)) // Drops 35% of the time
                        .with(ItemEntry.builder(Items.MANGROVE_PLANKS))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4.0f, 4.0f)).build());
                LootTable emptyLoot = LootTable.EMPTY;
                //setter.set(emptyLoot);
                tableBuilder.pool(poolBuilder.build());
                counter =1;

            }
            if(CHERRY.equals(id)) {

                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(1f)) // Drops 35% of the time
                        .with(ItemEntry.builder(Items.CHERRY_PLANKS))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4.0f, 4.0f)).build());
                LootTable emptyLoot = LootTable.EMPTY;
                //setter.set(emptyLoot);
                tableBuilder.pool(poolBuilder.build());
                counter =1;

            }
            if(CRIMSON.equals(id)) {

                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(1f)) // Drops 35% of the time
                        .with(ItemEntry.builder(Items.CRIMSON_PLANKS))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4.0f, 4.0f)).build());
                LootTable emptyLoot = LootTable.EMPTY;
                //setter.set(emptyLoot);
                tableBuilder.pool(poolBuilder.build());
                counter =1;

            }
            if(WARPED.equals(id)) {

                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(1f)) // Drops 35% of the time
                        .with(ItemEntry.builder(Items.ACACIA_PLANKS))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4.0f, 4.0f)).build());
                LootTable emptyLoot = LootTable.EMPTY;
                //setter.set(emptyLoot);
                tableBuilder.pool(poolBuilder.build());
                counter =1;

            }

        }));
    }

*/