package net.uhhitscam.knightfall.item.custom.projectile;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/** Immutable, validated settings saved and synchronized with each debug blaster. */
public final class DebugBlasterSettings {
    public record Setting(String key, String label, String group, String initial,
                          double min, double max, boolean integer, List<String> choices) {
        public void validate(String value) {
            if (!choices.isEmpty()) {
                if (!choices.contains(value)) throw new IllegalArgumentException(label + ": choose a listed value");
                return;
            }
            try {
                double number = Double.parseDouble(value);
                if (!Double.isFinite(number) || number < min || number > max || integer && number != Math.rint(number)) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException exception) {
                throw new IllegalArgumentException(label + ": " + (integer ? "whole number " : "number ") + min + " to " + max);
            }
        }
    }

    public static final List<Setting> SETTINGS = createSettings();
    public static final Codec<DebugBlasterSettings> CODEC = Codec.unboundedMap(Codec.STRING, Codec.STRING)
            .comapFlatMap(DebugBlasterSettings::decode, DebugBlasterSettings::values);
    public static final DebugBlasterSettings DEFAULT = new DebugBlasterSettings(Map.of());

    private final Map<String, String> values;
    private final ProjectileWeaponDefinition definition;

    public DebugBlasterSettings(Map<String, String> supplied) {
        Map<String, String> normalized = new LinkedHashMap<>();
        for (Setting setting : SETTINGS) {
            String value = supplied.getOrDefault(setting.key(), setting.initial());
            setting.validate(value);
            normalized.put(setting.key(), value);
        }
        for (String key : supplied.keySet()) {
            if (!normalized.containsKey(key)) throw new IllegalArgumentException("Unknown setting: " + key);
        }
        values = Map.copyOf(normalized);
        definition = buildDefinition();
    }

    private static DataResult<DebugBlasterSettings> decode(Map<String, String> values) {
        try {
            return DataResult.success(new DebugBlasterSettings(values));
        } catch (IllegalArgumentException | IllegalStateException exception) {
            return DataResult.error(exception::getMessage);
        }
    }

    public Map<String, String> values() { return values; }
    public String value(String key) { return values.get(key); }
    public double number(String key) { return Double.parseDouble(value(key)); }
    public int integer(String key) { return (int) number(key); }
    public float decimal(String key) { return (float) number(key); }
    public ProjectileWeaponDefinition definition() { return definition; }

    private ProjectileWeaponDefinition buildDefinition() {
        FiringMode defaultMode = FiringMode.valueOf(value("default_mode"));
        List<FiringMode> modes = new ArrayList<>();
        EnumMap<FiringMode, Float> zoom = new EnumMap<>(FiringMode.class);
        EnumMap<FiringMode, ScopeTexture> scopes = new EnumMap<>(FiringMode.class);
        EnumMap<FiringMode, ProjectileWeaponStats> stats = new EnumMap<>(FiringMode.class);
        BeamWeaponStats beamStats = null;
        ProjectileWeaponTiming timing = ProjectileWeaponTiming.defaults()
                .withEquipTicks(integer("equip_ticks")).withChargeThresholdTicks(integer("charge_ticks"));
        for (FiringMode mode : FiringMode.values()) {
            String prefix = prefix(mode);
            if (value(prefix + "enabled").equals("true")) modes.add(mode);
            timing = timing.withReloadTicks(mode, integer(prefix + "reload_ticks"))
                    .withSwitchTicks(mode, integer(prefix + "switch_ticks"));
            zoom.put(mode, decimal(prefix + "zoom"));
            if (!value(prefix + "scope").equals("NONE")) {
                scopes.put(mode, ScopeTexture.valueOf(value(prefix + "scope")));
            }
            if (mode == FiringMode.BEAM) {
                beamStats = new BeamWeaponStats(decimal(prefix + "damage"), integer(prefix + "heat"));
            } else {
                stats.put(mode, new ProjectileWeaponStats(integer(prefix + "fire_interval"), decimal(prefix + "recoil"),
                        decimal(prefix + "inaccuracy"), integer(prefix + "damage"),
                        mode == FiringMode.REPULSE ? 0 : integer(prefix + "heat")));
            }
        }
        if (!modes.contains(defaultMode)) throw new IllegalArgumentException("Enable the default firing mode before applying");
        // The editor validates the fields itself and permits useful test cases such as zero heat.
        return new ProjectileWeaponDefinition(WeaponName.DEBUG_BLASTER, "debug_blaster", new Item.Properties().stacksTo(1),
                decimal("projectile_speed"), integer("max_ammo"), integer("max_heat"), integer("cooling_amount"),
                integer("cooling_interval"), integer("burst_interval"), integer("burst_count"), integer("scatter_shots"), stats, beamStats,
                List.copyOf(modes), ProjectileWeaponUI.of(CrosshairTexture.valueOf(value("crosshair")), 0.85F, zoom, scopes),
                defaultMode, AmmoType.valueOf(value("ammo_type")), WeaponClassification.valueOf(value("classification")),
                decimal("dual_recoil_multiplier"), decimal("dual_recoil_penalty"), decimal("dual_spread_multiplier"),
                decimal("dual_spread_penalty"), number("movement_speed"), timing);
    }

    public static String prefix(FiringMode mode) { return mode.name().toLowerCase(Locale.ROOT) + "."; }

    private static List<Setting> createSettings() {
        List<Setting> list = new ArrayList<>();
        numeric(list, "projectile_speed", "Projectile speed (blocks/tick)", "Weapon", "4", 0.01, 100, false);
        numeric(list, "projectile_lifetime", "Bolt lifetime (ticks)", "Weapon", "50", 1, 1200, true);
        numeric(list, "projectile_gravity", "Projectile gravity", "Weapon", "0.002", 0, 1, false);
        numeric(list, "explosion_radius", "Charged blast radius (blocks)", "Weapon", "1.5", 0.1, 32, false);
        numeric(list, "explosion_damage", "Charged blast damage", "Weapon", "3.5", 0, 1000000, false);
        numeric(list, "explosion_knockback", "Charged blast knockback", "Weapon", "0.25", 0, 100, false);
        numeric(list, "max_ammo", "Ammo capacity", "Weapon", "500", 1, 1000000, true);
        numeric(list, "max_heat", "Overheat limit (0 disables heat)", "Weapon", "100", 0, 1000000, true);
        numeric(list, "cooling_amount", "Heat removed per cooling step", "Weapon", "5", 0, 1000000, true);
        numeric(list, "cooling_interval", "Cooling interval (ticks)", "Weapon", "2", 1, 72000, true);
        numeric(list, "cooling_delay", "Cooling delay after shot (ticks)", "Weapon", "20", 0, 72000, true);
        numeric(list, "overheat_penalty", "Extra heat on overheating", "Weapon", "20", 0, 1000000, true);
        numeric(list, "burst_interval", "Burst shot interval (ticks)", "Weapon", "2", 1, 72000, true);
        numeric(list, "burst_count", "Shots per burst", "Weapon", "3", 1, 100, true);
        numeric(list, "scatter_shots", "Projectiles per scatter shot", "Weapon", "5", 1, 100, true);
        choice(list, "ammo_type", "Reload ammo / bolt color", "Weapon", "TIBANNA", AmmoType.getGasTypes().stream().map(Enum::name).sorted().toList());
        choice(list, "classification", "Weapon classification", "Weapon", "PISTOL", Arrays.stream(WeaponClassification.values())
                .filter(c -> c != WeaponClassification.SLUGTHROWER && c != WeaponClassification.FLECHETTE).map(Enum::name).toList());
        choice(list, "default_mode", "Default firing mode", "Weapon", "SEMI_AUTO", names(FiringMode.values()));
        choice(list, "crosshair", "Crosshair", "Handling", "CROSS", names(CrosshairTexture.values()));
        numeric(list, "movement_speed", "Held movement speed multiplier", "Handling", "1", 0.01, 1, false);
        numeric(list, "equip_ticks", "Equip time (ticks)", "Handling", "4", 0, 72000, true);
        numeric(list, "charge_ticks", "Charge time (ticks)", "Handling", "33", 0, 72000, true);
        numeric(list, "dual_recoil_multiplier", "Dual wield recoil multiplier", "Handling", "1", 1, 100, false);
        numeric(list, "dual_recoil_penalty", "Dual wield added recoil", "Handling", "0", 0, 1000, false);
        numeric(list, "dual_spread_multiplier", "Dual wield spread multiplier", "Handling", "1", 1, 100, false);
        numeric(list, "dual_spread_penalty", "Dual wield added inaccuracy", "Handling", "0", 0, 1000, false);
        numeric(list, "aim_recoil_multiplier", "Aiming recoil multiplier", "Handling", "0.4", 0, 10, false);
        numeric(list, "aim_spread_multiplier", "Aiming inaccuracy multiplier", "Handling", "0.7", 0, 10, false);
        for (FiringMode mode : FiringMode.values()) {
            String prefix = prefix(mode);
            String group = mode.name();
            choice(list, prefix + "enabled", "Enable this firing mode", group,
                    mode == FiringMode.SEMI_AUTO || mode == FiringMode.FULL_AUTO ? "true" : "false", List.of("false", "true"));
            if (mode != FiringMode.BEAM) {
                numeric(list, prefix + "fire_interval", "Time between shots (ticks)", group, "5", 1, 72000, true);
                numeric(list, prefix + "recoil", "Recoil", group, "1", 0, 1000, false);
                numeric(list, prefix + "inaccuracy", "Inaccuracy", group, "2", 0, 1000, false);
            }
            if (mode != FiringMode.STUN && mode != FiringMode.REPULSE) {
                numeric(list, prefix + "damage", mode == FiringMode.BEAM ? "Damage per pulse" : "Base damage (before ammo effects)", group, "5", 0, 1000000, mode != FiringMode.BEAM);
            } else {
                // These modes use effects instead of direct damage; retain the definition's unused field.
                numeric(list, prefix + "damage", "Unused direct damage", group, "0", 0, 0, true);
            }
            numeric(list, prefix + "heat", mode == FiringMode.BEAM ? "Heat per pulse" : "Heat per shot", group,
                    mode == FiringMode.REPULSE ? "0" : "5", 0, mode == FiringMode.REPULSE ? 0 : 1000000, true);
            numeric(list, prefix + "reload_ticks", "Reload time (ticks)", group, "19", 0, 72000, true);
            numeric(list, prefix + "switch_ticks", "Switch time (ticks)", group, "4", 0, 72000, true);
            numeric(list, prefix + "zoom", "Aiming FOV multiplier", group, "0.85", 0.05, 1, false);
            List<String> scopes = new ArrayList<>(List.of("NONE"));
            scopes.addAll(names(ScopeTexture.values()));
            choice(list, prefix + "scope", "Scope overlay", group, "NONE", scopes);
        }
        numeric(list, "beam_range", "Beam range (blocks)", "BEAM", "64", 1, 128, false);
        numeric(list, "beam_pulse_ticks", "Time between pulses (ticks)", "BEAM", "5", 1, 72000, true);
        numeric(list, "beam_ammo_per_second", "Beam ammo per second", "BEAM", "4", 0, 1000, false);
        numeric(list, "repulse_radius", "Repulse radius (blocks)", "REPULSE", "7", 0.1, 64, false);
        numeric(list, "repulse_strength", "Repulse strength", "REPULSE", "2.5", 0, 100, false);
        numeric(list, "stun_duration", "Stun effect duration (ticks)", "STUN", "240", 1, 72000, true);
        numeric(list, "stun_lifetime", "Stun bolt lifetime (ticks)", "STUN", "17", 1, 1200, true);
        return List.copyOf(list);
    }

    private static List<String> names(Enum<?>[] values) { return Arrays.stream(values).map(Enum::name).toList(); }
    private static void numeric(List<Setting> list, String key, String label, String group, String initial, double min, double max, boolean integer) {
        list.add(new Setting(key, label, group, initial, min, max, integer, List.of()));
    }
    private static void choice(List<Setting> list, String key, String label, String group, String initial, List<String> choices) {
        list.add(new Setting(key, label, group, initial, 0, 0, false, List.copyOf(choices)));
    }

    @Override public boolean equals(Object other) { return other instanceof DebugBlasterSettings settings && values.equals(settings.values); }
    @Override public int hashCode() { return values.hashCode(); }
}
