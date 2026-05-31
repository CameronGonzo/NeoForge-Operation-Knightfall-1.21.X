package net.uhhitscam.knightfall.util;

import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import net.uhhitscam.knightfall.event.FaceAlignedParticleType;
import net.uhhitscam.knightfall.network.CSFaceAlignedParticlePacket;
import org.joml.Vector3f;

public final class FaceAlignedParticleUtil {
    private FaceAlignedParticleUtil() {
    }

    public static void spawnSonicRipple(Level level, BlockHitResult blockHitResult) {
        spawn(level, blockHitResult, FaceAlignedParticleType.SONIC_RIPPLE, 0);
    }

    public static void spawnBlasterBurn(Level level, BlockHitResult blockHitResult) {
        if (level.isClientSide) {
            return;
        }

        int variant = 1 + level.random.nextInt(4);
        spawn(level, blockHitResult, FaceAlignedParticleType.BLASTER_BURN_MARK, variant);
    }

    private static void spawn(
            Level level,
            BlockHitResult blockHitResult,
            FaceAlignedParticleType effectType,
            int variant
    ) {
        if (level.isClientSide || !(level instanceof ServerLevel serverLevel)) {
            return;
        }

        Vec3 hitLocation = blockHitResult.getLocation();
        Direction direction = blockHitResult.getDirection();

        double offsetAmount = effectType == FaceAlignedParticleType.BLASTER_BURN_MARK ? 0.012D : 0.01D;

        Vec3 offsetLocation = hitLocation.add(
                direction.getStepX() * offsetAmount,
                direction.getStepY() * offsetAmount,
                direction.getStepZ() * offsetAmount
        );

        PacketDistributor.sendToPlayersNear(
                serverLevel,
                null,
                offsetLocation.x,
                offsetLocation.y,
                offsetLocation.z,
                48.0D,
                new CSFaceAlignedParticlePacket(
                        new Vector3f(
                                (float) offsetLocation.x,
                                (float) offsetLocation.y,
                                (float) offsetLocation.z
                        ),
                        direction.get3DDataValue(),
                        effectType.id(),
                        variant
                )
        );
    }
}