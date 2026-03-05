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
import net.uhhitscam.knightfall.entity.custom.ExplosiveTippedSteelSlugEntity;

public class ExplosiveTippedSteelSlugRenderer extends EntityRenderer<ExplosiveTippedSteelSlugEntity> {
    private ExplosiveTippedSteelSlugModel model;

    private static final ResourceLocation EXPLOSIVE_TIPPED_STEEL_SLUG_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/entity/explosive_tipped_steel_slug.png");

    public ExplosiveTippedSteelSlugRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new ExplosiveTippedSteelSlugModel(context.bakeLayer(ModModelLayers.EXPLOSIVE_TIPPED_STEEL_SLUG));
    }

    @Override
    public void render(ExplosiveTippedSteelSlugEntity entity, float entityYaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

        poseStack.pushPose();

        float scale = 0.4f;
        poseStack.scale(scale, scale, scale);
        poseStack.translate(0.0F, 0.1F, 0.0F);

        float yaw = entity.getYRot() - 180;
        float pitch = entity.getXRot();
        poseStack.mulPose(Axis.YP.rotationDegrees(yaw));
        poseStack.mulPose(Axis.XP.rotationDegrees(pitch));

        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entitySolid(EXPLOSIVE_TIPPED_STEEL_SLUG_TEXTURE));
        model.root().render(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(ExplosiveTippedSteelSlugEntity entity) {
        return EXPLOSIVE_TIPPED_STEEL_SLUG_TEXTURE;
    }
}