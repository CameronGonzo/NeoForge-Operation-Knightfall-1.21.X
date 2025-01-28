package net.uhhitscam.starwars.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.uhhitscam.starwars.OperationKnightfall;
import net.uhhitscam.starwars.entity.custom.StunBlasterBoltEntity;

public class StunBlasterBoltRenderer extends EntityRenderer<StunBlasterBoltEntity> {
    private StunBlasterBoltModel model;

    public StunBlasterBoltRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new StunBlasterBoltModel(context.bakeLayer(ModModelLayers.STUN_BLASTER_BOLT));
    }

    @Override
    public void render(StunBlasterBoltEntity pEntity, float entityYaw, float partialTicks, PoseStack poseStack,
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

        VertexConsumer mainVertexConsumer = bufferSource.getBuffer(this.model.renderType(this.getTextureLocation(pEntity)));
        this.model.renderToBuffer(poseStack, mainVertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);

        VertexConsumer emissiveVertexConsumer = bufferSource.getBuffer(RenderType.entityTranslucentEmissive(getTextureLocation(pEntity)));
        this.model.renderToBuffer(poseStack, emissiveVertexConsumer, 0xF000F0, OverlayTexture.NO_OVERLAY);

        poseStack.popPose();
        super.render(pEntity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(StunBlasterBoltEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/entity/stun_blaster_bolt.png");
    }
}