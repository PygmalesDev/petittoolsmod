package net.pygmales.petittools;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import net.pygmales.petittools.data.generator.WorldGenerator;
import net.pygmales.petittools.data.provider.EnUsTranslationProvider;
import net.pygmales.petittools.data.provider.LootTableProvider;
import net.pygmales.petittools.data.provider.ModelProvider;
import net.pygmales.petittools.data.provider.RecipeProvider;
import net.pygmales.petittools.worldgen.ConfiguredFeatures;
import net.pygmales.petittools.worldgen.PlacedFeatures;

public class PetitToolsDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ModelProvider::new);
		pack.addProvider(EnUsTranslationProvider::new);
		pack.addProvider(LootTableProvider::new);
		pack.addProvider(RecipeProvider::new);
		pack.addProvider(WorldGenerator::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ConfiguredFeatures::bootstrap);
		registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, PlacedFeatures::bootstrap);
	}
}
