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

public class BlasterBeamRenderer extends EntityRenderer<BlasterBeamEndpointEntity> {

    private static final ResourceLocation CORE_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/entity/beam_core.png");

    private static final ResourceLocation GLOW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/entity/beam_exterior.png");


    public BlasterBeamRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public boolean shouldRender(BlasterBeamEndpointEntity entity, net.minecraft.client.renderer.culling.Frustum frustum,
                                double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public void render(BlasterBeamEndpointEntity beam, float entityYaw, float partialTick,
                       PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
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

        long t = owner.level().getGameTime();
        renderSpinningBeam(poseStack, buffer, partialTick, t, len);

        poseStack.popPose();
        super.render(beam, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    private static void renderSpinningBeam(PoseStack poseStack, MultiBufferSource buffer,
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

        VertexConsumer depth = buffer.getBuffer(RenderType.entityCutoutNoCull(CORE_TEXTURE));
        renderTube(poseStack.last(), depth, coreColor, 0.0F, height, beamRadius, v0, v1);

        VertexConsumer core = buffer.getBuffer(RenderType.entityTranslucentEmissive(CORE_TEXTURE));
        renderTube(poseStack.last(), core, coreColor, 0.0F, height, beamRadius, v0, v1);

        poseStack.popPose();

        float gv0 = -1.0F + f2;
        float gv1 = height + gv0;

        VertexConsumer glow = buffer.getBuffer(RenderType.entityTranslucentEmissive(GLOW_TEXTURE));
        renderTube(poseStack.last(), glow, glowColor, 0.0F, height, glowRadius, gv0, gv1);
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

    @Override
    public ResourceLocation getTextureLocation(BlasterBeamEndpointEntity entity) {
        return GLOW_TEXTURE;
    }
}
