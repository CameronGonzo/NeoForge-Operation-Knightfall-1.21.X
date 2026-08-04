package net.uhhitscam.knightfall.item.custom;

import net.uhhitscam.knightfall.util.CustomExplosion;

import java.util.Objects;

public final class GrenadeEffects {
    private GrenadeEffects() {
    }

    public static GrenadeEffect explosion(GrenadeExplosionSpec spec) {
        Objects.requireNonNull(spec, "Grenade explosion specification cannot be null.");

        return context -> {
            CustomExplosion.create(
                    context.grenade(),
                    context.owner(),
                    context.position(),
                    spec.entityRadius(),
                    spec.damage(),
                    spec.knockback(),
                    spec.blockBreakRadius(),
                    spec.blockInteraction(),
                    spec.causesFire()
            );

            spec.detonationSound().play(context.level(), context.position());
            for (GrenadeParticleBurst particle : spec.particles()) {
                particle.spawn(context.level(), context.position());
            }
        };
    }
}
