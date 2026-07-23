package net.uhhitscam.knightfall.event;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.util.ThermalVisionUtil;
import net.uhhitscam.knightfall.util.WeaponAimRules;

@EventBusSubscriber(modid = OperationKnightfall.MODID, value = Dist.CLIENT)
public final class ProjectileWeaponHudEvents {
    private static final ResourceLocation OVAL_THERMAL_SCOPE = ResourceLocation.fromNamespaceAndPath(
            OperationKnightfall.MODID, "textures/gui/oval_long_red_scope_thermal.png"
    );
    private static final ResourceLocation CIRCLE_THERMAL_SCOPE = ResourceLocation.fromNamespaceAndPath(
            OperationKnightfall.MODID, "textures/gui/circle_red_bracket_scope_thermal.png"
    );

    private ProjectileWeaponHudEvents() {}

    @SubscribeEvent
    public static void onRenderCrosshair(RenderGuiLayerEvent.Pre event) {
        if (!event.getName().equals(VanillaGuiLayers.CROSSHAIR)) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player == null || minecraft.options.hideGui) {
            ThermalVisionUtil.setThermalActive(false);
            return;
        }

        ResourceLocation scope = WeaponAimRules.getScopeTexture(player);
        if (scope != null) {
            ThermalVisionUtil.setThermalActive(isThermalScope(scope));
            renderScope(event.getGuiGraphics(), scope);
            event.setCanceled(true);
            return;
        }

        ThermalVisionUtil.setThermalActive(false);
        ResourceLocation crosshair = WeaponAimRules.getCrosshairTexture(player);
        if (crosshair != null) {
            renderCrosshair(event.getGuiGraphics(), crosshair);
            event.setCanceled(true);
        }
    }

    private static boolean isThermalScope(ResourceLocation scope) {
        return scope.equals(OVAL_THERMAL_SCOPE) || scope.equals(CIRCLE_THERMAL_SCOPE);
    }

    private static void renderScope(GuiGraphics graphics, ResourceLocation scope) {
        int width = graphics.guiWidth();
        int height = graphics.guiHeight();
        RenderSystem.enableBlend();
        graphics.blit(scope, 0, 0, 0, 0, width, height, width, height);
        RenderSystem.disableBlend();
    }

    private static void renderCrosshair(GuiGraphics graphics, ResourceLocation crosshair) {
        int size = 25;
        int x = (graphics.guiWidth() - size) / 2;
        int y = (graphics.guiHeight() - size) / 2;

        graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(
                GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR,
                GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR,
                GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO
        );
        graphics.blit(crosshair, x, y, 0, 0, size, size, size, size);
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
