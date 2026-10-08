package net.uhhitscam.knightfall.particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record IonFlashParticleOption(float size) implements ParticleOptions {
    public static final MapCodec<IonFlashParticleOption> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(Codec.FLOAT.fieldOf("size").forGetter(IonFlashParticleOption::size))
                    .apply(instance, IonFlashParticleOption::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, IonFlashParticleOption> STREAM_CODEC =
            StreamCodec.composite(ByteBufCodecs.FLOAT, IonFlashParticleOption::size, IonFlashParticleOption::new);

    public IonFlashParticleOption {
        if (!Float.isFinite(size) || size <= 0.0F) {
            throw new IllegalArgumentException("Ion flash particle size must be positive and finite.");
        }
    }

    @Override
    public ParticleType<?> getType() {
        return ModParticles.ION_FLASH_PARTICLES.get();
    }
}
