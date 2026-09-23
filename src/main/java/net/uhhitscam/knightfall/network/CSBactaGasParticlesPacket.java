package net.uhhitscam.knightfall.network;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.uhhitscam.knightfall.particle.ModParticles;
import org.joml.Vector3f;

public record CSBactaGasParticlesPacket(
        Vector3f origin,
        int particleCount,
        float minimumSpeed,
        float maximumSpeed
) implements Packet {
    public static final Type<CSBactaGasParticlesPacket> TYPE = new Type<>(
            Identifier.fromNamespaceAndPath("knightfall", "bacta_gas_particles")
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, CSBactaGasParticlesPacket> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.VECTOR3F.map(Vector3f::new, value -> value),
                    CSBactaGasParticlesPacket::origin,
                    ByteBufCodecs.INT,
                    CSBactaGasParticlesPacket::particleCount,
                    ByteBufCodecs.FLOAT,
                    CSBactaGasParticlesPacket::minimumSpeed,
                    ByteBufCodecs.FLOAT,
                    CSBactaGasParticlesPacket::maximumSpeed,
                    CSBactaGasParticlesPacket::new
            );

    @Override
    public void handle(IPayloadContext context) {
        Player player = context.player();
        Level level = player.level();
        RandomSource random = level.getRandom();

        for (int particleIndex = 0; particleIndex < particleCount; particleIndex++) {
            double verticalDirection = random.nextDouble() * 2.0 - 1.0;
            double horizontalDirection = Math.sqrt(1.0 - verticalDirection * verticalDirection);
            double angle = random.nextDouble() * Mth.TWO_PI;
            double speed = Mth.lerp(random.nextDouble(), minimumSpeed, maximumSpeed);

            level.addParticle(
                    particle(particleIndex),
                    origin.x(),
                    origin.y(),
                    origin.z(),
                    Math.cos(angle) * horizontalDirection * speed,
                    verticalDirection * speed,
                    Math.sin(angle) * horizontalDirection * speed
            );
        }
    }

    private static SimpleParticleType particle(int particleIndex) {
        return switch (particleIndex % 3) {
            case 0 -> ModParticles.BACTA_GAS_01_PARTICLES.get();
            case 1 -> ModParticles.BACTA_GAS_02_PARTICLES.get();
            default -> ModParticles.BACTA_GAS_03_PARTICLES.get();
        };
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
