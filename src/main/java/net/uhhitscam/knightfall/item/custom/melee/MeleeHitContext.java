package net.uhhitscam.knightfall.item.custom.melee;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public record MeleeHitContext(
        ServerLevel level,
        LivingEntity attacker,
        LivingEntity target,
        ItemStack weaponStack,
        MeleeWeaponDefinition definition
) {
}
