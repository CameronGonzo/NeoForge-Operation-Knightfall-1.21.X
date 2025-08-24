package net.uhhitscam.knightfall.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Objects;

public record AmmoData(int ammo) {
    public static final Codec<AmmoData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(Codec.INT.fieldOf("gas_ammo").forGetter(AmmoData::ammo))
                    .apply(instance, AmmoData::new));

    public boolean isEmpty() {
        return ammo <= 0;
    }

    public AmmoData withAmmo(int newAmmo) {
        return new AmmoData(Math.max(0, newAmmo));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.ammo);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else {
            return obj instanceof AmmoData fad && this.ammo == fad.ammo;
        }
    }
}
