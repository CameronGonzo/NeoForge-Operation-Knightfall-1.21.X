package net.uhhitscam.knightfall.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ExtractLevelRenderStateEvent;
import net.neoforged.neoforge.client.event.SubmitCustomGeometryEvent;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.item.custom.grenade.GrenadeIonArcProfile;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class IonArcClient {
    private static final Identifier CORE_TEXTURE = Identifier.fromNamespaceAndPath(
            OperationKnightfall.MODID, "textures/particle/ion_arc_core.png");
    private static final Identifier EXTERIOR_TEXTURE = Identifier.fromNamespaceAndPath(
            OperationKnightfall.MODID, "textures/particle/ion_arc_exterior.png");
    private static final ContextKey<List<ArcSnapshot>> RENDER_ARCS = new ContextKey<>(
            Identifier.fromNamespaceAndPath(OperationKnightfall.MODID, "ion_arc_render"));
    private static final List<ArcInstance> ARCS = new ArrayList<>();

    private IonArcClient() {
    }

    public static void register(IEventBus eventBus) {
        eventBus.addListener(IonArcClient::extract);
        eventBus.addListener(IonArcClient::submit);
    }

    public static void add(Vec3 origin, long seed, int windowTicks, GrenadeIonArcProfile profile) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null || windowTicks <= 0 || profile.count() == 0) {
            return;
        }

        RandomSource random = RandomSource.create(seed);
        Set<Integer> usedDelays = new HashSet<>();
        long startTime = level.getGameTime();
        for (int i = 0; i < profile.count(); i++) {
            int delay;
            do {
                delay = random.nextInt(windowTicks);
            } while (usedDelays.size() < windowTicks && usedDelays.contains(delay));
            usedDelays.add(delay);

            int segmentCount = profile.minimumSegments()
                    + random.nextInt(profile.maximumSegments() - profile.minimumSegments() + 1);
            ARCS.add(new ArcInstance(level, buildPath(origin, segmentCount, profile, random),
                    startTime + delay, profile));
        }
    }

    private static List<Vec3> buildPath(Vec3 origin, int segmentCount, GrenadeIonArcProfile profile,
                                        RandomSource random) {
        List<Vec3> points = new ArrayList<>(segmentCount + 1);
        points.add(origin);

        double angle = random.nextDouble() * Math.PI * 2.0;
        Vec3 direction = new Vec3(Math.cos(angle), 0.15 + random.nextDouble() * 0.65,
                Math.sin(angle)).normalize();
        Vec3 point = origin;
        for (int i = 0; i < segmentCount; i++) {
            Vec3 bend = new Vec3(
                    (random.nextDouble() * 2.0 - 1.0) * profile.bend(),
                    (random.nextDouble() * 2.0 - 1.0) * profile.bend(),
                    (random.nextDouble() * 2.0 - 1.0) * profile.bend());
            Vec3 nextDirection = direction.add(bend);
            if (nextDirection.lengthSqr() > 0.001) {
                direction = nextDirection.normalize();
            }
            point = point.add(direction.scale(profile.segmentLength()));
            points.add(point);
        }
        return List.copyOf(points);
    }

    private static void extract(ExtractLevelRenderStateEvent event) {
        ClientLevel level = Minecraft.getInstance().level;
        List<ArcSnapshot> snapshots = new ArrayList<>();
        Iterator<ArcInstance> iterator = ARCS.iterator();
        while (iterator.hasNext()) {
            ArcInstance arc = iterator.next();
            if (level == null || arc.level != level || level.getGameTime() >= arc.startTime + arc.profile.lifetimeTicks()) {
                iterator.remove();
            } else if (level.getGameTime() >= arc.startTime) {
                snapshots.add(new ArcSnapshot(arc.points, arc.profile));
            }
        }
        event.getRenderState().setRenderData(RENDER_ARCS, List.copyOf(snapshots));
    }

    private static void submit(SubmitCustomGeometryEvent event) {
        List<ArcSnapshot> arcs = event.getLevelRenderState().getRenderData(RENDER_ARCS);
        if (arcs == null || arcs.isEmpty()) {
            return;
        }
        Vec3 camera = event.getLevelRenderState().cameraRenderState.pos;
        for (ArcSnapshot arc : arcs) {
            int exteriorColor = 0xFF000000 | arc.profile.exteriorColor();
            int coreColor = 0xFFFFFFFF;

            for (int i = 1; i < arc.points.size(); i++) {
                renderSegment(arc.points.get(i - 1), arc.points.get(i), camera,
                        arc.profile, exteriorColor, coreColor,
                        event.getPoseStack(), event.getSubmitNodeCollector());
            }
        }
    }

    private static void renderSegment(Vec3 start, Vec3 end, Vec3 camera,
                                      GrenadeIonArcProfile profile, int exteriorColor, int coreColor,
                                      PoseStack poseStack, SubmitNodeCollector buffer) {
        Vec3 segment = end.subtract(start);
        float length = (float) segment.length();
        if (length < 0.001F) {
            return;
        }

        poseStack.pushPose();
        poseStack.translate(start.x - camera.x, start.y - camera.y, start.z - camera.z);
        poseStack.mulPose(new Quaternionf().rotateTo(new Vector3f(0.0F, 1.0F, 0.0F),
                new Vector3f((float) (segment.x / length), (float) (segment.y / length),
                        (float) (segment.z / length))));

        buffer.submitCustomGeometry(poseStack, RenderTypes.entityCutout(CORE_TEXTURE),
                (pose, vertices) -> renderTube(pose, vertices, coreColor, length,
                        profile.coreRadius()));
        buffer.submitCustomGeometry(poseStack, RenderTypes.entityTranslucentEmissive(CORE_TEXTURE),
                (pose, vertices) -> renderTube(pose, vertices, coreColor, length,
                        profile.coreRadius()));
        buffer.submitCustomGeometry(poseStack, RenderTypes.entityTranslucentEmissive(EXTERIOR_TEXTURE),
                (pose, vertices) -> renderTube(pose, vertices, exteriorColor, length,
                        profile.exteriorRadius()));
        poseStack.popPose();
    }

    private static void renderTube(PoseStack.Pose pose, VertexConsumer vertices,
                                   int color, float length, float radius) {
        float start = -radius;
        float end = length + radius;
        quad(pose, vertices, color, start, end, radius, 0.0F, 0.0F, -radius,
                0.7071F, 0.0F, -0.7071F);
        quad(pose, vertices, color, start, end, 0.0F, -radius, -radius, 0.0F,
                -0.7071F, 0.0F, -0.7071F);
        quad(pose, vertices, color, start, end, -radius, 0.0F, 0.0F, radius,
                -0.7071F, 0.0F, 0.7071F);
        quad(pose, vertices, color, start, end, 0.0F, radius, radius, 0.0F,
                0.7071F, 0.0F, 0.7071F);
    }

    private static void quad(PoseStack.Pose pose, VertexConsumer vertices, int color,
                             float start, float end, float x0, float z0, float x1, float z1,
                             float nx, float ny, float nz) {
        vertex(pose, vertices, color, x0, end, z0, 0.0F, 0.0F, nx, ny, nz);
        vertex(pose, vertices, color, x0, start, z0, 0.0F, 1.0F, nx, ny, nz);
        vertex(pose, vertices, color, x1, start, z1, 1.0F, 1.0F, nx, ny, nz);
        vertex(pose, vertices, color, x1, end, z1, 1.0F, 0.0F, nx, ny, nz);
    }

    private static void vertex(PoseStack.Pose pose, VertexConsumer vertices, int color,
                               float x, float y, float z, float u, float v,
                               float nx, float ny, float nz) {
        vertices.addVertex(pose, x, y, z)
                .setColor(color)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(0xF000F0)
                .setNormal(pose, nx, ny, nz);
    }

    private record ArcInstance(ClientLevel level, List<Vec3> points, long startTime,
                               GrenadeIonArcProfile profile) {
    }

    private record ArcSnapshot(List<Vec3> points, GrenadeIonArcProfile profile) {
    }
}
