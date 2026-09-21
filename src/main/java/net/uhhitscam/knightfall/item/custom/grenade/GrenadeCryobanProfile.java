package net.uhhitscam.knightfall.item.custom.grenade;

public record GrenadeCryobanProfile(
        double freezeRadius,
        int freezeDurationTicks
) {
    public GrenadeCryobanProfile {
        if (!Double.isFinite(freezeRadius) || freezeRadius <= 0.0) {
            throw new IllegalArgumentException("Cryoban freeze radius must be positive and finite.");
        }
        if (freezeDurationTicks <= 0 || freezeDurationTicks > (Integer.MAX_VALUE / 2)) {
            throw new IllegalArgumentException("Cryoban freeze duration must be between 1 and 1,073,741,823 ticks.");
        }
    }
}
