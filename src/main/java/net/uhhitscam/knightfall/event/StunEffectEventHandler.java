package net.uhhitscam.knightfall.event;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.block.BreakBlockEvent;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.effect.custom.StunEffect;
import net.uhhitscam.knightfall.effect.custom.LightningShockEffect;
import net.uhhitscam.knightfall.effect.EffectActionRules;

@EventBusSubscriber(modid = OperationKnightfall.MODID)
public final class StunEffectEventHandler {
    private StunEffectEventHandler() {}

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (cancelIfActionsBlocked(event.getEntity(), event)) {
            event.setCancellationResult(InteractionResult.FAIL);
        }
    }

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        if (cancelIfActionsBlocked(event.getEntity(), event)) {
            event.setCancellationResult(InteractionResult.FAIL);
        }
    }

    @SubscribeEvent
    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        cancelIfActionsBlocked(event.getEntity(), event);
    }

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (cancelIfActionsBlocked(event.getEntity(), event)) {
            event.setCancellationResult(InteractionResult.FAIL);
        }
    }

    @SubscribeEvent
    public static void onPlayerAttack(AttackEntityEvent event) {
        cancelIfActionsBlocked(event.getEntity(), event);
    }

    @SubscribeEvent
    public static void onItemUseStart(LivingEntityUseItemEvent.Start event) {
        cancelIfActionsBlocked(event.getEntity(), event);
    }

    @SubscribeEvent
    public static void onBlockBreak(BreakBlockEvent event) {
        cancelIfActionsBlocked(event.getPlayer(), event);
    }

    @SubscribeEvent
    public static void onIncomingDamage(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker
                && (StunEffect.isStunned(attacker)
                // Shock blocks direct attacks, not grenades or projectiles already launched before being shocked.
                || LightningShockEffect.isShocked(attacker) && event.getSource().getDirectEntity() == attacker)) {
            event.setCanceled(true);
        }
    }

    private static boolean cancelIfActionsBlocked(LivingEntity entity, ICancellableEvent event) {
        if (EffectActionRules.blocksActions(entity)) {
            event.setCanceled(true);
            return true;
        }

        return false;
    }
}
