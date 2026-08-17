package net.uhhitscam.knightfall.item.custom;

import net.minecraft.world.level.Level;

import java.util.Objects;

public record AttachedExplosiveSpec(
        int fuseTicks,
        int normalBeepIntervalTicks,
        int urgentBeepIntervalTicks,
        int urgentThresholdTicks,
        MeleeWeaponSound normalBeepSound,
        MeleeWeaponSound urgentBeepSound,
        MeleeWeaponSound detonationSound,
        double entityRadius,
        float damage,
        double knockback,
        float blockBreakRadius,
        Level.ExplosionInteraction blockInteraction,
        boolean causesFire
) {
    public AttachedExplosiveSpec {
        if (fuseTicks <= 0) {
            throw new IllegalArgumentException("Attached explosive fuse must be greater than 0.");
        }
        if (normalBeepIntervalTicks <= 0 || urgentBeepIntervalTicks <= 0) {
            throw new IllegalArgumentException("Attached explosive beep intervals must be greater than 0.");
        }
        if (urgentThresholdTicks < 0 || urgentThresholdTicks > fuseTicks) {
            throw new IllegalArgumentException("Attached explosive urgent threshold must be within its fuse.");
        }
        if (entityRadius <= 0.0 || damage < 0.0F || knockback < 0.0 || blockBreakRadius < 0.0F) {
            throw new IllegalArgumentException("Attached explosive effect values cannot be negative or empty.");
        }
        Objects.requireNonNull(normalBeepSound, "Attached explosive normal beep sound cannot be null.");
        Objects.requireNonNull(urgentBeepSound, "Attached explosive urgent beep sound cannot be null.");
        Objects.requireNonNull(detonationSound, "Attached explosive detonation sound cannot be null.");
        Objects.requireNonNull(blockInteraction, "Attached explosive block interaction cannot be null.");
        if (blockBreakRadius == 0.0F && blockInteraction != Level.ExplosionInteraction.NONE) {
            throw new IllegalArgumentException("An attached explosive without terrain damage must use NONE interaction.");
        }
        if (blockBreakRadius > 0.0F && blockInteraction == Level.ExplosionInteraction.NONE) {
            throw new IllegalArgumentException("An attached explosive with terrain damage must declare an interaction.");
        }
    }

    public boolean shouldPlayBeep(int remainingFuseTicks) {
        if (remainingFuseTicks <= 0 || remainingFuseTicks >= fuseTicks) {
            return false;
        }
        return remainingFuseTicks % beepInterval(remainingFuseTicks) == 0;
    }

    public int beepInterval(int remainingFuseTicks) {
        return remainingFuseTicks <= urgentThresholdTicks
                ? urgentBeepIntervalTicks
                : normalBeepIntervalTicks;
    }

    public MeleeWeaponSound beepSound(int remainingFuseTicks) {
        return remainingFuseTicks <= urgentThresholdTicks ? urgentBeepSound : normalBeepSound;
    }
}
