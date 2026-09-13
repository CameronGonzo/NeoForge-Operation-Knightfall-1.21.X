package net.uhhitscam.knightfall.item.custom.projectile;

public record ProjectileWeaponStats(
        int fireRate,
        float recoil,
        float inaccuracy,
        int damage,
        int overheatPerShot) {
}
