package net.uhhitscam.knightfall.item.custom;

public record GrenadeIncendiaryProfile(
        double entityIgnitionRadius,
        int entityFireTicks,
        double firePatchRadius,
        int firePatchAttempts,
        int verticalSearchRange
) {
    public GrenadeIncendiaryProfile {
        if (!Double.isFinite(entityIgnitionRadius) || entityIgnitionRadius <= 0.0) {
            throw new IllegalArgumentException("Grenade ignition radius must be positive and finite.");
        }
        if (entityFireTicks <= 0) {
            throw new IllegalArgumentException("Grenade entity fire duration must be greater than 0.");
        }
        if (!Double.isFinite(firePatchRadius) || firePatchRadius <= 0.0) {
            throw new IllegalArgumentException("Grenade fire patch radius must be positive and finite.");
        }
        if (firePatchAttempts <= 0) {
            throw new IllegalArgumentException("Grenade fire patch attempts must be greater than 0.");
        }
        if (verticalSearchRange < 0) {
            throw new IllegalArgumentException("Grenade fire vertical search range cannot be negative.");
        }
    }
}
