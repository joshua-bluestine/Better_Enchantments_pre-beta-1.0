package net.josh.coolenchants.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.josh.coolenchants.CoolEnchants;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static Block COOLED_LAVA;
    public static Block SEE_THROUGH;
    public static Block LIGHT_UP_BLOCK;
    public static Block HOED;
    private static Block register(String name, Block block){
        return Registry.register(Registries.BLOCK, new Identifier(CoolEnchants.MOD_ID, name), block);

    }

    public static void registerBlocks() {
        System.out.println("Registering Blocks for " + CoolEnchants.MOD_ID);


            COOLED_LAVA = register(
                "cooled_lava",
                new CooledLavaBlock(AbstractBlock.Settings.create().
                        mapColor(MapColor.PALE_PURPLE).
                        slipperiness(0.98f).ticksRandomly().
                        strength(0.5f).
                        sounds(BlockSoundGroup.GLASS).
                        nonOpaque().
                        solidBlock(Blocks::never)));

        SEE_THROUGH = register("st",
                new SeeThroughBlock(AbstractBlock.
                            Settings.create().
                            mapColor(MapColor.CLEAR).
                            noCollision().
                            sounds(BlockSoundGroup.SOUL_SOIL).
                            dropsNothing().
                            noBlockBreakParticles(),null));
        LIGHT_UP_BLOCK = register("light_up_block",
                new LightUpBlock(AbstractBlock.
                            Settings.create().
                            mapColor(MapColor.CLEAR).
                            noCollision().
                            sounds(BlockSoundGroup.SOUL_SOIL).
                            dropsNothing().
                            luminance(state -> 14).
                            noBlockBreakParticles(),null));


    }
    /*
    private static Item registerBlockItem(String name, Block block){
        return Registry.register(Registries.ITEM, new Identifier(CoolEnchants.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }
    */

}
