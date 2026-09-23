package net.uhhitscam.knightfall.item.custom.grenade;

public record GrenadeDioxisProfile(
        float radius,
        int cloudDurationTicks,
        int poisonDurationTicks,
        int poisonAmplifier,
        int gasParticleCount,
        int gasColor
) {
    public GrenadeDioxisProfile {
        if (!Float.isFinite(radius) || radius <= 0.0F) {
            throw new IllegalArgumentException("Dioxis Grenade radius must be positive and finite.");
        }
        if (cloudDurationTicks <= 0) {
            throw new IllegalArgumentException("Dioxis Grenade cloud duration must be greater than zero.");
        }
        if (poisonDurationTicks <= 0) {
            throw new IllegalArgumentException("Dioxis Grenade poison duration must be greater than zero.");
        }
        if (poisonAmplifier < 0) {
            throw new IllegalArgumentException("Dioxis Grenade poison amplifier cannot be negative.");
        }
        if (gasParticleCount <= 0) {
            throw new IllegalArgumentException("Dioxis Grenade gas particle count must be greater than zero.");
        }
        if ((gasColor & 0xFF000000) != 0) {
            throw new IllegalArgumentException("Dioxis Grenade gas color must be a 24-bit RGB value.");
        }
    }
}
