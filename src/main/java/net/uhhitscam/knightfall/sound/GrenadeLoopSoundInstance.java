package net.uhhitscam.knightfall.sound;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.uhhitscam.knightfall.entity.custom.GrenadeEntity;
import net.uhhitscam.knightfall.item.custom.grenade.GrenadeDefinition;
import net.uhhitscam.knightfall.item.custom.grenade.GrenadeFuseSoundMode;
import net.uhhitscam.knightfall.item.custom.grenade.GrenadeSound;

public final class GrenadeLoopSoundInstance extends AbstractTickableSoundInstance {
    private final GrenadeEntity grenade;

    public GrenadeLoopSoundInstance(GrenadeSound sound, GrenadeEntity grenade) {
        super(sound.sound().get(), sound.source(), grenade.level().getRandom());
        this.grenade = grenade;
        this.looping = true;
        this.volume = sound.volume();
        this.pitch = sound.pitch();
        updatePosition();
    }

    @Override
    public void tick() {
        GrenadeDefinition definition = grenade.getGrenadeDefinition();
        if (grenade.isRemoved()
                || definition == null
                || definition.fuseSoundMode() != GrenadeFuseSoundMode.LOOP_UNTIL_REMOVED) {
            Minecraft.getInstance().getSoundManager().stop(this);
            stop();
            return;
        }

        updatePosition();
    }

    private void updatePosition() {
        x = grenade.getX();
        y = grenade.getBoundingBox().getCenter().y;
        z = grenade.getZ();
    }
}
