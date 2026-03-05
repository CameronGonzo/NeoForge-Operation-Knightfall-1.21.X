package net.uhhitscam.knightfall.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.uhhitscam.knightfall.item.custom.ProjectileItem;
import net.uhhitscam.knightfall.util.WeaponZoomUtil;

import java.util.UUID;
import java.util.WeakHashMap;

import static net.minecraft.util.Mth.lerp;

public class ProjectileWeaponZoomEventHandler {
    private static final WeakHashMap<UUID, Float> zoomLevels = new WeakHashMap<>();
    private static final float NORMAL_FOV = 1.0f;
    private static final float ZOOM_SPEED = 0.1f;
    private static Double baseSensitivity = null;
    private static boolean wasZooming = false;

    public static void register(IEventBus eventBus) {
        eventBus.addListener(ProjectileWeaponZoomEventHandler::onFovModify);
        eventBus.addListener(ProjectileWeaponZoomEventHandler::onClientTick);
    }

    @SubscribeEvent
    public static void onFovModify(ViewportEvent.ComputeFov event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        float ZOOMED_FOV = WeaponZoomUtil.getProjectileWeaponZoomFactor(player);

        UUID playerId = player.getUUID();
        float currentZoom = zoomLevels.getOrDefault(playerId, NORMAL_FOV);

        boolean isZooming = isZooming(player);
        float targetZoom = isZooming ? ZOOMED_FOV : NORMAL_FOV;

        float newZoom = lerp(ZOOM_SPEED, currentZoom, targetZoom);
        zoomLevels.put(playerId, newZoom);

        event.setFOV(event.getFOV() * newZoom);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) return;

        boolean zooming = isZooming(player);
        float zoomFactor = zoomLevels.getOrDefault(player.getUUID(), NORMAL_FOV);
        double currentSensitivity = mc.options.sensitivity().get();

        if (zooming && !wasZooming) {
            baseSensitivity = currentSensitivity;
        }

        if (zooming) {
            double newSensitivity = baseSensitivity * zoomFactor * 0.75;
            mc.options.sensitivity().set(newSensitivity);
        }

        if (!zooming && wasZooming && baseSensitivity != null) {
            mc.options.sensitivity().set(baseSensitivity);
        }

        if (!zooming && !wasZooming) {
            baseSensitivity = currentSensitivity;
        }

        wasZooming = zooming;
    }

    private static boolean isZooming(LocalPlayer player) {
        return ((player.getMainHandItem().getItem() instanceof ProjectileItem
                || player.getOffhandItem().getItem() instanceof ProjectileItem)
                && player.isShiftKeyDown());
    }

    private static float lerp(float delta, float start, float end) {
        return start + delta * (end - start);
    }
}