package net.pygmales.petittools.entities;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.pygmales.petittools.PetitTools;
import net.pygmales.petittools.blocks.BlockRegistry;

public class BlockEntityTypeInit {
    public static final BlockEntityType<CounterBlockEntity> COUNTER_BLOCK_ENTITY = register("counter_block_entity",
            FabricBlockEntityTypeBuilder.create(CounterBlockEntity::new, BlockRegistry.COUNTER_BLOCK).build());
    public static final BlockEntityType<MinerBlockEntity> MINER_BLOCK_ENTITY = register("miner_block_entity",
            FabricBlockEntityTypeBuilder.create(MinerBlockEntity::new, BlockRegistry.MINER_BLOCK).build());

    public static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> type) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, PetitTools.id(name), type);
    }

    public static void initialize() {

    }
}
