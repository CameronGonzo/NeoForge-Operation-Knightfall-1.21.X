package net.uhhitscam.knightfall.entity.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.uhhitscam.knightfall.item.custom.WeaponClassification;
import net.uhhitscam.knightfall.particle.ModParticles;

import java.util.List;

public class SkevonBlasterBoltEntity extends Snowball {
    private final float bolt_speed;
    private final int blasterDamage;
    private final WeaponClassification classification;
    private boolean explosiveShot;

    public SkevonBlasterBoltEntity(EntityType<? extends SkevonBlasterBoltEntity> entityType, Level level) {
        super(entityType, level);
        //base values just in case something goes wrong
        this.bolt_speed = 2.0F;
        this.blasterDamage = 0;
        this.classification = WeaponClassification.PISTOL;
        this.explosiveShot = false;
    }

    public SkevonBlasterBoltEntity(EntityType<? extends SkevonBlasterBoltEntity> entityType, Level level, LivingEntity shooter, float bolt_speed, int blasterDamage, WeaponClassification classification, boolean explosiveShot) {
        super(entityType, level); // Directly reference the EntityType
        this.bolt_speed = bolt_speed;
        this.blasterDamage = blasterDamage;
        this.classification = classification;
        this.explosiveShot = explosiveShot;

        Vec3 direction = shooter.getLookAngle().normalize().scale(bolt_speed);
        this.setDeltaMovement(direction);
    }

    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();

        int i = (-1 * blasterDamage) + 1; //training projectileWeapon bolts are skevon
        int blasterBoltDamage = i + blasterDamage;

        if (entity.hurt(this.damageSources().thrown(this, this.getOwner()), blasterBoltDamage)) {
            // Reset the invulnerability timer to allow immediate damage from other bolts
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.invulnerableTime = 0;
            }
        }
    }

    protected void onHit(HitResult result) {
        super.onHit(result);
        int numParticles;
        if (explosiveShot) {
            explode(result.getLocation());
        }

        if (!this.level().isClientSide) {
            numParticles = switch (classification) {
                case WeaponClassification.PISTOL -> 5 + level().random.nextInt(5);
                case WeaponClassification.CARBINE -> 5 + level().random.nextInt(8);
                case WeaponClassification.RIFLE -> 8 + level().random.nextInt(10);
                case WeaponClassification.REPEATER -> 6 + level().random.nextInt(3);
                case WeaponClassification.SCATTER -> 10 + level().random.nextInt(5);
                case WeaponClassification.SNIPER -> 10 + level().random.nextInt(20);
                case WeaponClassification.SLUGTHROWER -> 10 + level().random.nextInt(15);
                case WeaponClassification.DISRUPTOR -> 15 + level().random.nextInt(20);
                default -> 7 + level().random.nextInt(7);
            };

            if (this.level() instanceof ServerLevel serverLevel) {
                if (explosiveShot) {
                    serverLevel.sendParticles(ModParticles.EXPLOSIVE_SHOT_SKEVON_PARTICLES.get(),
                            this.getX(), this.getY(), this.getZ(),
                            1, 0, 0, 0, 0);
                } else {
                    serverLevel.sendParticles(ModParticles.SPARK_PARTICLES.get(),
                            this.getX(), this.getY(), this.getZ(),
                            numParticles, 0, 0, 0, 0.08);

                    serverLevel.sendParticles(ParticleTypes.SMOKE,
                            this.getX(), this.getY(), this.getZ(),
                            3, 0, 0, 0, 0.02);
                }
            }

            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }

    private void explode(Vec3 hitPos) {
        if (!level().isClientSide) {
            double radius = 1.0D; // explosion radius
            double knockbackStrength = 0.05D;
            float damageAmount = 1F;

            List<Entity> entities = level().getEntities(this, new AABB(
                    hitPos.x - radius, hitPos.y - radius, hitPos.z - radius,
                    hitPos.x + radius, hitPos.y + radius, hitPos.z + radius
            ));

            for (Entity e : entities) {
                if (e instanceof LivingEntity living) {
                    Vec3 knockbackDir = living.position().subtract(hitPos).normalize();
                    living.push(knockbackDir.x * knockbackStrength, knockbackDir.y * knockbackStrength, knockbackDir.z * knockbackStrength);

                    living.hurt(damageSources().generic(), damageAmount);
                    living.invulnerableTime = 0;
                }
            }

            discard();
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
//            for (int i = 0; i < 1 + level().random.nextInt(3); i++) {
//                this.level().addParticle(ParticleTypes.SMOKE,
//                        this.getX(), this.getY(), this.getZ(),
//                        (this.level().random.nextDouble() - 0.5) * 0.01,
//                        (this.level().random.nextDouble() * 0.1) + 0.05, // Small upward motion
//                        (this.level().random.nextDouble() - 0.5) * 0.01
//                );
//            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        Vec3 velocity = this.getDeltaMovement();
        double speed = velocity.length(); // Magnitude of the velocity vector

        if (speed > 0.0001) { // Only update direction if the entity is moving
            // Calculate yaw (horizontal rotation, rotation around Y-axis)
            double yaw = Math.toDegrees(Math.atan2(velocity.x, velocity.z)); // atan2 gives us the correct direction in the horizontal plane

            // Calculate pitch (vertical rotation, rotation around X-axis)
            double pitch = Math.toDegrees(Math.atan2(velocity.y, Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z)));

            // Prevent pitch from being too extreme when moving directly up or down
//            if (Math.abs(pitch) > 90) {
//                pitch = pitch > 0 ? 90 : -90;
//            }

            // Negate the yaw and pitch to rotate in the opposite direction
            this.setYRot((float) yaw);  // Update yaw (Y rotation)
            this.setXRot((float) pitch); // Update pitch (X rotation)
            this.yRotO = this.getYRot(); // Synchronize previous Y rotation
            this.xRotO = this.getXRot(); // Synchronize previous X rotation
        }

        // Keep the movement constant
        this.setDeltaMovement(velocity.normalize().scale(this.bolt_speed));

        if (!this.level().isClientSide && this.tickCount > 50) {
            this.discard();
        }
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    protected double getDefaultGravity() {
        return 0.002F;
    }


    @Override
    public AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(0.5);
    }
}