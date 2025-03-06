package net.uhhitscam.starwars.effect.client;

import com.mojang.blaze3d.shaders.Effect;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.uhhitscam.starwars.effect.ModEffects;

public class StunEffectRenderer {
    public static void registerClientEvents(IEventBus eventBus) {
        eventBus.addListener(StunEffectRenderer::onRenderScreen);
    }

    private static void onRenderScreen(RenderGuiLayerEvent.Post event) {
        LocalPlayer player = Minecraft.getInstance().player;

        if (player != null && player.hasEffect(ModEffects.STUN_EFFECT)) {
            RenderSystem.enableBlend();
            RenderSystem.setShaderColor(0f, 0f, 0f, 0.5f); // Black overlay with 50% opacity

            GuiGraphics guiGraphics = event.getGuiGraphics();
            int width = Minecraft.getInstance().getWindow().getGuiScaledWidth();
            int height = Minecraft.getInstance().getWindow().getGuiScaledHeight();

            guiGraphics.fill(0, 0, width, height, 0x80000000); // Dark overlay
            RenderSystem.disableBlend();
        }
    }
}