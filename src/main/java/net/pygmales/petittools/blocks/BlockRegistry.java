package net.pygmales.petittools.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.pygmales.petittools.PetitTools;
import net.pygmales.petittools.blocks.custom.BookBlock;
import net.pygmales.petittools.item_groups.ItemGroups;

import java.util.function.Function;

public class BlockRegistry {
    public static final Block AZURITE_ORE_BLOCK = registerBlock(
            "azurite_ore_block", Block::new, AbstractBlock.Settings.create()
                    .hardness(1.0f).requiresTool().sounds(BlockSoundGroup.STONE).luminance(settings -> 15), true);
    public static final Item AZURITE_ORE_BLOCK_ITEM = AZURITE_ORE_BLOCK.asItem();

    public static final Block CREEPER_SACK_BLOCK = registerBlock(
            "creeper_sack_block", Block::new,
            AbstractBlock.Settings.create().sounds(BlockSoundGroup.GRASS), true);
    public static final Item CREEPER_SACK_BLOCK_ITEM = CREEPER_SACK_BLOCK.asItem();

    public static final Block SACK_FURNACE_BLOCK = registerBlock(
            "sack_furnace", SackFurnace::new,
            FurnaceBlock.Settings.create(), true);
    public static final Item SACK_FURNACE_BLOCK_ITEM = SACK_FURNACE_BLOCK.asItem();

    public static final Block COUNTER_BLOCK = registerBlock(
            "counter_block", CounterBlock::new,
            CounterBlock.Settings.create(), true);
    public static final Item COUNTER_BLOCK_ITEM = COUNTER_BLOCK.asItem();

    public static final Block MINER_BLOCK = registerBlock(
            "miner_block", MinerBlock::new,
            MinerBlock.Settings.create(), true);
    public static final Item MINER_BLOCK_ITEM = MINER_BLOCK.asItem();

    public static final Block BOOK_BLOCK = registerBlock(
            "book_block", BookBlock::new,
            BookBlock.Settings.create(), true);
    public static final Item BOOK_BLOCK_ITEM = BOOK_BLOCK.asItem();

    public static Block registerBlock(String blockName, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings, boolean registerItem) {
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, PetitTools.id(blockName));
        Block block = blockFactory.apply(settings.registryKey(key));

        if (registerItem) registerBlockItem(blockName, block);

        return Registry.register(Registries.BLOCK, key, block);
    }

    public static void registerBlockItem(String blockName, Block block) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, PetitTools.id(blockName));
        BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(key));

        Registry.register(Registries.ITEM, key, blockItem);
    }

    public static void initialize() {
        ItemGroups.mainAddAll(
                CREEPER_SACK_BLOCK_ITEM,
                SACK_FURNACE_BLOCK_ITEM,
                COUNTER_BLOCK_ITEM,
                MINER_BLOCK_ITEM,
                BOOK_BLOCK_ITEM,
                AZURITE_ORE_BLOCK_ITEM
        );
    }

}
