package net.uhhitscam.knightfall.item.custom;

import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.Map;

public record ProjectileWeaponUI(
        CrosshairTexture crosshair,
        float defaultZoom,
        EnumMap<FiringMode, Float> zoomByMode,
        EnumMap<FiringMode, ScopeTexture> scopeByMode
) {
    public static ProjectileWeaponUI of(CrosshairTexture crosshair, float defaultZoom) {
        return new ProjectileWeaponUI(
                crosshair,
                defaultZoom,
                new EnumMap<>(FiringMode.class),
                new EnumMap<>(FiringMode.class)
        );
    }

    public static ProjectileWeaponUI of(
            CrosshairTexture crosshair,
            float defaultZoom,
            Map<FiringMode, Float> zoomByMode,
            Map<FiringMode, ScopeTexture> scopeByMode
    ) {
        EnumMap<FiringMode, Float> zoomMap = new EnumMap<>(FiringMode.class);
        zoomMap.putAll(zoomByMode);

        EnumMap<FiringMode, ScopeTexture> scopeMap = new EnumMap<>(FiringMode.class);
        scopeMap.putAll(scopeByMode);

        return new ProjectileWeaponUI(
                crosshair,
                defaultZoom,
                zoomMap,
                scopeMap
        );
    }

    public float zoom(FiringMode firingMode) {
        if (firingMode == FiringMode.STUN) {
            return 0.85f;
        }

        return zoomByMode.getOrDefault(firingMode, defaultZoom);
    }

    @Nullable
    public ScopeTexture scope(FiringMode firingMode) {
        if (firingMode == FiringMode.STUN) {
            return null;
        }

        return scopeByMode.get(firingMode);
    }
}