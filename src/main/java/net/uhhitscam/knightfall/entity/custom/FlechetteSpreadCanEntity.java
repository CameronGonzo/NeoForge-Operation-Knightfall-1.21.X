package net.uhhitscam.knightfall.entity.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.uhhitscam.knightfall.entity.ModEntities;
import net.uhhitscam.knightfall.item.ModItems;

import java.util.List;

public class FlechetteSpreadCanEntity extends Snowball {
    private final float flechetteSpeed;
    private final int flechetteDamage;
    private boolean hasExploded = false;

    public FlechetteSpreadCanEntity(EntityType<? extends FlechetteSpreadCanEntity> entityType, Level level) {
        super(entityType, level);
        this.flechetteSpeed = 2.0F;
        this.flechetteDamage = 0;
    }

    public FlechetteSpreadCanEntity(EntityType<? extends FlechetteSpreadCanEntity> entityType, Level level, LivingEntity shooter, float flechetteSpeed, int flechetteDamage) {
        super(entityType, level);
        this.flechetteSpeed = flechetteSpeed;
        this.flechetteDamage = flechetteDamage;

        Vec3 direction = shooter.getLookAngle().normalize().scale(flechetteSpeed);
        this.setDeltaMovement(direction);
    }

    protected Item getDefaultItem() {
        return ModItems.FLECHETTE_SPREAD_CANISTER.get();
    }

    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        this.level().broadcastEntityEvent(this, (byte) 3);

        int i = (-1 * flechetteDamage) + 1;
        int canisterDamage = i + flechetteDamage;

        if (entity.hurt(this.damageSources().thrown(this, this.getOwner()), canisterDamage)) {
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.invulnerableTime = 0;
//                level().playSound((Player) null, entity.getX(), entity.getY(), entity.getZ(), blasterFireSound, SoundSource.NEUTRAL, 0.5F, 1.0F);
            }
        }
    }

    protected void onHit(HitResult result) {
        super.onHit(result);
        this.level().broadcastEntityEvent(this, (byte) 3);

        if (!this.level().isClientSide) {
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

        this.setDeltaMovement(velocity.normalize().scale(this.flechetteSpeed));

        if (!this.level().isClientSide) {
            List<LivingEntity> nearbyEntities = this.level().getEntitiesOfClass(
                    LivingEntity.class,
                    this.getBoundingBox().inflate(3),
                    entity -> !entity.equals(this) && !entity.equals(this.getOwner())
            );

            if (!hasExploded && !nearbyEntities.isEmpty()) {
                hasExploded = true;
                spawnFlechettes();
                this.discard();
            }

            if (!nearbyEntities.isEmpty()) {
                spawnFlechettes();
                this.discard();
                return;
            }
        }

        if (!this.level().isClientSide && this.tickCount > 50) {
            this.discard();
        }
    }

    private void spawnFlechettes() {
        if (!(this.level() instanceof ServerLevel serverLevel)) return;

        for (int i = 0; i < 10; i++) {
            FlechetteEntity flechette = new FlechetteEntity(ModEntities.FLECHETTE.get(), serverLevel, (LivingEntity) this.getOwner(), 0, flechetteDamage, 2);

            flechette.setPos(this.getX(), this.getY(), this.getZ());

            Vec3 canisterDirection = this.getDeltaMovement().normalize();
            Vec3 randomSpread = new Vec3(
                    this.random.nextGaussian() * 0.3,
                    this.random.nextGaussian() * 0.3,
                    this.random.nextGaussian() * 0.3
            );
            Vec3 direction = canisterDirection.scale(0.5).add(randomSpread.scale(0.5)).normalize().scale(this.flechetteSpeed);

            flechette.setDeltaMovement(direction);
            flechette.setOwner(this.getOwner());

            serverLevel.addFreshEntity(flechette);
        }

        level().playSound(null, this.blockPosition(), SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.NEUTRAL, 0.6F, 1.2F);
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