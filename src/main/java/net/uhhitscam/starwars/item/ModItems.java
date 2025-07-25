package net.uhhitscam.starwars.item;

import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.uhhitscam.starwars.OperationKnightfall;
import net.uhhitscam.starwars.item.custom.*;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(OperationKnightfall.MODID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    //Gas Items
    public static final DeferredItem<Item> GAS_CARTRIDGE = ITEMS.registerSimpleItem("gas_cartridge");
    public static final DeferredItem<Item> TIBANNA_GAS = ITEMS.registerItem("tibanna_gas",
            properties -> new GasItem(properties, 6400, 500, "TIBANNA"), new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> IONIZED_TIBANNA_GAS = ITEMS.registerItem("ionized_tibanna_gas",
            properties -> new GasItem(properties, 5200, 500, "IONIZED_TIBANNA"), new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> SPIN_SEALED_TIBANNA_GAS = ITEMS.registerItem("spin_sealed_tibanna_gas",
            properties -> new GasItem(properties, 10000, 500, "SPIN_SEALED_TIBANNA"), new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> TIBANNAX_GAS = ITEMS.registerItem("tibannax_gas",
            properties -> new GasItem(properties, 2800, 50, "TIBANNAX"), new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> SIG_GAS = ITEMS.registerItem("sig_gas",
            properties -> new GasItem(properties, 8000, 500, "SIG"), new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> MAGNETIZED_SIG_GAS = ITEMS.registerItem("magnetized_sig_gas",
            properties -> new GasItem(properties, 12800, 500, "MAGNETIZED_SIG"), new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> SKEVON = ITEMS.registerItem("skevon_gas",
            properties -> new GasItem(properties, 2000, 200, "SKEVON"), new Item.Properties().stacksTo(1));

    //Blaster Bolt
    public static final DeferredItem<Item> TIBANNA_BLASTER_BOLT = ITEMS.registerSimpleItem("tibanna_blaster_bolt");
    public static final DeferredItem<Item> IONIZED_TIBANNA_BLASTER_BOLT = ITEMS.registerSimpleItem("ionized_tibanna_blaster_bolt");
    public static final DeferredItem<Item> SPIN_SEALED_TIBANNA_BLASTER_BOLT = ITEMS.registerSimpleItem("spin_sealed_tibanna_blaster_bolt");
    public static final DeferredItem<Item> TIBANNAX_BLASTER_BOLT = ITEMS.registerSimpleItem("tibannax_blaster_bolt");
    public static final DeferredItem<Item> SIG_BLASTER_BOLT = ITEMS.registerSimpleItem("sig_blaster_bolt");
    public static final DeferredItem<Item> MAGNETIZED_SIG_BLASTER_BOLT = ITEMS.registerSimpleItem("magnetized_sig_blaster_bolt");
    public static final DeferredItem<Item> SKEVON_BLASTER_BOLT = ITEMS.registerSimpleItem("skevon_blaster_bolt");
    public static final DeferredItem<Item> STUN_BLASTER_BOLT = ITEMS.registerSimpleItem("stun_blaster_bolt");

    //Blaster-like Items
    public static final DeferredItem<Item> _62AUG2_HUNTING_RIFLE = ITEMS.register("_62aug2_hunting_rifle",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.8f, 10, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new BlasterStats(0, 0f, 0f, 10))),
                    List.of(FiringMode.SNIPER),
                    FiringMode.SNIPER, GasType.TIBANNA, Classification.SLUGTHROWER, BlasterName._62AUG2_HUNTING_RIFLE));
    public static final DeferredItem<Item> _84U_HUNTING_RIFLE = ITEMS.register("_84u_hunting_rifle",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.9f, 30, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new BlasterStats(0, 0f, 0f, 12))),
                    List.of(FiringMode.SNIPER),
                    FiringMode.SNIPER, GasType.TIBANNA, Classification.SNIPER, BlasterName._84U_HUNTING_RIFLE));
    public static final DeferredItem<Item> _434_DEATHHAMMER = ITEMS.register("_434_deathhammer",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.0f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 14))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName._434_DEATHHAMMER));
    public static final DeferredItem<Item> _773_FIREPUNCHER = ITEMS.register("_773_firepuncher",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 3.1f, 10, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new BlasterStats(0, 0f, 0f, 17),
                            FiringMode.LAUNCHER, new BlasterStats(0, 0f, 0f, 2))),
                    List.of(FiringMode.SNIPER, FiringMode.LAUNCHER),
                    FiringMode.SNIPER, GasType.IONIZED_TIBANNA, Classification.SNIPER, BlasterName._773_FIREPUNCHER));
    public static final DeferredItem<Item> _785MK_FIREPUNCHERX = ITEMS.register("_785mk_firepuncherx",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 3.2f, 20, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new BlasterStats(0, 0f, 0f, 19))),
                    List.of(FiringMode.SNIPER),
                    FiringMode.SNIPER, GasType.TIBANNA, Classification.SNIPER, BlasterName._785MK_FIREPUNCHERX));
    public static final DeferredItem<Item> A140 = ITEMS.register("a140",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.1f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 8))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.A140));
    public static final DeferredItem<Item> A180 = ITEMS.register("a180",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.2f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 15),
                            FiringMode.FULL_AUTO, new BlasterStats(0, 0f, 0f, 9),
                            FiringMode.SNIPER, new BlasterStats(0, 0f, 0f, 9),
                            FiringMode.STUN, new BlasterStats(15, 10f, 2f, 0))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.BURST, FiringMode.SNIPER, FiringMode.STUN),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.A180));
    public static final DeferredItem<Item> A280 = ITEMS.register("a280",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.6f, 500, 3, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(3, 1.8f, 2.1f, 15),
                            FiringMode.BURST, new BlasterStats(11, 1.6f, 2.8f, 9))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.BURST),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.RIFLE, BlasterName.A280));
    public static final DeferredItem<Item> A280C = ITEMS.register("a280c",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.6f, 500, 3, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.BURST, new BlasterStats(10, 1.4f, 2f, 8),
                            FiringMode.FULL_AUTO, new BlasterStats(3, 1.4f, 3.4f, 8))),
                    List.of(FiringMode.BURST, FiringMode.FULL_AUTO),
                    FiringMode.FULL_AUTO, GasType.TIBANNA, Classification.RIFLE, BlasterName.A280C));
    public static final DeferredItem<Item> A280CFE = ITEMS.register("a280cfe",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.7f, 500, 3, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 16),
                            FiringMode.BURST, new BlasterStats(0, 0f, 0f, 7),
                            FiringMode.SNIPER, new BlasterStats(0, 0f, 0f, 19))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.BURST, FiringMode.SNIPER),
                    FiringMode.BURST, GasType.TIBANNA, Classification.RIFLE, BlasterName.A280CFE));
    public static final DeferredItem<Item> A295 = ITEMS.register("a295",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.6f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(5, 1.6f, 2.8f, 15),
                            FiringMode.BURST, new BlasterStats(6, 2.2f, 3.3f, 5))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.BURST),
                    FiringMode.FULL_AUTO, GasType.TIBANNA, Classification.RIFLE, BlasterName.A295));
    public static final DeferredItem<Item> A300 = ITEMS.register("a300",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.7f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new BlasterStats(3, 1.6f, 4f, 6))),
                    List.of(FiringMode.FULL_AUTO),
                    FiringMode.FULL_AUTO, GasType.TIBANNA, Classification.RIFLE, BlasterName.A300));
    public static final DeferredItem<Item> A310 = ITEMS.register("a310",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.7f, 500, 3, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(5, 1.9f, 2f, 17),
                            FiringMode.BURST, new BlasterStats(12, 1.7f, 2.9f, 9))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.BURST),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.RIFLE, BlasterName.A310));
    public static final DeferredItem<Item> A350 = ITEMS.register("a350",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.7f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 15),
                            FiringMode.FULL_AUTO, new BlasterStats(0, 0f, 0f, 6))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.FULL_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.RIFLE, BlasterName.A350));
    public static final DeferredItem<Item> AC177 = ITEMS.register("ac177",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 1.9f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 12))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.AC177));
    public static final DeferredItem<Item> ACP_REPEATER = ITEMS.register("acp_repeater",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.1f, 200, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new BlasterStats(0, 0f, 0f, 8))),
                    List.of(FiringMode.FULL_AUTO),
                    FiringMode.FULL_AUTO, GasType.TIBANNA, Classification.REPEATER, BlasterName.ACP_REPEATER));
    public static final DeferredItem<Item> AKBARC = ITEMS.register("akbarc",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.5f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 15))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.RIFLE, BlasterName.AKBARC));
    public static final DeferredItem<Item> AMBAN_DISRUPTOR = ITEMS.register("amban_disruptor",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 4.0f, 1, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new BlasterStats(50, 3f, 1f, 30))),
                    List.of(FiringMode.SNIPER),
                    FiringMode.SNIPER, GasType.SIG, Classification.DISRUPTOR, BlasterName.AMBAN_DISRUPTOR));
    public static final DeferredItem<Item> APACHE = ITEMS.register("apache",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.3f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 12))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.APACHE));
    public static final DeferredItem<Item> ASTRA40 = ITEMS.register("astra40",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.3f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 12))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.ASTRA40));
    public static final DeferredItem<Item> AVARIK = ITEMS.register("avarik",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 3.0f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new BlasterStats(0, 0f, 0f, 8))),
                    List.of(FiringMode.SNIPER),
                    FiringMode.SNIPER, GasType.IONIZED_TIBANNA, Classification.SNIPER, BlasterName.AVARIK));
    public static final DeferredItem<Item> B1NA = ITEMS.register("b1na",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 8))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.B1NA));
    public static final DeferredItem<Item> B22 = ITEMS.register("b22",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.0f, 10, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 8))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.B22));
    public static final DeferredItem<Item> BALNAB = ITEMS.register("balnab",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.6f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new BlasterStats(0, 0f, 0f, 16))),
                    List.of(FiringMode.SNIPER),
                    FiringMode.SNIPER, GasType.TIBANNA, Classification.SNIPER, BlasterName.BALNAB));
    public static final DeferredItem<Item> BARMST12 = ITEMS.register("barmst12",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.7f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SCATTER, new BlasterStats(0, 0f, 0f, 6))),
                    List.of(FiringMode.SCATTER),
                    FiringMode.SCATTER, GasType.TIBANNA, Classification.SCATTER, BlasterName.BARMST12));
    public static final DeferredItem<Item> BATON_BLASTER = ITEMS.register("baton_blaster",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.1f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 14))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.IONIZED_TIBANNA, Classification.PISTOL, BlasterName.BATON_BLASTER));
    public static final DeferredItem<Item> BE09 = ITEMS.register("be09",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.2f, 20, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 5))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.BE09));
    public static final DeferredItem<Item> BE29 = ITEMS.register("be29",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 10))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.RIFLE, BlasterName.BE29));
    public static final DeferredItem<Item> BERSERKER = ITEMS.register("berserker",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.9f, 20, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(6, 2.4f, 3.4f, 17))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.SLUGTHROWER, BlasterName.BERSERKER));
    public static final DeferredItem<Item> BH4 = ITEMS.register("bh4",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.5f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 10),
                            FiringMode.FULL_AUTO, new BlasterStats(0, 0f, 0f, 3))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.FULL_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.BH4));
    public static final DeferredItem<Item> BLASTER_SPEAR = ITEMS.register("blaster_spear",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.7f, 150, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 12))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.IONIZED_TIBANNA, Classification.RIFLE, BlasterName.BLASTER_SPEAR));
    public static final DeferredItem<Item> BLNDRBUS = ITEMS.register("blndrbus",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SCATTER, new BlasterStats(0, 0f, 0f, 6),
                            FiringMode.REPULSE, new BlasterStats(0, 0f, 0f, 1),
                            FiringMode.STUN, new BlasterStats(15, 10f, 2f, 0))),
                    List.of(FiringMode.SCATTER, FiringMode.REPULSE, FiringMode.STUN),
                    FiringMode.SCATTER, GasType.TIBANNA, Classification.SCATTER, BlasterName.BLNDRBUS));
    public static final DeferredItem<Item> BLURRG1120 = ITEMS.register("blurrg1120",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.2f, 500, 1, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 9),
                            FiringMode.BURST, new BlasterStats(0, 0f, 0f, 4),
                            FiringMode.FULL_AUTO, new BlasterStats(0, 0f, 0f, 2))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.BURST, FiringMode.FULL_AUTO),
                    FiringMode.BURST, GasType.TIBANNA, Classification.PISTOL, BlasterName.BLURRG1120));
    public static final DeferredItem<Item> BM107 = ITEMS.register("bm107",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 3.5f, 120, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 20),
                            FiringMode.CHARGED, new BlasterStats(0, 0f, 0f, 29))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.CHARGED),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.RIFLE, BlasterName.BM107));
    public static final DeferredItem<Item> BOILER_RIFLE = ITEMS.register("boiler_rifle",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.7f, 200, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 15))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.RIFLE, BlasterName.BOILER_RIFLE));
    public static final DeferredItem<Item> BOONTA_BLASTER = ITEMS.register("boonta_blaster",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.0f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 11))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.BOONTA_BLASTER));
    public static final DeferredItem<Item> BOWCASTER = ITEMS.register("bowcaster",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.9f, 75, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 18),
                            FiringMode.CHARGED, new BlasterStats(0, 0f, 0f, 25))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.CHARGED),
                    FiringMode.SEMI_AUTO, GasType.SPIN_SEALED_TIBANNA, Classification.RIFLE, BlasterName.BOWCASTER));
    public static final DeferredItem<Item> BR14 = ITEMS.register("br14",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.5f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 5))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.IONIZED_TIBANNA, Classification.PISTOL, BlasterName.BR14));
    public static final DeferredItem<Item> BRYAR_RIFLE = ITEMS.register("bryar_rifle",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.3f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 15),
                            FiringMode.CHARGED, new BlasterStats(0, 0f, 0f, 20))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.CHARGED),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.RIFLE, BlasterName.BRYAR_RIFLE));
    public static final DeferredItem<Item> C10 = ITEMS.register("c10",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 17),
                            FiringMode.STUN, new BlasterStats(15, 10f, 2f, 0))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.C10));
    public static final DeferredItem<Item> C96 = ITEMS.register("c96",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.7f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 6),
                            FiringMode.STUN, new BlasterStats(15, 10f, 2f, 0))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.C96));
    public static final DeferredItem<Item> CA87 = ITEMS.register("ca87",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.5f, 30, 0, 5,
                    new EnumMap<>(Map.of(
                            FiringMode.SCATTER, new BlasterStats(25, 5f, 10f, 6),
                            FiringMode.REPULSE, new BlasterStats(28, 7f, 0f, 2),
                            FiringMode.STUN, new BlasterStats(15, 10f, 2f, 0))),
                    List.of(FiringMode.SCATTER, FiringMode.REPULSE, FiringMode.STUN),
                    FiringMode.SCATTER, GasType.TIBANNA, Classification.SCATTER, BlasterName.CA87));
    public static final DeferredItem<Item> CAIJ_VANDAS_BLASTER_PISTOL = ITEMS.register("caij_vandas_blaster_pistol",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.5f, 200, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 15),
                            FiringMode.CHARGED, new BlasterStats(0, 0f, 0f, 20))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.CHARGED),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.CAIJ_VANDAS_BLASTER_PISTOL));
    public static final DeferredItem<Item> CC420 = ITEMS.register("cc420",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.6f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 12))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.CC420));
    public static final DeferredItem<Item> CJ9_BO_RIFLE = ITEMS.register("cj9_bo_rifle",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.5f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 11))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.RIFLE, BlasterName.CJ9_BO_RIFLE));
    public static final DeferredItem<Item> CORPO_RIFLE = ITEMS.register("corpo_rifle",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.5f, 150, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 13))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.RIFLE, BlasterName.CORPO_RIFLE));
    public static final DeferredItem<Item> CR2 = ITEMS.register("cr2",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.3f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new BlasterStats(0, 0f, 0f, 2),
                            FiringMode.STUN, new BlasterStats(15, 10f, 2f, 0))),
                    List.of(FiringMode.FULL_AUTO, FiringMode.STUN),
                    FiringMode.FULL_AUTO, GasType.IONIZED_TIBANNA, Classification.PISTOL, BlasterName.CR2));
    public static final DeferredItem<Item> CS14 = ITEMS.register("cs14",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.2f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 4),
                            FiringMode.STUN, new BlasterStats(15, 10f, 2f, 0))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.CS14));
    public static final DeferredItem<Item> CYCLER_RIFLE = ITEMS.register("cycler_rifle",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 3.9f, 15, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new BlasterStats(25, 3f, 1f, 18))),
                    List.of(FiringMode.SNIPER),
                    FiringMode.SNIPER, GasType.SPIN_SEALED_TIBANNA, Classification.SNIPER, BlasterName.CYCLER_RIFLE));
    public static final DeferredItem<Item> CZERKA_ADVENTURER = ITEMS.register("czerka_adventurer",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 4.0f, 20, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new BlasterStats(0, 0f, 0f, 19))),
                    List.of(FiringMode.SNIPER),
                    FiringMode.SNIPER, GasType.TIBANNA, Classification.SLUGTHROWER, BlasterName.CZERKA_ADVENTURER));
    public static final DeferredItem<Item> DARK_TROOPER_RIFLE = ITEMS.register("dark_trooper_rifle",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.5f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 17))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.RIFLE, BlasterName.DARK_TROOPER_RIFLE));
    public static final DeferredItem<Item> DC12U = ITEMS.register("dc12u",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 15))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.IONIZED_TIBANNA, Classification.RIFLE, BlasterName.DC12U));
    public static final DeferredItem<Item> DC15A = ITEMS.register("dc15a",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.6f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(7, 1.6f, 1f, 17),
                            FiringMode.FULL_AUTO, new BlasterStats(3, 1f, 3f, 5),
                            FiringMode.STUN, new BlasterStats(15, 10f, 2f, 0))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.FULL_AUTO, FiringMode.STUN),
                    FiringMode.SEMI_AUTO, GasType.IONIZED_TIBANNA, Classification.RIFLE, BlasterName.DC15A));
    public static final DeferredItem<Item> DC15LE = ITEMS.register("dc15le",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.5f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new BlasterStats(3, 1.7f, 4.7f, 6))),
                    List.of(FiringMode.FULL_AUTO),
                    FiringMode.FULL_AUTO, GasType.IONIZED_TIBANNA, Classification.RIFLE, BlasterName.DC15LE));
    public static final DeferredItem<Item> DC15S_CARBINE = ITEMS.register("dc15s_carbine",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(10, 1.7f, 3.5f, 12),
                            FiringMode.FULL_AUTO, new BlasterStats(5, 1.3f, 4.4f, 5),
                            FiringMode.STUN, new BlasterStats(15, 10f, 2f, 0))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.FULL_AUTO, FiringMode.STUN),
                    FiringMode.SEMI_AUTO, GasType.IONIZED_TIBANNA, Classification.CARBINE, BlasterName.DC15S_CARBINE));
    public static final DeferredItem<Item> DC15S_SIDEARM = ITEMS.register("dc15s_sidearm",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 15, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 6),
                            FiringMode.STUN, new BlasterStats(15, 10f, 2f, 0))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    FiringMode.SEMI_AUTO, GasType.IONIZED_TIBANNA, Classification.PISTOL, BlasterName.DC15S_SIDEARM));
    public static final DeferredItem<Item> DC15X = ITEMS.register("dc15x",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.8f, 6, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new BlasterStats(0, 0f, 0f, 23),
                            FiringMode.CHARGED, new BlasterStats(0, 0f, 0f, 17))),
                    List.of(FiringMode.SNIPER, FiringMode.CHARGED),
                    FiringMode.SNIPER, GasType.IONIZED_TIBANNA, Classification.SNIPER, BlasterName.DC15X));
    public static final DeferredItem<Item> DC17 = ITEMS.register("dc17",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(3, 0.6f, 6f, 14),
                            FiringMode.STUN, new BlasterStats(15, 10f, 2f, 0))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    FiringMode.SEMI_AUTO, GasType.IONIZED_TIBANNA, Classification.PISTOL, BlasterName.DC17));
    public static final DeferredItem<Item> DC17M = ITEMS.register("dc17m",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.5f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new BlasterStats(0, 0f, 0f, 5),
                            FiringMode.CHARGED, new BlasterStats(0, 0f, 0f, 25),
                            FiringMode.SNIPER, new BlasterStats(0, 0f, 0f, 18),
                            FiringMode.LAUNCHER, new BlasterStats(0, 0f, 0f, 8))),
                    List.of(FiringMode.FULL_AUTO, FiringMode.CHARGED, FiringMode.SNIPER, FiringMode.LAUNCHER),
                    FiringMode.FULL_AUTO, GasType.IONIZED_TIBANNA, Classification.REPEATER, BlasterName.DC17M));
    public static final DeferredItem<Item> DC17S = ITEMS.register("dc17s",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.5f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 16))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.IONIZED_TIBANNA, Classification.PISTOL, BlasterName.DC17S));
    public static final DeferredItem<Item> DC19 = ITEMS.register("dc19",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.6f, 10, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 15))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNAX, Classification.CARBINE, BlasterName.DC19));
    public static final DeferredItem<Item> DE10 = ITEMS.register("de10",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.7f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 14))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.IONIZED_TIBANNA, Classification.PISTOL, BlasterName.DE10));
    public static final DeferredItem<Item> DEFTECH = ITEMS.register("deftech",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.8f, 6, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 17),
                            FiringMode.BURST, new BlasterStats(0, 0f, 0f, 7))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.BURST),
                    FiringMode.SEMI_AUTO, GasType.SIG, Classification.RIFLE, BlasterName.DEFTECH));
    public static final DeferredItem<Item> DFD1 = ITEMS.register("dfd1",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.8f, 6, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 18))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.SLUGTHROWER, BlasterName.DFD1));
    public static final DeferredItem<Item> DG29 = ITEMS.register("dg29",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 7))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.DG29));
    public static final DeferredItem<Item> DH16 = ITEMS.register("dh16",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.3f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 7))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.DH16));
    public static final DeferredItem<Item> DH17 = ITEMS.register("dh17",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.5f, 500, 2, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(11, 2.7f, 2f, 17),
                            FiringMode.BURST, new BlasterStats(14, 1.4f, 2.7f, 7),
                            FiringMode.FULL_AUTO, new BlasterStats(4, 2.1f, 3.9f, 5),
                            FiringMode.STUN, new BlasterStats(15, 10f, 2f, 0))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.BURST, FiringMode.FULL_AUTO, FiringMode.STUN),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.DH17));
    public static final DeferredItem<Item> DH23 = ITEMS.register("dh23",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.6f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 14))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.DH23));
    public static final DeferredItem<Item> DH447 = ITEMS.register("dh447",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.7f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new BlasterStats(0, 0f, 0f, 17))),
                    List.of(FiringMode.SNIPER),
                    FiringMode.SNIPER, GasType.TIBANNA, Classification.SNIPER, BlasterName.DH447));
    public static final DeferredItem<Item> DL18 = ITEMS.register("dl18",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 12),
                            FiringMode.STUN, new BlasterStats(15, 10f, 2f, 0))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.DL21));
    public static final DeferredItem<Item> DL21 = ITEMS.register("dl21",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 0),
                            FiringMode.STUN, new BlasterStats(15, 10f, 2f, 0))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.DL21));
    public static final DeferredItem<Item> DL44 = ITEMS.register("dl44",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.5f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(7, 28f, 2.9f, 16),
                            FiringMode.SNIPER, new BlasterStats(15, 36f, 2.2f, 20))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.SNIPER),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.DL44));
    public static final DeferredItem<Item> DLS12 = ITEMS.register("dls12",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.3f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 14))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.IONIZED_TIBANNA, Classification.CARBINE, BlasterName.DLS12));
    public static final DeferredItem<Item> DLT18 = ITEMS.register("dlt18",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.2f, 200, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new BlasterStats(0, 0f, 0f, 8))),
                    List.of(FiringMode.FULL_AUTO),
                    FiringMode.FULL_AUTO, GasType.TIBANNA, Classification.RIFLE, BlasterName.DLT18));
    public static final DeferredItem<Item> DLT19 = ITEMS.register("dlt19",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new BlasterStats(0, 0f, 0f, 9))),
                    List.of(FiringMode.FULL_AUTO),
                    FiringMode.FULL_AUTO, GasType.TIBANNA, Classification.RIFLE, BlasterName.DLT19));
    public static final DeferredItem<Item> DLT19D = ITEMS.register("dlt19d",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 10, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 14))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.RIFLE, BlasterName.DLT19D));
    public static final DeferredItem<Item> DLT19X = ITEMS.register("dlt19x",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.6f, 40, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new BlasterStats(0, 0f, 0f, 18))),
                    List.of(FiringMode.SNIPER),
                    FiringMode.SNIPER, GasType.TIBANNA, Classification.SNIPER, BlasterName.DLT19X));
    public static final DeferredItem<Item> DLT20A = ITEMS.register("dlt20a",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.6f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 17))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.SPIN_SEALED_TIBANNA, Classification.RIFLE, BlasterName.DLT20A));
    public static final DeferredItem<Item> DN_BOLT_CASTER = ITEMS.register("dn_bolt_caster",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.0f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 20))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.IONIZED_TIBANNA, Classification.DISRUPTOR, BlasterName.DN_BOLT_CASTER));
    public static final DeferredItem<Item> DP23 = ITEMS.register("dp23",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.3f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 13),
                            FiringMode.CHARGED, new BlasterStats(0, 0f, 0f, 18))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.CHARGED),
                    FiringMode.SEMI_AUTO, GasType.IONIZED_TIBANNA, Classification.RIFLE, BlasterName.DP23));
    public static final DeferredItem<Item> DRESSELLIAN_PROJECTILE_RIFLE = ITEMS.register("dressellian_projectile_rifle",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 3.8f, 15, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new BlasterStats(0, 0f, 0f, 19))),
                    List.of(FiringMode.SNIPER),
                    FiringMode.SNIPER, GasType.TIBANNA, Classification.SLUGTHROWER, BlasterName.DRESSELLIAN_PROJECTILE_RIFLE));
    public static final DeferredItem<Item> DT12 = ITEMS.register("dt12",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.2f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(7, 1.1f, 4.9f, 9))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.DT12));
    public static final DeferredItem<Item> DT15 = ITEMS.register("dt15",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.1f, 200, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 15))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.DT15));
    public static final DeferredItem<Item> DT29 = ITEMS.register("dt29",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.8f, 6, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 16))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.DT29));
    public static final DeferredItem<Item> DT57 = ITEMS.register("dt57",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.1f, 400, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 8))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.DT57));
    public static final DeferredItem<Item> DX13 = ITEMS.register("dx13",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.3f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 17),
                            FiringMode.FULL_AUTO, new BlasterStats(0, 0f, 0f, 7),
                            FiringMode.STUN, new BlasterStats(15, 10f, 2f, 0))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.FULL_AUTO, FiringMode.STUN),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.DX13));
    public static final DeferredItem<Item> E5 = ITEMS.register("e5",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.1f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(6, 1.9f, 4f, 6),
                            FiringMode.STUN, new BlasterStats(15, 10f, 2f, 0))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.RIFLE, BlasterName.E5));
    public static final DeferredItem<Item> E5_BX = ITEMS.register("e5_bx",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.3f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(9, 2f, 3.7f, 12),
                            FiringMode.STUN, new BlasterStats(15, 10f, 2f, 0))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.RIFLE, BlasterName.E5_BX));
    public static final DeferredItem<Item> E5_CE = ITEMS.register("e5_ce",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.2f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(8, 2.7f, 3.4f, 13),
                            FiringMode.FULL_AUTO, new BlasterStats(3, 1.5f, 5f, 5))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.FULL_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.RIFLE, BlasterName.E5_CE));
    public static final DeferredItem<Item> E5C = ITEMS.register("e5c",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.0f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new BlasterStats(2, 0.6f, 5.6f, 4))),
                    List.of(FiringMode.FULL_AUTO),
                    FiringMode.FULL_AUTO, GasType.TIBANNA, Classification.RIFLE, BlasterName.E5C));
    public static final DeferredItem<Item> E5S = ITEMS.register("e5s",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.8f, 4, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new BlasterStats(0, 0f, 0f, 4))),
                    List.of(FiringMode.SNIPER),
                    FiringMode.SNIPER, GasType.TIBANNA, Classification.SNIPER, BlasterName.E5S));
    public static final DeferredItem<Item> E10 = ITEMS.register("e10",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.6f, 500, 2, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 8),
                            FiringMode.BURST, new BlasterStats(0, 0f, 0f, 5))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.BURST),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.RIFLE, BlasterName.E10));
    public static final DeferredItem<Item> E10_5 = ITEMS.register("e10_5",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.6f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 9))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.RIFLE, BlasterName.E10_5));
    public static final DeferredItem<Item> E10R = ITEMS.register("e10r",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.8f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 16))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.RIFLE, BlasterName.E10R));
    public static final DeferredItem<Item> E11_CARBINE = ITEMS.register("e11_carbine",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 8),
                            FiringMode.STUN, new BlasterStats(15, 10f, 2f, 0))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.CARBINE, BlasterName.E11_CARBINE));
    public static final DeferredItem<Item> E11_RIFLE = ITEMS.register("e11_rifle",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.7f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(10, 1f, 5f, 13),
                            FiringMode.FULL_AUTO, new BlasterStats(5, 0.8f, 7.8f, 6),
                            FiringMode.STUN, new BlasterStats(15, 10f, 2f, 0))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.FULL_AUTO, FiringMode.STUN),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.RIFLE, BlasterName.E11_RIFLE));
    public static final DeferredItem<Item> E11B = ITEMS.register("e11b",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.7f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 9),
                            FiringMode.STUN, new BlasterStats(15, 10f, 2f, 0))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.RIFLE, BlasterName.E11B));
    public static final DeferredItem<Item> E11D = ITEMS.register("e11d",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.6f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 16),
                            FiringMode.STUN, new BlasterStats(15, 10f, 2f, 0))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.CARBINE, BlasterName.E11D));
    public static final DeferredItem<Item> E11S = ITEMS.register("e11s",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 3.0f, 6, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new BlasterStats(0, 0f, 0f, 17))),
                    List.of(FiringMode.SNIPER),
                    FiringMode.SNIPER, GasType.TIBANNA, Classification.SNIPER, BlasterName.E11S));
    public static final DeferredItem<Item> E17D = ITEMS.register("e17d",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.8f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new BlasterStats(0, 0f, 0f, 16))),
                    List.of(FiringMode.SNIPER),
                    FiringMode.SNIPER, GasType.TIBANNA, Classification.SNIPER, BlasterName.E17D));
    public static final DeferredItem<Item> E22 = ITEMS.register("e22",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.6f, 500, 2, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 9),
                            FiringMode.BURST, new BlasterStats(0, 0f, 0f, 6),
                            FiringMode.FULL_AUTO, new BlasterStats(0, 0f, 0f, 4))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.BURST, FiringMode.FULL_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.RIFLE, BlasterName.E22));
    public static final DeferredItem<Item> EC17 = ITEMS.register("ec17",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.2f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 6),
                            FiringMode.STUN, new BlasterStats(15, 10f, 2f, 0))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.EC17));
    public static final DeferredItem<Item> EE3 = ITEMS.register("ee3",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.7f, 300, 2, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(7, 2f, 2.4f, 15),
                            FiringMode.BURST, new BlasterStats(15, 1f, 2.7f, 6))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.BURST),
                    FiringMode.BURST, GasType.TIBANNA, Classification.CARBINE, BlasterName.EE3));
    public static final DeferredItem<Item> EE4 = ITEMS.register("ee4",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 500, 1, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.BURST, new BlasterStats(0, 0f, 0f, 8))),
                    List.of(FiringMode.BURST),
                    FiringMode.BURST, GasType.TIBANNA, Classification.CARBINE, BlasterName.EE4));
    public static final DeferredItem<Item> ELG3A = ITEMS.register("elg3a",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.5f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(5, 1.8f, 4f, 13),
                            FiringMode.STUN, new BlasterStats(15, 10f, 2f, 0))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    FiringMode.SEMI_AUTO, GasType.SPIN_SEALED_TIBANNA, Classification.PISTOL, BlasterName.ELG3A));
//    public static final DeferredItem<Item> EWEB = ITEMS.register("eweb",
//            () -> new BlasterItem(new Item.Properties().stacksTo(1), 4.0f, 500, 0, 1,
//                    new EnumMap<>(Map.of(
//                            FiringMode.FULL_AUTO, new BlasterStats(0, 0f, 0f, 8))),
//                    List.of(FiringMode.FULL_AUTO),
//                    FiringMode.FULL_AUTO, GasType.TIBANNA, Classification.REPEATER, BlasterName.EWEB));
    public static final DeferredItem<Item> FC1_FLECHETTE_LAUNCHER = ITEMS.register("fc1_flechette_launcher",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.9f, 6, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 19))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.SLUGTHROWER, BlasterName.FC1_FLECHETTE_LAUNCHER));
    public static final DeferredItem<Item> FLINTLOQ_PISTOL = ITEMS.register("flintloq_pistol",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.1f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 14))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.FLINTLOQ_PISTOL));
    public static final DeferredItem<Item> FLINTLOQ_RIFLE = ITEMS.register("flintloq_rifle",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 5, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new BlasterStats(0, 0f, 0f, 17))),
                    List.of(FiringMode.SNIPER),
                    FiringMode.SNIPER, GasType.TIBANNA, Classification.SNIPER, BlasterName.FLINTLOQ_RIFLE));
    public static final DeferredItem<Item> FLITE37 = ITEMS.register("flite37",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SCATTER, new BlasterStats(0, 0f, 0f, 18))),
                    List.of(FiringMode.SCATTER),
                    FiringMode.SCATTER, GasType.TIBANNA, Classification.SCATTER, BlasterName.FLITE37));
    public static final DeferredItem<Item> FN57 = ITEMS.register("fn57",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.3f, 200, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 14))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.FN57));
    public static final DeferredItem<Item> FP45 = ITEMS.register("fp45",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.0f, 15, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 10))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.FP45));
    public static final DeferredItem<Item> GALAAR15 = ITEMS.register("galaar15",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.5f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 13))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.SIG, Classification.CARBINE, BlasterName.GALAAR15));
    public static final DeferredItem<Item> GALAR90 = ITEMS.register("galar90",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.9f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new BlasterStats(0, 0f, 0f, 15))),
                    List.of(FiringMode.SNIPER),
                    FiringMode.SNIPER, GasType.SIG, Classification.SNIPER, BlasterName.GALAR90));
    public static final DeferredItem<Item> GE36 = ITEMS.register("ge36",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 3.0f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new BlasterStats(0, 0f, 0f, 19))),
                    List.of(FiringMode.SNIPER),
                    FiringMode.SNIPER, GasType.TIBANNA, Classification.SNIPER, BlasterName.GE36));
    public static final DeferredItem<Item> GL77 = ITEMS.register("gl77",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.3f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 14))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.IONIZED_TIBANNA, Classification.PISTOL, BlasterName.GL77));
    public static final DeferredItem<Item> HF94 = ITEMS.register("hf94",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.6f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 17))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.HF94));
    public static final DeferredItem<Item> IB94 = ITEMS.register("ib94",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.6f, 200, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 15))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.IB94));
    public static final DeferredItem<Item> IMPERIAL_SUPERCOMMANDO_BLASTER = ITEMS.register("imperial_supercommando_blaster",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.7f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 16))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.RIFLE, BlasterName.IMPERIAL_SUPERCOMMANDO_BLASTER));
    public static final DeferredItem<Item> IQA11 = ITEMS.register("iqa11",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.8f, 7, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new BlasterStats(0, 0f, 0f, 16))),
                    List.of(FiringMode.SNIPER),
                    FiringMode.SNIPER, GasType.IONIZED_TIBANNA, Classification.SNIPER, BlasterName.IQA11));
    public static final DeferredItem<Item> JEZALI_CYCLER_RIFLE = ITEMS.register("jezali_cycler_rifle",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 3.3f, 20, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new BlasterStats(0, 0f, 0f, 20))),
                    List.of(FiringMode.SNIPER),
                    FiringMode.SNIPER, GasType.TIBANNA, Classification.SLUGTHROWER, BlasterName.JEZALI_CYCLER_RIFLE));
    public static final DeferredItem<Item> JND41 = ITEMS.register("jnd41",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.5f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 15),
                            FiringMode.CHARGED, new BlasterStats(0, 0f, 0f, 19))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.CHARGED),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.RIFLE, BlasterName.JND41));
    public static final DeferredItem<Item> K16_BRYAR_PISTOL = ITEMS.register("k16_bryar_pistol",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.2f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 12),
                            FiringMode.CHARGED, new BlasterStats(0, 0f, 0f, 17))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.CHARGED),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.K16_BRYAR_PISTOL));
    public static final DeferredItem<Item> KA74 = ITEMS.register("ka74",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 14),
                            FiringMode.FULL_AUTO, new BlasterStats(0, 0f, 0f, 5))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.FULL_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.RIFLE, BlasterName.KA74));
    public static final DeferredItem<Item> KISTEER_1284 = ITEMS.register("kisteer_1284",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.9f, 20, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new BlasterStats(0, 0f, 0f, 18))),
                    List.of(FiringMode.SNIPER),
                    FiringMode.SNIPER, GasType.TIBANNA, Classification.SNIPER, BlasterName.KISTEER_1284));
    public static final DeferredItem<Item> KOCH9S = ITEMS.register("koch9s",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.1f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 14))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.KOCH9S));
    public static final DeferredItem<Item> KRIE4 = ITEMS.register("krie4",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.2f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 12))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.KRIE4));
    public static final DeferredItem<Item> KUEGET_LN21 = ITEMS.register("kueget_ln21",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.2f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 12),
                            FiringMode.STUN, new BlasterStats(15, 10f, 2f, 0))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.KUEGET_LN21));
    public static final DeferredItem<Item> KYD21 = ITEMS.register("kyd21",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 75, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 10))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.KYD21));
    public static final DeferredItem<Item> L5 = ITEMS.register("l5",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 14))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.L5));
    public static final DeferredItem<Item> L60 = ITEMS.register("l60",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 16))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.RIFLE, BlasterName.L60));
    public static final DeferredItem<Item> LEUCHT42 = ITEMS.register("leucht42",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.6f, 150, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 14))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.LEUCHT42));
    public static final DeferredItem<Item> LL30 = ITEMS.register("ll30",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.7f, 200, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 16))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.LL30));
    public static final DeferredItem<Item> LUG_PO8 = ITEMS.register("lug_po8",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.5f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 13))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.LUG_PO8));
    public static final DeferredItem<Item> LW896 = ITEMS.register("lw896",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.6f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 11))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.LW896));
    public static final DeferredItem<Item> M12 = ITEMS.register("m12",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.6f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 16),
                            FiringMode.FULL_AUTO, new BlasterStats(0, 0f, 0f, 8))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.FULL_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.RIFLE, BlasterName.M12));
    public static final DeferredItem<Item> M19A1 = ITEMS.register("m19a1",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.1f, 200, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 15))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.M19A1));
    public static final DeferredItem<Item> M32 = ITEMS.register("m32",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.6f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new BlasterStats(0, 0f, 0f, 9))),
                    List.of(FiringMode.FULL_AUTO),
                    FiringMode.FULL_AUTO, GasType.TIBANNA, Classification.REPEATER, BlasterName.M32));
    public static final DeferredItem<Item> M41 = ITEMS.register("m41",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.6f, 400, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 16),
                            FiringMode.FULL_AUTO, new BlasterStats(0, 0f, 0f, 9))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.FULL_AUTO),
                    FiringMode.FULL_AUTO, GasType.TIBANNA, Classification.REPEATER, BlasterName.M41));
    public static final DeferredItem<Item> M45 = ITEMS.register("m45",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.6f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new BlasterStats(0, 0f, 0f, 11))),
                    List.of(FiringMode.FULL_AUTO),
                    FiringMode.FULL_AUTO, GasType.TIBANNA, Classification.REPEATER, BlasterName.M45));
    public static final DeferredItem<Item> M55 = ITEMS.register("m55",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.7f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new BlasterStats(0, 0f, 0f, 10))),
                    List.of(FiringMode.FULL_AUTO),
                    FiringMode.FULL_AUTO, GasType.TIBANNA, Classification.REPEATER, BlasterName.M55));
    public static final DeferredItem<Item> M61 = ITEMS.register("m61",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.6f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new BlasterStats(0, 0f, 0f, 19))),
                    List.of(FiringMode.FULL_AUTO),
                    FiringMode.FULL_AUTO, GasType.TIBANNA, Classification.REPEATER, BlasterName.M61));
    public static final DeferredItem<Item> MARG_MCM = ITEMS.register("marg_mcm",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 15))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.MARG_MCM));
    public static final DeferredItem<Item> MK_II_PALADIN = ITEMS.register("mk_ii_paladin",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.5f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 16))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.IONIZED_TIBANNA, Classification.RIFLE, BlasterName.MK_II_PALADIN));
    public static final DeferredItem<Item> MODEL_57 = ITEMS.register("model_57",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.7f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 15),
                            FiringMode.STUN, new BlasterStats(15, 10f, 2f, 0))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.MODEL_57));
//    public static final DeferredItem<Item> MORTAR = ITEMS.register("mortar",
//            () -> new BlasterItem(new Item.Properties().stacksTo(1), 3.0f, 300, 0, 1,
//                    new EnumMap<>(Map.of(
//                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 15))),
//                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
//                    FiringMode.SEMI_AUTO, GasType.IONIZED_TIBANNA, Classification.PISTOL, BlasterName.MORTAR));
    public static final DeferredItem<Item> MOTTO_MK_4 = ITEMS.register("motto_mk_4",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 200, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 15),
                            FiringMode.REPULSE, new BlasterStats(0, 0f, 0f, 1),
                            FiringMode.STUN, new BlasterStats(15, 10f, 2f, 0))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.REPULSE, FiringMode.STUN),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.RIFLE, BlasterName.MOTTO_MK_4));
    public static final DeferredItem<Item> MW20_BRYAR_PISTOL = ITEMS.register("mw20_bryar_pistol",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.5f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 15),
                            FiringMode.CHARGED, new BlasterStats(0, 0f, 0f, 1))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.CHARGED),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.MW20_BRYAR_PISTOL));
    public static final DeferredItem<Item> MWC35C = ITEMS.register("mwc35c",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.3f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 17),
                            FiringMode.FULL_AUTO, new BlasterStats(0, 0f, 0f, 9))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.FULL_AUTO),
                    FiringMode.FULL_AUTO, GasType.TIBANNA, Classification.REPEATER, BlasterName.MWC35C));
    public static final DeferredItem<Item> NAMBU14 = ITEMS.register("nambu14",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.2f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 13))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.NAMBU14));
    public static final DeferredItem<Item> NEO_CRUSADER_RIFLE = ITEMS.register("neo_crusader_rifle",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.5f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 17),
                            FiringMode.CHARGED, new BlasterStats(0, 0f, 0f, 20))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.CHARGED),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.RIFLE, BlasterName.NEO_CRUSADER_RIFLE));
    public static final DeferredItem<Item> NIGHT_STINGER = ITEMS.register("night_stinger",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.9f, 5, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new BlasterStats(0, 0f, 0f, 19),
                            FiringMode.CHARGED, new BlasterStats(0, 0f, 0f, 15))),
                    List.of(FiringMode.SNIPER, FiringMode.CHARGED),
                    FiringMode.SNIPER, GasType.TIBANNA, Classification.SNIPER, BlasterName.NIGHT_STINGER));
    public static final DeferredItem<Item> NIGHT_WIND_RIFLE = ITEMS.register("night_wind_rifle",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 16))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.RIFLE, BlasterName.NIGHT_WIND_RIFLE));
    public static final DeferredItem<Item> NT242 = ITEMS.register("nt242",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 3.0f, 75, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new BlasterStats(0, 0f, 0f, 18))),
                    List.of(FiringMode.SNIPER),
                    FiringMode.SNIPER, GasType.SPIN_SEALED_TIBANNA, Classification.SNIPER, BlasterName.NT242));
    public static final DeferredItem<Item> OK98 = ITEMS.register("ok98",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.6f, 1000, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 15))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.CARBINE, BlasterName.OK98));
    public static final DeferredItem<Item> OUTLAND_RIFLE = ITEMS.register("outland_rifle",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.8f, 8, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new BlasterStats(0, 0f, 0f, 16))),
                    List.of(FiringMode.SNIPER),
                    FiringMode.SNIPER, GasType.TIBANNA, Classification.SLUGTHROWER, BlasterName.OUTLAND_RIFLE));
    public static final DeferredItem<Item> P38 = ITEMS.register("p38",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.6f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 18))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.P38));
    public static final DeferredItem<Item> PANIC_PISTOL = ITEMS.register("panic_pistol",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.5f, 5, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 9))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.SLUGTHROWER, BlasterName.PANIC_PISTOL));
    public static final DeferredItem<Item> PCC_PROJECTOR = ITEMS.register("pcc_projector",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.3f, 150, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 17))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.PCC_PROJECTOR));
    public static final DeferredItem<Item> PK23 = ITEMS.register("pk23",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.9f, 40, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new BlasterStats(0, 0f, 0f, 19))),
                    List.of(FiringMode.SNIPER),
                    FiringMode.SNIPER, GasType.TIBANNA, Classification.SNIPER, BlasterName.PK23));
    public static final DeferredItem<Item> POWER_5 = ITEMS.register("power_5",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.6f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 14),
                            FiringMode.CHARGED, new BlasterStats(0, 0f, 0f, 18))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.CHARGED),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.POWER_5));
    public static final DeferredItem<Item> PREMIER = ITEMS.register("premier",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.2f, 200, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 15))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.PREMIER));
    public static final DeferredItem<Item> Q2 = ITEMS.register("q2",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.0f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(11, 1.3f, 6f, 11))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.Q2));
    public static final DeferredItem<Item> QUARREN_RIFLE = ITEMS.register("quarren_rifle",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.5f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 15))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.RIFLE, BlasterName.QUARREN_RIFLE));
    public static final DeferredItem<Item> RECIPROCATING_QUAD_BLASTER_CANNON = ITEMS.register("reciprocating_quad_blaster_cannon",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new BlasterStats(0, 0f, 0f, 12))),
                    List.of(FiringMode.FULL_AUTO),
                    FiringMode.FULL_AUTO, GasType.IONIZED_TIBANNA, Classification.REPEATER, BlasterName.RECIPROCATING_QUAD_BLASTER_CANNON));
    public static final DeferredItem<Item> RELBY_K23 = ITEMS.register("relby_k23",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 12),
                            FiringMode.STUN, new BlasterStats(15, 10f, 2f, 0))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.RELBY_K23));
    public static final DeferredItem<Item> RELBY_V10 = ITEMS.register("relby_v10",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.8f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.CHARGED, new BlasterStats(0, 0f, 0f, 22),
                            FiringMode.SNIPER, new BlasterStats(0, 0f, 0f, 18),
                            FiringMode.LAUNCHER, new BlasterStats(0, 0f, 0f, 4))),
                    List.of(FiringMode.CHARGED, FiringMode.SNIPER, FiringMode.LAUNCHER),
                    FiringMode.SNIPER, GasType.TIBANNA, Classification.SNIPER, BlasterName.RELBY_V10));
    public static final DeferredItem<Item> RENEGADE = ITEMS.register("renegade",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 17))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.RENEGADE));
    public static final DeferredItem<Item> RG4D = ITEMS.register("rg4d",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.2f, 50, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 7))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.RG4D));
    public static final DeferredItem<Item> RIG420 = ITEMS.register("rig420",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.5f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 14))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.RIG420));
    public static final DeferredItem<Item> RK3 = ITEMS.register("rk3",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 75, 2, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 9),
                            FiringMode.BURST, new BlasterStats(0, 0f, 0f, 4),
                            FiringMode.STUN, new BlasterStats(15, 10f, 2f, 0))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.BURST, FiringMode.STUN),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.RK3));
    public static final DeferredItem<Item> RSKF44 = ITEMS.register("rskf44",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 16))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.RSKF44));
    public static final DeferredItem<Item> RT97C = ITEMS.register("rt97c",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.3f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new BlasterStats(2, 0.9f, 4.5f, 5))),
                    List.of(FiringMode.FULL_AUTO),
                    FiringMode.FULL_AUTO, GasType.TIBANNA, Classification.REPEATER, BlasterName.RT97C));
    public static final DeferredItem<Item> RUGER_BLASTER = ITEMS.register("ruger_blaster",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.3f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 13),
                            FiringMode.STUN, new BlasterStats(15, 10f, 2f, 0))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.RUGER_BLASTER));
    public static final DeferredItem<Item> S5 = ITEMS.register("s5",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.5f, 20, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 13),
                            FiringMode.STUN, new BlasterStats(15, 10f, 2f, 0))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    FiringMode.SEMI_AUTO, GasType.SPIN_SEALED_TIBANNA, Classification.PISTOL, BlasterName.S5));
    public static final DeferredItem<Item> S195 = ITEMS.register("s195",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.6f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 12),
                            FiringMode.STUN, new BlasterStats(15, 10f, 2f, 0))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.S195));
    public static final DeferredItem<Item> SACROS_K11 = ITEMS.register("sacros_k11",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.6f, 150, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 16))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.SIG, Classification.PISTOL, BlasterName.SACROS_K11));
    public static final DeferredItem<Item> SATINES_LAMENT = ITEMS.register("satines_lament",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.6f, 100, 2, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 17),
                            FiringMode.BURST, new BlasterStats(0, 0f, 0f, 7),
                            FiringMode.CHARGED, new BlasterStats(0, 0f, 0f, 20),
                            FiringMode.STUN, new BlasterStats(15, 10f, 2f, 0))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.BURST, FiringMode.CHARGED, FiringMode.STUN),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.SATINES_LAMENT));
    public static final DeferredItem<Item> SE14C = ITEMS.register("se14c",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 200, 2, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new BlasterStats(3, 0.6f, 5.1f, 7))),
                    List.of(FiringMode.FULL_AUTO),
                    FiringMode.FULL_AUTO, GasType.TIBANNA, Classification.REPEATER, BlasterName.SE14C));
    public static final DeferredItem<Item> SE14R = ITEMS.register("se14r",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.6f, 400, 2, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(8, 1.5f, 4f, 9),
                            FiringMode.BURST, new BlasterStats(16, 0.7f, 6.4f, 6),
                            FiringMode.STUN, new BlasterStats(15, 10f, 2f, 0))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.BURST, FiringMode.STUN),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.SE14R));
    public static final DeferredItem<Item> SEDGLEYS_MK_5 = ITEMS.register("sedgleys_mk_5",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 250, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 17))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.SEDGLEYS_MK_5));
    public static final DeferredItem<Item> SEREXIM_MK_5 = ITEMS.register("serexim_mk_5",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.6f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 14))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.SEREXIM_MK_5));
    public static final DeferredItem<Item> SHADOW_TROOPER_BLASTER = ITEMS.register("shadow_trooper_blaster",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.7f, 200, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 16),
                            FiringMode.CHARGED, new BlasterStats(0, 0f, 0f, 19),
                            FiringMode.STUN, new BlasterStats(15, 10f, 2f, 0))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.CHARGED, FiringMode.STUN),
                    FiringMode.SEMI_AUTO, GasType.TIBANNAX, Classification.RIFLE, BlasterName.SHADOW_TROOPER_BLASTER));
    public static final DeferredItem<Item> SHARD3A = ITEMS.register("shard3a",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.3f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 11))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.SHARD3A));
    public static final DeferredItem<Item> SK32 = ITEMS.register("sk32",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.5f, 250, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 15),
                            FiringMode.CHARGED, new BlasterStats(0, 0f, 0f, 18))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.CHARGED),
                    FiringMode.SEMI_AUTO, GasType.MAGNETIZED_SIG, Classification.PISTOL, BlasterName.SK32));
    public static final DeferredItem<Item> SNUBBLE = ITEMS.register("snubble",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.1f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 14),
                            FiringMode.STUN, new BlasterStats(15, 10f, 2f, 0))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.STUN),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.CARBINE, BlasterName.SNUBBLE));
    public static final DeferredItem<Item> STEYR43 = ITEMS.register("steyr43",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.3f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 16))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.STEYR43));
    public static final DeferredItem<Item> SX21 = ITEMS.register("sx21",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 150, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SCATTER, new BlasterStats(0, 0f, 0f, 6))),
                    List.of(FiringMode.SCATTER),
                    FiringMode.SCATTER, GasType.TIBANNA, Classification.SCATTER, BlasterName.SX21));
    public static final DeferredItem<Item> T6 = ITEMS.register("t6",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.2f, 25, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 15))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.T6));
    public static final DeferredItem<Item> T7_ION_DISRUPTOR = ITEMS.register("t7_ion_disruptor",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 30, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.CHARGED, new BlasterStats(0, 0f, 0f, 25))),
                    List.of(FiringMode.CHARGED),
                    FiringMode.CHARGED, GasType.TIBANNA, Classification.DISRUPTOR, BlasterName.T7_ION_DISRUPTOR));
    public static final DeferredItem<Item> T21 = ITEMS.register("t21",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 150, 3, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.BURST, new BlasterStats(14, 1f, 2f, 13))),
                    List.of(FiringMode.BURST),
                    FiringMode.BURST, GasType.TIBANNA, Classification.REPEATER, BlasterName.T21));
    public static final DeferredItem<Item> T21B = ITEMS.register("t21b",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.6f, 150, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(10, 2.3f, 1.4f, 16))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.RIFLE, BlasterName.T21B));
    public static final DeferredItem<Item> TCA_PRO = ITEMS.register("tca_pro",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.2f, 200, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 14))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.TCA_PRO));
    public static final DeferredItem<Item> TL50 = ITEMS.register("tl50",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 250, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new BlasterStats(0, 0f, 0f, 4),
                            FiringMode.CHARGED, new BlasterStats(0, 0f, 0f, 6))),
                    List.of(FiringMode.FULL_AUTO, FiringMode.CHARGED),
                    FiringMode.FULL_AUTO, GasType.TIBANNA, Classification.REPEATER, BlasterName.TL50));
    public static final DeferredItem<Item> TOMSUN97 = ITEMS.register("tomsun97",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 14),
                            FiringMode.FULL_AUTO, new BlasterStats(0, 0f, 0f, 4))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.CHARGED),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.REPEATER, BlasterName.TOMSUN97));
    public static final DeferredItem<Item> TYPE14 = ITEMS.register("type14",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.2f, 250, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 13))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.TYPE14));
    public static final DeferredItem<Item> UMBARAN_PISTOL = ITEMS.register("umbaran_pistol",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.6f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 18))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.SPIN_SEALED_TIBANNA, Classification.PISTOL, BlasterName.UMBARAN_PISTOL));
    public static final DeferredItem<Item> VALKEN38X = ITEMS.register("valken38x",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.8f, 14, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new BlasterStats(0, 0f, 0f, 16))),
                    List.of(FiringMode.SNIPER),
                    FiringMode.SNIPER, GasType.IONIZED_TIBANNA, Classification.SNIPER, BlasterName.VALKEN38X));
    public static final DeferredItem<Item> VANGUARD_SCATTER = ITEMS.register("vanguard_scatter",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 30, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SCATTER, new BlasterStats(0, 0f, 0f, 6))),
                    List.of(FiringMode.SCATTER),
                    FiringMode.SCATTER, GasType.TIBANNA, Classification.SCATTER, BlasterName.VANGUARD_SCATTER));
    public static final DeferredItem<Item> VECT_UZI = ITEMS.register("vect_uzi",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.3f, 200, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 14),
                            FiringMode.FULL_AUTO, new BlasterStats(0, 0f, 0f, 4))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.FULL_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.REPEATER, BlasterName.VECT_UZI));
    public static final DeferredItem<Item> VERPINE_SHATTER = ITEMS.register("verpine_shatter",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.9f, 20, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 15))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.SLUGTHROWER, BlasterName.VERPINE_SHATTER));
    public static final DeferredItem<Item> VULK_TAU623_ROTARY = ITEMS.register("vulk_tau623_rotary",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.3f, 500, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new BlasterStats(0, 0f, 0f, 5))),
                    List.of(FiringMode.FULL_AUTO),
                    FiringMode.FULL_AUTO, GasType.TIBANNA, Classification.REPEATER, BlasterName.VULK_TAU623_ROTARY));
    public static final DeferredItem<Item> WALTHER_BLASTER = ITEMS.register("walther_blaster",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.6f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 16))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.WALTHER_BLASTER));
    public static final DeferredItem<Item> WALTHER_LPM_BLASTER = ITEMS.register("walther_lpm_blaster",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.3f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 15))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.WALTHER_LPM_BLASTER));
    public static final DeferredItem<Item> WEBLY_S4 = ITEMS.register("webly_s4",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.0f, 20, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 10))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.WEBLY_S4));
    public static final DeferredItem<Item> WEBTEMP = ITEMS.register("webtemp",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.4f, 150, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 15))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.WEBTEMP));
    public static final DeferredItem<Item> WEEQUAY_LANCE = ITEMS.register("weequay_lance",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.9f, 10, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new BlasterStats(0, 0f, 0f, 18))),
                    List.of(FiringMode.SNIPER),
                    FiringMode.SNIPER, GasType.TIBANNA, Classification.SNIPER, BlasterName.WEEQUAY_LANCE));
    public static final DeferredItem<Item> WEEQUAY_PISTOL = ITEMS.register("weequay_pistol",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.2f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 12))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.WEEQUAY_PISTOL));
    public static final DeferredItem<Item> WEEQUAY_RIFLE = ITEMS.register("weequay_rifle",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 3.0f, 20, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SNIPER, new BlasterStats(0, 0f, 0f, 18))),
                    List.of(FiringMode.SNIPER),
                    FiringMode.SNIPER, GasType.TIBANNA, Classification.SNIPER, BlasterName.WEEQUAY_RIFLE));
    public static final DeferredItem<Item> WESTAR_20 = ITEMS.register("westar_20",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.5f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 15))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.WESTAR_20));
    public static final DeferredItem<Item> WESTAR_34 = ITEMS.register("westar_34",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.5f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(13, 2.4f, 4f, 17))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.WESTAR_34));
    public static final DeferredItem<Item> WESTAR_35 = ITEMS.register("westar_35",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.5f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 17),
                            FiringMode.FULL_AUTO, new BlasterStats(0, 0f, 0f, 17),
                            FiringMode.STUN, new BlasterStats(15, 10f, 2f, 0))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.FULL_AUTO, FiringMode.STUN),
                    FiringMode.SEMI_AUTO, GasType.SIG, Classification.PISTOL, BlasterName.WESTAR_35));
    public static final DeferredItem<Item> WESTARM5 = ITEMS.register("westarm5",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.5f, 350, 2, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(4, 1f, 3f, 12),
                            FiringMode.BURST, new BlasterStats(11, 0.8f, 3.5f, 5),
                            FiringMode.FULL_AUTO, new BlasterStats(3, 1.4f, 3.8f, 3))),
                    List.of(FiringMode.SEMI_AUTO, FiringMode.BURST, FiringMode.FULL_AUTO),
                    FiringMode.FULL_AUTO, GasType.IONIZED_TIBANNA, Classification.RIFLE, BlasterName.WESTARM5));
    public static final DeferredItem<Item> WINCHESTER87 = ITEMS.register("winchester87",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.3f, 250, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SCATTER, new BlasterStats(0, 0f, 0f, 14),
                            FiringMode.STUN, new BlasterStats(15, 10f, 2f, 0))),
                    List.of(FiringMode.SCATTER, FiringMode.STUN),
                    FiringMode.SCATTER, GasType.TIBANNA, Classification.SCATTER, BlasterName.WINCHESTER87));
    public static final DeferredItem<Item> X30 = ITEMS.register("x30",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.3f, 300, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 17))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.X30));
    public static final DeferredItem<Item> X8_NIGHT_SNIPER = ITEMS.register("x8_night_sniper",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.6f, 100, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.SEMI_AUTO, new BlasterStats(0, 0f, 0f, 12))),
                    List.of(FiringMode.SEMI_AUTO),
                    FiringMode.SEMI_AUTO, GasType.TIBANNA, Classification.PISTOL, BlasterName.X8_NIGHT_SNIPER));
    public static final DeferredItem<Item> Z6_ROTARY = ITEMS.register("z6_rotary",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.2f, 200, 0, 1,
                    new EnumMap<>(Map.of(
                            FiringMode.FULL_AUTO, new BlasterStats(2, 0.4f, 5f, 4))),
                    List.of(FiringMode.FULL_AUTO),
                    FiringMode.FULL_AUTO, GasType.IONIZED_TIBANNA, Classification.REPEATER, BlasterName.Z6_ROTARY));
}