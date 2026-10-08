package net.uhhitscam.knightfall.item.custom.grenade;

public record GrenadeIonArcProfile(
        int count,
        int lifetimeTicks,
        int minimumSegments,
        int maximumSegments,
        float segmentLength,
        float bend,
        int exteriorColor,
        float coreRadius,
        float exteriorRadius
) {
    public GrenadeIonArcProfile {
        if (count < 0 || count > 64 || lifetimeTicks < 1 || lifetimeTicks > 100) {
            throw new IllegalArgumentException("Ion arc count or lifetime is out of range.");
        }
        if (minimumSegments < 1 || maximumSegments < minimumSegments || maximumSegments > 16) {
            throw new IllegalArgumentException("Ion arc segment range is invalid.");
        }
        if (!Float.isFinite(segmentLength) || segmentLength <= 0.0F
                || !Float.isFinite(bend) || bend < 0.0F || bend > 1.0F) {
            throw new IllegalArgumentException("Ion arc length or bend is invalid.");
        }
        if ((exteriorColor & 0xFF000000) != 0) {
            throw new IllegalArgumentException("Ion arc exterior color must be a 24-bit RGB value.");
        }
        if (!Float.isFinite(coreRadius) || coreRadius <= 0.0F
                || !Float.isFinite(exteriorRadius) || exteriorRadius < coreRadius) {
            throw new IllegalArgumentException("Ion arc radii are invalid.");
        }
    }
}
