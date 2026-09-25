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

public class SmokeGrenadeModel extends Model<Unit> {
    public SmokeGrenadeModel(ModelPart root) {
        super(root.getChild("smokeGrenade"), RenderTypes::entityCutout);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();

        PartDefinition smokeGrenade = root.addOrReplaceChild(
                "smokeGrenade",
                CubeListBuilder.create()
                        .texOffs(0, 7).addBox(-3.0F, -2.0F, -1.5F, 6.0F, 2.0F, 3.0F,
                                new CubeDeformation(0.0F))
                        .texOffs(20, 2).addBox(2.15F, -1.5F, -1.0F, 1.0F, 1.0F, 2.0F,
                                new CubeDeformation(0.0F))
                        .texOffs(20, 0).addBox(0.05F, -2.05F, -0.5F, 3.0F, 1.0F, 1.0F,
                                new CubeDeformation(0.0F)),
                PartPose.ZERO
        );

        smokeGrenade.addOrReplaceChild(
                "cube_r1",
                CubeListBuilder.create().texOffs(0, 17)
                        .addBox(-2.0F, -2.0F, -2.5F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.03F)),
                PartPose.offsetAndRotation(0.5F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F)
        );
        smokeGrenade.addOrReplaceChild(
                "cube_r2",
                CubeListBuilder.create().texOffs(18, 12)
                        .addBox(-2.0F, -2.0F, -2.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)),
                PartPose.offsetAndRotation(1.5F, -0.4F, -1.0F, 0.0F, 1.5708F, 0.0F)
        );
        smokeGrenade.addOrReplaceChild(
                "cube_r3",
                CubeListBuilder.create().texOffs(18, 7)
                        .addBox(2.0F, -2.0F, -2.5F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.75F, 0.5F, 5.75F, 0.0F, 1.5708F, 0.0F)
        );
        smokeGrenade.addOrReplaceChild(
                "cube_r4",
                CubeListBuilder.create().texOffs(16, 17)
                        .addBox(2.0F, -2.0F, -2.5F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.25F, 0.5F, -0.75F, 0.0F, 1.5708F, 0.0F)
        );
        smokeGrenade.addOrReplaceChild(
                "cube_r5",
                CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-2.0F, -2.0F, -2.5F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.01F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.5F, 0.0F, 1.5708F, 0.0F)
        );
        smokeGrenade.addOrReplaceChild(
                "cube_r6",
                CubeListBuilder.create().texOffs(0, 12)
                        .addBox(-3.0F, -2.0F, -1.5F, 6.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F)
        );

        return LayerDefinition.create(meshDefinition, 32, 32);
    }
}
