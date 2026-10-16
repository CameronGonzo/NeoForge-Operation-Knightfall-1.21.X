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

public class PressureMineModel extends Model<Unit> {
    // Model-space offsets in pixels (16 pixels = 1 block before renderer scaling).
    // Center the actual rotated geometry, not the enclosing unrotated cube bounds.
    private static final float MODEL_OFFSET_X = 0.0F;
    private static final float MODEL_OFFSET_Y = 0.65F;
    private static final float MODEL_OFFSET_Z = 0.7144661F;

    public PressureMineModel(ModelPart root) {
        super(root.getChild("pressureMine"), RenderTypes::entityCutout);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();

        PartDefinition pressureMine = root.addOrReplaceChild("pressureMine", CubeListBuilder.create(),
                PartPose.offset(MODEL_OFFSET_X, MODEL_OFFSET_Y, MODEL_OFFSET_Z));

        pressureMine.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(8, 24).addBox(2.0F, -1.3F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
        .texOffs(4, 24).addBox(3.0F, -1.3F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
        .texOffs(0, 24).addBox(2.0F, -1.3F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
        .texOffs(16, 23).addBox(3.5F, -1.25F, 3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
        .texOffs(22, 22).addBox(3.5F, -1.25F, 2.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
        .texOffs(0, 21).addBox(2.5F, -1.25F, 3.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
        .texOffs(8, 18).addBox(1.5F, -1.25F, 3.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
        .texOffs(16, 18).addBox(3.5F, -1.25F, 1.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
        .texOffs(14, 14).addBox(0.5F, -1.25F, 0.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
        .texOffs(12, 22).addBox(5.0F, -1.0F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
        .texOffs(22, 20).addBox(5.0F, -1.0F, 4.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
        .texOffs(0, 17).addBox(4.0F, -1.0F, 4.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
        .texOffs(16, 0).addBox(3.0F, -1.0F, 4.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
        .texOffs(16, 5).addBox(3.0F, -1.0F, 3.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
        .texOffs(0, 0).addBox(2.0F, -1.0F, 2.0F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
        .texOffs(14, 12).addBox(3.0F, -1.0F, 2.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
        .texOffs(16, 20).addBox(1.0F, -1.0F, 7.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
        .texOffs(6, 22).addBox(7.0F, -1.0F, 1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
        .texOffs(0, 8).addBox(0.0F, -1.0F, 2.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
        .texOffs(0, 14).addBox(2.0F, -1.0F, 0.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
        .texOffs(14, 8).addBox(-1.0F, -1.0F, -1.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -4.25F, 0.0F, -0.7854F, 0.0F));

        return LayerDefinition.create(meshDefinition, 32, 32);
    }

}
