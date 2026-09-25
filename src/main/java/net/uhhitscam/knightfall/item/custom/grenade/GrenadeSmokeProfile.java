package net.uhhitscam.knightfall.item.custom.grenade;

public record GrenadeSmokeProfile(
        double radius,
        int expansionTicks,
        int particlesPerTick,
        double particleDrift,
        double cloudCenterOffset,
        double mobTargetingRange
) {
    public GrenadeSmokeProfile {
        if (!Double.isFinite(radius) || radius <= 0.0) {
            throw new IllegalArgumentException("Smoke radius must be positive and finite.");
        }
        if (expansionTicks <= 0) {
            throw new IllegalArgumentException("Smoke expansion time must be greater than zero.");
        }
        if (particlesPerTick <= 0) {
            throw new IllegalArgumentException("Smoke particles per tick must be greater than zero.");
        }
        if (!Double.isFinite(particleDrift) || particleDrift < 0.0) {
            throw new IllegalArgumentException("Smoke particle drift cannot be negative.");
        }
        if (!Double.isFinite(cloudCenterOffset) || cloudCenterOffset < 0.0) {
            throw new IllegalArgumentException("Smoke cloud center offset cannot be negative.");
        }
        if (!Double.isFinite(mobTargetingRange) || mobTargetingRange < radius) {
            throw new IllegalArgumentException("Smoke mob targeting range cannot be smaller than its radius.");
        }
    }

    public double radiusAt(int totalDurationTicks, int remainingDurationTicks) {
        int elapsedTicks = Math.max(0, totalDurationTicks - remainingDurationTicks);
        double expansion = Math.min(1.0, (double) elapsedTicks / expansionTicks);
        return radius * expansion;
    }
}
