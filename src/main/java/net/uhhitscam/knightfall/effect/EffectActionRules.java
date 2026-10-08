package net.uhhitscam.knightfall.effect;

import net.minecraft.world.entity.LivingEntity;
import net.uhhitscam.knightfall.effect.custom.LightningShockEffect;
import net.uhhitscam.knightfall.effect.custom.StunEffect;

public final class EffectActionRules {
    private EffectActionRules() {}

    public static boolean blocksActions(LivingEntity entity) {
        return StunEffect.isStunned(entity) || LightningShockEffect.isShocked(entity);
    }
}
