package net.uhhitscam.starwars.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.uhhitscam.starwars.entity.custom.IonizedTibannaBlasterBoltEntity;
import net.uhhitscam.starwars.entity.custom.MagnetizedSigBlasterBoltEntity;

public class MagnetizedSigBlasterBoltModel extends HierarchicalModel<MagnetizedSigBlasterBoltEntity> {
    private final ModelPart bolt;
    private final ModelPart bolt_exterior;
    private final ModelPart bolt_core;

    public MagnetizedSigBlasterBoltModel(ModelPart root) {
        this.bolt = root.getChild("bolt");
        this.bolt_exterior = this.bolt.getChild("bolt_exterior");
        this.bolt_core = this.bolt.getChild("bolt_core");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bolt = partdefinition.addOrReplaceChild("bolt", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition bolt_exterior = bolt.addOrReplaceChild("bolt_exterior", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -8.0F, 3.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 0.0F));

        PartDefinition bolt_core = bolt.addOrReplaceChild("bolt_core", CubeListBuilder.create().texOffs(0, 19).addBox(-1.0F, -1.0F, -7.0F, 1.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(MagnetizedSigBlasterBoltEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    public void renderCore(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay) {
        bolt_core.render(poseStack, vertexConsumer, packedLight, packedOverlay);
    }

    public void renderGlow(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay) {
        bolt_exterior.render(poseStack, vertexConsumer, packedLight, packedOverlay);
    }

    @Override
    public ModelPart root() {
        return bolt;
    }
}