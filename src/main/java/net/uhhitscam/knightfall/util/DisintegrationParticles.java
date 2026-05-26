package net.uhhitscam.knightfall.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import net.uhhitscam.knightfall.network.CSDisintegrationParticlesPacket;
import org.joml.Vector3f;

public final class DisintegrationParticles {
    private DisintegrationParticles() {
    }

    public static void spawn(Level level, Entity entity) {
        if (level.isClientSide || !(level instanceof ServerLevel serverLevel)) {
            return;
        }

        Vec3 origin = entity.position().add(0, entity.getBbHeight() * 0.5, 0);

        PacketDistributor.sendToPlayersNear(
                serverLevel,
                null,
                origin.x,
                origin.y,
                origin.z,
                32,
                new CSDisintegrationParticlesPacket(
                        fromVec3(origin),
                        (float) entity.getBbWidth(),
                        (float) entity.getBbHeight(),
                        8,
                        12,
                        18
                )
        );
    }

    private static Vector3f fromVec3(Vec3 vec) {
        return new Vector3f((float) vec.x, (float) vec.y, (float) vec.z);
    }
}