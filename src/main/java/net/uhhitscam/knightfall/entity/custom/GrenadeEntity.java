package net.uhhitscam.knightfall.entity.custom;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.uhhitscam.knightfall.item.ModItems;
import net.uhhitscam.knightfall.item.custom.GrenadeDefinition;
import net.uhhitscam.knightfall.item.custom.GrenadeDetonationContext;
import net.uhhitscam.knightfall.item.custom.GrenadeItem;
import net.uhhitscam.knightfall.item.custom.GrenadePhysics;
import org.jetbrains.annotations.Nullable;

public class GrenadeEntity extends ThrowableItemProjectile {
    private static final String FUSE_TAG = "Fuse";
    private static final String DETONATED_TAG = "Detonated";
    private static final double HIT_POSITION_EPSILON = 0.01;
    private static final EntityDataAccessor<Integer> DATA_FUSE_TICKS =
            SynchedEntityData.defineId(GrenadeEntity.class, EntityDataSerializers.INT);

    @Nullable
    private Vec3 velocityAfterImpact;
    private long lastBounceSoundTick = Long.MIN_VALUE;
    private boolean resting;
    private boolean detonated;

    public GrenadeEntity(EntityType<? extends GrenadeEntity> entityType, Level level) {
        super(entityType, level);
    }

    public GrenadeEntity(EntityType<? extends GrenadeEntity> entityType, Level level, LivingEntity owner) {
        super(entityType, owner, level);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.THERMAL_DETONATOR.get();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_FUSE_TICKS, 80);
    }

    public int getFuseTicks() {
        return entityData.get(DATA_FUSE_TICKS);
    }

    public void setFuseTicks(int fuseTicks) {
        entityData.set(DATA_FUSE_TICKS, Math.max(0, fuseTicks));
    }

    @Nullable
    public GrenadeDefinition getGrenadeDefinition() {
        return getItem().getItem() instanceof GrenadeItem grenadeItem
                ? grenadeItem.getDefinition()
                : null;
    }

    @Override
    public void tick() {
        if (resting && level().noCollision(this, getBoundingBox().move(0.0, -0.02, 0.0))) {
            resting = false;
        }

        super.tick();

        if (isRemoved()) {
            return;
        }

        restoreVelocityAfterImpact();

        if (level().isClientSide) {
            return;
        }

        GrenadeDefinition definition = getGrenadeDefinition();
        if (definition == null || detonated) {
            discard();
            return;
        }

        int remainingFuseTicks = getFuseTicks() - 1;
        setFuseTicks(remainingFuseTicks);

        if (remainingFuseTicks <= 0) {
            if (definition.trigger().detonatesOnFuse()) {
                detonate();
            } else {
                discard();
            }
            return;
        }

        if (definition.audio().shouldPlayBeep(remainingFuseTicks, definition.fuseTicks())) {
            definition.audio().beepSound().play(level(), position());
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);

        GrenadeDefinition definition = getGrenadeDefinition();
        if (definition == null) {
            if (!level().isClientSide) {
                discard();
            }
            return;
        }

        if (definition.trigger().detonatesOnImpact()) {
            stopAtImpact(result.getLocation());
            if (!level().isClientSide) {
                detonate();
            }
            return;
        }

        Direction direction = result.getDirection();
        Vec3 normal = Vec3.atLowerCornerOf(direction.getNormal());
        bounce(result.getLocation(), normal, definition, direction == Direction.UP);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);

        GrenadeDefinition definition = getGrenadeDefinition();
        if (definition == null) {
            if (!level().isClientSide) {
                discard();
            }
            return;
        }

        if (definition.trigger().detonatesOnImpact()) {
            stopAtImpact(result.getLocation());
            if (!level().isClientSide) {
                detonate();
            }
            return;
        }

        Vec3 entityCenter = result.getEntity().getBoundingBox().getCenter();
        Vec3 normal = result.getLocation().subtract(entityCenter);
        if (normal.lengthSqr() < 1.0E-8) {
            normal = getDeltaMovement().scale(-1.0);
        }
        bounce(result.getLocation(), normal, definition, false);
    }

    private void bounce(Vec3 hitLocation, Vec3 rawNormal, GrenadeDefinition definition, boolean canRest) {
        Vec3 incomingVelocity = getDeltaMovement();
        double incomingSpeed = incomingVelocity.length();
        if (incomingSpeed < 1.0E-8 || rawNormal.lengthSqr() < 1.0E-8) {
            stopAtImpact(hitLocation);
            return;
        }

        Vec3 normal = rawNormal.normalize();
        GrenadePhysics physics = definition.physics();
        double normalSpeed = incomingVelocity.dot(normal);
        Vec3 normalComponent = normal.scale(normalSpeed);
        Vec3 tangentComponent = incomingVelocity.subtract(normalComponent);
        Vec3 reflectedVelocity = tangentComponent.scale(physics.tangentialRetention())
                .subtract(normalComponent.scale(physics.normalRestitution()));

        if (reflectedVelocity.length() < physics.minimumBounceSpeed()) {
            reflectedVelocity = Vec3.ZERO;
            resting = canRest;
        } else {
            resting = false;
        }

        double distanceToHit = position().distanceTo(hitLocation);
        double remainingTravelFraction = Math.max(0.0, Math.min(1.0, 1.0 - distanceToHit / incomingSpeed));
        setPos(hitLocation.add(normal.scale(HIT_POSITION_EPSILON)));
        setDeltaMovement(reflectedVelocity.scale(remainingTravelFraction));
        velocityAfterImpact = reflectedVelocity;

        if (!level().isClientSide
                && Math.abs(normalSpeed) >= physics.minimumBounceSpeed()
                && level().getGameTime() != lastBounceSoundTick) {
            definition.audio().bounceSound().play(level(), hitLocation);
            lastBounceSoundTick = level().getGameTime();
        }
    }

    private void stopAtImpact(Vec3 hitLocation) {
        setPos(hitLocation);
        setDeltaMovement(Vec3.ZERO);
        velocityAfterImpact = null;
    }

    private void restoreVelocityAfterImpact() {
        if (velocityAfterImpact == null) {
            return;
        }

        double drag = isInWater() ? 0.8 : 0.99;
        Vec3 restoredVelocity = velocityAfterImpact.scale(drag);
        if (!resting) {
            restoredVelocity = restoredVelocity.add(0.0, -getDefaultGravity(), 0.0);
        }

        setDeltaMovement(restoredVelocity);
        velocityAfterImpact = null;
    }

    public void detonate() {
        if (level().isClientSide || detonated) {
            return;
        }

        GrenadeDefinition definition = getGrenadeDefinition();
        if (definition == null || !(level() instanceof ServerLevel serverLevel)) {
            discard();
            return;
        }

        detonated = true;
        LivingEntity owner = getOwner() instanceof LivingEntity livingEntity ? livingEntity : null;

        try {
            definition.effect().detonate(new GrenadeDetonationContext(
                    serverLevel,
                    this,
                    owner,
                    definition,
                    position()
            ));
        } finally {
            discard();
        }
    }

    @Override
    protected double getDefaultGravity() {
        if (resting) {
            return 0.0;
        }

        GrenadeDefinition definition = getGrenadeDefinition();
        return definition != null ? definition.physics().gravity() : 0.03;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt(FUSE_TAG, getFuseTicks());
        tag.putBoolean(DETONATED_TAG, detonated);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains(FUSE_TAG)) {
            setFuseTicks(tag.getInt(FUSE_TAG));
        }
        detonated = tag.getBoolean(DETONATED_TAG);
    }
}
