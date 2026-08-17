package net.uhhitscam.knightfall.item.custom;

import java.util.Objects;

public record MeleeWeaponAudioProfile(
        MeleeWeaponSound equipSound,
        MeleeWeaponSound unequipSound
) {
    public MeleeWeaponAudioProfile {
        Objects.requireNonNull(equipSound, "Melee weapon equip sound cannot be null.");
        Objects.requireNonNull(unequipSound, "Melee weapon unequip sound cannot be null.");
    }
}
