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
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.entity.custom.FlechetteToxicSpreadCanEntity;

public class FlechetteToxicSpreadCanRenderer extends EntityRenderer<FlechetteToxicSpreadCanEntity> {
    private FlechetteToxicSpreadCanModel model;

    private static final ResourceLocation FLECHETTE_TOXIC_SPREAD_CAN_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/entity/flechette_toxic_spread_can.png");

    public FlechetteToxicSpreadCanRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new FlechetteToxicSpreadCanModel(context.bakeLayer(ModModelLayers.FLECHETTE_TOXIC_SPREAD_CAN));
    }

    @Override
    public void render(FlechetteToxicSpreadCanEntity entity, float entityYaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

        poseStack.pushPose();

        float scale = 0.4f;
        poseStack.scale(scale, scale, scale);
        poseStack.translate(0.0F, 0.1F, 0.0F);

        float yaw = entity.getYRot() - 180;
        float pitch = entity.getXRot();
        poseStack.mulPose(Axis.YP.rotationDegrees(yaw));
        poseStack.mulPose(Axis.XP.rotationDegrees(pitch));

        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entitySolid(FLECHETTE_TOXIC_SPREAD_CAN_TEXTURE));
        model.root().render(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(FlechetteToxicSpreadCanEntity entity) {
        return FLECHETTE_TOXIC_SPREAD_CAN_TEXTURE;
    }
}