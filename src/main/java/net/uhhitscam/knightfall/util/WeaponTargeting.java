package net.uhhitscam.knightfall.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public final class WeaponTargeting {
    private static final double ENTITY_HITBOX_INFLATION = 0.2;

    private WeaponTargeting() {}

    public static WeaponHit findHit(Level level, Entity owner, Vec3 start, Vec3 direction, double range) {
        return findHit(level, owner, start, direction, range, entity -> true);
    }

    public static WeaponHit findBeamHit(Level level, Entity owner, Vec3 start, Vec3 direction, double range) {
        return findHit(level, owner, start, direction, range, entity -> entity instanceof LivingEntity);
    }

    private static WeaponHit findHit(Level level, Entity owner, Vec3 start, Vec3 direction, double range, Predicate<Entity> targetFilter) {
        Vec3 normalizedDirection = direction.normalize();
        Vec3 maximumEnd = start.add(normalizedDirection.scale(range));
        BlockHitResult blockHit = level.clip(new ClipContext(
                start,
                maximumEnd,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                owner
        ));

        Vec3 end = blockHit.getType() == HitResult.Type.MISS ? maximumEnd : blockHit.getLocation();
        double closestDistance = start.distanceTo(end);
        Entity closestEntity = null;
        Vec3 closestEntityHit = null;
        AABB scanBox = new AABB(start, end).inflate(0.5);

        for (Entity candidate : level.getEntities(owner, scanBox, entity ->
                entity != owner && entity.isPickable() && !entity.isSpectator() && targetFilter.test(entity))) {
            AABB hitbox = candidate.getBoundingBox().inflate(ENTITY_HITBOX_INFLATION);
            if (hitbox.contains(start)) {
                closestEntity = candidate;
                closestEntityHit = start;
                break;
            }

            var clipped = hitbox.clip(start, end);
            if (clipped.isEmpty()) {
                continue;
            }

            double distance = start.distanceTo(clipped.get());
            if (distance < closestDistance) {
                closestDistance = distance;
                closestEntity = candidate;
                closestEntityHit = clipped.get();
            }
        }

        return closestEntity == null
                ? new WeaponHit(end, null)
                : new WeaponHit(closestEntityHit, closestEntity);
    }

    public record WeaponHit(Vec3 endPosition, @Nullable Entity entity) {
        @Nullable
        public LivingEntity livingEntity() {
            return entity instanceof LivingEntity living ? living : null;
        }
    }
}
