package net.uhhitscam.knightfall.entity.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.uhhitscam.knightfall.component.ModDataComponentTypes;
import net.uhhitscam.knightfall.entity.ModEntities;
import net.uhhitscam.knightfall.event.MeleeWeaponServerEvents;
import net.uhhitscam.knightfall.item.custom.melee.MeleeAttack;
import net.uhhitscam.knightfall.item.custom.melee.MeleeWeaponItem;
import net.uhhitscam.knightfall.util.MeleeTargeting;

public class MeleeProjectileEntity extends AbstractArrow implements ItemSupplier {
    private static final EntityDataAccessor<ItemStack> ITEM = SynchedEntityData.defineId(MeleeProjectileEntity.class, EntityDataSerializers.ITEM_STACK);
    private boolean hook;
    private boolean hitTarget;
    private boolean recoverable;
    private LivingEntity attached;
    private float damage = 5;
    private double tetherRange = 12;
    private int age;

    public MeleeProjectileEntity(EntityType<? extends MeleeProjectileEntity> type, Level level) { super(type, level); }

    private MeleeProjectileEntity(ServerPlayer owner, ItemStack stack, MeleeAttack attack, boolean hook, boolean recoverable) {
        super(ModEntities.MELEE_PROJECTILE.get(), owner, owner.level(), stack.copyWithCount(1), stack.copyWithCount(1));
        ItemStack copy = stack.copyWithCount(1);
        copy.remove(ModDataComponentTypes.MELEE_ACTION.get());
        entityData.set(ITEM, copy);
        setPickupItemStack(copy);
        this.hook = hook;
        this.recoverable = recoverable;
        this.pickup = recoverable ? Pickup.ALLOWED : Pickup.DISALLOWED;
        MeleeWeaponItem weapon = (MeleeWeaponItem) stack.getItem();
        damage = (float) weapon.getForm(stack).damage() * attack.damageMultiplier();
        tetherRange = weapon.getDefinition().tuning().tetherRange();
        setNoGravity(hook);
        setPos(owner.getEyePosition());
        shootFromRotation(owner, owner.getXRot(), owner.getYRot(), 0, (float) attack.speed(), 0);
    }

    public static MeleeProjectileEntity launch(ServerPlayer owner, ItemStack stack, MeleeAttack attack, boolean hook, boolean recoverable) {
        MeleeProjectileEntity projectile = new MeleeProjectileEntity(owner, stack, attack, hook, recoverable);
        return owner.level().addFreshEntity(projectile) ? projectile : null;
    }

    @Override protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ITEM, ItemStack.EMPTY);
    }

    @Override public ItemStack getItem() { return entityData.get(ITEM); }
    @Override protected ItemStack getDefaultPickupItem() { return new ItemStack(Items.IRON_NUGGET); }
    public LivingEntity attachedTarget() { return attached != null && attached.isAlive() ? attached : null; }

    @Override protected boolean canHitEntity(Entity entity) {
        return !hitTarget && super.canHitEntity(entity) && entity instanceof LivingEntity target
                && getOwner() instanceof LivingEntity owner && MeleeTargeting.canHit(owner, target);
    }

    @Override protected void onHitEntity(EntityHitResult result) {
        if (level().isClientSide() || !(getOwner() instanceof ServerPlayer owner)
                || !(result.getEntity() instanceof LivingEntity target) || !MeleeTargeting.canHit(owner, target)) return;
        hitTarget = true;
        if (hook) {
            attached = target;
            setDeltaMovement(Vec3.ZERO);
            setNoPhysics(true);
            return;
        }
        var source = damageSources().thrown(this, owner);
        float amount = EnchantmentHelper.modifyDamage(owner.level(), getItem(), target, source, damage);
        float before = target.getHealth() + target.getAbsorptionAmount();
        if (net.uhhitscam.knightfall.util.WeaponDamage.hurt(target, source, amount) && target.getHealth() + target.getAbsorptionAmount() < before) {
            MeleeWeaponServerEvents.applyProperties(owner, target, getItem());
            MeleeWeaponServerEvents.projectileHit(owner, getItem());
            EnchantmentHelper.doPostAttackEffectsWithItemSource(owner.level(), target, source, getItem());
        }
        if (!recoverable || getItem().isEmpty()) discard();
        else {
            // Remain in the world and fall to the ground for pickup
            setPickupItemStack(getItem().copy());
            setDeltaMovement(getDeltaMovement().scale(-0.1));
        }
    }

    @Override protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!level().isClientSide() && (hook || !recoverable)) discard();
    }

    @Override public void tick() {
        if (!level().isClientSide() && hook) {
            if (!(getOwner() instanceof ServerPlayer owner) || !owner.isAlive() || owner.level() != level()
                    || owner.distanceTo(this) > tetherRange || !owner.hasLineOfSight(this)
                    || attached != null && (!MeleeTargeting.canHit(owner, attached) || attached.level() != level())) {
                discard();
                return;
            }
            if (attached != null) {
                setPos(attached.getBoundingBox().getCenter());
                setDeltaMovement(Vec3.ZERO);
            }
            if (tickCount % 4 == 0) {
                Vec3 start = owner.getEyePosition();
                Vec3 span = position().subtract(start);
                int count = Math.max(1, (int) (span.length() * 2));
                for (int i = 1; i <= count; i++) {
                    Vec3 point = start.add(span.scale((double) i / count));
                    owner.level().sendParticles(ParticleTypes.ELECTRIC_SPARK, point.x, point.y, point.z, 1, 0, 0, 0, 0);
                }
            }
        }
        super.tick();
        if (!level().isClientSide() && ++age > (recoverable ? 6000 : hook ? 1200 : 100)) discard();
    }

    @Override public boolean shouldBeSaved() { return !hook && super.shouldBeSaved(); }
    @Override protected void tickDespawn() {}

    @Override public void addAdditionalSaveData(net.minecraft.world.level.storage.ValueOutput tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("MeleeRecoverable", recoverable);
        tag.putBoolean("MeleeHit", hitTarget);
        tag.putFloat("MeleeDamage", damage);
        tag.putInt("MeleeAge", age);
        if (!getItem().isEmpty()) tag.store("MeleeItem", net.minecraft.world.item.ItemStack.CODEC, getItem());
    }

    @Override public void readAdditionalSaveData(net.minecraft.world.level.storage.ValueInput tag) {
        super.readAdditionalSaveData(tag);
        recoverable = tag.getBooleanOr("MeleeRecoverable", false);
        hitTarget = tag.getBooleanOr("MeleeHit", false);
        damage = tag.getFloatOr("MeleeDamage", 0.0F);
        age = tag.getIntOr("MeleeAge", 0);
        entityData.set(ITEM, tag.read("MeleeItem", net.minecraft.world.item.ItemStack.CODEC).orElse(net.minecraft.world.item.ItemStack.EMPTY));
        pickup = recoverable ? Pickup.ALLOWED : Pickup.DISALLOWED;
    }
}
