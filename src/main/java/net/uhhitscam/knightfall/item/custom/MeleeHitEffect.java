package net.uhhitscam.knightfall.item.custom;

@FunctionalInterface
public interface MeleeHitEffect {
    boolean apply(MeleeHitContext context);
}
