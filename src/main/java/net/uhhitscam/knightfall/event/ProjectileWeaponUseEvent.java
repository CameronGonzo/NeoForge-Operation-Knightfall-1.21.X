package net.uhhitscam.knightfall.event;

import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;

public final class ProjectileWeaponUseEvent extends Event {
    private final Player player;

    public ProjectileWeaponUseEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
