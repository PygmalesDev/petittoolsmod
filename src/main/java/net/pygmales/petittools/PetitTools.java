package net.pygmales.petittools;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.util.Identifier;
import net.pygmales.petittools.blocks.BlockRegistry;
import net.pygmales.petittools.entities.BlockEntityTypeInit;
import net.pygmales.petittools.entities.MinerBlockEntity;
import net.pygmales.petittools.item_groups.ItemGroups;
import net.pygmales.petittools.items.ItemRegistry;
import net.pygmales.petittools.screenhnadler.ScreenHandlerTypeInit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PetitTools implements ModInitializer {
	public static final String MOD_ID = "petittools";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Petit Tools Mod");

		ItemGroups.initialize();
		ItemRegistry.initialize();
		BlockRegistry.initialize();
		BlockEntityTypeInit.initialize();
		ScreenHandlerTypeInit.initialize();

		ItemStorage.SIDED.registerForBlockEntity(MinerBlockEntity::getInventoryProvider, BlockEntityTypeInit.MINER_BLOCK_ENTITY);

		LOGGER.info("Petit Tools Mod has initialized successfully");
	}

	public static Identifier id(String name) {
		return Identifier.of(MOD_ID, name);
	}
}