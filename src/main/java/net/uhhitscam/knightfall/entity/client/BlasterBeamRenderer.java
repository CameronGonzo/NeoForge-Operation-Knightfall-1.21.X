package net.uhhitscam.knightfall.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.entity.custom.BlasterBeamEndpointEntity;
import net.uhhitscam.knightfall.util.WeaponAimRules;
import net.uhhitscam.knightfall.util.WeaponTargeting;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class BlasterBeamRenderer extends EntityRenderer<BlasterBeamEndpointEntity, BlasterBeamRenderer.State> {

    private static final Identifier CORE_TEXTURE =
            Identifier.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/entity/beam_core.png");

    private static final Identifier GLOW_TEXTURE =
            Identifier.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/entity/beam_exterior.png");


    public BlasterBeamRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public boolean shouldRender(BlasterBeamEndpointEntity entity, net.minecraft.client.renderer.culling.Frustum frustum,
                                double camX, double camY, double camZ) {
        return true;
    }

    public static class State extends EntityRenderState {
        public Vec3 start = Vec3.ZERO;
        public Vec3 end = Vec3.ZERO;
        public Vec3 origin = Vec3.ZERO;
        public float partialTick;
        public long gameTime;
        public boolean visible;
    }

    @Override
    public State createRenderState() { return new State(); }

    @Override
    public void extractRenderState(BlasterBeamEndpointEntity beam, State state, float partialTick) {
        super.extractRenderState(beam, state, partialTick);
        state.visible = false;
        LivingEntity owner = beam.getOwnerLiving();
        if (owner == null) return;

        Vec3 start;
        Vec3 direction = owner.getViewVector(partialTick);
        if (owner instanceof Player player) {
            start = WeaponAimRules.getBeamMuzzlePosition(player, beam.isMainHand(), partialTick);
        } else {
            start = owner.getEyePosition(partialTick).add(direction.scale(0.4));
        }
        Vec3 end = WeaponTargeting.findBeamHit(
                owner.level(), owner, start, direction, BlasterBeamEndpointEntity.DEFAULT_RANGE
        ).endPosition();
        Vec3 entityOrigin = new Vec3(
                Mth.lerp(partialTick, beam.xo, beam.getX()),
                Mth.lerp(partialTick, beam.yo, beam.getY()),
                Mth.lerp(partialTick, beam.zo, beam.getZ())
        );

        state.start = start;
        state.end = end;
        state.origin = entityOrigin;
        state.gameTime = owner.level().getGameTime();
        state.partialTick = partialTick;
        state.visible = true;
    }

    @Override
    public void submit(State state, PoseStack poseStack, SubmitNodeCollector buffer, CameraRenderState camera) {
        if (!state.visible) return;
        Vec3 start = state.start;
        Vec3 end = state.end;
        Vec3 entityOrigin = state.origin;
        Vec3 dir = end.subtract(start);
        float len = (float) dir.length();
        if (len < 0.01F) return;

        Vec3 n = dir.scale(1.0 / len);

        Vec3 diff = start.subtract(entityOrigin);

        poseStack.pushPose();
        poseStack.translate(diff.x, diff.y, diff.z);

        Quaternionf q = new Quaternionf().rotateTo(
                new Vector3f(0, 1, 0),
                new Vector3f((float) n.x, (float) n.y, (float) n.z)
        );
        poseStack.mulPose(q);

        long t = state.gameTime;
        renderSpinningBeam(poseStack, buffer, state.partialTick, t, len);

        poseStack.popPose();
        super.submit(state, poseStack, buffer, camera);
    }

    private static void renderSpinningBeam(PoseStack poseStack, SubmitNodeCollector buffer,
                                           float partialTick, long gameTime, float height) {

        float beamRadius = 0.01F;
        float glowRadius = 0.02F;

        int coreColor = 0xFFFFFFFF;
        int glowColor = 0x80FFFFFF;

        float f = (float)Math.floorMod(gameTime, 40) + partialTick;
        float f2 = Mth.frac((-f) * 0.2F - (float)Mth.floor((-f) * 0.1F));

        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(f * 2.25F - 45.0F));

        float v0 = -1.0F + f2;
        float v1 = height * (0.5F / beamRadius) + v0;

        buffer.submitCustomGeometry(poseStack, RenderTypes.entityCutout(CORE_TEXTURE), (pose, vertices) -> renderTube(pose, vertices, coreColor, 0.0F, height, beamRadius, v0, v1));

        buffer.submitCustomGeometry(poseStack, RenderTypes.entityTranslucentEmissive(CORE_TEXTURE), (pose, vertices) -> renderTube(pose, vertices, coreColor, 0.0F, height, beamRadius, v0, v1));

        poseStack.popPose();

        float gv0 = -1.0F + f2;
        float gv1 = height + gv0;

        buffer.submitCustomGeometry(poseStack, RenderTypes.entityTranslucentEmissive(GLOW_TEXTURE), (pose, vertices) -> renderTube(pose, vertices, glowColor, 0.0F, height, glowRadius, gv0, gv1));
    }

    private static void renderTube(PoseStack.Pose pose, VertexConsumer vc, int color,
                                   float y0, float y1, float r, float v0, float v1) {

        quad(pose, vc, color, y0, y1,  r, 0,  0, -r,  0.7071F, 0F, -0.7071F,  0, 1, v1, v0);

        quad(pose, vc, color, y0, y1,  0, -r, -r, 0, -0.7071F, 0F, -0.7071F,  0, 1, v1, v0);

        quad(pose, vc, color, y0, y1, -r, 0,  0, r, -0.7071F, 0F,  0.7071F,  0, 1, v1, v0);

        quad(pose, vc, color, y0, y1,  0, r,  r, 0,  0.7071F, 0F,  0.7071F,  0, 1, v1, v0);
    }

    private static void quad(PoseStack.Pose pose, VertexConsumer vc, int color,
                             float y0, float y1,
                             float x0, float z0, float x1, float z1,
                             float nx, float ny, float nz,
                             float u0, float u1, float v0, float v1) {
        add(pose, vc, color, y1, x0, z0, nx, ny, nz, u1, v0);
        add(pose, vc, color, y0, x0, z0, nx, ny, nz, u1, v1);
        add(pose, vc, color, y0, x1, z1, nx, ny, nz, u0, v1);
        add(pose, vc, color, y1, x1, z1, nx, ny, nz, u0, v0);
    }

    private static void add(PoseStack.Pose pose, VertexConsumer vc, int color,
                            float y, float x, float z,
                            float nx, float ny, float nz,
                            float u, float v) {
        vc.addVertex(pose, x, y, z)
                .setColor(color)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(15728880)
                .setNormal(pose, nx, ny, nz);
    }

    public Identifier getTextureLocation(BlasterBeamEndpointEntity entity) {
        return GLOW_TEXTURE;
    }
    @Override
    protected net.minecraft.world.phys.AABB getBoundingBoxForCulling(BlasterBeamEndpointEntity entity) {
        return entity.getBoundingBoxForCulling();
    }
}
