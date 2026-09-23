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

public class BactaBombModel extends Model<Unit> {
    public BactaBombModel(ModelPart root) {
        this(root, "bactaBomb");
    }

    protected BactaBombModel(ModelPart root, String modelPartName) {
        super(root.getChild(modelPartName), RenderTypes::entityCutout);
    }

    public static LayerDefinition createBodyLayer() {
        return createBodyLayer("bactaBomb");
    }

    protected static LayerDefinition createBodyLayer(String modelPartName) {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();

        PartDefinition grenade = root.addOrReplaceChild(
                modelPartName,
                CubeListBuilder.create(),
                PartPose.rotation(0.0F, 0.0F, (float) Math.toRadians(-90.0))
        );

        PartDefinition body = grenade.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-3.0F, -11.0F, -2.0F, 6.0F, 10.0F, 4.0F, new CubeDeformation(0.001F))
                .texOffs(20, 24).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(20, 10).addBox(-1.5F, -13.0F, -2.5F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(20, 5).addBox(-3.0F, -13.5F, -2.0F, 6.0F, 1.0F, 4.0F, new CubeDeformation(0.001F))
                .texOffs(32, 29).addBox(-1.5F, -13.75F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(18, 35).addBox(-0.5F, -12.25F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 35).addBox(2.0F, -12.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 34).addBox(-3.5F, -10.0F, -1.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(14, 35).addBox(-3.75F, -10.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.001F))
                .texOffs(26, 35).addBox(2.0F, -9.5F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.01F))
                .texOffs(36, 10).addBox(2.0F, -9.5F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.01F))
                .texOffs(36, 12).addBox(2.0F, -7.5F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.01F))
                .texOffs(36, 14).addBox(2.0F, -7.5F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.01F))
                .texOffs(36, 16).addBox(2.0F, -5.5F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.01F))
                .texOffs(36, 18).addBox(2.0F, -5.5F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.01F))
                .texOffs(36, 20).addBox(2.0F, -3.5F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.01F))
                .texOffs(36, 22).addBox(2.0F, -3.5F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)),
                PartPose.offset(0.0F, 6.875F, 0.0F));

        body.addOrReplaceChild("cube_r1", CubeListBuilder.create()
                .texOffs(16, 32).addBox(-3.0F, -9.0F, 0.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.01F))
                .texOffs(0, 31).addBox(-3.0F, -11.0F, 0.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.01F))
                .texOffs(16, 29).addBox(-3.0F, -13.0F, 0.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.01F))
                .texOffs(0, 28).addBox(-3.0F, -15.0F, 0.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.01F))
                .texOffs(20, 0).addBox(-3.0F, -19.0F, -2.0F, 6.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(20, 17).addBox(-1.5F, -18.5F, -2.5F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.001F))
                .texOffs(0, 14).addBox(-3.0F, -16.5F, -2.0F, 6.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 5.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

        body.addOrReplaceChild("cube_r2", CubeListBuilder.create()
                        .texOffs(4, 34).addBox(2.0F, -9.0F, -2.0F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.5F, -1.5F, 5.25F, 0.0F, 1.5708F, 0.0F));
        body.addOrReplaceChild("cube_r3", CubeListBuilder.create()
                        .texOffs(0, 34).addBox(2.0F, -9.0F, -2.0F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(4.25F, -1.5F, 2.5F, 0.0F, 1.5708F, 0.0F));
        body.addOrReplaceChild("cube_r4", CubeListBuilder.create()
                        .texOffs(32, 33).addBox(2.0F, -9.0F, -2.0F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.5F, -1.5F, -0.25F, 0.0F, 1.5708F, 0.0F));

        return LayerDefinition.create(meshDefinition, 64, 64);
    }
}
