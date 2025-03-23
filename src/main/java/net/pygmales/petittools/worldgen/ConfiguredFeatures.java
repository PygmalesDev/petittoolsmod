package net.pygmales.petittools.worldgen;

import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.pygmales.petittools.PetitTools;
import net.pygmales.petittools.blocks.BlockRegistry;

import java.util.List;

public class ConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> OVERWORLD_AZURITE_ORE_KEY = registerKey("overworld_azurite_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> NETHER_AZURITE_ORE_KEY = registerKey("nether_azurite_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> END_AZURITE_ORE_KEY = registerKey("end_azurite_ore");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneOreReplaceables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateOreReplaceables = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherOreReplaceables = new TagMatchRuleTest(BlockTags.BASE_STONE_NETHER);
        RuleTest endOreReblaceables = new BlockMatchRuleTest(Blocks.END_STONE);

        List<OreFeatureConfig.Target> overworldAzuriteTargets = List.of(
                OreFeatureConfig.createTarget(stoneOreReplaceables, BlockRegistry.AZURITE_ORE_BLOCK.getDefaultState()),
                OreFeatureConfig.createTarget(deepslateOreReplaceables, BlockRegistry.AZURITE_ORE_BLOCK.getDefaultState()));
        register(context, OVERWORLD_AZURITE_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldAzuriteTargets, 16));
    }

    private static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, PetitTools.id(name));
    }

    private static <FC extends FeatureConfig, f extends Feature<FC>> void register(
            Registerable<ConfiguredFeature<?, ?>> context,
            RegistryKey<ConfiguredFeature<?, ?>> key,
            f feature,
            FC featureConfig) {
        context.register(key, new ConfiguredFeature<>(feature, featureConfig));
    }
}
