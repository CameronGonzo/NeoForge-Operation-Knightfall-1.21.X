package net.uhhitscam.knightfall.effect.custom;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import net.uhhitscam.knightfall.effect.ModEffects;

public final class StunEffect extends MobEffect {
    public static final int DURATION_TICKS = 12 * 20;

    public StunEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onEffectStarted(LivingEntity entity, int amplifier) {
        entity.stopUsingItem();

        Vec3 movement = entity.getDeltaMovement();
        entity.setDeltaMovement(0.0, movement.y, 0.0);

        if (entity instanceof Mob mob) {
            mob.getNavigation().stop();
        }
    }

    public static boolean isStunned(LivingEntity entity) {
        return entity.hasEffect(ModEffects.STUN_EFFECT);
    }
}
