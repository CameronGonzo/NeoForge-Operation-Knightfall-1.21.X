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
import net.uhhitscam.starwars.component.GasAmmoData;
import net.uhhitscam.starwars.component.GasTypeData;
import net.uhhitscam.starwars.component.ModDataComponentTypes;

public record SSReloadPacket(ItemStack blaster, int ammo, String gasType) implements Packet {
    public static final Type<SSReloadPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("starwars", "reload"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SSReloadPacket> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC,  // Correctly use the ItemStack codec from the example
            SSReloadPacket::blaster,
            ByteBufCodecs.INT,      // For the ammo count
            SSReloadPacket::ammo,
            ByteBufCodecs.STRING_UTF8,
            SSReloadPacket::gasType,
            SSReloadPacket::new
    );

    @Override
    public void handle(IPayloadContext context) {
        System.out.println("running SSReloadPacket.handle");
        Player player = context.player();
        Level level = player.level();

        if (!level.isClientSide) {
            System.out.println("SSGasAmmoPacket.handle is serverside");
            // Get the ItemStack from the player's inventory
            ItemStack serverBlasterStack = player.getItemInHand(player.getUsedItemHand());

            // Update ammo
            serverBlasterStack.set(ModDataComponentTypes.GAS_AMMO.get(), new GasAmmoData(ammo));

            // Update gas type
            if (gasType != null) {
                serverBlasterStack.set(ModDataComponentTypes.GAS_TYPE.get(), new GasTypeData(gasType));
            }

            // Sync changes
            player.inventoryMenu.broadcastChanges();
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}