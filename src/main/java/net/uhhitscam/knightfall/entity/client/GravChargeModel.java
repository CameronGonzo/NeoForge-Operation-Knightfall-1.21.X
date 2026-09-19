package net.uhhitscam.knightfall.entity.client;

import net.minecraft.client.model.Model;
import net.minecraft.util.Unit;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.uhhitscam.knightfall.entity.custom.GrenadeEntity;

public class GravChargeModel extends Model<Unit> {
    private final ModelPart gravCharge;

    public GravChargeModel(ModelPart root) {
        super(root.getChild("gravCharge"), RenderTypes::entityCutout);
        this.gravCharge = root.getChild("gravCharge");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();

        root.addOrReplaceChild(
                "gravCharge",
                CubeListBuilder.create()
                        .texOffs(0, 4)
                        .addBox(-1.0F, -1.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0)
                        .addBox(-1.5F, -0.5F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 7)
                        .addBox(-0.5F, -1.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)),
                PartPose.ZERO
        );

        return LayerDefinition.create(meshDefinition, 16, 16);
    }
}
