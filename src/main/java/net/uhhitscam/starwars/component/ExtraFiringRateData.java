package net.uhhitscam.starwars.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.Level;

import java.util.Objects;

public record ExtraFiringRateData(long cooldownEndTime, int shotsFired, boolean mainHand) {
    public static final Codec<ExtraFiringRateData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.LONG.fieldOf("cooldownEndTime").forGetter(ExtraFiringRateData::cooldownEndTime),
                    Codec.INT.fieldOf("shotsFired").forGetter(ExtraFiringRateData::shotsFired),
                    Codec.BOOL.fieldOf("mainHand").forGetter(ExtraFiringRateData::mainHand)
            ).apply(instance, ExtraFiringRateData::new));

    public ExtraFiringRateData withCooldownEndTime(long newCooldownEndTime) {
        return new ExtraFiringRateData(newCooldownEndTime, shotsFired, mainHand);
    }

    public ExtraFiringRateData withShotsFired(int newShotsFired) {
        return new ExtraFiringRateData(cooldownEndTime, newShotsFired, mainHand);
    }

    public ExtraFiringRateData withMaxShots(int newShotsToFire) {
        return new ExtraFiringRateData(cooldownEndTime, newShotsToFire, mainHand);
    }

    public boolean canFire(Level level) {
        return level.getGameTime() >= cooldownEndTime && shotsFired < 3;
    }

    public boolean extraFiring(Level level) {
        return level.getGameTime() < cooldownEndTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cooldownEndTime, shotsFired);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else {
            return obj instanceof ExtraFiringRateData other
                    && cooldownEndTime == other.cooldownEndTime
                    && shotsFired == other.shotsFired
                    && mainHand == other.mainHand;
        }
    }
}