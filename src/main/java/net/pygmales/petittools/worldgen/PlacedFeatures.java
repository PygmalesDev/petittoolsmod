package net.pygmales.petittools.worldgen;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.*;
import net.pygmales.petittools.PetitTools;

import java.util.List;

public class PlacedFeatures {
    public static final RegistryKey<PlacedFeature> OVERWORLD_AZURITE_ORE_KEY = registerKey("overworld_azurite_ore");

    public static void bootstrap(Registerable<PlacedFeature> context) {
        RegistryEntryLookup<ConfiguredFeature<?,?>> registryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context, OVERWORLD_AZURITE_ORE_KEY, registryLookup.getOrThrow(ConfiguredFeatures.OVERWORLD_AZURITE_ORE_KEY),
                Modifiers.modifiersWithCount(16, HeightRangePlacementModifier.uniform(YOffset.BOTTOM, YOffset.fixed(64))));
    }

    private static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, PetitTools.id(name));
    }

    private static void register(
            Registerable<PlacedFeature> context,
            RegistryKey<PlacedFeature> key,
            RegistryEntry<ConfiguredFeature<?, ?>> config,
            List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(config, List.copyOf(modifiers)));
    }

    private static class Modifiers {
        public static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
            return List.of(countModifier, SquarePlacementModifier.of(), heightModifier, BiomePlacementModifier.of());
        }

        public static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModifier) {
            return modifiers(CountPlacementModifier.of(count), heightModifier);
        }

        public static List<PlacementModifier> modifiersWithRarity(int chance, PlacementModifier heightModifier) {
            return modifiers(RarityFilterPlacementModifier.of(chance), heightModifier);
        }
    }
}
