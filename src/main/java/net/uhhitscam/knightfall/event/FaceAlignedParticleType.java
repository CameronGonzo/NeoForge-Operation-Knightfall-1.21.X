package net.uhhitscam.knightfall.event;

public enum FaceAlignedParticleType {
    SONIC_RIPPLE(0),
    BLASTER_BURN_MARK(1);

    private final int id;

    FaceAlignedParticleType(int id) {
        this.id = id;
    }

    public int id() {
        return id;
    }

    public static FaceAlignedParticleType byId(int id) {
        for (FaceAlignedParticleType type : values()) {
            if (type.id == id) {
                return type;
            }
        }

        return BLASTER_BURN_MARK;
    }
}