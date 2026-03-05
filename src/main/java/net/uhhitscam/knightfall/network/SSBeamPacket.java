package net.uhhitscam.knightfall.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.item.custom.FiringMode;
import net.uhhitscam.knightfall.item.custom.ProjectileItem;
import net.uhhitscam.knightfall.util.BeamLogic;

public record SSBeamPacket(boolean mainHand, boolean active) implements Packet {
    public static final Type<SSBeamPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "beam"));
    public static final StreamCodec<FriendlyByteBuf, SSBeamPacket> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.BOOL, SSBeamPacket::mainHand,
                    ByteBufCodecs.BOOL, SSBeamPacket::active,
                    SSBeamPacket::new
            );

    @Override
    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player)) return;

            boolean mainHand = mainHand();
            ItemStack stack = mainHand ? player.getMainHandItem() : player.getOffhandItem();

            if (!(stack.getItem() instanceof ProjectileItem weapon)) return;
            if (weapon.getFiringMode(stack) != FiringMode.BEAM) return;

            if (active()) {
                BeamLogic.startOrUpdateBeam(player, weapon, stack, mainHand);
            } else {
                BeamLogic.stopBeam(player, mainHand);
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}