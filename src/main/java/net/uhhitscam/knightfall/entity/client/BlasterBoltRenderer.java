package net.uhhitscam.knightfall.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.uhhitscam.knightfall.entity.custom.BlasterBoltEntity;

public class BlasterBoltRenderer extends EntityRenderer<BlasterBoltEntity, ProjectileRenderState> {
    private final BlasterBoltModel model;

    public BlasterBoltRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new BlasterBoltModel(context.bakeLayer(ModModelLayers.BLASTER_BOLT));
    }

    @Override
    public void submit(ProjectileRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        poseStack.pushPose();
        poseStack.scale(0.4F, 0.4F, 0.4F);
        poseStack.translate(0.0F, 0.1F, 0.0F);

        float pitch = -state.pitch;
        poseStack.mulPose(Axis.YP.rotationDegrees(state.yaw));
        poseStack.mulPose(Axis.XP.rotationDegrees(pitch));

        int fullBright = 0xF000F0;
        Identifier coreTexture = state.coreTexture;
        Identifier glowTexture = state.glowTexture;

        model.submitCore(poseStack, collector, RenderTypes.entityCutout(coreTexture), fullBright, OverlayTexture.NO_OVERLAY);

        model.submitGlow(poseStack, collector, RenderTypes.entityTranslucentEmissive(glowTexture), fullBright, OverlayTexture.NO_OVERLAY);

        model.submitCore(poseStack, collector, RenderTypes.entityTranslucentEmissive(coreTexture), fullBright, OverlayTexture.NO_OVERLAY);

        poseStack.popPose();
        super.submit(state, poseStack, collector, camera);
    }

    public Identifier getTextureLocation(BlasterBoltEntity entity) {
        return entity.getBoltType().glowTexture();
    }

    @Override
    public ProjectileRenderState createRenderState() {
        return new ProjectileRenderState();
    }

    @Override
    public void extractRenderState(BlasterBoltEntity entity, ProjectileRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.yaw = net.minecraft.util.Mth.rotLerp(partialTick, entity.yRotO, entity.getYRot());
        state.pitch = net.minecraft.util.Mth.lerp(partialTick, entity.xRotO, entity.getXRot());
        state.texture = getTextureLocation(entity);
        state.coreTexture = entity.getBoltType().coreTexture();
        state.glowTexture = entity.getBoltType().glowTexture();
    }
    @Override
    protected net.minecraft.world.phys.AABB getBoundingBoxForCulling(BlasterBoltEntity entity) {
        return entity.getBoundingBoxForCulling();
    }
}
