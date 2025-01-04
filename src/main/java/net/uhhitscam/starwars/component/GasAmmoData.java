package net.uhhitscam.starwars.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Objects;

public record GasAmmoData(int ammo) {
    public static final Codec<GasAmmoData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(Codec.INT.fieldOf("gas_ammo").forGetter(GasAmmoData::ammo))
                    .apply(instance, GasAmmoData::new));

    public boolean isEmpty() {
        return ammo <= 0;
    }

    public GasAmmoData withAmmo(int newAmmo) {
        return new GasAmmoData(Math.max(0, newAmmo));
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
            return obj instanceof GasAmmoData fad && this.ammo == fad.ammo;
        }
    }
}
