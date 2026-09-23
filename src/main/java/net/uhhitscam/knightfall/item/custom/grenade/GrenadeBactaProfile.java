package net.uhhitscam.knightfall.item.custom.grenade;

public record GrenadeBactaProfile(
        double radius,
        float instantHealing,
        int regenerationDurationTicks,
        int regenerationAmplifier,
        int gasParticleCount,
        double minimumGasParticleSpeed,
        double maximumGasParticleSpeed
) {
    public GrenadeBactaProfile {
        if (!Double.isFinite(radius) || radius <= 0.0) {
            throw new IllegalArgumentException("Bacta Bomb radius must be positive and finite.");
        }
        if (!Float.isFinite(instantHealing) || instantHealing < 0.0F) {
            throw new IllegalArgumentException("Bacta Bomb instant healing cannot be negative.");
        }
        if (regenerationDurationTicks < 0) {
            throw new IllegalArgumentException("Bacta Bomb regeneration duration cannot be negative.");
        }
        if (regenerationAmplifier < 0) {
            throw new IllegalArgumentException("Bacta Bomb regeneration amplifier cannot be negative.");
        }
        if (gasParticleCount <= 0) {
            throw new IllegalArgumentException("Bacta Bomb gas particle count must be greater than zero.");
        }
        if (!Double.isFinite(minimumGasParticleSpeed) || minimumGasParticleSpeed < 0.0) {
            throw new IllegalArgumentException("Bacta Bomb minimum gas particle speed cannot be negative.");
        }
        if (!Double.isFinite(maximumGasParticleSpeed)
                || maximumGasParticleSpeed < minimumGasParticleSpeed) {
            throw new IllegalArgumentException(
                    "Bacta Bomb maximum gas particle speed cannot be less than its minimum speed."
            );
        }
    }
}
