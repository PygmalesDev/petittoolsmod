package net.pygmales.petittools;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.pygmales.petittools.entities.BlockEntityTypeInit;
import net.pygmales.petittools.model.BookModel;
import net.pygmales.petittools.renderer.BookBlockBER;
import net.pygmales.petittools.screen.MinerBlockScreen;
import net.pygmales.petittools.screen.TestBookScreen;
import net.pygmales.petittools.screenhnadler.ScreenHandlerTypeInit;

public class PetitToolsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		HandledScreens.register(ScreenHandlerTypeInit.MINER_BLOCK, MinerBlockScreen::new);
		HandledScreens.register(ScreenHandlerTypeInit.TEST_BOOK, TestBookScreen::new);

		EntityModelLayerRegistry.registerModelLayer(BookModel.LAYER_LOCATION, BookModel::getTexturedModelData);

		// Block entity renderers
		BlockEntityRendererFactories.register(BlockEntityTypeInit.BOOK_BLOCK_ENTITY, BookBlockBER::new);
	}
}