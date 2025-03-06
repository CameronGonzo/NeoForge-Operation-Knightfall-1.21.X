package net.uhhitscam.starwars.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.uhhitscam.starwars.entity.ModEntities;
import net.uhhitscam.starwars.item.ModItems;
import net.uhhitscam.starwars.particle.ModParticles;


public class IonizedTibannaBlasterBoltEntity extends Snowball {
    private final float bolt_speed;
    private final int blasterDamage;
    private final String currentGasType;
    private final String classification;
//    private BlockPos lastLightBlockPos;

    public IonizedTibannaBlasterBoltEntity(EntityType<? extends IonizedTibannaBlasterBoltEntity> entityType, Level level) {
        super(entityType, level);
        this.bolt_speed = 2.0F;
        this.blasterDamage = 0;
        this.currentGasType = "IONIZED_TIBANNA_GAS";
        this.classification = "PISTOL";
//        this.lastLightBlockPos = null; // Initialize last position as null
    }

    public IonizedTibannaBlasterBoltEntity(EntityType<? extends IonizedTibannaBlasterBoltEntity> entityType, Level level, LivingEntity shooter, float bolt_speed, int blasterDamage, String currentGasType, String classification) {
        super(ModEntities.IONIZED_TIBANNA_BLASTER_BOLT.get(), level); // Directly reference the EntityType
        this.bolt_speed = bolt_speed;
        this.blasterDamage = blasterDamage;
        this.currentGasType = currentGasType;
        this.classification = classification;
//        this.lastLightBlockPos = null; // Initialize last position as null

        Vec3 direction = shooter.getLookAngle().normalize().scale(bolt_speed);
        this.setDeltaMovement(direction);
    }

    public String getGasType() {
        return currentGasType;
    }

    protected Item getDefaultItem() {
        return ModItems.IONIZED_TIBANNA_BLASTER_BOLT.get();
    }

    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();

        int i = 0; //for now since no Droids have been added to the game yet
//      INT i = entity instanceof Droid ? 7 : 0; //Droids are damaged more by ionized gas
        int blasterBoltDamage = i + blasterDamage;

        if (entity.hurt(this.damageSources().thrown(this, this.getOwner()), blasterBoltDamage)) {
            //Reset the invulnerability timer to allow immediate damage from other bolts
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
        BlockPos currentPos = this.blockPosition();

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

//        // Check if the entity moved to a new block
//        if (lastLightBlockPos == null || !currentPos.equals(lastLightBlockPos)) {
//            // Remove the previous light block
//            if (lastLightBlockPos != null) {
//                if (this.level().getBlockState(lastLightBlockPos).is(Blocks.LIGHT)) {
//                    // Only remove the block if it's a light block
//                    this.level().setBlock(lastLightBlockPos, Blocks.AIR.defaultBlockState(), 3);
//                }
//            }
//
//            // Place a new light block at the current position, only if the block is air
//            if (this.level().getBlockState(currentPos).isAir()) {
//                this.level().setBlock(currentPos, Blocks.LIGHT.defaultBlockState().setValue(LightBlock.LEVEL, 5), 3);
//                lastLightBlockPos = currentPos; // Update the last position
//            }
//        }
//
//        // Remove the light block when the entity is dead or removed
//        if (!this.isAlive() && lastLightBlockPos != null) {
//            if (this.level().getBlockState(lastLightBlockPos).is(Blocks.LIGHT)) {
//                this.level().setBlock(lastLightBlockPos, Blocks.AIR.defaultBlockState(), 3);
//            }
//        }
//
//        // Only update delta movement and discard the entity if it's in a loaded chunk
//        if (!this.level().isClientSide && this.tickCount > 200) {
//            if (this.level().isLoaded(this.blockPosition())) {
//                this.discard();
//            }
//        }

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

//    @Override
//    public void onRemovedFromLevel() {
//        super.onRemovedFromLevel();
//        if (lastLightBlockPos != null) {
//            if (this.level().getBlockState(lastLightBlockPos).is(Blocks.LIGHT)) {
//                this.level().setBlock(lastLightBlockPos, Blocks.AIR.defaultBlockState(), 3);
//            }
//        }
//    }
}