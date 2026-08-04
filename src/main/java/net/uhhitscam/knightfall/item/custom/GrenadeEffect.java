package net.uhhitscam.knightfall.item.custom;

@FunctionalInterface
public interface GrenadeEffect {
    void detonate(GrenadeDetonationContext context);
}
