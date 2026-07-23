package net.uhhitscam.knightfall.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.event.ClientProjectileWeaponRecoil;

public record CSProjectileWeaponRecoilPacket(float recoil) implements Packet {
    public static final Type<CSProjectileWeaponRecoilPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "projectile_weapon_recoil")
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, CSProjectileWeaponRecoilPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT,
            CSProjectileWeaponRecoilPacket::recoil,
            CSProjectileWeaponRecoilPacket::new
    );

    @Override
    public void handle(IPayloadContext context) {
        ClientProjectileWeaponRecoil.add(recoil);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
