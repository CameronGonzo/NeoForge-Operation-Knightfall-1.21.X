package net.uhhitscam.starwars.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.Level;

import java.util.Objects;

public record CoolDownData(long cooldownEndTime) {
    public static final Codec<CoolDownData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.LONG.fieldOf("cooldownEndTime").forGetter(CoolDownData::cooldownEndTime)
            ).apply(instance, CoolDownData::new));

    public CoolDownData withCooldownEndTime(long newCooldownEndTime) {
        return new CoolDownData(newCooldownEndTime);
    }

    public boolean isOnCooldown(Level level) {
        return level.getGameTime() < cooldownEndTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cooldownEndTime);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else {
            return obj instanceof CoolDownData other && cooldownEndTime == other.cooldownEndTime;
        }
    }
}
