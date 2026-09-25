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

public class BaradiumThermalDetonatorModel extends Model<Unit> {
    public BaradiumThermalDetonatorModel(ModelPart root) {
        super(root.getChild("baradiumThermalDetonator"), RenderTypes::entityCutout);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();

        PartDefinition baradiumThermalDetonator = root.addOrReplaceChild(
                "baradiumThermalDetonator",
                CubeListBuilder.create(),
                PartPose.rotation(0.0F, 0.0F, (float) Math.toRadians(90.0))
        );

        baradiumThermalDetonator.addOrReplaceChild(
                "body",
                CubeListBuilder.create()
                        .texOffs(12, 5).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 1.0F, 3.0F,
                                new CubeDeformation(0.0F))
                        .texOffs(0, 9).addBox(-1.5F, -2.25F, -1.5F, 3.0F, 2.0F, 3.0F,
                                new CubeDeformation(-0.1F))
                        .texOffs(12, 0).addBox(-1.5F, -8.75F, -1.5F, 3.0F, 2.0F, 3.0F,
                                new CubeDeformation(-0.1F))
                        .texOffs(12, 9).addBox(-1.5F, -9.0F, -1.5F, 3.0F, 1.0F, 3.0F,
                                new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(-1.5F, -7.5F, -1.5F, 3.0F, 6.0F, 3.0F,
                                new CubeDeformation(0.0F))
                        .texOffs(12, 13).addBox(-1.25F, -3.0F, -1.75F, 1.0F, 1.0F, 1.0F,
                                new CubeDeformation(0.0F))
                        .texOffs(0, 14).addBox(-1.25F, -4.25F, -1.75F, 1.0F, 1.0F, 1.0F,
                                new CubeDeformation(0.0F))
                        .texOffs(4, 14).addBox(-1.25F, -5.5F, -1.75F, 1.0F, 1.0F, 1.0F,
                                new CubeDeformation(0.0F))
                        .texOffs(8, 14).addBox(-1.25F, -6.75F, -1.75F, 1.0F, 1.0F, 1.0F,
                                new CubeDeformation(0.0F))
                        .texOffs(12, 15).addBox(0.25F, -4.0F, -1.75F, 1.0F, 1.0F, 1.0F,
                                new CubeDeformation(-0.1F)),
                PartPose.offset(0.0F, 4.5F, 0.0F)
        );

        return LayerDefinition.create(meshDefinition, 32, 32);
    }
}
