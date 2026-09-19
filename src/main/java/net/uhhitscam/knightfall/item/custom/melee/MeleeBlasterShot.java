package net.uhhitscam.knightfall.item.custom.melee;

import net.uhhitscam.knightfall.item.custom.projectile.WeaponClassification;


import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.uhhitscam.knightfall.entity.ModEntities;
import net.uhhitscam.knightfall.entity.custom.BlasterBoltEntity;
import net.uhhitscam.knightfall.util.WeaponAimRules;

import java.util.Objects;

public record MeleeBlasterShot(BlasterBoltEntity.BoltType type, float speed, int damage,
                               WeaponClassification classification, MeleeWeaponSound sound) {
    public static final MeleeBlasterShot DEFAULT = new MeleeBlasterShot(
            BlasterBoltEntity.BoltType.TIBANNA, 2, 5, WeaponClassification.PISTOL, null);

    public MeleeBlasterShot {
        Objects.requireNonNull(type);
        Objects.requireNonNull(classification);
        if (!Float.isFinite(speed) || speed <= 0 || damage < 1) throw new IllegalArgumentException("Invalid melee blaster shot.");
    }

    public void fire(ServerPlayer player, InteractionHand hand, ItemStack weaponStack) {
        BlasterBoltEntity bolt = new BlasterBoltEntity(ModEntities.BLASTER_BOLT.get(), player.level(), player,
                type, speed, damage, classification, false, false);
        bolt.setOwner(player);
        bolt.setMeleeWeapon(weaponStack);
        // Start at the eye so the first collision check cannot skip a wall beside the muzzle.
        bolt.setPos(player.getEyePosition());
        WeaponAimRules.setProjectileMotion(bolt, bolt.getDeltaMovement());
        player.level().addFreshEntity(bolt);
        if (sound != null) sound.play(player.level(), player.position());
    }
}
