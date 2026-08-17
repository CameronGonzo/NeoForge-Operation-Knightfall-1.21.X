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

public class PyroDentonExplosiveModel extends HierarchicalModel<GrenadeEntity> {
    private final ModelPart pyroDentonExplosive;

    public PyroDentonExplosiveModel(ModelPart root) {
        this.pyroDentonExplosive = root.getChild("pyroDentonExplosive");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();

        PartDefinition explosive = root.addOrReplaceChild(
                "pyroDentonExplosive",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4.0F, -2.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 19).addBox(2.0F, -2.5F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                        .texOffs(14, 19).addBox(-3.0F, -2.5F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 26).addBox(-2.0F, -2.5F, 2.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(10, 26).addBox(-2.0F, -2.5F, -3.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 10).addBox(-4.0F, -1.5F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.01F)),
                PartPose.ZERO
        );

        return LayerDefinition.create(meshDefinition, 32, 32);
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
        return pyroDentonExplosive;
    }
}
