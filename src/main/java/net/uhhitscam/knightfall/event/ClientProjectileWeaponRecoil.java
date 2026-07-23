package net.uhhitscam.knightfall.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.uhhitscam.knightfall.OperationKnightfall;

import java.util.UUID;

@EventBusSubscriber(modid = OperationKnightfall.MODID, value = Dist.CLIENT)
public final class ClientProjectileWeaponRecoil {
    private static final float MAX_RECOIL = 20.0F;
    private static final float RECOIL_DECAY = 0.8F;
    private static final float MIN_RECOIL_TO_KEEP = 0.01F;

    private static float recoil;
    private static UUID currentPlayerId;

    private ClientProjectileWeaponRecoil() {}

    public static void add(float amount) {
        recoil = Math.min(recoil + Math.max(0.0F, amount), MAX_RECOIL);
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            recoil = 0.0F;
            currentPlayerId = null;
            return;
        }

        if (!player.getUUID().equals(currentPlayerId)) {
            recoil = 0.0F;
            currentPlayerId = player.getUUID();
        }

        if (recoil < MIN_RECOIL_TO_KEEP) {
            recoil = 0.0F;
            return;
        }

        float decayedRecoil = recoil * RECOIL_DECAY;
        float recoilEffect = recoil - decayedRecoil;
        player.setXRot(Mth.clamp(player.getXRot() - recoilEffect, -90.0F, 90.0F));
        recoil = decayedRecoil;
    }

    @SubscribeEvent
    public static void onLoggingOut(ClientPlayerNetworkEvent.LoggingOut event) {
        recoil = 0.0F;
        currentPlayerId = null;
    }
}
