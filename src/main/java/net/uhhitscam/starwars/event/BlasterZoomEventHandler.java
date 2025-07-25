package net.uhhitscam.starwars.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.uhhitscam.starwars.item.custom.BlasterItem;
import net.uhhitscam.starwars.util.BlasterZoomUtil;

import java.util.UUID;
import java.util.WeakHashMap;

import static net.minecraft.util.Mth.lerp;

public class BlasterZoomEventHandler {
    private static final WeakHashMap<UUID, Float> zoomLevels = new WeakHashMap<>();
    private static final float NORMAL_FOV = 1.0f;
    private static final float ZOOM_SPEED = 0.1f;

    public static void register(IEventBus eventBus) {
        eventBus.addListener(BlasterZoomEventHandler::onFovModify);
    }

    @SubscribeEvent
    public static void onFovModify(ViewportEvent.ComputeFov event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        float ZOOMED_FOV = BlasterZoomUtil.getBlasterZoomFactor(player);

        UUID playerId = player.getUUID();
        float currentZoom = zoomLevels.getOrDefault(playerId, NORMAL_FOV);

        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand = player.getOffhandItem();
        boolean shouldZoom = (mainHand.getItem() instanceof BlasterItem || offHand.getItem() instanceof BlasterItem) && player.isShiftKeyDown();

        float targetZoom = shouldZoom ? ZOOMED_FOV : NORMAL_FOV;
        float newZoom = lerp(ZOOM_SPEED, currentZoom, targetZoom);

        zoomLevels.put(playerId, newZoom);
        event.setFOV(event.getFOV() * newZoom);
    }
}