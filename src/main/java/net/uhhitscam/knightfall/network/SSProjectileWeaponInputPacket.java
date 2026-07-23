package net.uhhitscam.knightfall.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.event.ProjectileWeaponServerEvents;

public record SSProjectileWeaponInputPacket(boolean mainHand, boolean active) implements Packet {
    public static final Type<SSProjectileWeaponInputPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "projectile_weapon_input")
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, SSProjectileWeaponInputPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            SSProjectileWeaponInputPacket::mainHand,
            ByteBufCodecs.BOOL,
            SSProjectileWeaponInputPacket::active,
            SSProjectileWeaponInputPacket::new
    );

    @Override
    public void handle(IPayloadContext context) {
        if (context.player() instanceof ServerPlayer player) {
            ProjectileWeaponServerEvents.handleInput(player, mainHand, active);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
