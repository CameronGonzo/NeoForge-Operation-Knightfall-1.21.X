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

public record CSRepulseParticlesPacket(Vector3f origin, Vector3f lookDir, int largeNum, int mediumNum, int smallNum) implements Packet {
    public static final Type<CSRepulseParticlesPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("knightfall", "repulse_particles"));
    public static final StreamCodec<RegistryFriendlyByteBuf, CSRepulseParticlesPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VECTOR3F,
            CSRepulseParticlesPacket::origin,
            ByteBufCodecs.VECTOR3F,
            CSRepulseParticlesPacket::lookDir,
            ByteBufCodecs.INT,
            CSRepulseParticlesPacket::largeNum,
            ByteBufCodecs.INT,
            CSRepulseParticlesPacket::mediumNum,
            ByteBufCodecs.INT,
            CSRepulseParticlesPacket::smallNum,
            CSRepulseParticlesPacket::new
    );

    @Override
    public void handle(IPayloadContext context) {
        Player player = context.player();
        Level level = player.level();

        for (int i = 0; i < largeNum; i++) {
            Vec3 randomSpread = new Vec3(
                    level.random.nextGaussian() * 0.3,
                    level.random.nextGaussian() * 0.3,
                    level.random.nextGaussian() * 0.3
            );

            Double deltax = (randomSpread.x * 0.3) + (lookDir.x * 0.7);
            Double deltay = (randomSpread.y * 0.3) + (lookDir.y * 0.7);
            Double deltaz = (randomSpread.z * 0.3) + (lookDir.z * 0.7);

            level.addParticle(
                    ModParticles.REPULSE_SHOT_LARGE_PARTICLES.get(),
                    origin.x, origin.y, origin.z,
                    deltax * 0.7, deltay * 0.7, deltaz * 0.7
            );
        }

        for (int i = 0; i < mediumNum; i++) {
            Vec3 randomSpread = new Vec3(
                    level.random.nextGaussian() * 0.3,
                    level.random.nextGaussian() * 0.3,
                    level.random.nextGaussian() * 0.3
            );

            Double deltax = (randomSpread.x * 0.3) + (lookDir.x * 0.7);
            Double deltay = (randomSpread.y * 0.3) + (lookDir.y * 0.7);
            Double deltaz = (randomSpread.z * 0.3) + (lookDir.z * 0.7);

            level.addParticle(
                    ModParticles.REPULSE_SHOT_MEDIUM_PARTICLES.get(),
                    origin.x, origin.y, origin.z,
                    deltax * 0.7, deltay * 0.7, deltaz * 0.7
            );
        }

        for (int i = 0; i < smallNum; i++) {
            Vec3 randomSpread = new Vec3(
                    level.random.nextGaussian() * 0.3,
                    level.random.nextGaussian() * 0.3,
                    level.random.nextGaussian() * 0.3
            );

            Double deltax = (randomSpread.x * 0.3) + (lookDir.x * 0.7);
            Double deltay = (randomSpread.y * 0.3) + (lookDir.y * 0.7);
            Double deltaz = (randomSpread.z * 0.3) + (lookDir.z * 0.7);

            level.addParticle(
                    ModParticles.REPULSE_SHOT_SMALL_PARTICLES.get(),
                    origin.x, origin.y, origin.z,
                    deltax * 0.7, deltay * 0.7, deltaz * 0.7
            );
        }
    }

    Vec3 toVec3(Vector3f vec) {
        return new Vec3(vec.x(), vec.y(), vec.z());
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}