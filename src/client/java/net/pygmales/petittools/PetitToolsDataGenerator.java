package net.pygmales.petittools;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.pygmales.petittools.data.provider.EnUsTranslationProvider;
import net.pygmales.petittools.data.provider.LootTableProvider;
import net.pygmales.petittools.data.provider.ModelProvider;
import net.pygmales.petittools.data.provider.RecipeProvider;

public class PetitToolsDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ModelProvider::new);
		pack.addProvider(EnUsTranslationProvider::new);
		pack.addProvider(LootTableProvider::new);
		pack.addProvider(RecipeProvider::new);
	}
}
