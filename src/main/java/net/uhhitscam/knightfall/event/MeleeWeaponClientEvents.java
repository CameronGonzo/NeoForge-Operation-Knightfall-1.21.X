package net.uhhitscam.knightfall.event;

import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.effect.custom.StunEffect;
import net.uhhitscam.knightfall.item.custom.melee.MeleeWeaponItem;
import net.uhhitscam.knightfall.network.PayloadRegister;
import net.uhhitscam.knightfall.network.SSMeleeInputPacket;

@EventBusSubscriber(modid = OperationKnightfall.MODID, value = Dist.CLIENT)
public final class MeleeWeaponClientEvents {
    private static boolean useDown;
    private static ItemStack held = ItemStack.EMPTY;
    private static InteractionHand hand;
    private static int slot;

    private MeleeWeaponClientEvents() {}

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void input(InputEvent.InteractionKeyMappingTriggered event) {
        Minecraft mc = Minecraft.getInstance();
        var player = mc.player;
        if (player == null || mc.screen != null) return;
        if (event.isAttack() && player.getMainHandItem().getItem() instanceof MeleeWeaponItem weapon
                && weapon.getForm(player.getMainHandItem()) != null) {
            // Keep normal block mining
            var attack = weapon.getForm(player.getMainHandItem()).attack(net.uhhitscam.knightfall.item.custom.melee.MeleeInput.LEFT_CLICK);
            boolean extendedTarget = attack != null && attack.trait() == net.uhhitscam.knightfall.item.custom.melee.MeleeAttackTrait.THRUST
                    && net.uhhitscam.knightfall.util.MeleeTargeting.ray(player, player.entityInteractionRange() + 1) != null;
            if (mc.hitResult != null && mc.hitResult.getType() == HitResult.Type.BLOCK && !extendedTarget) return;
            event.setCanceled(true);
            event.setSwingHand(false);
            if (!StunEffect.isStunned(player) && !useDown && !player.isUsingItem()) {
                PayloadRegister.sendToServer(new SSMeleeInputPacket(SSMeleeInputPacket.ATTACK));
                player.swing(InteractionHand.MAIN_HAND);
                player.resetAttackStrengthTicker();
            }
        } else if (event.isUseItem()) {
            InteractionHand useHand = MeleeWeaponServerEvents.useHand(player);
            if (!useDown && useHand == null) return;
            event.setCanceled(true);
            event.setSwingHand(false);
            if (!useDown && !StunEffect.isStunned(player) && !player.isUsingItem()) {
                useDown = true;
                hand = useHand;
                held = player.getItemInHand(hand).copy();
                slot = player.getInventory().selected;
                PayloadRegister.sendToServer(new SSMeleeInputPacket(SSMeleeInputPacket.PRESS));
            }
        }
    }

    @SubscribeEvent
    public static void tick(ClientTickEvent.Post event) {
        if (!useDown) return;
        Minecraft mc = Minecraft.getInstance();
        var player = mc.player;
        if (player == null) { clear(); return; }
        boolean cancel = mc.screen != null || !mc.isWindowActive() || !player.isAlive() || player.isSpectator()
                || StunEffect.isStunned(player) || slot != player.getInventory().selected
                || player.getItemInHand(hand).getItem() != held.getItem();
        if (cancel || !mc.options.keyUse.isDown()) {
            PayloadRegister.sendToServer(new SSMeleeInputPacket(cancel ? SSMeleeInputPacket.CANCEL : SSMeleeInputPacket.RELEASE));
            clear();
        }
    }

    private static void clear() { useDown = false; held = ItemStack.EMPTY; hand = null; }
}
