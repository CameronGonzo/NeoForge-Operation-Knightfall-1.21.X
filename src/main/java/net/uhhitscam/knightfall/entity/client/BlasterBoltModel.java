package net.uhhitscam.knightfall.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.uhhitscam.knightfall.entity.custom.BlasterBoltEntity;

public class BlasterBoltModel extends HierarchicalModel<BlasterBoltEntity> {
    private final ModelPart bolt;
    private final ModelPart boltExterior;
    private final ModelPart boltCore;

    public BlasterBoltModel(ModelPart root) {
        this.bolt = root.getChild("bolt");
        this.boltExterior = this.bolt.getChild("bolt_exterior");
        this.boltCore = this.bolt.getChild("bolt_core");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();

        PartDefinition bolt = root.addOrReplaceChild(
                "bolt",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 24.0F, 0.0F)
        );

        bolt.addOrReplaceChild(
                "bolt_exterior",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-2.0F, -2.0F, -8.0F, 3.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -5.0F, 0.0F)
        );

        bolt.addOrReplaceChild(
                "bolt_core",
                CubeListBuilder.create()
                        .texOffs(0, 19)
                        .addBox(-1.0F, -1.0F, -7.0F, 1.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -5.0F, 0.0F)
        );

        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    public void setupAnim(
            BlasterBoltEntity entity,
            float limbSwing,
            float limbSwingAmount,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
    }

    public void renderCore(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay) {
        boltCore.render(poseStack, vertexConsumer, packedLight, packedOverlay);
    }

    public void renderGlow(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay) {
        boltExterior.render(poseStack, vertexConsumer, packedLight, packedOverlay);
    }

    @Override
    public ModelPart root() {
        return bolt;
    }
}
