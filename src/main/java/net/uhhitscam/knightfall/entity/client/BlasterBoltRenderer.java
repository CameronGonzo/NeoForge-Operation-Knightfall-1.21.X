package net.uhhitscam.knightfall.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.uhhitscam.knightfall.entity.custom.BlasterBoltEntity;

public class BlasterBoltRenderer extends EntityRenderer<BlasterBoltEntity> {
    private final BlasterBoltModel model;

    public BlasterBoltRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new BlasterBoltModel(context.bakeLayer(ModModelLayers.BLASTER_BOLT));
    }

    @Override
    public void render(
            BlasterBoltEntity entity,
            float entityYaw,
            float partialTicks,
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            int packedLight
    ) {
        poseStack.pushPose();
        poseStack.scale(0.4F, 0.4F, 0.4F);
        poseStack.translate(0.0F, 0.1F, 0.0F);

        float pitch = -Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());
        poseStack.mulPose(Axis.YP.rotationDegrees(entityYaw));
        poseStack.mulPose(Axis.XP.rotationDegrees(pitch));

        int fullBright = 0xF000F0;
        ResourceLocation coreTexture = entity.getBoltType().coreTexture();
        ResourceLocation glowTexture = entity.getBoltType().glowTexture();

        VertexConsumer depthConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(coreTexture));
        model.renderCore(poseStack, depthConsumer, fullBright, OverlayTexture.NO_OVERLAY);

        VertexConsumer glowConsumer = bufferSource.getBuffer(RenderType.entityTranslucentEmissive(glowTexture));
        model.renderGlow(poseStack, glowConsumer, fullBright, OverlayTexture.NO_OVERLAY);

        VertexConsumer coreConsumer = bufferSource.getBuffer(RenderType.entityTranslucentEmissive(coreTexture));
        model.renderCore(poseStack, coreConsumer, fullBright, OverlayTexture.NO_OVERLAY);

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(BlasterBoltEntity entity) {
        return entity.getBoltType().glowTexture();
    }
}
