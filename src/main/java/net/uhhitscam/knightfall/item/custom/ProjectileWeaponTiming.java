package net.uhhitscam.knightfall.item.custom;

import net.uhhitscam.knightfall.util.WeaponSoundsUtil;

import java.util.EnumMap;
import java.util.Map;

public record ProjectileWeaponTiming(
        long defaultReloadTicks,
        EnumMap<FiringMode, Long> reloadTicksByMode,
        long defaultSwitchTicks,
        EnumMap<FiringMode, Long> switchTicksByMode,
        int chargeThresholdTicks,
        long equipTicks
) {
    public static ProjectileWeaponTiming defaults(WeaponName weaponName) {
        return new ProjectileWeaponTiming(
                19,
                new EnumMap<>(FiringMode.class),
                4,
                new EnumMap<>(FiringMode.class),
                33,
                defaultEquipTicks(weaponName)
        );
    }

    public static ProjectileWeaponTiming defaults() {
        return defaults(null);
    }

    private static long defaultEquipTicks(WeaponName weaponName) {
        if (weaponName == null) {
            return 6;
        }

        if (WeaponSoundsUtil.usesScatterEquipProfile(weaponName)) {
            return 7;
        }

        if (WeaponSoundsUtil.isLargeWeapon(weaponName)) {
            return 10;
        }

        if (WeaponSoundsUtil.isSmallWeapon(weaponName)) {
            return 4;
        }

        return 6;
    }

    public long reloadTicks(FiringMode firingMode) {
        return reloadTicksByMode.getOrDefault(firingMode, defaultReloadTicks);
    }

    public long switchTicks(FiringMode firingMode) {
        return switchTicksByMode.getOrDefault(firingMode, defaultSwitchTicks);
    }

    public ProjectileWeaponTiming withReloadTicks(long ticks) {
        return new ProjectileWeaponTiming(
                ticks,
                new EnumMap<>(reloadTicksByMode),
                defaultSwitchTicks,
                new EnumMap<>(switchTicksByMode),
                chargeThresholdTicks,
                equipTicks
        );
    }

    public ProjectileWeaponTiming withReloadTicks(FiringMode firingMode, long ticks) {
        EnumMap<FiringMode, Long> updated = new EnumMap<>(reloadTicksByMode);
        updated.put(firingMode, ticks);

        return new ProjectileWeaponTiming(
                defaultReloadTicks,
                updated,
                defaultSwitchTicks,
                new EnumMap<>(switchTicksByMode),
                chargeThresholdTicks,
                equipTicks
        );
    }

    public ProjectileWeaponTiming withSwitchTicks(long ticks) {
        return new ProjectileWeaponTiming(
                defaultReloadTicks,
                new EnumMap<>(reloadTicksByMode),
                ticks,
                new EnumMap<>(switchTicksByMode),
                chargeThresholdTicks,
                equipTicks
        );
    }

    public ProjectileWeaponTiming withSwitchTicks(FiringMode firingMode, long ticks) {
        EnumMap<FiringMode, Long> updated = new EnumMap<>(switchTicksByMode);
        updated.put(firingMode, ticks);

        return new ProjectileWeaponTiming(
                defaultReloadTicks,
                new EnumMap<>(reloadTicksByMode),
                defaultSwitchTicks,
                updated,
                chargeThresholdTicks,
                equipTicks
        );
    }

    public ProjectileWeaponTiming withChargeThresholdTicks(int ticks) {
        return new ProjectileWeaponTiming(
                defaultReloadTicks,
                new EnumMap<>(reloadTicksByMode),
                defaultSwitchTicks,
                new EnumMap<>(switchTicksByMode),
                ticks,
                equipTicks
        );
    }

    public ProjectileWeaponTiming withEquipTicks(long ticks) {
        return new ProjectileWeaponTiming(
                defaultReloadTicks,
                new EnumMap<>(reloadTicksByMode),
                defaultSwitchTicks,
                new EnumMap<>(switchTicksByMode),
                chargeThresholdTicks,
                ticks
        );
    }

    public static ProjectileWeaponTiming of(
            long defaultReloadTicks,
            Map<FiringMode, Long> reloadTicksByMode,
            long defaultSwitchTicks,
            Map<FiringMode, Long> switchTicksByMode,
            int chargeThresholdTicks,
            long equipTicks
    ) {
        EnumMap<FiringMode, Long> reloadMap = new EnumMap<>(FiringMode.class);
        reloadMap.putAll(reloadTicksByMode);

        EnumMap<FiringMode, Long> switchMap = new EnumMap<>(FiringMode.class);
        switchMap.putAll(switchTicksByMode);

        return new ProjectileWeaponTiming(
                defaultReloadTicks,
                reloadMap,
                defaultSwitchTicks,
                switchMap,
                chargeThresholdTicks,
                equipTicks
        );
    }
}