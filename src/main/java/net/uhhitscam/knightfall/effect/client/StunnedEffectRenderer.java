package net.uhhitscam.knightfall.effect.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StunnedEffectRenderer {
    private static final Map<UUID, Long> blueTintStartTimes = new HashMap<>();
    private static final int DURATION_OF_STUN_EFFECT = 240;
    private static final int FADE_IN_TICKS = 80;
    private static final int FULL_OPACITY_TICKS = DURATION_OF_STUN_EFFECT - FADE_IN_TICKS;
    private static final int FADE_OUT_TICKS = 100;
    private static final int TOTAL_DURATION_TICKS = FADE_IN_TICKS + FULL_OPACITY_TICKS + FADE_OUT_TICKS;

    public static void triggerBlueTint(Player player) {
        if (player != null && player.level().isClientSide) {
            blueTintStartTimes.put(player.getUUID(), Minecraft.getInstance().level.getGameTime());
        }
    }

    public static void register(IEventBus eventBus) {
        eventBus.addListener(StunnedEffectRenderer::onRenderScreen);
    }

    public static void onRenderScreen(RenderGuiLayerEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) return;

        Long startTick = blueTintStartTimes.get(player.getUUID());
        if (startTick == null) return;

        long currentTick = mc.level.getGameTime();
        long elapsed = currentTick - startTick;

        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();

        GuiGraphics guiGraphics = event.getGuiGraphics();
        RenderSystem.enableBlend();

        float progress;
        if (elapsed <= FADE_IN_TICKS) {
            // Fade in
            float fadeInProgress = elapsed / (float) FADE_IN_TICKS;
            progress = (float) Math.pow(fadeInProgress, 2); // ease-in
        } else if (elapsed <= FADE_IN_TICKS + FULL_OPACITY_TICKS) {
            // Fully opaque
            progress = 1.0f;
        } else if (elapsed <= TOTAL_DURATION_TICKS) {
            // Fade out
            float fadeOutElapsed = elapsed - FADE_IN_TICKS - FULL_OPACITY_TICKS;
            float fadeOutProgress = 1.0f - (fadeOutElapsed / (float) FADE_OUT_TICKS);
            progress = (float) Math.pow(fadeOutProgress, 2); // ease-out
        } else {
            // Done
            blueTintStartTimes.remove(player.getUUID());
            return;
        }

        float maxOpacity = 1.0f;
        int alpha = (int) (progress * maxOpacity * 255.0f);
        int blueTintColor = (alpha << 24) | 0x1b46c8;

        guiGraphics.fill(
                0, 0, screenWidth, screenHeight,
                blueTintColor
        );

        RenderSystem.disableBlend();
    }
}
