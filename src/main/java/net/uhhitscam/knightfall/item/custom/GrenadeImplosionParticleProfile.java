package net.uhhitscam.knightfall.item.custom;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;
import java.util.function.Supplier;

public record GrenadeImplosionParticleProfile(
        Supplier<? extends ParticleOptions> particle,
        int countPerTick,
        double spawnRadius,
        double inwardSpeed
) {
    public GrenadeImplosionParticleProfile {
        Objects.requireNonNull(particle, "Grenade implosion particle supplier cannot be null.");
        if (countPerTick <= 0) {
            throw new IllegalArgumentException("Grenade implosion particle count must be greater than 0.");
        }
        if (!Double.isFinite(spawnRadius) || spawnRadius <= 0.0) {
            throw new IllegalArgumentException("Grenade implosion particle radius must be positive and finite.");
        }
        if (!Double.isFinite(inwardSpeed) || inwardSpeed <= 0.0) {
            throw new IllegalArgumentException("Grenade implosion particle speed must be positive and finite.");
        }
    }

    public void spawn(ServerLevel level, Vec3 center) {
        RandomSource random = level.getRandom();

        for (int index = 0; index < countPerTick; index++) {
            Vec3 direction = randomDirection(random);
            double distance = spawnRadius * (0.65 + random.nextDouble() * 0.35);
            Vec3 spawnPosition = center.add(direction.scale(distance));
            Vec3 velocity = direction.scale(-inwardSpeed);

            level.sendParticles(
                    particle.get(),
                    spawnPosition.x,
                    spawnPosition.y,
                    spawnPosition.z,
                    0,
                    velocity.x,
                    velocity.y,
                    velocity.z,
                    1.0
            );
        }
    }

    private static Vec3 randomDirection(RandomSource random) {
        Vec3 direction;
        do {
            direction = new Vec3(random.nextGaussian(), random.nextGaussian(), random.nextGaussian());
        } while (direction.lengthSqr() < 1.0E-6);
        return direction.normalize();
    }
}
