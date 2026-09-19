package net.uhhitscam.knightfall.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.Identifier;
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
    private static final Identifier OVAL_THERMAL_SCOPE = Identifier.fromNamespaceAndPath(
            OperationKnightfall.MODID, "textures/gui/oval_long_red_scope_thermal.png"
    );
    private static final Identifier CIRCLE_THERMAL_SCOPE = Identifier.fromNamespaceAndPath(
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
        if (player == null || minecraft.gui.hud.isHidden()) {
            ThermalVisionUtil.setThermalActive(false);
            return;
        }

        Identifier scope = WeaponAimRules.getScopeTexture(player);
        if (scope != null) {
            ThermalVisionUtil.setThermalActive(isThermalScope(scope));
            renderScope(event.getGuiGraphics(), scope);
            event.setCanceled(true);
            return;
        }

        ThermalVisionUtil.setThermalActive(false);
        Identifier crosshair = WeaponAimRules.getCrosshairTexture(player);
        if (crosshair != null) {
            renderCrosshair(event.getGuiGraphics(), crosshair);
            event.setCanceled(true);
        }
    }

    private static boolean isThermalScope(Identifier scope) {
        return scope.equals(OVAL_THERMAL_SCOPE) || scope.equals(CIRCLE_THERMAL_SCOPE);
    }

    private static void renderScope(GuiGraphicsExtractor graphics, Identifier scope) {
        int width = graphics.guiWidth();
        int height = graphics.guiHeight();
        graphics.blit(net.minecraft.client.renderer.RenderPipelines.GUI_TEXTURED, scope, 0, 0, 0, 0, width, height, width, height);
    }

    private static void renderCrosshair(GuiGraphicsExtractor graphics, Identifier crosshair) {
        int size = 25;
        int x = (graphics.guiWidth() - size) / 2;
        int y = (graphics.guiHeight() - size) / 2;

        graphics.blit(net.minecraft.client.renderer.RenderPipelines.CROSSHAIR, crosshair, x, y, 0, 0, size, size, size, size);
    }
}
