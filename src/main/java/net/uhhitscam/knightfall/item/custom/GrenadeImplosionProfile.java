package net.uhhitscam.knightfall.item.custom;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public record GrenadeImplosionProfile(
        int durationTicks,
        double radius,
        double pullStrength,
        GrenadeSound startSound,
        @Nullable GrenadeImplosionParticleProfile particles
) {
    public GrenadeImplosionProfile {
        if (durationTicks <= 0) {
            throw new IllegalArgumentException("Grenade implosion duration must be greater than 0.");
        }
        if (!Double.isFinite(radius) || radius <= 0.0) {
            throw new IllegalArgumentException("Grenade implosion radius must be positive and finite.");
        }
        if (!Double.isFinite(pullStrength) || pullStrength <= 0.0) {
            throw new IllegalArgumentException("Grenade implosion pull strength must be positive and finite.");
        }
        Objects.requireNonNull(startSound, "Grenade implosion start sound cannot be null.");
    }

    public GrenadeImplosionProfile(
            int durationTicks,
            double radius,
            double pullStrength,
            GrenadeSound startSound
    ) {
        this(durationTicks, radius, pullStrength, startSound, null);
    }
}
