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

public class ImpactThermalDetonatorModel extends Model<Unit> {
    private final ModelPart impactThermalDetonator;

    public ImpactThermalDetonatorModel(ModelPart root) {
        super(root.getChild("impactThermalDetonator"), RenderTypes::entityCutout);
        this.impactThermalDetonator = root.getChild("impactThermalDetonator");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();

        root.addOrReplaceChild(
                "impactThermalDetonator",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 10)
                        .addBox(-0.5F, -3.0F, -2.25F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.ZERO
        );

        return LayerDefinition.create(meshDefinition, 32, 32);
    }
}
