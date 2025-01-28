package net.uhhitscam.starwars.event;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.uhhitscam.starwars.OperationKnightfall;
import net.uhhitscam.starwars.effect.ModEffects;

@EventBusSubscriber(modid = OperationKnightfall.MODID)
public class StunEffectEventHandler {

    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
        if (hasStunEffect(event.getEntity())) {
            event.setCanceled(true); // Cancel block interaction
        }
    }

    @SubscribeEvent
    public static void onPlayerItemUse(PlayerInteractEvent.RightClickItem event) {
        if (hasStunEffect(event.getEntity())) {
            event.setCanceled(true); // Cancel item usage
        }
    }

    @SubscribeEvent
    public static void onPlayerAttack(AttackEntityEvent event) {
        if (hasStunEffect(event.getEntity())) {
            event.setCanceled(true); // Cancel attacking entities
        }
    }

    @SubscribeEvent
    public static void onPlayerBreak(PlayerInteractEvent.LeftClickBlock event) {
        if (hasStunEffect(event.getEntity())) {
            event.setCanceled(true); // Cancel breaking blocks
        }
    }

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (hasStunEffect(event.getEntity())) {
            event.setCanceled(true); // Cancel attacking entities
        }
    }

    private static boolean hasStunEffect(LivingEntity entity) {
        return entity.hasEffect(ModEffects.STUN_EFFECT);
    }
}