package net.pygmales.petittools.screenhnadler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.pygmales.petittools.network.TestBookPayload;

public class TestBookScreenHandler extends ScreenHandler {
    public TestBookScreenHandler(int syncId, PlayerInventory inventory, TestBookPayload payload) {
        this(syncId,  inventory);
    }

    public TestBookScreenHandler(int syncId, PlayerInventory inventory) {
        super(ScreenHandlerTypeInit.TEST_BOOK, syncId);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
