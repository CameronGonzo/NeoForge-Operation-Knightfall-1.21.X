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
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.entity.custom.ExplosiveKnifeEntity;

public class ExplosiveKnifeRenderer extends EntityRenderer<ExplosiveKnifeEntity> {
    private static final ResourceLocation BASE_TEXTURE = texture("explosive_knife");
    private static final ResourceLocation BEEP_TEXTURE = texture("explosive_knife_1");

    private final ExplosiveKnifeModel model;

    public ExplosiveKnifeRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new ExplosiveKnifeModel(context.bakeLayer(ModModelLayers.EXPLOSIVE_KNIFE));
        shadowRadius = 0.0F;
    }

    @Override
    public void render(
            ExplosiveKnifeEntity entity,
            float entityYaw,
            float partialTicks,
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            int packedLight
    ) {
        poseStack.pushPose();
        float yaw = Mth.rotLerp(partialTicks, entity.yRotO, entity.getYRot());
        poseStack.mulPose(Axis.YP.rotationDegrees(yaw));
        poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
        poseStack.scale(-0.3F, -0.3F, -0.3F);

        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(entity)));
        model.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(ExplosiveKnifeEntity entity) {
        return entity.isBeepLightOn() ? BEEP_TEXTURE : BASE_TEXTURE;
    }

    private static ResourceLocation texture(String name) {
        return ResourceLocation.fromNamespaceAndPath(
                OperationKnightfall.MODID,
                "textures/entity/" + name + ".png"
        );
    }
}
