package net.uhhitscam.knightfall.entity.client;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.uhhitscam.knightfall.entity.custom.GrenadeEntity;

public class BaradiumBombModel extends HierarchicalModel<GrenadeEntity> {
    private final ModelPart baradiumBomb;

    public BaradiumBombModel(ModelPart root) {
        this.baradiumBomb = root.getChild("baradiumBomb");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();

        root.addOrReplaceChild(
                "baradiumBomb",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-6.0F, -1.0F, -5.0F, 12.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 11).addBox(-5.5F, -2.0F, -4.5F, 11.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 21).addBox(-5.5F, -3.0F, -4.0F, 11.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                        .texOffs(32, 35).addBox(3.5F, -3.5F, 3.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 36).addBox(1.5F, -3.5F, 3.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(6, 36).addBox(-0.5F, -3.5F, 3.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(36, 30).addBox(-2.5F, -3.5F, 3.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(12, 38).addBox(-4.5F, -3.5F, 3.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 30).addBox(-4.0F, -3.5F, 3.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F))
                        .texOffs(0, 34).addBox(-3.5F, -3.0F, 2.5F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.004F))
                        .texOffs(20, 30).addBox(0.75F, -3.0F, -3.25F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.004F))
                        .texOffs(12, 36).addBox(-4.75F, -3.0F, -3.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.004F))
                        .texOffs(16, 35).addBox(-3.75F, -3.0F, -1.75F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.2F))
                        .texOffs(24, 35).addBox(-3.75F, -3.75F, -1.75F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(18, 38).addBox(-3.5F, -2.5F, -5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F))
                        .texOffs(38, 21).addBox(2.5F, -2.5F, -5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)),
                PartPose.rotation(0.0F, (float) Math.toRadians(-90.0), 0.0F)
        );

        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    public void setupAnim(
            GrenadeEntity entity,
            float limbSwing,
            float limbSwingAmount,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
    }

    @Override
    public ModelPart root() {
        return baradiumBomb;
    }
}
