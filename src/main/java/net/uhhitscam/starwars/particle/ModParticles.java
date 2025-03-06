package net.uhhitscam.starwars.particle;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.uhhitscam.starwars.OperationKnightfall;

import java.util.function.Supplier;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, OperationKnightfall.MODID);

    public static final Supplier<SimpleParticleType> SPARK_PARTICLES =
            PARTICLE_TYPES.register("spark_particles", () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> STUN_SPARK_PARTICLES =
            PARTICLE_TYPES.register("stun_spark_particles", () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}