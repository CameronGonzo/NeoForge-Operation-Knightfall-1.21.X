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
import net.uhhitscam.starwars.component.ModDataComponentTypes;

public record SSGasAmmoPacket(ItemStack gasStack, int ammo) implements Packet {
    public static final Type<SSGasAmmoPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("starwars", "sync_gas_ammo"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SSGasAmmoPacket> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC,  // Stream codec for the ItemStack
            SSGasAmmoPacket::gasStack,
            ByteBufCodecs.INT,      // Stream codec for the ammo count
            SSGasAmmoPacket::ammo,
            SSGasAmmoPacket::new
    );

    @Override
    public void handle(IPayloadContext context) {
        System.out.println("running SSGasAmmoPacket.handle");
        Player player = context.player();
        Level level = player.level();

        if (!level.isClientSide) {
            System.out.println("SSGasAmmoPacket.handle is serverside");
            // Update the gas stack's ammo data
            gasStack.set(ModDataComponentTypes.GAS_AMMO.get(), new GasAmmoData(ammo));

            // Sync the changes to the player's inventory
            player.inventoryMenu.broadcastChanges();
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
