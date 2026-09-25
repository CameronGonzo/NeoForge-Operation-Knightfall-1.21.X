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

public class FlashGrenadeModel extends Model<Unit> {
    public FlashGrenadeModel(ModelPart root) {
        super(root.getChild("flashGrenade"), RenderTypes::entityCutout);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();
        PartDefinition flashGrenade = root.addOrReplaceChild(
                "flashGrenade",
                CubeListBuilder.create(),
                PartPose.rotation(0.0F, 0.0F, (float) Math.toRadians(90.0))
        );

        flashGrenade.addOrReplaceChild(
                "body",
                CubeListBuilder.create()
                        .texOffs(0, 12).addBox(-1.5F, -12.6F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(12, 0).addBox(-1.5F, -2.15F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(12, 10).addBox(-0.5F, -11.25F, -2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(12, 15).addBox(1.0F, -11.25F, -2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 16).addBox(1.0F, -11.25F, 1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(4, 16).addBox(-2.0F, -11.25F, -2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(8, 16).addBox(-2.0F, -11.25F, 1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(16, 10).addBox(-0.5F, -11.25F, 1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(16, 15).addBox(-2.0F, -11.25F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(20, 4).addBox(1.0F, -11.25F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(-1.5F, -11.4F, -1.5F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.25F))
                        .texOffs(20, 9).addBox(-0.5F, -6.5F, 1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(12, 20).addBox(-2.0F, -6.5F, 1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(20, 14).addBox(-2.0F, -6.5F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(16, 20).addBox(-2.0F, -6.5F, -2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(20, 19).addBox(-0.5F, -6.5F, -2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 21).addBox(1.0F, -6.5F, 1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(4, 21).addBox(1.0F, -6.5F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(8, 21).addBox(1.0F, -6.5F, -2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(12, 4).addBox(-1.0F, -1.25F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.25F))
                        .texOffs(12, 7).addBox(-1.0F, -13.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.25F)),
                PartPose.offset(0.0F, 6.875F, 0.0F)
        );

        return LayerDefinition.create(meshDefinition, 32, 32);
    }
}
