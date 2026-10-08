package net.uhhitscam.knightfall.entity.client;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.util.Unit;

public class IonGrenadeModel extends Model<Unit> {
    public IonGrenadeModel(ModelPart root) {
        super(root.getChild("ionGrenade"), RenderTypes::entityCutout);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();
        PartDefinition ionGrenade = root.addOrReplaceChild(
                "ionGrenade",
                CubeListBuilder.create(),
                PartPose.rotation(0.0F, 0.0F, (float) Math.toRadians(90.0))
        );

        PartDefinition body = ionGrenade.addOrReplaceChild(
                "body",
                CubeListBuilder.create()
                        .texOffs(14, 10).addBox(-2.0F, -1.0F, -1.5F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.11F))
                        .texOffs(0, 9).addBox(-2.0F, -7.0F, -1.5F, 4.0F, 6.0F, 3.0F, new CubeDeformation(0.01F))
                        .texOffs(14, 18).addBox(-2.0F, -7.5F, -1.5F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.11F))
                        .texOffs(0, 22).addBox(-2.0F, -8.5F, -1.5F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.01F))
                        .texOffs(14, 5).addBox(-2.0F, -9.75F, -1.5F, 4.0F, 2.0F, 3.0F, new CubeDeformation(-0.29F))
                        .texOffs(0, 26).addBox(-2.0F, -10.5F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.59F))
                        .texOffs(28, 8).addBox(-0.25F, -9.75F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 5.25F, 0.0F)
        );

        body.addOrReplaceChild("cube_r1", CubeListBuilder.create()
                        .texOffs(28, 10).addBox(1.0F, -1.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)),
                PartPose.offsetAndRotation(1.5F, -1.5F, -0.25F, 0.0F, 1.5708F, 0.0F));
        body.addOrReplaceChild("cube_r2", CubeListBuilder.create()
                        .texOffs(28, 5).addBox(1.0F, -3.0F, -2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.01F)),
                PartPose.offsetAndRotation(1.5F, -2.75F, -0.5F, 0.0F, 1.5708F, 0.0F));
        body.addOrReplaceChild("cube_r3", CubeListBuilder.create()
                        .texOffs(28, 0).addBox(1.0F, -3.0F, -2.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.0F, -2.75F, -0.25F, 0.0F, 1.5708F, 0.0F));
        body.addOrReplaceChild("cube_r4", CubeListBuilder.create()
                        .texOffs(20, 26).addBox(0.0F, -1.0F, -2.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.6F)),
                PartPose.offsetAndRotation(0.5F, -9.5F, 2.0F, 0.0F, 1.5708F, 0.0F));
        body.addOrReplaceChild("cube_r5", CubeListBuilder.create()
                        .texOffs(10, 26).addBox(0.0F, -1.0F, -2.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.6F))
                        .texOffs(14, 0).addBox(-2.0F, -0.25F, -2.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(-0.3F))
                        .texOffs(14, 22).addBox(-2.0F, 1.0F, -2.0F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 18).addBox(-2.0F, 2.0F, -2.0F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.1F))
                        .texOffs(0, 0).addBox(-2.0F, 2.5F, -2.0F, 4.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(14, 14).addBox(-2.0F, 8.5F, -2.0F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.1F)),
                PartPose.offsetAndRotation(0.5F, -9.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

        return LayerDefinition.create(meshDefinition, 64, 64);
    }
}
