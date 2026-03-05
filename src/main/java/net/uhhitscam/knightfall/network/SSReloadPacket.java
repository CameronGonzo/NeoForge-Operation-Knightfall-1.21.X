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
import net.uhhitscam.knightfall.component.AmmoTypeData;
import net.uhhitscam.knightfall.component.AmmoData;
import net.uhhitscam.knightfall.component.ModDataComponentTypes;

public record SSReloadPacket(ItemStack blaster, int ammo, String ammoType, boolean mainHand) implements Packet {
    public static final Type<SSReloadPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("knightfall", "reload"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SSReloadPacket> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC,
            SSReloadPacket::blaster,
            ByteBufCodecs.INT,
            SSReloadPacket::ammo,
            ByteBufCodecs.STRING_UTF8,
            SSReloadPacket::ammoType,
            ByteBufCodecs.BOOL,
            SSReloadPacket::mainHand,
            SSReloadPacket::new
    );

    @Override
    public void handle(IPayloadContext context) {
        Player player = context.player();
        Level level = player.level();

        if (!level.isClientSide) {
            if (mainHand) {
                ItemStack serverBlasterStack = player.getMainHandItem();
                serverBlasterStack.set(ModDataComponentTypes.AMMO.get(), new AmmoData(ammo));

                if (ammoType != null) {
                    serverBlasterStack.set(ModDataComponentTypes.AMMO_TYPE.get(), new AmmoTypeData(ammoType));
                }
            } else {
                ItemStack serverBlasterStack = player.getOffhandItem();
                serverBlasterStack.set(ModDataComponentTypes.AMMO.get(), new AmmoData(ammo));

                if (ammoType != null) {
                    serverBlasterStack.set(ModDataComponentTypes.AMMO_TYPE.get(), new AmmoTypeData(ammoType));
                }
            }

            player.inventoryMenu.broadcastChanges();
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}