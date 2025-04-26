package net.uhhitscam.starwars.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.Level;

import java.util.Objects;

public record ReloadNSwitchCoolDownData(long ReloadNSwitchCoolDownEndTime) {
    public static final Codec<ReloadNSwitchCoolDownData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.LONG.fieldOf("ReloadNSwitchCoolDownEndTime").forGetter(ReloadNSwitchCoolDownData::ReloadNSwitchCoolDownEndTime)
            ).apply(instance, ReloadNSwitchCoolDownData::new));

    public ReloadNSwitchCoolDownData withReloadNSwitchCoolDownEndTime(long newReloadNSwitchCoolDownEndTime) {
        return new ReloadNSwitchCoolDownData(newReloadNSwitchCoolDownEndTime);
    }

    public boolean isOnCooldown(Level level) {
        return level.getGameTime() < ReloadNSwitchCoolDownEndTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ReloadNSwitchCoolDownEndTime);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else {
            return obj instanceof ReloadNSwitchCoolDownData other && ReloadNSwitchCoolDownEndTime == other.ReloadNSwitchCoolDownEndTime;
        }
    }
}