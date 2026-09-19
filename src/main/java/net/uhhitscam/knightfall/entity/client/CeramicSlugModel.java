package net.uhhitscam.knightfall.entity.client;

import net.minecraft.client.model.Model;
import net.minecraft.util.Unit;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.uhhitscam.knightfall.entity.custom.CeramicSlugEntity;

public class CeramicSlugModel extends Model<Unit> {
    private final ModelPart ceramicSlug;

    public CeramicSlugModel(ModelPart root) {
        super(root.getChild("ceramicSlug"), RenderTypes::entityCutout);
        this.ceramicSlug = root.getChild("ceramicSlug");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition ceramicSlug = partdefinition.addOrReplaceChild("ceramicSlug", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -6.0F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.001F))
                .texOffs(0, 4).addBox(-1.0F, -6.3F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 7).addBox(-0.7F, -6.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(6, 7).addBox(-1.3F, -6.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(6, 4).addBox(-1.0F, -5.7F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }




}