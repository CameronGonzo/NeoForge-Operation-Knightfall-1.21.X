package net.uhhitscam.knightfall.item.custom;

import net.minecraft.world.item.Item;

import java.util.Objects;
import java.util.function.Supplier;

public record GrenadeRemoteProfile(
        Supplier<Item> detonatorItem,
        int beepIntervalTicks,
        GrenadeDetonatorDelivery detonatorDelivery,
        boolean beepsBeforeSticking,
        boolean activationSoundOnStick,
        int remoteDetonationDelayTicks,
        GrenadeSound remoteDetonationSound
) {
    public GrenadeRemoteProfile(Supplier<Item> detonatorItem, int beepIntervalTicks) {
        this(
                detonatorItem,
                beepIntervalTicks,
                GrenadeDetonatorDelivery.ADD_TO_INVENTORY,
                true,
                false,
                0,
                null
        );
    }

    public GrenadeRemoteProfile(
            Supplier<Item> detonatorItem,
            int beepIntervalTicks,
            GrenadeDetonatorDelivery detonatorDelivery
    ) {
        this(detonatorItem, beepIntervalTicks, detonatorDelivery, true, false, 0, null);
    }

    public GrenadeRemoteProfile {
        Objects.requireNonNull(detonatorItem, "Remote grenade detonator supplier cannot be null.");
        Objects.requireNonNull(detonatorDelivery, "Remote grenade detonator delivery cannot be null.");
        if (beepIntervalTicks < 0) {
            throw new IllegalArgumentException("Remote grenade beep interval cannot be negative.");
        }
        if (remoteDetonationDelayTicks < 0) {
            throw new IllegalArgumentException("Remote grenade detonation delay cannot be negative.");
        }
        if (remoteDetonationDelayTicks > 0 && remoteDetonationSound == null) {
            throw new IllegalArgumentException("A delayed remote detonation requires a countdown sound.");
        }
    }

    public boolean beepsWhileDeployed() {
        return beepIntervalTicks > 0;
    }
}
