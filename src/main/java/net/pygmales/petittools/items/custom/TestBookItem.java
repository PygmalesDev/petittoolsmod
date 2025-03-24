package net.pygmales.petittools.items.custom;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.pygmales.petittools.PetitTools;
import net.pygmales.petittools.network.TestBookPayload;
import net.pygmales.petittools.screenhnadler.TestBookScreenHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class TestBookItem extends Item implements ExtendedScreenHandlerFactory<TestBookPayload> {
    private static final Text BOOK_DISPLAY_TEXT = Text.translatable("display." + PetitTools.MOD_ID + ".test_book");
    public TestBookItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) user.openHandledScreen(this);

        world.playSound(null, user.getBlockPos(), SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.PLAYERS);
        return ActionResult.SUCCESS;
    }

    @Override
    public TestBookPayload getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
            return Objects.nonNull(serverPlayerEntity.getPlayerListName())
                ? new TestBookPayload(serverPlayerEntity.getPlayerListName().getString())
                : new TestBookPayload("entity_null");
    }

    @Override
    public Text getDisplayName() {
        return BOOK_DISPLAY_TEXT;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new TestBookScreenHandler(syncId, playerInventory);
    }
}
