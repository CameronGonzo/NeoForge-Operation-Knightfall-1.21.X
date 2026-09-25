package net.uhhitscam.knightfall.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.util.FlashRequests;

public record CSFlashEffectPacket(int fullWhiteTicks, int fadeOutTicks) implements Packet {
    public static final Type<CSFlashEffectPacket> TYPE = new Type<>(
            Identifier.fromNamespaceAndPath(OperationKnightfall.MODID, "flash_effect")
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, CSFlashEffectPacket> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.INT, CSFlashEffectPacket::fullWhiteTicks,
                    ByteBufCodecs.INT, CSFlashEffectPacket::fadeOutTicks,
                    CSFlashEffectPacket::new
            );

    @Override
    public void handle(IPayloadContext context) {
        FlashRequests.enqueue(new FlashRequests.Request(fullWhiteTicks, fadeOutTicks));
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
