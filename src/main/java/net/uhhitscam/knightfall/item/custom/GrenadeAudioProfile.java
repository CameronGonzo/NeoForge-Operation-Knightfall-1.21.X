package net.uhhitscam.knightfall.item.custom;

import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Objects;

public record GrenadeAudioProfile(
        GrenadeSound throwSound,
        GrenadeSound bounceSound,
        GrenadeSound activationSound,
        GrenadeSound beepSound,
        int normalBeepIntervalTicks,
        int urgentBeepIntervalTicks,
        int urgentThresholdTicks,
        List<GrenadeSound> orderedBeepSounds
) {
    public GrenadeAudioProfile(
            GrenadeSound throwSound,
            GrenadeSound bounceSound,
            GrenadeSound activationSound,
            GrenadeSound beepSound,
            int normalBeepIntervalTicks,
            int urgentBeepIntervalTicks,
            int urgentThresholdTicks
    ) {
        this(
                throwSound,
                bounceSound,
                activationSound,
                beepSound,
                normalBeepIntervalTicks,
                urgentBeepIntervalTicks,
                urgentThresholdTicks,
                List.of()
        );
    }

    public GrenadeAudioProfile {
        Objects.requireNonNull(throwSound, "Grenade throw sound cannot be null.");
        Objects.requireNonNull(bounceSound, "Grenade bounce sound cannot be null.");
        Objects.requireNonNull(activationSound, "Grenade activation sound cannot be null.");
        Objects.requireNonNull(beepSound, "Grenade beep sound cannot be null.");
        orderedBeepSounds = List.copyOf(Objects.requireNonNull(
                orderedBeepSounds,
                "Ordered grenade beep sounds cannot be null."
        ));

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
        if (remainingFuseTicks < 0 || remainingFuseTicks >= fullFuseTicks) {
            return false;
        }
        if (remainingFuseTicks == 0) {
            return !orderedBeepSounds.isEmpty();
        }

        int interval = remainingFuseTicks <= urgentThresholdTicks
                ? urgentBeepIntervalTicks
                : normalBeepIntervalTicks;
        return remainingFuseTicks % interval == 0;
    }

    public void playBeep(Level level, Vec3 position, int remainingFuseTicks, int fullFuseTicks) {
        GrenadeSound sound = beepSound;
        if (!orderedBeepSounds.isEmpty()) {
            int sequenceIndex = beepSequenceIndex(remainingFuseTicks, fullFuseTicks);
            sound = orderedBeepSounds.get(Math.min(sequenceIndex, orderedBeepSounds.size() - 1));
        }

        sound.play(level, position);
    }

    private int beepSequenceIndex(int remainingFuseTicks, int fullFuseTicks) {
        int beepCount = 0;
        for (int fuseTicks = fullFuseTicks - 1; fuseTicks >= remainingFuseTicks; fuseTicks--) {
            if (shouldPlayBeep(fuseTicks, fullFuseTicks)) {
                beepCount++;
            }
        }
        return Math.max(0, beepCount - 1);
    }
}
