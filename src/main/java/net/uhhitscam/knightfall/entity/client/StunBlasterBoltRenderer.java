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
import net.uhhitscam.knightfall.entity.custom.StunBlasterBoltEntity;

public class StunBlasterBoltRenderer extends EntityRenderer<StunBlasterBoltEntity, ProjectileRenderState> {
    private StunBlasterBoltModel model;

    public StunBlasterBoltRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new StunBlasterBoltModel(context.bakeLayer(ModModelLayers.STUN_BLASTER_BOLT));
    }

    @Override
    public void submit(ProjectileRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        int packedLight = state.lightCoords;
        poseStack.pushPose();

        float scale = 0.5f;  // Scale factor of bolt size
        poseStack.scale(scale, scale, scale);  // Apply uniform scaling

        poseStack.translate(0.0F, 0.8F, 0.0F); //adjust spawn position of projectileWeapon bolt to gun height

        float yaw = state.yaw;  // Horizontal rotation (yaw)
        float pitch = -state.pitch;  // Vertical rotation (pitch)

        // Apply the rotations to the projectileWeapon bolt
        poseStack.mulPose(Axis.YP.rotationDegrees(yaw)); // Rotate around Y-axis (horizontal)
        poseStack.mulPose(Axis.XP.rotationDegrees(pitch)); // Rotate around X-axis (vertical)

        collector.submitModelPart(model.root(), poseStack, RenderTypes.entityCutout(state.texture), packedLight, OverlayTexture.NO_OVERLAY, null);

        collector.submitModelPart(model.root(), poseStack, RenderTypes.entityTranslucentEmissive(state.texture), 0xF000F0, OverlayTexture.NO_OVERLAY, null);

        poseStack.popPose();
        super.submit(state, poseStack, collector, camera);
    }

    public Identifier getTextureLocation(StunBlasterBoltEntity entity) {
        return Identifier.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/entity/stun_blaster_bolt.png");
    }

    @Override
    public ProjectileRenderState createRenderState() {
        return new ProjectileRenderState();
    }

    @Override
    public void extractRenderState(StunBlasterBoltEntity entity, ProjectileRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.yaw = net.minecraft.util.Mth.rotLerp(partialTick, entity.yRotO, entity.getYRot());
        state.pitch = net.minecraft.util.Mth.lerp(partialTick, entity.xRotO, entity.getXRot());
        state.texture = getTextureLocation(entity);
    }
    @Override
    protected net.minecraft.world.phys.AABB getBoundingBoxForCulling(StunBlasterBoltEntity entity) {
        return entity.getBoundingBoxForCulling();
    }
}
