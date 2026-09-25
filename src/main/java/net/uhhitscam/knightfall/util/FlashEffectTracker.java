package net.uhhitscam.knightfall.util;

import net.minecraft.world.entity.Mob;

import java.util.Map;
import java.util.WeakHashMap;

public final class FlashEffectTracker {
    private static final Map<Mob, Long> FLASHED_MOBS = new WeakHashMap<>();

    private FlashEffectTracker() {
    }

    public static void apply(Mob mob, int durationTicks) {
        mob.setTarget(null);
        FLASHED_MOBS.put(mob, mob.level().getGameTime() + durationTicks);
    }

    public static boolean isFlashed(Mob mob) {
        Long expirationTick = FLASHED_MOBS.get(mob);
        if (expirationTick == null) {
            return false;
        }
        if (mob.level().getGameTime() >= expirationTick || !mob.isAlive()) {
            FLASHED_MOBS.remove(mob);
            return false;
        }
        return true;
    }
}
