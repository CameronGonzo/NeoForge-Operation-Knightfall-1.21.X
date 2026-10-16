package net.uhhitscam.knightfall.item.custom.grenade;

public record GrenadePressureProfile(int requiredItemTypes, double detectionHeight) {
    public GrenadePressureProfile {
        if (requiredItemTypes <= 0 || !Double.isFinite(detectionHeight) || detectionHeight <= 0.0) {
            throw new IllegalArgumentException("Pressure mine item threshold and detection height must be positive.");
        }
    }
}
