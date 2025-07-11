package net.uhhitscam.starwars.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.uhhitscam.starwars.OperationKnightfall;
import net.uhhitscam.starwars.entity.custom.IonizedTibannaBlasterBoltEntity;
import net.uhhitscam.starwars.entity.custom.TibannaBlasterBoltEntity;

public class IonizedTibannaBlasterBoltRenderer extends EntityRenderer<IonizedTibannaBlasterBoltEntity> {
    private IonizedTibannaBlasterBoltModel model;

    private static final ResourceLocation CORE_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/entity/bolt_core.png");
    private static final ResourceLocation GLOW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/entity/ionized_tibanna_bolt_exterior.png");


    public IonizedTibannaBlasterBoltRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new IonizedTibannaBlasterBoltModel(context.bakeLayer(ModModelLayers.IONIZED_TIBANNA_BLASTER_BOLT));
    }

    @Override
    public void render(IonizedTibannaBlasterBoltEntity entity, float entityYaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

        poseStack.pushPose();

        float scale = 0.5f;
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
    public ResourceLocation getTextureLocation(IonizedTibannaBlasterBoltEntity entity) {
        return GLOW_TEXTURE;
    }
}