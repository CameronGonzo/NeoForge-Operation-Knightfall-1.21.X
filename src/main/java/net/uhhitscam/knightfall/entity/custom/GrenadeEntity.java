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
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
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
import net.uhhitscam.knightfall.item.custom.grenade.GrenadeDefinition;
import net.uhhitscam.knightfall.item.custom.grenade.GrenadeEffects;
import net.uhhitscam.knightfall.item.custom.grenade.GrenadeDetonationDelayProfile;
import net.uhhitscam.knightfall.item.custom.grenade.GrenadeDetonationContext;
import net.uhhitscam.knightfall.item.custom.grenade.GrenadeFuseSoundMode;
import net.uhhitscam.knightfall.item.custom.grenade.GrenadeImplosionProfile;
import net.uhhitscam.knightfall.item.custom.grenade.GrenadeItem;
import net.uhhitscam.knightfall.item.custom.grenade.GrenadePhysics;
import net.uhhitscam.knightfall.item.custom.grenade.GrenadePressureProfile;
import net.uhhitscam.knightfall.item.custom.grenade.GrenadeProximityProfile;
import net.uhhitscam.knightfall.item.custom.grenade.GrenadeRemoteDetonations;
import net.uhhitscam.knightfall.item.custom.grenade.GrenadeRemoteProfile;
import net.uhhitscam.knightfall.item.custom.grenade.GrenadeSmokeProfile;
import net.uhhitscam.knightfall.item.custom.grenade.GrenadeShockFieldProfile;
import net.uhhitscam.knightfall.item.custom.grenade.GrenadeIonArcProfile;
import net.uhhitscam.knightfall.effect.custom.LightningShockEffect;
import net.uhhitscam.knightfall.effect.EffectActionRules;
import net.uhhitscam.knightfall.network.CSIonArcPacket;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Vector3f;
import net.uhhitscam.knightfall.particle.ModParticles;
import net.uhhitscam.knightfall.component.GrenadeRemoteLink;
import net.uhhitscam.knightfall.component.ModDataComponentTypes;
import net.uhhitscam.knightfall.util.SmokeScreenUtil;
import net.uhhitscam.knightfall.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class GrenadeEntity extends ThrowableItemProjectile {
    private static final String FUSE_TAG = "Fuse";
    private static final String FUSE_RUNNING_TAG = "FuseRunning";
    private static final String RESTING_TAG = "Resting";
    private static final String STUCK_FACE_TAG = "StuckFace";
    private static final String DETONATED_TAG = "Detonated";
    private static final String BEEP_FLASH_TAG = "BeepFlash";
    private static final String REMOTE_REGISTERED_TAG = "RemoteRegistered";
    private static final String REMOTE_DETONATION_TICKS_TAG = "RemoteDetonationTicks";
    private static final String ONE_SHOT_FUSE_SOUND_PLAYED_TAG = "OneShotFuseSoundPlayed";
    private static final String SOUND_LOOP_TICKS_TAG = "SoundLoopTicks";
    private static final String IMPLOSION_TICKS_TAG = "ImplosionTicks";
    private static final String DETONATION_DELAY_TICKS_TAG = "DetonationDelayTicks";
    private static final String PROXIMITY_AGE_TAG = "ProximityAgeTicks";
    private static final String SHOCK_FIELD_END_TAG = "ShockFieldEndTick";
    private static final String PRESSURE_ACTIVATED_TAG = "PressureActivated";
    private static final double HIT_POSITION_EPSILON = 0.01;
    private static final double GROUND_PROBE_START_OFFSET = 0.02;
    private static final double GROUND_PROBE_DISTANCE = 0.08;
    private static final double GROUND_PROBE_INSET = 0.02;
    private static final double NORMAL_SETTLE_GRAVITY_MULTIPLIER = 2.0;
    private static final int BOUNCE_SOUND_COOLDOWN_TICKS = 4;
    private static final double PUNCH_HORIZONTAL_SPEED = 0.45;
    private static final double PUNCH_UPWARD_SPEED = 0.20;
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
    private static final EntityDataAccessor<Integer> DATA_IMPLOSION_TICKS =
            SynchedEntityData.defineId(GrenadeEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_DETONATION_DELAY_TICKS =
            SynchedEntityData.defineId(GrenadeEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Long> DATA_SHOCK_FIELD_END_TICK =
            SynchedEntityData.defineId(GrenadeEntity.class, EntityDataSerializers.LONG);
    private static final EntityDataAccessor<Boolean> DATA_PRESSURE_ACTIVATED =
            SynchedEntityData.defineId(GrenadeEntity.class, EntityDataSerializers.BOOLEAN);

    @Nullable
    private Vec3 velocityAfterImpact;
    @Nullable
    private Item dimensionsItem;
    @Nullable
    private Direction dimensionsFace;
    private long lastBounceSoundTick = Long.MIN_VALUE;
    private boolean detonated;
    private boolean remoteRegistered;
    private boolean oneShotFuseSoundPlayed;
    private int soundLoopTicks;
    private int proximityAgeTicks;

    public GrenadeEntity(EntityType<? extends GrenadeEntity> entityType, Level level) {
        super(entityType, level);
    }

    public GrenadeEntity(EntityType<? extends GrenadeEntity> entityType, Level level, LivingEntity owner) {
        super(entityType, owner, level, net.minecraft.world.item.ItemStack.EMPTY);
    }

    @Override
    public void onAddedToLevel() {
        super.onAddedToLevel();
        if (level().isClientSide() || remoteRegistered) {
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
        if (!level().isClientSide() && remoteRegistered && reason.shouldDestroy()) {
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
        builder.define(DATA_IMPLOSION_TICKS, -1);
        builder.define(DATA_DETONATION_DELAY_TICKS, -1);
        builder.define(DATA_SHOCK_FIELD_END_TICK, -1L);
        builder.define(DATA_PRESSURE_ACTIVATED, false);
    }

    public int getFuseTicks() {
        return entityData.get(DATA_FUSE_TICKS);
    }

    public boolean isPressureActivated() {
        return entityData.get(DATA_PRESSURE_ACTIVATED);
    }

    public boolean isShockFieldActive() {
        return entityData.get(DATA_SHOCK_FIELD_END_TICK) >= 0L;
    }

    public int getShockFieldRemainingTicks() {
        return (int) Math.max(0L, entityData.get(DATA_SHOCK_FIELD_END_TICK) - level().getGameTime());
    }

    public void setFuseTicks(int fuseTicks) {
        entityData.set(DATA_FUSE_TICKS, Math.max(0, fuseTicks));
    }

    public boolean isFuseRunning() {
        return entityData.get(DATA_FUSE_RUNNING);
    }

    public boolean isDetonationDelayed() {
        return entityData.get(DATA_DETONATION_DELAY_TICKS) >= 0;
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
    protected AABB makeBoundingBox(Vec3 position) {
        GrenadeDefinition definition = getGrenadeDefinition();
        Direction stuckFace = getStuckFace();
        if (definition == null || stuckFace == null) {
            return super.makeBoundingBox(position);
        }

        double halfWidth = definition.hitboxWidth() * 0.5;
        double halfDepth = definition.hitboxDepth() * 0.5;
        double height = definition.hitboxHeight();
        double x = position.x;
        double y = position.y;
        double z = position.z;

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

    public void setSoundLoopTicks(int ticks) {
        soundLoopTicks = Math.max(0, ticks);
    }

    public boolean isRemoteDetonationActivated() {
        return entityData.get(DATA_REMOTE_DETONATION_TICKS) >= 0;
    }

    public boolean isImploding() {
        return entityData.get(DATA_IMPLOSION_TICKS) >= 0;
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

        if (isStuck() || isShockFieldActive()) {
            setDeltaMovement(Vec3.ZERO);
        } else {
            updateRestingMotion();
        }

        super.tick();

        if (isRemoved()) {
            return;
        }

        restoreVelocityAfterImpact();

        GrenadeDefinition definition = getGrenadeDefinition();
        if (level().isClientSide()) {
            if (definition != null && definition.smokeProfile() != null) {
                tickSmokeParticles(definition.smokeProfile());
            }
            return;
        }

        if (definition != null && isShockFieldActive()) {
            tickShockField(definition.shockFieldProfile());
            return;
        }

        if (definition == null || detonated) {
            discard();
            return;
        }

        if (definition.smokeProfile() != null) {
            clearTargetsThroughSmoke(definition.smokeProfile());
        }

        if (isImploding()) {
            tickImplosion(definition);
            return;
        }

        if (isDetonationDelayed()) {
            tickDetonationDelay(definition);
            return;
        }

        if (definition.pressureProfile() != null) {
            tickPressureMine(definition);
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

        GrenadeProximityProfile proximityProfile = definition.proximityProfile();
        if (proximityProfile != null && tickProximity(proximityProfile)) {
            return;
        }

        if (!isFuseRunning()) {
            return;
        }

        playOneShotFuseSoundIfNeeded(definition);

        int currentFuseTicks = getFuseTicks();
        int remainingFuseTicks = currentFuseTicks - 1;
        setFuseTicks(remainingFuseTicks);
        boolean shouldPlayBeep;
        if (definition.fuseSoundMode() == GrenadeFuseSoundMode.ONCE_BEFORE_DETONATION) {
            shouldPlayBeep = !oneShotFuseSoundPlayed && remainingFuseTicks > 0
                    && remainingFuseTicks <= definition.audio().urgentThresholdTicks();
            if (shouldPlayBeep) {
                oneShotFuseSoundPlayed = true;
            }
        } else if (definition.fuseSoundMode() == GrenadeFuseSoundMode.REPEATED_AFTER_THROW) {
            boolean firstFuseTick = currentFuseTicks == definition.fuseTicks();
            if (!firstFuseTick) {
                soundLoopTicks++;
            }
            shouldPlayBeep = firstFuseTick
                    || soundLoopTicks >= definition.audio().normalBeepIntervalTicks();
            if (shouldPlayBeep) {
                soundLoopTicks = 0;
            }
        } else {
            shouldPlayBeep = definition.fuseSoundMode() == GrenadeFuseSoundMode.SCHEDULED_BEEPS
                    && definition.audio().shouldPlayBeep(
                    remainingFuseTicks,
                    definition.fuseTicks()
            );
        }

        if (remainingFuseTicks <= 0) {
            if (definition.trigger().detonatesOnFuse()) {
                if (shouldPlayBeep
                        && definition.fuseSoundMode() == GrenadeFuseSoundMode.SCHEDULED_BEEPS) {
                    definition.audio().playBeep(level(), position(), remainingFuseTicks, definition.fuseTicks());
                }
                detonate();
            } else {
                discard();
            }
            return;
        }

        if (shouldPlayBeep) {
            if (definition.fuseSoundMode() == GrenadeFuseSoundMode.REPEATED_AFTER_THROW) {
                definition.audio().beepSound().play(level(), position());
                setBeepFlashTicks(3);
            } else {
                definition.audio().playBeep(level(), position(), remainingFuseTicks, definition.fuseTicks());
            }
        }
    }

    private boolean tickProximity(GrenadeProximityProfile profile) {
        if (++proximityAgeTicks < profile.armingTicks()) {
            return false;
        }

        Vec3 center = getBoundingBox().getCenter();
        AABB searchBounds = new AABB(center, center).inflate(profile.radius());
        double radiusSquared = profile.radius() * profile.radius();
        Entity owner = getOwner();
        for (LivingEntity target : level().getEntitiesOfClass(LivingEntity.class, searchBounds)) {
            if (target == owner || !target.isAlive() || target.isSpectator()) {
                continue;
            }

            AABB targetBounds = target.getBoundingBox();
            double nearestX = Math.max(targetBounds.minX, Math.min(center.x, targetBounds.maxX));
            double nearestY = Math.max(targetBounds.minY, Math.min(center.y, targetBounds.maxY));
            double nearestZ = Math.max(targetBounds.minZ, Math.min(center.z, targetBounds.maxZ));
            if (center.distanceToSqr(nearestX, nearestY, nearestZ) <= radiusSquared) {
                detonate();
                return true;
            }
        }
        return false;
    }

    private void tickSmokeParticles(GrenadeSmokeProfile profile) {
        if (!SmokeScreenUtil.isActive(this)) {
            return;
        }

        Vec3 center = SmokeScreenUtil.center(this, profile);
        double radius = SmokeScreenUtil.activeRadius(this, profile);
        if (radius <= 0.0) {
            return;
        }

        for (int i = 0; i < profile.particlesPerTick(); i++) {
            double vertical = random.nextDouble() * 2.0 - 1.0;
            double horizontal = Math.sqrt(Math.max(0.0, 1.0 - vertical * vertical));
            double angle = random.nextDouble() * Math.PI * 2.0;
            double distance = radius * Math.cbrt(random.nextDouble());
            Vec3 direction = new Vec3(
                    Math.cos(angle) * horizontal,
                    vertical,
                    Math.sin(angle) * horizontal
            );
            Vec3 offset = direction.scale(distance);
            Vec3 velocity = direction.scale(profile.particleDrift())
                    .add(0.0, profile.particleDrift() * 0.35, 0.0);

            level().addParticle(
                    ModParticles.SMOKE_SCREEN_PARTICLES.get(),
                    center.x + offset.x,
                    center.y + offset.y,
                    center.z + offset.z,
                    velocity.x,
                    velocity.y,
                    velocity.z
            );
        }
    }

    private void clearTargetsThroughSmoke(GrenadeSmokeProfile profile) {
        if (tickCount % 5 != 0 || !SmokeScreenUtil.isActive(this)) {
            return;
        }

        Vec3 center = SmokeScreenUtil.center(this, profile);
        double radius = SmokeScreenUtil.activeRadius(this, profile);
        AABB searchBounds = new AABB(center, center).inflate(profile.mobTargetingRange());
        for (Mob mob : level().getEntitiesOfClass(Mob.class, searchBounds)) {
            LivingEntity target = mob.getTarget();
            if (target != null && SmokeScreenUtil.segmentIntersectsSphere(
                    mob.getEyePosition(),
                    target.getEyePosition(),
                    center,
                    radius
            )) {
                mob.setTarget(null);
            }
        }
    }

    private void playOneShotFuseSoundIfNeeded(GrenadeDefinition definition) {
        if (oneShotFuseSoundPlayed
                || definition.fuseSoundMode()
                != GrenadeFuseSoundMode.ONCE_WHEN_THROWN) {
            return;
        }

        oneShotFuseSoundPlayed = true;
        definition.audio().beepSound().playAttached(this);
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
            if (!level().isClientSide()) {
                discard();
            }
            return;
        }

        if (definition.trigger().detonatesOnImpact()) {
            stopAtImpact(result.getLocation());
            if (!level().isClientSide()) {
                definition.audio().bounceSound().play(level(), result.getLocation());
                detonate();
            }
            return;
        }

        if (definition.trigger().sticksToBlocks()) {
            startFuseIfNeeded(definition);
            if (!isStuck() && definition.canStickTo(level().getBlockState(result.getBlockPos()))
                    && (definition.pressureProfile() == null || result.getDirection() == Direction.UP)) {
                stickToBlock(result, definition);
                return;
            }
        }

        Direction direction = result.getDirection();
        Vec3 normal = Vec3.atLowerCornerOf(direction.getUnitVec3i());
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
        return !isRemoved() && !isShockFieldActive();
    }

    @Override
    public boolean skipAttackInteraction(Entity attacker) {
        if (!(attacker instanceof Player player)) {
            return super.skipAttackInteraction(attacker);
        }

        // Handle punches before vanilla melee damage, sprint knockback, or projectile redirection.
        if (!level().isClientSide() && player.isAlive() && !player.isSpectator()
                && !EffectActionRules.blocksActions(player) && canBePunched()) {
            knockAway(player);
        }
        return true;
    }

    private boolean canBePunched() {
        return !isRemoved() && !detonated && !isStuck() && !isShockFieldActive() && !SmokeScreenUtil.isActive(this)
                && !isImploding() && !isDetonationDelayed() && !isRemoteDetonationActivated()
                && !isPressureActivated()
                && getGrenadeDefinition() != null;
    }

    private void knockAway(Player player) {
        Vec3 direction = player.getLookAngle().multiply(1.0, 0.0, 1.0);
        if (direction.lengthSqr() < 1.0E-6) {
            direction = position().subtract(player.position()).multiply(1.0, 0.0, 1.0);
        }
        if (direction.lengthSqr() < 1.0E-6) {
            direction = Vec3.directionFromRotation(0.0F, player.getYRot());
        }

        setResting(false);
        setOnGround(false);
        setNoGravity(false);
        velocityAfterImpact = null;
        setDeltaMovement(direction.normalize().scale(PUNCH_HORIZONTAL_SPEED).add(0.0, PUNCH_UPWARD_SPEED, 0.0));
        // Use vanilla motion synchronization without changing ownership, fuse, or remote links.
        hurtMarked = true;
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand, Vec3 location) {
        GrenadeDefinition definition = getGrenadeDefinition();
        if (detonated || isRemoteDetonationActivated() || isRemoved() || player.isSpectator()
                || definition == null || definition.remoteProfile() == null && definition.pressureProfile() == null) {
            return InteractionResult.PASS;
        }

        if (level().isClientSide()) {
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

    public boolean disarmAndDrop() {
        if (!(level() instanceof ServerLevel serverLevel) || isRemoved() || detonated
                || !getItem().is(ModTags.Items.ELECTRICALLY_DISARMABLE_EXPLOSIVES)) {
            return false;
        }

        detonated = true; // Prevent any pending fuse or remote trigger from exploding during removal.
        setFuseRunning(false);
        ItemStack recovered = getItem().copyWithCount(1);
        recovered.remove(ModDataComponentTypes.GRENADE_REMOTE_LINK.get());
        var dropped = new ItemEntity(serverLevel, getX(), getY(), getZ(), recovered);
        dropped.setDeltaMovement(Vec3.ZERO);
        dropped.setDefaultPickUpDelay();
        serverLevel.addFreshEntity(dropped);
        // Keep the original entity's link until removal so deployed counts and detonators get cleaned up.
        discard();
        return true;
    }

    @Override
    public boolean hurtServer(net.minecraft.server.level.ServerLevel serverLevel, DamageSource source, float amount) {
        if (level().isClientSide() || detonated || isRemoved()) {
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
            if (!level().isClientSide()) {
                discard();
            }
            return;
        }

        if (!definition.trigger().detonatesOnImpact()) {
            return;
        }

        stopAtImpact(result.getLocation());
        if (!level().isClientSide()) {
            definition.audio().bounceSound().play(level(), result.getLocation());
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
        if (!level().isClientSide()
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
        Vec3 normal = Vec3.atLowerCornerOf(direction.getUnitVec3i());
        setPos(result.getLocation().add(normal.scale(surfaceAttachmentDistance(direction, definition))));
        setDeltaMovement(Vec3.ZERO);
        velocityAfterImpact = null;
        setResting(false);
        setStuckFace(direction);
        startFuseIfNeeded(definition);

        if (!level().isClientSide()) {
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
        Vec3 normal = Vec3.atLowerCornerOf(direction.getUnitVec3i());
        GrenadeDefinition definition = getGrenadeDefinition();
        double attachmentDistance = definition != null
                ? surfaceAttachmentDistance(direction, definition)
                : surfaceAttachmentDistance(direction, -0.05);
        setPos(location.add(normal.scale(attachmentDistance)));
        setDeltaMovement(Vec3.ZERO);
        velocityAfterImpact = null;
        setResting(false);
        setStuckFace(direction);
        setFuseRunning(definition == null || definition.pressureProfile() == null);
    }

    private void tickPressureMine(GrenadeDefinition definition) {
        if (!isStuck()) {
            return;
        }
        GrenadePressureProfile profile = definition.pressureProfile();
        AABB footprint = getBoundingBox();
        AABB detection = new AABB(footprint.minX, footprint.minY, footprint.minZ,
                footprint.maxX, footprint.maxY + profile.detectionHeight(), footprint.maxZ);
        boolean pressed = !level().getEntitiesOfClass(LivingEntity.class, detection,
                target -> target.isAlive() && !target.isSpectator()
                        && target.getBoundingBox().minY >= footprint.minY - 0.1).isEmpty();
        if (!pressed) {
            Set<Item> itemTypes = new HashSet<>();
            for (ItemEntity item : level().getEntitiesOfClass(ItemEntity.class, detection)) {
                if (item.isAlive() && !item.getItem().isEmpty()) {
                    itemTypes.add(item.getItem().getItem());
                    if (itemTypes.size() >= profile.requiredItemTypes()) {
                        pressed = true;
                        break;
                    }
                }
            }
        }
        if (pressed && !isPressureActivated()) {
            entityData.set(DATA_PRESSURE_ACTIVATED, true);
            definition.audio().activationSound().play(level(), position());
        } else if (!pressed && isPressureActivated()) {
            detonate();
        }
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
        if (level().isClientSide() || detonated) {
            return;
        }

        GrenadeDefinition definition = getGrenadeDefinition();
        if (definition == null || !(level() instanceof ServerLevel serverLevel)) {
            discard();
            return;
        }

        GrenadeImplosionProfile implosionProfile = definition.implosionProfile();
        if (implosionProfile != null) {
            if (!isImploding()) {
                beginImplosion(implosionProfile);
            }
            return;
        }

        GrenadeDetonationDelayProfile delayProfile = definition.detonationDelayProfile();
        if (delayProfile != null) {
            if (!isDetonationDelayed()) {
                beginDetonationDelay(delayProfile);
            }
            return;
        }

        completeDetonation(definition, serverLevel);
    }

    private void beginDetonationDelay(GrenadeDetonationDelayProfile profile) {
        setFuseRunning(false);
        setResting(true);
        setDeltaMovement(Vec3.ZERO);
        velocityAfterImpact = null;
        entityData.set(DATA_DETONATION_DELAY_TICKS, profile.durationTicks());
        profile.startSound().play(level(), position());
    }

    private void tickDetonationDelay(GrenadeDefinition definition) {
        int remainingTicks = entityData.get(DATA_DETONATION_DELAY_TICKS) - 1;
        entityData.set(DATA_DETONATION_DELAY_TICKS, remainingTicks);
        if (remainingTicks <= 0 && level() instanceof ServerLevel serverLevel) {
            completeDetonation(definition, serverLevel);
        }
    }

    private void beginImplosion(GrenadeImplosionProfile profile) {
        setFuseRunning(false);
        setResting(true);
        setDeltaMovement(Vec3.ZERO);
        velocityAfterImpact = null;
        entityData.set(DATA_IMPLOSION_TICKS, profile.durationTicks());
        profile.startSound().play(level(), position());
        if (profile.particles() != null && level() instanceof ServerLevel serverLevel) {
            profile.particles().spawn(serverLevel, getBoundingBox().getCenter());
        }
    }

    private void tickImplosion(GrenadeDefinition definition) {
        GrenadeImplosionProfile profile = definition.implosionProfile();
        if (profile == null || !(level() instanceof ServerLevel serverLevel)) {
            entityData.set(DATA_IMPLOSION_TICKS, -1);
            detonate();
            return;
        }

        pullNearbyLivingEntities(profile);
        if (profile.particles() != null) {
            profile.particles().spawn(serverLevel, getBoundingBox().getCenter());
        }

        int remainingTicks = entityData.get(DATA_IMPLOSION_TICKS) - 1;
        entityData.set(DATA_IMPLOSION_TICKS, remainingTicks);
        if (remainingTicks <= 0) {
            completeDetonation(definition, serverLevel);
        }
    }

    private void pullNearbyLivingEntities(GrenadeImplosionProfile profile) {
        Vec3 center = getBoundingBox().getCenter();
        double radiusSquared = profile.radius() * profile.radius();
        AABB bounds = new AABB(center, center).inflate(profile.radius());

        for (LivingEntity target : level().getEntitiesOfClass(LivingEntity.class, bounds)) {
            if (!target.isAlive() || target.isSpectator()) {
                continue;
            }

            Vec3 targetCenter = target.position().add(0.0, target.getBbHeight() * 0.5, 0.0);
            Vec3 towardCenter = center.subtract(targetCenter);
            double distanceSquared = towardCenter.lengthSqr();
            if (distanceSquared <= 1.0E-6 || distanceSquared > radiusSquared) {
                continue;
            }

            double distance = Math.sqrt(distanceSquared);
            double falloff = 1.0 - distance / profile.radius();
            Vec3 pull = towardCenter.scale(profile.pullStrength() * falloff / distance);
            target.setDeltaMovement(target.getDeltaMovement().add(pull));
            target.hurtMarked = true;
        }
    }

    private void completeDetonation(GrenadeDefinition definition, ServerLevel serverLevel) {
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
            if (!isShockFieldActive()) {
                discard();
            }
        }
    }

    public void beginShockField(GrenadeShockFieldProfile profile) {
        if (!(level() instanceof ServerLevel) || isShockFieldActive()) {
            return;
        }
        setFuseRunning(false);
        detonated = true;
        setResting(true);
        setNoGravity(true);
        setDeltaMovement(Vec3.ZERO);
        velocityAfterImpact = null;
        entityData.set(DATA_SHOCK_FIELD_END_TICK, level().getGameTime() + profile.durationTicks());
        profile.startSound().play(level(), position());
        tickShockField(profile);
    }

    private void tickShockField(@Nullable GrenadeShockFieldProfile profile) {
        int remainingTicks = getShockFieldRemainingTicks();
        if (profile == null || remainingTicks <= 0 || !(level() instanceof ServerLevel serverLevel)) {
            discard();
            return;
        }
        AABB bounds = new AABB(position(), position()).inflate(profile.radius());
        double radiusSquared = profile.radius() * profile.radius();
        GrenadeEffects.disarmNearbyExplosives(serverLevel, position(), profile.radius());
        for (LivingEntity target : serverLevel.getEntitiesOfClass(LivingEntity.class, bounds)) {
            if (target.distanceToSqr(position()) <= radiusSquared) {
                // Refresh while inside; leaving or field expiry stops refreshing the short after-effect.
                LightningShockEffect.apply(target, profile.shockLingerTicks());
            }
        }
        int elapsedTicks = profile.durationTicks() - remainingTicks;
        if (elapsedTicks % profile.arcIntervalTicks() == 0) {
            GrenadeIonArcProfile arcs = profile.arcs();
            GrenadeIonArcProfile burst = new GrenadeIonArcProfile(arcs.count(),
                    Math.min(remainingTicks, arcs.lifetimeTicks()),
                    arcs.minimumSegments(), arcs.maximumSegments(), arcs.segmentLength(), arcs.bend(),
                    arcs.exteriorColor(), arcs.coreRadius(), arcs.exteriorRadius());
            PacketDistributor.sendToPlayersNear(serverLevel, null, getX(), getY(), getZ(), 64.0,
                    new CSIonArcPacket(new Vector3f((float) getX(), (float) getY(), (float) getZ()),
                            serverLevel.getRandom().nextLong(), 1, burst));
        }
    }

    public void activateRemoteDetonation() {
        if (level().isClientSide() || detonated || isRemoteDetonationActivated()) {
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
    public void addAdditionalSaveData(net.minecraft.world.level.storage.ValueOutput tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt(FUSE_TAG, getFuseTicks());
        tag.putBoolean(FUSE_RUNNING_TAG, isFuseRunning());
        tag.putBoolean(RESTING_TAG, isResting());
        tag.putBoolean(PRESSURE_ACTIVATED_TAG, isPressureActivated());
        Direction stuckFace = getStuckFace();
        if (stuckFace != null) {
            tag.putInt(STUCK_FACE_TAG, stuckFace.get3DDataValue());
        }
        tag.putBoolean(DETONATED_TAG, detonated);
        tag.putInt(BEEP_FLASH_TAG, entityData.get(DATA_BEEP_FLASH_TICKS));
        tag.putBoolean(REMOTE_REGISTERED_TAG, remoteRegistered);
        tag.putInt(REMOTE_DETONATION_TICKS_TAG, entityData.get(DATA_REMOTE_DETONATION_TICKS));
        tag.putBoolean(ONE_SHOT_FUSE_SOUND_PLAYED_TAG, oneShotFuseSoundPlayed);
        tag.putInt(SOUND_LOOP_TICKS_TAG, soundLoopTicks);
        tag.putInt(IMPLOSION_TICKS_TAG, entityData.get(DATA_IMPLOSION_TICKS));
        tag.putInt(DETONATION_DELAY_TICKS_TAG, entityData.get(DATA_DETONATION_DELAY_TICKS));
        tag.putInt(PROXIMITY_AGE_TAG, proximityAgeTicks);
        tag.putLong(SHOCK_FIELD_END_TAG, entityData.get(DATA_SHOCK_FIELD_END_TICK));
    }

    @Override
    public void readAdditionalSaveData(net.minecraft.world.level.storage.ValueInput tag) {
        super.readAdditionalSaveData(tag);
        if (tag.read(FUSE_TAG, com.mojang.serialization.Codec.INT).isPresent()) {
            setFuseTicks(tag.getIntOr(FUSE_TAG, 0));
        }
        if (tag.read(FUSE_RUNNING_TAG, com.mojang.serialization.Codec.BOOL).isPresent()) {
            setFuseRunning(tag.getBooleanOr(FUSE_RUNNING_TAG, false));
        }
        setResting(tag.getBooleanOr(RESTING_TAG, false));
        entityData.set(DATA_PRESSURE_ACTIVATED, tag.getBooleanOr(PRESSURE_ACTIVATED_TAG, false));
        setStuckFace(tag.read(STUCK_FACE_TAG, com.mojang.serialization.Codec.INT).isPresent()
                ? Direction.from3DDataValue(tag.getIntOr(STUCK_FACE_TAG, 0))
                : null);
        detonated = tag.getBooleanOr(DETONATED_TAG, false);
        setBeepFlashTicks(tag.getIntOr(BEEP_FLASH_TAG, 0));
        remoteRegistered = tag.getBooleanOr(REMOTE_REGISTERED_TAG, false);
        entityData.set(
                DATA_REMOTE_DETONATION_TICKS,
                tag.read(REMOTE_DETONATION_TICKS_TAG, com.mojang.serialization.Codec.INT).isPresent() ? tag.getIntOr(REMOTE_DETONATION_TICKS_TAG, 0) : -1
        );
        oneShotFuseSoundPlayed = tag.getBooleanOr(ONE_SHOT_FUSE_SOUND_PLAYED_TAG, false);
        soundLoopTicks = Math.max(0, tag.getIntOr(SOUND_LOOP_TICKS_TAG, 0));
        entityData.set(
                DATA_IMPLOSION_TICKS,
                tag.read(IMPLOSION_TICKS_TAG, com.mojang.serialization.Codec.INT).isPresent() ? tag.getIntOr(IMPLOSION_TICKS_TAG, 0) : -1
        );
        entityData.set(DATA_DETONATION_DELAY_TICKS, tag.getIntOr(DETONATION_DELAY_TICKS_TAG, -1));
        proximityAgeTicks = Math.max(0, tag.getIntOr(PROXIMITY_AGE_TAG, 0));
        entityData.set(DATA_SHOCK_FIELD_END_TICK, tag.getLongOr(SHOCK_FIELD_END_TAG, -1L));
        if (isShockFieldActive()) {
            setNoGravity(true);
        }
    }
}
