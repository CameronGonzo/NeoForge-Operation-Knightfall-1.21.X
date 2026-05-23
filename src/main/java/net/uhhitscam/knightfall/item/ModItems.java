package net.uhhitscam.knightfall.item;

import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.item.custom.*;
import net.uhhitscam.knightfall.item.custom.ProjectileItem;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(OperationKnightfall.MODID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    //gas items
    public static final DeferredItem<Item> GAS_CARTRIDGE = ITEMS.registerSimpleItem("gas_cartridge");
    public static final DeferredItem<Item> TIBANNA_GAS = ITEMS.registerItem("tibanna_gas",
            properties -> new GasItem(properties, 6400, 500, AmmoType.TIBANNA), new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> IONIZED_TIBANNA_GAS = ITEMS.registerItem("ionized_tibanna_gas",
            properties -> new GasItem(properties, 5200, 500, AmmoType.IONIZED_TIBANNA), new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> SPIN_SEALED_TIBANNA_GAS = ITEMS.registerItem("spin_sealed_tibanna_gas",
            properties -> new GasItem(properties, 10000, 500, AmmoType.SPIN_SEALED_TIBANNA), new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> TIBANNAX_GAS = ITEMS.registerItem("tibannax_gas",
            properties -> new GasItem(properties, 2800, 50, AmmoType.TIBANNAX), new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> SIG_GAS = ITEMS.registerItem("sig_gas",
            properties -> new GasItem(properties, 8000, 500, AmmoType.SIG), new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> MAGNETIZED_SIG_GAS = ITEMS.registerItem("magnetized_sig_gas",
            properties -> new GasItem(properties, 12800, 500, AmmoType.MAGNETIZED_SIG), new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> SKEVON = ITEMS.registerItem("skevon_gas",
            properties -> new GasItem(properties, 2000, 200, AmmoType.SKEVON), new Item.Properties().stacksTo(1));

    //projectile items
    public static final DeferredItem<Item> STEEL_SLUG = ITEMS.registerItem("steel_slug",
            properties -> new SlugItem(properties, AmmoType.STEEL_SLUG));
    public static final DeferredItem<Item> RAZOR_STEEL_SLUG = ITEMS.registerItem("razor_steel_slug",
            properties -> new SlugItem(properties, AmmoType.RAZOR_STEEL_SLUG));
    public static final DeferredItem<Item> POISON_TIPPED_STEEL_SLUG = ITEMS.registerItem("poison_tipped_steel_slug",
            properties -> new SlugItem(properties, AmmoType.POISON_TIPPED_STEEL_SLUG));
    public static final DeferredItem<Item> EXPLOSIVE_TIPPED_STEEL_SLUG = ITEMS.registerItem("explosive_tipped_steel_slug",
            properties -> new SlugItem(properties, AmmoType.EXPLOSIVE_TIPPED_STEEL_SLUG));
    public static final DeferredItem<Item> ION_TIPPED_STEEL_SLUG = ITEMS.registerItem("ion_tipped_steel_slug",
            properties -> new SlugItem(properties, AmmoType.ION_TIPPED_STEEL_SLUG));
    public static final DeferredItem<Item> PLASTIC_SLUG = ITEMS.registerItem("plastic_slug",
            properties -> new SlugItem(properties, AmmoType.PLASTIC_SLUG));
    public static final DeferredItem<Item> CERAMIC_SLUG = ITEMS.registerItem("ceramic_slug",
            properties -> new SlugItem(properties, AmmoType.CERAMIC_SLUG));
    public static final DeferredItem<Item> FLECHETTE = ITEMS.registerSimpleItem("flechette");
    public static final DeferredItem<Item> FLECHETTE_TOXIC = ITEMS.registerSimpleItem("flechette_toxic");
    public static final DeferredItem<Item> CANISTER = ITEMS.registerSimpleItem("canister");
    public static final DeferredItem<Item> FLECHETTE_CANISTER = ITEMS.registerItem("flechette_canister",
            properties -> new FlechetteCanisterItem(properties, AmmoType.FLECHETTE_CAN), new Item.Properties().stacksTo(16));
    public static final DeferredItem<Item> FLECHETTE_TOXIC_CANISTER = ITEMS.registerItem("flechette_toxic_canister",
            properties -> new FlechetteCanisterItem(properties, AmmoType.FLECHETTE_TOXIC_CAN), new Item.Properties().stacksTo(16));
    public static final DeferredItem<Item> FLECHETTE_SPREAD_CANISTER = ITEMS.registerItem("flechette_spread_canister",
            properties -> new FlechetteCanisterItem(properties, AmmoType.FLECHETTE_SPREAD_CAN), new Item.Properties().stacksTo(16));
    public static final DeferredItem<Item> FLECHETTE_TOXIC_SPREAD_CANISTER = ITEMS.registerItem("flechette_toxic_spread_canister",
            properties -> new FlechetteCanisterItem(properties, AmmoType.FLECHETTE_TOXIC_SPREAD_CAN), new Item.Properties().stacksTo(16));

    //projectile weapon items
    public static final DeferredItem<Item> _22T4 = ITEMS.register("_22t4",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.8f, 10, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 10))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.DOT,0.4f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName._22T4));
    public static final DeferredItem<Item> _62AUG2_HUNTING_RIFLE = ITEMS.register("_62aug2_hunting_rifle",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.8f, 10, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 10))), null,
                    List.of(FiringMode.SNIPER),
                    ProjectileWeaponUI.of(CrosshairTexture.CARROT, 0.25f,
                            Map.of(),
                            Map.of(FiringMode.SNIPER, ScopeTexture.CIRCLE_YELLOW)),
                    FiringMode.SNIPER, AmmoType.STEEL_SLUG, WeaponClassification.SLUGTHROWER, WeaponName._62AUG2_HUNTING_RIFLE));
    public static final DeferredItem<Item> _84U_HUNTING_RIFLE = ITEMS.register("_84u_hunting_rifle",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.9f, 30, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 12))), null,
                    List.of(FiringMode.SNIPER),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_DASH, 0.15f,
                            Map.of(),
                            Map.of(FiringMode.SNIPER, ScopeTexture.CIRCLE_GREY)),
                    FiringMode.SNIPER, AmmoType.TIBANNA, WeaponClassification.SNIPER, WeaponName._84U_HUNTING_RIFLE));
    public static final DeferredItem<Item> _434_DEATHHAMMER = ITEMS.register("_434_deathhammer",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.0f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 14))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_SMALL_LINE, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName._434_DEATHHAMMER));
    public static final DeferredItem<Item> _773_FIREPUNCHER = ITEMS.register("_773_firepuncher",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 3.1f, 10, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 17),
                            FiringMode.LAUNCHER, new ProjectileWeaponStats(0, 0f, 0f, 2),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))),
                    new BeamWeaponStats(6f),
                    List.of(FiringMode.SNIPER, FiringMode.LAUNCHER, FiringMode.STUN, FiringMode.BEAM),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_LINE, 0.45f,
                            Map.of(FiringMode.SNIPER, 0.15f),
                            Map.of(FiringMode.SNIPER, ScopeTexture.CIRCLE_BLUE_CROSS, FiringMode.LAUNCHER, ScopeTexture.CIRCLE_BLUE_CROSS, FiringMode.BEAM, ScopeTexture.CIRCLE_BLUE_CROSS)),
                    FiringMode.SNIPER, AmmoType.IONIZED_TIBANNA, WeaponClassification.SNIPER, WeaponName._773_FIREPUNCHER));
    public static final DeferredItem<Item> _785MK_FIREPUNCHERX = ITEMS.register("_785mk_firepuncherx",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 3.2f, 20, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 19))), null,
                    List.of(FiringMode.SNIPER),
                    ProjectileWeaponUI.of(CrosshairTexture._X, 0.15f,
                            Map.of(),
                            Map.of(FiringMode.SNIPER, ScopeTexture.OVAL_LONG_RED)),
                    FiringMode.SNIPER, AmmoType.TIBANNA, WeaponClassification.SNIPER, WeaponName._785MK_FIREPUNCHERX));
    public static final DeferredItem<Item> A2H = ITEMS.register("a2h",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.8f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 8))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.MORSE,0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.A2H));
    public static final DeferredItem<Item> A140 = ITEMS.register("a140",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.1f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 8))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.ANGLE_BRACKETS, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.A140));
    public static final DeferredItem<Item> A180 = ITEMS.register("a180",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.2f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 15),
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 9),
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 9),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.FULL_AUTO, FiringMode.SNIPER, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.MORSE, 0.85f,
                        Map.of(FiringMode.FULL_AUTO, 0.55f, FiringMode.SNIPER, 0.2f),
                        Map.of(FiringMode.FULL_AUTO, ScopeTexture.OVAL_SMALL_SOFT_FOCUS, FiringMode.SNIPER, ScopeTexture.OVAL_SMALL_X)),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.A180));
    public static final DeferredItem<Item> A240 = ITEMS.register("a240",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.8f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 10))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_DASH, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.A240));
    public static final DeferredItem<Item> A280 = ITEMS.register("a280",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 500, 3, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(3, 1.8f, 2.1f, 15),
                            FiringMode.BURST, new ProjectileWeaponStats(11, 1.6f, 2.8f, 9))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.BURST),
                    ProjectileWeaponUI.of(CrosshairTexture.CROSS, 0.5f,
                        Map.of(),
                        Map.of(FiringMode.SEMI_AUTO, ScopeTexture.CLASSIC, FiringMode.BURST, ScopeTexture.CLASSIC)),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.A280));
    public static final DeferredItem<Item> A280C = ITEMS.register("a280c",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 500, 3, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.BURST, new ProjectileWeaponStats(10, 1.4f, 2f, 8),
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(3, 1.4f, 3.4f, 8))), null,
                    List.of(FiringMode.BURST, FiringMode.FULL_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture._X, 0.5f,
                            Map.of(),
                            Map.of(FiringMode.BURST, ScopeTexture.CLASSIC, FiringMode.FULL_AUTO, ScopeTexture.CLASSIC)),
                    FiringMode.FULL_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.A280C));
    public static final DeferredItem<Item> A280CFE = ITEMS.register("a280cfe",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.7f, 500, 3, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 16),
                            FiringMode.BURST, new ProjectileWeaponStats(0, 0f, 0f, 7),
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 19))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.BURST, FiringMode.SNIPER),
                    ProjectileWeaponUI.of(CrosshairTexture.CROSS, 0.8f,
                            Map.of(FiringMode.BURST, 0.55f, FiringMode.SNIPER, 0.3f),
                            Map.of(FiringMode.BURST, ScopeTexture.CIRCLE_GREEN_2, FiringMode.SNIPER, ScopeTexture.CIRCLE_GREEN_2)),
                    FiringMode.BURST, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.A280CFE));
    public static final DeferredItem<Item> A295 = ITEMS.register("a295",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(5, 1.6f, 2.8f, 15),
                            FiringMode.BURST, new ProjectileWeaponStats(6, 2.2f, 3.3f, 5))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.BURST),
                    ProjectileWeaponUI.of(CrosshairTexture._X, 0.5f,
                            Map.of(),
                            Map.of(FiringMode.SEMI_AUTO, ScopeTexture.CLASSIC, FiringMode.BURST, ScopeTexture.CLASSIC)),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.A295));
    public static final DeferredItem<Item> A300 = ITEMS.register("a300",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.7f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(3, 1.6f, 4f, 6))), null,
                    List.of(FiringMode.FULL_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.SEXTUPLE_DASH, 0.5f,
                            Map.of(),
                            Map.of(FiringMode.FULL_AUTO, ScopeTexture.OVAL_SMALL_SOFT_FOCUS)),
                    FiringMode.FULL_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.A300));
    public static final DeferredItem<Item> A310 = ITEMS.register("a310",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.7f, 500, 3, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(5, 1.9f, 2f, 17),
                            FiringMode.BURST, new ProjectileWeaponStats(12, 1.7f, 2.9f, 9))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.BURST),
                    ProjectileWeaponUI.of(CrosshairTexture.ANGLE_BRACKETS, 0.5f,
                            Map.of(),
                            Map.of(FiringMode.SEMI_AUTO, ScopeTexture.OVAL_SMALL_SOFT_FOCUS, FiringMode.BURST, ScopeTexture.OVAL_SMALL_SOFT_FOCUS)),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.A310));
    public static final DeferredItem<Item> A350 = ITEMS.register("a350",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.7f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 15),
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 6))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.FULL_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.VERTICAL_ANGLE_BRACKETS, 0.5f,
                            Map.of(),
                            Map.of(FiringMode.SEMI_AUTO, ScopeTexture.OVAL_SMALL_INDENT_GREEN, FiringMode.FULL_AUTO, ScopeTexture.OVAL_SMALL_INDENT_GREEN)),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.A350));
    public static final DeferredItem<Item> AB75_BO_RIFLE = ITEMS.register("ab75_bo_rifle",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.7f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 15))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_CIRCLE, 0.7f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.AB75_BO_RIFLE));
    public static final DeferredItem<Item> ABR2_ZATO = ITEMS.register("abr2_zato",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.7f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.BURST, new ProjectileWeaponStats(0, 0f, 0f, 15))), null,
                    List.of(FiringMode.BURST),
                    ProjectileWeaponUI.of(CrosshairTexture.ANGLE_BRACKETS_SQUARE, 0.75f),
                    FiringMode.BURST, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.ABR2_ZATO));
    public static final DeferredItem<Item> AC177 = ITEMS.register("ac177",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 1.9f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 12))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_DOT, 0.85f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.AC177));
    public static final DeferredItem<Item> ACP_ARRAY = ITEMS.register("acp_array",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 1.9f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 12))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.VERTICAL_SOFT_FOCUS, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.REPEATER, WeaponName.ACP_ARRAY));
    public static final DeferredItem<Item> ACP_REPEATER = ITEMS.register("acp_repeater",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.1f, 200, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 8))), null,
                    List.of(FiringMode.FULL_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_CIRCLE, 0.75f),
                    FiringMode.FULL_AUTO, AmmoType.TIBANNA, WeaponClassification.REPEATER, WeaponName.ACP_REPEATER));
    public static final DeferredItem<Item> AMBAN_DISRUPTOR = ITEMS.register("amban_disruptor",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 4.0f, 1, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(50, 3f, 1f, 30))), null,
                    List.of(FiringMode.SNIPER),
                    ProjectileWeaponUI.of(CrosshairTexture.CARROT, 0.2f,
                            Map.of(),
                            Map.of(FiringMode.SNIPER, ScopeTexture.OVAL_LONG_RED_BLUE)),
                    FiringMode.SNIPER, AmmoType.SIG, WeaponClassification.DISRUPTOR, WeaponName.AMBAN_DISRUPTOR));
    public static final DeferredItem<Item> APACHE = ITEMS.register("apache",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 12))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.DOT, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.APACHE));
    public static final DeferredItem<Item> ASTRA40 = ITEMS.register("astra40",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 12))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_SMALL, 0.7f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.ASTRA40));
    public static final DeferredItem<Item> AVARIK = ITEMS.register("avarik",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 3.0f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 8))), null,
                    List.of(FiringMode.SNIPER),
                    ProjectileWeaponUI.of(CrosshairTexture.SEXTUPLE_DASH, 0.15f,
                            Map.of(),
                            Map.of(FiringMode.SNIPER, ScopeTexture.CIRCLE_BLUE_CROSS)),
                    FiringMode.SNIPER, AmmoType.IONIZED_TIBANNA, WeaponClassification.SNIPER, WeaponName.AVARIK));
    public static final DeferredItem<Item> AZ6 = ITEMS.register("az6",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 16))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.DOT, 0.75f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.AZ6));
    public static final DeferredItem<Item> B1NA = ITEMS.register("b1na",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 100, 3, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 8),
                            FiringMode.BURST, new ProjectileWeaponStats(0, 0f, 0f, 4))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.BURST),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_SMALL_LINE, 0.75f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.B1NA));
    public static final DeferredItem<Item> B1X = ITEMS.register("b1x",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 100, 3, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 8))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.TRIANGLE, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.B1X));
    public static final DeferredItem<Item> B22 = ITEMS.register("b22",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.0f, 10, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 8))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.TRIANGLE, 0.85f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.B22));
    public static final DeferredItem<Item> B33 = ITEMS.register("b33",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.0f, 10, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 8))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.QUAD_LINE, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.B33));
    public static final DeferredItem<Item> BAC5 = ITEMS.register("bac5",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.8f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 8))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CARROT,0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.BAC5));
    public static final DeferredItem<Item> BALNAB = ITEMS.register("balnab",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 16))), null,
                    List.of(FiringMode.SNIPER),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_LINE, 0.5f,
                            Map.of(),
                            Map.of(FiringMode.SNIPER, ScopeTexture.CIRCLE_BLUE_CROSS)),
                    FiringMode.SNIPER, AmmoType.TIBANNA, WeaponClassification.SNIPER, WeaponName.BALNAB));
    public static final DeferredItem<Item> BALNAB_SIDEARM = ITEMS.register("balnab_sidearm",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 16))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.TRIANGLE, 0.9f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.BALNAB_SIDEARM));
    public static final DeferredItem<Item> BARMST12 = ITEMS.register("barmst12",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.7f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SCATTER, new ProjectileWeaponStats(0, 0f, 0f, 6))), null,
                    List.of(FiringMode.SCATTER),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_LARGE_CROSS, 0.85f),
                    FiringMode.SCATTER, AmmoType.TIBANNA, WeaponClassification.SCATTER, WeaponName.BARMST12));
    public static final DeferredItem<Item> BATON_BLASTER = ITEMS.register("baton_blaster",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.1f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 14))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.DOT, 1f),
                    FiringMode.SEMI_AUTO, AmmoType.IONIZED_TIBANNA, WeaponClassification.PISTOL, WeaponName.BATON_BLASTER));
    public static final DeferredItem<Item> BE09 = ITEMS.register("be09",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.2f, 20, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 5))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_SMALL, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.BE09));
    public static final DeferredItem<Item> BE29 = ITEMS.register("be29",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SCATTER, new ProjectileWeaponStats(0, 0f, 0f, 10))), null,
                    List.of(FiringMode.SCATTER),
                    ProjectileWeaponUI.of(CrosshairTexture.DOT, 0.5f,
                            Map.of(),
                            Map.of(FiringMode.SCATTER, ScopeTexture.CIRCLE_GREY_DOT)),
                    FiringMode.SCATTER, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.BE29));
    public static final DeferredItem<Item> BERSERKER = ITEMS.register("berserker",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.9f, 20, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(6, 2.4f, 3.4f, 17))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CROSS, 0.7f),
                    FiringMode.SEMI_AUTO, AmmoType.STEEL_SLUG, WeaponClassification.SLUGTHROWER, WeaponName.BERSERKER));
    public static final DeferredItem<Item> BH4 = ITEMS.register("bh4",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 10),
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 3))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.FULL_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.VERTICAL_ANGLE_BRACKETS, 0.85f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.BH4));
    public static final DeferredItem<Item> BK28 = ITEMS.register("bk28",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 10),
                            FiringMode.CHARGENSHOOT, new ProjectileWeaponStats(0, 0f, 0f, 3))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.CHARGENSHOOT),
                    ProjectileWeaponUI.of(CrosshairTexture.QUAD_LINE, 0.85f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.BK28));
    public static final DeferredItem<Item> BK43 = ITEMS.register("bk43",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 15))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture._X, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.BK43));
    public static final DeferredItem<Item> BLASTER_SPEAR = ITEMS.register("blaster_spear",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.7f, 150, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 12))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.TEE, 0.7f),
                    FiringMode.SEMI_AUTO, AmmoType.IONIZED_TIBANNA, WeaponClassification.RIFLE, WeaponName.BLASTER_SPEAR));
    public static final DeferredItem<Item> BLNDRBUS = ITEMS.register("blndrbus",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SCATTER, new ProjectileWeaponStats(0, 0f, 0f, 6),
                            FiringMode.REPULSE, new ProjectileWeaponStats(0, 0f, 0f, 1),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SCATTER, FiringMode.REPULSE, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_QUAD, 0.85f),
                    FiringMode.SCATTER, AmmoType.TIBANNA, WeaponClassification.SCATTER, WeaponName.BLNDRBUS));
    public static final DeferredItem<Item> BLURRG1120 = ITEMS.register("blurrg1120",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.2f, 500, 2, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(4, 1.1f, 3f, 9),
                            FiringMode.BURST, new ProjectileWeaponStats(9, 0.9f, 4f, 4),
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(3, 0.6f, 5f, 2))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.BURST, FiringMode.FULL_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_DASH, 0.8f),
                    FiringMode.BURST, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.BLURRG1120));
    public static final DeferredItem<Item> BM107 = ITEMS.register("bm107",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 3.5f, 120, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 20),
                            FiringMode.CHARGENSHOOT, new ProjectileWeaponStats(0, 0f, 0f, 29))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.CHARGENSHOOT),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_LARGE_DASH, 0.4f,
                            Map.of(),
                            Map.of(FiringMode.SEMI_AUTO, ScopeTexture.OVAL_SMALL_VERT_DOT, FiringMode.CHARGENSHOOT, ScopeTexture.OVAL_SMALL_VERT_DOT)),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.BM107));
    public static final DeferredItem<Item> BOILER_RIFLE = ITEMS.register("boiler_rifle",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.7f, 200, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 15))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.BOWL, 0.5f,
                            Map.of(),
                            Map.of(FiringMode.SEMI_AUTO, ScopeTexture.CIRCLE_GREY_TRI_DASH)),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.BOILER_RIFLE));
    public static final DeferredItem<Item> BOONTA_BLASTER = ITEMS.register("boonta_blaster",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.0f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 11))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.DOT, 0.85f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.BOONTA_BLASTER));
    public static final DeferredItem<Item> BOWCASTER = ITEMS.register("bowcaster",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.9f, 75, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(11, 2f, 1.3f, 18),
                            FiringMode.CHARGENSHOOT, new ProjectileWeaponStats(10, 4f, 0.7f, 25))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.CHARGENSHOOT),
                    ProjectileWeaponUI.of(CrosshairTexture.QUAD_DOT, 0.6f,
                            Map.of(),
                            Map.of(FiringMode.SEMI_AUTO, ScopeTexture.CIRCLE_BLUE_CROSS_2, FiringMode.CHARGENSHOOT, ScopeTexture.CIRCLE_BLUE_CROSS_2)),
                    FiringMode.SEMI_AUTO, AmmoType.SPIN_SEALED_TIBANNA, WeaponClassification.RIFLE, WeaponName.BOWCASTER));
    public static final DeferredItem<Item> BR14 = ITEMS.register("br14",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 5))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_DOT, 0.85f),
                    FiringMode.SEMI_AUTO, AmmoType.IONIZED_TIBANNA, WeaponClassification.PISTOL, WeaponName.BR14));
    public static final DeferredItem<Item> BRYAR_RIFLE = ITEMS.register("bryar_rifle",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 15),
                            FiringMode.CHARGENSHOOT, new ProjectileWeaponStats(0, 0f, 0f, 20))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.CHARGENSHOOT),
                    ProjectileWeaponUI.of(CrosshairTexture.ANGLE_BRACKETS_SQUARE, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.BRYAR_RIFLE));
    public static final DeferredItem<Item> BSR7 = ITEMS.register("bsr7",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 15))), null,
                    List.of(FiringMode.SNIPER),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_SMALL, 0.6f),
                    FiringMode.SNIPER, AmmoType.TIBANNA, WeaponClassification.SNIPER, WeaponName.BSR7));
    public static final DeferredItem<Item> BT06 = ITEMS.register("bt06",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 15))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.BOWL, 0.85f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.BT06));
    public static final DeferredItem<Item> BX33 = ITEMS.register("bx33",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SCATTER, new ProjectileWeaponStats(0, 0f, 0f, 15))), null,
                    List.of(FiringMode.SCATTER),
                    ProjectileWeaponUI.of(CrosshairTexture.VERTICAL_SOFT_FOCUS, 0.9f),
                    FiringMode.SCATTER, AmmoType.TIBANNA, WeaponClassification.SCATTER, WeaponName.BX33));
    public static final DeferredItem<Item> BX49 = ITEMS.register("bx49",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 15),
                            FiringMode.LAUNCHER, new ProjectileWeaponStats(0, 0f, 0f, 15))), null,
                    List.of(FiringMode.SNIPER, FiringMode.LAUNCHER),
                    ProjectileWeaponUI.of(CrosshairTexture.REACTOR, 0.4f),
                    FiringMode.SNIPER, AmmoType.TIBANNA, WeaponClassification.SNIPER, WeaponName.BX49));
    public static final DeferredItem<Item> C10 = ITEMS.register("c10",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 16),
                            FiringMode.CHARGENSHOOTONRELEASE, new ProjectileWeaponStats(0, 0f, 0f, 16),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.CHARGENSHOOTONRELEASE, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_CONCENTRIC, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.C10));
    public static final DeferredItem<Item> CA87 = ITEMS.register("ca87",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 30, 0, 5,
                    new EnumMap<>(Map.of(
                            FiringMode.SCATTER, new ProjectileWeaponStats(25, 5f, 15f, 6),
                            FiringMode.REPULSE, new ProjectileWeaponStats(28, 7f, 0f, 2),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SCATTER, FiringMode.REPULSE, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_LARGE, 0.85f),
                    FiringMode.SCATTER, AmmoType.TIBANNA, WeaponClassification.SCATTER, WeaponName.CA87));
    public static final DeferredItem<Item> CB88 = ITEMS.register("cb88",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.1f, 300, 0, 5,
                    new EnumMap<>(Map.of(
                            FiringMode.SCATTER, new ProjectileWeaponStats(25, 5f, 15f, 6),
                            FiringMode.REPULSE, new ProjectileWeaponStats(28, 7f, 0f, 2),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SCATTER, FiringMode.REPULSE, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_QUAD, 0.8f),
                    FiringMode.STUN, AmmoType.TIBANNA, WeaponClassification.SCATTER, WeaponName.CB88));
    public static final DeferredItem<Item> CC19 = ITEMS.register("cc19",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.1f, 200, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 15))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.EYE, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.CC19));
    public static final DeferredItem<Item> CC420 = ITEMS.register("cc420",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 12))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CARROT, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.CC420));
    public static final DeferredItem<Item> CH60 = ITEMS.register("ch60",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.1f, 300, 0, 5,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(6, 2f, 1f, 9),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_CONCENTRIC, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.CARBINE, WeaponName.CH60));
    public static final DeferredItem<Item> CHARRIC = ITEMS.register("charric",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 12),
                            FiringMode.CHARGENSHOOT, new ProjectileWeaponStats(0, 0f, 0f, 12))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.CHARGENSHOOT),
                    ProjectileWeaponUI.of(CrosshairTexture.QUAD_LINE, 0.75f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.CHARRIC));
    public static final DeferredItem<Item> CJ9_BO_RIFLE = ITEMS.register("cj9_bo_rifle",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 11))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.QUAD_LINE, 0.7f,
                            Map.of(),
                            Map.of(FiringMode.SEMI_AUTO, ScopeTexture.CIRCLE_GREEN)),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.CJ9_BO_RIFLE));
    public static final DeferredItem<Item> CL14 = ITEMS.register("cl14",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.1f, 300, 0, 5,
                    new EnumMap<>(Map.of(
                            FiringMode.SCATTER, new ProjectileWeaponStats(25, 5f, 15f, 6),
                            FiringMode.REPULSE, new ProjectileWeaponStats(28, 7f, 0f, 2),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SCATTER, FiringMode.REPULSE, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_CIRCLE, 0.8f),
                    FiringMode.SCATTER, AmmoType.TIBANNA, WeaponClassification.SCATTER, WeaponName.CL14));
    public static final DeferredItem<Item> CORE_J3 = ITEMS.register("core_j3",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 11))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_DOT, 0.85f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.CORE_J3));
    public static final DeferredItem<Item> CORE_R5 = ITEMS.register("core_r5",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 11))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.MORSE, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.CORE_R5));
    public static final DeferredItem<Item> CORE_U12 = ITEMS.register("core_u12",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 11))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_SMALL_LINE, 0.75f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.CORE_U12));
    public static final DeferredItem<Item> CORPO_RIFLE = ITEMS.register("corpo_rifle",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 150, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 13))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_LARGE_DASH, 0.6f,
                            Map.of(),
                            Map.of(FiringMode.SEMI_AUTO, ScopeTexture.CIRCLE_GREY_DOT)),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.CORPO_RIFLE));
    public static final DeferredItem<Item> CP5 = ITEMS.register("cp5",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.1f, 300, 0, 5,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(6, 2f, 1f, 9),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_QUAD, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.CARBINE, WeaponName.CP5));
    public static final DeferredItem<Item> CP6 = ITEMS.register("cp6",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.1f, 300, 0, 4,
                    new EnumMap<>(Map.of(
                            FiringMode.SCATTER, new ProjectileWeaponStats(25, 5f, 15f, 6),
                            FiringMode.REPULSE, new ProjectileWeaponStats(28, 7f, 0f, 2),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SCATTER, FiringMode.REPULSE, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_LARGE_CROSS, 0.8f),
                    FiringMode.SCATTER, AmmoType.TIBANNA, WeaponClassification.SCATTER, WeaponName.CP6));
    public static final DeferredItem<Item> CQ29 = ITEMS.register("cq29",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.1f, 300, 0, 5,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(6, 2f, 1f, 9),
                            FiringMode.REPULSE, new ProjectileWeaponStats(28, 7f, 0f, 2),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.REPULSE, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_CONCENTRIC, 0.8f),
                    FiringMode.STUN, AmmoType.TIBANNA, WeaponClassification.CARBINE, WeaponName.CQ29));
    public static final DeferredItem<Item> CR2 = ITEMS.register("cr2",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(1, 0.5f, 9.4f, 2),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.FULL_AUTO, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.SOFT_FOCUS, 0.85f),
                    FiringMode.FULL_AUTO, AmmoType.IONIZED_TIBANNA, WeaponClassification.PISTOL, WeaponName.CR2));
    public static final DeferredItem<Item> CS14 = ITEMS.register("cs14",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.2f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 4),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.DOT, 0.85f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.CS14));
    public static final DeferredItem<Item> CT33 = ITEMS.register("ct33",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.1f, 300, 0, 4,
                    new EnumMap<>(Map.of(
                            FiringMode.BURST, new ProjectileWeaponStats(6, 2f, 1f, 9),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.BURST, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.QUAD_DOT, 0.8f),
                    FiringMode.BURST, AmmoType.TIBANNA, WeaponClassification.CARBINE, WeaponName.CT33));
    public static final DeferredItem<Item> CW24 = ITEMS.register("cw24",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 150, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 14))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_DOT, 0.7f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.CW24));
    public static final DeferredItem<Item> CW76 = ITEMS.register("cw76",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 16))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.MORSE, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.CW76));
    public static final DeferredItem<Item> CYCLER_RIFLE = ITEMS.register("cycler_rifle",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 3.9f, 15, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(25, 3f, 1f, 18))), null,
                    List.of(FiringMode.SNIPER),
                    ProjectileWeaponUI.of(CrosshairTexture.TEE, 0.15f,
                            Map.of(),
                            Map.of(FiringMode.SNIPER, ScopeTexture.CIRCLE_GREY_CROSS)),
                    FiringMode.SNIPER, AmmoType.SPIN_SEALED_TIBANNA, WeaponClassification.SNIPER, WeaponName.CYCLER_RIFLE));
    public static final DeferredItem<Item> CZERKA_19 = ITEMS.register("czerka_19",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 3.9f, 15, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(25, 3f, 1f, 18))), null,
                    List.of(FiringMode.SNIPER),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_CONCENTRIC, 0.25f),
                    FiringMode.SNIPER, AmmoType.STEEL_SLUG, WeaponClassification.SLUGTHROWER, WeaponName.CZERKA_19));
    public static final DeferredItem<Item> CZERKA_ADVENTURER = ITEMS.register("czerka_adventurer",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 4.0f, 20, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 19))), null,
                    List.of(FiringMode.SNIPER),
                    ProjectileWeaponUI.of(CrosshairTexture.REACTOR, 0.15f,
                            Map.of(),
                            Map.of(FiringMode.SNIPER, ScopeTexture.CIRCLE_YELLOW)),
                    FiringMode.SNIPER, AmmoType.STEEL_SLUG, WeaponClassification.SLUGTHROWER, WeaponName.CZERKA_ADVENTURER));
    public static final DeferredItem<Item> DC12U = ITEMS.register("dc12u",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 15))),
                    new BeamWeaponStats(2.5f),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.BEAM),
                    ProjectileWeaponUI.of(CrosshairTexture.BOWL, 0.7f),
                    FiringMode.SEMI_AUTO, AmmoType.IONIZED_TIBANNA, WeaponClassification.RIFLE, WeaponName.DC12U));
    public static final DeferredItem<Item> DC15A = ITEMS.register("dc15a",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(7, 1.6f, 1f, 17),
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(3, 1f, 3f, 5),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.FULL_AUTO, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.CROSS, 0.4f,
                            Map.of(),
                            Map.of(FiringMode.SEMI_AUTO, ScopeTexture.OVAL_SMALL_X, FiringMode.FULL_AUTO, ScopeTexture.OVAL_SMALL_X)),
                    FiringMode.SEMI_AUTO, AmmoType.IONIZED_TIBANNA, WeaponClassification.RIFLE, WeaponName.DC15A));
    public static final DeferredItem<Item> DC15LE = ITEMS.register("dc15le",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(3, 1.7f, 4.7f, 6))), null,
                    List.of(FiringMode.FULL_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_LARGE, 0.5f,
                            Map.of(),
                            Map.of(FiringMode.FULL_AUTO, ScopeTexture.OVAL_SMALL_X)),
                    FiringMode.FULL_AUTO, AmmoType.IONIZED_TIBANNA, WeaponClassification.RIFLE, WeaponName.DC15LE));
    public static final DeferredItem<Item> DC15S_CARBINE = ITEMS.register("dc15s_carbine",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(10, 1.7f, 3.5f, 12),
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(5, 1.3f, 4.4f, 5),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.FULL_AUTO, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.CROSS, 0.7f),
                    FiringMode.SEMI_AUTO, AmmoType.IONIZED_TIBANNA, WeaponClassification.CARBINE, WeaponName.DC15S_CARBINE));
    public static final DeferredItem<Item> DC15S_SIDEARM = ITEMS.register("dc15s_sidearm",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 17, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(3, 1.4f, 4f, 6),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_LINE, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.IONIZED_TIBANNA, WeaponClassification.PISTOL, WeaponName.DC15S_SIDEARM));
    public static final DeferredItem<Item> DC15X = ITEMS.register("dc15x",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.8f, 6, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 23),
                            FiringMode.CHARGENSHOOTONRELEASE, new ProjectileWeaponStats(0, 0f, 0f, 17))), null,
                    List.of(FiringMode.SNIPER, FiringMode.CHARGENSHOOTONRELEASE),
                    ProjectileWeaponUI.of(CrosshairTexture.QUAD_LINE, 0.2f,
                            Map.of(),
                            Map.of(FiringMode.SNIPER, ScopeTexture.OVAL_SMALL_FANCY, FiringMode.CHARGENSHOOTONRELEASE, ScopeTexture.OVAL_SMALL_FANCY)),
                    FiringMode.SNIPER, AmmoType.IONIZED_TIBANNA, WeaponClassification.SNIPER, WeaponName.DC15X));
    public static final DeferredItem<Item> DC17 = ITEMS.register("dc17",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(3, 0.6f, 6f, 14),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.DOT, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.IONIZED_TIBANNA, WeaponClassification.PISTOL, WeaponName.DC17));
    public static final DeferredItem<Item> DC17M = ITEMS.register("dc17m",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 5),
                            FiringMode.CHARGENSHOOT, new ProjectileWeaponStats(0, 0f, 0f, 25),
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 18),
                            FiringMode.LAUNCHER, new ProjectileWeaponStats(0, 0f, 0f, 8),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.FULL_AUTO, FiringMode.CHARGENSHOOT, FiringMode.SNIPER, FiringMode.LAUNCHER, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_LINE, 0.75f,
                            Map.of(FiringMode.CHARGENSHOOT, 0.25f, FiringMode.SNIPER, 0.2f, FiringMode.LAUNCHER, 0.8f),
                            Map.of(FiringMode.SNIPER, ScopeTexture.CIRCLE_GREY_BLUE, FiringMode.CHARGENSHOOT, ScopeTexture.CIRCLE_GREY_BLUE)),
                    FiringMode.FULL_AUTO, AmmoType.IONIZED_TIBANNA, WeaponClassification.REPEATER, WeaponName.DC17M));
    public static final DeferredItem<Item> DC17S = ITEMS.register("dc17s",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(3, 1.3f, 5f, 16),
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(2, 1.8f, 3.6f, 7))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.FULL_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.TRIANGLE, 0.8f),
                    FiringMode.FULL_AUTO, AmmoType.IONIZED_TIBANNA, WeaponClassification.PISTOL, WeaponName.DC17S));
    public static final DeferredItem<Item> DC19 = ITEMS.register("dc19",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 10, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 15))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.ANGLE_BRACKETS_SQUARE, 0.7f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNAX, WeaponClassification.CARBINE, WeaponName.DC19));
    public static final DeferredItem<Item> DCX = ITEMS.register("dcx",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 15))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.TRIANGLE, 0.7f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.DCX));
    public static final DeferredItem<Item> DE10 = ITEMS.register("de10",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.7f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 14))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture._X, 0.7f,
                            Map.of(),
                            Map.of(FiringMode.SEMI_AUTO, ScopeTexture.CIRCLE_GREY_TRI_DASH)),
                    FiringMode.SEMI_AUTO, AmmoType.IONIZED_TIBANNA, WeaponClassification.PISTOL, WeaponName.DE10));
    public static final DeferredItem<Item> DEACTIVATOR = ITEMS.register("deactivator",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.7f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.VERTICAL_SOFT_FOCUS, 1f),
                    FiringMode.STUN, AmmoType.IONIZED_TIBANNA, WeaponClassification.PISTOL, WeaponName.DEACTIVATOR));
    public static final DeferredItem<Item> DEFTECH = ITEMS.register("deftech",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.8f, 300, 3, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 17),
                            FiringMode.BURST, new ProjectileWeaponStats(0, 0f, 0f, 7))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.BURST),
                    ProjectileWeaponUI.of(CrosshairTexture.SOFT_FOCUS, 0.5f,
                            Map.of(),
                            Map.of(FiringMode.SEMI_AUTO, ScopeTexture.CIRCLE_BLUE_CROSS_2, FiringMode.BURST, ScopeTexture.CIRCLE_BLUE_CROSS_2)),
                    FiringMode.SEMI_AUTO, AmmoType.SIG, WeaponClassification.RIFLE, WeaponName.DEFTECH));
    public static final DeferredItem<Item> DER4 = ITEMS.register("der4",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.8f, 300, 3, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 17))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_SMALL, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.DER4));
    public static final DeferredItem<Item> DFD1 = ITEMS.register("dfd1",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.8f, 6, 0, 8,
                    new EnumMap<>(Map.of(
                            FiringMode.SCATTER, new ProjectileWeaponStats(2, 2f, 12f, 18))), null,
                    List.of(FiringMode.SCATTER),
                    ProjectileWeaponUI.of(CrosshairTexture.SEXTUPLE_DASH, 0.75f),
                    FiringMode.SCATTER, AmmoType.FLECHETTE_CAN, WeaponClassification.FLECHETTE, WeaponName.DFD1));
    public static final DeferredItem<Item> DFQ91 = ITEMS.register("dfq91",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.8f, 6, 0, 8,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(2, 2f, 12f, 18))), null,
                    List.of(FiringMode.SNIPER),
                    ProjectileWeaponUI.of(CrosshairTexture.TEE, 0.6f),
                    FiringMode.SNIPER, AmmoType.TIBANNA, WeaponClassification.SNIPER, WeaponName.DFQ91));
    public static final DeferredItem<Item> DG29 = ITEMS.register("dg29",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 7))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.EYE, 0.7f,
                            Map.of(),
                            Map.of(FiringMode.SEMI_AUTO, ScopeTexture.CIRCLE_GREY_DOT)),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.DG29));
    public static final DeferredItem<Item> DH16 = ITEMS.register("dh16",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 7))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.MORSE, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.DH16));
    public static final DeferredItem<Item> DH17 = ITEMS.register("dh17",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 500, 2, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(11, 2.7f, 2f, 17),
                            FiringMode.BURST, new ProjectileWeaponStats(14, 1.4f, 2.7f, 7),
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(4, 2.1f, 3.9f, 5),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.BURST, FiringMode.FULL_AUTO, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.CROSS, 0.75f,
                            Map.of(),
                            Map.of(FiringMode.SEMI_AUTO, ScopeTexture.CLASSIC_BUBBLED, FiringMode.BURST, ScopeTexture.CLASSIC_BUBBLED, FiringMode.FULL_AUTO, ScopeTexture.CLASSIC_BUBBLED)),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.DH17));
    public static final DeferredItem<Item> DH23 = ITEMS.register("dh23",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 14))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_DASH, 0.85f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.DH23));
    public static final DeferredItem<Item> DH42 = ITEMS.register("dh42",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 14),
                            FiringMode.BURST, new ProjectileWeaponStats(0, 0f, 0f, 14))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.BURST),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_DOT, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.DH42));
    public static final DeferredItem<Item> DH447 = ITEMS.register("dh447",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.7f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 17))), null,
                    List.of(FiringMode.SNIPER),
                    ProjectileWeaponUI.of(CrosshairTexture.REACTOR, 0.25f,
                            Map.of(),
                            Map.of(FiringMode.SNIPER, ScopeTexture.CIRCLE_GREEN_2)),
                    FiringMode.SNIPER, AmmoType.TIBANNA, WeaponClassification.SNIPER, WeaponName.DH447));
    public static final DeferredItem<Item> DL11 = ITEMS.register("dl11",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.7f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 17))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_LINE, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.DL11));
    public static final DeferredItem<Item> DL18 = ITEMS.register("dl18",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 12),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.HILL, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.DL18));
    public static final DeferredItem<Item> DL21 = ITEMS.register("dl21",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 0),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.CROSS, 0.7f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.DL21));
    public static final DeferredItem<Item> DL23 = ITEMS.register("dl23",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.TRIANGLE, 0.55f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.DL23));
    public static final DeferredItem<Item> DL44 = ITEMS.register("dl44",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(7, 28f, 2.9f, 16),
                            FiringMode.SNIPER, new ProjectileWeaponStats(15, 36f, 2.2f, 20))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.SNIPER),
                    ProjectileWeaponUI.of(CrosshairTexture.CROSS, 0.75f,
                            Map.of(FiringMode.SNIPER, 0.35f),
                            Map.of(FiringMode.SNIPER, ScopeTexture.CIRCLE_GREY_CROSS)),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.DL44));
    public static final DeferredItem<Item> DLS12 = ITEMS.register("dls12",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 14))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.ANGLE_BRACKETS_SQUARE, 0.7f),
                    FiringMode.SEMI_AUTO, AmmoType.IONIZED_TIBANNA, WeaponClassification.CARBINE, WeaponName.DLS12));
    public static final DeferredItem<Item> DLT15 = ITEMS.register("dlt15",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.2f, 200, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(2, 1.2f, 3.7f, 8))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_LARGE_DASH, 0.55f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.DLT15));
    public static final DeferredItem<Item> DLT18 = ITEMS.register("dlt18",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.2f, 200, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(2, 1.2f, 3.7f, 8))), null,
                    List.of(FiringMode.FULL_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_CONCENTRIC, 0.75f),
                    FiringMode.FULL_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.DLT18));
    public static final DeferredItem<Item> DLT19 = ITEMS.register("dlt19",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(2, 0.9f, 2.9f, 9))), null,
                    List.of(FiringMode.FULL_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_CIRCLE, 0.75f),
                    FiringMode.FULL_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.DLT19));
    public static final DeferredItem<Item> DLT19D = ITEMS.register("dlt19d",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 10, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(10, 2.5f, 1.4f, 14))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.MORSE, 0.5f,
                            Map.of(),
                            Map.of(FiringMode.SEMI_AUTO, ScopeTexture.OVAL_SMALL_VERT_DOT)),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.DLT19D));
    public static final DeferredItem<Item> DLT19X = ITEMS.register("dlt19x",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 40, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(9, 8f, 1f, 18))), null,
                    List.of(FiringMode.SNIPER),
                    ProjectileWeaponUI.of(CrosshairTexture.HILL, 0.15f,
                            Map.of(),
                            Map.of(FiringMode.SNIPER, ScopeTexture.CLASSIC_BUBBLED_GREEN)),
                    FiringMode.SNIPER, AmmoType.TIBANNA, WeaponClassification.SNIPER, WeaponName.DLT19X));
    public static final DeferredItem<Item> DLT20A = ITEMS.register("dlt20a",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(11, 6.3f, 1.4f, 17))),
                    new BeamWeaponStats(3.5f),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.BEAM),
                    ProjectileWeaponUI.of(CrosshairTexture.HILL, 0.5f,
                            Map.of(),
                            Map.of(FiringMode.SEMI_AUTO, ScopeTexture.CIRCLE_BLUE_BRACKET, FiringMode.BEAM, ScopeTexture.CIRCLE_BLUE_BRACKET)),
                    FiringMode.SEMI_AUTO, AmmoType.SPIN_SEALED_TIBANNA, WeaponClassification.RIFLE, WeaponName.DLT20A));
    public static final DeferredItem<Item> DN_BOLT_CASTER = ITEMS.register("dn_bolt_caster",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.0f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.CHARGENSHOOT, new ProjectileWeaponStats(0, 0f, 0f, 20))), null,
                    List.of(FiringMode.CHARGENSHOOT),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_LARGE_CROSS, 0.6f),
                    FiringMode.CHARGENSHOOT, AmmoType.IONIZED_TIBANNA, WeaponClassification.DISRUPTOR, WeaponName.DN_BOLT_CASTER));
    public static final DeferredItem<Item> DP23 = ITEMS.register("dp23",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 13),
                            FiringMode.CHARGENSHOOT, new ProjectileWeaponStats(0, 0f, 0f, 18))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.CHARGENSHOOT),
                    ProjectileWeaponUI.of(CrosshairTexture.ANGLE_BRACKETS, 0.75f),
                    FiringMode.SEMI_AUTO, AmmoType.IONIZED_TIBANNA, WeaponClassification.RIFLE, WeaponName.DP23));
    public static final DeferredItem<Item> DRESSELLIAN_PROJECTILE_RIFLE = ITEMS.register("dressellian_projectile_rifle",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 3.8f, 15, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 19))), null,
                    List.of(FiringMode.SNIPER),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_SMALL_LINE, 0.65f),
                    FiringMode.SNIPER, AmmoType.STEEL_SLUG, WeaponClassification.SLUGTHROWER, WeaponName.DRESSELLIAN_PROJECTILE_RIFLE));
    public static final DeferredItem<Item> DT12 = ITEMS.register("dt12",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.2f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(7, 1.1f, 4.9f, 9))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_DOT, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.DT12));
    public static final DeferredItem<Item> DT15 = ITEMS.register("dt15",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.1f, 200, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 15))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.QUAD_LINE, 0.75f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.DT15));
    public static final DeferredItem<Item> DT29 = ITEMS.register("dt29",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.8f, 6, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 16))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_SMALL, 0.85f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.DT29));
    public static final DeferredItem<Item> DT57 = ITEMS.register("dt57",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.1f, 400, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 8))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CROSS, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.DT57));
    public static final DeferredItem<Item> DUJ3 = ITEMS.register("duj3",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.1f, 400, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 8))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.MORSE, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.DUJ3));
    public static final DeferredItem<Item> DUL4 = ITEMS.register("dul4",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.1f, 400, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 8))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CROSS, 0.7f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.DUL4));
    public static final DeferredItem<Item> DX2 = ITEMS.register("dx2",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.1f, 400, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.CHARGENSHOOT, new ProjectileWeaponStats(0, 0f, 0f, 8))), null,
                    List.of(FiringMode.CHARGENSHOOT),
                    ProjectileWeaponUI.of(CrosshairTexture.VERTICAL_SOFT_FOCUS, 0.8f),
                    FiringMode.CHARGENSHOOT, AmmoType.TIBANNA, WeaponClassification.DISRUPTOR, WeaponName.DX2));
    public static final DeferredItem<Item> DX13 = ITEMS.register("dx13",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 17),
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 7),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.FULL_AUTO, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.VERTICAL_ANGLE_BRACKETS, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.DX13));
    public static final DeferredItem<Item> DXR6 = ITEMS.register("dxr6",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.CHARGENSHOOT, new ProjectileWeaponStats(0, 0f, 0f, 8))), null,
                    List.of(FiringMode.CHARGENSHOOT),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_LARGE_CROSS, 0.7f),
                    FiringMode.CHARGENSHOOT, AmmoType.TIBANNA, WeaponClassification.DISRUPTOR, WeaponName.DXR6));
    public static final DeferredItem<Item> E5 = ITEMS.register("e5",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.1f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(6, 1.9f, 4f, 6),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.CROSS, 0.75f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.E5));
    public static final DeferredItem<Item> E5_BX = ITEMS.register("e5_bx",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(9, 2f, 3.7f, 12),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_DASH, 0.75f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.E5_BX));
    public static final DeferredItem<Item> E5_CARBINE = ITEMS.register("e5_carbine",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(9, 2f, 3.7f, 12),
                            FiringMode.BURST, new ProjectileWeaponStats(9, 2f, 3.7f, 12))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.BURST),
                    ProjectileWeaponUI.of(CrosshairTexture.CROSS, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.CARBINE, WeaponName.E5_CARBINE));
    public static final DeferredItem<Item> E5_CE = ITEMS.register("e5_ce",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.2f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(8, 2.7f, 3.4f, 13),
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(3, 1.5f, 5f, 5))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.FULL_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.BOWL, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.E5_CE));
    public static final DeferredItem<Item> E5C = ITEMS.register("e5c",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.0f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(2, 0.6f, 5.6f, 4))), null,
                    List.of(FiringMode.FULL_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_CIRCLE, 0.85f),
                    FiringMode.FULL_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.E5C));
    public static final DeferredItem<Item> E5R = ITEMS.register("e5r",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(2, 0f, 2f, 4))), null,
                    List.of(FiringMode.FULL_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.EYE, 0.25f,
                            Map.of(),
                            Map.of(FiringMode.FULL_AUTO, ScopeTexture.CIRCLE_RED_ANGLED_BRACKET)),
                    FiringMode.FULL_AUTO, AmmoType.TIBANNA, WeaponClassification.REPEATER, WeaponName.E5R));
    public static final DeferredItem<Item> E5S = ITEMS.register("e5s",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.8f, 4, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 4))), null,
                    List.of(FiringMode.SNIPER),
                    ProjectileWeaponUI.of(CrosshairTexture.EYE, 0.25f,
                            Map.of(),
                            Map.of(FiringMode.SNIPER, ScopeTexture.CIRCLE_GREEN_YELLOW)),
                    FiringMode.SNIPER, AmmoType.TIBANNA, WeaponClassification.SNIPER, WeaponName.E5S));
    public static final DeferredItem<Item> E5T = ITEMS.register("e5t",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.8f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 7))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.MORSE, 0.7f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.E5T));
    public static final DeferredItem<Item> E10 = ITEMS.register("e10",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 500, 3, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 8),
                            FiringMode.BURST, new ProjectileWeaponStats(0, 0f, 0f, 5))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.BURST),
                    ProjectileWeaponUI.of(CrosshairTexture._X, 0.75f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.E10));
    public static final DeferredItem<Item> E10_5 = ITEMS.register("e10_5",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 9))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture._X, 0.75f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.E10_5));
    public static final DeferredItem<Item> E10R = ITEMS.register("e10r",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.8f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 16))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CROSS, 0.6f,
                            Map.of(),
                            Map.of(FiringMode.SEMI_AUTO, ScopeTexture.CIRCLE_GREEN_REACTOR)),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.E10R));
    public static final DeferredItem<Item> E11_CARBINE = ITEMS.register("e11_carbine",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 8),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.CROSS, 0.7f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.CARBINE, WeaponName.E11_CARBINE));
    public static final DeferredItem<Item> E11_RIFLE = ITEMS.register("e11_rifle",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.7f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(10, 1f, 5f, 13),
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(5, 0.8f, 7.8f, 6),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.FULL_AUTO, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.CROSS, 0.75f,
                            Map.of(),
                            Map.of(FiringMode.SEMI_AUTO, ScopeTexture.CIRCLE_BLUE, FiringMode.FULL_AUTO, ScopeTexture.CIRCLE_BLUE)),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.E11_RIFLE));
    public static final DeferredItem<Item> E11B = ITEMS.register("e11b",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.7f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 9),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.CROSS, 0.5f,
                            Map.of(),
                            Map.of(FiringMode.SEMI_AUTO, ScopeTexture.CIRCLE_BLUE)),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.E11B));
    public static final DeferredItem<Item> E11D = ITEMS.register("e11d",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(7, 2.2f, 2f, 16),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_DASH, 0.7f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.CARBINE, WeaponName.E11D));
    public static final DeferredItem<Item> E11P = ITEMS.register("e11p",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(7, 2.2f, 2f, 16))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.MORSE, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.E11P));
    public static final DeferredItem<Item> E11S = ITEMS.register("e11s",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 3.0f, 6, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 17))), null,
                    List.of(FiringMode.SNIPER),
                    ProjectileWeaponUI.of(CrosshairTexture.QUAD_LINE, 0.2f,
                            Map.of(),
                            Map.of(FiringMode.SNIPER, ScopeTexture.CIRCLE_RED_DOT)),
                    FiringMode.SNIPER, AmmoType.TIBANNA, WeaponClassification.SNIPER, WeaponName.E11S));
    public static final DeferredItem<Item> E11T = ITEMS.register("e11t",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 3.0f, 6, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 17))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.BOWL, 0.7f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.E11T));
    public static final DeferredItem<Item> E17D = ITEMS.register("e17d",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.8f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 16))), null,
                    List.of(FiringMode.SNIPER),
                    ProjectileWeaponUI.of(CrosshairTexture.HILL, 0.15f,
                            Map.of(),
                            Map.of(FiringMode.SNIPER, ScopeTexture.CLASSIC_BUBBLED)),
                    FiringMode.SNIPER, AmmoType.TIBANNA, WeaponClassification.SNIPER, WeaponName.E17D));
    public static final DeferredItem<Item> E22 = ITEMS.register("e22",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 500, 3, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 9),
                            FiringMode.BURST, new ProjectileWeaponStats(0, 0f, 0f, 6),
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 4))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.BURST, FiringMode.FULL_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.VERTICAL_ANGLE_BRACKETS, 0.75f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.E22));
    public static final DeferredItem<Item> E44 = ITEMS.register("e44",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 17))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.VERTICAL_SOFT_FOCUS, 0.5f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.E44));
    public static final DeferredItem<Item> E9V = ITEMS.register("e9v",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.7f, 200, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 16),
                            FiringMode.CHARGENSHOOT, new ProjectileWeaponStats(0, 0f, 0f, 19),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.CHARGENSHOOT, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.SOFT_FOCUS, 0.5f,
                            Map.of(),
                            Map.of(FiringMode.SEMI_AUTO, ScopeTexture.CIRCLE_RED_DOT, FiringMode.CHARGENSHOOT, ScopeTexture.CIRCLE_RED_DOT)),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNAX, WeaponClassification.RIFLE, WeaponName.E9V));
    public static final DeferredItem<Item> EC17 = ITEMS.register("ec17",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.2f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(5, 1f, 8f, 6),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_SMALL, 0.85f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.EC17));
    public static final DeferredItem<Item> EE3 = ITEMS.register("ee3",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.7f, 300, 2, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(7, 2f, 2.4f, 15),
                            FiringMode.BURST, new ProjectileWeaponStats(15, 1f, 2.7f, 6))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.BURST),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_DASH, 0.65f,
                            Map.of(),
                            Map.of(FiringMode.SEMI_AUTO, ScopeTexture.CIRCLE_GREY_TRI_DASH, FiringMode.BURST, ScopeTexture.CIRCLE_GREY_TRI_DASH)),
                    FiringMode.BURST, AmmoType.TIBANNA, WeaponClassification.CARBINE, WeaponName.EE3));
    public static final DeferredItem<Item> EE4 = ITEMS.register("ee4",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 500, 1, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.BURST, new ProjectileWeaponStats(0, 0f, 0f, 8))), null,
                    List.of(FiringMode.BURST),
                    ProjectileWeaponUI.of(CrosshairTexture.SOFT_FOCUS, 0.7f),
                    FiringMode.BURST, AmmoType.TIBANNA, WeaponClassification.CARBINE, WeaponName.EE4));
    public static final DeferredItem<Item> EL5 = ITEMS.register("el5",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(5, 1.8f, 4f, 13))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.EYE, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.EL5));
    public static final DeferredItem<Item> EL244 = ITEMS.register("el244",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(5, 1.8f, 4f, 13))), null,
                    List.of(FiringMode.SNIPER),
                    ProjectileWeaponUI.of(CrosshairTexture.HILL, 0.65f),
                    FiringMode.SNIPER, AmmoType.TIBANNA, WeaponClassification.SNIPER, WeaponName.EL244));
    public static final DeferredItem<Item> ELG3A = ITEMS.register("elg3a",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(5, 1.8f, 4f, 13),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.CARROT, 0.7f),
                    FiringMode.SEMI_AUTO, AmmoType.SPIN_SEALED_TIBANNA, WeaponClassification.PISTOL, WeaponName.ELG3A));
    public static final DeferredItem<Item> EMG2 = ITEMS.register("emg2",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 150, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 17))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_SMALL, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.EMG2));
    public static final DeferredItem<Item> ESB3 = ITEMS.register("esb3",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 500, 2, 3,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(5, 1.8f, 4f, 13),
                            FiringMode.BURST, new ProjectileWeaponStats(5, 1.8f, 4f, 13))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.BURST),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_DASH, 0.65f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.CARBINE, WeaponName.ESB3));
//    public static final DeferredItem<Item> EWEB = ITEMS.register("eweb",
//            () -> new BlasterItem(new Item.Properties().stacksTo(1), 4.0f, 500, 0, 1,
//                    new EnumMap<>(Map.of(
//                            FiringMode.FULL_AUTO, new BlasterStats(0, 0f, 0f, 8))), null,
//                    List.of(FiringMode.FULL_AUTO),
//                    FiringMode.FULL_AUTO, GasType.TIBANNA, Classification.REPEATER, BlasterName.EWEB));
    public static final DeferredItem<Item> F2L = ITEMS.register("f2l",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.1f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 14))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_SMALL_LINE, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.F2L));
    public static final DeferredItem<Item> F4L = ITEMS.register("f4l",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 5, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 17))), null,
                    List.of(FiringMode.SNIPER),
                    ProjectileWeaponUI.of(CrosshairTexture.EYE, 0.7f),
                    FiringMode.SNIPER, AmmoType.TIBANNA, WeaponClassification.SNIPER, WeaponName.F4L));
    public static final DeferredItem<Item> F38G = ITEMS.register("f38g",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 18))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_SMALL_LINE, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.F38G));
    public static final DeferredItem<Item> FC1_FLECHETTE_LAUNCHER = ITEMS.register("fc1_flechette_launcher",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.9f, 6, 0, 8,
                    new EnumMap<>(Map.of(
                            FiringMode.SCATTER, new ProjectileWeaponStats(2, 8f, 12f, 19))), null,
                    List.of(FiringMode.SCATTER),
                    ProjectileWeaponUI.of(CrosshairTexture.QUAD_DOT, 0.85f),
                    FiringMode.SCATTER, AmmoType.FLECHETTE_CAN, WeaponClassification.FLECHETTE, WeaponName.FC1_FLECHETTE_LAUNCHER));
    public static final DeferredItem<Item> FC29 = ITEMS.register("fc29",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 7),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_DASH, 0.85f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.FC29));
    public static final DeferredItem<Item> FLITE37 = ITEMS.register("flite37",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SCATTER, new ProjectileWeaponStats(0, 0f, 0f, 18))), null,
                    List.of(FiringMode.SCATTER),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_LARGE_CROSS, 0.85f),
                    FiringMode.SCATTER, AmmoType.TIBANNA, WeaponClassification.SCATTER, WeaponName.FLITE37));
    public static final DeferredItem<Item> FN57 = ITEMS.register("fn57",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 200, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 14))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CARROT, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.FN57));
    public static final DeferredItem<Item> FP45 = ITEMS.register("fp45",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.0f, 15, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 10))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.VERTICAL_SOFT_FOCUS, 0.9f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.FP45));
//    public static final DeferredItem<Item> FWEB = ITEMS.register("fweb",
//            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.0f, 15, 0, 1,
//                    new EnumMap<>(Map.of(
//                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 10))), null,
//                    List.of(FiringMode.FULL_AUTO),
//                    FiringMode.FULL_AUTO, AmmoType.TIBANNA, WeaponClassification.REPEATER, WeaponName.FWEB));
    public static final DeferredItem<Item> FWG5 = ITEMS.register("fwg5",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.0f, 15, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 10))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture._X, 0.85f),
                    FiringMode.SEMI_AUTO, AmmoType.FLECHETTE_CAN, WeaponClassification.FLECHETTE, WeaponName.FWG5));
    public static final DeferredItem<Item> FWG7 = ITEMS.register("fwg7",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.0f, 15, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 10))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CROSS, 0.85f),
                    FiringMode.SEMI_AUTO, AmmoType.FLECHETTE_CAN, WeaponClassification.FLECHETTE, WeaponName.FWG7));
    public static final DeferredItem<Item> G433 = ITEMS.register("g433",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.0f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 8))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.ANGLE_BRACKETS_SQUARE, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.CARBINE, WeaponName.G433));
    public static final DeferredItem<Item> GA3R = ITEMS.register("ga3r",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.0f, 15, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 10))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.TRIANGLE, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.GA3R));
    public static final DeferredItem<Item> GALAAR15 = ITEMS.register("galaar15",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(9, 1.8f, 2.1f, 13))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CROSS, 0.7f),
                    FiringMode.SEMI_AUTO, AmmoType.SIG, WeaponClassification.CARBINE, WeaponName.GALAAR15));
    public static final DeferredItem<Item> GALAR90 = ITEMS.register("galar90",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.9f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 15))), null,
                    List.of(FiringMode.SNIPER),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_LINE, 0.25f,
                            Map.of(),
                            Map.of(FiringMode.SNIPER, ScopeTexture.CIRCLE_BLUE_CROSS_3)),
                    FiringMode.SNIPER, AmmoType.SIG, WeaponClassification.SNIPER, WeaponName.GALAR90));
    public static final DeferredItem<Item> GE36 = ITEMS.register("ge36",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 3.0f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 19))), null,
                    List.of(FiringMode.SNIPER),
                    ProjectileWeaponUI.of(CrosshairTexture.QUAD_LINE, 0.2f,
                            Map.of(),
                            Map.of(FiringMode.SNIPER, ScopeTexture.OCTAGONAL_RED)),
                    FiringMode.SNIPER, AmmoType.TIBANNA, WeaponClassification.SNIPER, WeaponName.GE36));
//    public static final DeferredItem<Item> GI9 = ITEMS.register("gi9",
//            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 300, 0, 1,
//                    new EnumMap<>(Map.of(
//                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 14))), null,
//                    List.of(FiringMode.SEMI_AUTO),
//                    FiringMode.SEMI_AUTO, AmmoType.IONIZED_TIBANNA, WeaponClassification.RIFLE, WeaponName.GI9));
    public static final DeferredItem<Item> GL77 = ITEMS.register("gl77",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 14))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.EYE, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.IONIZED_TIBANNA, WeaponClassification.PISTOL, WeaponName.GL77));
    public static final DeferredItem<Item> GLX_FIRELANCE = ITEMS.register("glx_firelance",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 14),
                            FiringMode.BURST, new ProjectileWeaponStats(0, 0f, 0f, 14),
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 14))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.BURST, FiringMode.FULL_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.BOWL, 0.7f),
                    FiringMode.BURST, AmmoType.TIBANNA, WeaponClassification.CARBINE, WeaponName.GLX_FIRELANCE));
    public static final DeferredItem<Item> GM46 = ITEMS.register("gm46",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 14),
                            FiringMode.CHARGENSHOOT, new ProjectileWeaponStats(0, 0f, 0f, 14))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.CHARGENSHOOT),
                    ProjectileWeaponUI.of(CrosshairTexture.TRIANGLE, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.REPEATER, WeaponName.GM46));
    public static final DeferredItem<Item> GR4_ST = ITEMS.register("gr4_st",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 14))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.EYE, 0.85f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.GR4_ST));
    public static final DeferredItem<Item> GR13 = ITEMS.register("gr13",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 14))), null,
                    List.of(FiringMode.FULL_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_LARGE_DASH, 0.85f),
                    FiringMode.FULL_AUTO, AmmoType.TIBANNA, WeaponClassification.REPEATER, WeaponName.GR13));
    public static final DeferredItem<Item> GRN4 = ITEMS.register("grn4",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 14))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture._X, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.GRN4));
    public static final DeferredItem<Item> GRS1 = ITEMS.register("grs1",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 14),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0),
                            FiringMode.LAUNCHER, new ProjectileWeaponStats(0, 0f, 0f, 14))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN, FiringMode.LAUNCHER),
                    ProjectileWeaponUI.of(CrosshairTexture.SOFT_FOCUS, 0.75f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.GRS1));
    public static final DeferredItem<Item> HB9 = ITEMS.register("hb9",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 17))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.QUAD_DOT, 0.65f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.HB9));
    public static final DeferredItem<Item> HF94 = ITEMS.register("hf94",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 17))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.ANGLE_BRACKETS, 0.75f,
                            Map.of(),
                            Map.of(FiringMode.SEMI_AUTO, ScopeTexture.CIRCLE_GREY_DOT)),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.HF94));
    public static final DeferredItem<Item> HT9 = ITEMS.register("ht9",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.7f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 6),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_DOT, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.HT9));
    public static final DeferredItem<Item> IB94 = ITEMS.register("ib94",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 200, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(5, 1.2f, 2.8f, 15))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.MORSE, 0.75f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.IB94));
    public static final DeferredItem<Item> ION_STUNNER = ITEMS.register("ion_stunner",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 200, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.VERTICAL_SOFT_FOCUS, 1f),
                    FiringMode.STUN, AmmoType.IONIZED_TIBANNA, WeaponClassification.PISTOL, WeaponName.ION_STUNNER));
    public static final DeferredItem<Item> IQA11 = ITEMS.register("iqa11",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.8f, 7, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 16))), null,
                    List.of(FiringMode.SNIPER),
                    ProjectileWeaponUI.of(CrosshairTexture.TEE, 0.4f,
                            Map.of(),
                            Map.of(FiringMode.SNIPER, ScopeTexture.OVAL_LONG_ORANGE)),
                    FiringMode.SNIPER, AmmoType.IONIZED_TIBANNA, WeaponClassification.SNIPER, WeaponName.IQA11));
    public static final DeferredItem<Item> J19_BO_RIFLE = ITEMS.register("j19_bo_rifle",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.8f, 7, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 16))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.ANGLE_BRACKETS, 0.7f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.J19_BO_RIFLE));
    public static final DeferredItem<Item> JEZALI_CYCLER_RIFLE = ITEMS.register("jezali_cycler_rifle",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 3.3f, 20, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(20, 7f, 2f, 20))), null,
                    List.of(FiringMode.SNIPER),
                    ProjectileWeaponUI.of(CrosshairTexture.TEE, 0.15f,
                            Map.of(),
                            Map.of(FiringMode.SNIPER, ScopeTexture.CIRCLE_GREY_CROSS)),
                    FiringMode.SNIPER, AmmoType.STEEL_SLUG, WeaponClassification.SLUGTHROWER, WeaponName.JEZALI_CYCLER_RIFLE));
    public static final DeferredItem<Item> JND41 = ITEMS.register("jnd41",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 15),
                            FiringMode.CHARGENSHOOT, new ProjectileWeaponStats(0, 0f, 0f, 19))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.CHARGENSHOOT),
                    ProjectileWeaponUI.of(CrosshairTexture.ANGLE_BRACKETS_SQUARE, 0.5f,
                            Map.of(),
                            Map.of(FiringMode.SEMI_AUTO, ScopeTexture.CIRCLE_GOLD_V, FiringMode.CHARGENSHOOT, ScopeTexture.CIRCLE_GOLD_V)),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.JND41));
    public static final DeferredItem<Item> K13 = ITEMS.register("k13",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 15))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_SMALL_LINE, 0.85f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.K13));
    public static final DeferredItem<Item> K16_BRYAR_PISTOL = ITEMS.register("k16_bryar_pistol",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.2f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 12),
                            FiringMode.CHARGENSHOOTONRELEASE, new ProjectileWeaponStats(0, 0f, 0f, 17))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.CHARGENSHOOTONRELEASE),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_LINE, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.K16_BRYAR_PISTOL));
    public static final DeferredItem<Item> K63R = ITEMS.register("k63r",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.2f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 7))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.VERTICAL_ANGLE_BRACKETS, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.K63R));
    public static final DeferredItem<Item> KA74 = ITEMS.register("ka74",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 14),
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 5))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.FULL_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CROSS, 0.5f,
                            Map.of(),
                            Map.of(FiringMode.SEMI_AUTO, ScopeTexture.CIRCLE_GREEN_2, FiringMode.FULL_AUTO, ScopeTexture.CIRCLE_GREEN_2)),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.KA74));
    public static final DeferredItem<Item> KINETICBLAST = ITEMS.register("kineticblast",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 14))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture._X, 0.7f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.KINETICBLAST));
    public static final DeferredItem<Item> KISTEER_1284 = ITEMS.register("kisteer_1284",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.9f, 20, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 18))), null,
                    List.of(FiringMode.SNIPER),
                    ProjectileWeaponUI.of(CrosshairTexture.SEXTUPLE_DASH, 0.25f,
                            Map.of(),
                            Map.of(FiringMode.SNIPER, ScopeTexture.CIRCLE_RED_BRACKET)),
                    FiringMode.SNIPER, AmmoType.STEEL_SLUG, WeaponClassification.SLUGTHROWER, WeaponName.KISTEER_1284));
    public static final DeferredItem<Item> KL9 = ITEMS.register("kl9",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.1f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 14))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CARROT, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.KL9));
    public static final DeferredItem<Item> KM9 = ITEMS.register("km9",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 14))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.DOT, 0.9f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.KM9));
    public static final DeferredItem<Item> KUEGET_LN21 = ITEMS.register("kueget_ln21",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.2f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 12),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture._X, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.KUEGET_LN21));
    public static final DeferredItem<Item> KYD21 = ITEMS.register("kyd21",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 75, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 10))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_SMALL, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.KYD21));
    public static final DeferredItem<Item> L5 = ITEMS.register("l5",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 14))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.SEXTUPLE_DASH, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.L5));
    public static final DeferredItem<Item> L60 = ITEMS.register("l60",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 16))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.QUAD_DOT, 0.7f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.L60));
    public static final DeferredItem<Item> LJ40 = ITEMS.register("lj40",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 16))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.TRIANGLE, 0.7f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.CARBINE, WeaponName.LJ40));
    public static final DeferredItem<Item> LJ50 = ITEMS.register("lj50",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 16))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_CIRCLE, 0.7f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.LJ50));
    public static final DeferredItem<Item> LL30 = ITEMS.register("ll30",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.7f, 200, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(8, 0.8f, 2f, 16),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_SMALL_LINE, 0.75f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.LL30));
    public static final DeferredItem<Item> LP_LAW = ITEMS.register("lp_law",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.7f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(8, 0.8f, 2f, 6))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.MORSE, 0.75f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.LP_LAW));
    public static final DeferredItem<Item> LS150 = ITEMS.register("ls150",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.7f, 200, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(8, 0.8f, 2f, 16))), null,
                    List.of(FiringMode.FULL_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_CONCENTRIC, 0.75f),
                    FiringMode.FULL_AUTO, AmmoType.TIBANNA, WeaponClassification.REPEATER, WeaponName.LS150));
    public static final DeferredItem<Item> LV7C = ITEMS.register("lv7c",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 200, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 15),
                            FiringMode.CHARGENSHOOT, new ProjectileWeaponStats(0, 0f, 0f, 20))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.CHARGENSHOOT),
                    ProjectileWeaponUI.of(CrosshairTexture.TRIANGLE, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.LV7C));
    public static final DeferredItem<Item> LW896 = ITEMS.register("lw896",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 11))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.QUAD_LINE, 0.75f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.LW896));
    public static final DeferredItem<Item> M12 = ITEMS.register("m12",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 16),
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 8))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.FULL_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.BOWL, 0.7f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.M12));
    public static final DeferredItem<Item> M32 = ITEMS.register("m32",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 9))), null,
                    List.of(FiringMode.FULL_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_LARGE, 0.8f),
                    FiringMode.FULL_AUTO, AmmoType.TIBANNA, WeaponClassification.REPEATER, WeaponName.M32));
    public static final DeferredItem<Item> M41 = ITEMS.register("m41",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 400, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 16),
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 9))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.FULL_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.ANGLE_BRACKETS, 0.75f),
                    FiringMode.FULL_AUTO, AmmoType.TIBANNA, WeaponClassification.REPEATER, WeaponName.M41));
    public static final DeferredItem<Item> M45 = ITEMS.register("m45",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 11))), null,
                    List.of(FiringMode.FULL_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_LARGE_CROSS, 0.65f,
                            Map.of(),
                            Map.of(FiringMode.FULL_AUTO, ScopeTexture.CIRCLE_BLUE)),
                    FiringMode.FULL_AUTO, AmmoType.TIBANNA, WeaponClassification.REPEATER, WeaponName.M45));
    public static final DeferredItem<Item> M55 = ITEMS.register("m55",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.7f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 10))), null,
                    List.of(FiringMode.FULL_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.SEXTUPLE_DASH, 0.8f),
                    FiringMode.FULL_AUTO, AmmoType.TIBANNA, WeaponClassification.REPEATER, WeaponName.M55));
    public static final DeferredItem<Item> M61 = ITEMS.register("m61",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 19))), null,
                    List.of(FiringMode.FULL_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.REACTOR, 0.85f),
                    FiringMode.FULL_AUTO, AmmoType.TIBANNA, WeaponClassification.REPEATER, WeaponName.M61));
    public static final DeferredItem<Item> MARG_MCM = ITEMS.register("marg_mcm",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 15))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_DOT, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.MARG_MCM));
    public static final DeferredItem<Item> MK3T = ITEMS.register("mk3t",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 2f, 7))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_SMALL_LINE, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.MK3T));
    public static final DeferredItem<Item> MK_II_PALADIN = ITEMS.register("mk_ii_paladin",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 16))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.BOWL, 0.65f),
                    FiringMode.SEMI_AUTO, AmmoType.IONIZED_TIBANNA, WeaponClassification.RIFLE, WeaponName.MK_II_PALADIN));
    public static final DeferredItem<Item> MODEL_57 = ITEMS.register("model_57",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.7f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 15),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_DASH, 0.75f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.MODEL_57));
//    public static final DeferredItem<Item> MORTAR = ITEMS.register("mortar",
//            () -> new BlasterItem(new Item.Properties().stacksTo(1), 3.0f, 300, 0, 1,
//                    new EnumMap<>(Map.of(
//                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 15))), null,
//                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
//                    FiringMode.SEMI_AUTO, GasType.IONIZED_TIBANNA, Classification.PISTOL, BlasterName.MORTAR));
    public static final DeferredItem<Item> MOTTO_MK_4 = ITEMS.register("motto_mk_4",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 200, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 15),
                            FiringMode.REPULSE, new ProjectileWeaponStats(0, 0f, 0f, 1),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.REPULSE, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_QUAD, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.MOTTO_MK_4));
    public static final DeferredItem<Item> MSD32 = ITEMS.register("msd32",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 15),
                            FiringMode.CHARGENSHOOT, new ProjectileWeaponStats(0, 0f, 0f, 20))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.CHARGENSHOOT),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_LINE, 0.85f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.DISRUPTOR, WeaponName.MSD32));
    public static final DeferredItem<Item> MW20_BRYAR_PISTOL = ITEMS.register("mw20_bryar_pistol",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 15),
                            FiringMode.CHARGENSHOOTONRELEASE, new ProjectileWeaponStats(0, 0f, 0f, 1),
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 18))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.CHARGENSHOOTONRELEASE, FiringMode.SNIPER),
                    ProjectileWeaponUI.of(CrosshairTexture.QUAD_LINE, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.MW20_BRYAR_PISTOL));
    public static final DeferredItem<Item> MWC35C = ITEMS.register("mwc35c",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 17),
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 9))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.FULL_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.VERTICAL_ANGLE_BRACKETS, 0.8f),
                    FiringMode.FULL_AUTO, AmmoType.TIBANNA, WeaponClassification.REPEATER, WeaponName.MWC35C));
    public static final DeferredItem<Item> NEO_CRUSADER_RIFLE = ITEMS.register("neo_crusader_rifle",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(7, 15f, 3.8f, 17),
                            FiringMode.CHARGENSHOOT, new ProjectileWeaponStats(7, 23f, 3.2f, 20))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.CHARGENSHOOT),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_SMALL_LINE, 0.7f,
                            Map.of(),
                            Map.of(FiringMode.SEMI_AUTO, ScopeTexture.CIRCLE_GREY_DOT, FiringMode.CHARGENSHOOT, ScopeTexture.CIRCLE_GREY_DOT)),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.NEO_CRUSADER_RIFLE));
    public static final DeferredItem<Item> NIGHT_STINGER = ITEMS.register("night_stinger",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.9f, 5, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 19),
                            FiringMode.CHARGENSHOOT, new ProjectileWeaponStats(0, 0f, 0f, 15))), null,
                    List.of(FiringMode.SNIPER, FiringMode.CHARGENSHOOT),
                    ProjectileWeaponUI.of(CrosshairTexture.TEE, 0.25f,
                            Map.of(),
                            Map.of(FiringMode.SNIPER, ScopeTexture.OVAL_LONG_RED_THERMAL, FiringMode.CHARGENSHOOT, ScopeTexture.OVAL_LONG_RED_THERMAL)),
                    FiringMode.SNIPER, AmmoType.TIBANNA, WeaponClassification.SNIPER, WeaponName.NIGHT_STINGER));
    public static final DeferredItem<Item> NOSLO19 = ITEMS.register("noslo19",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.9f, 5, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 19))), null,
                    List.of(FiringMode.SNIPER, FiringMode.CHARGENSHOOT),
                    ProjectileWeaponUI.of(CrosshairTexture.ANGLE_BRACKETS, 0.2f),
                    FiringMode.SNIPER, AmmoType.TIBANNA, WeaponClassification.SLUGTHROWER, WeaponName.NOSLO19));
    public static final DeferredItem<Item> NT242 = ITEMS.register("nt242",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 3.0f, 75, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 18))),
                    new BeamWeaponStats(7f),
                    List.of(FiringMode.SNIPER, FiringMode.BEAM),
                    ProjectileWeaponUI.of(CrosshairTexture.HILL, 0.15f,
                            Map.of(),
                            Map.of(FiringMode.SNIPER, ScopeTexture.CLASSIC_BUBBLED_GREEN, FiringMode.BEAM, ScopeTexture.CLASSIC_BUBBLED_GREEN)),
                    FiringMode.SNIPER, AmmoType.SPIN_SEALED_TIBANNA, WeaponClassification.SNIPER, WeaponName.NT242));
    public static final DeferredItem<Item> OK98 = ITEMS.register("ok98",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 1000, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 15))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.MORSE, 0.75f,
                            Map.of(),
                            Map.of(FiringMode.SEMI_AUTO, ScopeTexture.CIRCLE_GREY_BLUE)),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.CARBINE, WeaponName.OK98));
    public static final DeferredItem<Item> OUTLAND_RIFLE = ITEMS.register("outland_rifle",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.8f, 8, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 16))), null,
                    List.of(FiringMode.SNIPER),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_LARGE_DASH, 0.25f,
                            Map.of(),
                            Map.of(FiringMode.SNIPER, ScopeTexture.CIRCLE_GREY_CROSS)),
                    FiringMode.SNIPER, AmmoType.STEEL_SLUG, WeaponClassification.SLUGTHROWER, WeaponName.OUTLAND_RIFLE));
    public static final DeferredItem<Item> P224 = ITEMS.register("p224",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 150, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 15))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CARROT, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.P224));
    public static final DeferredItem<Item> PANIC_PISTOL = ITEMS.register("panic_pistol",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 5, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 9))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CARROT, 0.9f),
                    FiringMode.SEMI_AUTO, AmmoType.STEEL_SLUG, WeaponClassification.SLUGTHROWER, WeaponName.PANIC_PISTOL));
    public static final DeferredItem<Item> PD44 = ITEMS.register("pd44",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.9f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 6))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CARROT, 0.85f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.PD44));
    public static final DeferredItem<Item> PK23 = ITEMS.register("pk23",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.9f, 40, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 19))), null,
                    List.of(FiringMode.SNIPER),
                    ProjectileWeaponUI.of(CrosshairTexture.TEE, 0.25f,
                            Map.of(),
                            Map.of(FiringMode.SNIPER, ScopeTexture.CIRCLE_GREEN)),
                    FiringMode.SNIPER, AmmoType.TIBANNA, WeaponClassification.SNIPER, WeaponName.PK23));
    public static final DeferredItem<Item> POWER_5 = ITEMS.register("power_5",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 14),
                            FiringMode.CHARGENSHOOTONRELEASE, new ProjectileWeaponStats(0, 0f, 0f, 18))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.CHARGENSHOOTONRELEASE),
                    ProjectileWeaponUI.of(CrosshairTexture._X, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.POWER_5));
    public static final DeferredItem<Item> PR9 = ITEMS.register("pr9",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 14))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CARROT, 0.4f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.PR9));
    public static final DeferredItem<Item> PRD8 = ITEMS.register("prd8",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 6))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture._X, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.PRD8));
    public static final DeferredItem<Item> PRD58 = ITEMS.register("prd58",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 9))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.ANGLE_BRACKETS_SQUARE, 0.6f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.PRD58));
    public static final DeferredItem<Item> PRD62 = ITEMS.register("prd62",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 8))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.ANGLE_BRACKETS, 0.7f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.PRD62));
    public static final DeferredItem<Item> PRECISIONX = ITEMS.register("precisionx",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 14))), null,
                    List.of(FiringMode.SNIPER),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_LARGE_DASH, 0.15f),
                    FiringMode.SNIPER, AmmoType.TIBANNA, WeaponClassification.SNIPER, WeaponName.PRECISIONX));
    public static final DeferredItem<Item> Q2 = ITEMS.register("q2",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.0f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(11, 1.3f, 6f, 11))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_SMALL, 0.85f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.Q2));
    public static final DeferredItem<Item> QUARREN_RIFLE = ITEMS.register("quarren_rifle",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 15))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_LINE, 0.7f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.QUARREN_RIFLE));
    public static final DeferredItem<Item> R88 = ITEMS.register("r88",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 15),
                            FiringMode.REPULSE, new ProjectileWeaponStats(0, 0f, 0f, 15),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0),
                            FiringMode.LAUNCHER, new ProjectileWeaponStats(0, 0f, 0f, 15))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.REPULSE, FiringMode.STUN, FiringMode.LAUNCHER),
                    ProjectileWeaponUI.of(CrosshairTexture.VERTICAL_ANGLE_BRACKETS, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.SNIPER, WeaponName.R88));
    public static final DeferredItem<Item> RD2B = ITEMS.register("rd2b",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 7))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_SMALL, 0.9f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.RD2B));
    public static final DeferredItem<Item> RD6 = ITEMS.register("rd6",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 8))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.VERTICAL_SOFT_FOCUS, 0.9f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.RD6));
    public static final DeferredItem<Item> RECIPROCATING_QUAD_BLASTER_CANNON = ITEMS.register("reciprocating_quad_blaster_cannon",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 12))), null,
                    List.of(FiringMode.FULL_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.QUAD_DOT, 0.9f),
                    FiringMode.FULL_AUTO, AmmoType.IONIZED_TIBANNA, WeaponClassification.REPEATER, WeaponName.RECIPROCATING_QUAD_BLASTER_CANNON));
    public static final DeferredItem<Item> RELBY_K23 = ITEMS.register("relby_k23",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 12),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_SMALL_LINE, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.RELBY_K23));
    public static final DeferredItem<Item> RELBY_K25 = ITEMS.register("relby_k25",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 12),
                            FiringMode.BURST, new ProjectileWeaponStats(0, 0f, 0f, 12),
                            FiringMode.CHARGENSHOOT, new ProjectileWeaponStats(0, 0f, 0f, 12))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.CHARGENSHOOT, FiringMode.BURST),
                    ProjectileWeaponUI.of(CrosshairTexture.EYE, 0.5f,
                            Map.of(FiringMode.SEMI_AUTO, 0.75f),
                            Map.of()),
                    FiringMode.BURST, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.RELBY_K25));
    public static final DeferredItem<Item> RELBY_V10 = ITEMS.register("relby_v10",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.8f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.CHARGENSHOOTONRELEASE, new ProjectileWeaponStats(0, 0f, 0f, 22),
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 18),
                            FiringMode.LAUNCHER, new ProjectileWeaponStats(0, 0f, 0f, 4))), null,
                    List.of(FiringMode.CHARGENSHOOTONRELEASE, FiringMode.SNIPER, FiringMode.LAUNCHER),
                    ProjectileWeaponUI.of(CrosshairTexture.REACTOR, 0.2f,
                            Map.of(FiringMode.CHARGENSHOOTONRELEASE, 0.15f, FiringMode.LAUNCHER, 0.8f),
                            Map.of(FiringMode.CHARGENSHOOTONRELEASE, ScopeTexture.CIRCLE_GREEN_REACTOR, FiringMode.SNIPER, ScopeTexture.CIRCLE_GREEN_REACTOR)),
                    FiringMode.SNIPER, AmmoType.TIBANNA, WeaponClassification.SNIPER, WeaponName.RELBY_V10));
    public static final DeferredItem<Item> RENEGADE = ITEMS.register("renegade",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 17))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_QUAD, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.RENEGADE));
    public static final DeferredItem<Item> RG4D = ITEMS.register("rg4d",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.2f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 7))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CROSS, 0.75f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.RG4D));
    public static final DeferredItem<Item> RIG420 = ITEMS.register("rig420",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 14))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_SMALL_LINE, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.RIG420));
    public static final DeferredItem<Item> RK2P = ITEMS.register("rk2p",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 13))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_DOT, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.RK2P));
    public static final DeferredItem<Item> RK3 = ITEMS.register("rk3",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 75, 2, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(4, 0.9f, 3.6f, 9),
                            FiringMode.BURST, new ProjectileWeaponStats(12, 1.1f, 4.2f, 4),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.BURST, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.DOT, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.RK3));
    public static final DeferredItem<Item> RLR_MK_II = ITEMS.register("rlr_mk_ii",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 13),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_SMALL, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.RLR_MK_II));
    public static final DeferredItem<Item> RM7 = ITEMS.register("rm7",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.2f, 200, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 15))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CARROT, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.RM7));
    public static final DeferredItem<Item> RM_1P = ITEMS.register("rm_1p",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 16))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CARROT, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.RM_1P));
    public static final DeferredItem<Item> RSKF44 = ITEMS.register("rskf44",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 16))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.QUAD_LINE, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.RSKF44));
    public static final DeferredItem<Item> RT97C = ITEMS.register("rt97c",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(2, 0.9f, 4.5f, 5))), null,
                    List.of(FiringMode.FULL_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_LARGE, 0.6f,
                            Map.of(),
                            Map.of(FiringMode.FULL_AUTO, ScopeTexture.CLASSIC_BUBBLED)),
                    FiringMode.FULL_AUTO, AmmoType.TIBANNA, WeaponClassification.REPEATER, WeaponName.RT97C));
    public static final DeferredItem<Item> S2S = ITEMS.register("s2s",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(2, 0.9f, 4.5f, 5))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.DOT, 0.9f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.S2S));
    public static final DeferredItem<Item> S5 = ITEMS.register("s5",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 20, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 13),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture._X, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.SPIN_SEALED_TIBANNA, WeaponClassification.PISTOL, WeaponName.S5));
    public static final DeferredItem<Item> S3_MK_5 = ITEMS.register("s3_mk_5",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 14))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.TRIANGLE, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.S3_MK_5));
    public static final DeferredItem<Item> S195 = ITEMS.register("s195",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 12),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.MORSE, 0.75f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.S195));
    public static final DeferredItem<Item> SACROS_K11 = ITEMS.register("sacros_k11",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 150, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 16))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.DOT, 0.75f),
                    FiringMode.SEMI_AUTO, AmmoType.SIG, WeaponClassification.PISTOL, WeaponName.SACROS_K11));
    public static final DeferredItem<Item> SATINES_LAMENT = ITEMS.register("satines_lament",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 100, 2, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(6, 3f, 5.2f, 17),
                            FiringMode.BURST, new ProjectileWeaponStats(10, 2f, 7.5f, 7),
                            FiringMode.CHARGENSHOOT, new ProjectileWeaponStats(6, 7f, 4.1f, 20),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.BURST, FiringMode.CHARGENSHOOT, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.DOT, 0.75f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.SATINES_LAMENT));
    public static final DeferredItem<Item> SE14C = ITEMS.register("se14c",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 200, 2, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(3, 0.6f, 5.1f, 7))), null,
                    List.of(FiringMode.FULL_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_DASH, 0.75f),
                    FiringMode.FULL_AUTO, AmmoType.TIBANNA, WeaponClassification.REPEATER, WeaponName.SE14C));
    public static final DeferredItem<Item> SE14R = ITEMS.register("se14r",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 400, 2, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(8, 1.5f, 4f, 9),
                            FiringMode.BURST, new ProjectileWeaponStats(16, 0.7f, 6.4f, 6),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.BURST, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.VERTICAL_SOFT_FOCUS, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.SE14R));
    public static final DeferredItem<Item> SE9V = ITEMS.register("se9v",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.2f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 12))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.SOFT_FOCUS, 0.85f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.SE9V));
    public static final DeferredItem<Item> SEDGLEYS_MK_5 = ITEMS.register("sedgleys_mk_5",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 250, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 17))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_LINE, 0.75f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.SEDGLEYS_MK_5));
    public static final DeferredItem<Item> SEL3 = ITEMS.register("sel3",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 7))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.SOFT_FOCUS, 0.75f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.SEL3));
    public static final DeferredItem<Item> SER5 = ITEMS.register("ser5",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 250, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 17))), null,
                    List.of(FiringMode.SNIPER),
                    ProjectileWeaponUI.of(CrosshairTexture.HILL, 0.5f),
                    FiringMode.SNIPER, AmmoType.TIBANNA, WeaponClassification.SNIPER, WeaponName.SER5));
    public static final DeferredItem<Item> SETTLERS_STUN = ITEMS.register("settlers_stun",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 250, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.SOFT_FOCUS, 1f),
                    FiringMode.STUN, AmmoType.IONIZED_TIBANNA, WeaponClassification.PISTOL, WeaponName.SETTLERS_STUN));
    public static final DeferredItem<Item> SHARD3A = ITEMS.register("shard3a",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 11))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.QUAD_LINE, 0.85f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.SHARD3A));
    public static final DeferredItem<Item> SK32 = ITEMS.register("sk32",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 250, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 15),
                            FiringMode.CHARGENSHOOT, new ProjectileWeaponStats(0, 0f, 0f, 18))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.CHARGENSHOOT),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_SMALL_LINE, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.MAGNETIZED_SIG, WeaponClassification.PISTOL, WeaponName.SK32));
    public static final DeferredItem<Item> SNUB_BLASTER = ITEMS.register("snub_blaster",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 250, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 10))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.VERTICAL_SOFT_FOCUS, 0.85f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.SNUB_BLASTER));
    public static final DeferredItem<Item> SNUB_SCATTER = ITEMS.register("snub_scatter",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 250, 0, 5,
                    new EnumMap<>(Map.of(
                            FiringMode.SCATTER, new ProjectileWeaponStats(0, 0f, 0f, 10))), null,
                    List.of(FiringMode.SCATTER),
                    ProjectileWeaponUI.of(CrosshairTexture.VERTICAL_SOFT_FOCUS, 0.85f),
                    FiringMode.SCATTER, AmmoType.TIBANNA, WeaponClassification.SCATTER, WeaponName.SNUB_SCATTER));
    public static final DeferredItem<Item> SNUBBLE = ITEMS.register("snubble",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.1f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 14),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_DOT, 0.75f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.CARBINE, WeaponName.SNUBBLE));
    public static final DeferredItem<Item> SONIC_BLASTER = ITEMS.register("sonic_blaster",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.1f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 14))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_LARGE_CROSS, 0.75f),
                    FiringMode.SEMI_AUTO, AmmoType.SPIN_SEALED_TIBANNA, WeaponClassification.RIFLE, WeaponName.SONIC_BLASTER));
    public static final DeferredItem<Item> SONIC_STUNNER = ITEMS.register("sonic_stunner",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.1f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 14))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.SOFT_FOCUS, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.SPIN_SEALED_TIBANNA, WeaponClassification.PISTOL, WeaponName.SONIC_STUNNER));
    public static final DeferredItem<Item> SS410 = ITEMS.register("ss410",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.1f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 14))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_LINE, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.SS410));
    public static final DeferredItem<Item> SWE1 = ITEMS.register("swe1",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.1f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 11))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_LARGE_DASH, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.IONIZED_TIBANNA, WeaponClassification.CARBINE, WeaponName.SWE1));
    public static final DeferredItem<Item> SWE2 = ITEMS.register("swe2",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.1f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 14))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_LARGE_CROSS, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.SWE2));
    public static final DeferredItem<Item> SX21 = ITEMS.register("sx21",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 150, 0, 5,
                    new EnumMap<>(Map.of(
                            FiringMode.SCATTER, new ProjectileWeaponStats(0, 0f, 7f, 6))), null,
                    List.of(FiringMode.SCATTER),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_CONCENTRIC, 0.85f),
                    FiringMode.SCATTER, AmmoType.TIBANNA, WeaponClassification.SCATTER, WeaponName.SX21));
    public static final DeferredItem<Item> T4W1 = ITEMS.register("t4w1",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.2f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 7))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_DOT, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.T4W1));
    public static final DeferredItem<Item> T6 = ITEMS.register("t6",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.2f, 25, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 15))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.SEXTUPLE_DASH, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.T6));
    public static final DeferredItem<Item> T7_ION_DISRUPTOR = ITEMS.register("t7_ion_disruptor",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 30, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.CHARGENSHOOT, new ProjectileWeaponStats(0, 0f, 0f, 25))), null,
                    List.of(FiringMode.CHARGENSHOOT),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_LARGE_CROSS, 0.6f),
                    FiringMode.CHARGENSHOOT, AmmoType.TIBANNA, WeaponClassification.DISRUPTOR, WeaponName.T7_ION_DISRUPTOR));
    public static final DeferredItem<Item> T9K7 = ITEMS.register("t9k7",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 14),
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 4))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.FULL_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.BOWL, 0.75f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.REPEATER, WeaponName.T9K7));
    public static final DeferredItem<Item> T21 = ITEMS.register("t21",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 150, 3, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.BURST, new ProjectileWeaponStats(14, 1f, 2f, 13))), null,
                    List.of(FiringMode.BURST),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_LARGE, 0.75f),
                    FiringMode.BURST, AmmoType.TIBANNA, WeaponClassification.REPEATER, WeaponName.T21));
    public static final DeferredItem<Item> T21B = ITEMS.register("t21b",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 150, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(10, 2.3f, 1.4f, 16))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_LARGE, 0.5f,
                            Map.of(),
                            Map.of(FiringMode.SEMI_AUTO, ScopeTexture.OVAL_LONG_RED_ARROW)),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.T21B));
    public static final DeferredItem<Item> TG446 = ITEMS.register("tg446",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 150, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(10, 2.3f, 1.4f, 16))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.TRIANGLE, 0.75f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.TG446));
    public static final DeferredItem<Item> THUNDERBLASTER = ITEMS.register("thunderblaster",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 150, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(10, 2.3f, 1.4f, 16))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_LARGE_DASH, 0.5f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.THUNDERBLASTER));
    public static final DeferredItem<Item> TL40 = ITEMS.register("tl40",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 250, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.LAUNCHER, new ProjectileWeaponStats(0, 0f, 0f, 4))), null,
                    List.of(FiringMode.LAUNCHER),
                    ProjectileWeaponUI.of(CrosshairTexture.VERTICAL_SOFT_FOCUS, 0.8f),
                    FiringMode.LAUNCHER, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.TL40));
    public static final DeferredItem<Item> TL50 = ITEMS.register("tl50",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 250, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 4),
                            FiringMode.CHARGENSHOOT, new ProjectileWeaponStats(0, 0f, 0f, 6))), null,
                    List.of(FiringMode.FULL_AUTO, FiringMode.CHARGENSHOOT),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_CIRCLE, 0.75f),
                    FiringMode.FULL_AUTO, AmmoType.TIBANNA, WeaponClassification.REPEATER, WeaponName.TL50));
    public static final DeferredItem<Item> UMBARAN_BLASTER = ITEMS.register("umbaran_blaster",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 250, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 14))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.QUAD_LINE, 0.75f),
                    FiringMode.SEMI_AUTO, AmmoType.SPIN_SEALED_TIBANNA, WeaponClassification.PISTOL, WeaponName.UMBARAN_BLASTER));
    public static final DeferredItem<Item> UTK3 = ITEMS.register("utk3",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 250, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 14))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.SOFT_FOCUS, 0.85f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.UTK3));
    public static final DeferredItem<Item> V13 = ITEMS.register("v13",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 200, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 14),
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 4))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.FULL_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.QUAD_DOT, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.REPEATER, WeaponName.V13));
    public static final DeferredItem<Item> V850_MK = ITEMS.register("v850_mk",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.8f, 14, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 16))), null,
                    List.of(FiringMode.SNIPER),
                    ProjectileWeaponUI.of(CrosshairTexture.SEXTUPLE_DASH, 0.15f),
                    FiringMode.SNIPER, AmmoType.TIBANNA, WeaponClassification.SNIPER, WeaponName.V850_MK));
    public static final DeferredItem<Item> VALKEN38X = ITEMS.register("valken38x",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.8f, 14, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 16))), null,
                    List.of(FiringMode.SNIPER),
                    ProjectileWeaponUI.of(CrosshairTexture.TEE, 0.25f,
                            Map.of(),
                            Map.of(FiringMode.SNIPER, ScopeTexture.CIRCLE_GREY_BLUE)),
                    FiringMode.SNIPER, AmmoType.IONIZED_TIBANNA, WeaponClassification.SNIPER, WeaponName.VALKEN38X));
    public static final DeferredItem<Item> VANGUARD_SCATTER = ITEMS.register("vanguard_scatter",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.4f, 30, 0, 5,
                    new EnumMap<>(Map.of(
                            FiringMode.SCATTER, new ProjectileWeaponStats(0, 0f, 8f, 6))), null,
                    List.of(FiringMode.SCATTER),
                    ProjectileWeaponUI.of(CrosshairTexture.SOFT_FOCUS, 0.85f),
                    FiringMode.SCATTER, AmmoType.TIBANNA, WeaponClassification.SCATTER, WeaponName.VANGUARD_SCATTER));
    public static final DeferredItem<Item> VERPINE_SHATTER_RIFLE = ITEMS.register("verpine_shatter_rifle",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.9f, 20, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 15))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.TEE, 0.2f),
                    FiringMode.SEMI_AUTO, AmmoType.STEEL_SLUG, WeaponClassification.SLUGTHROWER, WeaponName.VERPINE_SHATTER_RIFLE));
    public static final DeferredItem<Item> VERPINE_SIDEARM = ITEMS.register("verpine_sidearm",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.9f, 20, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 15))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_LINE, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.STEEL_SLUG, WeaponClassification.SLUGTHROWER, WeaponName.VERPINE_SIDEARM));
    public static final DeferredItem<Item> VILMARHS_REVENGE = ITEMS.register("vilmarhs_revenge",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.9f, 20, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 15))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_SMALL_LINE, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.VILMARHS_REVENGE));
    public static final DeferredItem<Item> VM19 = ITEMS.register("vm19",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.9f, 20, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 15),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_LINE, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.VM19));
    public static final DeferredItem<Item> VT20 = ITEMS.register("vt20",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.9f, 20, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 15))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.MORSE, 0.85f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.VT20));
    public static final DeferredItem<Item> VULK_TAU623_ROTARY = ITEMS.register("vulk_tau623_rotary",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 5))), null,
                    List.of(FiringMode.FULL_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_QUAD, 0.9f),
                    FiringMode.FULL_AUTO, AmmoType.TIBANNA, WeaponClassification.REPEATER, WeaponName.VULK_TAU623_ROTARY));
    public static final DeferredItem<Item> W50S = ITEMS.register("w50s",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 16))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.DOT, 0.9f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.W50S));
    public static final DeferredItem<Item> W90 = ITEMS.register("w90",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 16))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.BOWL, 0.75f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.W90));
    public static final DeferredItem<Item> W210 = ITEMS.register("w210",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 8))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.BOWL, 0.7f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.W210));
    public static final DeferredItem<Item> W310 = ITEMS.register("w310_blaster",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 16))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture._X, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.W310));
    public static final DeferredItem<Item> W340LM = ITEMS.register("w340lm",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 15))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture._X, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.W340LM));
    public static final DeferredItem<Item> WEEQUAY_LANCE = ITEMS.register("weequay_lance",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.9f, 10, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 18))), null,
                    List.of(FiringMode.SNIPER),
                    ProjectileWeaponUI.of(CrosshairTexture.CROSS, 0.25f,
                            Map.of(),
                            Map.of(FiringMode.SNIPER, ScopeTexture.CIRCLE_YELLOW)),
                    FiringMode.SNIPER, AmmoType.TIBANNA, WeaponClassification.SNIPER, WeaponName.WEEQUAY_LANCE));
    public static final DeferredItem<Item> WEEQUAY_PISTOL = ITEMS.register("weequay_pistol",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.2f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 12),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.MORSE, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.WEEQUAY_PISTOL));
    public static final DeferredItem<Item> WEEQUAY_RIFLE = ITEMS.register("weequay_rifle",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 3.0f, 20, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 18))), null,
                    List.of(FiringMode.SNIPER),
                    ProjectileWeaponUI.of(CrosshairTexture.MORSE, 0.2f,
                            Map.of(),
                            Map.of(FiringMode.SNIPER, ScopeTexture.CIRCLE_GREY)),
                    FiringMode.SNIPER, AmmoType.TIBANNA, WeaponClassification.SNIPER, WeaponName.WEEQUAY_RIFLE));
    public static final DeferredItem<Item> WESTAR_20 = ITEMS.register("westar_20",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 15))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.ANGLE_BRACKETS_SQUARE, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.WESTAR_20));
    public static final DeferredItem<Item> WESTAR_33 = ITEMS.register("westar_33",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 15))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_CIRCLE, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.WESTAR_33));
    public static final DeferredItem<Item> WESTAR_34 = ITEMS.register("westar_34",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(13, 2.4f, 4f, 17))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.DOT, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.WESTAR_34));
    public static final DeferredItem<Item> WESTAR_35 = ITEMS.register("westar_35",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(8, 2f, 2f, 17),
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(4, 1.5f, 3.7f, 17),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.FULL_AUTO, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.ANGLE_BRACKETS, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.SIG, WeaponClassification.PISTOL, WeaponName.WESTAR_35));
    public static final DeferredItem<Item> WESTAR2L = ITEMS.register("westar2l",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.2f, 200, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 14))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.EYE, 0.75f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.WESTAR2L));
    public static final DeferredItem<Item> WESTARE9 = ITEMS.register("westare9",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.7f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 16))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_LINE, 0.7f,
                            Map.of(),
                            Map.of(FiringMode.SEMI_AUTO, ScopeTexture.CIRCLE_BLUE_CROSS_3)),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.WESTARE9));
    public static final DeferredItem<Item> WESTARL4 = ITEMS.register("westarl4",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(8, 2f, 2f, 17),
                            FiringMode.BURST, new ProjectileWeaponStats(4, 1.5f, 3.7f, 17))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.BURST),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_LINE, 0.7f),
                    FiringMode.SEMI_AUTO, AmmoType.SIG, WeaponClassification.RIFLE, WeaponName.WESTARL4));
    public static final DeferredItem<Item> WESTARLVN = ITEMS.register("westarlvn",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(8, 2f, 2f, 17))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.QUAD_LINE, 0.7f),
                    FiringMode.SEMI_AUTO, AmmoType.SIG, WeaponClassification.RIFLE, WeaponName.WESTARLVN));
    public static final DeferredItem<Item> WESTARM5 = ITEMS.register("westarm5",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.5f, 350, 2, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(4, 1f, 3f, 12),
                            FiringMode.BURST, new ProjectileWeaponStats(11, 0.8f, 3.5f, 5),
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(3, 1.4f, 3.8f, 3))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.BURST, FiringMode.FULL_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.TRI_CIRCLE, 0.55f,
                            Map.of(),
                            Map.of(FiringMode.SEMI_AUTO, ScopeTexture.OVAL_SMALL_X, FiringMode.BURST, ScopeTexture.OVAL_SMALL_X, FiringMode.FULL_AUTO, ScopeTexture.OVAL_SMALL_X)),
                    FiringMode.FULL_AUTO, AmmoType.IONIZED_TIBANNA, WeaponClassification.RIFLE, WeaponName.WESTARM5));
    public static final DeferredItem<Item> WINCHESTER87 = ITEMS.register("winchester87",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 250, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SCATTER, new ProjectileWeaponStats(0, 0f, 0f, 14),
                            FiringMode.STUN, new ProjectileWeaponStats(15, 10f, 2f, 0))), null,
                    List.of(FiringMode.SCATTER, FiringMode.STUN),
                    ProjectileWeaponUI.of(CrosshairTexture.SOFT_FOCUS, 0.85f),
                    FiringMode.SCATTER, AmmoType.TIBANNA, WeaponClassification.SCATTER, WeaponName.WINCHESTER87));
    public static final DeferredItem<Item> WOOKIE_RIFLE = ITEMS.register("wookie_rifle",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(6, 3f, 5.2f, 17),
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(6, 3f, 5.2f, 17),
                            FiringMode.CHARGENSHOOT, new ProjectileWeaponStats(6, 3f, 5.2f, 17))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.FULL_AUTO, FiringMode.CHARGENSHOOT),
                    ProjectileWeaponUI.of(CrosshairTexture.EYE, 0.7f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.WOOKIE_RIFLE));
    public static final DeferredItem<Item> WOOKIE_SIDEARM = ITEMS.register("wookie_sidearm",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(6, 3f, 5.2f, 17),
                            FiringMode.CHARGENSHOOT, new ProjectileWeaponStats(6, 3f, 5.2f, 17))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.CHARGENSHOOT),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_SMALL_LINE, 0.75f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.WOOKIE_SIDEARM));
    public static final DeferredItem<Item> WS4 = ITEMS.register("ws4",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.0f, 20, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 10))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_SMALL_LINE, 0.85f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.WS4));
    public static final DeferredItem<Item> X8_NIGHT_SNIPER = ITEMS.register("x8_night_sniper",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 12))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.DOT, 0.75f,
                            Map.of(),
                            Map.of(FiringMode.SEMI_AUTO, ScopeTexture.CIRCLE_GREEN_YELLOW)),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.X8_NIGHT_SNIPER));
    public static final DeferredItem<Item> X30 = ITEMS.register("x30",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.3f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(6, 3f, 5.2f, 17))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CARROT, 0.75f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.X30));
    public static final DeferredItem<Item> X45 = ITEMS.register("x45",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 12))), null,
                    List.of(FiringMode.SNIPER),
                    ProjectileWeaponUI.of(CrosshairTexture.SEXTUPLE_DASH, 0.25f),
                    FiringMode.SNIPER, AmmoType.TIBANNA, WeaponClassification.SNIPER, WeaponName.X45));
    public static final DeferredItem<Item> X47 = ITEMS.register("x47",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new ProjectileWeaponStats(0, 0f, 0f, 12))), null,
                    List.of(FiringMode.SNIPER),
                    ProjectileWeaponUI.of(CrosshairTexture.REACTOR, 0.5f),
                    FiringMode.SNIPER, AmmoType.TIBANNA, WeaponClassification.SNIPER, WeaponName.X47));
    public static final DeferredItem<Item> XT7 = ITEMS.register("xt7",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.6f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 7),
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 3))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.FULL_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CROSS, 0.5f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.XT7));
    public static final DeferredItem<Item> Z6_ROTARY = ITEMS.register("z6_rotary",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.2f, 200, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.CHARGENSHOOT, new ProjectileWeaponStats(2, 0.4f, 5f, 4))), null,
                    List.of(FiringMode.CHARGENSHOOT),
                    ProjectileWeaponUI.of(CrosshairTexture.CIRCLE_LARGE, 0.85f),
                    FiringMode.CHARGENSHOOT, AmmoType.IONIZED_TIBANNA, WeaponClassification.REPEATER, WeaponName.Z6_ROTARY));
    public static final DeferredItem<Item> ZB3 = ITEMS.register("zb3",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.2f, 200, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 4))), null,
                    List.of(FiringMode.FULL_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture.CROSS, 0.8f),
                    FiringMode.FULL_AUTO, AmmoType.SPIN_SEALED_TIBANNA, WeaponClassification.REPEATER, WeaponName.ZB3));
    public static final DeferredItem<Item> ZP20 = ITEMS.register("zp20",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.2f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(0, 0f, 0f, 6))), null,
                    List.of(FiringMode.SEMI_AUTO),
                    ProjectileWeaponUI.of(CrosshairTexture._X, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.PISTOL, WeaponName.ZP20));
    public static final DeferredItem<Item> ZYGERRIAN_BLASTER = ITEMS.register("zygerrian_blaster",
            () -> new ProjectileItem(new Item.Properties().stacksTo(1), 2.2f, 200, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new ProjectileWeaponStats(2, 0.4f, 5f, 4),
                            FiringMode.CHARGENSHOOT, new ProjectileWeaponStats(2, 0.4f, 5f, 4))), null,
                    List.of(FiringMode.SEMI_AUTO, FiringMode.CHARGENSHOOT),
                    ProjectileWeaponUI.of(CrosshairTexture.VERTICAL_ANGLE_BRACKETS, 0.8f),
                    FiringMode.SEMI_AUTO, AmmoType.TIBANNA, WeaponClassification.RIFLE, WeaponName.ZYGERRIAN_BLASTER));
}