package net.pygmales.petittools.renderer;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.pygmales.petittools.blocks.custom.BookBlock;
import net.pygmales.petittools.entities.BookBlockEntity;
import net.pygmales.petittools.model.BookModel;

public class BookBlockBER implements BlockEntityRenderer<BookBlockEntity> {
    private static final double MAX_ANGLE = Math.toRadians(-180);
    private static final double DEFAULT_ANGLE = Math.toRadians(0);
    private static final float MAX_PADDING = -2.5f;
    private static final float DEFAULT_PADDING = 0.0f;
    private final BlockEntityRendererFactory.Context context;
    private final BookModel model;

    public BookBlockBER(BlockEntityRendererFactory.Context context) {
        this.context = context;
        this.model = new BookModel(context.getLayerModelPart(BookModel.LAYER_LOCATION));
    }

    @Override
    public void render(BookBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        matrices.translate(0.5f, 0.0f, 0.5f);

        float upperPartAngle = entity.getUpperPartAngle();
        float bookPadding = entity.getBookPadding();
        ModelPart upperPart = model.getUpperPart();
        ModelPart main = model.getMain();

        main.roll = (float) MAX_ANGLE;
        main.pivotY = 1.0f;

        if (entity.getBookClosedState()) {
            upperPart.roll = (float) MathHelper.lerp(tickDelta / 16, upperPartAngle, MAX_ANGLE);
            main.pivotX = MathHelper.lerp(tickDelta/8, bookPadding, MAX_PADDING);
        } else {
            upperPart.roll = (float) MathHelper.lerp(tickDelta / 16, upperPartAngle, DEFAULT_ANGLE);
            main.pivotX = MathHelper.lerp(tickDelta/8, bookPadding, DEFAULT_PADDING);
        }
        entity.setUpperPartAngle(upperPart.roll);
        entity.setBookPadding(main.pivotX);

        switch (entity.getCachedState().get(BookBlock.FACING)) {
            case EAST -> matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
            case SOUTH -> matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
            case WEST -> matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(270));
        }

        model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntitySolid(BookModel.TEXTURE_LOCATION)), light, overlay);

        upperPart.roll = (float) DEFAULT_ANGLE;
        main.pivotX = DEFAULT_PADDING;

        matrices.pop();
    }

    public BlockEntityRendererFactory.Context getContext() {
        return context;
    }
}
