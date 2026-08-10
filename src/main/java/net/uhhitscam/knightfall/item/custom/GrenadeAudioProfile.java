package net.uhhitscam.knightfall.item.custom;

import java.util.Objects;

public record GrenadeAudioProfile(
        GrenadeSound throwSound,
        GrenadeSound bounceSound,
        GrenadeSound activationSound,
        GrenadeSound beepSound,
        int normalBeepIntervalTicks,
        int urgentBeepIntervalTicks,
        int urgentThresholdTicks
) {
    public GrenadeAudioProfile {
        Objects.requireNonNull(throwSound, "Grenade throw sound cannot be null.");
        Objects.requireNonNull(bounceSound, "Grenade bounce sound cannot be null.");
        Objects.requireNonNull(activationSound, "Grenade activation sound cannot be null.");
        Objects.requireNonNull(beepSound, "Grenade beep sound cannot be null.");

        if (normalBeepIntervalTicks <= 0) {
            throw new IllegalArgumentException("Normal grenade beep interval must be greater than 0.");
        }
        if (urgentBeepIntervalTicks <= 0) {
            throw new IllegalArgumentException("Urgent grenade beep interval must be greater than 0.");
        }
        if (urgentThresholdTicks < 0) {
            throw new IllegalArgumentException("Urgent grenade beep threshold cannot be negative.");
        }
    }

    public boolean shouldPlayBeep(int remainingFuseTicks, int fullFuseTicks) {
        if (remainingFuseTicks <= 0 || remainingFuseTicks >= fullFuseTicks) {
            return false;
        }

        int interval = remainingFuseTicks <= urgentThresholdTicks
                ? urgentBeepIntervalTicks
                : normalBeepIntervalTicks;
        return remainingFuseTicks % interval == 0;
    }
}
