package net.uhhitscam.starwars.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.uhhitscam.starwars.entity.custom.SkevonBlasterBoltEntity;

public class SkevonBlasterBoltModel extends HierarchicalModel<SkevonBlasterBoltEntity> {
    private final ModelPart skevonblasterbolt;

    public SkevonBlasterBoltModel(ModelPart root) {
        this.skevonblasterbolt = root.getChild("skevon_blaster_bolt");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition skevon_blaster_bolt = partdefinition.addOrReplaceChild("skevon_blaster_bolt", CubeListBuilder.create().texOffs(0, 0).addBox(-16.0F, -0.75F, 0.0F, 30.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 30).addBox(-7.5F, -0.75F, 1.25F, 20.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 2).addBox(-11.75F, -0.75F, 0.5F, 25.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).addBox(-11.75F, -0.75F, -0.5F, 25.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 4).addBox(-11.75F, -1.25F, 0.0F, 25.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 6).addBox(-11.75F, -0.25F, 0.0F, 25.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 32).addBox(-7.5F, -0.75F, -1.25F, 20.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(46, 12).addBox(-3.0F, -0.75F, -1.75F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(46, 14).addBox(-3.0F, -0.75F, 1.75F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(38, 34).addBox(-5.25F, -0.5F, -1.25F, 17.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(38, 38).addBox(-5.25F, -0.5F, 0.25F, 17.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 28).addBox(-7.5F, 0.5F, 0.0F, 20.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 26).addBox(-7.5F, -2.0F, 0.0F, 20.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(-8.5F, -1.5F, -0.25F, 21.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 14).addBox(-8.5F, -1.5F, -0.75F, 21.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 18).addBox(-8.5F, -1.0F, -0.75F, 21.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 22).addBox(-8.5F, -1.0F, -0.25F, 21.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 46).addBox(-3.0F, -2.5F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(46, 10).addBox(-3.0F, 1.0F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 38).addBox(-5.25F, -2.0F, 0.25F, 17.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 34).addBox(-5.25F, -2.0F, -1.25F, 17.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 42).addBox(-4.0F, -1.25F, -1.5F, 15.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 42).addBox(-4.0F, -2.25F, -0.5F, 15.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(42, 32).addBox(-4.0F, -2.25F, 0.5F, 15.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 44).addBox(-4.0F, 0.75F, 0.5F, 15.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 44).addBox(-4.0F, 0.75F, -0.5F, 15.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(42, 26).addBox(-4.0F, -0.25F, -1.5F, 15.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(42, 28).addBox(-4.0F, -0.25F, 1.5F, 15.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(42, 30).addBox(-4.0F, -1.25F, 1.5F, 15.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 22.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(SkevonBlasterBoltEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        skevonblasterbolt.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return skevonblasterbolt;
    }
}
