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

public class IncendiaryGrenadeModel extends HierarchicalModel<GrenadeEntity> {
    private final ModelPart incendiaryGrenade;

    public IncendiaryGrenadeModel(ModelPart root) {
        this.incendiaryGrenade = root.getChild("incendiaryGrenade");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();

        root.addOrReplaceChild(
                "incendiaryGrenade",
                CubeListBuilder.create()
                        .texOffs(0, 12)
                        .addBox(-1.5F, 3.5F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0)
                        .addBox(-2.0F, -4.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(12, 12)
                        .addBox(-1.5F, -4.5F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 16)
                        .addBox(-0.5F, -1.5F, -2.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(16, 0)
                        .addBox(-0.5F, -3.25F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)),
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
        return incendiaryGrenade;
    }
}
