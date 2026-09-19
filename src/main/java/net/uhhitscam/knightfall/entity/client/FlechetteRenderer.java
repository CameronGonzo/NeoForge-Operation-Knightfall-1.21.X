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
import net.uhhitscam.knightfall.entity.custom.FlechetteEntity;

public class FlechetteRenderer extends EntityRenderer<FlechetteEntity, ProjectileRenderState> {
    private FlechetteModel model;

    private static final Identifier FLECHETTE_TEXTURE =
            Identifier.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/entity/flechette.png");

    public FlechetteRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new FlechetteModel(context.bakeLayer(ModModelLayers.FLECHETTE));
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

        collector.submitModelPart(model.root(), poseStack, RenderTypes.entitySolid(FLECHETTE_TEXTURE), packedLight, OverlayTexture.NO_OVERLAY, null);

        poseStack.popPose();
        super.submit(state, poseStack, collector, camera);
    }

    public Identifier getTextureLocation(FlechetteEntity entity) {
        return FLECHETTE_TEXTURE;
    }

    @Override
    public ProjectileRenderState createRenderState() {
        return new ProjectileRenderState();
    }

    @Override
    public void extractRenderState(FlechetteEntity entity, ProjectileRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.yaw = net.minecraft.util.Mth.rotLerp(partialTick, entity.yRotO, entity.getYRot());
        state.pitch = net.minecraft.util.Mth.lerp(partialTick, entity.xRotO, entity.getXRot());
        state.texture = getTextureLocation(entity);
    }
    @Override
    protected net.minecraft.world.phys.AABB getBoundingBoxForCulling(FlechetteEntity entity) {
        return entity.getBoundingBoxForCulling();
    }
}
