package net.uhhitscam.knightfall.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.entity.custom.PlasticSlugEntity;

public class PlasticSlugRenderer extends EntityRenderer<PlasticSlugEntity, ProjectileRenderState> {
    private PlasticSlugModel model;

    private static final Identifier PLASTIC_SLUG_TEXTURE =
            Identifier.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/entity/plastic_slug.png");

    public PlasticSlugRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new PlasticSlugModel(context.bakeLayer(ModModelLayers.PLASTIC_SLUG));
    }

    @Override
    public void submit(ProjectileRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        int packedLight = state.lightCoords;

        poseStack.pushPose();

        float scale = 0.4f;
        poseStack.scale(scale, scale, scale);
        poseStack.translate(0.0F, 0.1F, 0.0F);

        float yaw = state.yaw - 180;
        float pitch = state.pitch;
        poseStack.mulPose(Axis.YP.rotationDegrees(yaw));
        poseStack.mulPose(Axis.XP.rotationDegrees(pitch));

        collector.submitModelPart(model.root(), poseStack, RenderTypes.entitySolid(PLASTIC_SLUG_TEXTURE), packedLight, OverlayTexture.NO_OVERLAY, null);

        poseStack.popPose();
        super.submit(state, poseStack, collector, camera);
    }

    public Identifier getTextureLocation(PlasticSlugEntity entity) {
        return PLASTIC_SLUG_TEXTURE;
    }

    @Override
    public ProjectileRenderState createRenderState() {
        return new ProjectileRenderState();
    }

    @Override
    public void extractRenderState(PlasticSlugEntity entity, ProjectileRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.yaw = net.minecraft.util.Mth.rotLerp(partialTick, entity.yRotO, entity.getYRot());
        state.pitch = net.minecraft.util.Mth.lerp(partialTick, entity.xRotO, entity.getXRot());
        state.texture = getTextureLocation(entity);
    }
    @Override
    protected net.minecraft.world.phys.AABB getBoundingBoxForCulling(PlasticSlugEntity entity) {
        return entity.getBoundingBoxForCulling();
    }
}
