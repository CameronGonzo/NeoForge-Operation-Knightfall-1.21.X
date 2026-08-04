package net.uhhitscam.knightfall.effect.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.effect.ModEffects;

import java.util.UUID;

public final class StunEffectRenderer {
    private static final ResourceLocation STUN_HIT_TEXTURE = ResourceLocation.fromNamespaceAndPath(
            OperationKnightfall.MODID,
            "textures/gui/stun_hit_effect.png"
    );
    private static final int TEXTURE_WIDTH = 16;
    private static final int FRAME_SIZE = 16;
    private static final int FRAME_COUNT = 16;
    private static final int TEXTURE_HEIGHT = FRAME_SIZE * FRAME_COUNT;
    private static final int TICKS_PER_FRAME = 2;
    private static final int BLUE_FADE_IN_TICKS = 80;
    private static final int BLUE_TINT_RGB = 0x1B46C8;

    private static UUID trackedPlayer;
    private static long effectStartTick;
    private static int previousRemainingTicks = -1;

    private StunEffectRenderer() {}

    public static void register(IEventBus eventBus) {
        eventBus.addListener(StunEffectRenderer::onRenderGui);
    }

    private static void onRenderGui(RenderGuiEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        MobEffectInstance stunEffect = player == null ? null : player.getEffect(ModEffects.STUN_EFFECT);
        if (stunEffect == null) {
            reset();
            return;
        }

        long currentTick = player.level().getGameTime();
        int remainingTicks = stunEffect.getDuration();
        if (!player.getUUID().equals(trackedPlayer)
                || currentTick < effectStartTick
                || remainingTicks > previousRemainingTicks) {
            trackedPlayer = player.getUUID();
            effectStartTick = currentTick;
        }
        previousRemainingTicks = remainingTicks;

        int elapsedTicks = (int) Math.max(0L, currentTick - effectStartTick);
        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();

        GuiGraphics guiGraphics = event.getGuiGraphics();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        renderHitAnimation(guiGraphics, screenWidth, screenHeight, elapsedTicks);
        renderBlueTint(guiGraphics, screenWidth, screenHeight, elapsedTicks);

        RenderSystem.disableBlend();
    }

    private static void renderHitAnimation(
            GuiGraphics guiGraphics,
            int screenWidth,
            int screenHeight,
            int elapsedTicks
    ) {
        int frame = elapsedTicks / TICKS_PER_FRAME;
        if (frame >= FRAME_COUNT) {
            return;
        }

        guiGraphics.blit(
                STUN_HIT_TEXTURE,
                0,
                0,
                screenWidth,
                screenHeight,
                0.0F,
                (float) (frame * FRAME_SIZE),
                FRAME_SIZE,
                FRAME_SIZE,
                TEXTURE_WIDTH,
                TEXTURE_HEIGHT
        );
    }

    private static void renderBlueTint(
            GuiGraphics guiGraphics,
            int screenWidth,
            int screenHeight,
            int elapsedTicks
    ) {
        float fadeProgress = Math.min(elapsedTicks / (float) BLUE_FADE_IN_TICKS, 1.0F);
        int alpha = Math.round(fadeProgress * fadeProgress * 255.0F);
        if (alpha == 0) {
            return;
        }

        guiGraphics.fill(0, 0, screenWidth, screenHeight, alpha << 24 | BLUE_TINT_RGB);
    }

    private static void reset() {
        trackedPlayer = null;
        previousRemainingTicks = -1;
    }
}
