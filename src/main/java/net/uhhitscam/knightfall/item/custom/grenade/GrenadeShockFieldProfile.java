package net.uhhitscam.knightfall.item.custom.grenade;

import java.util.Objects;

public record GrenadeShockFieldProfile(
        double radius,
        int durationTicks,
        int shockLingerTicks,
        int arcIntervalTicks,
        GrenadeIonArcProfile arcs,
        GrenadeSound startSound
) {
    public GrenadeShockFieldProfile {
        Objects.requireNonNull(arcs, "Shock field arcs cannot be null.");
        Objects.requireNonNull(startSound, "Shock field sound cannot be null.");
        if (!Double.isFinite(radius) || radius <= 0.0 || durationTicks <= 0 || shockLingerTicks <= 0
                || arcIntervalTicks <= 0 || arcIntervalTicks > arcs.lifetimeTicks()) {
            throw new IllegalArgumentException("Shock field radius, duration, linger time, or arc interval is invalid.");
        }
    }
}
