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
import net.uhhitscam.starwars.component.GasAmmoData;
import net.uhhitscam.starwars.component.GasTypeData;
import net.uhhitscam.starwars.component.ModDataComponentTypes;
import net.uhhitscam.starwars.item.custom.BlasterItem;

public record SSFiringModePacket(ItemStack blaster, String firingMode, boolean mainHand) implements Packet {

    public static final Type<SSFiringModePacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("starwars", "firing_mode"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SSFiringModePacket> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC,  // Codec for the ItemStack
            SSFiringModePacket::blaster,
            ByteBufCodecs.STRING_UTF8, // Codec for the firing mode as a string
            SSFiringModePacket::firingMode,
            ByteBufCodecs.BOOL,
            SSFiringModePacket::mainHand,
            SSFiringModePacket::new
    );

    @Override
    public void handle(IPayloadContext context) {
        Player player = context.player();
        Level level = player.level();

        if (!level.isClientSide) {
            if (mainHand) {
                ItemStack serverBlasterStack = player.getMainHandItem();
                //Update the firing mode in the blaster's data
                serverBlasterStack.set(ModDataComponentTypes.FIRING_MODE.get(), new FiringModeData(firingMode));

            } else {
                ItemStack serverBlasterStack = player.getOffhandItem();
                //Update the firing mode in the blaster's data
                serverBlasterStack.set(ModDataComponentTypes.FIRING_MODE.get(), new FiringModeData(firingMode));

            }

            player.inventoryMenu.broadcastChanges();
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
