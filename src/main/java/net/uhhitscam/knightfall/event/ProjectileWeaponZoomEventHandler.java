package net.uhhitscam.knightfall.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.CalculatePlayerTurnEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.uhhitscam.knightfall.util.WeaponAimRules;

import java.util.UUID;

public final class ProjectileWeaponZoomEventHandler {
    private static final float NORMAL_FOV = 1.0F;
    private static final float ZOOM_SPEED = 0.5F;
    private static final double AIM_SENSITIVITY_MULTIPLIER = 0.75;

    private static float previousZoom = NORMAL_FOV;
    private static float currentZoom = NORMAL_FOV;
    private static UUID currentPlayerId;

    private ProjectileWeaponZoomEventHandler() {}

    public static void register(IEventBus eventBus) {
        eventBus.addListener(ProjectileWeaponZoomEventHandler::onFovModify);
        eventBus.addListener(ProjectileWeaponZoomEventHandler::onClientTick);
        eventBus.addListener(ProjectileWeaponZoomEventHandler::onCalculatePlayerTurn);
    }

    private static void onFovModify(ViewportEvent.ComputeFov event) {
        if (Minecraft.getInstance().player != null) {
            float partialTick = Mth.clamp((float) event.getPartialTick(), 0.0F, 1.0F);
            event.setFOV(event.getFOV() * Mth.lerp(partialTick, previousZoom, currentZoom));
        }
    }

    private static void onClientTick(ClientTickEvent.Post event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            previousZoom = NORMAL_FOV;
            currentZoom = NORMAL_FOV;
            currentPlayerId = null;
            return;
        }

        if (!player.getUUID().equals(currentPlayerId)) {
            previousZoom = NORMAL_FOV;
            currentZoom = NORMAL_FOV;
            currentPlayerId = player.getUUID();
        }

        float targetZoom = WeaponAimRules.getZoomFactor(player);
        previousZoom = currentZoom;
        currentZoom = Mth.lerp(ZOOM_SPEED, currentZoom, targetZoom);
    }

    private static void onCalculatePlayerTurn(CalculatePlayerTurnEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null && WeaponAimRules.isAiming(player)) {
            event.setMouseSensitivity(event.getMouseSensitivity() * currentZoom * AIM_SENSITIVITY_MULTIPLIER);
        }
    }
}
