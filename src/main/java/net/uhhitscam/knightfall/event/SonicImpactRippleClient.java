package net.uhhitscam.knightfall.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.uhhitscam.knightfall.OperationKnightfall;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class SonicImpactRippleClient {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            OperationKnightfall.MODID,
            "textures/particle/sonic_bolt_ripple.png"
    );

    private static final int LIFETIME_TICKS = 16;
    private static final float START_SIZE = 0.2F;
    private static final float END_SIZE = 2.8F;

    private static final List<Ripple> RIPPLES = new ArrayList<>();

    private SonicImpactRippleClient() {
    }

    public static void register(IEventBus eventBus) {
        eventBus.addListener(SonicImpactRippleClient::onRenderLevelStage);
    }

    public static void add(Vector3f position, Direction direction) {
        RIPPLES.add(new Ripple(
                new Vec3(position.x(), position.y(), position.z()),
                direction,
                0
        ));
    }

    @SubscribeEvent
    public static void onRenderLevelStage(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES) {
            return;
        }

        if (RIPPLES.isEmpty()) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        Camera camera = minecraft.gameRenderer.getMainCamera();
        Vec3 cameraPosition = camera.getPosition();

        PoseStack poseStack = event.getPoseStack();
        MultiBufferSource.BufferSource buffer = minecraft.renderBuffers().bufferSource();
        VertexConsumer consumer = buffer.getBuffer(RenderType.entityTranslucent(TEXTURE));

        Iterator<Ripple> iterator = RIPPLES.iterator();

        while (iterator.hasNext()) {
            Ripple ripple = iterator.next();

            if (ripple.age >= LIFETIME_TICKS) {
                iterator.remove();
                continue;
            }

            renderRipple(ripple, event.getPartialTick().getGameTimeDeltaPartialTick(false), cameraPosition, poseStack, consumer);
            ripple.age++;
        }

        buffer.endBatch(RenderType.entityTranslucent(TEXTURE));
    }

    private static void renderRipple(
            Ripple ripple,
            float partialTick,
            Vec3 cameraPosition,
            PoseStack poseStack,
            VertexConsumer consumer
    ) {
        float ageProgress = (ripple.age + partialTick) / LIFETIME_TICKS;
        float size = lerp(START_SIZE, END_SIZE, ageProgress);
        float alpha = 1.0F;

        double renderX = ripple.position.x - cameraPosition.x;
        double renderY = ripple.position.y - cameraPosition.y;
        double renderZ = ripple.position.z - cameraPosition.z;

        poseStack.pushPose();
        poseStack.translate(renderX, renderY, renderZ);
        rotateToFaceDirection(poseStack, ripple.direction);

        float halfSize = size * 0.5F;

        PoseStack.Pose pose = poseStack.last();

        addVertex(consumer, pose, -halfSize, -halfSize, 0.0F, 0.0F, 1.0F, alpha);
        addVertex(consumer, pose, halfSize, -halfSize, 0.0F, 1.0F, 1.0F, alpha);
        addVertex(consumer, pose, halfSize, halfSize, 0.0F, 1.0F, 0.0F, alpha);
        addVertex(consumer, pose, -halfSize, halfSize, 0.0F, 0.0F, 0.0F, alpha);

        poseStack.popPose();
    }

    private static void rotateToFaceDirection(PoseStack poseStack, Direction direction) {
        switch (direction) {
            case UP -> poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            case DOWN -> poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
            case NORTH -> {
                // Default quad faces north/south closely enough depending on texture orientation.
            }
            case SOUTH -> poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            case EAST -> poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
            case WEST -> poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
        }
    }

    private static void addVertex(
            VertexConsumer consumer,
            PoseStack.Pose pose,
            float x,
            float y,
            float z,
            float u,
            float v,
            float alpha
    ) {
        consumer.addVertex(pose, x, y, z)
                .setColor(1.0F, 1.0F, 1.0F, alpha)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(0xF000F0)
                .setNormal(pose, 0.0F, 0.0F, 1.0F);
    }

    private static float lerp(float start, float end, float progress) {
        return start + (end - start) * progress;
    }

    private static final class Ripple {
        private final Vec3 position;
        private final Direction direction;
        private int age;

        private Ripple(Vec3 position, Direction direction, int age) {
            this.position = position;
            this.direction = direction;
            this.age = age;
        }
    }
}