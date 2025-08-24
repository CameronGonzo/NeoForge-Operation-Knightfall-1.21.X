package net.uhhitscam.knightfall.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.uhhitscam.knightfall.util.InventoryUtil;

public record SSGiveItemPacket(ItemStack stack) implements Packet {
    public static final Type<SSGiveItemPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("knightfall", "give_item"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SSGiveItemPacket> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC,
            SSGiveItemPacket::stack,
            SSGiveItemPacket::new
    );

    @Override
    public void handle(IPayloadContext context) {
        Player player = context.player();
        InventoryUtil.giveItem(player, stack);
        player.inventoryMenu.broadcastChanges();
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}