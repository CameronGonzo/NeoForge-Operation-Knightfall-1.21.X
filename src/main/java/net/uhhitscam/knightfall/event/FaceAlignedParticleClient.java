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
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.uhhitscam.knightfall.OperationKnightfall;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class FaceAlignedParticleClient {
    private static int nextLayerOffset = 0;
    private static final int MAX_DECAL_LAYERS = 64;
    private static final float DECAL_LAYER_STEP = 0.0002F;

    private static final List<EffectInstance> EFFECTS = new ArrayList<>();

    private FaceAlignedParticleClient() {
    }

    public static void register(IEventBus eventBus) {
        eventBus.addListener(FaceAlignedParticleClient::onRenderLevelStage);
    }

    public static void add(
            Vector3f position,
            Direction direction,
            FaceAlignedParticleType effectType,
            int variant
    ) {
        float depthOffset = 0.0F;

        if (effectType == FaceAlignedParticleType.BLASTER_BURN_MARK) {
            depthOffset = nextLayerOffset * DECAL_LAYER_STEP;
            nextLayerOffset = (nextLayerOffset + 1) % MAX_DECAL_LAYERS;
        }

        EFFECTS.add(new EffectInstance(
                new Vec3(position.x(), position.y(), position.z()),
                direction,
                effectType,
                Math.max(1, variant),
                depthOffset,
                0
        ));
    }

    public static void onRenderLevelStage(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES) {
            return;
        }

        if (EFFECTS.isEmpty()) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        Camera camera = minecraft.gameRenderer.getMainCamera();
        Vec3 cameraPosition = camera.getPosition();

        PoseStack poseStack = event.getPoseStack();
        MultiBufferSource.BufferSource buffer = minecraft.renderBuffers().bufferSource();

        Iterator<EffectInstance> iterator = EFFECTS.iterator();

        while (iterator.hasNext()) {
            EffectInstance effect = iterator.next();

            EffectVisual visual = EffectVisual.forEffect(effect);
            if (effect.age >= visual.totalLifetime()) {
                iterator.remove();
                continue;
            }

            renderEffect(
                    effect,
                    visual,
                    event.getPartialTick().getGameTimeDeltaPartialTick(false),
                    cameraPosition,
                    poseStack,
                    buffer
            );

            effect.age++;
        }

        buffer.endBatch();
    }

    private static void renderEffect(
            EffectInstance effect,
            EffectVisual visual,
            float partialTick,
            Vec3 cameraPosition,
            PoseStack poseStack,
            MultiBufferSource.BufferSource buffer
    ) {
        float age = effect.age + partialTick;
        float progress = age / visual.totalLifetime();

        double renderX = effect.position.x - cameraPosition.x;
        double renderY = effect.position.y - cameraPosition.y;
        double renderZ = effect.position.z - cameraPosition.z;

        poseStack.pushPose();
        poseStack.translate(renderX, renderY, renderZ);
        rotateToFaceDirection(poseStack, effect.direction);
        poseStack.translate(0.0F, 0.0F, effect.depthOffset);

        switch (effect.effectType) {
            case SONIC_RIPPLE -> renderSonicRipple(effect, visual, progress, poseStack, buffer);
            case BLASTER_BURN_MARK -> renderBlasterBurnMark(effect, visual, age, poseStack, buffer);
        }

        poseStack.popPose();
    }

    private static void renderSonicRipple(
            EffectInstance effect,
            EffectVisual visual,
            float progress,
            PoseStack poseStack,
            MultiBufferSource.BufferSource buffer
    ) {
        float size = lerp(visual.startSize(), visual.endSize(), progress);
        float alpha = 1.0F - progress;

        VertexConsumer consumer = buffer.getBuffer(RenderType.entityTranslucent(visual.primaryTexture()));
        renderQuad(poseStack, consumer, size, alpha);
    }

    private static void renderBlasterBurnMark(
            EffectInstance effect,
            EffectVisual visual,
            float age,
            PoseStack poseStack,
            MultiBufferSource.BufferSource buffer
    ) {
        float size = visual.endSize();

        int burnTicks = 100;
        int darkenTicks = 150;
        int fadeTicks = visual.totalLifetime() - burnTicks - darkenTicks;

        ResourceLocation burnTexture = blasterBurnTexture(effect.variant);
        ResourceLocation markTexture = blasterMarkTexture(effect.variant);

        if (age <= burnTicks) {
            VertexConsumer burnConsumer = buffer.getBuffer(RenderType.entityTranslucent(burnTexture));
            renderQuad(poseStack, burnConsumer, size, 1.0F);
            return;
        }

        if (age <= burnTicks + darkenTicks) {
            float darkenProgress = (age - burnTicks) / darkenTicks;

            VertexConsumer burnConsumer = buffer.getBuffer(RenderType.entityTranslucent(burnTexture));
            renderQuad(poseStack, burnConsumer, size, 1.0F - darkenProgress);

            VertexConsumer markConsumer = buffer.getBuffer(RenderType.entityTranslucent(markTexture));
            renderQuad(poseStack, markConsumer, size, darkenProgress);

            return;
        }

        float fadeProgress = (age - burnTicks - darkenTicks) / Math.max(1, fadeTicks);
        float alpha = 1.0F - fadeProgress;

        VertexConsumer markConsumer = buffer.getBuffer(RenderType.entityTranslucent(markTexture));
        renderQuad(poseStack, markConsumer, size, alpha);
    }

    private static void renderQuad(
            PoseStack poseStack,
            VertexConsumer consumer,
            float size,
            float alpha
    ) {
        float halfSize = size * 0.5F;
        PoseStack.Pose pose = poseStack.last();

        addVertex(consumer, pose, -halfSize, -halfSize, 0.0F, 0.0F, 1.0F, alpha);
        addVertex(consumer, pose, halfSize, -halfSize, 0.0F, 1.0F, 1.0F, alpha);
        addVertex(consumer, pose, halfSize, halfSize, 0.0F, 1.0F, 0.0F, alpha);
        addVertex(consumer, pose, -halfSize, halfSize, 0.0F, 0.0F, 0.0F, alpha);
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

    private static void rotateToFaceDirection(PoseStack poseStack, Direction direction) {
        switch (direction) {
            case UP -> poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            case DOWN -> poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
            case NORTH -> {
            }
            case SOUTH -> poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            case EAST -> poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
            case WEST -> poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
        }
    }

    private static ResourceLocation blasterBurnTexture(int variant) {
        return ResourceLocation.fromNamespaceAndPath(
                OperationKnightfall.MODID,
                "textures/particle/blaster_burn_" + twoDigitVariant(variant) + ".png"
        );
    }

    private static ResourceLocation blasterMarkTexture(int variant) {
        return ResourceLocation.fromNamespaceAndPath(
                OperationKnightfall.MODID,
                "textures/particle/blaster_mark_" + twoDigitVariant(variant) + ".png"
        );
    }

    private static String twoDigitVariant(int variant) {
        int safeVariant = Math.max(1, Math.min(4, variant));
        return safeVariant < 10 ? "0" + safeVariant : String.valueOf(safeVariant);
    }

    private static float lerp(float start, float end, float progress) {
        return start + (end - start) * progress;
    }

    private record EffectVisual(
            ResourceLocation primaryTexture,
            float startSize,
            float endSize,
            int totalLifetime
    ) {
        private static EffectVisual forEffect(EffectInstance effect) {
            return switch (effect.effectType) {
                case SONIC_RIPPLE -> new EffectVisual(
                        ResourceLocation.fromNamespaceAndPath(
                                OperationKnightfall.MODID,
                                "textures/particle/sonic_bolt_ripple.png"
                        ),
                        0.2F,
                        2.8F,
                        20
                );
                case BLASTER_BURN_MARK -> new EffectVisual(
                        blasterBurnTexture(effect.variant),
                        0.3F,
                        0.3F,
                        400
                );
            };
        }
    }

    private static final class EffectInstance {
        private final Vec3 position;
        private final Direction direction;
        private final FaceAlignedParticleType effectType;
        private final int variant;
        private final float depthOffset;
        private int age;

        private EffectInstance(
                Vec3 position,
                Direction direction,
                FaceAlignedParticleType effectType,
                int variant,
                float depthOffset,
                int age
        ) {
            this.position = position;
            this.direction = direction;
            this.effectType = effectType;
            this.variant = variant;
            this.depthOffset = depthOffset;
            this.age = age;
        }
    }
}