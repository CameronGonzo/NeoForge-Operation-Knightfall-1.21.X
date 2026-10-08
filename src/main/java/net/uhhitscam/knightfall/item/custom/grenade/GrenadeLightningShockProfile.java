package net.uhhitscam.knightfall.item.custom.grenade;

public record GrenadeLightningShockProfile(int durationTicks) {
    public GrenadeLightningShockProfile {
        if (durationTicks < 0) {
            throw new IllegalArgumentException("Lightning shock duration cannot be negative.");
        }
    }
}
