package net.uhhitscam.knightfall.item.custom.grenade;

import java.util.Objects;

public record GrenadeIonProfile(
        float droidDamageBonus,
        int flashCount,
        int flashDurationTicks,
        double flashHorizontalRadius,
        double flashMinimumHeight,
        double flashMaximumHeight,
        float flashSize,
        GrenadeIonArcProfile arcs,
        GrenadeLightningShockProfile shock
) {
    public GrenadeIonProfile {
        Objects.requireNonNull(arcs, "Ion Grenade arc profile cannot be null.");
        Objects.requireNonNull(shock, "Ion Grenade lightning shock profile cannot be null.");
        if (droidDamageBonus < 0.0F) {
            throw new IllegalArgumentException("Ion Grenade droid damage bonus cannot be negative.");
        }
        if (flashCount < 0) {
            throw new IllegalArgumentException("Ion Grenade flash count cannot be negative.");
        }
        if (flashDurationTicks < 0) {
            throw new IllegalArgumentException("Ion Grenade flash duration cannot be negative.");
        }
        if (flashCount > 0 && flashDurationTicks == 0) {
            throw new IllegalArgumentException("Ion Grenade flashes require a positive duration.");
        }
        if (!Double.isFinite(flashHorizontalRadius) || flashHorizontalRadius < 0.0) {
            throw new IllegalArgumentException("Ion Grenade flash horizontal radius must be finite and nonnegative.");
        }
        if (!Double.isFinite(flashMinimumHeight) || !Double.isFinite(flashMaximumHeight)
                || flashMinimumHeight < 0.0 || flashMaximumHeight < flashMinimumHeight) {
            throw new IllegalArgumentException("Ion Grenade flash height range must be finite and nonnegative.");
        }
        if (!Float.isFinite(flashSize) || flashSize <= 0.0F) {
            throw new IllegalArgumentException("Ion Grenade flash size must be positive and finite.");
        }
    }
}
