package net.uhhitscam.knightfall.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;

public final class WeaponDamage {
    private WeaponDamage() {}

    public static boolean hurt(Entity target, DamageSource source, float damage) {
        return target.level() instanceof ServerLevel level && target.hurtServer(level, source, damage);
    }

    public static boolean isBlocked(net.minecraft.world.entity.LivingEntity target, DamageSource source) {
        if (source.getDirectEntity() instanceof net.minecraft.world.entity.projectile.arrow.AbstractArrow arrow
                && arrow.getPierceLevel() > 0) return false;
        var stack = target.getItemBlockingWith();
        if (stack == null) return false;
        var blocks = stack.get(net.minecraft.core.component.DataComponents.BLOCKS_ATTACKS);
        if (blocks == null || blocks.bypassedBy().map(tag -> tag.contains(source.typeHolder())).orElse(false)) return false;
        var position = source.getSourcePosition();
        double angle = Math.PI;
        if (position != null) {
            var toward = position.subtract(target.position()).multiply(1, 0, 1).normalize();
            var view = net.minecraft.world.phys.Vec3.directionFromRotation(0, target.getYHeadRot());
            angle = Math.acos(net.minecraft.util.Mth.clamp(toward.dot(view), -1, 1));
        }
        return blocks.resolveBlockedDamage(source, 1.0F, angle) > 0;
    }
}
