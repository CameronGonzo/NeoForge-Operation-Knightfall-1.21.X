package net.uhhitscam.knightfall.item.custom.grenade;

public record GrenadeProximityProfile(double radius, int armingTicks) {
    public GrenadeProximityProfile {
        if (!Double.isFinite(radius) || radius <= 0.0) {
            throw new IllegalArgumentException("Grenade proximity radius must be positive and finite.");
        }
        if (armingTicks < 0) {
            throw new IllegalArgumentException("Grenade proximity arming ticks cannot be negative.");
        }
    }
}
