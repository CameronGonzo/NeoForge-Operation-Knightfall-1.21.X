package net.uhhitscam.knightfall.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.uhhitscam.knightfall.particle.ModParticles;
import org.joml.Vector3f;

public record CSDisintegrationParticlesPacket(Vector3f origin, float entityWidth, float entityHeight, int largeNum, int mediumNum, int smallNum) implements Packet {
    public static final Type<CSDisintegrationParticlesPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("knightfall", "disintegration_particles"));
    public static final StreamCodec<RegistryFriendlyByteBuf, CSDisintegrationParticlesPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VECTOR3F,
            CSDisintegrationParticlesPacket::origin,
            ByteBufCodecs.FLOAT,
            CSDisintegrationParticlesPacket::entityWidth,
            ByteBufCodecs.FLOAT,
            CSDisintegrationParticlesPacket::entityHeight,
            ByteBufCodecs.INT,
            CSDisintegrationParticlesPacket::largeNum,
            ByteBufCodecs.INT,
            CSDisintegrationParticlesPacket::mediumNum,
            ByteBufCodecs.INT,
            CSDisintegrationParticlesPacket::smallNum,
            CSDisintegrationParticlesPacket::new
    );

    @Override
    public void handle(IPayloadContext context) {
        Player player = context.player();
        Level level = player.level();

        spawnParticles(level, largeNum, ParticleSize.LARGE);
        spawnParticles(level, mediumNum, ParticleSize.MEDIUM);
        spawnParticles(level, smallNum, ParticleSize.SMALL);
    }

    private void spawnParticles(Level level, int count, ParticleSize size) {
        for (int i = 0; i < count; i++) {
            Vec3 spawnOffset = randomBodyOffset(level);
            Vec3 velocity = randomUpwardVelocity(level, size);

            level.addParticle(
                    size.particleType(),
                    origin.x() + spawnOffset.x,
                    origin.y() + spawnOffset.y,
                    origin.z() + spawnOffset.z,
                    velocity.x,
                    velocity.y,
                    velocity.z
            );
        }
    }

    private Vec3 randomBodyOffset(Level level) {
        double radius = Math.max(0.15F, entityWidth * 0.45F);

        double x = (level.random.nextDouble() - 0.3D) * radius;
        double y = (level.random.nextDouble() - 0.3D) * entityHeight;
        double z = (level.random.nextDouble() - 0.3D) * radius;

        return new Vec3(x, y, z);
    }

    private Vec3 randomUpwardVelocity(Level level, ParticleSize size) {
        double horizontalSpread = size.horizontalSpread;
        double upwardSpeed = size.upwardSpeed;

        double x = level.random.nextGaussian() * horizontalSpread;
        double y = upwardSpeed + level.random.nextDouble() * upwardSpeed;
        double z = level.random.nextGaussian() * horizontalSpread;

        return new Vec3(x, y, z);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    private enum ParticleSize {
        LARGE(0.06D, 0.055D),
        MEDIUM(0.05D, 0.075D),
        SMALL(0.07D, 0.095D);

        private final double horizontalSpread;
        private final double upwardSpeed;

        ParticleSize(double horizontalSpread, double upwardSpeed) {
            this.horizontalSpread = horizontalSpread;
            this.upwardSpeed = upwardSpeed;
        }

        private net.minecraft.core.particles.SimpleParticleType particleType() {
            return switch (this) {
                case LARGE -> ModParticles.DISINTEGRATION_LARGE_PARTICLES.get();
                case MEDIUM -> ModParticles.DISINTEGRATION_MEDIUM_PARTICLES.get();
                case SMALL -> ModParticles.DISINTEGRATION_SMALL_PARTICLES.get();
            };
        }
    }
}