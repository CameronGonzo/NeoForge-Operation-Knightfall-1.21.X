package net.uhhitscam.knightfall.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.event.MeleeWeaponServerEvents;

public record SSMeleeInputPacket(int action) implements Packet {
    public static final int ATTACK = 0, PRESS = 1, RELEASE = 2, CANCEL = 3;
    public static final Type<SSMeleeInputPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(OperationKnightfall.MODID, "melee_input"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SSMeleeInputPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, SSMeleeInputPacket::action, SSMeleeInputPacket::new);
    public void handle(IPayloadContext context) {
        if (context.player() instanceof ServerPlayer player && action >= ATTACK && action <= CANCEL) {
            MeleeWeaponServerEvents.handleInput(player, action);
        }
    }
    public Type<SSMeleeInputPacket> type() { return TYPE; }
}
