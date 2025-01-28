package net.uhhitscam.starwars.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.uhhitscam.starwars.entity.custom.StunBlasterBoltEntity;

public class StunBlasterBoltModel extends HierarchicalModel<StunBlasterBoltEntity> {
    private final ModelPart stun_blaster_bolt;

    public StunBlasterBoltModel(ModelPart root) {
        this.stun_blaster_bolt = root.getChild("stun_blaster_bolt");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition stun_blaster_bolt = partdefinition.addOrReplaceChild("stun_blaster_bolt", CubeListBuilder.create().texOffs(0, 6).addBox(-2.5F, -1.0F, 0.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 15).addBox(-4.5F, -2.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 15).addBox(-4.5F, -3.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(18, 4).addBox(-3.5F, -1.5F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 19).addBox(0.5F, -1.5F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 6).addBox(-3.5F, -12.5F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 19).addBox(0.5F, -12.5F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(28, 23).addBox(5.0F, -6.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(26, 28).addBox(5.0F, -10.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(30, 27).addBox(-6.0F, -10.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 29).addBox(-6.0F, -6.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(12, 34).addBox(-4.5F, -3.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(34, 6).addBox(-4.5F, -11.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(28, 6).addBox(-1.0F, -13.3F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(30, 17).addBox(-1.0F, -0.75F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(34, 27).addBox(-5.0F, -11.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 35).addBox(-5.0F, -10.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 36).addBox(-5.0F, -4.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 36).addBox(4.0F, -4.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 12).addBox(5.8F, -7.5F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 32).addBox(-6.8F, -7.5F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 36).addBox(4.0F, -10.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(12, 36).addBox(2.5F, -11.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(36, 12).addBox(-3.5F, -11.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(36, 16).addBox(-3.5F, -2.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(36, 14).addBox(2.5F, -2.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(34, 29).addBox(4.0F, -11.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 35).addBox(4.0F, -2.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 35).addBox(-5.0F, -2.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 28).addBox(-5.5F, -5.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 28).addBox(-5.5F, -11.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 18).addBox(-6.5F, -9.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 34).addBox(3.5F, -11.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 28).addBox(4.5F, -5.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(18, 28).addBox(4.5F, -11.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 18).addBox(5.5F, -9.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 25).addBox(3.5F, -3.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 17).addBox(1.5F, -2.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(18, 2).addBox(-4.5F, -12.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).addBox(-2.5F, -13.0F, 0.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(18, 0).addBox(1.5F, -12.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(-2.0F, -1.6F, 0.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 31).addBox(-3.5F, -2.5F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 4).addBox(-2.5F, -2.0F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 8).addBox(0.5F, -2.0F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(30, 31).addBox(1.5F, -2.5F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 2).addBox(-3.5F, -11.5F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 10).addBox(-2.0F, -12.4F, 0.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 0).addBox(1.5F, -11.5F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 32).addBox(0.5F, -12.0F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 10).addBox(-2.5F, -12.0F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(36, 18).addBox(3.0F, -3.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(36, 20).addBox(-4.0F, -3.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(36, 22).addBox(-4.0F, -10.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(28, 36).addBox(-3.0F, -11.25F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(36, 31).addBox(2.0F, -11.25F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 37).addBox(-3.0F, -2.75F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 37).addBox(2.0F, -2.75F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(36, 24).addBox(3.0F, -10.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 36).addBox(3.75F, -9.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(36, 33).addBox(3.75F, -4.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(36, 35).addBox(-4.75F, -4.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 37).addBox(-4.75F, -9.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 24).addBox(4.9F, -8.5F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 19).addBox(4.0F, -5.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(28, 33).addBox(4.5F, -6.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 33).addBox(4.5F, -9.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 32).addBox(4.0F, -10.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 32).addBox(-5.0F, -10.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 33).addBox(-5.5F, -9.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 33).addBox(-5.5F, -6.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 12).addBox(-5.9F, -8.5F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 22).addBox(-5.0F, -5.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-2.0F, -1.25F, -0.5F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(6, 12).addBox(-4.0F, -2.25F, -0.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(24, 17).addBox(-4.625F, -2.875F, -0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(16, 25).addBox(-4.625F, -11.125F, -0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(4, 25).addBox(3.625F, -2.875F, -0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(10, 25).addBox(3.625F, -11.125F, -0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(12, 6).addBox(2.0F, -2.25F, -0.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 12).addBox(5.25F, -8.5F, -0.5F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(20, 8).addBox(4.25F, -10.5F, -0.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(26, 11).addBox(4.75F, -9.0F, -0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(26, 14).addBox(4.75F, -5.0F, -0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(4, 28).addBox(-5.75F, -5.0F, -0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(26, 20).addBox(-5.75F, -9.0F, -0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 21).addBox(4.25F, -4.5F, -0.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(20, 21).addBox(-5.25F, -4.5F, -0.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(12, 0).addBox(-6.25F, -8.5F, -0.5F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(14, 21).addBox(-5.25F, -10.5F, -0.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(6, 15).addBox(2.0F, -11.75F, -0.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 3).addBox(-2.0F, -12.75F, -0.5F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(14, 12).addBox(-4.0F, -11.75F, -0.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(22, 25).addBox(-2.5F, -12.25F, -0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(26, 0).addBox(1.5F, -12.25F, -0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(26, 8).addBox(1.5F, -1.75F, -0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(26, 3).addBox(-2.5F, -1.75F, -0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(StunBlasterBoltEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        stun_blaster_bolt.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return stun_blaster_bolt;
    }
}
