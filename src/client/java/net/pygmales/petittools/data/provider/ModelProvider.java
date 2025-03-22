package net.pygmales.petittools.data.provider;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.*;
import net.pygmales.petittools.blocks.BlockRegistry;
import net.pygmales.petittools.items.ItemRegistry;
public class ModelProvider extends FabricModelProvider {

    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(BlockRegistry.CREEPER_SACK_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(BlockRegistry.COUNTER_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ItemRegistry.SUSPICIOUS_SUBSTANCE, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.CREEPER_SACK, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.COUNTER_PICKAXE, Models.HANDHELD);
    }
}
