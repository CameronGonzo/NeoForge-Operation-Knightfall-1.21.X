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
import net.uhhitscam.starwars.entity.custom.TibannaXBlasterBoltEntity;

public class TibannaXBlasterBoltRenderer extends EntityRenderer<TibannaXBlasterBoltEntity> {
    private TibannaXBlasterBoltModel model;

    private static final ResourceLocation CORE_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/entity/tibannax_bolt_core.png");
    private static final ResourceLocation GLOW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/entity/tibannax_bolt_exterior.png");


    public TibannaXBlasterBoltRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new TibannaXBlasterBoltModel(context.bakeLayer(ModModelLayers.TIBANNAX_BLASTER_BOLT));
    }

    @Override
    public void render(TibannaXBlasterBoltEntity entity, float entityYaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

        poseStack.pushPose();

        float scale = 0.4f;
        poseStack.scale(scale, scale, scale);
        poseStack.translate(0.0F, 0.1F, 0.0F);

        float yaw = entity.getYRot();
        float pitch = -entity.getXRot();
        poseStack.mulPose(Axis.YP.rotationDegrees(yaw));
        poseStack.mulPose(Axis.XP.rotationDegrees(pitch));

        int fullBright = 0xF000F0;

        VertexConsumer glowConsumer = bufferSource.getBuffer(RenderType.entityTranslucentEmissive(GLOW_TEXTURE));
        model.renderGlow(poseStack, glowConsumer, fullBright, OverlayTexture.NO_OVERLAY);

        poseStack.pushPose();
        VertexConsumer coreConsumer = bufferSource.getBuffer(RenderType.entityTranslucentEmissive(CORE_TEXTURE));
        model.renderCore(poseStack, coreConsumer, fullBright, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(TibannaXBlasterBoltEntity entity) {
        return GLOW_TEXTURE;
    }
}