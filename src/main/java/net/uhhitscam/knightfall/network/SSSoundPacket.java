package net.uhhitscam.knightfall.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.uhhitscam.knightfall.sound.ModSounds;

public record SSSoundPacket(boolean mainHand) implements Packet {

    public static final Type<SSSoundPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("knightfall", "no_ammo_sound"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SSSoundPacket> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.BOOL,
                    SSSoundPacket::mainHand,
                    SSSoundPacket::new
            );

    @Override
    public void handle(IPayloadContext context) {
        Player player = context.player();
        if (player.level().isClientSide) return;

        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.FOLEY_NO_AMMO.get(), SoundSource.NEUTRAL, 0.5F, 1.0F);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}