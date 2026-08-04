package net.uhhitscam.knightfall.item.custom;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;
import java.util.function.Supplier;

public record GrenadeSound(
        Supplier<SoundEvent> sound,
        SoundSource source,
        float volume,
        float pitch
) {
    public GrenadeSound {
        Objects.requireNonNull(sound, "Grenade sound supplier cannot be null.");
        Objects.requireNonNull(source, "Grenade sound source cannot be null.");

        if (volume < 0.0F) {
            throw new IllegalArgumentException("Grenade sound volume cannot be negative.");
        }
        if (pitch <= 0.0F) {
            throw new IllegalArgumentException("Grenade sound pitch must be greater than 0.");
        }
    }

    public void play(Level level, Vec3 position) {
        level.playSound(
                null,
                position.x,
                position.y,
                position.z,
                sound.get(),
                source,
                volume,
                pitch
        );
    }
}
