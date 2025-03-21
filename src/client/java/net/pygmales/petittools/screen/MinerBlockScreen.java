package net.pygmales.petittools.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.pygmales.petittools.PetitTools;
import net.pygmales.petittools.screenhnadler.MinerBlockScreenHandler;

public class MinerBlockScreen extends HandledScreen<MinerBlockScreenHandler> {
    public static final Identifier TEXTURE = PetitTools.id("textures/container/miner_inventory.png");

    public MinerBlockScreen(MinerBlockScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int workingTicks = handler.getWorkingTicks();
        int fuelHeight = workingTicks == 5 ? 0 : workingTicks == 0 ? 17 : 17/workingTicks;

        context.drawTexture(RenderLayer::getGuiTextured, TEXTURE,
                this.x, this.y, 0.0f, 0.0f, this.backgroundWidth, this.backgroundHeight, 256, 256);
        context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, this.x+72, this.y+25 + fuelHeight,
                176, fuelHeight, 9, 17, 256, 256);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
