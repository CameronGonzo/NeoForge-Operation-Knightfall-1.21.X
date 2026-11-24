package net.uhhitscam.knightfall.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.gui.DebugBlasterScreen;
import net.uhhitscam.knightfall.item.custom.projectile.DebugBlasterSettings;

public record CSDebugBlasterEditorPacket(int sessionId, boolean mainHand, DebugBlasterSettings settings,
                                       String message, boolean close) implements Packet {
    public static final Type<CSDebugBlasterEditorPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(OperationKnightfall.MODID, "debug_blaster_editor_result"));
    public static final StreamCodec<RegistryFriendlyByteBuf, CSDebugBlasterEditorPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, CSDebugBlasterEditorPacket::sessionId,
            ByteBufCodecs.BOOL, CSDebugBlasterEditorPacket::mainHand,
            ByteBufCodecs.fromCodec(DebugBlasterSettings.CODEC), CSDebugBlasterEditorPacket::settings,
            ByteBufCodecs.stringUtf8(512), CSDebugBlasterEditorPacket::message,
            ByteBufCodecs.BOOL, CSDebugBlasterEditorPacket::close,
            CSDebugBlasterEditorPacket::new);

    @Override public void handle(IPayloadContext context) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.gui.screen() instanceof DebugBlasterScreen screen && screen.sessionId() == sessionId) {
            screen.receive(message, close);
        } else if (message.isEmpty() && !close) {
            minecraft.gui.setScreen(new DebugBlasterScreen(sessionId, mainHand, settings));
        }
    }
    @Override public Type<CSDebugBlasterEditorPacket> type() { return TYPE; }
}
