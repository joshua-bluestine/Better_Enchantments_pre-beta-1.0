package net.josh.coolenchants;

import com.google.gson.JsonElement;
import net.josh.coolenchants.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class CustomBlockStateModelGenerator extends BlockStateModelGenerator {

        public CustomBlockStateModelGenerator(Consumer<BlockStateSupplier> blockStateCollector, BiConsumer<Identifier, Supplier<JsonElement>> modelCollector, Consumer<Item> simpleItemModelExemptionCollector) {
            super(blockStateCollector, modelCollector, simpleItemModelExemptionCollector);
        }


    public void registerCooledLavaBlock() {
            this.blockStateCollector.accept(VariantsBlockStateSupplier.create(ModBlocks.COOLED_LAVA)
                    .coordinate(BlockStateVariantMap.create(Properties.AGE_3)
                            .register(0, BlockStateVariant.create().put(VariantSettings.MODEL, createSubModel(ModBlocks.COOLED_LAVA, "_0", Models.CUBE_ALL, TextureMap::all)))
                            .register(1, BlockStateVariant.create().put(VariantSettings.MODEL, createSubModel(ModBlocks.COOLED_LAVA, "_1", Models.CUBE_ALL, TextureMap::all)))
                            .register(2, BlockStateVariant.create().put(VariantSettings.MODEL, createSubModel(ModBlocks.COOLED_LAVA, "_2", Models.CUBE_ALL, TextureMap::all)))
                            .register(3, BlockStateVariant.create().put(VariantSettings.MODEL, createSubModel(ModBlocks.COOLED_LAVA, "_3", Models.CUBE_ALL, TextureMap::all)))
                    )
            );
        }

       // private Identifier createSubModels(Block block, String suffix, Model model, Function<Identifier, TextureMap> texturesFactory) {
         //   String blockName = Registries.BLOCK.getId(block).getPath(); // Get the block's registry name
      //      return new Identifier(blockName + suffix); // Construct and return the model identifier
       // }
    }


