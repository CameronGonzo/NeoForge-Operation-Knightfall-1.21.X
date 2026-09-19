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
import net.uhhitscam.knightfall.entity.custom.SonicBoltEntity;

public class SonicBoltRenderer extends EntityRenderer<SonicBoltEntity, ProjectileRenderState> {
    private SonicBoltModel model;

    private static final Identifier CORE_TEXTURE =
            Identifier.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/entity/sonic_bolt_core.png");
    private static final Identifier GLOW_TEXTURE =
            Identifier.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/entity/sonic_bolt_exterior.png");


    public SonicBoltRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new SonicBoltModel(context.bakeLayer(ModModelLayers.SONIC_BOLT));
    }

    @Override
    public void submit(ProjectileRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        int packedLight = state.lightCoords;

        poseStack.pushPose();

        float scale = 0.6f;
        poseStack.scale(scale, scale, scale);
        poseStack.translate(0.0F, 0.1F, 0.0F);

        float yaw = state.yaw;
        float pitch = -state.pitch;
        poseStack.mulPose(Axis.YP.rotationDegrees(yaw));
        poseStack.mulPose(Axis.XP.rotationDegrees(pitch));

        int fullBright = 0xF000F0;

        model.submitCore(poseStack, collector, RenderTypes.entityCutout(CORE_TEXTURE), fullBright, OverlayTexture.NO_OVERLAY);

        model.submitGlow(poseStack, collector, RenderTypes.entityTranslucentEmissive(GLOW_TEXTURE), fullBright, OverlayTexture.NO_OVERLAY);

        model.submitCore(poseStack, collector, RenderTypes.entityTranslucentEmissive(CORE_TEXTURE), fullBright, OverlayTexture.NO_OVERLAY);

        poseStack.popPose();
        super.submit(state, poseStack, collector, camera);
    }


    public Identifier getTextureLocation(SonicBoltEntity entity) {
        return GLOW_TEXTURE;
    }

    @Override
    public ProjectileRenderState createRenderState() {
        return new ProjectileRenderState();
    }

    @Override
    public void extractRenderState(SonicBoltEntity entity, ProjectileRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.yaw = net.minecraft.util.Mth.rotLerp(partialTick, entity.yRotO, entity.getYRot());
        state.pitch = net.minecraft.util.Mth.lerp(partialTick, entity.xRotO, entity.getXRot());
        state.texture = getTextureLocation(entity);
    }
    @Override
    protected net.minecraft.world.phys.AABB getBoundingBoxForCulling(SonicBoltEntity entity) {
        return entity.getBoundingBoxForCulling();
    }
}
