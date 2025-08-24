package net.uhhitscam.knightfall.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.Level;

import java.util.Objects;

public record FireCoolDownData(long FireCoolDownEndTime) {
    public static final Codec<FireCoolDownData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.LONG.fieldOf("FireCoolDownEndTime").forGetter(FireCoolDownData::FireCoolDownEndTime)
            ).apply(instance, FireCoolDownData::new));

    public FireCoolDownData withFireCoolDownEndTime(long newFireCoolDownEndTime) {
        return new FireCoolDownData(newFireCoolDownEndTime);
    }

    public boolean isOnCooldown(Level level) {
        return level.getGameTime() < FireCoolDownEndTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(FireCoolDownEndTime);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else {
            return obj instanceof FireCoolDownData other && FireCoolDownEndTime == other.FireCoolDownEndTime;
        }
    }
}