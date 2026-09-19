package net.uhhitscam.knightfall.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.neoforged.neoforge.client.event.ExtractLevelRenderStateEvent;
import net.neoforged.neoforge.client.event.SubmitCustomGeometryEvent;
import net.minecraft.util.context.ContextKey;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
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
        eventBus.addListener(FaceAlignedParticleClient::extract);
        eventBus.addListener(FaceAlignedParticleClient::submit);
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

    private static final ContextKey<List<EffectInstance>> RENDER_EFFECTS = new ContextKey<>(
            Identifier.fromNamespaceAndPath("knightfall", "impact_marks"));
    private static final ContextKey<Float> PARTIAL_TICK = new ContextKey<>(
            Identifier.fromNamespaceAndPath("knightfall", "impact_partial_tick"));

    public static void extract(ExtractLevelRenderStateEvent event) {
        List<EffectInstance> snapshots = new ArrayList<>();
        Iterator<EffectInstance> iterator = EFFECTS.iterator();
        while (iterator.hasNext()) {
            EffectInstance effect = iterator.next();
            if (effect.age >= EffectVisual.forEffect(effect).totalLifetime()) {
                iterator.remove();
            } else {
                snapshots.add(new EffectInstance(effect.position, effect.direction, effect.effectType,
                        effect.variant, effect.depthOffset, effect.age));
                effect.age++;
            }
        }
        event.getRenderState().setRenderData(RENDER_EFFECTS, List.copyOf(snapshots));
        event.getRenderState().setRenderData(PARTIAL_TICK, event.getDeltaTracker().getGameTimeDeltaPartialTick(false));
    }

    public static void submit(SubmitCustomGeometryEvent event) {
        List<EffectInstance> effects = event.getLevelRenderState().getRenderData(RENDER_EFFECTS);
        if (effects == null) return;
        Float partialTick = event.getLevelRenderState().getRenderData(PARTIAL_TICK);
        for (EffectInstance effect : effects) {
            renderEffect(effect, EffectVisual.forEffect(effect), partialTick == null ? 0 : partialTick,
                    event.getLevelRenderState().cameraRenderState.pos, event.getPoseStack(), event.getSubmitNodeCollector());
        }
    }

    private static void renderEffect(
            EffectInstance effect,
            EffectVisual visual,
            float partialTick,
            Vec3 cameraPosition,
            PoseStack poseStack,
            SubmitNodeCollector buffer
    ) {
        float age = effect.age + partialTick;
        float progress = age / visual.totalLifetime();

        Vec3 renderPosition = effect.position.add(
                effect.direction.getStepX() * effect.depthOffset,
                effect.direction.getStepY() * effect.depthOffset,
                effect.direction.getStepZ() * effect.depthOffset
        );
        double renderX = renderPosition.x - cameraPosition.x;
        double renderY = renderPosition.y - cameraPosition.y;
        double renderZ = renderPosition.z - cameraPosition.z;

        poseStack.pushPose();
        poseStack.translate(renderX, renderY, renderZ);
        rotateToFaceDirection(poseStack, effect.direction);

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
            SubmitNodeCollector buffer
    ) {
        float size = lerp(visual.startSize(), visual.endSize(), progress);
        float alpha = 1.0F - progress;

        buffer.submitCustomGeometry(poseStack, RenderTypes.entityTranslucent(visual.primaryTexture()), (pose, vertices) -> renderQuad(pose, vertices, size, alpha));
    }

    private static void renderBlasterBurnMark(
            EffectInstance effect,
            EffectVisual visual,
            float age,
            PoseStack poseStack,
            SubmitNodeCollector buffer
    ) {
        float size = visual.endSize();

        int burnTicks = 100;
        int darkenTicks = 150;
        int fadeTicks = visual.totalLifetime() - burnTicks - darkenTicks;

        Identifier burnTexture = blasterBurnTexture(effect.variant);
        Identifier markTexture = blasterMarkTexture(effect.variant);

        if (age <= burnTicks) {
            buffer.submitCustomGeometry(poseStack, RenderTypes.entityTranslucent(burnTexture), (pose, vertices) -> renderQuad(pose, vertices, size, 1.0F));
            return;
        }

        if (age <= burnTicks + darkenTicks) {
            float darkenProgress = (age - burnTicks) / darkenTicks;

            buffer.submitCustomGeometry(poseStack, RenderTypes.entityTranslucent(burnTexture), (pose, vertices) -> renderQuad(pose, vertices, size, 1.0F - darkenProgress));

            buffer.submitCustomGeometry(poseStack, RenderTypes.entityTranslucent(markTexture), (pose, vertices) -> renderQuad(pose, vertices, size, darkenProgress));

            return;
        }

        float fadeProgress = (age - burnTicks - darkenTicks) / Math.max(1, fadeTicks);
        float alpha = 1.0F - fadeProgress;

        buffer.submitCustomGeometry(poseStack, RenderTypes.entityTranslucent(markTexture), (pose, vertices) -> renderQuad(pose, vertices, size, alpha));
    }

    private static void renderQuad(
            PoseStack.Pose pose,
            VertexConsumer consumer,
            float size,
            float alpha
    ) {
        float halfSize = size * 0.5F;

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

    private static Identifier blasterBurnTexture(int variant) {
        return Identifier.fromNamespaceAndPath(
                OperationKnightfall.MODID,
                "textures/particle/blaster_burn_" + twoDigitVariant(variant) + ".png"
        );
    }

    private static Identifier blasterMarkTexture(int variant) {
        return Identifier.fromNamespaceAndPath(
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
            Identifier primaryTexture,
            float startSize,
            float endSize,
            int totalLifetime
    ) {
        private static EffectVisual forEffect(EffectInstance effect) {
            return switch (effect.effectType) {
                case SONIC_RIPPLE -> new EffectVisual(
                        Identifier.fromNamespaceAndPath(
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
