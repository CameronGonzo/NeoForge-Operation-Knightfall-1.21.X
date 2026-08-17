package net.uhhitscam.knightfall.item.custom;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;
import java.util.function.Supplier;

public record MeleeWeaponSound(
        Supplier<SoundEvent> sound,
        SoundSource source,
        float volume,
        float pitch
) {
    public MeleeWeaponSound {
        Objects.requireNonNull(sound, "Melee weapon sound supplier cannot be null.");
        Objects.requireNonNull(source, "Melee weapon sound source cannot be null.");
        if (volume < 0.0F) {
            throw new IllegalArgumentException("Melee weapon sound volume cannot be negative.");
        }
        if (pitch <= 0.0F) {
            throw new IllegalArgumentException("Melee weapon sound pitch must be greater than 0.");
        }
    }

    public void play(Level level, Vec3 position) {
        level.playSound(null, position.x, position.y, position.z, sound.get(), source, volume, pitch);
    }
}
