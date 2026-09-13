package net.uhhitscam.knightfall.item.custom.melee;

@FunctionalInterface
public interface MeleeHitEffect {
    boolean apply(MeleeHitContext context);
}
