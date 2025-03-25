package net.pygmales.petittools.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.pygmales.petittools.items.ItemRegistry;
import net.pygmales.petittools.screen.widget.ItemButtonWidget;
import net.pygmales.petittools.screenhnadler.TestBookScreenHandler;

import java.util.ArrayList;
import java.util.List;

public class TestBookScreen extends HandledScreen<TestBookScreenHandler> {
   // private final List<ItemButtonWidget> itemButtonWidgets = new ArrayList<>();

    public TestBookScreen(TestBookScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        this.initializeItemWidgets();
    }

    private void initializeItemWidgets() {
        int row = 0, column = 0;
        for (Item item : ItemRegistry.ITEMS) {
            addDrawableChild(new ItemButtonWidget(item, x+row*26, y+26*column));
            row++;
            if (row == 6) {
                row = 0;
                column++;
            }
        }
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {

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
