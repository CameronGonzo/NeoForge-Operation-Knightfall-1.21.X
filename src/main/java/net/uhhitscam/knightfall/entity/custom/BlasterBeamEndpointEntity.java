package net.uhhitscam.knightfall.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.uhhitscam.knightfall.component.AmmoTypeData;
import net.uhhitscam.knightfall.component.ModDataComponentTypes;
import net.uhhitscam.knightfall.item.custom.AmmoType;
import net.uhhitscam.knightfall.item.custom.FiringMode;
import net.uhhitscam.knightfall.item.custom.ProjectileItem;
import net.uhhitscam.knightfall.sound.ModSounds;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class BlasterBeamEndpointEntity extends Entity {

    private static final EntityDataAccessor<Boolean> MAIN_HAND =
            SynchedEntityData.defineId(BlasterBeamEndpointEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<java.util.Optional<java.util.UUID>> OWNER_UUID =
            SynchedEntityData.defineId(BlasterBeamEndpointEntity.class, EntityDataSerializers.OPTIONAL_UUID);

    private float damagePerPulse = 2.0F;
    private double range = 48.0;
    private int damageTicker = 0;
    private float ammoDrainAccumulator = 0.0F;

    public BlasterBeamEndpointEntity(EntityType<?> type, Level level) {
        super(type, level);
        this.noPhysics = true;
        this.setNoGravity(true);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(OWNER_UUID, java.util.Optional.empty());
        builder.define(MAIN_HAND, true);
    }

    public java.util.Optional<java.util.UUID> getOwnerUUID() {
        return this.entityData.get(OWNER_UUID);
    }

    public void setOwner(LivingEntity owner) {
        this.entityData.set(OWNER_UUID, java.util.Optional.of(owner.getUUID()));
    }

    public boolean isOwnedBy(Player player) {
        return this.entityData.get(OWNER_UUID)
                .map(uuid -> uuid.equals(player.getUUID()))
                .orElse(false);
    }

    @Nullable
    public LivingEntity getOwnerLiving() {
        Optional<UUID> opt = this.entityData.get(OWNER_UUID);
        if (opt.isEmpty()) return null;

        Player p = this.level().getPlayerByUUID(opt.get());
        if (p != null) return p;

        return null;
    }

    public void setMainHand(boolean mainHand) {
        this.entityData.set(MAIN_HAND, mainHand);
    }

    public boolean isMainHand() {
        return this.entityData.get(MAIN_HAND);
    }

    public void configure(double range, float damagePerPulse) {
        this.range = range;
        this.damagePerPulse = damagePerPulse;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) return;

        LivingEntity owner = getOwnerLiving();
        if (!(owner instanceof Player player) || !owner.isAlive()) {
            this.discard();
            return;
        }

        ItemStack held = this.isMainHand() ? player.getMainHandItem() : player.getOffhandItem();
        if (!(held.getItem() instanceof ProjectileItem weapon) ||
                weapon.getFiringMode(held) != FiringMode.BEAM) {
            this.discard();
            return;
        }

        int ammo = weapon.getAmmo(held);
        if (ammo <= 0) {
            held.set(ModDataComponentTypes.AMMO_TYPE.get(), new AmmoTypeData(AmmoType.NONE.name()));
            this.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                    ModSounds.FOLEY_NO_AMMO.get(), SoundSource.NEUTRAL, 0.5F, 1.0F);
            this.discard();
            return;
        }

        ammoDrainAccumulator += (4.0F / 20.0F);
        int toConsume = (int) ammoDrainAccumulator;
        if (toConsume > 0) {
            ammoDrainAccumulator -= toConsume;

            int newAmmo = Math.max(0, ammo - toConsume);
            weapon.setAmmo(held, newAmmo);

            if (newAmmo <= 0) {
                held.set(ModDataComponentTypes.AMMO_TYPE.get(), new AmmoTypeData(AmmoType.NONE.name()));
                this.discard();
                return;
            }
        }

        BeamHit hit = computeBeamHit((ServerLevel) this.level(), owner, range);

        Vec3 end = hit.endPos();
        this.setPos(end.x, end.y, end.z);

        if (++damageTicker >= 5) {
            damageTicker = 0;

            LivingEntity target = hit.hitEntity();
            if (target != null && target.isAlive()) {
                target.invulnerableTime = 0;

                DamageSource src = (owner instanceof Player p)
                        ? p.damageSources().playerAttack(p)
                        : owner.damageSources().mobAttack(owner);

                if (target.hurt(src, damagePerPulse)) {
                    target.invulnerableTime = 0;
                }
            }
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) { }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) { }

    @Override
    public boolean isPickable() { return false; }

    @Override
    public boolean canBeCollidedWith() { return false; }

    @Override
    public AABB getBoundingBoxForCulling() {
        LivingEntity owner = getOwnerLiving();
        if (owner != null) {
            Vec3 start = owner.getEyePosition();
            Vec3 end = this.position();
            return new AABB(start, end).inflate(1.0);
        }
        return super.getBoundingBoxForCulling().inflate(1.0);
    }

    public record BeamHit(Vec3 endPos, @Nullable LivingEntity hitEntity) {}

    private static BeamHit computeBeamHit(ServerLevel level, LivingEntity owner, double range) {
        Vec3 look = owner.getLookAngle();
        Vec3 start = owner.getEyePosition().add(look.scale(0.2));
        Vec3 maxEnd = start.add(look.scale(range));

        BlockHitResult blockHit = level.clip(new ClipContext(
                start, maxEnd,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                owner
        ));

        Vec3 end = maxEnd;
        double maxDist = range;
        if (blockHit.getType() != HitResult.Type.MISS) {
            end = blockHit.getLocation();
            maxDist = end.distanceTo(start);
        }

        Vec3 cappedEnd = start.add(look.scale(maxDist));
        AABB scanBox = new AABB(start, cappedEnd).inflate(0.5);

        LivingEntity bestEntity = null;
        Vec3 bestHitPos = null;
        double bestDist = maxDist;

        for (Entity e : level.getEntities(owner, scanBox, ent ->
                ent instanceof LivingEntity &&
                        ent.isPickable() &&
                        ent != owner
        )) {
            AABB bb = e.getBoundingBox().inflate(0.2);
            if (bb.contains(start)) {
                bestEntity = (LivingEntity) e;
                bestHitPos = start;
                bestDist = 0.0;
                break;
            }

            var clipped = bb.clip(start, cappedEnd);
            if (clipped.isPresent()) {
                double d = start.distanceTo(clipped.get());
                if (d < bestDist) {
                    bestDist = d;
                    bestEntity = (LivingEntity) e;
                    bestHitPos = clipped.get();
                }
            }
        }

        if (bestEntity != null) {
            return new BeamHit(bestHitPos != null ? bestHitPos : cappedEnd, bestEntity);
        }

        return new BeamHit(end, null);
    }
}
