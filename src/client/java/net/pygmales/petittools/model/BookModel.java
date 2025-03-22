package net.pygmales.petittools.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.pygmales.petittools.PetitTools;

public class BookModel extends Model {
	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(PetitTools.id("main"), "book");
	public static final Identifier TEXTURE_LOCATION = PetitTools.id("textures/entity/book_texture.png");

	private final ModelPart main;
	private final ModelPart upperPart;
	private final ModelPart lowerPart;

	public BookModel(ModelPart root) {
		super(root, RenderLayer::getEntitySolid);

		this.main = root.getChild("main");
		this.upperPart = main.getChild("upper_part");
		this.lowerPart = main.getChild("lower_part");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData main =
				modelPartData.addChild("main",
						ModelPartBuilder.create(),
						ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		main.addChild("upper_part",
				ModelPartBuilder.create().uv(0, 0)
						.cuboid(0.0F, 0.0F, -4.0F, 5.0F, 1.0F, 8.0F, new Dilation(0.0F)),
				ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		main.addChild("lower_part", ModelPartBuilder.create().uv(0, 9)
						.cuboid(-5.0F, 0.0F, -4.0F, 5.0F, 1.0F, 8.0F, new Dilation(0.0F)),
				ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		return TexturedModelData.of(modelData, 32, 32);
	}

	public ModelPart getLowerPart() {
		return lowerPart;
	}

	public ModelPart getUpperPart() {
		return upperPart;
	}

	public ModelPart getMain() {
		return main;
	}
}