package net.uhhitscam.knightfall.item.custom;

public record GrenadePhysics(
        double gravity,
        double normalRestitution,
        double tangentialRetention,
        double minimumBounceSpeed
) {
    public GrenadePhysics {
        if (gravity < 0.0) {
            throw new IllegalArgumentException("Grenade gravity cannot be negative.");
        }
        if (normalRestitution < 0.0 || normalRestitution > 1.0) {
            throw new IllegalArgumentException("Grenade normal restitution must be between 0 and 1.");
        }
        if (tangentialRetention < 0.0 || tangentialRetention > 1.0) {
            throw new IllegalArgumentException("Grenade tangential retention must be between 0 and 1.");
        }
        if (minimumBounceSpeed < 0.0) {
            throw new IllegalArgumentException("Grenade minimum bounce speed cannot be negative.");
        }
    }

    public static GrenadePhysics defaults() {
        return new GrenadePhysics(0.03, 0.45, 0.8, 0.08);
    }
}
