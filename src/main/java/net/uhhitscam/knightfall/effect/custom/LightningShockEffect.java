package net.uhhitscam.knightfall.effect.custom;

import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.effect.ModEffects;

public final class LightningShockEffect extends MobEffect {
    // Amplifier zero halves walking speed, without immobilizing entities or disabling jumps.
    public static final double MOVEMENT_SPEED_REDUCTION = 0.5;

    public LightningShockEffect(MobEffectCategory category, int color) {
        super(category, color);
        addAttributeModifier(Attributes.MOVEMENT_SPEED,
                Identifier.fromNamespaceAndPath(OperationKnightfall.MODID, "effect.lightning_shock"),
                -MOVEMENT_SPEED_REDUCTION, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    @Override
    public void onEffectStarted(LivingEntity entity, int amplifier) {
        entity.stopUsingItem();
    }

    /** Shared entry point for electrical attacks; vanilla synchronizes and expires the effect. */
    public static void apply(LivingEntity entity, int durationTicks) {
        if (durationTicks > 0 && entity.isAlive() && !entity.isSpectator()) {
            // The screen animation replaces effect particles and a status icon.
            entity.addEffect(new MobEffectInstance(ModEffects.LIGHTNING_SHOCK_EFFECT,
                    durationTicks, 0, false, false, false));
        }
    }

    public static boolean isShocked(LivingEntity entity) {
        return entity.hasEffect(ModEffects.LIGHTNING_SHOCK_EFFECT);
    }
}
