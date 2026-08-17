package net.uhhitscam.knightfall.entity.client;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.uhhitscam.knightfall.entity.custom.GrenadeEntity;

public class DetoniteChargeModel extends HierarchicalModel<GrenadeEntity> {
    private final ModelPart detoniteCharge;

    public DetoniteChargeModel(ModelPart root) {
        this.detoniteCharge = root.getChild("detoniteCharge");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();

        root.addOrReplaceChild(
                "detoniteCharge",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-2.0F, -1.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 5)
                        .addBox(-1.5F, -4.25F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 12)
                        .addBox(-0.5F, -5.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(4, 12)
                        .addBox(0.75F, -3.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(12, 5)
                        .addBox(-0.5F, -1.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.005F))
                        .texOffs(12, 7)
                        .addBox(-0.5F, -5.35F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.8F))
                        .texOffs(8, 12)
                        .addBox(-1.75F, -3.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(12, 9)
                        .addBox(-0.5F, -1.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.005F)),
                PartPose.ZERO
        );

        return LayerDefinition.create(meshDefinition, 16, 16);
    }

    @Override
    public void setupAnim(
            GrenadeEntity entity,
            float limbSwing,
            float limbSwingAmount,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
    }

    @Override
    public ModelPart root() {
        return detoniteCharge;
    }
}
