package net.uhhitscam.knightfall.item.custom;

import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record GrenadeExplosionSpec(
        double entityRadius,
        float damage,
        double knockback,
        float blockBreakRadius,
        Level.ExplosionInteraction blockInteraction,
        boolean causesFire,
        GrenadeSound detonationSound,
        List<GrenadeParticleBurst> particles
) {
    public GrenadeExplosionSpec {
        if (entityRadius <= 0.0) {
            throw new IllegalArgumentException("Grenade entity radius must be greater than 0.");
        }
        if (damage < 0.0F) {
            throw new IllegalArgumentException("Grenade damage cannot be negative.");
        }
        if (knockback < 0.0) {
            throw new IllegalArgumentException("Grenade knockback cannot be negative.");
        }
        if (blockBreakRadius < 0.0F) {
            throw new IllegalArgumentException("Grenade block break radius cannot be negative.");
        }

        Objects.requireNonNull(blockInteraction, "Grenade block interaction cannot be null.");
        Objects.requireNonNull(detonationSound, "Grenade detonation sound cannot be null.");
        particles = List.copyOf(Objects.requireNonNull(particles, "Grenade particles cannot be null."));

        if (blockBreakRadius == 0.0F && blockInteraction != Level.ExplosionInteraction.NONE) {
            throw new IllegalArgumentException("Grenades without a block break radius must use ExplosionInteraction.NONE.");
        }
        if (blockBreakRadius > 0.0F && blockInteraction == Level.ExplosionInteraction.NONE) {
            throw new IllegalArgumentException("Grenades with a block break radius must declare a block interaction.");
        }
    }

    public static Builder builder(double entityRadius, float damage, GrenadeSound detonationSound) {
        return new Builder(entityRadius, damage, detonationSound);
    }

    public static final class Builder {
        private final double entityRadius;
        private final float damage;
        private final GrenadeSound detonationSound;
        private final List<GrenadeParticleBurst> particles = new ArrayList<>();

        private double knockback;
        private float blockBreakRadius;
        private Level.ExplosionInteraction blockInteraction = Level.ExplosionInteraction.NONE;
        private boolean causesFire;

        private Builder(double entityRadius, float damage, GrenadeSound detonationSound) {
            this.entityRadius = entityRadius;
            this.damage = damage;
            this.detonationSound = Objects.requireNonNull(detonationSound, "Grenade detonation sound cannot be null.");
        }

        public Builder knockback(double knockback) {
            this.knockback = knockback;
            return this;
        }

        public Builder terrain(float blockBreakRadius, Level.ExplosionInteraction blockInteraction, boolean causesFire) {
            this.blockBreakRadius = blockBreakRadius;
            this.blockInteraction = Objects.requireNonNull(blockInteraction, "Grenade block interaction cannot be null.");
            this.causesFire = causesFire;
            return this;
        }

        public Builder particle(GrenadeParticleBurst particle) {
            this.particles.add(Objects.requireNonNull(particle, "Grenade particle burst cannot be null."));
            return this;
        }

        public GrenadeExplosionSpec build() {
            return new GrenadeExplosionSpec(
                    entityRadius,
                    damage,
                    knockback,
                    blockBreakRadius,
                    blockInteraction,
                    causesFire,
                    detonationSound,
                    particles
            );
        }
    }
}
