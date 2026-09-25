package net.uhhitscam.knightfall.util;

import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.uhhitscam.knightfall.entity.custom.GrenadeEntity;
import net.uhhitscam.knightfall.item.custom.grenade.GrenadeDefinition;
import net.uhhitscam.knightfall.item.custom.grenade.GrenadeSmokeProfile;

public final class SmokeScreenUtil {
    private static final double SCREEN_SEARCH_PADDING = 32.0;

    private SmokeScreenUtil() {
    }

    public static boolean isActive(GrenadeEntity grenade) {
        GrenadeDefinition definition = grenade.getGrenadeDefinition();
        return definition != null
                && definition.smokeProfile() != null
                && grenade.isStuck()
                && grenade.isFuseRunning()
                && grenade.getFuseTicks() > 0;
    }

    public static double activeRadius(GrenadeEntity grenade, GrenadeSmokeProfile profile) {
        GrenadeDefinition definition = grenade.getGrenadeDefinition();
        return definition == null
                ? 0.0
                : profile.radiusAt(definition.fuseTicks(), grenade.getFuseTicks());
    }

    public static Vec3 center(GrenadeEntity grenade, GrenadeSmokeProfile profile) {
        Direction face = grenade.getStuckFace();
        if (face == null) {
            return grenade.position();
        }
        Vec3 normal = Vec3.atLowerCornerOf(face.getUnitVec3i());
        return grenade.position().add(normal.scale(profile.cloudCenterOffset()));
    }

    public static boolean blocksVision(Level level, Vec3 from, Vec3 to) {
        AABB searchBounds = new AABB(from, to).inflate(SCREEN_SEARCH_PADDING);
        for (GrenadeEntity grenade : level.getEntitiesOfClass(GrenadeEntity.class, searchBounds)) {
            if (!isActive(grenade)) {
                continue;
            }

            GrenadeSmokeProfile profile = grenade.getGrenadeDefinition().smokeProfile();
            double radius = activeRadius(grenade, profile);
            if (radius > 0.0 && segmentIntersectsSphere(from, to, center(grenade, profile), radius)) {
                return true;
            }
        }
        return false;
    }

    public static boolean segmentIntersectsSphere(Vec3 from, Vec3 to, Vec3 center, double radius) {
        Vec3 segment = to.subtract(from);
        double lengthSquared = segment.lengthSqr();
        if (lengthSquared < 1.0E-8) {
            return from.distanceToSqr(center) <= radius * radius;
        }

        double projection = center.subtract(from).dot(segment) / lengthSquared;
        double clampedProjection = Math.max(0.0, Math.min(1.0, projection));
        Vec3 closestPoint = from.add(segment.scale(clampedProjection));
        return closestPoint.distanceToSqr(center) <= radius * radius;
    }
}
