package net.uhhitscam.knightfall.entity.custom;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.uhhitscam.knightfall.item.ModItems;
import net.uhhitscam.knightfall.entity.ModEntities;
import net.uhhitscam.knightfall.item.custom.GrenadeDefinition;
import net.uhhitscam.knightfall.item.custom.GrenadeDetonationContext;
import net.uhhitscam.knightfall.item.custom.GrenadeItem;
import net.uhhitscam.knightfall.item.custom.GrenadePhysics;
import net.uhhitscam.knightfall.item.custom.GrenadeRemoteDetonations;
import net.uhhitscam.knightfall.item.custom.GrenadeRemoteProfile;
import net.uhhitscam.knightfall.component.GrenadeRemoteLink;
import net.uhhitscam.knightfall.component.ModDataComponentTypes;
import org.jetbrains.annotations.Nullable;

public class GrenadeEntity extends ThrowableItemProjectile {
    private static final String FUSE_TAG = "Fuse";
    private static final String FUSE_RUNNING_TAG = "FuseRunning";
    private static final String RESTING_TAG = "Resting";
    private static final String STUCK_FACE_TAG = "StuckFace";
    private static final String DETONATED_TAG = "Detonated";
    private static final String BEEP_FLASH_TAG = "BeepFlash";
    private static final String REMOTE_REGISTERED_TAG = "RemoteRegistered";
    private static final String REMOTE_DETONATION_TICKS_TAG = "RemoteDetonationTicks";
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
    private static final EntityDataAccessor<Boolean> DATA_FUSE_RUNNING =
            SynchedEntityData.defineId(GrenadeEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_STUCK_FACE =
            SynchedEntityData.defineId(GrenadeEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_BEEP_FLASH_TICKS =
            SynchedEntityData.defineId(GrenadeEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_REMOTE_DETONATION_TICKS =
            SynchedEntityData.defineId(GrenadeEntity.class, EntityDataSerializers.INT);

    @Nullable
    private Vec3 velocityAfterImpact;
    @Nullable
    private Item dimensionsItem;
    @Nullable
    private Direction dimensionsFace;
    private long lastBounceSoundTick = Long.MIN_VALUE;
    private boolean detonated;
    private boolean remoteRegistered;

    public GrenadeEntity(EntityType<? extends GrenadeEntity> entityType, Level level) {
        super(entityType, level);
    }

    public GrenadeEntity(EntityType<? extends GrenadeEntity> entityType, Level level, LivingEntity owner) {
        super(entityType, owner, level);
    }

    @Override
    public void onAddedToLevel() {
        super.onAddedToLevel();
        if (level().isClientSide || remoteRegistered) {
            return;
        }

        GrenadeDefinition definition = getGrenadeDefinition();
        GrenadeRemoteLink link = getItem().get(ModDataComponentTypes.GRENADE_REMOTE_LINK.get());
        if (definition != null && definition.remoteProfile() != null && link != null) {
            GrenadeRemoteDetonations.get(((ServerLevel) level()).getServer()).registerDeployedCharge(link);
            remoteRegistered = true;
        }
    }

    @Override
    public void remove(RemovalReason reason) {
        if (!level().isClientSide && remoteRegistered && reason.shouldDestroy()) {
            GrenadeRemoteLink link = getItem().get(ModDataComponentTypes.GRENADE_REMOTE_LINK.get());
            if (link != null) {
                GrenadeRemoteDetonations detonations = GrenadeRemoteDetonations.get(((ServerLevel) level()).getServer());
                detonations.unregisterDeployedCharge(((ServerLevel) level()).getServer(), link);
            }
            remoteRegistered = false;
        }
        super.remove(reason);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.THERMAL_DETONATOR.get();
    }

    @Override
    public void setItem(ItemStack stack) {
        super.setItem(stack);
        dimensionsItem = stack.getItem();
        dimensionsFace = getStuckFace();
        refreshDimensions();
        setBoundingBox(makeBoundingBox());
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        GrenadeDefinition definition = getGrenadeDefinition();
        return definition != null
                ? EntityDimensions.fixed(definition.hitboxWidth(), definition.hitboxHeight())
                : super.getDimensions(pose);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_FUSE_TICKS, 80);
        builder.define(DATA_RESTING, false);
        builder.define(DATA_FUSE_RUNNING, true);
        builder.define(DATA_STUCK_FACE, -1);
        builder.define(DATA_BEEP_FLASH_TICKS, 0);
        builder.define(DATA_REMOTE_DETONATION_TICKS, -1);
    }

    public int getFuseTicks() {
        return entityData.get(DATA_FUSE_TICKS);
    }

    public void setFuseTicks(int fuseTicks) {
        entityData.set(DATA_FUSE_TICKS, Math.max(0, fuseTicks));
    }

    public boolean isFuseRunning() {
        return entityData.get(DATA_FUSE_RUNNING);
    }

    public void setFuseRunning(boolean fuseRunning) {
        entityData.set(DATA_FUSE_RUNNING, fuseRunning);
    }

    public boolean isResting() {
        return entityData.get(DATA_RESTING);
    }

    private void setResting(boolean resting) {
        entityData.set(DATA_RESTING, resting);
    }

    public boolean isStuck() {
        return entityData.get(DATA_STUCK_FACE) >= 0;
    }

    @Nullable
    public Direction getStuckFace() {
        int directionId = entityData.get(DATA_STUCK_FACE);
        return directionId >= 0 ? Direction.from3DDataValue(directionId) : null;
    }

    private void setStuckFace(@Nullable Direction direction) {
        entityData.set(DATA_STUCK_FACE, direction == null ? -1 : direction.get3DDataValue());
        dimensionsFace = direction;
        setBoundingBox(makeBoundingBox());
    }

    @Override
    protected AABB makeBoundingBox() {
        GrenadeDefinition definition = getGrenadeDefinition();
        Direction stuckFace = getStuckFace();
        if (definition == null || stuckFace == null) {
            return super.makeBoundingBox();
        }

        double halfWidth = definition.hitboxWidth() * 0.5;
        double halfDepth = definition.hitboxDepth() * 0.5;
        double height = definition.hitboxHeight();
        double x = getX();
        double y = getY();
        double z = getZ();

        return switch (stuckFace.getAxis()) {
            case Y -> new AABB(
                    x - halfWidth, y, z - halfWidth,
                    x + halfWidth, y + definition.hitboxDepth(), z + halfWidth
            );
            case Z -> new AABB(
                    x - halfWidth, y, z - halfDepth,
                    x + halfWidth, y + height, z + halfDepth
            );
            case X -> new AABB(
                    x - halfDepth, y, z - halfWidth,
                    x + halfDepth, y + height, z + halfWidth
            );
        };
    }

    public boolean isBeepFlashActive() {
        return entityData.get(DATA_BEEP_FLASH_TICKS) > 0;
    }

    public boolean isRemoteDetonationActivated() {
        return entityData.get(DATA_REMOTE_DETONATION_TICKS) >= 0;
    }

    private void setBeepFlashTicks(int ticks) {
        entityData.set(DATA_BEEP_FLASH_TICKS, Math.max(0, ticks));
    }

    @Nullable
    public GrenadeDefinition getGrenadeDefinition() {
        return getItem().getItem() instanceof GrenadeItem grenadeItem
                ? grenadeItem.getDefinition()
                : null;
    }

    @Override
    public void tick() {
        refreshDimensionsIfItemChanged();

        if (isStuck()) {
            setDeltaMovement(Vec3.ZERO);
        } else {
            updateRestingMotion();
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

        if (isBeepFlashActive()) {
            setBeepFlashTicks(entityData.get(DATA_BEEP_FLASH_TICKS) - 1);
        }

        GrenadeRemoteProfile remoteProfile = definition.remoteProfile();
        if (remoteProfile != null) {
            if (isRemoteDetonationActivated()) {
                int remainingTicks = entityData.get(DATA_REMOTE_DETONATION_TICKS) - 1;
                entityData.set(DATA_REMOTE_DETONATION_TICKS, remainingTicks);
                if (remainingTicks <= 0) {
                    detonate();
                }
                return;
            }

            GrenadeRemoteLink link = getItem().get(ModDataComponentTypes.GRENADE_REMOTE_LINK.get());
            if (link != null && GrenadeRemoteDetonations.get(((ServerLevel) level()).getServer()).isActivated(link)) {
                activateRemoteDetonation();
                return;
            }

            tickRemoteBeep(definition, remoteProfile);
            return;
        }

        if (!isFuseRunning()) {
            return;
        }

        int remainingFuseTicks = getFuseTicks() - 1;
        setFuseTicks(remainingFuseTicks);
        boolean shouldPlayBeep = definition.audio().shouldPlayBeep(
                remainingFuseTicks,
                definition.fuseTicks()
        );

        if (remainingFuseTicks <= 0) {
            if (definition.trigger().detonatesOnFuse()) {
                if (shouldPlayBeep) {
                    definition.audio().playBeep(level(), position(), remainingFuseTicks, definition.fuseTicks());
                }
                detonate();
            } else {
                discard();
            }
            return;
        }

        if (shouldPlayBeep) {
            definition.audio().playBeep(level(), position(), remainingFuseTicks, definition.fuseTicks());
        }
    }

    private void refreshDimensionsIfItemChanged() {
        Item currentItem = getItem().getItem();
        Direction currentFace = getStuckFace();
        if (currentItem != dimensionsItem || currentFace != dimensionsFace) {
            dimensionsItem = currentItem;
            dimensionsFace = currentFace;
            refreshDimensions();
            setBoundingBox(makeBoundingBox());
        }
    }

    private void tickRemoteBeep(GrenadeDefinition definition, GrenadeRemoteProfile remoteProfile) {
        if (!isFuseRunning() || !remoteProfile.beepsWhileDeployed()) {
            return;
        }

        int remainingBeepTicks = getFuseTicks() - 1;
        if (remainingBeepTicks > 0) {
            setFuseTicks(remainingBeepTicks);
            return;
        }

        definition.audio().beepSound().play(level(), position());
        setFuseTicks(remoteProfile.beepIntervalTicks());
        setBeepFlashTicks(3);
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

        if (definition.trigger().sticksToBlocks()) {
            startFuseIfNeeded(definition);
            if (!isStuck() && definition.canStickTo(level().getBlockState(result.getBlockPos()))) {
                stickToBlock(result, definition);
                return;
            }
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
                && !(target instanceof GrenadeEntity)
                && super.canHitEntity(target);
    }

    @Override
    public boolean isPickable() {
        return !isRemoved();
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        GrenadeDefinition definition = getGrenadeDefinition();
        if (detonated || isRemoteDetonationActivated() || isRemoved() || player.isSpectator()
                || definition == null || definition.remoteProfile() == null) {
            return InteractionResult.PASS;
        }

        if (level().isClientSide) {
            return InteractionResult.SUCCESS;
        }

        ItemStack pickedUpCharge = getItem().copyWithCount(1);
        pickedUpCharge.remove(ModDataComponentTypes.GRENADE_REMOTE_LINK.get());
        Item pickedUpItem = pickedUpCharge.getItem();
        if (!player.getInventory().add(pickedUpCharge) && !pickedUpCharge.isEmpty()) {
            player.drop(pickedUpCharge, false);
        }

        playSound(SoundEvents.ITEM_PICKUP, 0.2F, 1.0F);
        player.take(this, 1);
        player.awardStat(Stats.ITEM_PICKED_UP.get(pickedUpItem), 1);
        discard();
        return InteractionResult.CONSUME;
    }

    @Override
    public float getPickRadius() {
        return 0.0F;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (level().isClientSide || detonated || isRemoved()) {
            return false;
        }

        Entity directEntity = source.getDirectEntity();
        if (directEntity == null || !ModEntities.isGrenadeTriggeringProjectile(directEntity)) {
            return false;
        }

        detonate();
        return true;
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

    private void stickToBlock(BlockHitResult result, GrenadeDefinition definition) {
        Direction direction = result.getDirection();
        Vec3 normal = Vec3.atLowerCornerOf(direction.getNormal());
        setPos(result.getLocation().add(normal.scale(surfaceAttachmentDistance(direction, definition))));
        setDeltaMovement(Vec3.ZERO);
        velocityAfterImpact = null;
        setResting(false);
        setStuckFace(direction);
        startFuseIfNeeded(definition);

        if (!level().isClientSide) {
            definition.audio().bounceSound().play(level(), result.getLocation());
            GrenadeRemoteProfile remoteProfile = definition.remoteProfile();
            if (remoteProfile != null && remoteProfile.activationSoundOnStick()) {
                definition.audio().activationSound().play(level(), result.getLocation());
                if (remoteProfile.beepsWhileDeployed()) {
                    definition.audio().beepSound().play(level(), result.getLocation());
                    setFuseTicks(remoteProfile.beepIntervalTicks());
                }
            }
        }
    }

    public void placeOnSurface(Vec3 location, Direction direction) {
        Vec3 normal = Vec3.atLowerCornerOf(direction.getNormal());
        GrenadeDefinition definition = getGrenadeDefinition();
        double attachmentDistance = definition != null
                ? surfaceAttachmentDistance(direction, definition)
                : surfaceAttachmentDistance(direction, -0.05);
        setPos(location.add(normal.scale(attachmentDistance)));
        setDeltaMovement(Vec3.ZERO);
        velocityAfterImpact = null;
        setResting(false);
        setStuckFace(direction);
        setFuseRunning(true);
    }

    private double surfaceAttachmentDistance(Direction direction, GrenadeDefinition definition) {
        double adjustment = definition.surfaceAttachmentOffset();
        if (direction == Direction.UP) {
            return adjustment;
        }
        if (direction == Direction.DOWN) {
            return definition.hitboxDepth() + adjustment;
        }
        return definition.hitboxDepth() * 0.5 + adjustment;
    }

    private double surfaceAttachmentDistance(Direction direction, double adjustment) {
        if (direction == Direction.UP) {
            return adjustment;
        }
        if (direction == Direction.DOWN) {
            return getBbHeight() + adjustment;
        }
        return getBbWidth() * 0.5 + adjustment;
    }

    private void startFuseIfNeeded(GrenadeDefinition definition) {
        if (isFuseRunning()) {
            return;
        }

        setFuseTicks(definition.remoteProfile() != null
                ? definition.remoteProfile().beepIntervalTicks()
                : definition.fuseTicks());
        setFuseRunning(true);
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
        if (isStuck()) {
            setDeltaMovement(Vec3.ZERO);
            velocityAfterImpact = null;
            return;
        }

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

    public void activateRemoteDetonation() {
        if (level().isClientSide || detonated || isRemoteDetonationActivated()) {
            return;
        }

        GrenadeDefinition definition = getGrenadeDefinition();
        GrenadeRemoteProfile remoteProfile = definition != null ? definition.remoteProfile() : null;
        if (remoteProfile == null || remoteProfile.remoteDetonationDelayTicks() <= 0) {
            detonate();
            return;
        }

        setFuseRunning(false);
        setBeepFlashTicks(0);
        entityData.set(DATA_REMOTE_DETONATION_TICKS, remoteProfile.remoteDetonationDelayTicks());
        remoteProfile.remoteDetonationSound().play(level(), position());
    }

    @Override
    protected double getDefaultGravity() {
        if (isResting() || isStuck()) {
            return 0.0;
        }

        GrenadeDefinition definition = getGrenadeDefinition();
        return definition != null ? definition.physics().gravity() : 0.03;
    }

    @Override
    protected void updateRotation() {
        if (!isStuck() && getDeltaMovement().lengthSqr() > 1.0E-6) {
            super.updateRotation();
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt(FUSE_TAG, getFuseTicks());
        tag.putBoolean(FUSE_RUNNING_TAG, isFuseRunning());
        tag.putBoolean(RESTING_TAG, isResting());
        Direction stuckFace = getStuckFace();
        if (stuckFace != null) {
            tag.putInt(STUCK_FACE_TAG, stuckFace.get3DDataValue());
        }
        tag.putBoolean(DETONATED_TAG, detonated);
        tag.putInt(BEEP_FLASH_TAG, entityData.get(DATA_BEEP_FLASH_TICKS));
        tag.putBoolean(REMOTE_REGISTERED_TAG, remoteRegistered);
        tag.putInt(REMOTE_DETONATION_TICKS_TAG, entityData.get(DATA_REMOTE_DETONATION_TICKS));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains(FUSE_TAG)) {
            setFuseTicks(tag.getInt(FUSE_TAG));
        }
        if (tag.contains(FUSE_RUNNING_TAG)) {
            setFuseRunning(tag.getBoolean(FUSE_RUNNING_TAG));
        }
        setResting(tag.getBoolean(RESTING_TAG));
        setStuckFace(tag.contains(STUCK_FACE_TAG)
                ? Direction.from3DDataValue(tag.getInt(STUCK_FACE_TAG))
                : null);
        detonated = tag.getBoolean(DETONATED_TAG);
        setBeepFlashTicks(tag.getInt(BEEP_FLASH_TAG));
        remoteRegistered = tag.getBoolean(REMOTE_REGISTERED_TAG);
        entityData.set(
                DATA_REMOTE_DETONATION_TICKS,
                tag.contains(REMOTE_DETONATION_TICKS_TAG) ? tag.getInt(REMOTE_DETONATION_TICKS_TAG) : -1
        );
    }
}
