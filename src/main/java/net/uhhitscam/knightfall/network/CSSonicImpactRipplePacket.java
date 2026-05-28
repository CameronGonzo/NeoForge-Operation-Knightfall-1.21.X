package net.uhhitscam.knightfall.network;

import net.minecraft.core.Direction;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.event.SonicImpactRippleClient;
import org.joml.Vector3f;

public record CSSonicImpactRipplePacket(Vector3f position, int directionId) implements Packet {
    public static final Type<CSSonicImpactRipplePacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "sonic_impact_ripple")
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, CSSonicImpactRipplePacket> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.VECTOR3F,
                    CSSonicImpactRipplePacket::position,
                    ByteBufCodecs.INT,
                    CSSonicImpactRipplePacket::directionId,
                    CSSonicImpactRipplePacket::new
            );

    @Override
    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            Direction direction = Direction.from3DDataValue(directionId);
            SonicImpactRippleClient.add(position, direction);
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}