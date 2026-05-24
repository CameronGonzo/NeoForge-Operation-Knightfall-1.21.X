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
        int burstRate,
        int scatterShots,
        EnumMap<FiringMode, ProjectileWeaponStats> stats,
        @Nullable BeamWeaponStats beamStats,
        List<FiringMode> firingModes,
        ProjectileWeaponUI ui,
        FiringMode defaultFiringMode,
        AmmoType ammoType,
        WeaponClassification classification,
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
        private int burstRate = 2;
        private int scatterShots = 5;
        private final EnumMap<FiringMode, ProjectileWeaponStats> stats = new EnumMap<>(FiringMode.class);
        private BeamWeaponStats beamStats;
        private final List<FiringMode> firingModes = new ArrayList<>();
        private ProjectileWeaponUI ui;
        private FiringMode defaultFiringMode;
        private AmmoType ammoType = AmmoType.TIBANNA;
        private WeaponClassification classification;
        private ProjectileWeaponTiming timing = ProjectileWeaponTiming.defaults();

        private Builder(WeaponName weaponName, String registryName) {
            this.weaponName = Objects.requireNonNull(weaponName, "weaponName cannot be null");

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

        public Builder burstRate(int burstRate) {
            this.burstRate = burstRate;
            return this;
        }

        public Builder scatterShots(int scatterShots) {
            this.scatterShots = scatterShots;
            return this;
        }

        public Builder stat(FiringMode firingMode, int fireRate, float recoil, float inaccuracy, int damage) {
            return stat(firingMode, new ProjectileWeaponStats(fireRate, recoil, inaccuracy, damage));
        }

        public Builder stat(FiringMode firingMode, ProjectileWeaponStats weaponStats) {
            stats.put(
                    Objects.requireNonNull(firingMode, "firingMode cannot be null"),
                    Objects.requireNonNull(weaponStats, "weaponStats cannot be null")
            );
            return this;
        }

        public Builder beam(float damagePerPulse) {
            this.beamStats = new BeamWeaponStats(damagePerPulse);
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

        public ProjectileWeaponDefinition build() {
            validate();

            return new ProjectileWeaponDefinition(
                    weaponName,
                    registryName,
                    itemProperties,
                    projectileSpeed,
                    maxAmmo,
                    burstRate,
                    scatterShots,
                    new EnumMap<>(stats),
                    beamStats,
                    List.copyOf(firingModes),
                    ui,
                    defaultFiringMode,
                    ammoType,
                    classification,
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

            for (FiringMode firingMode : firingModes) {
                if (firingMode == FiringMode.BEAM) {
                    if (beamStats == null) {
                        throw new IllegalStateException(weaponName + " has BEAM firing mode but no BeamWeaponStats.");
                    }

                    continue;
                }

                if (!stats.containsKey(firingMode)) {
                    throw new IllegalStateException(weaponName + " is missing stats for firing mode " + firingMode + ".");
                }
            }
        }
    }
}