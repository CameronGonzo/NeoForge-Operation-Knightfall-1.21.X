package net.uhhitscam.knightfall.entity.client;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.uhhitscam.knightfall.entity.custom.PoisonTippedSteelSlugEntity;
import net.uhhitscam.knightfall.entity.custom.RazorSteelSlugEntity;

public class PoisonTippedSteelSlugModel extends HierarchicalModel<PoisonTippedSteelSlugEntity> {
    private final ModelPart poisonTippedSteelSlug;

    public PoisonTippedSteelSlugModel(ModelPart root) {
        this.poisonTippedSteelSlug = root.getChild("poisonTippedSteelSlug");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition poisonTippedSteelSlug = partdefinition.addOrReplaceChild("poisonTippedSteelSlug", CubeListBuilder.create().texOffs(8, 4).addBox(-1.0F, -6.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.001F))
                .texOffs(12, 13).addBox(-1.0F, -6.0F, -3.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.001F))
                .texOffs(0, 0).addBox(-1.0F, -6.3F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0001F))
                .texOffs(8, 7).addBox(-1.0F, -6.4F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).addBox(-0.7F, -6.0F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0001F))
                .texOffs(0, 12).addBox(-0.6F, -6.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 0).addBox(-1.3F, -6.0F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0001F))
                .texOffs(8, 10).addBox(-1.4F, -6.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 4).addBox(-1.0F, -5.7F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0001F))
                .texOffs(6, 13).addBox(-1.0F, -5.6F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(PoisonTippedSteelSlugEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public ModelPart root() {
        return poisonTippedSteelSlug;
    }
}