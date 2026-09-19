package net.uhhitscam.knightfall.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.uhhitscam.knightfall.util.BlurRequests;

@EventBusSubscriber(modid = "knightfall", value = net.neoforged.api.distmarker.Dist.CLIENT)
public final class BlurEffectEventHandler {


    private static int ageTicks = 0;
    private static int holdTicks = 0;
    private static int fadeOutTicks = 0;
    private static float maxRadius = 0f;

    private BlurEffectEventHandler() {}

    public static void trigger(int hold, int fadeOut, float radius) {
        holdTicks = Math.max(0, hold);
        fadeOutTicks = Math.max(1, fadeOut);
        maxRadius = Math.max(0f, radius);
        ageTicks = 0;
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) {
            stop(mc);
            return;
        }

        float bestIntensity = 0f;
        int bestHold = 0;
        int bestFade = 0;

        BlurRequests.Request req;
        while ((req = BlurRequests.poll()) != null) {
            Vec3 impact = req.impactPos();

            Vec3 eye = mc.player.getEyePosition();
            double dist = eye.distanceTo(impact);

            float r = req.effectRadiusBlocks();
            if (dist <= r) {
                float t = 1.0f - (float)(dist / r);
                t = clamp01(t);

                float intensity = req.maxShaderRadius() * (t * t);

                if (intensity > bestIntensity) {
                    bestIntensity = intensity;
                    bestHold = req.holdTicks();
                    bestFade = req.fadeOutTicks();
                }
            }
        }

        if (bestIntensity > maxRadius) {
            trigger(bestHold, bestFade, bestIntensity);
        }

        ageTicks++;

        float radiusNow;
        if (ageTicks <= holdTicks) {
            radiusNow = maxRadius;
        } else {
            float t = (ageTicks - holdTicks) / (float) fadeOutTicks; // 0..1+
            t = clamp01(t);

            float k = 1.0f - t;
            radiusNow = maxRadius * (k * k);
        }

        if (radiusNow > 0.001f) {
            apply(mc, radiusNow);
        } else {
            stop(mc);
            ageTicks = 0;
            holdTicks = 0;
            fadeOutTicks = 0;
            maxRadius = 0f;
        }
    }

    private static float activeRadius;

    private static void apply(Minecraft mc, float radiusNow) {
        activeRadius = radiusNow;
    }

    private static void stop(Minecraft mc) {
        activeRadius = 0;
    }

    @SubscribeEvent
    public static void extractBlur(net.neoforged.neoforge.client.event.RenderGuiEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        if (activeRadius > 0.001F && mc.level != null && mc.gui.screen() == null) {
            mc.gameRenderer.gameRenderState().optionsRenderState.menuBackgroundBlurriness =
                    Math.max(1, Math.round(activeRadius));
            event.getGuiGraphics().blurBeforeThisStratum();
        }
    }

    private static float clamp01(float v) {
        return v < 0f ? 0f : (v > 1f ? 1f : v);
    }
}