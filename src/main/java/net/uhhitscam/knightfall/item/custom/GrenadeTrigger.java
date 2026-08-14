package net.uhhitscam.knightfall.item.custom;

public enum GrenadeTrigger {
    FUSE(true, false, true, false),
    IMPACT(false, true, false, false),
    IMPACT_OR_FUSE(true, true, false, false),
    FUSE_OR_IMPACT(true, true, true, false),
    STICKY_FUSE(true, false, false, true);

    private final boolean detonatesOnFuse;
    private final boolean detonatesOnImpact;
    private final boolean fuseRunsWhileHeld;
    private final boolean sticksToBlocks;

    GrenadeTrigger(
            boolean detonatesOnFuse,
            boolean detonatesOnImpact,
            boolean fuseRunsWhileHeld,
            boolean sticksToBlocks
    ) {
        this.detonatesOnFuse = detonatesOnFuse;
        this.detonatesOnImpact = detonatesOnImpact;
        this.fuseRunsWhileHeld = fuseRunsWhileHeld;
        this.sticksToBlocks = sticksToBlocks;
    }

    public boolean detonatesOnFuse() {
        return detonatesOnFuse;
    }

    public boolean detonatesOnImpact() {
        return detonatesOnImpact;
    }

    public boolean fuseRunsWhileHeld() {
        return fuseRunsWhileHeld;
    }

    public boolean sticksToBlocks() {
        return sticksToBlocks;
    }
}
