package net.uhhitscam.knightfall.item.custom.melee;

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

    public MeleeWeaponForm getForm(ItemStack stack) {
        return definition.alternateForm() != null && stack.getOrDefault(
                net.uhhitscam.knightfall.component.ModDataComponentTypes.MELEE_ALTERNATE_FORM.get(), false)
                ? definition.alternateForm() : definition.form();
    }

    public void switchForm(ItemStack stack) {
        if (definition.alternateForm() == null) return;
        var key = net.uhhitscam.knightfall.component.ModDataComponentTypes.MELEE_ALTERNATE_FORM.get();
        stack.set(key, !stack.getOrDefault(key, false));
        stack.set(net.minecraft.core.component.DataComponents.ATTRIBUTE_MODIFIERS, getForm(stack).attributes());
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, net.minecraft.world.entity.player.Player player,
                                     net.minecraft.world.entity.Entity target) {
        return getForm(stack) != null && !net.uhhitscam.knightfall.event.MeleeWeaponServerEvents.isExecutingAttack(player);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        // Configured attacks apply effects only after confirmed damage in the combat controller
        if (getForm(stack) != null) return true;
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
