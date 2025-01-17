package net.uhhitscam.starwars.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Objects;

public record FiringModeData(String firingMode) {
    public static final Codec<FiringModeData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("firingMode").forGetter(FiringModeData::firingMode)
            ).apply(instance, FiringModeData::new));

    public FiringModeData withFiringMode(String newMode) {
        return new FiringModeData(newMode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firingMode);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else {
            return obj instanceof FiringModeData other && Objects.equals(this.firingMode, other.firingMode);
        }
    }
}