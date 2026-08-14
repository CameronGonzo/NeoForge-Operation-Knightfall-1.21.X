package net.uhhitscam.knightfall.item.custom;

import net.minecraft.world.phys.AABB;
import net.uhhitscam.knightfall.entity.custom.GrenadeEntity;
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

            detonateNearbyGrenades(context, spec.entityRadius());
        };
    }

    private static void detonateNearbyGrenades(GrenadeDetonationContext context, double radius) {
        double radiusSquared = radius * radius;
        AABB bounds = new AABB(context.position(), context.position()).inflate(radius);

        for (GrenadeEntity grenade : context.level().getEntitiesOfClass(GrenadeEntity.class, bounds)) {
            if (grenade != context.grenade()
                    && !grenade.isRemoved()
                    && grenade.distanceToSqr(context.position()) <= radiusSquared) {
                grenade.detonate();
            }
        }
    }
}
