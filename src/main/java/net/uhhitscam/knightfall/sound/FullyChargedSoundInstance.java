package net.uhhitscam.knightfall.sound;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FullyChargedSoundInstance extends AbstractTickableSoundInstance {
    private final Player player;
    public static boolean stopAudio = false;
    public final SoundEvent soundEvent;

    public FullyChargedSoundInstance(SoundEvent soundEvent, Player player) {
        super(soundEvent, SoundSource.PLAYERS, player.level().getRandom());
        this.player = player;
        this.soundEvent = soundEvent;
        this.looping = true;
        this.volume = 1.0f;
        this.pitch = 1.0f;
        this.x = player.getX();
        this.y = player.getY();
        this.z = player.getZ();
    }

    @Override
    public void tick() {
        if (player.isRemoved() || stopAudio) {
            Minecraft.getInstance().getSoundManager().stop(this);
            this.stop();
            stopAudio = false;
        }

        this.x = player.getX();
        this.y = player.getY();
        this.z = player.getZ();
    }
}
