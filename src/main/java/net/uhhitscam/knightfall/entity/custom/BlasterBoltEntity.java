package net.uhhitscam.knightfall.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.item.custom.AmmoType;
import net.uhhitscam.knightfall.item.custom.WeaponClassification;
import net.uhhitscam.knightfall.network.CSConcussionBlurPacket;
import net.uhhitscam.knightfall.particle.ModParticles;
import net.uhhitscam.knightfall.sound.ModSounds;
import net.uhhitscam.knightfall.util.BlasterImpactSoundUtil;
import net.uhhitscam.knightfall.util.CustomExplosion;
import net.uhhitscam.knightfall.util.DisintegrationParticles;
import net.uhhitscam.knightfall.util.FaceAlignedParticleUtil;
import org.joml.Vector3f;

import java.util.function.Supplier;

public class BlasterBoltEntity extends Snowball {
    private static final EntityDataAccessor<Integer> BOLT_TYPE =
            SynchedEntityData.defineId(BlasterBoltEntity.class, EntityDataSerializers.INT);

    private float boltSpeed = 2.0F;
    private int blasterDamage;
    private WeaponClassification classification = WeaponClassification.PISTOL;
    private boolean explosiveShot;
    private boolean concussiveShot;

    public BlasterBoltEntity(EntityType<? extends BlasterBoltEntity> entityType, Level level) {
        super(entityType, level);
    }

    public BlasterBoltEntity(
            EntityType<? extends BlasterBoltEntity> entityType,
            Level level,
            LivingEntity shooter,
            BoltType boltType,
            float boltSpeed,
            int blasterDamage,
            WeaponClassification classification,
            boolean explosiveShot,
            boolean concussiveShot
    ) {
        super(entityType, level);
        this.setBoltType(boltType);
        this.boltSpeed = boltSpeed;
        this.blasterDamage = blasterDamage;
        this.classification = classification;
        this.explosiveShot = explosiveShot;
        this.concussiveShot = concussiveShot;

        Vec3 direction = shooter.getLookAngle().normalize().scale(boltSpeed);
        this.setDeltaMovement(direction);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(BOLT_TYPE, BoltType.TIBANNA.ordinal());
    }

    public BoltType getBoltType() {
        return BoltType.fromId(this.entityData.get(BOLT_TYPE));
    }

    private void setBoltType(BoltType boltType) {
        this.entityData.set(BOLT_TYPE, boltType.ordinal());
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);

        Entity entity = result.getEntity();
        int damage = calculateDamage(entity);

        if (entity.hurt(this.damageSources().thrown(this, this.getOwner()), damage)
                && entity instanceof LivingEntity livingEntity) {
            livingEntity.invulnerableTime = 0;

            if (shouldSpawnDisintegrationParticles(livingEntity)) {
                playEntityImpactSound(entity);
                DisintegrationParticles.spawn(this.level(), livingEntity);
            }
        }
    }

    private int calculateDamage(Entity target) {
        if (getBoltType() == BoltType.SKEVON) {
            return 1;
        }

        int damage = blasterDamage + getBoltType().damageBonus();
        if (getBoltType() == BoltType.IONIZED_TIBANNA) {
            damage += getIonizedDamageBonus(target);
        }
        return damage;
    }

    private int getIonizedDamageBonus(Entity target) {
        // Add the droid entity or tag check here once droid mobs exist.
        return 0;
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);

        if (this.level().isClientSide) {
            return;
        }

        if (result instanceof BlockHitResult blockHitResult) {
            BlasterImpactSoundUtil.playBlockImpactSound(this.level(), blockHitResult);
            breakGlassIfNeeded(blockHitResult);
        }

        this.level().broadcastEntityEvent(this, (byte) 3);
        if (explosiveShot) {
            CustomExplosion.create(this, result.getLocation(), 1.5, 3.5F, 0.25);
        } else if (concussiveShot) {
            CustomExplosion.create(this, result.getLocation(), 1.5, 1.5F, 0.15);

            ServerLevel serverLevel = (ServerLevel) this.level();
            serverLevel.sendParticles(ModParticles.CONCUSSIVE_SHOT_EXPLOSION_PARTICLES.get(),
                    this.getX(), this.getY(), this.getZ(),
                    1, 0, 0, 0, 0);

            PacketDistributor.sendToPlayersNear(
                    serverLevel,
                    null,
                    result.getLocation().x, result.getLocation().y, result.getLocation().z,
                    5.0F,
                    new CSConcussionBlurPacket(
                            new Vector3f((float) result.getLocation().x, (float) result.getLocation().y, (float) result.getLocation().z),
                            5.0F,
                            40,
                            40,
                            30.0F
                    )
            );
        }

        int numParticles = switch (classification) {
            case PISTOL -> 5 + level().random.nextInt(5);
            case CARBINE -> 5 + level().random.nextInt(8);
            case RIFLE -> 8 + level().random.nextInt(10);
            case REPEATER -> 6 + level().random.nextInt(3);
            case SCATTER -> 10 + level().random.nextInt(5);
            case SNIPER -> 10 + level().random.nextInt(20);
            case SLUGTHROWER -> 10 + level().random.nextInt(15);
            case DISRUPTOR -> 15 + level().random.nextInt(20);
            default -> 7 + level().random.nextInt(7);
        };

        ServerLevel serverLevel = (ServerLevel) this.level();
        if (explosiveShot) {
            serverLevel.sendParticles(getBoltType().explosiveParticle().get(),
                    this.getX(), this.getY(), this.getZ(),
                    1, 0, 0, 0, 0);
        } else {
            serverLevel.sendParticles(ModParticles.SPARK_PARTICLES.get(),
                    this.getX(), this.getY(), this.getZ(),
                    numParticles, 0, 0, 0, 0.08);

            serverLevel.sendParticles(ParticleTypes.SMOKE,
                    this.getX(), this.getY(), this.getZ(),
                    3, 0, 0, 0, 0.02);
        }

        this.discard();
    }

    private boolean shouldSpawnDisintegrationParticles(LivingEntity livingEntity) {
        return classification == WeaponClassification.DISRUPTOR && livingEntity.isDeadOrDying();
    }

    private void playEntityImpactSound(Entity entity) {
        if (this.level().isClientSide || classification != WeaponClassification.DISRUPTOR) {
            return;
        }

        this.level().playSound(
                null,
                entity.getX(),
                entity.getY(),
                entity.getZ(),
                ModSounds.BLASTER_IMPACT_DISINTEGRATION.get(),
                SoundSource.NEUTRAL,
                0.7F,
                0.95F + this.level().random.nextFloat() * 0.1F
        );
    }

    private void breakGlassIfNeeded(BlockHitResult blockHitResult) {
        BlockPos blockPos = blockHitResult.getBlockPos();
        BlockState blockState = this.level().getBlockState(blockPos);

        if (!BlasterImpactSoundUtil.isBreakableGlass(blockState)) {
            FaceAlignedParticleUtil.spawnBlasterBurn(this.level(), blockHitResult);
            return;
        }

        this.level().destroyBlock(blockPos, false);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id != 3) {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public void tick() {
        super.tick();

        Vec3 velocity = this.getDeltaMovement();
        double speed = velocity.length();

        if (speed > 0.0001) {
            double yaw = Math.toDegrees(Math.atan2(velocity.x, velocity.z));
            double pitch = Math.toDegrees(Math.atan2(
                    velocity.y,
                    Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z)
            ));

            this.setYRot((float) yaw);
            this.setXRot((float) pitch);
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();

            this.setDeltaMovement(velocity.normalize().scale(this.boltSpeed));
        }

        if (!this.level().isClientSide && this.tickCount > 50) {
            this.discard();
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("BoltType", getBoltType().name());
        tag.putFloat("BoltSpeed", this.boltSpeed);
        tag.putInt("BlasterDamage", this.blasterDamage);
        tag.putString("WeaponClassification", this.classification.name());
        tag.putBoolean("ExplosiveShot", this.explosiveShot);
        tag.putBoolean("ConcussiveShot", this.concussiveShot);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setBoltType(BoltType.fromName(tag.getString("BoltType")));

        if (tag.contains("BoltSpeed")) {
            this.boltSpeed = tag.getFloat("BoltSpeed");
        }
        if (tag.contains("BlasterDamage")) {
            this.blasterDamage = tag.getInt("BlasterDamage");
        }
        if (tag.contains("WeaponClassification")) {
            try {
                this.classification = WeaponClassification.valueOf(tag.getString("WeaponClassification"));
            } catch (IllegalArgumentException ignored) {
                this.classification = WeaponClassification.PISTOL;
            }
        }

        this.explosiveShot = tag.getBoolean("ExplosiveShot");
        this.concussiveShot = tag.getBoolean("ConcussiveShot");
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

    public enum BoltType {
        TIBANNA(
                0,
                "textures/entity/bolt_core.png",
                "textures/entity/tibanna_bolt_exterior.png",
                ModParticles.EXPLOSIVE_SHOT_TIBANNA_PARTICLES
        ),
        IONIZED_TIBANNA(
                0,
                "textures/entity/bolt_core.png",
                "textures/entity/ionized_tibanna_bolt_exterior.png",
                ModParticles.EXPLOSIVE_SHOT_IONIZED_TIBANNA_PARTICLES
        ),
        SPIN_SEALED_TIBANNA(
                2,
                "textures/entity/bolt_core.png",
                "textures/entity/spin_sealed_tibanna_bolt_exterior.png",
                ModParticles.EXPLOSIVE_SHOT_SPIN_SEALED_TIBANNA_PARTICLES
        ),
        TIBANNAX(
                0,
                "textures/entity/tibannax_bolt_core.png",
                "textures/entity/tibannax_bolt_exterior.png",
                ModParticles.EXPLOSIVE_SHOT_TIBANNAX_PARTICLES
        ),
        SIG(
                1,
                "textures/entity/bolt_core.png",
                "textures/entity/sig_bolt_exterior.png",
                ModParticles.EXPLOSIVE_SHOT_SIG_PARTICLES
        ),
        MAGNETIZED_SIG(
                3,
                "textures/entity/bolt_core.png",
                "textures/entity/magnetized_sig_bolt_exterior.png",
                ModParticles.EXPLOSIVE_SHOT_MAGNETIZED_SIG_PARTICLES
        ),
        SKEVON(
                0,
                "textures/entity/bolt_core.png",
                "textures/entity/skevon_bolt_exterior.png",
                ModParticles.EXPLOSIVE_SHOT_SKEVON_PARTICLES
        );

        private static final BoltType[] VALUES = values();

        private final int damageBonus;
        private final ResourceLocation coreTexture;
        private final ResourceLocation glowTexture;
        private final Supplier<SimpleParticleType> explosiveParticle;

        BoltType(
                int damageBonus,
                String coreTexture,
                String glowTexture,
                Supplier<SimpleParticleType> explosiveParticle
        ) {
            this.damageBonus = damageBonus;
            this.coreTexture = ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, coreTexture);
            this.glowTexture = ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, glowTexture);
            this.explosiveParticle = explosiveParticle;
        }

        public int damageBonus() {
            return damageBonus;
        }

        public ResourceLocation coreTexture() {
            return coreTexture;
        }

        public ResourceLocation glowTexture() {
            return glowTexture;
        }

        public Supplier<SimpleParticleType> explosiveParticle() {
            return explosiveParticle;
        }

        public static BoltType fromAmmoType(AmmoType ammoType) {
            return switch (ammoType) {
                case IONIZED_TIBANNA -> IONIZED_TIBANNA;
                case SPIN_SEALED_TIBANNA -> SPIN_SEALED_TIBANNA;
                case TIBANNAX -> TIBANNAX;
                case SIG -> SIG;
                case MAGNETIZED_SIG -> MAGNETIZED_SIG;
                case SKEVON -> SKEVON;
                default -> TIBANNA;
            };
        }

        private static BoltType fromId(int id) {
            return id >= 0 && id < VALUES.length ? VALUES[id] : TIBANNA;
        }

        private static BoltType fromName(String name) {
            try {
                return valueOf(name);
            } catch (IllegalArgumentException ignored) {
                return TIBANNA;
            }
        }
    }
}
