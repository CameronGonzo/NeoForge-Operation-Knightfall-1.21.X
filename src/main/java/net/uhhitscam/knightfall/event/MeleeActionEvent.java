package net.uhhitscam.knightfall.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;
import net.uhhitscam.knightfall.item.custom.melee.MeleeAttack;

public final class MeleeActionEvent extends Event {
    private final ServerPlayer player;
    private final InteractionHand hand;
    private final ItemStack stack;
    private final MeleeAttack attack;
    private final boolean charging;

    public MeleeActionEvent(ServerPlayer player, InteractionHand hand, ItemStack stack, MeleeAttack attack, boolean charging) {
        this.player = player; this.hand = hand; this.stack = stack.copy(); this.attack = attack; this.charging = charging;
    }

    public ServerPlayer getPlayer() { return player; }
    public InteractionHand getHand() { return hand; }
    public ItemStack getStack() { return stack; }
    public MeleeAttack getAttack() { return attack; }
    public boolean isCharging() { return charging; }
}
