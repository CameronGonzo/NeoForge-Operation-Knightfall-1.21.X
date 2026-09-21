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

public class CryobanGrenadeModel extends Model<Unit> {
    private final ModelPart cryobanGrenade;

    public CryobanGrenadeModel(ModelPart root) {
        super(root.getChild("cryobanGrenade"), RenderTypes::entityCutout);
        this.cryobanGrenade = root.getChild("cryobanGrenade");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();

        PartDefinition cryobanGrenade = root.addOrReplaceChild(
                "cryobanGrenade",
                CubeListBuilder.create(),
                PartPose.rotation(0.0F, 0.0F, (float) Math.toRadians(90.0))
        );

        cryobanGrenade.addOrReplaceChild(
                "body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1.5F, -12.0F, -1.5F, 3.0F, 12.0F, 3.0F,
                                new CubeDeformation(0.0F))
                        .texOffs(12, 0).addBox(-1.0F, -21.0F, -1.0F, 2.0F, 9.0F, 2.0F,
                                new CubeDeformation(0.0F))
                        .texOffs(12, 11).addBox(0.25F, -17.0F, -0.5F, 1.0F, 3.0F, 1.0F,
                                new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 10.5F, 0.0F)
        );

        return LayerDefinition.create(meshDefinition, 32, 32);
    }
}
