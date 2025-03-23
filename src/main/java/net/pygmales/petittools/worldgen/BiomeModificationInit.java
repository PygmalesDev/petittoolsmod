package net.pygmales.petittools.worldgen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.gen.GenerationStep;

public class BiomeModificationInit {
    public static void initialize() {
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                PlacedFeatures.OVERWORLD_AZURITE_ORE_KEY
        );
    }
}
