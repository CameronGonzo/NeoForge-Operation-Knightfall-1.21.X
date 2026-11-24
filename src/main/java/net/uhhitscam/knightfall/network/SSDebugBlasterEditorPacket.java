package net.uhhitscam.knightfall.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.event.DebugBlasterEditing;
import net.uhhitscam.knightfall.item.custom.projectile.DebugBlasterSettings;

public record SSDebugBlasterEditorPacket(int action, boolean mainHand, int sessionId, DebugBlasterSettings settings) implements Packet {
    public static final int OPEN = 0;
    public static final int APPLY = 1;
    public static final int REFILL = 2;
    public static final Type<SSDebugBlasterEditorPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(OperationKnightfall.MODID, "debug_blaster_editor"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SSDebugBlasterEditorPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, SSDebugBlasterEditorPacket::action,
            ByteBufCodecs.BOOL, SSDebugBlasterEditorPacket::mainHand,
            ByteBufCodecs.VAR_INT, SSDebugBlasterEditorPacket::sessionId,
            ByteBufCodecs.fromCodec(DebugBlasterSettings.CODEC), SSDebugBlasterEditorPacket::settings,
            SSDebugBlasterEditorPacket::new);

    @Override public void handle(IPayloadContext context) {
        if (context.player() instanceof ServerPlayer player) DebugBlasterEditing.handle(player, this);
    }
    @Override public Type<SSDebugBlasterEditorPacket> type() { return TYPE; }
}
