package net.uhhitscam.knightfall.entity.client;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.uhhitscam.knightfall.entity.custom.FlechetteToxicSpreadCanEntity;

public class FlechetteToxicSpreadCanModel extends HierarchicalModel<FlechetteToxicSpreadCanEntity> {
    private final ModelPart flechetteToxicSpreadCan;

    public FlechetteToxicSpreadCanModel(ModelPart root) {
        this.flechetteToxicSpreadCan = root.getChild("flechetteToxicSpreadCan");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition flechette = partdefinition.addOrReplaceChild("flechetteToxicSpreadCan", CubeListBuilder.create().texOffs(0, 15).addBox(-1.5F, -7.0F, -3.0F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).addBox(-2.0F, -6.5F, -3.0F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.5F, -6.5F, -3.5F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(FlechetteToxicSpreadCanEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public ModelPart root() {
        return flechetteToxicSpreadCan;
    }
}