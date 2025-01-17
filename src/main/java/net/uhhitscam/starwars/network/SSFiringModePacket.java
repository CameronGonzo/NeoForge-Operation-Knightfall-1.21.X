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
import net.uhhitscam.starwars.component.FiringModeData;
import net.uhhitscam.starwars.component.ModDataComponentTypes;
import net.uhhitscam.starwars.item.custom.BlasterItem;

public record SSFiringModePacket(ItemStack blaster, String firingMode) implements Packet {

    public static final Type<SSFiringModePacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("starwars", "firing_mode"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SSFiringModePacket> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC,  // Codec for the ItemStack
            SSFiringModePacket::blaster,
            ByteBufCodecs.STRING_UTF8, // Codec for the firing mode as a string
            SSFiringModePacket::firingMode,
            SSFiringModePacket::new
    );

    @Override
    public void handle(IPayloadContext context) {
        System.out.println("running SSFiringModePacket.handle");
        Player player = context.player();
        Level level = player.level();

        if (!level.isClientSide) {
            System.out.println("SSFiringModePacket.handle is serverside");
            // Get the ItemStack from the player's inventory
            ItemStack serverBlasterStack = player.getItemInHand(player.getUsedItemHand());

            if (serverBlasterStack.getItem() instanceof BlasterItem) {
                BlasterItem blasterItem = (BlasterItem) serverBlasterStack.getItem();

                // Update the firing mode in the blaster's data
                serverBlasterStack.set(ModDataComponentTypes.FIRING_MODE.get(), new FiringModeData(firingMode));

                // Sync changes to the player's inventory
                player.inventoryMenu.broadcastChanges();
            }
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
