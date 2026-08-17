package net.uhhitscam.knightfall.util;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CustomExplosion {
    public static void create(Entity sourceEntity, Vec3 location,
                              double entityRadius, float damage, double knockback) {
        create(sourceEntity, sourceEntity, location, entityRadius, damage, knockback,
                0.0f, Level.ExplosionInteraction.NONE, false);
    }

    public static void create(Entity sourceEntity, Vec3 location,
                              double entityRadius, float damage, double knockback,
                              float blockBreakRadius, Level.ExplosionInteraction interaction, boolean causesFire) {
        create(sourceEntity, sourceEntity, location, entityRadius, damage, knockback,
                blockBreakRadius, interaction, causesFire);
    }

    public static void create(Entity sourceEntity, @Nullable Entity causingEntity, Vec3 location,
                              double entityRadius, float damage, double knockback,
                              float blockBreakRadius, Level.ExplosionInteraction interaction, boolean causesFire) {

        if (sourceEntity.level().isClientSide) return;
        ServerLevel serverLevel = (ServerLevel) sourceEntity.level();

        final double x = location.x;
        final double y = location.y;
        final double z = location.z;

        AABB box = new AABB(
                x - entityRadius, y - entityRadius, z - entityRadius,
                x + entityRadius, y + entityRadius, z + entityRadius
        );

        List<LivingEntity> entities = serverLevel.getEntitiesOfClass(LivingEntity.class, box);
        DamageSource dmgSrc = sourceEntity.damageSources().explosion(sourceEntity, causingEntity);

        double r2 = entityRadius * entityRadius;

        for (LivingEntity target : entities) {
            if (target == sourceEntity) continue;

            double dist2 = target.distanceToSqr(x, y, z);
            if (dist2 > r2) continue;

            target.hurt(dmgSrc, damage);

            Vec3 delta = target.position().subtract(x, y, z);
            if (delta.lengthSqr() > 1.0e-6) {
                Vec3 dir = delta.normalize();
                target.push(dir.x * knockback, dir.y * knockback * 0.5, dir.z * knockback);
            }
        }

        if (blockBreakRadius > 0.0f && interaction != Level.ExplosionInteraction.NONE) {

            ExplosionDamageCalculator noEntityEffects = new ExplosionDamageCalculator() {
                @Override
                public boolean shouldDamageEntity(Explosion explosion, Entity entity) {
                    return false;
                }

                @Override
                public float getEntityDamageAmount(Explosion explosion, Entity entity) {
                    return 0.0F;
                }

                @Override
                public float getKnockbackMultiplier(Entity entity) {
                    return 0.0F;
                }
            };

            serverLevel.explode(
                    sourceEntity,
                    dmgSrc,
                    noEntityEffects,
                    x, y, z,
                    blockBreakRadius,
                    causesFire,
                    interaction,
                    false,
                    ParticleTypes.EXPLOSION,
                    ParticleTypes.EXPLOSION_EMITTER,
                    BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.GENERIC_EXPLODE.value())
            );
        }

        destroyGroundItems(serverLevel, location, Math.max(entityRadius, blockBreakRadius));
    }

    private static void destroyGroundItems(ServerLevel level, Vec3 location, double radius) {
        double radiusSquared = radius * radius;
        AABB bounds = new AABB(location, location).inflate(radius);
        for (ItemEntity item : level.getEntitiesOfClass(ItemEntity.class, bounds)) {
            if (item.distanceToSqr(location) <= radiusSquared) {
                item.discard();
            }
        }
    }
}
