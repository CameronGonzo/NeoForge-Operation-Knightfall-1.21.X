package net.uhhitscam.starwars.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.uhhitscam.starwars.entity.custom.TibannaBlasterBoltEntity;

public class TibannaBlasterBoltModel extends HierarchicalModel<TibannaBlasterBoltEntity> {
    private final ModelPart inner;
    private final ModelPart outer;

    public TibannaBlasterBoltModel(ModelPart root) {
        this.inner = root.getChild("inner");
        this.outer = root.getChild("outer");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition inner = partdefinition.addOrReplaceChild("inner", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-2.0F, -2.0F, -7.0F, 2.0F, 2.0F, 16.0F, new CubeDeformation(-0.3F)), PartPose.offset(-7.0F, -10.0F, 7.0F));

        PartDefinition outer = partdefinition.addOrReplaceChild("outer", CubeListBuilder.create()
                .texOffs(0, 18).addBox(-2.0F, -2.0F, -7.0F, 2.0F, 2.0F, 16.0F, new CubeDeformation(-0.2F))
                .texOffs(0, 36).addBox(-2.0F, -2.0F, -7.0F, 2.0F, 2.0F, 16.0F, new CubeDeformation(-0.1F)), PartPose.offset(-7.0F, -10.0F, 7.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(TibannaBlasterBoltEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    public void renderCore(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay) {
        inner.render(poseStack, vertexConsumer, packedLight, packedOverlay);
    }

    public void renderGlow(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay) {
        outer.render(poseStack, vertexConsumer, packedLight, packedOverlay);
    }

    @Override
    public ModelPart root() {
        return inner;
    }
}
