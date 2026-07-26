package net.uhhitscam.knightfall.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.Level;
import net.uhhitscam.knightfall.item.custom.WeaponCooldownAction;

import java.util.Objects;

public record ReloadNSwitchCoolDownData(
        long ReloadNSwitchCoolDownEndTime,
        WeaponCooldownAction cooldownAction
) {
    private static final Codec<WeaponCooldownAction> ACTION_CODEC = Codec.INT.xmap(
            WeaponCooldownAction::byId,
            WeaponCooldownAction::id
    );

    public static final Codec<ReloadNSwitchCoolDownData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.LONG.fieldOf("ReloadNSwitchCoolDownEndTime").forGetter(ReloadNSwitchCoolDownData::ReloadNSwitchCoolDownEndTime),
                    ACTION_CODEC.optionalFieldOf("WeaponCooldownAction", WeaponCooldownAction.SWITCH)
                            .forGetter(ReloadNSwitchCoolDownData::cooldownAction)
            ).apply(instance, ReloadNSwitchCoolDownData::new));

    public ReloadNSwitchCoolDownData(long reloadNSwitchCoolDownEndTime) {
        this(reloadNSwitchCoolDownEndTime, WeaponCooldownAction.SWITCH);
    }

    public ReloadNSwitchCoolDownData withReloadNSwitchCoolDownEndTime(long newReloadNSwitchCoolDownEndTime) {
        return new ReloadNSwitchCoolDownData(newReloadNSwitchCoolDownEndTime, cooldownAction);
    }

    public ReloadNSwitchCoolDownData withCooldown(
            long newReloadNSwitchCoolDownEndTime,
            WeaponCooldownAction newCooldownAction
    ) {
        return new ReloadNSwitchCoolDownData(newReloadNSwitchCoolDownEndTime, newCooldownAction);
    }

    public boolean isOnCooldown(Level level) {
        return level.getGameTime() < ReloadNSwitchCoolDownEndTime;
    }

    public boolean blocksReload(Level level) {
        return isOnCooldown(level) && cooldownAction != WeaponCooldownAction.EQUIP;
    }

    public boolean isActiveEquipCooldown(Level level) {
        return isOnCooldown(level) && cooldownAction == WeaponCooldownAction.EQUIP;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ReloadNSwitchCoolDownEndTime, cooldownAction);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else {
            return obj instanceof ReloadNSwitchCoolDownData other
                    && ReloadNSwitchCoolDownEndTime == other.ReloadNSwitchCoolDownEndTime
                    && cooldownAction == other.cooldownAction;
        }
    }
}
