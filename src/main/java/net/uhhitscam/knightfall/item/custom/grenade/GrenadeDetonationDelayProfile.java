package net.uhhitscam.knightfall.item.custom.grenade;

import java.util.Objects;

public record GrenadeDetonationDelayProfile(int durationTicks, GrenadeSound startSound) {
    public GrenadeDetonationDelayProfile {
        if (durationTicks <= 0) {
            throw new IllegalArgumentException("Grenade detonation delay must be greater than 0.");
        }
        Objects.requireNonNull(startSound, "Grenade detonation delay sound cannot be null.");
    }
}
