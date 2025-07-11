package net.uhhitscam.starwars.effect.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.uhhitscam.starwars.OperationKnightfall;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StunEffectRenderer {
    private static final ResourceLocation STUN_OVERLAY = ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/stun_hit_effect.png");

    private static final int FRAME_HEIGHT = 16;
    private static final int FRAME_COUNT = 16;
    private static final int FRAME_DURATION_TICKS = 2;

    private static final Map<UUID, Long> stunStartTimes = new HashMap<>();
    private static final int ANIMATION_DURATION_TICKS = FRAME_COUNT * FRAME_DURATION_TICKS;

    public static void register(IEventBus eventBus) {
        eventBus.addListener(StunEffectRenderer::onRenderScreen);
    }

    public static void triggerStunAnimation(Player player) {
        if (player != null && player.level().isClientSide) {
            stunStartTimes.put(player.getUUID(), Minecraft.getInstance().level.getGameTime());
        }
    }

    public static void onRenderScreen(RenderGuiLayerEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) return;

        Long startTick = stunStartTimes.get(player.getUUID());
        if (startTick == null) return;

        long currentTick = mc.level.getGameTime();
        long elapsed = currentTick - startTick;

        if (elapsed >= ANIMATION_DURATION_TICKS) {
            stunStartTimes.remove(player.getUUID());
            return;
        }

        int currentFrame = (int) (elapsed / FRAME_DURATION_TICKS);
        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();
        int frameY = currentFrame * screenHeight;


        GuiGraphics guiGraphics = event.getGuiGraphics();
        RenderSystem.enableBlend();

        guiGraphics.blit(
                STUN_OVERLAY,
                0,
                0,
                0,
                (float) 0,
                (float) frameY,
                screenWidth,
                screenHeight,
                screenWidth,
                screenHeight * FRAME_HEIGHT
        );

        RenderSystem.disableBlend();
    }
}