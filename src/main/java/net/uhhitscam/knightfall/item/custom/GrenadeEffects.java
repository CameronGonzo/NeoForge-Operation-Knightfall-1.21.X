package net.uhhitscam.knightfall.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.network.PacketDistributor;
import net.uhhitscam.knightfall.entity.custom.GrenadeEntity;
import net.uhhitscam.knightfall.network.CSConcussionBlurPacket;
import net.uhhitscam.knightfall.util.CustomExplosion;
import org.joml.Vector3f;

import java.util.Objects;

public final class GrenadeEffects {
    private GrenadeEffects() {
    }

    public static GrenadeEffect explosion(GrenadeExplosionSpec spec) {
        Objects.requireNonNull(spec, "Grenade explosion specification cannot be null.");

        return context -> detonateExplosion(context, spec);
    }

    public static GrenadeEffect concussiveExplosion(
            GrenadeExplosionSpec explosionSpec,
            GrenadeConcussionProfile concussionProfile
    ) {
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

    public static GrenadeEffect incendiaryExplosion(
            GrenadeExplosionSpec explosionSpec,
            GrenadeIncendiaryProfile incendiaryProfile
    ) {
        Objects.requireNonNull(explosionSpec, "Grenade explosion specification cannot be null.");
        Objects.requireNonNull(incendiaryProfile, "Grenade incendiary profile cannot be null.");

        return context -> {
            detonateExplosion(context, explosionSpec);
            igniteNearbyEntities(context, incendiaryProfile);
            placeFirePatches(context, incendiaryProfile);
        };
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
                spec.causesFire()
        );

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

    private static void igniteNearbyEntities(
            GrenadeDetonationContext context,
            GrenadeIncendiaryProfile profile
    ) {
        double radiusSquared = profile.entityIgnitionRadius() * profile.entityIgnitionRadius();
        AABB bounds = new AABB(context.position(), context.position()).inflate(profile.entityIgnitionRadius());

        for (LivingEntity target : context.level().getEntitiesOfClass(LivingEntity.class, bounds)) {
            if (target.isAlive() && target.distanceToSqr(context.position()) <= radiusSquared) {
                target.setRemainingFireTicks(Math.max(target.getRemainingFireTicks(), profile.entityFireTicks()));
            }
        }
    }

    private static void placeFirePatches(
            GrenadeDetonationContext context,
            GrenadeIncendiaryProfile profile
    ) {
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
