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

public class ImpactBombModel extends Model<Unit> {
    private final ModelPart impactBomb;

    public ImpactBombModel(ModelPart root) {
        super(root.getChild("impactBomb"), RenderTypes::entityCutout);
        this.impactBomb = root.getChild("impactBomb");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();

        PartDefinition impactBomb = root.addOrReplaceChild(
                "impactBomb",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2.5F, -4.25F, -2.5F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 8).addBox(-2.0F, -5.25F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(16, 8).addBox(-1.0F, -6.75F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(16, 12).addBox(-0.5F, -7.25F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(16, 15).addBox(-0.5F, -3.25F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(8, 17).addBox(-0.5F, -5.5F, 1.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(12, 17).addBox(-0.5F, -5.5F, -2.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(16, 17).addBox(-0.5F, -1.0F, 1.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 19).addBox(-0.5F, -1.0F, -2.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(20, 4).addBox(-0.5F, -3.25F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 3.625F, 0.0F)
        );

        impactBomb.addOrReplaceChild(
                "cube_r1",
                CubeListBuilder.create()
                        .texOffs(20, 2).addBox(0.75F, -1.75F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(20, 0).addBox(-1.75F, -1.75F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(16, 19).addBox(-1.75F, 0.75F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(12, 19).addBox(0.75F, 0.75F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(8, 19).addBox(-1.75F, -1.75F, 1.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(4, 19).addBox(0.75F, -1.75F, 1.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(4, 17).addBox(-1.75F, 0.75F, 1.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 17).addBox(0.75F, 0.75F, 1.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -2.75F, 0.0F, 0.0F, 1.5708F, 0.0F)
        );

        return LayerDefinition.create(meshDefinition, 32, 32);
    }
}
