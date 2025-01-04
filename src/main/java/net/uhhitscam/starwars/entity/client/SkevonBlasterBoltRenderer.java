package net.uhhitscam.starwars.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.uhhitscam.starwars.OperationKnightfall;
import net.uhhitscam.starwars.entity.custom.SkevonBlasterBoltEntity;
import net.uhhitscam.starwars.entity.custom.TibannaBlasterBoltEntity;

public class SkevonBlasterBoltRenderer extends EntityRenderer<SkevonBlasterBoltEntity> {
    private SkevonBlasterBoltModel model;

    public SkevonBlasterBoltRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new SkevonBlasterBoltModel(context.bakeLayer(ModModelLayers.SKEVON_BLASTER_BOLT));
    }

    @Override
    public void render(SkevonBlasterBoltEntity pEntity, float entityYaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();

        float scale = 0.5f;  // Scale factor of bolt size
        poseStack.scale(scale, scale, scale);  // Apply uniform scaling

        poseStack.translate(0.0F, -1.4F, 0.0F); //adjust spawn position of blaster bolt to gun height

        float yaw = pEntity.getYRot();  // Horizontal rotation (yaw)
        float pitch = pEntity.getXRot();  // Vertical rotation (pitch)

        // Apply the rotations to the blaster bolt
        poseStack.mulPose(Axis.YP.rotationDegrees(yaw)); // Rotate around Y-axis (horizontal)
        poseStack.mulPose(Axis.XP.rotationDegrees(pitch)); // Rotate around X-axis (vertical)

        VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(
                bufferSource, this.model.renderType(this.getTextureLocation(pEntity)),false, false);
        this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        super.render(pEntity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(SkevonBlasterBoltEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/entity/skevon_blaster_bolt.png");
    }
}