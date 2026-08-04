package net.uhhitscam.knightfall.item.custom;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;
import java.util.function.Supplier;

public record GrenadeParticleBurst(
        Supplier<? extends ParticleOptions> particle,
        int count,
        double spreadX,
        double spreadY,
        double spreadZ,
        double speed
) {
    public GrenadeParticleBurst {
        Objects.requireNonNull(particle, "Grenade particle supplier cannot be null.");

        if (count <= 0) {
            throw new IllegalArgumentException("Grenade particle count must be greater than 0.");
        }
        if (spreadX < 0.0 || spreadY < 0.0 || spreadZ < 0.0) {
            throw new IllegalArgumentException("Grenade particle spread cannot be negative.");
        }
        if (speed < 0.0) {
            throw new IllegalArgumentException("Grenade particle speed cannot be negative.");
        }
    }

    public void spawn(ServerLevel level, Vec3 position) {
        level.sendParticles(
                particle.get(),
                position.x,
                position.y,
                position.z,
                count,
                spreadX,
                spreadY,
                spreadZ,
                speed
        );
    }
}
