package net.uhhitscam.knightfall.item.custom.grenade;

public record GrenadeFlashProfile(
        double radius,
        double viewingRadius,
        double minimumViewDot,
        int fullWhiteTicks,
        int fadeOutTicks,
        int mobTargetSuppressionTicks
) {
    public GrenadeFlashProfile {
        if (radius <= 0.0) {
            throw new IllegalArgumentException("Flash Grenade radius must be greater than 0.");
        }
        if (viewingRadius < radius) {
            throw new IllegalArgumentException("Flash Grenade viewing radius cannot be smaller than its inner radius.");
        }
        if (minimumViewDot < -1.0 || minimumViewDot > 1.0) {
            throw new IllegalArgumentException("Flash Grenade minimum view dot must be between -1 and 1.");
        }
        if (fullWhiteTicks < 0) {
            throw new IllegalArgumentException("Flash Grenade full-white time cannot be negative.");
        }
        if (fadeOutTicks <= 0) {
            throw new IllegalArgumentException("Flash Grenade fade time must be greater than 0.");
        }
        if (mobTargetSuppressionTicks <= 0) {
            throw new IllegalArgumentException("Flash Grenade target suppression time must be greater than 0.");
        }
    }
}
