package net.uhhitscam.knightfall.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import net.uhhitscam.knightfall.network.CSRepulseParticlesPacket;
import org.joml.Vector3f;

import java.util.List;

public class Repulse {
    public static void execute(Level level, Player player, double range, double strength) {
        Vec3 lookDir = player.getLookAngle().normalize();
        Vec3 origin = player.position().add(0, player.getEyeHeight(), 0);

        PacketDistributor.sendToPlayersNear(
                (ServerLevel) level,
                null,
                origin.x, origin.y, origin.z,
                32,
                new CSRepulseParticlesPacket(fromVec3(origin), fromVec3(lookDir), 3, 4, 5)
        );

        List<Entity> entities = level.getEntities(player, player.getBoundingBox().inflate(range),
                e -> e instanceof LivingEntity && e != player);

        for (Entity entity : entities) {
            Vec3 toTarget = entity.position().add(0, entity.getEyeHeight() / 2, 0).subtract(origin);
            double distance = toTarget.length();

            double dot = lookDir.dot(toTarget.normalize());
            if (dot > Math.cos(Math.toRadians(45)) && distance <= range) {
                Vec3 knockback = toTarget.normalize().scale(strength);

                knockback = new Vec3(knockback.x, knockback.y, knockback.z);

                entity.setDeltaMovement(knockback);
                entity.hurtMarked = true;
            }
        }
    }

    static Vector3f fromVec3(Vec3 vec) {
        return new Vector3f((float) vec.x, (float) vec.y, (float) vec.z);
    }

}
