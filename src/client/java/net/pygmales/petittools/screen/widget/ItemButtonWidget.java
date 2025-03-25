package net.pygmales.petittools.screen.widget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;

import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.pygmales.petittools.PetitTools;

import java.util.Objects;

public class ItemButtonWidget extends ButtonWidget {
    private static final Identifier FRAME_WIDGET_TEXTURE = PetitTools.id("textures/gui/widget/item_widget_frame.png");
    private static final float ITEM_SIZE = 16.0f;
    private static final float MAX_ITEM_SCALE = 8.0f;

    private final float centerX;
    private final float centerY;
    private final ItemStack item;
    protected final MinecraftClient client = MinecraftClient.getInstance();
    private final TextRenderer textRenderer = client.textRenderer;
    private final ItemRenderState itemRenderState = new ItemRenderState();

    private float itemScale = 0.0f;

    public ItemButtonWidget(Item item, int x, int y) {
        super(x, y, 24, 24, Text.empty(), new Action(), ButtonWidget.DEFAULT_NARRATION_SUPPLIER);
        this.centerX = this.getX() + this.getWidth()/2.0f;
        this.centerY = this.getY() + this.getHeight()/2.0f;
        this.item = new ItemStack(item);
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawTexture(RenderLayer::getGuiTextured, FRAME_WIDGET_TEXTURE,
                this.getX(), this.getY(), 0, 0, this.getWidth(), this.getHeight(), this.getWidth(), this.getHeight());

        if (isHovered()) {
            context.drawItemTooltip(this.textRenderer, this.item, mouseX, mouseY);
            this.itemScale = MathHelper.clampedLerp(this.itemScale, MAX_ITEM_SCALE, delta);
        } else
            this.itemScale = MathHelper.clampedLerp(this.itemScale, 0, delta);

        this.renderItem2();
    }

    private void renderItem2() {
        var matrices = new MatrixStack();
        var consumers = this.client.getBufferBuilders().getEntityVertexConsumers();

        this.client.getItemModelManager().update(this.itemRenderState, this.item, ModelTransformationMode.GUI, false, client.world, client.player, 0);
        matrices.push();
        matrices.translate(this.centerX, this.centerY, 1.0f);
        matrices.scale(ITEM_SIZE+this.itemScale, -(ITEM_SIZE+this.itemScale), ITEM_SIZE);

        boolean isItem = !this.itemRenderState.isSideLit();
        if (isItem) DiffuseLighting.disableGuiDepthLighting();

        this.itemRenderState.render(matrices, consumers, 15728880, OverlayTexture.DEFAULT_UV);
        consumers.draw();

        if (isItem) DiffuseLighting.enableGuiDepthLighting();
        matrices.pop();

    }

    public static class Action implements PressAction {
        public void onPress(ButtonWidget button) {
            if (button instanceof ItemButtonWidget bw) {
                if (Objects.nonNull(bw.client.world) && !bw.client.world.isClient) return;
                if (Objects.isNull(bw.client.player)) return;
                PlayerInventory inventory = bw.client.player.getInventory();
                //TODO: inserted item disappears after being thrown.
                inventory.insertStack(bw.item);
            }
        }
    }
}
