package net.uhhitscam.knightfall.entity.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.uhhitscam.knightfall.effect.ModEffects;
import net.uhhitscam.knightfall.effect.custom.StunEffect;
import net.uhhitscam.knightfall.particle.ModParticles;

public class StunBlasterBoltEntity extends Snowball {
    private static final float BOLT_SPEED = 1.5F;
    private static final int MAX_LIFETIME_TICKS = 17;

    public StunBlasterBoltEntity(EntityType<? extends StunBlasterBoltEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);

        if (!this.level().isClientSide && result.getEntity() instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(
                    new MobEffectInstance(ModEffects.STUN_EFFECT, StunEffect.DURATION_TICKS),
                    this.getOwner()
            );
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (this.level().isClientSide) {
            return;
        }

        ServerLevel serverLevel = (ServerLevel) this.level();
        serverLevel.sendParticles(
                ModParticles.STUN_SPARK_PARTICLES.get(),
                this.getX(),
                this.getY(),
                this.getZ(),
                15 + this.level().random.nextInt(5),
                0,
                0,
                0,
                0.08
        );
        this.discard();
    }

    @Override
    public void tick() {
        super.tick();

        Vec3 velocity = this.getDeltaMovement();
        if (velocity.lengthSqr() > 1.0E-8) {
            double yaw = Math.toDegrees(Math.atan2(velocity.x, velocity.z));
            double horizontalSpeed = Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z);
            double pitch = Math.toDegrees(Math.atan2(velocity.y, horizontalSpeed));

            this.setYRot((float) yaw);
            this.setXRot((float) pitch);
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
            this.setDeltaMovement(velocity.normalize().scale(BOLT_SPEED));
        }

        if (!this.level().isClientSide && this.tickCount > MAX_LIFETIME_TICKS) {
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
