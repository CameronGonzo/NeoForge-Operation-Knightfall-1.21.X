package net.uhhitscam.knightfall.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Objects;

public record AmmoTypeData(String ammoType) {
    public static final Codec<AmmoTypeData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("ammoType").forGetter(AmmoTypeData::ammoType)
            ).apply(instance, AmmoTypeData::new));

    @Override
    public int hashCode() {
        return Objects.hash(ammoType);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else {
            return obj instanceof AmmoTypeData other && Objects.equals(this.ammoType, other.ammoType);
        }
    }
}
