package net.pygmales.petittools.data.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;
import net.pygmales.petittools.blocks.BlockRegistry;

import java.util.concurrent.CompletableFuture;


public class LootTableProvider extends FabricBlockLootTableProvider {
    public LootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(BlockRegistry.CREEPER_SACK_BLOCK);
        addDrop(BlockRegistry.SACK_FURNACE_BLOCK);
    }
}
