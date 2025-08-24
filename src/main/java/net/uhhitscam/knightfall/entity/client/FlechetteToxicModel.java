package net.uhhitscam.knightfall.entity.client;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.uhhitscam.knightfall.entity.custom.FlechetteToxicEntity;

public class FlechetteToxicModel extends HierarchicalModel<FlechetteToxicEntity> {
    private final ModelPart flechetteToxic;

    public FlechetteToxicModel(ModelPart root) {
        this.flechetteToxic = root.getChild("flechetteToxic");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition flechette = partdefinition.addOrReplaceChild("flechetteToxic", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -6.0F, -4.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.001F))
                .texOffs(0, 7).addBox(-0.5F, -6.5F, 1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.001F))
                .texOffs(8, 7).addBox(-1.0F, -6.2F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 9).addBox(-1.0F, -5.8F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 11).addBox(-0.8F, -6.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 11).addBox(-1.2F, -6.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition flechetteToxic_r1 = flechette.addOrReplaceChild("flechetteToxic_r1", CubeListBuilder.create().texOffs(4, 7).addBox(0.0F, -1.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-0.5F, -5.5F, 2.0F, 0.0F, 0.0F, -1.5708F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public void setupAnim(FlechetteToxicEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public ModelPart root() {
        return flechetteToxic;
    }
}