package net.uhhitscam.knightfall.event;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.util.SmokeScreenUtil;

@EventBusSubscriber(modid = OperationKnightfall.MODID)
public final class SmokeScreenEvents {
    private SmokeScreenEvents() {
    }

    @SubscribeEvent
    public static void onLivingChangeTarget(LivingChangeTargetEvent event) {
        LivingEntity newTarget = event.getNewAboutToBeSetTarget();
        LivingEntity observer = event.getEntity();
        if (newTarget == null || observer.level() != newTarget.level()) {
            return;
        }

        if (SmokeScreenUtil.blocksVision(
                observer.level(),
                observer.getEyePosition(),
                newTarget.getEyePosition()
        )) {
            event.setNewAboutToBeSetTarget(null);
        }
    }
}
