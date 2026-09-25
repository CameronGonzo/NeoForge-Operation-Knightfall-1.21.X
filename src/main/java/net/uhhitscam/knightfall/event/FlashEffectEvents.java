package net.uhhitscam.knightfall.event;

import net.minecraft.world.entity.Mob;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.util.FlashEffectTracker;

@EventBusSubscriber(modid = OperationKnightfall.MODID)
public final class FlashEffectEvents {
    private FlashEffectEvents() {
    }

    @SubscribeEvent
    public static void onLivingChangeTarget(LivingChangeTargetEvent event) {
        if (event.getNewAboutToBeSetTarget() != null
                && event.getEntity() instanceof Mob mob
                && FlashEffectTracker.isFlashed(mob)) {
            event.setNewAboutToBeSetTarget(null);
        }
    }
}
