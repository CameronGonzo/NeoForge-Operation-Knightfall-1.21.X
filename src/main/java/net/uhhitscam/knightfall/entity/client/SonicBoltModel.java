package net.uhhitscam.knightfall.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.util.Unit;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.uhhitscam.knightfall.entity.custom.SonicBoltEntity;

public class SonicBoltModel extends Model<Unit> {
    private final ModelPart sonic_bolt;
    private final ModelPart sonic_bolt_exterior;
    private final ModelPart sonic_bolt_core;

    public SonicBoltModel(ModelPart root) {
        super(root.getChild("sonic_bolt"), RenderTypes::entityCutout);
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

    public void submitCore(PoseStack poseStack, net.minecraft.client.renderer.SubmitNodeCollector collector, net.minecraft.client.renderer.rendertype.RenderType renderType, int packedLight, int packedOverlay) {
        collector.submitModelPart(sonic_bolt_core, poseStack, renderType, packedLight, packedOverlay, null);
    }

    public void submitGlow(PoseStack poseStack, net.minecraft.client.renderer.SubmitNodeCollector collector, net.minecraft.client.renderer.rendertype.RenderType renderType, int packedLight, int packedOverlay) {
        collector.submitModelPart(sonic_bolt_exterior, poseStack, renderType, packedLight, packedOverlay, null);
    }
}
