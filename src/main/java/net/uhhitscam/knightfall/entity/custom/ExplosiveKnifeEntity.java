package net.uhhitscam.knightfall.entity.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.uhhitscam.knightfall.item.custom.AttachedExplosiveMeleeEffect;
import net.uhhitscam.knightfall.item.custom.AttachedExplosiveSpec;
import net.uhhitscam.knightfall.item.custom.MeleeWeaponItem;
import net.uhhitscam.knightfall.util.CustomExplosion;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class ExplosiveKnifeEntity extends Entity {
    private static final String ITEM_TAG = "Item";
    private static final String FUSE_TAG = "Fuse";
    private static final String TARGET_UUID_TAG = "TargetUUID";
    private static final String OWNER_UUID_TAG = "OwnerUUID";
    private static final String LOCAL_X_TAG = "LocalX";
    private static final String LOCAL_Y_TAG = "LocalY";
    private static final String LOCAL_Z_TAG = "LocalZ";
    private static final int BEEP_FLASH_TICKS = 3;
    private static final double ATTACHMENT_INSET = -0.1;

    private static final EntityDataAccessor<ItemStack> DATA_ITEM =
            SynchedEntityData.defineId(ExplosiveKnifeEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<Integer> DATA_FUSE =
            SynchedEntityData.defineId(ExplosiveKnifeEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_TARGET_ID =
            SynchedEntityData.defineId(ExplosiveKnifeEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> DATA_LOCAL_X =
            SynchedEntityData.defineId(ExplosiveKnifeEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_LOCAL_Y =
            SynchedEntityData.defineId(ExplosiveKnifeEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_LOCAL_Z =
            SynchedEntityData.defineId(ExplosiveKnifeEntity.class, EntityDataSerializers.FLOAT);

    @Nullable
    private UUID targetUuid;
    @Nullable
    private UUID ownerUuid;
    private boolean detonated;

    public ExplosiveKnifeEntity(EntityType<? extends ExplosiveKnifeEntity> type, Level level) {
        super(type, level);
        noPhysics = true;
        setNoGravity(true);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_ITEM, ItemStack.EMPTY);
        builder.define(DATA_FUSE, 0);
        builder.define(DATA_TARGET_ID, -1);
        builder.define(DATA_LOCAL_X, 0.0F);
        builder.define(DATA_LOCAL_Y, 0.0F);
        builder.define(DATA_LOCAL_Z, 0.0F);
    }

    public void attach(
            LivingEntity target,
            LivingEntity owner,
            ItemStack weaponStack,
            AttachedExplosiveSpec spec
    ) {
        setWeaponStack(weaponStack);
        setFuseTicks(spec.fuseTicks());
        targetUuid = target.getUUID();
        ownerUuid = owner.getUUID();
        entityData.set(DATA_TARGET_ID, target.getId());

        Vec3 outward = owner.position().subtract(target.position()).multiply(1.0, 0.0, 1.0);
        if (outward.lengthSqr() < 1.0E-6) {
            outward = target.getLookAngle().multiply(-1.0, 0.0, -1.0);
        }
        outward = outward.normalize();

        double surfaceDistance = Math.max(0.0, target.getBbWidth() * 0.5 - ATTACHMENT_INSET);
        Vec3 worldOffset = new Vec3(
                outward.x * surfaceDistance,
                target.getBbHeight() * 0.55,
                outward.z * surfaceDistance
        );
        float targetYaw = attachmentYaw(target);
        double targetYawRadians = Math.toRadians(targetYaw);
        double cos = Math.cos(targetYawRadians);
        double sin = Math.sin(targetYawRadians);

        entityData.set(DATA_LOCAL_X, (float) (worldOffset.x * cos + worldOffset.z * sin));
        entityData.set(DATA_LOCAL_Y, (float) worldOffset.y);
        entityData.set(DATA_LOCAL_Z, (float) (-worldOffset.x * sin + worldOffset.z * cos));

        updateAttachment(target);
        yRotO = getYRot();
    }

    public ItemStack getWeaponStack() {
        return entityData.get(DATA_ITEM);
    }

    private void setWeaponStack(ItemStack stack) {
        entityData.set(DATA_ITEM, stack.copyWithCount(1));
    }

    public int getFuseTicks() {
        return entityData.get(DATA_FUSE);
    }

    private void setFuseTicks(int fuseTicks) {
        entityData.set(DATA_FUSE, fuseTicks);
    }

    public boolean isBeepLightOn() {
        AttachedExplosiveSpec spec = getSpec();
        if (spec == null) {
            return false;
        }
        for (int elapsedTicks = 0; elapsedTicks < BEEP_FLASH_TICKS; elapsedTicks++) {
            if (spec.shouldPlayBeep(getFuseTicks() + elapsedTicks)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void tick() {
        super.tick();

        Entity target = resolveTarget();
        if (target != null && !target.isRemoved()) {
            updateAttachment(target);
        }

        if (level().isClientSide) {
            return;
        }

        AttachedExplosiveSpec spec = getSpec();
        if (spec == null) {
            discard();
            return;
        }

        int remainingFuseTicks = getFuseTicks() - 1;
        setFuseTicks(remainingFuseTicks);
        if (remainingFuseTicks <= 0) {
            detonate(spec);
            return;
        }
        if (spec.shouldPlayBeep(remainingFuseTicks)) {
            spec.beepSound(remainingFuseTicks).play(level(), position());
        }
    }

    private void updateAttachment(Entity target) {
        double targetYawRadians = Math.toRadians(attachmentYaw(target));
        double cos = Math.cos(targetYawRadians);
        double sin = Math.sin(targetYawRadians);
        double localX = entityData.get(DATA_LOCAL_X);
        double localZ = entityData.get(DATA_LOCAL_Z);
        double worldX = localX * cos - localZ * sin;
        double worldZ = localX * sin + localZ * cos;

        setPos(
                target.getX() + worldX,
                target.getY() + entityData.get(DATA_LOCAL_Y),
                target.getZ() + worldZ
        );
        if (worldX * worldX + worldZ * worldZ > 1.0E-6) {
            setYRot((float) Math.toDegrees(Math.atan2(-worldX, -worldZ)));
        }
    }

    private static float attachmentYaw(Entity target) {
        return target instanceof LivingEntity livingEntity ? livingEntity.yBodyRot : target.getYRot();
    }

    @Nullable
    private Entity resolveTarget() {
        Entity target = level().getEntity(entityData.get(DATA_TARGET_ID));
        if (target != null || targetUuid == null || !(level() instanceof ServerLevel serverLevel)) {
            return target;
        }

        target = serverLevel.getEntity(targetUuid);
        if (target != null) {
            entityData.set(DATA_TARGET_ID, target.getId());
        }
        return target;
    }

    @Nullable
    private LivingEntity resolveOwner() {
        if (ownerUuid == null || !(level() instanceof ServerLevel serverLevel)) {
            return null;
        }
        Entity owner = serverLevel.getEntity(ownerUuid);
        return owner instanceof LivingEntity livingEntity ? livingEntity : null;
    }

    @Nullable
    private AttachedExplosiveSpec getSpec() {
        if (!(getWeaponStack().getItem() instanceof MeleeWeaponItem meleeWeapon)) {
            return null;
        }
        return meleeWeapon.getDefinition().hitEffect() instanceof AttachedExplosiveMeleeEffect effect
                ? effect.spec()
                : null;
    }

    private void detonate(AttachedExplosiveSpec spec) {
        if (detonated || !(level() instanceof ServerLevel serverLevel)) {
            return;
        }
        detonated = true;

        CustomExplosion.create(
                this,
                resolveOwner(),
                position(),
                spec.entityRadius(),
                spec.damage(),
                spec.knockback(),
                spec.blockBreakRadius(),
                spec.blockInteraction(),
                spec.causesFire()
        );
        spec.detonationSound().play(serverLevel, position());
        serverLevel.sendParticles(ParticleTypes.EXPLOSION_EMITTER, getX(), getY(), getZ(), 1, 0.0, 0.0, 0.0, 0.0);
        serverLevel.sendParticles(ParticleTypes.SMOKE, getX(), getY(), getZ(), 24, 0.8, 0.5, 0.8, 0.03);
        discard();
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        if (!getWeaponStack().isEmpty()) {
            tag.put(ITEM_TAG, getWeaponStack().save(registryAccess()));
        }
        tag.putInt(FUSE_TAG, getFuseTicks());
        if (targetUuid != null) {
            tag.putUUID(TARGET_UUID_TAG, targetUuid);
        }
        if (ownerUuid != null) {
            tag.putUUID(OWNER_UUID_TAG, ownerUuid);
        }
        tag.putFloat(LOCAL_X_TAG, entityData.get(DATA_LOCAL_X));
        tag.putFloat(LOCAL_Y_TAG, entityData.get(DATA_LOCAL_Y));
        tag.putFloat(LOCAL_Z_TAG, entityData.get(DATA_LOCAL_Z));
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains(ITEM_TAG)) {
            setWeaponStack(ItemStack.parseOptional(registryAccess(), tag.getCompound(ITEM_TAG)));
        }
        setFuseTicks(tag.getInt(FUSE_TAG));
        targetUuid = tag.hasUUID(TARGET_UUID_TAG) ? tag.getUUID(TARGET_UUID_TAG) : null;
        ownerUuid = tag.hasUUID(OWNER_UUID_TAG) ? tag.getUUID(OWNER_UUID_TAG) : null;
        entityData.set(DATA_LOCAL_X, tag.getFloat(LOCAL_X_TAG));
        entityData.set(DATA_LOCAL_Y, tag.getFloat(LOCAL_Y_TAG));
        entityData.set(DATA_LOCAL_Z, tag.getFloat(LOCAL_Z_TAG));
    }

    @Override
    public boolean isPickable() {
        return false;
    }
}
