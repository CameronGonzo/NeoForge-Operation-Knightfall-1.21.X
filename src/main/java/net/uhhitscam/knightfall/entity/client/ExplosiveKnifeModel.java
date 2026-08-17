package net.uhhitscam.knightfall.entity.client;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.uhhitscam.knightfall.entity.custom.ExplosiveKnifeEntity;

public class ExplosiveKnifeModel extends HierarchicalModel<ExplosiveKnifeEntity> {
	private final ModelPart bb_main;

	public ExplosiveKnifeModel(ModelPart root) {
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(34, 14).addBox(-0.5F, 0.0F, -0.05F, 2.0F, 8.0F, 0.1F, new CubeDeformation(0.0F))
		.texOffs(8, 35).addBox(-1.0F, 8.0F, -0.05F, 2.0F, 2.0F, 0.1F, new CubeDeformation(0.0F))
		.texOffs(12, 35).addBox(-0.5F, 10.0F, -0.05F, 1.0F, 3.0F, 0.1F, new CubeDeformation(0.0F))
		.texOffs(14, 35).addBox(-2.0F, 9.0F, -0.05F, 1.0F, 1.0F, 0.1F, new CubeDeformation(0.0F))
		.texOffs(16, 35).addBox(-1.5F, 10.0F, -0.05F, 1.0F, 1.0F, 0.1F, new CubeDeformation(0.0F))
		.texOffs(18, 35).addBox(0.5F, 10.0F, -0.05F, 1.0F, 1.0F, 0.1F, new CubeDeformation(0.0F))
		.texOffs(36, 11).addBox(1.0F, 9.0F, -0.05F, 1.0F, 1.0F, 0.1F, new CubeDeformation(0.0F))
		.texOffs(36, 12).addBox(1.75F, 8.0F, -0.05F, 1.0F, 1.0F, 0.1F, new CubeDeformation(0.0F))
		.texOffs(36, 13).addBox(-2.75F, 8.0F, -0.05F, 1.0F, 1.0F, 0.1F, new CubeDeformation(0.0F))
		.texOffs(14, 36).addBox(-3.5F, 7.0F, -0.05F, 1.0F, 1.0F, 0.1F, new CubeDeformation(0.0F))
		.texOffs(16, 36).addBox(2.5F, 7.0F, -0.05F, 1.0F, 1.0F, 0.1F, new CubeDeformation(0.0F))
		.texOffs(32, 20).addBox(-1.5F, 6.0F, -0.05F, 1.0F, 2.0F, 0.1F, new CubeDeformation(0.0F))
		.texOffs(18, 36).addBox(-1.5F, 0.0F, -0.05F, 1.0F, 1.0F, 0.1F, new CubeDeformation(0.0F))
		.texOffs(20, 36).addBox(-1.5F, 1.5F, -0.05F, 1.0F, 1.0F, 0.1F, new CubeDeformation(0.0F))
		.texOffs(22, 36).addBox(-1.5F, 3.0F, -0.05F, 1.0F, 1.0F, 0.1F, new CubeDeformation(0.0F))
		.texOffs(24, 36).addBox(-1.5F, 4.5F, -0.05F, 1.0F, 1.0F, 0.1F, new CubeDeformation(0.0F))
		.texOffs(22, 8).addBox(-3.0F, -1.0F, -1.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(22, 4).addBox(-3.5F, -16.0F, -1.5F, 7.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 20).addBox(-3.5F, -3.0F, -2.0F, 7.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(22, 20).addBox(-4.5F, -5.0F, -2.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(20, 26).addBox(-3.5F, -4.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(30, 26).addBox(2.5F, -4.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(24, 14).addBox(3.5F, -5.0F, -2.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-4.0F, -13.75F, -1.5F, 8.0F, 11.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 14).addBox(-4.0F, -15.5F, -2.0F, 8.0F, 2.0F, 4.0F, new CubeDeformation(0.01F))
		.texOffs(0, 26).addBox(3.5F, -13.5F, -2.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(20, 31).addBox(2.5F, -13.5F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(30, 31).addBox(-3.5F, -13.5F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(10, 26).addBox(-4.5F, -13.5F, -2.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(22, 0).addBox(-4.0F, -8.75F, -1.5F, 8.0F, 1.0F, 3.0F, new CubeDeformation(0.01F))
		.texOffs(0, 32).addBox(-2.0F, -9.25F, -1.75F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.01F))
		.texOffs(10, 32).addBox(-2.0F, -9.25F, 0.75F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.01F))
		.texOffs(32, 22).addBox(-4.5F, -3.0F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.01F))
		.texOffs(0, 35).addBox(3.5F, -3.0F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.01F))
		.texOffs(22, 11).addBox(-2.5F, -16.25F, -1.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(ExplosiveKnifeEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public ModelPart root() {
		return bb_main;
	}
}
