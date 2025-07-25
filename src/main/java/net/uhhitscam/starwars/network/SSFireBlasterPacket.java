package net.uhhitscam.starwars.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.uhhitscam.starwars.item.custom.BlasterItem;

public record SSFireBlasterPacket(ItemStack blaster, boolean repeat, boolean mainHand) implements Packet {
    //Define the packet type and stream codec for serialization/deserialization
    public static final Type<SSFireBlasterPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("starwars", "fire_blaster"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SSFireBlasterPacket> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC,         //The ItemStack codec to serialize/deserialize the blaster
            SSFireBlasterPacket::blaster,   //The blaster item
            ByteBufCodecs.BOOL,
            SSFireBlasterPacket::repeat,
            ByteBufCodecs.BOOL,
            SSFireBlasterPacket::mainHand,
            SSFireBlasterPacket::new        //Constructor for the packet
    );

    @Override
    public void handle(IPayloadContext context) {
        Player player = context.player();
        Level level = player.level();

        if (!level.isClientSide) {
            if (mainHand) {
                ItemStack serverBlasterStack = player.getMainHandItem();

                if (serverBlasterStack.getItem() instanceof BlasterItem blasterItem) {
                    blasterItem.mainHandFiring(player);
                }
            } else {
                ItemStack serverBlasterStack = player.getOffhandItem();

                if (serverBlasterStack.getItem() instanceof BlasterItem blasterItem) {
                    blasterItem.offHandFiring(player);
                }
            }
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
