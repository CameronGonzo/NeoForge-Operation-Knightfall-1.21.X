package net.uhhitscam.knightfall.item.custom.grenade;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.uhhitscam.knightfall.OperationKnightfall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@EventBusSubscriber(modid = OperationKnightfall.MODID)
public final class GrenadeParticleScheduler {
    private static final int POSITION_ATTEMPTS = 12;
    private static final Map<ServerLevel, List<ScheduledParticle>> PENDING_PARTICLES = new HashMap<>();

    private GrenadeParticleScheduler() {
    }

    public static void scheduleRandomBurst(
            ServerLevel level,
            ParticleOptions particle,
            Vec3 center,
            int count,
            int durationTicks,
            double horizontalRadius,
            double minimumHeight,
            double maximumHeight
    ) {
        if (count <= 0 || durationTicks <= 0) {
            return;
        }

        RandomSource random = level.getRandom();
        Set<Integer> usedDelays = new HashSet<>();
        List<ScheduledParticle> particles = PENDING_PARTICLES.computeIfAbsent(level, ignored -> new ArrayList<>());

        for (int index = 0; index < count; index++) {
            for (int attempt = 0; attempt < POSITION_ATTEMPTS; attempt++) {
                double angle = random.nextDouble() * Mth.TWO_PI;
                double distance = Math.sqrt(random.nextDouble()) * horizontalRadius;
                Vec3 position = center.add(
                        Math.cos(angle) * distance,
                        minimumHeight + random.nextDouble() * (maximumHeight - minimumHeight),
                        Math.sin(angle) * distance
                );
                BlockPos blockPos = BlockPos.containing(position);
                if (!level.isInWorldBounds(blockPos) || !level.isEmptyBlock(blockPos)) {
                    continue;
                }

                int delay = randomDelay(random, durationTicks, usedDelays);
                particles.add(new ScheduledParticle(particle, position, level.getGameTime() + delay));
                break;
            }
        }
    }

    private static int randomDelay(RandomSource random, int durationTicks, Set<Integer> usedDelays) {
        if (usedDelays.size() >= durationTicks) {
            return random.nextInt(durationTicks);
        }

        int delay;
        do {
            delay = random.nextInt(durationTicks);
        } while (!usedDelays.add(delay));
        return delay;
    }

    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        if (!(event.getLevel() instanceof ServerLevel level)) {
            return;
        }

        List<ScheduledParticle> particles = PENDING_PARTICLES.get(level);
        if (particles == null) {
            return;
        }

        long gameTime = level.getGameTime();
        Iterator<ScheduledParticle> iterator = particles.iterator();
        while (iterator.hasNext()) {
            ScheduledParticle particle = iterator.next();
            if (particle.gameTime() > gameTime) {
                continue;
            }

            level.sendParticles(
                    particle.options(),
                    particle.position().x,
                    particle.position().y,
                    particle.position().z,
                    1,
                    0.0,
                    0.0,
                    0.0,
                    0.0
            );
            iterator.remove();
        }

        if (particles.isEmpty()) {
            PENDING_PARTICLES.remove(level);
        }
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        PENDING_PARTICLES.clear();
    }

    private record ScheduledParticle(ParticleOptions options, Vec3 position, long gameTime) {
    }
}
