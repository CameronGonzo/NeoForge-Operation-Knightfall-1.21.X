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

public record SSConsumeItemPacket(ItemStack stack, int amount, int index) implements Packet {
    public static final Type<SSConsumeItemPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("knightfall", "consume_item"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SSConsumeItemPacket> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC,
            SSConsumeItemPacket::stack,
            ByteBufCodecs.INT,
            SSConsumeItemPacket::amount,
            ByteBufCodecs.INT,
            SSConsumeItemPacket::index,
            SSConsumeItemPacket::new
    );

    @Override
    public void handle(IPayloadContext context) {
        Player player = context.player();
        Level level = player.level();

        if (!level.isClientSide) {
            stack.consume(amount, player);
            if (stack.isEmpty()) {
                player.getInventory().setItem(index, ItemStack.EMPTY);
            } else {
                player.getInventory().setItem(index, stack);
            }

            player.inventoryMenu.broadcastChanges();
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
