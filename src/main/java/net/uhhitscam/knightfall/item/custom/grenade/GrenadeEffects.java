package net.uhhitscam.knightfall.item.custom.grenade;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import net.uhhitscam.knightfall.entity.custom.GrenadeEntity;
import net.uhhitscam.knightfall.network.CSBactaGasParticlesPacket;
import net.uhhitscam.knightfall.network.CSConcussionBlurPacket;
import net.uhhitscam.knightfall.network.CSFlashEffectPacket;
import net.uhhitscam.knightfall.network.PayloadRegister;
import net.uhhitscam.knightfall.util.ColorUtil;
import net.uhhitscam.knightfall.util.CustomExplosion;
import net.uhhitscam.knightfall.util.FaceAlignedParticleUtil;
import net.uhhitscam.knightfall.util.FlashEffectTracker;
import org.joml.Vector3f;

import java.util.Objects;

public final class GrenadeEffects {
    private GrenadeEffects() {
    }

    public static GrenadeEffect explosion(GrenadeExplosionSpec spec) {
        Objects.requireNonNull(spec, "Grenade explosion specification cannot be null.");

        return context -> detonateExplosion(context, spec);
    }

    public static GrenadeEffect concussiveExplosion(GrenadeExplosionSpec explosionSpec, GrenadeConcussionProfile concussionProfile) {
        Objects.requireNonNull(explosionSpec, "Grenade explosion specification cannot be null.");
        Objects.requireNonNull(concussionProfile, "Grenade concussion profile cannot be null.");

        return context -> {
            detonateExplosion(context, explosionSpec);

            PacketDistributor.sendToPlayersNear(
                    context.level(),
                    null,
                    context.position().x,
                    context.position().y,
                    context.position().z,
                    concussionProfile.effectRadiusBlocks(),
                    new CSConcussionBlurPacket(
                            new Vector3f(
                                    (float) context.position().x,
                                    (float) context.position().y,
                                    (float) context.position().z
                            ),
                            concussionProfile.effectRadiusBlocks(),
                            concussionProfile.holdTicks(),
                            concussionProfile.fadeOutTicks(),
                            concussionProfile.maxShaderRadius()
                    )
            );
        };
    }

    public static GrenadeEffect sonicImplosionExplosion(GrenadeExplosionSpec explosionSpec) {
        Objects.requireNonNull(explosionSpec, "Sonic Imploder explosion specification cannot be null.");

        return context -> {
            FaceAlignedParticleUtil.spawnSonicImploderRipple(context.level(), context.position());
            detonateExplosion(context, explosionSpec);
        };
    }

    public static GrenadeEffect firebombExplosion(GrenadeExplosionSpec explosionSpec, GrenadeFirebombProfile firebombProfile) {
        Objects.requireNonNull(explosionSpec, "Grenade explosion specification cannot be null.");
        Objects.requireNonNull(firebombProfile, "Grenade Firebomb profile cannot be null.");

        return context -> {
            detonateExplosion(context, explosionSpec);
            igniteNearbyEntities(context, firebombProfile);
            placeFirePatches(context, firebombProfile);
        };
    }

    public static GrenadeEffect cryobanExplosion(GrenadeExplosionSpec explosionSpec, GrenadeCryobanProfile cryobanProfile) {
        Objects.requireNonNull(explosionSpec, "Grenade explosion specification cannot be null.");
        Objects.requireNonNull(cryobanProfile, "Grenade Cryoban profile cannot be null.");

        return context -> {
            detonateExplosion(context, explosionSpec);
            extinguishNearbyFire(context, explosionSpec.entityRadius());
            freezeNearbyEntities(context, cryobanProfile);
        };
    }

    public static GrenadeEffect flash(GrenadeFlashProfile profile, GrenadeSound detonationSound) {
        Objects.requireNonNull(profile, "Flash Grenade profile cannot be null.");
        Objects.requireNonNull(detonationSound, "Flash Grenade detonation sound cannot be null.");

        return context -> {
            detonationSound.play(context.level(), context.position());
            context.level().sendParticles(
                    ColorParticleOption.create(ParticleTypes.FLASH, 0xFFFFFFFF),
                    context.position().x,
                    context.position().y,
                    context.position().z,
                    1,
                    0.0,
                    0.0,
                    0.0,
                    0.0
            );

            AABB bounds = new AABB(context.position(), context.position()).inflate(profile.viewingRadius());

            for (ServerPlayer player : context.level().getEntitiesOfClass(ServerPlayer.class, bounds)) {
                if (isAffectedByFlash(player, context.position(), profile)) {
                    PayloadRegister.sendToPlayer(player, new CSFlashEffectPacket(
                            profile.fullWhiteTicks(),
                            profile.fadeOutTicks()
                    ));
                }
            }

            for (Mob mob : context.level().getEntitiesOfClass(Mob.class, bounds)) {
                if (mob.isAlive() && isAffectedByFlash(mob, context.position(), profile)) {
                    FlashEffectTracker.apply(mob, profile.mobTargetSuppressionTicks());
                }
            }

            detonateNearbyGrenades(context, profile.radius());
        };
    }

    private static boolean isAffectedByFlash(
            LivingEntity target,
            Vec3 flashPosition,
            GrenadeFlashProfile profile
    ) {
        double distanceSquared = target.distanceToSqr(flashPosition);
        if (distanceSquared <= profile.radius() * profile.radius()) {
            return true;
        }
        if (distanceSquared > profile.viewingRadius() * profile.viewingRadius()) {
            return false;
        }

        Vec3 directionToFlash = flashPosition.subtract(target.getEyePosition());
        return directionToFlash.lengthSqr() < 1.0e-6
                || target.getLookAngle().normalize().dot(directionToFlash.normalize()) >= profile.minimumViewDot();
    }

    public static GrenadeEffect noEffect() {
        return context -> {
        };
    }

    public static GrenadeEffect bactaGas(GrenadeBactaProfile profile, GrenadeSound releaseSound) {
        Objects.requireNonNull(profile, "Bacta Bomb profile cannot be null.");
        Objects.requireNonNull(releaseSound, "Bacta Bomb release sound cannot be null.");

        return context -> {
            releaseSound.play(context.level(), context.position());
            spawnBactaGasBurst(context, profile);

            double radiusSquared = profile.radius() * profile.radius();
            AABB bounds = new AABB(context.position(), context.position()).inflate(profile.radius());
            for (LivingEntity target : context.level().getEntitiesOfClass(LivingEntity.class, bounds)) {
                if (!target.isAlive() || target.distanceToSqr(context.position()) > radiusSquared) {
                    continue;
                }

                if (profile.instantHealing() > 0.0F) {
                    target.heal(profile.instantHealing());
                }
                if (profile.regenerationDurationTicks() > 0) {
                    target.addEffect(new MobEffectInstance(
                            MobEffects.REGENERATION,
                            profile.regenerationDurationTicks(),
                            profile.regenerationAmplifier()
                    ));
                }
            }

            detonateNearbyGrenades(context, profile.radius());
        };
    }

    private static void spawnBactaGasBurst(
            GrenadeDetonationContext context,
            GrenadeBactaProfile profile
    ) {
        PacketDistributor.sendToPlayersNear(
                context.level(),
                null,
                context.position().x,
                context.position().y,
                context.position().z,
                48.0,
                new CSBactaGasParticlesPacket(
                        new Vector3f(
                                (float) context.position().x,
                                (float) context.position().y,
                                (float) context.position().z
                        ),
                        profile.gasParticleCount(),
                        (float) profile.minimumGasParticleSpeed(),
                        (float) profile.maximumGasParticleSpeed()
                )
        );
    }

    public static GrenadeEffect dioxisGas(GrenadeDioxisProfile profile, GrenadeSound releaseSound) {
        Objects.requireNonNull(profile, "Dioxis Grenade profile cannot be null.");
        Objects.requireNonNull(releaseSound, "Dioxis Grenade release sound cannot be null.");

        return context -> {
            releaseSound.play(context.level(), context.position());
            spawnGasBurst(context, profile.gasColor(), profile.gasParticleCount(), profile.radius());

            AreaEffectCloud cloud = new AreaEffectCloud(
                    context.level(),
                    context.position().x,
                    context.position().y,
                    context.position().z
            );
            if (context.owner() != null) {
                cloud.setOwner(context.owner());
            }
            cloud.setRadius(profile.radius());
            cloud.setDuration(profile.cloudDurationTicks());
            cloud.setWaitTime(0);
            cloud.setRadiusOnUse(0.0F);
            cloud.setRadiusPerTick(0.0F);
            cloud.setDurationOnUse(0);
            cloud.setCustomParticle(gasParticle(profile.gasColor()));
            cloud.addEffect(new MobEffectInstance(
                    MobEffects.POISON,
                    profile.poisonDurationTicks(),
                    profile.poisonAmplifier()
            ));
            context.level().addFreshEntity(cloud);

            detonateNearbyGrenades(context, profile.radius());
        };
    }

    private static void spawnGasBurst(
            GrenadeDetonationContext context,
            int color,
            int particleCount,
            double radius
    ) {
        double spread = radius * 0.45;
        context.level().sendParticles(
                gasParticle(color),
                context.position().x,
                context.position().y,
                context.position().z,
                particleCount,
                spread,
                spread * 0.65,
                spread,
                0.04
        );
    }

    private static ColorParticleOption gasParticle(int color) {
        return ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, ColorUtil.argb(255, color));
    }

    private static void detonateExplosion(GrenadeDetonationContext context, GrenadeExplosionSpec spec) {
        CustomExplosion.create(
                context.grenade(),
                context.owner(),
                context.position(),
                spec.entityRadius(),
                spec.damage(),
                spec.knockback(),
                spec.forceKnockback(),
                spec.blockBreakRadius(),
                spec.blockInteraction(),
                spec.causesFire());

        if (spec.playsDetonationSound()) {
            spec.detonationSound().play(context.level(), context.position());
        }
        for (GrenadeParticleBurst particle : spec.particles()) {
            particle.spawn(context.level(), context.position());
        }

        detonateNearbyGrenades(context, spec.entityRadius());
    }

    private static void detonateNearbyGrenades(GrenadeDetonationContext context, double radius) {
        double radiusSquared = radius * radius;
        AABB bounds = new AABB(context.position(), context.position()).inflate(radius);

        for (GrenadeEntity grenade : context.level().getEntitiesOfClass(GrenadeEntity.class, bounds)) {
            if (grenade != context.grenade()
                    && !grenade.isRemoved()
                    && grenade.distanceToSqr(context.position()) <= radiusSquared) {
                grenade.detonate();
            }
        }
    }

    private static void igniteNearbyEntities(GrenadeDetonationContext context, GrenadeFirebombProfile profile) {
        double radiusSquared = profile.entityIgnitionRadius() * profile.entityIgnitionRadius();
        AABB bounds = new AABB(context.position(), context.position()).inflate(profile.entityIgnitionRadius());

        for (LivingEntity target : context.level().getEntitiesOfClass(LivingEntity.class, bounds)) {
            if (target.isAlive() && target.distanceToSqr(context.position()) <= radiusSquared) {
                target.setRemainingFireTicks(Math.max(target.getRemainingFireTicks(), profile.entityFireTicks()));
            }
        }
    }

    private static void freezeNearbyEntities(GrenadeDetonationContext context, GrenadeCryobanProfile profile) {
        double radiusSquared = profile.freezeRadius() * profile.freezeRadius();
        AABB bounds = new AABB(context.position(), context.position()).inflate(profile.freezeRadius());

        for (LivingEntity target : context.level().getEntitiesOfClass(LivingEntity.class, bounds)) {
            if (!target.isAlive()
                    || !target.canFreeze()
                    || target.distanceToSqr(context.position()) > radiusSquared) {
                continue;
            }

            int fullyFrozenTicks = target.getTicksRequiredToFreeze();
            int timedFreezeTicks = fullyFrozenTicks + profile.freezeDurationTicks() * 2;
            target.setTicksFrozen(Math.max(target.getTicksFrozen(), timedFreezeTicks));
        }
    }

    private static void extinguishNearbyFire(GrenadeDetonationContext context, double radius) {
        double radiusSquared = radius * radius;
        AABB bounds = new AABB(context.position(), context.position()).inflate(radius);

        for (LivingEntity target : context.level().getEntitiesOfClass(LivingEntity.class, bounds)) {
            if (target.distanceToSqr(context.position()) <= radiusSquared) {
                target.setRemainingFireTicks(0);
            }
        }

        BlockPos min = BlockPos.containing(
                context.position().x - radius,
                context.position().y - radius,
                context.position().z - radius
        );
        BlockPos max = BlockPos.containing(
                context.position().x + radius,
                context.position().y + radius,
                context.position().z + radius
        );

        for (BlockPos position : BlockPos.betweenClosed(min, max)) {
            double x = position.getX() + 0.5 - context.position().x;
            double y = position.getY() + 0.5 - context.position().y;
            double z = position.getZ() + 0.5 - context.position().z;
            if (x * x + y * y + z * z <= radiusSquared
                    && context.level().getBlockState(position).is(BlockTags.FIRE)) {
                context.level().removeBlock(position, false);
            }
        }
    }

    private static void placeFirePatches(GrenadeDetonationContext context, GrenadeFirebombProfile profile) {
        RandomSource random = context.level().getRandom();
        BlockPos origin = BlockPos.containing(context.position());
        BlockState fire = Blocks.FIRE.defaultBlockState();

        for (int attempt = 0; attempt < profile.firePatchAttempts(); attempt++) {
            double angle = random.nextDouble() * Mth.TWO_PI;
            double distance = Math.sqrt(random.nextDouble()) * profile.firePatchRadius();
            int x = Mth.floor(context.position().x + Math.cos(angle) * distance);
            int z = Mth.floor(context.position().z + Math.sin(angle) * distance);

            for (int offsetY = profile.verticalSearchRange();
                 offsetY >= -profile.verticalSearchRange();
                 offsetY--) {
                BlockPos candidate = new BlockPos(x, origin.getY() + offsetY, z);
                if (!context.level().isInWorldBounds(candidate)
                        || !context.level().isEmptyBlock(candidate)
                        || !fire.canSurvive(context.level(), candidate)) {
                    continue;
                }

                context.level().setBlockAndUpdate(candidate, fire);
                break;
            }
        }
    }
}
