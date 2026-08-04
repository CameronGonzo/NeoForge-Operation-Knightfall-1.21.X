package net.uhhitscam.knightfall.item.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.uhhitscam.knightfall.entity.custom.GrenadeEntity;
import org.jetbrains.annotations.Nullable;

public record GrenadeDetonationContext(
        ServerLevel level,
        GrenadeEntity grenade,
        @Nullable LivingEntity owner,
        GrenadeDefinition definition,
        Vec3 position
) {
}
