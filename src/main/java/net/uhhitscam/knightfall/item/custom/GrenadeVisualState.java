package net.uhhitscam.knightfall.item.custom;

public enum GrenadeVisualState {
    INACTIVE(0.0F),
    ACTIVE(1.0F),
    BEEP(2.0F);

    private static final int BEEP_FLASH_TICKS = 3;

    private final float modelPredicateValue;

    GrenadeVisualState(float modelPredicateValue) {
        this.modelPredicateValue = modelPredicateValue;
    }

    public float modelPredicateValue() {
        return modelPredicateValue;
    }

    public static GrenadeVisualState forHeldGrenade(
            GrenadeDefinition definition,
            int useTicks,
            int remainingUseTicks
    ) {
        if (useTicks < BEEP_FLASH_TICKS) {
            return BEEP;
        }

        if (definition.trigger().detonatesOnFuse()
                && isBeepFlashActive(definition, remainingUseTicks)) {
            return BEEP;
        }

        return ACTIVE;
    }

    public static GrenadeVisualState forThrownGrenade(
            GrenadeDefinition definition,
            int remainingFuseTicks
    ) {
        return isBeepFlashActive(definition, remainingFuseTicks) ? BEEP : ACTIVE;
    }

    private static boolean isBeepFlashActive(GrenadeDefinition definition, int remainingFuseTicks) {
        for (int elapsedTicks = 0; elapsedTicks < BEEP_FLASH_TICKS; elapsedTicks++) {
            int beepFuseTicks = remainingFuseTicks + elapsedTicks;
            if (beepFuseTicks <= definition.fuseTicks()
                    && definition.audio().shouldPlayBeep(beepFuseTicks, definition.fuseTicks())) {
                return true;
            }
        }

        return false;
    }
}
