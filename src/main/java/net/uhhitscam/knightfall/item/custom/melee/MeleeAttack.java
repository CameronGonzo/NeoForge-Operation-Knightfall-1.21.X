package net.uhhitscam.knightfall.item.custom.melee;

import java.util.Objects;

public record MeleeAttack(MeleeAttackTrait trait, float damageMultiplier, int cooldownTicks,
                          int chargeTicks, int intervalTicks, double radius, double knockback,
                          double lift, double speed, int durationTicks) {
    public MeleeAttack {
        Objects.requireNonNull(trait);
        if (!Float.isFinite(damageMultiplier) || damageMultiplier < 0 || cooldownTicks < 1
                || chargeTicks < 1 || intervalTicks < 1 || durationTicks < 1
                || !Double.isFinite(radius) || radius <= 0 || radius > 32
                || !Double.isFinite(knockback) || knockback < 0
                || !Double.isFinite(lift) || lift < 0 || !Double.isFinite(speed) || speed <= 0) {
            throw new IllegalArgumentException("Invalid melee attack tuning: " + trait);
        }
    }

    public static MeleeAttack of(MeleeAttackTrait trait) {
        return new MeleeAttack(trait, trait == MeleeAttackTrait.KNOCKBACK || trait == MeleeAttackTrait.CHARGE ? 0 : 1,
                10, 10, 10, 3, 0.65, 0.3, 1.2, 6);
    }

    public MeleeAttack damage(float multiplier) {
        return new MeleeAttack(trait, multiplier, cooldownTicks, chargeTicks, intervalTicks, radius, knockback, lift, speed, durationTicks);
    }

    public MeleeAttack timing(int cooldown, int charge, int interval) {
        return new MeleeAttack(trait, damageMultiplier, cooldown, charge, interval, radius, knockback, lift, speed, durationTicks);
    }

    public MeleeAttack area(double radius) {
        return new MeleeAttack(trait, damageMultiplier, cooldownTicks, chargeTicks, intervalTicks, radius, knockback, lift, speed, durationTicks);
    }

    public MeleeAttack motion(double knockback, double lift, double speed, int duration) {
        return new MeleeAttack(trait, damageMultiplier, cooldownTicks, chargeTicks, intervalTicks, radius, knockback, lift, speed, duration);
    }
}
