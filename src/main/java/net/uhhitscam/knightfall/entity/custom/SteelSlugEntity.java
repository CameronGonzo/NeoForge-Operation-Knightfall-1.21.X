package net.uhhitscam.knightfall.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.uhhitscam.knightfall.item.ModItems;
import net.uhhitscam.knightfall.item.custom.WeaponClassification;
import net.uhhitscam.knightfall.item.custom.WeaponName;
import net.uhhitscam.knightfall.particle.ModParticles;

public class SteelSlugEntity extends Snowball {
    private final float slugSpeed;
    private final int slugDamage;
    private final WeaponClassification classification;
    private final WeaponName weaponName;

    public SteelSlugEntity(EntityType<? extends SteelSlugEntity> entityType, Level level) {
        super(entityType, level);
        this.slugSpeed = 2.0F;
        this.slugDamage = 0;
        this.classification = WeaponClassification.SLUGTHROWER;
        this.weaponName = WeaponName._62AUG2_HUNTING_RIFLE;
    }

    public SteelSlugEntity(EntityType<? extends SteelSlugEntity> entityType, Level level, LivingEntity shooter, float slugSpeed, int slugDamage, WeaponClassification classification, WeaponName weaponName) {
        super(entityType, level);
        this.slugSpeed = slugSpeed;
        this.slugDamage = slugDamage;
        this.classification = classification;
        this.weaponName = weaponName;

        Vec3 direction = shooter.getLookAngle().normalize().scale(slugSpeed);
        this.setDeltaMovement(direction);
    }

    protected Item getDefaultItem() {
        return ModItems.STEEL_SLUG.get();
    }

    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        this.level().broadcastEntityEvent(this, (byte) 3);

        if (entity instanceof Creeper creeper && level().random.nextInt(2) == 1) {
            creeper.ignite();
        }

        int i = 0;  //no extra damage for this
        int steelSlugDamage = i + slugDamage;

        if (entity.hurt(this.damageSources().thrown(this, this.getOwner()), steelSlugDamage)) {
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.invulnerableTime = 0;
//                level().playSound((Player) null, entity.getX(), entity.getY(), entity.getZ(), blasterFireSound, SoundSource.NEUTRAL, 0.5F, 1.0F);

                if (weaponName.equals(WeaponName.BERSERKER) && level().random.nextInt(2) == 1) {
                    entity.setRemainingFireTicks(10 + this.random.nextInt(71));
                }
            }
        }
    }

    protected void onHit(HitResult result) {
        super.onHit(result);
        int numParticles = 7;
        this.level().broadcastEntityEvent(this, (byte) 3);

        if (!this.level().isClientSide) {
            if (result.getType() == HitResult.Type.BLOCK && weaponName.equals(WeaponName.BERSERKER) && level().random.nextInt(2) == 1) {
                BlockHitResult blockHit = (BlockHitResult) result;
                BlockPos pos = blockHit.getBlockPos().relative(blockHit.getDirection());

                // Only set fire if the space is air
                if (level().getBlockState(pos).isAir() && level().getBlockState(pos.below()).isSolidRender(level(), pos.below())) {
                    level().setBlockAndUpdate(pos, Blocks.FIRE.defaultBlockState());
                }
            }

            if (this.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ModParticles.SPARK_PARTICLES.get(),
                        this.getX(), this.getY(), this.getZ(),
                        numParticles, 0, 0, 0, 0.08);
            }

            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);

        Level level = this.level();
        BlockPos hitPos = result.getBlockPos();
        BlockState hitState = level.getBlockState(hitPos);

        if (hitState.is(Blocks.TNT) && level().random.nextInt(2) == 1) {
            level.removeBlock(hitPos, false);

            PrimedTnt primedTnt = new PrimedTnt(level, hitPos.getX() + 0.5, hitPos.getY(), hitPos.getZ() + 0.5, (LivingEntity) this.getOwner());
            level.addFreshEntity(primedTnt);

            level.playSound(null, hitPos, SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
        }

        this.discard();
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

        this.setDeltaMovement(velocity.normalize().scale(this.slugSpeed));

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