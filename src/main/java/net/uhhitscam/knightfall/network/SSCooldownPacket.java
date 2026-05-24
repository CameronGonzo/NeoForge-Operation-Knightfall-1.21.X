package net.uhhitscam.knightfall.network;

import io.netty.buffer.ByteBuf;
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
import net.uhhitscam.knightfall.item.custom.WeaponCooldownAction;

public record SSCooldownPacket(boolean mainHand, WeaponCooldownAction action) implements Packet {
    public static final Type<SSCooldownPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath("knightfall", "blaster_cooldown")
    );

    private static final StreamCodec<ByteBuf, WeaponCooldownAction> ACTION_CODEC =
            ByteBufCodecs.INT.map(WeaponCooldownAction::byId, WeaponCooldownAction::id);

    public static final StreamCodec<RegistryFriendlyByteBuf, SSCooldownPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            SSCooldownPacket::mainHand,
            ACTION_CODEC,
            SSCooldownPacket::action,
            SSCooldownPacket::new
    );

    public SSCooldownPacket(boolean mainHand, boolean reloading) {
        this(mainHand, reloading ? WeaponCooldownAction.RELOAD : WeaponCooldownAction.SWITCH);
    }

    @Override
    public void handle(IPayloadContext context) {
        Player player = context.player();
        Level level = player.level();

        if (level.isClientSide) {
            return;
        }

        ItemStack stack = mainHand ? player.getMainHandItem() : player.getOffhandItem();

        if (stack.getItem() instanceof ProjectileItem blasterItem) {
            blasterItem.startCooldown(player, stack, action);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}