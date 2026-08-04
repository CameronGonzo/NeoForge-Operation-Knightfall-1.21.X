package net.uhhitscam.knightfall.item.custom;

public enum GrenadeTrigger {
    FUSE(true, false),
    IMPACT(false, true),
    FUSE_OR_IMPACT(true, true);

    private final boolean detonatesOnFuse;
    private final boolean detonatesOnImpact;

    GrenadeTrigger(boolean detonatesOnFuse, boolean detonatesOnImpact) {
        this.detonatesOnFuse = detonatesOnFuse;
        this.detonatesOnImpact = detonatesOnImpact;
    }

    public boolean detonatesOnFuse() {
        return detonatesOnFuse;
    }

    public boolean detonatesOnImpact() {
        return detonatesOnImpact;
    }
}
