package net.uhhitscam.knightfall.util;

import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import net.uhhitscam.knightfall.network.CSSonicImpactRipplePacket;
import org.joml.Vector3f;

public final class SonicImpactRipple {
    private SonicImpactRipple() {
    }

    public static void spawn(Level level, BlockHitResult blockHitResult) {
        if (level.isClientSide || !(level instanceof ServerLevel serverLevel)) {
            return;
        }

        Vec3 hitLocation = blockHitResult.getLocation();
        Direction direction = blockHitResult.getDirection();

        Vec3 offsetLocation = hitLocation.add(
                direction.getStepX() * 0.01D,
                direction.getStepY() * 0.01D,
                direction.getStepZ() * 0.01D
        );

        PacketDistributor.sendToPlayersNear(
                serverLevel,
                null,
                offsetLocation.x,
                offsetLocation.y,
                offsetLocation.z,
                48.0D,
                new CSSonicImpactRipplePacket(
                        new Vector3f(
                                (float) offsetLocation.x,
                                (float) offsetLocation.y,
                                (float) offsetLocation.z
                        ),
                        direction.get3DDataValue()
                )
        );
    }
}