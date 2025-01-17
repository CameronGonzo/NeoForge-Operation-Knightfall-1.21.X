package net.uhhitscam.starwars.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.uhhitscam.starwars.item.custom.BlasterItem;

public record SSFireBlasterPacket(ItemStack blaster, String gasType) implements Packet {

    // Define the packet type and stream codec for serialization/deserialization
    public static final Type<SSFireBlasterPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("starwars", "fire_blaster"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SSFireBlasterPacket> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC,          // The ItemStack codec to serialize/deserialize the blaster
            SSFireBlasterPacket::blaster,   // The blaster item
            ByteBufCodecs.STRING_UTF8,      // The gas type as a string
            SSFireBlasterPacket::gasType,   // The gas type
            SSFireBlasterPacket::new        // Constructor for the packet
    );

    @Override
    public void handle(IPayloadContext context) {
        System.out.println("running SSFireBlasterPacket.handle");
        Player player = context.player();
        Level level = player.level();

        if (!level.isClientSide) {
            System.out.println("SSGasAmmoPacket.handle is serverside");
            // Get the ItemStack from the player's inventory
            ItemStack serverBlasterStack = player.getItemInHand(player.getUsedItemHand());

            if (serverBlasterStack.getItem() instanceof BlasterItem) {
                BlasterItem blasterItem = (BlasterItem) serverBlasterStack.getItem();

                // Call the firing method on the server-side
                blasterItem.mainHandFiring(player, serverBlasterStack);
            }
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
