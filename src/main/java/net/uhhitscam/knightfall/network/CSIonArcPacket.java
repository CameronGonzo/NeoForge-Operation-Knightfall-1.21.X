package net.uhhitscam.knightfall.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.event.IonArcClient;
import net.uhhitscam.knightfall.item.custom.grenade.GrenadeIonArcProfile;
import org.joml.Vector3f;

public record CSIonArcPacket(
        Vector3f origin,
        long seed,
        int windowTicks,
        GrenadeIonArcProfile arcs
) implements Packet {
    public static final Type<CSIonArcPacket> TYPE = new Type<>(
            Identifier.fromNamespaceAndPath(OperationKnightfall.MODID, "ion_arcs")
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, CSIonArcPacket> STREAM_CODEC = StreamCodec.of(
            (buffer, packet) -> {
                buffer.writeFloat(packet.origin.x());
                buffer.writeFloat(packet.origin.y());
                buffer.writeFloat(packet.origin.z());
                buffer.writeLong(packet.seed);
                buffer.writeInt(packet.windowTicks);

                GrenadeIonArcProfile arcs = packet.arcs;
                buffer.writeInt(arcs.count());
                buffer.writeInt(arcs.lifetimeTicks());
                buffer.writeInt(arcs.minimumSegments());
                buffer.writeInt(arcs.maximumSegments());
                buffer.writeFloat(arcs.segmentLength());
                buffer.writeFloat(arcs.bend());
                buffer.writeInt(arcs.exteriorColor());
                buffer.writeFloat(arcs.coreRadius());
                buffer.writeFloat(arcs.exteriorRadius());
            },
            buffer -> new CSIonArcPacket(
                    new Vector3f(buffer.readFloat(), buffer.readFloat(), buffer.readFloat()),
                    buffer.readLong(),
                    buffer.readInt(),
                    new GrenadeIonArcProfile(
                            buffer.readInt(),
                            buffer.readInt(),
                            buffer.readInt(),
                            buffer.readInt(),
                            buffer.readFloat(),
                            buffer.readFloat(),
                            buffer.readInt(),
                            buffer.readFloat(),
                            buffer.readFloat()
                    )
            )
    );

    @Override
    public void handle(IPayloadContext context) {
        IonArcClient.add(new Vec3(origin.x(), origin.y(), origin.z()), seed, windowTicks, arcs);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
