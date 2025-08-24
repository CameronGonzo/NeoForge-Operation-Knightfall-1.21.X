package net.uhhitscam.knightfall.entity.client;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.uhhitscam.knightfall.entity.custom.SteelSlugEntity;

public class SteelSlugModel extends HierarchicalModel<SteelSlugEntity> {
    private final ModelPart steelSlug;

    public SteelSlugModel(ModelPart root) {
        this.steelSlug = root.getChild("steelSlug");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition steelSlug = partdefinition.addOrReplaceChild("steelSlug", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -6.0F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.001F))
                .texOffs(0, 4).addBox(-1.0F, -6.3F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 7).addBox(-0.7F, -6.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(6, 7).addBox(-1.3F, -6.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(6, 4).addBox(-1.0F, -5.7F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public void setupAnim(SteelSlugEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public ModelPart root() {
        return steelSlug;
    }
}
