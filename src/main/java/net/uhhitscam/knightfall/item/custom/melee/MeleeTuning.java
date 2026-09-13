package net.uhhitscam.knightfall.item.custom.melee;

public record MeleeTuning(int holdThresholdTicks, int parryWindowTicks, int blockDisableTicks,
                          int fireSeconds, double flurryHitSpeed, double flurryMissSpeed,
                          float cortosisChance, int lightsaberDisableTicks, double tetherRange) {
    public static final MeleeTuning DEFAULT = new MeleeTuning(4, 1, 60, 4, 1.5, 0.6, 0.25F, 60, 12);

    public MeleeTuning {
        if (holdThresholdTicks < 1 || parryWindowTicks < 1 || blockDisableTicks < 1 || fireSeconds < 1
                || !Double.isFinite(flurryHitSpeed) || flurryHitSpeed <= 1
                || !Double.isFinite(flurryMissSpeed) || flurryMissSpeed <= 0 || flurryMissSpeed >= 1
                || !Float.isFinite(cortosisChance) || cortosisChance < 0 || cortosisChance > 1
                || lightsaberDisableTicks < 1 || !Double.isFinite(tetherRange) || tetherRange <= 0 || tetherRange > 64) {
            throw new IllegalArgumentException("Invalid melee weapon timing/property tuning.");
        }
    }
}
