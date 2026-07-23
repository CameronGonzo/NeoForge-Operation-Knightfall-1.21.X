package net.uhhitscam.knightfall.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class PayloadRegister {
    private static PayloadRegistrar registrar;

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        registrar = event.registrar("knightfall");
        server(SSProjectileWeaponInputPacket.TYPE, SSProjectileWeaponInputPacket.STREAM_CODEC);
        server(SSProjectileWeaponActionPacket.TYPE, SSProjectileWeaponActionPacket.STREAM_CODEC);
        server(SSSoundPacket.TYPE, SSSoundPacket.STREAM_CODEC);

        client(CSProjectileWeaponRecoilPacket.TYPE, CSProjectileWeaponRecoilPacket.STREAM_CODEC);
        client(CSRepulseParticlesPacket.TYPE, CSRepulseParticlesPacket.STREAM_CODEC);
        client(CSDisintegrationParticlesPacket.TYPE, CSDisintegrationParticlesPacket.STREAM_CODEC);
        client(CSConcussionBlurPacket.TYPE, CSConcussionBlurPacket.STREAM_CODEC);
        client(CSFaceAlignedParticlePacket.TYPE, CSFaceAlignedParticlePacket.STREAM_CODEC);
    }

    private static <T extends Packet> void server(CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> reader) {
        registrar.playToServer(type, reader, PayloadRegister::handlePacket);
    }

    private static <T extends Packet> void client(CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> reader) {
        registrar.playToClient(type, reader, PayloadRegister::handlePacket);
    }

    public static <T extends Packet> void handlePacket(final T data, final IPayloadContext context) {
        context.enqueueWork(() -> data.handle(context));
    }

    public static void sendToServer(Packet packet) {
        PacketDistributor.sendToServer(packet);
    }

    public static void sendToPlayer(ServerPlayer player, Packet packet) {
        PacketDistributor.sendToPlayer(player, packet);
    }
}
