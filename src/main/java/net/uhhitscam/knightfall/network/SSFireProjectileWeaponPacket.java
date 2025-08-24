package net.uhhitscam.knightfall.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.uhhitscam.knightfall.item.custom.ProjectileItem;

public record SSFireProjectileWeaponPacket(boolean mainHand) implements Packet {
    public static final Type<SSFireProjectileWeaponPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("knightfall", "fire_projectile_weapon"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SSFireProjectileWeaponPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            SSFireProjectileWeaponPacket::mainHand,
            SSFireProjectileWeaponPacket::new
    );

    @Override
    public void handle(IPayloadContext context) {
        Player player = context.player();
        Level level = player.level();

        if (!level.isClientSide) {
            if (mainHand) {
                ItemStack stack = player.getMainHandItem();

                if (stack.getItem() instanceof ProjectileItem projectileItem) {
                    projectileItem.mainHandFiring(player);
                }
            } else {
                ItemStack stack = player.getOffhandItem();

                if (stack.getItem() instanceof ProjectileItem projectileItem) {
                    projectileItem.offHandFiring(player);
                }
            }
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
