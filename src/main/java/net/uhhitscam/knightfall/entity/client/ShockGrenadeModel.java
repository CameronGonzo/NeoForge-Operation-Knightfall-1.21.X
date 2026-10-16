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

public class ShockGrenadeModel extends Model<Unit> {
    public ShockGrenadeModel(ModelPart root) {
        super(root.getChild("shockGrenade"), RenderTypes::entityCutout);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();
        PartDefinition shockGrenade = root.addOrReplaceChild("shockGrenade",
                CubeListBuilder.create(), PartPose.rotation(0.0F, 0.0F, (float) Math.toRadians(90.0)));

        PartDefinition body = shockGrenade.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -4.85F, -1.5F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.11F))
        .texOffs(14, 11).addBox(-2.0F, -6.35F, -1.5F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.01F))
        .texOffs(14, 0).addBox(-2.0F, -8.85F, -1.5F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.11F))
        .texOffs(8, 28).addBox(1.35F, -4.1F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
        .texOffs(14, 26).addBox(-1.5F, -10.1F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F))
        .texOffs(14, 16).addBox(-2.0F, -9.35F, -1.5F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.01F))
        .texOffs(0, 28).addBox(-1.0F, -10.6F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
        .texOffs(14, 21).addBox(-2.0F, -3.35F, -1.5F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.01F))
        .texOffs(26, 26).addBox(-1.5F, -1.6F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F))
        .texOffs(28, 0).addBox(-1.0F, -1.1F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.35F, 0.0F));

        body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(28, 8).addBox(-2.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.5F, -7.1F, 0.55F, 0.0F, 1.5708F, 0.0F));

        body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(28, 3).addBox(-2.0F, -1.0F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.5F, -7.6F, -3.95F, 0.0F, 1.5708F, 0.0F));

        body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 23).addBox(-2.0F, 2.0F, -2.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
        .texOffs(0, 18).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
        .texOffs(0, 12).addBox(-2.0F, -3.5F, -2.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.1F))
        .texOffs(14, 6).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
        .texOffs(0, 6).addBox(-2.0F, 0.5F, -2.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.5F, -5.35F, 0.0F, 0.0F, 1.5708F, 0.0F));

        return LayerDefinition.create(meshDefinition, 64, 64);
    }

}
