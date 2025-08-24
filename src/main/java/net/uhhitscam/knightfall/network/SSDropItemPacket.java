package net.uhhitscam.knightfall.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SSDropItemPacket(ItemStack stack) implements Packet {
    public static final Type<SSDropItemPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("knightfall", "drop_item"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SSDropItemPacket> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC,  // Correctly use the ItemStack codec from the example
            SSDropItemPacket::stack,
            SSDropItemPacket::new
    );

    @Override
    public void handle(IPayloadContext context) {
        Player player = context.player();
        player.drop(stack, false);
        player.inventoryMenu.broadcastChanges();
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}