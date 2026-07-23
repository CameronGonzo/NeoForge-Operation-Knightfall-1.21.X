package net.uhhitscam.knightfall.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.event.ProjectileWeaponServerEvents;
import net.uhhitscam.knightfall.item.custom.WeaponAction;

public record SSProjectileWeaponActionPacket(boolean mainHand, WeaponAction action) implements Packet {
    public static final Type<SSProjectileWeaponActionPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "projectile_weapon_action")
    );

    private static final StreamCodec<ByteBuf, WeaponAction> ACTION_CODEC =
            ByteBufCodecs.VAR_INT.map(WeaponAction::byId, WeaponAction::id);

    public static final StreamCodec<RegistryFriendlyByteBuf, SSProjectileWeaponActionPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            SSProjectileWeaponActionPacket::mainHand,
            ACTION_CODEC,
            SSProjectileWeaponActionPacket::action,
            SSProjectileWeaponActionPacket::new
    );

    @Override
    public void handle(IPayloadContext context) {
        if (context.player() instanceof ServerPlayer player) {
            ProjectileWeaponServerEvents.handleAction(player, mainHand, action);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
