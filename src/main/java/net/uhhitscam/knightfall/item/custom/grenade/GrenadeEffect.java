package net.uhhitscam.knightfall.item.custom.grenade;

@FunctionalInterface
public interface GrenadeEffect {
    void detonate(GrenadeDetonationContext context);
}
