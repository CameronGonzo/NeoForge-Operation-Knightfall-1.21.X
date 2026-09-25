package net.uhhitscam.knightfall.item.custom.grenade;

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
        if (definition.smokeProfile() != null) {
            return ACTIVE;
        }

        if (definition.trigger().sticksToBlocks()) {
            return INACTIVE;
        }

        if (definition.visualFlashIntervalTicks() > 0) {
            boolean repeatWhileHeld = definition.fuseSoundMode()
                    != GrenadeFuseSoundMode.LOOP_UNTIL_REMOVED;
            return (useTicks < BEEP_FLASH_TICKS
                    || repeatWhileHeld
                    && isPeriodicFlashActive(useTicks, definition.visualFlashIntervalTicks()))
                    ? BEEP
                    : ACTIVE;
        }

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
            int remainingFuseTicks,
            boolean fuseRunning
    ) {
        if (!fuseRunning) {
            return definition.smokeProfile() != null ? ACTIVE : INACTIVE;
        }

        return isBeepFlashActive(definition, remainingFuseTicks) ? BEEP : ACTIVE;
    }

    private static boolean isBeepFlashActive(GrenadeDefinition definition, int remainingFuseTicks) {
        if (definition.visualFlashIntervalTicks() > 0) {
            int elapsedFuseTicks = Math.max(0, definition.fuseTicks() - remainingFuseTicks);
            return isPeriodicFlashActive(elapsedFuseTicks, definition.visualFlashIntervalTicks());
        }

        for (int elapsedTicks = 0; elapsedTicks < BEEP_FLASH_TICKS; elapsedTicks++) {
            int beepFuseTicks = remainingFuseTicks + elapsedTicks;
            if (beepFuseTicks <= definition.fuseTicks()
                    && definition.audio().shouldPlayBeep(beepFuseTicks, definition.fuseTicks())) {
                return true;
            }
        }

        return false;
    }

    private static boolean isPeriodicFlashActive(int elapsedTicks, int intervalTicks) {
        return elapsedTicks > 0 && elapsedTicks % intervalTicks < BEEP_FLASH_TICKS;
    }
}
