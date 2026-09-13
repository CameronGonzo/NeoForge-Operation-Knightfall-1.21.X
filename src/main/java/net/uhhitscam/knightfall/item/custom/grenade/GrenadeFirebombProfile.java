package net.uhhitscam.knightfall.item.custom.grenade;

public record GrenadeFirebombProfile(
        double entityIgnitionRadius,
        int entityFireTicks,
        double firePatchRadius,
        int firePatchAttempts,
        int verticalSearchRange
) {
    public GrenadeFirebombProfile {
        if (!Double.isFinite(entityIgnitionRadius) || entityIgnitionRadius <= 0.0) {
            throw new IllegalArgumentException("Firebomb ignition radius must be positive and finite.");
        }
        if (entityFireTicks <= 0) {
            throw new IllegalArgumentException("Firebomb entity fire duration must be greater than 0.");
        }
        if (!Double.isFinite(firePatchRadius) || firePatchRadius <= 0.0) {
            throw new IllegalArgumentException("Firebomb fire patch radius must be positive and finite.");
        }
        if (firePatchAttempts <= 0) {
            throw new IllegalArgumentException("Firebomb fire patch attempts must be greater than 0.");
        }
        if (verticalSearchRange < 0) {
            throw new IllegalArgumentException("Firebomb vertical search range cannot be negative.");
        }
    }
}
