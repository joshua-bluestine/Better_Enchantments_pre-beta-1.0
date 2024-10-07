package net.josh.coolenchants;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.josh.coolenchants.biomes.ModBiomes;
import net.josh.coolenchants.datagen.ModWorldGenerator;
import net.josh.coolenchants.world.dimension.ModDimensions;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class CoolEnchantsDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ModWorldGenerator::new);

		//pack.addProvider(
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder){
		registryBuilder.addRegistry(RegistryKeys.DIMENSION_TYPE, ModDimensions::bootstrapType);
		registryBuilder.addRegistry(RegistryKeys.BIOME, ModBiomes::boostrap);
	}
}
