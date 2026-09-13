package net.uhhitscam.knightfall.item.custom.melee;

public final class MeleeInputRules {
    private MeleeInputRules() {}

    public static MeleeInput hold(MeleeWeaponForm form, boolean falling, boolean running) {
        if (falling && form.attack(MeleeInput.FALL_HOLD_RIGHT_CLICK) != null) return MeleeInput.FALL_HOLD_RIGHT_CLICK;
        if (running && form.attack(MeleeInput.RUN_HOLD_RIGHT_CLICK) != null) return MeleeInput.RUN_HOLD_RIGHT_CLICK;
        return form.attack(MeleeInput.HOLD_RIGHT_CLICK) != null ? MeleeInput.HOLD_RIGHT_CLICK : null;
    }

    public static boolean isTap(long elapsed, int threshold, boolean consumed) {
        return !consumed && elapsed < threshold;
    }
}
