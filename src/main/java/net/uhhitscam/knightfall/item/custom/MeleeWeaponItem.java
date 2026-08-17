package net.uhhitscam.knightfall.item.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class MeleeWeaponItem extends Item {
    private final MeleeWeaponDefinition definition;

    public MeleeWeaponItem(Properties properties, MeleeWeaponDefinition definition) {
        super(properties);
        this.definition = definition;
    }

    public MeleeWeaponDefinition getDefinition() {
        return definition;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.level() instanceof ServerLevel serverLevel) {
            boolean effectApplied = definition.hitEffect().apply(new MeleeHitContext(
                    serverLevel,
                    attacker,
                    target,
                    stack,
                    definition
            ));
            if (effectApplied && definition.consumesOnSuccessfulHit()) {
                stack.consume(1, attacker);
            }
        }
        return true;
    }
}
