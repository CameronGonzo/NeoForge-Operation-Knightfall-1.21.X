package net.uhhitscam.knightfall.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.uhhitscam.knightfall.entity.ModEntities;
import net.uhhitscam.knightfall.entity.custom.BlasterBeamEndpointEntity;
import net.uhhitscam.knightfall.item.custom.BeamWeaponStats;
import net.uhhitscam.knightfall.item.custom.ProjectileItem;

public final class BeamLogic {
    private BeamLogic() {}

    public static void startOrUpdateBeam(ServerPlayer player, ProjectileItem weapon, ItemStack stack, boolean mainHand) {
        ServerLevel level = player.serverLevel();

        BeamWeaponStats beamStats = weapon.getBeamStats();
        if (beamStats == null) {
            return;
        }

        double range = 64.0F;
        float damage = beamStats.damagePerPulse();

        BlasterBeamEndpointEntity beam = findExisting(level, player, mainHand);
        if (beam == null) {
            beam = new BlasterBeamEndpointEntity(ModEntities.BLASTER_BEAM.get(), level);
            beam.setOwner(player);
            beam.setMainHand(mainHand);
            beam.setPos(player.getX(), player.getEyeY(), player.getZ());
            level.addFreshEntity(beam);
        }

        beam.configure(range, damage);
    }

    public static void stopBeam(ServerPlayer player, boolean mainHand) {
        ServerLevel level = player.serverLevel();
        BlasterBeamEndpointEntity beam = findExisting(level, player, mainHand);
        if (beam != null) beam.discard();
    }

    private static BlasterBeamEndpointEntity findExisting(ServerLevel level, ServerPlayer player, boolean mainHand) {
        var beams = level.getEntitiesOfClass(
                BlasterBeamEndpointEntity.class,
                player.getBoundingBox().inflate(128),
                beam -> beam.isOwnedBy(player) && beam.isMainHand() == mainHand
        );
        return beams.isEmpty() ? null : beams.get(0);
    }
}
