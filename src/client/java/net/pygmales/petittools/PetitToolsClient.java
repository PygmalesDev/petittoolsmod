package net.pygmales.petittools;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.pygmales.petittools.screen.MinerBlockScreen;
import net.pygmales.petittools.screenhnadler.ScreenHandlerTypeInit;

public class PetitToolsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {

		HandledScreens.register(ScreenHandlerTypeInit.MINER_BLOCK, MinerBlockScreen::new);
	}
}