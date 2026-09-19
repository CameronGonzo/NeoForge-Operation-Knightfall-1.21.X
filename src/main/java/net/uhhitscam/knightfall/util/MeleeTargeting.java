package net.uhhitscam.knightfall.util;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;

public final class MeleeTargeting {
    private MeleeTargeting() {}

    public static boolean canHit(LivingEntity attacker, LivingEntity target) {
        return attacker != target && target.isAlive() && target.isPickable() && !target.isSpectator()
                && !attacker.isAlliedTo(target)
                && !(target instanceof Player p && (p.getAbilities().invulnerable
                || attacker instanceof Player a && (!a.canHarmPlayer(p)
                || a instanceof ServerPlayer serverPlayer && !serverPlayer.level().getGameRules().get(net.minecraft.world.level.gamerules.GameRules.PVP))));
    }

    public static LivingEntity ray(Player player, double reach) {
        Vec3 start = player.getEyePosition();
        Vec3 end = clipped(player, start, start.add(player.getLookAngle().scale(reach)));
        return player.level().getEntitiesOfClass(LivingEntity.class, new AABB(start, end).inflate(1),
                        target -> canHit(player, target) && (target.getBoundingBox().contains(start)
                                || target.getBoundingBox().inflate(target.getPickRadius()).clip(start, end).isPresent()))
                .stream().min(Comparator.comparingDouble(target -> target.getBoundingBox().inflate(target.getPickRadius())
                        .clip(start, end).orElse(end).distanceToSqr(start))).orElse(null);
    }

    public static List<LivingEntity> area(ServerPlayer player, double radius, boolean front) {
        Vec3 facing = player.getLookAngle().multiply(1, 0, 1).normalize();
        return player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(radius, 1.5, radius),
                target -> canHit(player, target) && target.distanceToSqr(player) <= radius * radius
                        && player.hasLineOfSight(target)
                        && (!front || target.position().subtract(player.position()).normalize().dot(facing) >= 0.25));
    }

    public static List<LivingEntity> path(ServerPlayer player, Vec3 previous) {
        if (previous.distanceToSqr(player.position()) > 16) previous = player.position();
        Vec3 end = clipped(player, previous.add(0, 0.8, 0), player.position().add(0, 0.8, 0));
        Vec3 start = previous.add(0, 0.8, 0);
        return player.level().getEntitiesOfClass(LivingEntity.class, new AABB(start, end).inflate(0.9),
                target -> canHit(player, target) && player.hasLineOfSight(target)
                        && (target.getBoundingBox().inflate(0.65).contains(end)
                        || target.getBoundingBox().inflate(0.65).clip(start, end).isPresent()));
    }

    public static List<LivingEntity> contact(ServerPlayer player, double reach) {
        Vec3 start = player.getEyePosition().add(0, -0.35, 0);
        Vec3 end = clipped(player, start, start.add(player.getLookAngle().scale(reach)));
        return player.level().getEntitiesOfClass(LivingEntity.class, new AABB(start, end).inflate(0.35),
                target -> canHit(player, target) && player.hasLineOfSight(target)
                        && (target.getBoundingBox().inflate(0.25).contains(start)
                        || target.getBoundingBox().inflate(0.25).clip(start, end).isPresent()));
    }

    public static Vec3 clipped(LivingEntity user, Vec3 start, Vec3 end) {
        var hit = user.level().clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, user));
        return hit.getType() == HitResult.Type.MISS ? end : hit.getLocation();
    }
}
