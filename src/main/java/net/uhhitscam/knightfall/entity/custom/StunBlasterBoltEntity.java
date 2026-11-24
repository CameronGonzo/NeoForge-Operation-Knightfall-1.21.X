package net.uhhitscam.knightfall.entity.custom;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.uhhitscam.knightfall.item.custom.projectile.DebugBlasterSettings;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.Snowball;
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
    private static final EntityDataAccessor<Float> DEBUG_SPEED =
            SynchedEntityData.defineId(StunBlasterBoltEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DEBUG_GRAVITY =
            SynchedEntityData.defineId(StunBlasterBoltEntity.class, EntityDataSerializers.FLOAT);
    private DebugBlasterSettings debugSettings;

    public void configureDebug(DebugBlasterSettings settings) {
        this.debugSettings = settings;
        this.entityData.set(DEBUG_SPEED, settings.decimal("projectile_speed"));
        this.entityData.set(DEBUG_GRAVITY, settings.decimal("projectile_gravity"));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DEBUG_SPEED, -1.0F);
        builder.define(DEBUG_GRAVITY, -1.0F);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput tag) {
        super.addAdditionalSaveData(tag);
        if (debugSettings != null) tag.store("DebugSettings", DebugBlasterSettings.CODEC, debugSettings);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput tag) {
        super.readAdditionalSaveData(tag);
        tag.read("DebugSettings", DebugBlasterSettings.CODEC).ifPresent(this::configureDebug);
    }

    public StunBlasterBoltEntity(EntityType<? extends StunBlasterBoltEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);

        if (!this.level().isClientSide() && result.getEntity() instanceof GrenadeEntity grenade) {
            net.uhhitscam.knightfall.util.WeaponDamage.hurt(grenade, this.damageSources().thrown(this, this.getOwner()), 0.0F);
            return;
        }

        if (!this.level().isClientSide() && result.getEntity() instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(
                    new MobEffectInstance(ModEffects.STUN_EFFECT, debugSettings == null ? StunEffect.DURATION_TICKS : debugSettings.integer("stun_duration")),
                    this.getOwner()
            );
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (this.level().isClientSide()) {
            return;
        }

        ServerLevel serverLevel = (ServerLevel) this.level();
        serverLevel.sendParticles(
                ModParticles.STUN_SPARK_PARTICLES.get(),
                this.getX(),
                this.getY(),
                this.getZ(),
                15 + this.level().getRandom().nextInt(5),
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
            float debugSpeed = this.entityData.get(DEBUG_SPEED);
            this.setDeltaMovement(velocity.normalize().scale(debugSpeed > 0 ? debugSpeed : BOLT_SPEED));
        }

        if (!this.level().isClientSide() && this.tickCount > (debugSettings == null ? MAX_LIFETIME_TICKS : debugSettings.integer("stun_lifetime"))) {
            this.discard();
        }
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    protected double getDefaultGravity() {
        float debugGravity = this.entityData.get(DEBUG_GRAVITY);
        return debugGravity >= 0 ? debugGravity : 0.002F;
    }

    public AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(0.5);
    }
}
