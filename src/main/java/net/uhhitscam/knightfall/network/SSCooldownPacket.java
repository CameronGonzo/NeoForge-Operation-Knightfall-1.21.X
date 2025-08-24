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
import net.uhhitscam.knightfall.item.custom.ProjectileItem;

public record SSCooldownPacket(boolean mainHand, boolean reloading) implements Packet {
    public static final Type<SSCooldownPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("knightfall", "blaster_cooldown"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SSCooldownPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            SSCooldownPacket::mainHand,
            ByteBufCodecs.BOOL,
            SSCooldownPacket::reloading,
            SSCooldownPacket::new);

    @Override
    public void handle(IPayloadContext context) {
        Player player = context.player();
        Level level = player.level();

        if (!level.isClientSide) {
            ItemStack stack = mainHand ? player.getMainHandItem() : player.getOffhandItem();

            if (stack.getItem() instanceof ProjectileItem blasterItem) {
                //Call your cooldown method
                blasterItem.startCooldown(player, stack, reloading);
            }
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

