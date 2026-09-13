package net.uhhitscam.knightfall.item.custom;

public record GrenadeConcussionProfile(
        float effectRadiusBlocks,
        int holdTicks,
        int fadeOutTicks,
        float maxShaderRadius
) {
    public GrenadeConcussionProfile {
        if (effectRadiusBlocks <= 0.0F) {
            throw new IllegalArgumentException("Grenade concussion radius must be greater than 0.");
        }
        if (holdTicks < 0) {
            throw new IllegalArgumentException("Grenade concussion hold time cannot be negative.");
        }
        if (fadeOutTicks <= 0) {
            throw new IllegalArgumentException("Grenade concussion fade time must be greater than 0.");
        }
        if (maxShaderRadius < 0.0F) {
            throw new IllegalArgumentException("Grenade concussion shader radius cannot be negative.");
        }
    }
}
