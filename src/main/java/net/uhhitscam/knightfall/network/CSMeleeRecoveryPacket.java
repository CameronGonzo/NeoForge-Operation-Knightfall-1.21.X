package net.uhhitscam.knightfall.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.uhhitscam.knightfall.OperationKnightfall;

public record CSMeleeRecoveryPacket() implements Packet {
    public static final Type<CSMeleeRecoveryPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "melee_recovery"));
    public static final StreamCodec<RegistryFriendlyByteBuf, CSMeleeRecoveryPacket> STREAM_CODEC = StreamCodec.unit(new CSMeleeRecoveryPacket());
    public void handle(IPayloadContext context) { context.player().resetAttackStrengthTicker(); }
    public Type<CSMeleeRecoveryPacket> type() { return TYPE; }
}
