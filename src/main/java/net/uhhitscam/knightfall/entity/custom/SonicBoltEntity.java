package net.uhhitscam.knightfall.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SimpleExplosionDamageCalculator;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.network.PacketDistributor;
import net.uhhitscam.knightfall.event.BlurEffectEventHandler;
import net.uhhitscam.knightfall.item.custom.WeaponClassification;
import net.uhhitscam.knightfall.item.custom.WeaponName;
import net.uhhitscam.knightfall.network.CSConcussionBlurPacket;
import net.uhhitscam.knightfall.particle.ModParticles;
import net.uhhitscam.knightfall.sound.ModSounds;
import net.uhhitscam.knightfall.util.*;
import org.joml.Vector3f;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class SonicBoltEntity extends Snowball {
    private final float bolt_speed;
    private final int blasterDamage;

    public SonicBoltEntity(EntityType<? extends SonicBoltEntity> entityType, Level level) {
        super(entityType, level);
        this.bolt_speed = 1.4F;
        this.blasterDamage = 0;
    }

    public SonicBoltEntity(EntityType<? extends SonicBoltEntity> entityType, Level level, LivingEntity shooter, float bolt_speed, int blasterDamage) {
        super(entityType, level);
        this.bolt_speed = bolt_speed;
        this.blasterDamage = blasterDamage;

        Vec3 direction = shooter.getLookAngle().normalize().scale(bolt_speed);
        this.setDeltaMovement(direction);
    }

    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);

        Entity entity = result.getEntity();
        this.level().broadcastEntityEvent(this, (byte) 3);

        int i = 0;
        int blasterBoltDamage = i + blasterDamage;

        if (entity.hurt(this.damageSources().thrown(this, this.getOwner()), blasterBoltDamage)) {
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.invulnerableTime = 0;
            }
        }

        if (!this.level().isClientSide) {
            this.discard();
        }
    }

    protected void onHit(HitResult result) {
        super.onHit(result);

        if (this.level().isClientSide) {
            return;
        }

        if (result instanceof BlockHitResult blockHitResult) {
            breakGlassIfNeeded(blockHitResult);
            FaceAlignedParticleUtil.spawnSonicRipple(this.level(), blockHitResult);

            Vec3 location = blockHitResult.getLocation();
            level().playSound(
                    null,
                    location.x,
                    location.y,
                    location.z,
                    ModSounds.BLASTER_IMPACT_SONIC_BOLT.get(),
                    SoundSource.NEUTRAL,
                    0.45F,
                    0.9F + level().random.nextFloat() * 0.2F
            );
        }

        this.level().broadcastEntityEvent(this, (byte) 3);

        this.discard();
    }

    private void breakGlassIfNeeded(BlockHitResult blockHitResult) {
        BlockPos blockPos = blockHitResult.getBlockPos();
        BlockState blockState = this.level().getBlockState(blockPos);

        if (!BlasterImpactSoundUtil.isBreakableGlass(blockState)) {
            return;
        }

        this.level().destroyBlock(blockPos, false);
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
        double speed = velocity.length();

        if (speed > 0.0001) {
            double yaw = Math.toDegrees(Math.atan2(velocity.x, velocity.z));

            double pitch = Math.toDegrees(Math.atan2(velocity.y, Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z)));

//            if (Math.abs(pitch) > 90) {
//                pitch = pitch > 0 ? 90 : -90;
//            }

            this.setYRot((float) yaw);
            this.setXRot((float) pitch);
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
        }

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
        return 0.0F;
    }

    @Override
    public AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(0.5);
    }
}