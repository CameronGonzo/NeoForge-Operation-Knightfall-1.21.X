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
import net.uhhitscam.knightfall.component.AmmoData;
import net.uhhitscam.knightfall.component.ModDataComponentTypes;
import net.uhhitscam.knightfall.item.ModItems;
import net.uhhitscam.knightfall.item.custom.GasItem;
import net.uhhitscam.knightfall.util.InventoryUtil;

public record SSGasAmmoPacket(ItemStack gasStack, int ammo, int i) implements Packet {
    public static final Type<SSGasAmmoPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("knightfall", "sync_gas_ammo"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SSGasAmmoPacket> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC,  // Stream codec for the ItemStack
            SSGasAmmoPacket::gasStack,
            ByteBufCodecs.INT,      // Stream codec for the ammo count
            SSGasAmmoPacket::ammo,
            ByteBufCodecs.INT,
            SSGasAmmoPacket::i,
            SSGasAmmoPacket::new
    );

    @Override
    public void handle(IPayloadContext context) {
        Player player = context.player();
        Level level = player.level();

        if (!level.isClientSide) {
            ItemStack stack = player.getInventory().getItem(i);

            // Update the gas stack's ammo data
            stack.set(ModDataComponentTypes.AMMO.get(), new AmmoData(ammo));

            if (stack.getItem() instanceof GasItem gasItem) {
                if (gasItem.getAmmo(stack) == 0 || stack.get(ModDataComponentTypes.AMMO.get()).ammo() == 0) {
                    ItemStack gasCartridge = new ItemStack(ModItems.GAS_CARTRIDGE.get());
                    InventoryUtil.replaceItem(stack, player, gasCartridge, i);
                }
            }

            // Sync the changes to the player's inventory
            player.inventoryMenu.broadcastChanges();
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
