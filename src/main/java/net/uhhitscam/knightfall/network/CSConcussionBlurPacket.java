package net.uhhitscam.knightfall.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.uhhitscam.knightfall.util.BlurRequests;
import org.joml.Vector3f;

public record CSConcussionBlurPacket(Vector3f impactPos,
                                     float effectRadiusBlocks,
                                     int holdTicks,
                                     int fadeOutTicks,
                                     float maxShaderRadius) implements Packet {

    public static final Type<CSConcussionBlurPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("knightfall", "concussion_blur"));

    public static final StreamCodec<RegistryFriendlyByteBuf, CSConcussionBlurPacket> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.VECTOR3F, CSConcussionBlurPacket::impactPos,
                    ByteBufCodecs.FLOAT,    CSConcussionBlurPacket::effectRadiusBlocks,
                    ByteBufCodecs.INT,      CSConcussionBlurPacket::holdTicks,
                    ByteBufCodecs.INT,      CSConcussionBlurPacket::fadeOutTicks,
                    ByteBufCodecs.FLOAT,    CSConcussionBlurPacket::maxShaderRadius,
                    CSConcussionBlurPacket::new
            );

    @Override
    public void handle(IPayloadContext context) {
        Vec3 impact = toVec3(impactPos);
        BlurRequests.enqueue(new BlurRequests.Request(
                impact,
                effectRadiusBlocks,
                holdTicks,
                fadeOutTicks,
                maxShaderRadius
        ));
    }

    private static Vec3 toVec3(Vector3f v) {
        return new Vec3(v.x(), v.y(), v.z());
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}