package net.pygmales.petittools.screenhnadler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.pygmales.petittools.blocks.BlockRegistry;
import net.pygmales.petittools.entities.MinerBlockEntity;
import net.pygmales.petittools.network.BlockPosPayload;

import java.util.Objects;

public class MinerBlockScreenHandler extends ScreenHandler {
    private final MinerBlockEntity entity;
    private final ScreenHandlerContext context;
    private final SimpleInventory minerInventory;

    // Client constructor
    public MinerBlockScreenHandler(int syncId, PlayerInventory inventory, BlockPosPayload payload) {
        this(syncId, inventory, (MinerBlockEntity) inventory.player.getWorld().getBlockEntity(payload.pos()));
    }

    // Server constructor
    public MinerBlockScreenHandler(int syncId, PlayerInventory inventory, MinerBlockEntity entity) {
        super(ScreenHandlerTypeInit.MINER_BLOCK, syncId);
        this.entity = entity;
        this.context = ScreenHandlerContext.create(entity.getWorld(), entity.getPos());
        this.minerInventory = entity.getInventory();

        checkSize(minerInventory, 10);
        this.minerInventory.onOpen(inventory.player);

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);
        addFuelSlot();
        addMinerInventory();
    }

    private void addFuelSlot() {
        addSlot(new Slot(minerInventory, 0, 44, 26));
    }

    private void addMinerInventory() {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                addSlot(new Slot(minerInventory, 1 + (column + row * 3), 97 + (column*18), 8 + (row*18)));
            }
        }
    }

    private void addPlayerInventory(PlayerInventory inventory) {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(inventory, 9 + (column + row * 9), 8 + (column*18), 84 + (row*18)));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory inventory) {
        for (int column = 0; column < 9;  column++) {
            addSlot(new Slot(inventory, column, 8 + (column*18),  142));
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slotIndex) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = getSlot(slotIndex);

        if (Objects.nonNull(slot) && slot.hasStack()) {
            ItemStack inSlot = slot.getStack();
            newStack = inSlot.copy();

            if (slotIndex < 36) {
                if (!insertItem(inSlot, 36, this.slots.size(), false))
                    return ItemStack.EMPTY;
            } else if (!insertItem(inSlot, 0, 36, true)) {
                    return ItemStack.EMPTY;
            }
            if (inSlot.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    // Describes the conditions upon which the menu will be opened
    @Override
    public boolean canUse(PlayerEntity player) {
        return canUse(context, player, BlockRegistry.MINER_BLOCK);
    }

    public int getWorkingTicks() {
        return entity.getWorkingTicks();
    }

    @Override
    public void onClosed(PlayerEntity player) {
        this.minerInventory.onClose(player);
        super.onClosed(player);
    }
}
