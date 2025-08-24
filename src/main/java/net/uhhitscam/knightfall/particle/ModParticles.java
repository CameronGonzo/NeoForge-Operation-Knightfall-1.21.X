package net.uhhitscam.knightfall.particle;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.uhhitscam.knightfall.OperationKnightfall;

import java.util.function.Supplier;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, OperationKnightfall.MODID);

    public static final Supplier<SimpleParticleType> SPARK_PARTICLES =
            PARTICLE_TYPES.register("spark_particles", () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> STUN_SPARK_PARTICLES =
            PARTICLE_TYPES.register("stun_spark_particles", () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> EXPLOSIVE_SHOT_TIBANNA_PARTICLES =
            PARTICLE_TYPES.register("explosive_shot_tibanna_particles", () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> EXPLOSIVE_SHOT_IONIZED_TIBANNA_PARTICLES =
            PARTICLE_TYPES.register("explosive_shot_ionized_tibanna_particles", () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> EXPLOSIVE_SHOT_SPIN_SEALED_TIBANNA_PARTICLES =
            PARTICLE_TYPES.register("explosive_shot_spin_sealed_tibanna_particles", () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> EXPLOSIVE_SHOT_TIBANNAX_PARTICLES =
            PARTICLE_TYPES.register("explosive_shot_tibannax_particles", () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> EXPLOSIVE_SHOT_SIG_PARTICLES =
            PARTICLE_TYPES.register("explosive_shot_sig_particles", () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> EXPLOSIVE_SHOT_MAGNETIZED_SIG_PARTICLES =
            PARTICLE_TYPES.register("explosive_shot_magnetized_sig_particles", () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> EXPLOSIVE_SHOT_SKEVON_PARTICLES =
            PARTICLE_TYPES.register("explosive_shot_skevon_particles", () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> REPULSE_SHOT_LARGE_PARTICLES =
            PARTICLE_TYPES.register("repulse_shot_large_particles", () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> REPULSE_SHOT_MEDIUM_PARTICLES =
            PARTICLE_TYPES.register("repulse_shot_medium_particles", () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> REPULSE_SHOT_SMALL_PARTICLES =
            PARTICLE_TYPES.register("repulse_shot_small_particles", () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}