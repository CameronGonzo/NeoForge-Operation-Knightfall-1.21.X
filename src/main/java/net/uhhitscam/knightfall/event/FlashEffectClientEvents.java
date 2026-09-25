package net.uhhitscam.knightfall.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.util.FlashRequests;

@EventBusSubscriber(modid = OperationKnightfall.MODID, value = Dist.CLIENT)
public final class FlashEffectClientEvents {
    private static final int WHITE_RGB = 0xFFFFFF;

    private static int ageTicks;
    private static int fullWhiteTicks;
    private static int fadeOutTicks;
    private static boolean active;

    private FlashEffectClientEvents() {
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null || minecraft.player == null) {
            reset();
            drainRequests();
            return;
        }

        FlashRequests.Request request;
        while ((request = FlashRequests.poll()) != null) {
            trigger(request.fullWhiteTicks(), request.fadeOutTicks());
        }

        if (active && ++ageTicks >= fullWhiteTicks + fadeOutTicks) {
            reset();
        }
    }

    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (!active || minecraft.player == null || minecraft.gui.screen() != null) {
            return;
        }

        float alphaFactor = 1.0F;
        if (ageTicks > fullWhiteTicks) {
            float fadeProgress = (ageTicks - fullWhiteTicks) / (float) fadeOutTicks;
            alphaFactor = 1.0F - Mth.clamp(fadeProgress, 0.0F, 1.0F);
        }

        int alpha = Mth.clamp(Math.round(alphaFactor * 255.0F), 0, 255);
        if (alpha == 0) {
            return;
        }

        GuiGraphicsExtractor graphics = event.getGuiGraphics();
        graphics.fill(
                0,
                0,
                minecraft.getWindow().getGuiScaledWidth(),
                minecraft.getWindow().getGuiScaledHeight(),
                alpha << 24 | WHITE_RGB
        );
    }

    private static void trigger(int holdTicks, int fadeTicks) {
        fullWhiteTicks = Math.max(0, holdTicks);
        fadeOutTicks = Math.max(1, fadeTicks);
        ageTicks = 0;
        active = true;
    }

    private static void reset() {
        ageTicks = 0;
        fullWhiteTicks = 0;
        fadeOutTicks = 0;
        active = false;
    }

    private static void drainRequests() {
        while (FlashRequests.poll() != null) {
        }
    }
}
