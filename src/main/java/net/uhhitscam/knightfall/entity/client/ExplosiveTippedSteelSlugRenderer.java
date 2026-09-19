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
import net.uhhitscam.knightfall.entity.custom.ExplosiveTippedSteelSlugEntity;

public class ExplosiveTippedSteelSlugRenderer extends EntityRenderer<ExplosiveTippedSteelSlugEntity, ProjectileRenderState> {
    private ExplosiveTippedSteelSlugModel model;

    private static final Identifier EXPLOSIVE_TIPPED_STEEL_SLUG_TEXTURE =
            Identifier.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/entity/explosive_tipped_steel_slug.png");

    public ExplosiveTippedSteelSlugRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new ExplosiveTippedSteelSlugModel(context.bakeLayer(ModModelLayers.EXPLOSIVE_TIPPED_STEEL_SLUG));
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

        collector.submitModelPart(model.root(), poseStack, RenderTypes.entitySolid(EXPLOSIVE_TIPPED_STEEL_SLUG_TEXTURE), packedLight, OverlayTexture.NO_OVERLAY, null);

        poseStack.popPose();
        super.submit(state, poseStack, collector, camera);
    }

    public Identifier getTextureLocation(ExplosiveTippedSteelSlugEntity entity) {
        return EXPLOSIVE_TIPPED_STEEL_SLUG_TEXTURE;
    }

    @Override
    public ProjectileRenderState createRenderState() {
        return new ProjectileRenderState();
    }

    @Override
    public void extractRenderState(ExplosiveTippedSteelSlugEntity entity, ProjectileRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.yaw = net.minecraft.util.Mth.rotLerp(partialTick, entity.yRotO, entity.getYRot());
        state.pitch = net.minecraft.util.Mth.lerp(partialTick, entity.xRotO, entity.getXRot());
        state.texture = getTextureLocation(entity);
    }
    @Override
    protected net.minecraft.world.phys.AABB getBoundingBoxForCulling(ExplosiveTippedSteelSlugEntity entity) {
        return entity.getBoundingBoxForCulling();
    }
}
