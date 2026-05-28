package net.uhhitscam.knightfall.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.uhhitscam.knightfall.entity.custom.SonicBoltEntity;

public class SonicBoltModel extends HierarchicalModel<SonicBoltEntity> {
    private final ModelPart sonic_bolt;
    private final ModelPart sonic_bolt_exterior;
    private final ModelPart sonic_bolt_core;

    public SonicBoltModel(ModelPart root) {
        this.sonic_bolt = root.getChild("sonic_bolt");
        this.sonic_bolt_exterior = this.sonic_bolt.getChild("sonic_bolt_exterior");
        this.sonic_bolt_core = this.sonic_bolt.getChild("sonic_bolt_core");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition sonic_bolt = partdefinition.addOrReplaceChild("sonic_bolt", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition sonic_bolt_exterior = sonic_bolt.addOrReplaceChild("sonic_bolt_exterior", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -5.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition sonic_bolt_core = sonic_bolt.addOrReplaceChild("sonic_bolt_core", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, -4.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(SonicBoltEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    public void renderCore(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay) {
        sonic_bolt_core.render(poseStack, vertexConsumer, packedLight, packedOverlay);
    }

    public void renderGlow(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay) {
        sonic_bolt_exterior.render(poseStack, vertexConsumer, packedLight, packedOverlay);
    }

    @Override
    public ModelPart root() {
        return sonic_bolt;
    }
}
