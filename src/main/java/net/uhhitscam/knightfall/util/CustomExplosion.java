package net.uhhitscam.knightfall.util;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class CustomExplosion {
    public static void create(Entity sourceEntity, double centerX, double centerY, double centerZ,
                              double radius, float damage, double knockback) {
        if (sourceEntity.level().isClientSide) {
            return;
        }

        ServerLevel serverLevel = (ServerLevel) sourceEntity.level();

        for (int i = 0; i < 20; i++) {
            double offsetX = (serverLevel.random.nextDouble() - 0.5) * 0.5;
            double offsetY = (serverLevel.random.nextDouble() - 0.5) * 0.5;
            double offsetZ = (serverLevel.random.nextDouble() - 0.5) * 0.5;
            serverLevel.sendParticles(
                    ParticleTypes.EXPLOSION,
                    centerX + offsetX,
                    centerY + offsetY,
                    centerZ + offsetZ,
                    1, 0, 0, 0, 0.1
            );
        }

        AABB explosionBox = new AABB(
                centerX - radius, centerY - radius, centerZ - radius,
                centerX + radius, centerY + radius, centerZ + radius
        );

        List<LivingEntity> entities = serverLevel.getEntitiesOfClass(LivingEntity.class, explosionBox);
        for (LivingEntity target : entities) {
            if (target == sourceEntity) continue;

            double distanceSq = target.distanceToSqr(centerX, centerY, centerZ);
            if (distanceSq > radius * radius) continue;

            target.hurt(sourceEntity.damageSources().explosion(sourceEntity, sourceEntity), damage);

            Vec3 knockDir = target.position().subtract(centerX, centerY, centerZ).normalize();
            target.push(knockDir.x * knockback, knockDir.y * knockback * 0.5, knockDir.z * knockback);
        }
    }
}