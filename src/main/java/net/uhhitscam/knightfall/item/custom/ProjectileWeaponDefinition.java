package net.uhhitscam.knightfall.item.custom;

import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;

public record ProjectileWeaponDefinition(
        WeaponName weaponName,
        String registryName,
        Item.Properties itemProperties,
        float projectileSpeed,
        int maxAmmo,
        int maxOverheat,
        int overheatCoolingAmount,
        int overheatCoolingIntervalTicks,
        int burstRate,
        int scatterShots,
        EnumMap<FiringMode, ProjectileWeaponStats> stats,
        @Nullable BeamWeaponStats beamStats,
        List<FiringMode> firingModes,
        ProjectileWeaponUI ui,
        FiringMode defaultFiringMode,
        AmmoType ammoType,
        WeaponClassification classification,
        float dualWieldRecoilMultiplier,
        float dualWieldRecoilPenalty,
        float dualWieldInaccuracyMultiplier,
        float dualWieldInaccuracyPenalty,
        double heldMovementSpeedMultiplier,
        ProjectileWeaponTiming timing
) {
    public static Builder builder(WeaponName weaponName, String registryName) {
        return new Builder(weaponName, registryName);
    }

    public static final class Builder {
        private final WeaponName weaponName;
        private final String registryName;

        private Item.Properties itemProperties = new Item.Properties().stacksTo(1);
        private float projectileSpeed;
        private int maxAmmo = 500;
        private int maxOverheat = -1;
        private int overheatCoolingAmount = -1;
        private int overheatCoolingIntervalTicks = -1;
        private int burstRate = 2;
        private int scatterShots = 5;
        private final EnumMap<FiringMode, ProjectileWeaponStats> stats = new EnumMap<>(FiringMode.class);
        private BeamWeaponStats beamStats;
        private final List<FiringMode> firingModes = new ArrayList<>();
        private ProjectileWeaponUI ui;
        private FiringMode defaultFiringMode;
        private AmmoType ammoType = AmmoType.TIBANNA;
        private WeaponClassification classification;
        private Float dualWieldRecoilMultiplier;
        private Float dualWieldRecoilPenalty;
        private Float dualWieldInaccuracyMultiplier;
        private Float dualWieldInaccuracyPenalty;
        private double heldMovementSpeedMultiplier = 1.0;
        private ProjectileWeaponTiming timing;

        private Builder(WeaponName weaponName, String registryName) {
            this.weaponName = Objects.requireNonNull(weaponName, "weaponName cannot be null");
            this.timing = ProjectileWeaponTiming.defaults(weaponName);

            if (registryName == null || registryName.isBlank()) {
                throw new IllegalArgumentException("registryName cannot be blank for " + weaponName);
            }

            this.registryName = registryName;
        }

        public Builder reloadTime(long ticks) {
            this.timing = this.timing.withReloadTicks(ticks);
            return this;
        }

        public Builder reloadTime(FiringMode firingMode, long ticks) {
            this.timing = this.timing.withReloadTicks(firingMode, ticks);
            return this;
        }

        public Builder switchTime(long ticks) {
            this.timing = this.timing.withSwitchTicks(ticks);
            return this;
        }

        public Builder switchTime(FiringMode firingMode, long ticks) {
            this.timing = this.timing.withSwitchTicks(firingMode, ticks);
            return this;
        }

        public Builder chargeThreshold(int ticks) {
            this.timing = this.timing.withChargeThresholdTicks(ticks);
            return this;
        }

        public Builder equipTime(long ticks) {
            this.timing = this.timing.withEquipTicks(ticks);
            return this;
        }

        public Builder itemProperties(Item.Properties itemProperties) {
            this.itemProperties = Objects.requireNonNull(itemProperties, "itemProperties cannot be null");
            return this;
        }

        public Builder projectileSpeed(float projectileSpeed) {
            this.projectileSpeed = projectileSpeed;
            return this;
        }

        public Builder maxAmmo(int maxAmmo) {
            this.maxAmmo = maxAmmo;
            return this;
        }

        public Builder maxOverheat(int maxOverheat) {
            this.maxOverheat = maxOverheat;
            return this;
        }

        public Builder overheatCooling(int amount, int intervalTicks) {
            this.overheatCoolingAmount = amount;
            this.overheatCoolingIntervalTicks = intervalTicks;
            return this;
        }

        public Builder burstRate(int burstRate) {
            this.burstRate = burstRate;
            return this;
        }

        public Builder scatterShots(int scatterShots) {
            this.scatterShots = scatterShots;
            return this;
        }

        public Builder stat(FiringMode firingMode, int fireRate, float recoil, float inaccuracy, int damage, int overheatPerShot) {
            return stat(firingMode, new ProjectileWeaponStats(fireRate, recoil, inaccuracy, damage, overheatPerShot));
        }

        public Builder stat(FiringMode firingMode, ProjectileWeaponStats weaponStats) {
            stats.put(
                    Objects.requireNonNull(firingMode, "firingMode cannot be null"),
                    Objects.requireNonNull(weaponStats, "weaponStats cannot be null")
            );
            return this;
        }

        public Builder beam(float damagePerPulse, int overheatPerPulse) {
            this.beamStats = new BeamWeaponStats(damagePerPulse, overheatPerPulse);
            return this;
        }

        public Builder beam(BeamWeaponStats beamStats) {
            this.beamStats = Objects.requireNonNull(beamStats, "beamStats cannot be null");
            return this;
        }

        public Builder firingModes(FiringMode... firingModes) {
            this.firingModes.clear();

            for (FiringMode firingMode : firingModes) {
                this.firingModes.add(Objects.requireNonNull(firingMode, "firingMode cannot be null"));
            }

            return this;
        }

        public Builder ui(ProjectileWeaponUI ui) {
            this.ui = Objects.requireNonNull(ui, "ui cannot be null");
            return this;
        }

        public Builder defaultFiringMode(FiringMode defaultFiringMode) {
            this.defaultFiringMode = Objects.requireNonNull(defaultFiringMode, "defaultFiringMode cannot be null");
            return this;
        }

        public Builder ammoType(AmmoType ammoType) {
            this.ammoType = Objects.requireNonNull(ammoType, "ammoType cannot be null");
            return this;
        }

        public Builder classification(WeaponClassification classification) {
            this.classification = Objects.requireNonNull(classification, "classification cannot be null");
            return this;
        }

        public Builder dualWieldRecoilMultiplier(float multiplier) {
            this.dualWieldRecoilMultiplier = multiplier;
            return this;
        }

        public Builder dualWieldRecoilPenalty(float penalty) {
            this.dualWieldRecoilPenalty = penalty;
            return this;
        }

        public Builder dualWieldInaccuracyMultiplier(float multiplier) {
            this.dualWieldInaccuracyMultiplier = multiplier;
            return this;
        }

        public Builder dualWieldInaccuracyPenalty(float penalty) {
            this.dualWieldInaccuracyPenalty = penalty;
            return this;
        }

        public Builder heldMovementSpeedMultiplier(double multiplier) {
            this.heldMovementSpeedMultiplier = multiplier;
            return this;
        }

        public ProjectileWeaponDefinition build() {
            validate();

            return new ProjectileWeaponDefinition(
                    weaponName,
                    registryName,
                    itemProperties,
                    projectileSpeed,
                    maxAmmo,
                    maxOverheat,
                    overheatCoolingAmount,
                    overheatCoolingIntervalTicks,
                    burstRate,
                    scatterShots,
                    new EnumMap<>(stats),
                    beamStats,
                    List.copyOf(firingModes),
                    ui,
                    defaultFiringMode,
                    ammoType,
                    classification,
                    dualWieldRecoilMultiplier != null
                            ? dualWieldRecoilMultiplier
                            : defaultDualWieldPenaltyMultiplier(classification),
                    dualWieldRecoilPenalty != null
                            ? dualWieldRecoilPenalty
                            : defaultDualWieldRecoilPenalty(classification),
                    dualWieldInaccuracyMultiplier != null
                            ? dualWieldInaccuracyMultiplier
                            : defaultDualWieldPenaltyMultiplier(classification),
                    dualWieldInaccuracyPenalty != null
                            ? dualWieldInaccuracyPenalty
                            : defaultDualWieldInaccuracyPenalty(classification),
                    heldMovementSpeedMultiplier,
                    timing
            );
        }

        private void validate() {
            if (projectileSpeed <= 0f) {
                throw new IllegalStateException(weaponName + " must have projectileSpeed greater than 0.");
            }

            if (maxAmmo <= 0) {
                throw new IllegalStateException(weaponName + " must have maxAmmo greater than 0.");
            }

            if (maxOverheat < 0) {
                throw new IllegalStateException(weaponName + " must declare maxOverheat.");
            }

            if (overheatCoolingAmount < 0 || overheatCoolingIntervalTicks < 0) {
                throw new IllegalStateException(weaponName + " must declare overheat cooling.");
            }

            if (scatterShots <= 0) {
                throw new IllegalStateException(weaponName + " must have scatterShots greater than 0.");
            }

            if (firingModes.isEmpty()) {
                throw new IllegalStateException(weaponName + " must have at least one firing mode.");
            }

            if (defaultFiringMode == null) {
                throw new IllegalStateException(weaponName + " must have a default firing mode.");
            }

            if (!firingModes.contains(defaultFiringMode)) {
                throw new IllegalStateException(weaponName + " default firing mode must be included in firingModes.");
            }

            if (ui == null) {
                throw new IllegalStateException(weaponName + " must have ProjectileWeaponUI.");
            }

            if (classification == null) {
                throw new IllegalStateException(weaponName + " must have a WeaponClassification.");
            }

            if (dualWieldRecoilMultiplier != null && dualWieldRecoilMultiplier < 1.0F) {
                throw new IllegalStateException(weaponName + " dual-wield recoil multiplier cannot reduce recoil.");
            }

            if (dualWieldRecoilPenalty != null && dualWieldRecoilPenalty < 0.0F) {
                throw new IllegalStateException(weaponName + " dual-wield recoil penalty cannot be negative.");
            }

            if (dualWieldInaccuracyMultiplier != null && dualWieldInaccuracyMultiplier < 1.0F) {
                throw new IllegalStateException(weaponName + " dual-wield inaccuracy multiplier cannot improve accuracy.");
            }

            if (dualWieldInaccuracyPenalty != null && dualWieldInaccuracyPenalty < 0.0F) {
                throw new IllegalStateException(weaponName + " dual-wield inaccuracy penalty cannot be negative.");
            }

            if (heldMovementSpeedMultiplier <= 0.0 || heldMovementSpeedMultiplier > 1.0) {
                throw new IllegalStateException(weaponName + " held movement speed multiplier must be greater than 0 and at most 1.");
            }

            boolean usesGasAmmo = classification != WeaponClassification.SLUGTHROWER
                    && classification != WeaponClassification.FLECHETTE;

            if (usesGasAmmo && maxOverheat <= 0) {
                throw new IllegalStateException(weaponName + " must have maxOverheat greater than 0.");
            }

            if (usesGasAmmo && (overheatCoolingAmount <= 0 || overheatCoolingIntervalTicks <= 0)) {
                throw new IllegalStateException(weaponName + " must have positive overheat cooling values.");
            }

            if (!usesGasAmmo && maxOverheat != 0) {
                throw new IllegalStateException(weaponName + " cannot use overheat without gas ammo.");
            }

            if (!usesGasAmmo && (overheatCoolingAmount != 0 || overheatCoolingIntervalTicks != 0)) {
                throw new IllegalStateException(weaponName + " cannot cool overheat without gas ammo.");
            }

            for (FiringMode firingMode : firingModes) {
                if (firingMode == FiringMode.BEAM) {
                    if (beamStats == null) {
                        throw new IllegalStateException(weaponName + " has BEAM firing mode but no BeamWeaponStats.");
                    }

                    if (beamStats.overheatPerPulse() < 0) {
                        throw new IllegalStateException(weaponName + " has negative BEAM overheat.");
                    }

                    if (usesGasAmmo && beamStats.overheatPerPulse() <= 0) {
                        throw new IllegalStateException(weaponName + " must have positive BEAM overheat.");
                    }

                    continue;
                }

                if (!stats.containsKey(firingMode)) {
                    throw new IllegalStateException(weaponName + " is missing stats for firing mode " + firingMode + ".");
                }

                int overheatPerShot = stats.get(firingMode).overheatPerShot();
                if (overheatPerShot < 0) {
                    throw new IllegalStateException(weaponName + " has negative overheat for " + firingMode + ".");
                }

                if (firingMode == FiringMode.REPULSE && overheatPerShot != 0) {
                    throw new IllegalStateException(weaponName + " REPULSE mode cannot add overheat.");
                }

                if (!usesGasAmmo && overheatPerShot != 0) {
                    throw new IllegalStateException(weaponName + " cannot add overheat without gas ammo.");
                }

                if (usesGasAmmo && firingMode != FiringMode.REPULSE && overheatPerShot <= 0) {
                    throw new IllegalStateException(weaponName + " must have positive overheat for " + firingMode + ".");
                }
            }
        }

        private static float defaultDualWieldPenaltyMultiplier(WeaponClassification classification) {
            return switch (classification) {
                case CARBINE -> 1.15F;
                case RIFLE, REPEATER, SNIPER, SLUGTHROWER, FLECHETTE, DISRUPTOR -> 1.35F;
                case PISTOL, SCATTER -> 1.0F;
            };
        }

        private static float defaultDualWieldRecoilPenalty(WeaponClassification classification) {
            return switch (classification) {
                case CARBINE -> 0.25F;
                case RIFLE, REPEATER, SNIPER, SLUGTHROWER, FLECHETTE, DISRUPTOR -> 0.5F;
                case PISTOL, SCATTER -> 0.0F;
            };
        }

        private static float defaultDualWieldInaccuracyPenalty(WeaponClassification classification) {
            return switch (classification) {
                case CARBINE -> 0.75F;
                case RIFLE, REPEATER, SNIPER, SLUGTHROWER, FLECHETTE, DISRUPTOR -> 1.5F;
                case PISTOL, SCATTER -> 0.0F;
            };
        }
    }
}
