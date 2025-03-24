package net.pygmales.petittools.screen;

import com.mojang.text2speech.NarratorWindows;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.pygmales.petittools.PetitTools;
import net.pygmales.petittools.items.ItemRegistry;
import net.pygmales.petittools.screenhnadler.TestBookScreenHandler;

public class TestBookScreen extends HandledScreen<TestBookScreenHandler> {
    private final ButtonWidget w;
    public TestBookScreen(TestBookScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        w = ButtonWidget.builder(Text.of("Hello!"),
                button -> PetitTools.LOGGER.info("Button Pressed")).build();
    }

    @Override
    protected void init() {
        super.init();
        addDrawableChild(w);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int row = 0, column = 0;

        for (Item item : ItemRegistry.ITEMS) {
            context.drawItem(new ItemStack(item), x + 60 + row*20, 40 + 20*column);
            row++;
            if (row == 6) {
                row = 0;
                column++;
            }
        }
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
    }


    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
