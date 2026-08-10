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
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.uhhitscam.knightfall.item.ModItems;
import net.uhhitscam.knightfall.item.custom.GrenadeDefinition;
import net.uhhitscam.knightfall.item.custom.GrenadeDetonationContext;
import net.uhhitscam.knightfall.item.custom.GrenadeItem;
import net.uhhitscam.knightfall.item.custom.GrenadePhysics;
import org.jetbrains.annotations.Nullable;

public class GrenadeEntity extends ThrowableItemProjectile {
    private static final String FUSE_TAG = "Fuse";
    private static final String RESTING_TAG = "Resting";
    private static final String DETONATED_TAG = "Detonated";
    private static final double HIT_POSITION_EPSILON = 0.01;
    private static final double GROUND_PROBE_START_OFFSET = 0.02;
    private static final double GROUND_PROBE_DISTANCE = 0.08;
    private static final double GROUND_PROBE_INSET = 0.02;
    private static final double NORMAL_SETTLE_GRAVITY_MULTIPLIER = 2.0;
    private static final int BOUNCE_SOUND_COOLDOWN_TICKS = 4;
    private static final EntityDataAccessor<Integer> DATA_FUSE_TICKS =
            SynchedEntityData.defineId(GrenadeEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_RESTING =
            SynchedEntityData.defineId(GrenadeEntity.class, EntityDataSerializers.BOOLEAN);

    @Nullable
    private Vec3 velocityAfterImpact;
    private long lastBounceSoundTick = Long.MIN_VALUE;
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
        builder.define(DATA_RESTING, false);
    }

    public int getFuseTicks() {
        return entityData.get(DATA_FUSE_TICKS);
    }

    public void setFuseTicks(int fuseTicks) {
        entityData.set(DATA_FUSE_TICKS, Math.max(0, fuseTicks));
    }

    public boolean isResting() {
        return entityData.get(DATA_RESTING);
    }

    private void setResting(boolean resting) {
        entityData.set(DATA_RESTING, resting);
    }

    @Nullable
    public GrenadeDefinition getGrenadeDefinition() {
        return getItem().getItem() instanceof GrenadeItem grenadeItem
                ? grenadeItem.getDefinition()
                : null;
    }

    @Override
    public void tick() {
        updateRestingMotion();

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
    protected boolean canHitEntity(Entity target) {
        GrenadeDefinition definition = getGrenadeDefinition();
        return definition != null
                && definition.trigger().detonatesOnImpact()
                && super.canHitEntity(target);
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

        if (!definition.trigger().detonatesOnImpact()) {
            return;
        }

        stopAtImpact(result.getLocation());
        if (!level().isClientSide) {
            detonate();
        }
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
        boolean wasResting = isResting();
        double reflectedNormalSpeed = Math.abs(normalSpeed) * physics.normalRestitution();

        double distanceToHit = position().distanceTo(hitLocation);
        double remainingTravelFraction = Math.max(0.0, Math.min(1.0, 1.0 - distanceToHit / incomingSpeed));
        setPos(hitLocation.add(normal.scale(HIT_POSITION_EPSILON)));

        boolean hasGroundSupport = canRest || hasGroundSupport();
        double lowSpeedCutoff = Math.max(
                physics.minimumBounceSpeed() * 2.0,
                physics.gravity() * 1.5
        );
        double normalSettleSpeed = Math.max(
                physics.minimumBounceSpeed() * 2.0,
                physics.gravity() * NORMAL_SETTLE_GRAVITY_MULTIPLIER
        );
        if (hasGroundSupport
                && (wasResting
                || reflectedNormalSpeed < physics.minimumBounceSpeed()
                || Math.abs(normalSpeed) <= normalSettleSpeed
                || incomingSpeed < lowSpeedCutoff)) {
            reflectedVelocity = new Vec3(reflectedVelocity.x, 0.0, reflectedVelocity.z);
            if (reflectedVelocity.horizontalDistance() < physics.minimumBounceSpeed()) {
                reflectedVelocity = Vec3.ZERO;
            }
            setResting(true);
        } else if (reflectedVelocity.length() < physics.minimumBounceSpeed()) {
            reflectedVelocity = Vec3.ZERO;
            setResting(false);
        } else {
            setResting(false);
        }

        setDeltaMovement(reflectedVelocity.scale(remainingTravelFraction));
        velocityAfterImpact = reflectedVelocity;

        long gameTime = level().getGameTime();
        if (!level().isClientSide
                && Math.abs(normalSpeed) > normalSettleSpeed
                && !wasResting
                && (lastBounceSoundTick == Long.MIN_VALUE
                || gameTime - lastBounceSoundTick >= BOUNCE_SOUND_COOLDOWN_TICKS)) {
            definition.audio().bounceSound().play(level(), hitLocation);
            lastBounceSoundTick = gameTime;
        }
    }

    private void updateRestingMotion() {
        if (!isResting()) {
            return;
        }

        if (!hasGroundSupport()) {
            setResting(false);
            return;
        }

        GrenadeDefinition definition = getGrenadeDefinition();
        if (definition == null) {
            setDeltaMovement(Vec3.ZERO);
            return;
        }

        GrenadePhysics physics = definition.physics();
        Vec3 movement = getDeltaMovement();
        Vec3 slidingVelocity = new Vec3(movement.x, 0.0, movement.z)
                .scale(physics.tangentialRetention());
        if (slidingVelocity.horizontalDistance() < physics.minimumBounceSpeed()) {
            slidingVelocity = Vec3.ZERO;
        }
        setDeltaMovement(slidingVelocity);
    }

    private boolean hasGroundSupport() {
        double minX = getBoundingBox().minX + GROUND_PROBE_INSET;
        double maxX = getBoundingBox().maxX - GROUND_PROBE_INSET;
        double minZ = getBoundingBox().minZ + GROUND_PROBE_INSET;
        double maxZ = getBoundingBox().maxZ - GROUND_PROBE_INSET;
        double[] xSamples = {getX(), minX, maxX};
        double[] zSamples = {getZ(), minZ, maxZ};

        for (double x : xSamples) {
            for (double z : zSamples) {
                if (hasGroundSupportAt(x, z)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean hasGroundSupportAt(double x, double z) {
        double bottomY = getBoundingBox().minY;
        Vec3 from = new Vec3(x, bottomY + GROUND_PROBE_START_OFFSET, z);
        Vec3 to = new Vec3(x, bottomY - GROUND_PROBE_DISTANCE, z);
        BlockHitResult result = level().clip(new ClipContext(
                from,
                to,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                this
        ));
        return result.getType() == HitResult.Type.BLOCK && result.getDirection() == Direction.UP;
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
        if (!isResting()) {
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
        if (isResting()) {
            return 0.0;
        }

        GrenadeDefinition definition = getGrenadeDefinition();
        return definition != null ? definition.physics().gravity() : 0.03;
    }

    @Override
    protected void updateRotation() {
        if (getDeltaMovement().lengthSqr() > 1.0E-6) {
            super.updateRotation();
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt(FUSE_TAG, getFuseTicks());
        tag.putBoolean(RESTING_TAG, isResting());
        tag.putBoolean(DETONATED_TAG, detonated);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains(FUSE_TAG)) {
            setFuseTicks(tag.getInt(FUSE_TAG));
        }
        setResting(tag.getBoolean(RESTING_TAG));
        detonated = tag.getBoolean(DETONATED_TAG);
    }
}
