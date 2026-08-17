package net.uhhitscam.knightfall.item.custom;

public final class MeleeEffects {
    private MeleeEffects() {
    }

    public static MeleeHitEffect attachedExplosive(AttachedExplosiveSpec spec) {
        return new AttachedExplosiveMeleeEffect(spec);
    }
}
