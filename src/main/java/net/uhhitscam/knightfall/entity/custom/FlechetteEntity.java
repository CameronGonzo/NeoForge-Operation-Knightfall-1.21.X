package net.uhhitscam.knightfall.entity.custom;

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
import net.uhhitscam.knightfall.item.ModItems;
import net.uhhitscam.knightfall.particle.ModParticles;

public class FlechetteEntity extends Snowball {
    private final float flechetteSpeed;
    private final int flechetteDamage;
    private final int lifeSpan;

    public FlechetteEntity(EntityType<? extends FlechetteEntity> entityType, Level level) {
        super(entityType, level);
        this.flechetteSpeed = 2.0F;
        this.flechetteDamage = 0;
        this.lifeSpan = 50;
    }

    public FlechetteEntity(EntityType<? extends FlechetteEntity> entityType, Level level, LivingEntity shooter, float flechetteSpeed, int flechetteDamage, int lifeSpan) {
        super(entityType, level);
        this.flechetteSpeed = flechetteSpeed;
        this.flechetteDamage = flechetteDamage;
        this.lifeSpan = lifeSpan;
    }

    protected Item getDefaultItem() {
        return ModItems.FLECHETTE.get();
    }

    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        this.level().broadcastEntityEvent(this, (byte) 3);

        if (entity.hurt(this.damageSources().thrown(this, this.getOwner()), flechetteDamage)) {
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.invulnerableTime = 0;
//                level().playSound((Player) null, entity.getX(), entity.getY(), entity.getZ(), blasterFireSound, SoundSource.NEUTRAL, 0.5F, 1.0F);
            }
        }
    }

    protected void onHit(HitResult result) {
        super.onHit(result);
        int numParticles = 7;
        this.level().broadcastEntityEvent(this, (byte) 3);

        if (!this.level().isClientSide) {
            if (this.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ModParticles.SPARK_PARTICLES.get(),
                        this.getX(), this.getY(), this.getZ(),
                        numParticles, 0, 0, 0, 0.08);
            }

            this.discard();
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

        if (speed > 0.0001) { // Prevents division by zero
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

        if (!this.level().isClientSide && this.tickCount > lifeSpan) {
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