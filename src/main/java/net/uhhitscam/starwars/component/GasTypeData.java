package net.uhhitscam.starwars.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Objects;

public record GasTypeData(String gasType) {
    public static final Codec<GasTypeData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("gasType").forGetter(GasTypeData::gasType)
            ).apply(instance, GasTypeData::new));

    @Override
    public int hashCode() {
        return Objects.hash(gasType);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else {
            return obj instanceof GasTypeData other && Objects.equals(this.gasType, other.gasType);
        }
    }
}
