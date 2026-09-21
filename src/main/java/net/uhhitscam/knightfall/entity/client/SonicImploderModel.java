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

public class SonicImploderModel extends Model<Unit> {
    private final ModelPart sonicImploder;

    public SonicImploderModel(ModelPart root) {
        super(root.getChild("sonicImploder"), RenderTypes::entityCutout);
        this.sonicImploder = root.getChild("sonicImploder");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();

        PartDefinition sonicImploder = root.addOrReplaceChild(
                "sonicImploder",
                CubeListBuilder.create(),
                PartPose.rotation(0.0F, 0.0F, (float) Math.toRadians(90.0))
        );

        PartDefinition body = sonicImploder.addOrReplaceChild(
                "body",
                CubeListBuilder.create()
                        .texOffs(0, 12).addBox(-1.5F, -12.6F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(12, 0).addBox(-1.5F, -2.15F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(8, 19).addBox(-0.5F, -11.25F, -2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(12, 19).addBox(1.0F, -11.25F, -2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(16, 19).addBox(1.0F, -11.25F, 1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 20).addBox(-2.0F, -11.25F, -2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(4, 20).addBox(-2.0F, -11.25F, 1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(20, 4).addBox(-0.5F, -11.25F, 1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(20, 9).addBox(-2.0F, -11.25F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(20, 19).addBox(1.0F, -11.25F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(-1.5F, -11.4F, -1.5F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.25F))
                        .texOffs(24, 0).addBox(-0.5F, -6.5F, 1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(24, 5).addBox(-2.0F, -6.5F, 1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(8, 24).addBox(-2.0F, -6.5F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(24, 10).addBox(-2.0F, -6.5F, -2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(12, 24).addBox(-0.5F, -6.5F, -2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(24, 15).addBox(1.0F, -6.5F, 1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(16, 24).addBox(1.0F, -6.5F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(20, 24).addBox(1.0F, -6.5F, -2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(8, 16).addBox(-1.0F, -1.25F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.25F))
                        .texOffs(16, 16).addBox(-1.0F, -13.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.25F)),
                PartPose.offset(0.0F, 6.875F, 0.0F)
        );

        body.addOrReplaceChild(
                "cube_r1",
                CubeListBuilder.create().texOffs(0, 16)
                        .addBox(0.0F, 2.0F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.25F)),
                PartPose.offsetAndRotation(0.5F, -8.25F, 1.25F, -2.3562F, 0.0F, -3.1416F)
        );
        body.addOrReplaceChild(
                "cube_r2",
                CubeListBuilder.create().texOffs(12, 12)
                        .addBox(0.0F, 2.0F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.25F)),
                PartPose.offsetAndRotation(-1.25F, -8.25F, 0.5F, 0.0F, 1.5708F, -0.7854F)
        );
        body.addOrReplaceChild(
                "cube_r3",
                CubeListBuilder.create().texOffs(12, 8)
                        .addBox(0.0F, 2.0F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.25F)),
                PartPose.offsetAndRotation(1.25F, -8.25F, -0.5F, 0.0F, -1.5708F, 0.7854F)
        );
        body.addOrReplaceChild(
                "cube_r4",
                CubeListBuilder.create().texOffs(12, 4)
                        .addBox(0.0F, 2.0F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.25F)),
                PartPose.offsetAndRotation(-0.5F, -8.25F, -1.25F, 0.7854F, 0.0F, 0.0F)
        );

        return LayerDefinition.create(meshDefinition, 32, 32);
    }
}
