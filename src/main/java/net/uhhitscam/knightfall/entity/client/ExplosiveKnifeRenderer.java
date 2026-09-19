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
import net.minecraft.util.Mth;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.entity.custom.ExplosiveKnifeEntity;

public class ExplosiveKnifeRenderer extends EntityRenderer<ExplosiveKnifeEntity, ProjectileRenderState> {
    private static final Identifier BASE_TEXTURE = texture("explosive_knife");
    private static final Identifier BEEP_TEXTURE = texture("explosive_knife_1");

    private final ExplosiveKnifeModel model;

    public ExplosiveKnifeRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new ExplosiveKnifeModel(context.bakeLayer(ModModelLayers.EXPLOSIVE_KNIFE));
        shadowRadius = 0.0F;
    }

    @Override
    public void submit(ProjectileRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        int packedLight = state.lightCoords;
        poseStack.pushPose();
        float yaw = state.yaw;
        poseStack.mulPose(Axis.YP.rotationDegrees(yaw));
        poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
        poseStack.scale(-0.3F, -0.3F, -0.3F);

        collector.submitModelPart(model.root(), poseStack, RenderTypes.entityCutout(state.texture), packedLight, OverlayTexture.NO_OVERLAY, null);
        poseStack.popPose();
        super.submit(state, poseStack, collector, camera);
    }

    public Identifier getTextureLocation(ExplosiveKnifeEntity entity) {
        return entity.isBeepLightOn() ? BEEP_TEXTURE : BASE_TEXTURE;
    }

    private static Identifier texture(String name) {
        return Identifier.fromNamespaceAndPath(
                OperationKnightfall.MODID,
                "textures/entity/" + name + ".png"
        );
    }

    @Override
    public ProjectileRenderState createRenderState() {
        return new ProjectileRenderState();
    }

    @Override
    public void extractRenderState(ExplosiveKnifeEntity entity, ProjectileRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.yaw = net.minecraft.util.Mth.rotLerp(partialTick, entity.yRotO, entity.getYRot());
        state.pitch = net.minecraft.util.Mth.lerp(partialTick, entity.xRotO, entity.getXRot());
        state.texture = getTextureLocation(entity);
    }
}
