package net.uhhitscam.knightfall.item.custom.melee;

import java.util.Objects;

public record MeleeWeaponAudioProfile(
        MeleeWeaponSound equipSound,
        MeleeWeaponSound unequipSound
) {
    public static final MeleeWeaponAudioProfile SILENT = new MeleeWeaponAudioProfile(
            new MeleeWeaponSound(() -> net.minecraft.sounds.SoundEvents.EMPTY,
                    net.minecraft.sounds.SoundSource.PLAYERS, 0, 1),
            new MeleeWeaponSound(() -> net.minecraft.sounds.SoundEvents.EMPTY,
                    net.minecraft.sounds.SoundSource.PLAYERS, 0, 1));
    public MeleeWeaponAudioProfile {
        Objects.requireNonNull(equipSound, "Melee weapon equip sound cannot be null.");
        Objects.requireNonNull(unequipSound, "Melee weapon unequip sound cannot be null.");
    }
}
