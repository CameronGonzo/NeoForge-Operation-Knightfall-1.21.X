package net.uhhitscam.knightfall.item.custom.melee;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public record MeleeWeaponForm(double damage, double attackSpeed, Map<MeleeInput, MeleeAttack> attacks,
                              Set<MeleeProperty> properties) {
    public MeleeWeaponForm {
        if (!Double.isFinite(damage) || damage <= 0 || !Double.isFinite(attackSpeed) || attackSpeed <= 0) {
            throw new IllegalArgumentException("Melee damage and attack speed must be positive.");
        }
        attacks = Map.copyOf(attacks);
        properties = Set.copyOf(properties);
        attacks.forEach((input, attack) -> {
            if (!attack.trait().supports(input)) {
                throw new IllegalArgumentException(attack.trait() + " cannot use " + input);
            }
        });
        // A latched whip owns subsequent tap/hold gestures until detached
        MeleeAttack tap = attacks.get(MeleeInput.RIGHT_CLICK);
        if (tap != null && tap.trait().whip() && attacks.keySet().stream().anyMatch(input ->
                input != MeleeInput.LEFT_CLICK && input != MeleeInput.RIGHT_CLICK)) {
            throw new IllegalArgumentException("A whip owns right-click holds; assign other holds to another form.");
        }
    }

    public ItemAttributeModifiers attributes() {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, damage - 1,
                        AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, attackSpeed - 4,
                        AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build();
    }

    public boolean has(MeleeProperty property) { return properties.contains(property); }
    public MeleeAttack attack(MeleeInput input) { return attacks.get(input); }
    public boolean hasUse() { return attacks.keySet().stream().anyMatch(input -> input != MeleeInput.LEFT_CLICK); }
    public boolean hasHold() { return attacks.keySet().stream().anyMatch(input -> input.ordinal() >= MeleeInput.HOLD_RIGHT_CLICK.ordinal()); }

    public static Builder builder(double damage, double attackSpeed) { return new Builder(damage, attackSpeed); }

    public static final class Builder {
        private final double damage;
        private final double attackSpeed;
        private final Map<MeleeInput, MeleeAttack> attacks = new EnumMap<>(MeleeInput.class);
        private final Set<MeleeProperty> properties = EnumSet.noneOf(MeleeProperty.class);

        private Builder(double damage, double attackSpeed) { this.damage = damage; this.attackSpeed = attackSpeed; }

        public Builder bind(MeleeInput input, MeleeAttackTrait trait) { return bind(input, MeleeAttack.of(trait)); }
        public Builder bind(MeleeInput input, MeleeAttack attack) {
            if (attacks.putIfAbsent(input, attack) != null) throw new IllegalArgumentException("Duplicate melee input: " + input);
            return this;
        }
        public Builder properties(MeleeProperty... values) { properties.addAll(Set.of(values)); return this; }
        public MeleeWeaponForm build() { return new MeleeWeaponForm(damage, attackSpeed, attacks, properties); }
    }
}
