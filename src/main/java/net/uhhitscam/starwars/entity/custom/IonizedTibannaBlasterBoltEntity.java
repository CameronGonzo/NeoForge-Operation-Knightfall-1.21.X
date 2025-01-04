package net.uhhitscam.starwars.entity.custom;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.uhhitscam.starwars.entity.ModEntities;
import net.uhhitscam.starwars.item.ModItems;

public class IonizedTibannaBlasterBoltEntity extends Snowball {
    private final float bolt_speed;
    private final int blasterDamage;
    private final String currentGasType;

    public IonizedTibannaBlasterBoltEntity(EntityType<? extends IonizedTibannaBlasterBoltEntity> entityType, Level level) {
        super(entityType, level);
        //base values just in case something goes wrong
        this.bolt_speed = 2.0F;
        this.blasterDamage = 0;
        this.currentGasType = "IONIZED_TIBANNA_GAS";
    }

    public IonizedTibannaBlasterBoltEntity(Level level, LivingEntity shooter, float bolt_speed, int blasterDamage, String currentGasType) {
        super(ModEntities.IONIZED_TIBANNA_BLASTER_BOLT.get(), level); // Directly reference the EntityType
        this.bolt_speed = bolt_speed;
        this.blasterDamage = blasterDamage;
        this.currentGasType = currentGasType;
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
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }

    }

    @Override
    public void tick() {
        super.tick();

        Vec3 velocity = this.getDeltaMovement();
        double speed = velocity.length(); // Magnitude of the velocity vector

        if (speed > 0.01) { // Only update direction if the entity is moving
            // Calculate yaw (horizontal rotation, rotation around Y-axis)
            double yaw = Math.toDegrees(Math.atan2(velocity.x, velocity.z)); // atan2 gives us the correct direction in the horizontal plane

            // Calculate pitch (vertical rotation, rotation around X-axis)
            double pitch = Math.toDegrees(Math.atan2(velocity.y, Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z)));

            // Prevent pitch from being too extreme when moving directly up or down
            if (Math.abs(pitch) > 90) {
                pitch = pitch > 0 ? 90 : -90;
            }

            // Negate the yaw and pitch to rotate in the opposite direction
            this.setYRot((float) yaw);  // Update yaw (Y rotation)
            this.setXRot((float) -pitch); // Update pitch (X rotation)
            this.yRotO = this.getYRot(); // Synchronize previous Y rotation
            this.xRotO = this.getXRot(); // Synchronize previous X rotation
        }

        // Keep the movement constant
        this.setDeltaMovement(velocity.normalize().scale(this.bolt_speed));

        if (!this.level().isClientSide && this.tickCount > 300) {
            this.discard();
        }
    }

    @Override
    protected double getDefaultGravity() {
        return 0.002F;
    }
}