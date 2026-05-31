package net.uhhitscam.knightfall.network;

import net.minecraft.core.Direction;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.event.FaceAlignedParticleClient;
import net.uhhitscam.knightfall.event.FaceAlignedParticleType;
import org.joml.Vector3f;

public record CSFaceAlignedParticlePacket(
        Vector3f position,
        int directionId,
        int effectTypeId,
        int variant
) implements Packet {
    public static final Type<CSFaceAlignedParticlePacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "face_aligned_particle")
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, CSFaceAlignedParticlePacket> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.VECTOR3F,
                    CSFaceAlignedParticlePacket::position,
                    ByteBufCodecs.INT,
                    CSFaceAlignedParticlePacket::directionId,
                    ByteBufCodecs.INT,
                    CSFaceAlignedParticlePacket::effectTypeId,
                    ByteBufCodecs.INT,
                    CSFaceAlignedParticlePacket::variant,
                    CSFaceAlignedParticlePacket::new
            );

    @Override
    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            Direction direction = Direction.from3DDataValue(directionId);
            FaceAlignedParticleType effectType = FaceAlignedParticleType.byId(effectTypeId);

            FaceAlignedParticleClient.add(
                    position,
                    direction,
                    effectType,
                    variant
            );
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}