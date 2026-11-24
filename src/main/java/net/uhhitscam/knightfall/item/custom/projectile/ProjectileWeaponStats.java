package net.uhhitscam.knightfall.item.custom.projectile;

public record ProjectileWeaponStats(
        // Shot cooldown in ticks, starting on the first shot of a burst; lower = faster.
        int fireRate,
        // Upward camera kick in degrees before aiming and dual wield adjustments.
        float recoil,
        // Random spread strength, divided by 100 when calculating shot direction; 0 = no spread.
        float inaccuracy,
        // Base direct-hit damage in health points (2 points = 1 heart), before ammo effects.
        int damage,
        // Heat added per shot; scatter adds this once for the whole volley.
        int overheatPerShot) {
}
