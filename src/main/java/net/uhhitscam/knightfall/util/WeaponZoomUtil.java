package net.uhhitscam.knightfall.util;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.item.custom.*;

public class WeaponZoomUtil {
    public static float getProjectileWeaponZoomFactor(LocalPlayer player) {
        ItemStack mainHandItem = player.getMainHandItem();
        ItemStack offHandItem = player.getOffhandItem();

        if (mainHandItem.getItem() instanceof ProjectileItem projectileWeaponMain) {
            if (mainHandItem.getItem() instanceof ProjectileItem && offHandItem.getItem() instanceof ProjectileItem projectileWeaponOff) {
                ProjectileItem prioritizedWeapon;
                ItemStack prioritizedItem;
                if (getZoomFactor(projectileWeaponMain, player.getMainHandItem()) >= getZoomFactor(projectileWeaponOff, player.getOffhandItem())) {
                    prioritizedItem = player.getMainHandItem();
                    prioritizedWeapon = projectileWeaponMain;
                } else {
                    prioritizedItem = player.getOffhandItem();
                    prioritizedWeapon = projectileWeaponOff;
                }
                return Math.max(getZoomFactor(prioritizedWeapon, prioritizedItem), 0.7f);
            } else {
                return getZoomFactor(projectileWeaponMain, player.getMainHandItem());
            }
        } else if (offHandItem.getItem() instanceof ProjectileItem blasterOff) {
            return getZoomFactor(blasterOff, player.getOffhandItem());
        }
        return 1f;
    }

    public static float getZoomFactor(ProjectileItem weaponItem, ItemStack weaponStack) {
        FiringMode firingMode = weaponItem.getFiringMode(weaponStack);
        return weaponItem.getUI().zoom(firingMode);
    }

    public static ResourceLocation getCrosshairTexture(ProjectileItem weaponItem, ItemStack weaponStack) {
        return weaponItem.getUI().crosshair().texture();
    }

    public static ResourceLocation getScopeTexture(ProjectileItem weaponItem, ItemStack weaponStack) {
        FiringMode firingMode = weaponItem.getFiringMode(weaponStack);
        ScopeTexture scopeTexture = weaponItem.getUI().scope(firingMode);

        return scopeTexture != null ? scopeTexture.texture() : null;
    }
}
