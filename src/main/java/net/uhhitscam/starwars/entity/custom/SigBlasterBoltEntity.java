package net.uhhitscam.starwars.entity.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.uhhitscam.starwars.entity.ModEntities;
import net.uhhitscam.starwars.item.ModItems;
import net.uhhitscam.starwars.particle.ModParticles;

public class SigBlasterBoltEntity extends Snowball {
    private final float bolt_speed;
    private final int blasterDamage;
    private final String currentGasType;
    private final String classification;

    public SigBlasterBoltEntity(EntityType<? extends SigBlasterBoltEntity> entityType, Level level) {
        super(entityType, level);
        //base values just in case something goes wrong
        this.bolt_speed = 2.0F;
        this.blasterDamage = 0;
        this.currentGasType = "SIG_GAS";
        this.classification = "PISTOL";
    }

    public SigBlasterBoltEntity(EntityType<? extends SigBlasterBoltEntity> entityType, Level level, LivingEntity shooter, float bolt_speed, int blasterDamage, String currentGasType, String classification) {
        super(ModEntities.SIG_BLASTER_BOLT.get(), level); // Directly reference the EntityType
        this.bolt_speed = bolt_speed;
        this.blasterDamage = blasterDamage;
        this.currentGasType = currentGasType;
        this.classification = "PISTOL";

        Vec3 direction = shooter.getLookAngle().normalize().scale(bolt_speed);
        this.setDeltaMovement(direction);
    }

    public String getGasType() {
        return currentGasType;
    }

    protected Item getDefaultItem() {
        return ModItems.SIG_BLASTER_BOLT.get();
    }

    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();

        int i = 1; //mandalorians love their weapons, what can I say
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

        if (!this.level().isClientSide) {
            numParticles = switch (classification) {
                case "PISTOL" -> 5 + level().random.nextInt(5);
                case "CARBINE" -> 5 + level().random.nextInt(8);
                case "RIFLE" -> 8 + level().random.nextInt(10);
                case "REPEATING" -> 6 + level().random.nextInt(3);
                case "SCATTER" -> 10 + level().random.nextInt(5);
                case "SNIPER" -> 10 + level().random.nextInt(20);
                case "SLUGTHROWER" -> 10 + level().random.nextInt(15);
                case "DISRUPTOR" -> 15 + level().random.nextInt(20);
                default -> 5 + level().random.nextInt(10);
            };

            if (this.level() instanceof ServerLevel serverLevel && level().random.nextInt(2) == 1) {
                serverLevel.sendParticles(ModParticles.SPARK_PARTICLES.get(),
                        this.getX(), this.getY(), this.getZ(),
                        numParticles, 0, 0, 0, 0.08);
            }

            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            for (int i = 0; i < 1 + level().random.nextInt(3); i++) {
                this.level().addParticle(ParticleTypes.SMOKE,
                        this.getX(), this.getY(), this.getZ(),
                        (this.level().random.nextDouble() - 0.5) * 0.01,
                        (this.level().random.nextDouble() * 0.1) + 0.05, // Small upward motion
                        (this.level().random.nextDouble() - 0.5) * 0.01
                );
            }
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

        if (!this.level().isClientSide && this.tickCount > 2000) {
            this.discard();
        }
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