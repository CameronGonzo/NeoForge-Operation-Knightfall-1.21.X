package net.uhhitscam.starwars.item;

import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.uhhitscam.starwars.OperationKnightfall;
import net.uhhitscam.starwars.item.custom.BlasterItem;
import net.uhhitscam.starwars.item.custom.GasItem;

import java.util.List;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(OperationKnightfall.MODID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    //Gas Items
    public static final DeferredItem<Item> GAS_CARTRIDGE = ITEMS.registerSimpleItem("gas_cartridge");
    public static final DeferredItem<Item> TIBANNA_GAS = ITEMS.registerItem("tibanna_gas",
            properties -> new GasItem(properties, 6400, 500, "TIBANNA_GAS"), new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> IONIZED_TIBANNA_GAS = ITEMS.registerItem("ionized_tibanna_gas",
            properties -> new GasItem(properties, 5200, 500, "IONIZED_TIBANNA_GAS"), new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> SPIN_SEALED_TIBANNA_GAS = ITEMS.registerItem("spin_sealed_tibanna_gas",
            properties -> new GasItem(properties, 10000, 500, "SPIN_SEALED_TIBANNA_GAS"), new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> TIBANNAX_GAS = ITEMS.registerItem("tibannax_gas",
            properties -> new GasItem(properties, 2800, 50, "TIBANNAX_GAS"), new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> SIG_GAS = ITEMS.registerItem("sig_gas",
            properties -> new GasItem(properties, 8000, 500, "SIG_GAS"), new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> MAGNETIZED_SIG_GAS = ITEMS.registerItem("magnetized_sig_gas",
            properties -> new GasItem(properties, 12800, 500, "MAGNETIZED_SIG_GAS"), new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> SKEVON = ITEMS.registerItem("skevon_gas",
            properties -> new GasItem(properties, 2000, 200, "SKEVON_GAS"), new Item.Properties().stacksTo(1));

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
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.8F, 10, 0,
                    0, 0, 0, 0, 0, 30, List.of("SNIPER"), 0F, 0F, 0F, 0F, 0F, 5F, "SNIPER",
                    0F, 0F, 0F, 0F, 0F, 1F, "TIBANNA_GAS", 0, 0, 0, 0, 0, 10, "SLUGTHROWER"));
    public static final DeferredItem<Item> _84U_HUNTING_RIFLE = ITEMS.register("_84u_hunting_rifle",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.9F, 30, 0,
                    0, 0, 0, 0, 0, 16, List.of("SNIPER"), 0F, 0F, 0F, 0F, 0F, 2F, "SNIPER",
                    0F, 0F, 0F, 0F, 0F, 1.3F, "TIBANNA_GAS", 0, 0, 0, 0, 0, 12, "SNIPER"));
    public static final DeferredItem<Item> _434_DEATHHAMMER = ITEMS.register("_434_deathhammer",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.0F, 50, 0,
                    8, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 1F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    1.6F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 14, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> _773_FIREPUNCHER = ITEMS.register("_773_firepuncher",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    3.1F, 10, 0,
                    0, 0, 0, 0, 0, 9, List.of("SNIPER", "LAUNCHER"), 0F, 0F, 0F, 0F, 0F, 0.7F, "SNIPER",
                    0F, 0F, 0F, 0F, 0F, 0.8F, "IONIZED_TIBANNA_GAS", 0, 0, 0, 0, 0, 17, "SNIPER"));
    public static final DeferredItem<Item> _785MK_FIREPUNCHERX = ITEMS.register("_785mk_firepuncherx",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    3.2F, 20, 0,
                    0, 0, 0, 0, 0, 12, List.of("SNIPER"), 0F, 0F, 0F, 0F, 0F, 1F, "SNIPER",
                    0F, 0F, 0F, 0F, 0F, 1.1F, "TIBANNA_GAS", 0, 0, 0, 0, 0, 19, "SNIPER"));
    public static final DeferredItem<Item> A140 = ITEMS.register("a140",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.1F, 100, 0,
                    7, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0.8F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    1.7F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 8, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> A180 = ITEMS.register("a180",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.2F, 500, 0,
                    7, 0, 4, 0, 0, 20, List.of("SEMI_AUTO", "FULL_AUTO", "SNIPER", "STUN"), 1F, 0F, 1.2F, 0F, 0F, 2F, "SEMI_AUTO",
                    1.3F, 0F, 1.1F, 0F, 0F, 0.8F, "TIBANNA_GAS", 15, 0, 7, 0, 0, 18, "PISTOL"));
    public static final DeferredItem<Item> A280 = ITEMS.register("a280",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.6F, 500, 3,
                    3, 11, 0, 0, 0, 0, List.of("SEMI_AUTO", "BURST"), 1.8F, 1.6F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    2.1F, 2.8F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 15, 9, 0, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> A280C = ITEMS.register("a280c",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.6F, 500, 3,
                    0, 10, 3, 0, 0, 0, List.of("BURST", "FULL_AUTO"), 0F, 1.4F, 1.4F, 0F, 0F, 0F, "FULL_AUTO",
                    0F, 2F, 3.4F, 0F, 0F, 0F, "TIBANNA_GAS", 0, 8, 8, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> A280CFE = ITEMS.register("a280cfe",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.7F, 500, 3,
                    8, 14, 0, 0, 0, 14, List.of("SEMI_AUTO", "BURST", "SNIPER"), 1.6F, 1F, 0F, 0F, 0F, 2F, "BURST",
                    1.7F, 1.3F, 0F, 0F, 0F, 1F, "TIBANNA_GAS", 16, 7, 0, 0, 0, 19, "RIFLE"));
    public static final DeferredItem<Item> A295 = ITEMS.register("a295",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.6F, 500, 0,
                    5, 0, 6, 0, 0, 0, List.of("SEMI_AUTO", "FULL_AUTO"), 1.6F, 0F, 2.2F, 0F, 0F, 0F, "SEMI_AUTO",
                    2.8F, 0F, 3.3F, 0F, 0F, 0F, "TIBANNA_GAS", 15, 0, 5, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> A300 = ITEMS.register("a300",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.7F, 500, 0,
                    0, 0, 3, 0, 0, 0, List.of("FULL_AUTO"), 0F, 0F, 1.6F, 0F, 0F, 0F, "FULL_AUTO",
                    0F, 0F, 4F, 0F, 0F, 0F, "TIBANNA_GAS", 0, 0, 6, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> A310 = ITEMS.register("a310",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.7F, 500, 3,
                    5, 12, 0, 0, 0, 0, List.of("SEMI_AUTO", "BURST"), 1.9F, 1.7F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    2F, 2.9F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 17, 9, 0, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> A350 = ITEMS.register("a350",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.7F, 500, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "FULL_AUTO"), 5F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 15, 0, 6, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> AC177 = ITEMS.register("ac177",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    1.9F, 50, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 5F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 12, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> ACP_REPEATER = ITEMS.register("acp_repeater",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.1F, 200, 0,
                    0, 0, 0, 0, 0, 0, List.of("FULL_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "FULL_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 0, 0, 8, 0, 0, 0, "REPEATER"));
    public static final DeferredItem<Item> AKBARC = ITEMS.register("akbarc",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.5F, 300, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 5F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 15, 0, 0, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> AMBAN_DISRUPTOR = ITEMS.register("amban_disruptor",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    4.0F, 1, 0,
                    0, 0, 0, 0, 0, 50, List.of("SNIPER"), 0F, 0F, 0F, 0F, 0F, 3F, "SNIPER",
                    0F, 0F, 0F, 0F, 0F, 1F, "SIG_GAS", 0, 0, 0, 0, 0, 30, "DISRUPTOR"));
    public static final DeferredItem<Item> APACHE = ITEMS.register("apache",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.3F, 100, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 5F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 12, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> ASTRA40 = ITEMS.register("astra40",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.3F, 100, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 5F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 12, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> AVARIK = ITEMS.register("avarik",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    3.0F, 50, 0,
                    0, 0, 0, 0, 0, 0, List.of("SNIPER"), 0F, 0F, 0F, 0F, 0F, 0F, "SNIPER",
                    0F, 0F, 0F, 0F, 0F, 0F, "IONIZED_TIBANNA_GAS", 0, 0, 0, 0, 0, 16, "SNIPER"));
    public static final DeferredItem<Item> B1NA = ITEMS.register("b1na",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 100, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 5F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 8, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> B22 = ITEMS.register("b22",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.0F, 10, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 5F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 8, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> BALNAB = ITEMS.register("balnab",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.6F, 50, 0,
                    0, 0, 0, 0, 0, 0, List.of("SNIPER"), 0F, 0F, 0F, 0F, 0F, 0F, "SNIPER",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 0, 0, 0, 0, 0, 16, "SNIPER"));
    public static final DeferredItem<Item> BARMST12 = ITEMS.register("barmst12",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.7F, 50, 0,
                    25, 0, 0, 0, 0, 0, List.of("SCATTER"), 5F, 0F, 0F, 0F, 0F, 0F, "SCATTER",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 6, 0, 0, 0, 0, 0, "SCATTER"));
    public static final DeferredItem<Item> BATON_BLASTER = ITEMS.register("baton_blaster",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.1F, 100, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 5F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "IONIZED_TIBANNA_GAS", 14, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> BE09 = ITEMS.register("be09",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.2F, 20, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 5F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 5, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> BE29 = ITEMS.register("be29",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 50, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 5F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 10, 0, 0, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> BERSERKER = ITEMS.register("berserker",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.9F, 20, 0,
                    6, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 2.4F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    3.4F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 17, 0, 0, 0, 0, 0, "SLUGTHROWER"));
    public static final DeferredItem<Item> BH4 = ITEMS.register("bh4",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.5F, 100, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "FULL_AUTO"), 5F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 10, 0, 3, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> BLASTER_SPEAR = ITEMS.register("blaster_spear",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.7F, 150, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 5F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "IONIZED_TIBANNA_GAS", 12, 0, 0, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> BLNDRBUS = ITEMS.register("blndrbus",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 50, 0,
                    25, 0, 0, 0, 0, 0, List.of("SCATTER", "REPULSE", "STUN"), 5F, 0F, 0F, 0F, 0F, 0F, "SCATTER",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 6, 0, 0, 0, 2, 0, "SCATTER"));
    public static final DeferredItem<Item> BLURRG1120 = ITEMS.register("blurrg1120",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.2F, 500, 1,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "BURST", "FULL_AUTO"), 5F, 0F, 0F, 0F, 0F, 0F, "BURST",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 9, 4, 2, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> BM107 = ITEMS.register("bm107",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    3.5F, 120, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "CHARGED"), 5F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 20, 0, 0, 29, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> BOILER_RIFLE = ITEMS.register("boiler_rifle",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.7F, 200, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 5F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 15, 0, 0, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> BOONTA_BLASTER = ITEMS.register("boonta_blaster",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.0F, 50, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 5F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 11, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> BOWCASTER = ITEMS.register("bowcaster",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.9F, 75, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "CHARGED"), 5F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "SPIN_SEALED_TIBANNA_GAS", 18, 0, 0, 25, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> BR14 = ITEMS.register("br14",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.5F, 50, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 5F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "IONIZED_TIBANNA_GAS", 5, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> BRYAR_RIFLE = ITEMS.register("bryar_rifle",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.3F, 50, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "CHARGED"), 5F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 15, 0, 0, 20, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> C10 = ITEMS.register("c10",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 100, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "STUN"), 5F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 17, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> C96 = ITEMS.register("c96",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.7F, 100, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "STUN"), 5F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 14, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> CA87 = ITEMS.register("ca87",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.5F, 30, 0,
                    25, 0, 0, 0, 28, 0, List.of("SCATTER", "REPULSE", "STUN"), 5F, 0F, 0F, 0F, 7F, 0F, "SCATTER",
                    10F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 6, 0, 0, 0, 2, 0, "SCATTER"));
    public static final DeferredItem<Item> CAIJ_VANDAS_BLASTER_PISTOL = ITEMS.register("caij_vandas_blaster_pistol",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.5F, 200, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "CHARGED"), 5F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 15, 0, 0, 20, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> CC420 = ITEMS.register("cc420",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.6F, 100, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 5F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 12, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> CJ9_BO_RIFLE = ITEMS.register("cj9_bo_rifle",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.5F, 500, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 5F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 11, 0, 0, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> CORPO_RIFLE = ITEMS.register("corpo_rifle",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.5F, 150, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 5F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 13, 0, 0, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> CR2 = ITEMS.register("cr2",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.3F, 100, 0,
                    0, 0, 0, 0, 0, 0, List.of("FULL_AUTO", "STUN"), 0F, 0F, 0F, 0F, 0F, 0F, "FULL_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "IONIZED_TIBANNA_GAS", 0, 0, 2, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> CS14 = ITEMS.register("cs14",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.2F, 50, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "STUN"), 5F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 4, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> CYCLER_RIFLE = ITEMS.register("cycler_rifle",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    3.9F, 15, 0,
                    0, 0, 0, 0, 0, 25, List.of("SNIPER"), 0F, 0F, 0F, 0F, 0F, 3F, "SNIPER",
                    0F, 0F, 0F, 0F, 0F, 1F, "SPIN_SEALDED_TIBANNA_GAS", 0, 0, 0, 0, 0, 18, "SNIPER"));
    public static final DeferredItem<Item> CZERKA_ADVENTURER = ITEMS.register("czerka_adventurer",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    4.0F, 20, 0,
                    0, 0, 0, 0, 0, 0, List.of("SNIPER"), 0F, 0F, 0F, 0F, 0F, 0F, "SNIPER",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 0, 0, 0, 0, 0, 19, "SLUGTHROWER"));
    public static final DeferredItem<Item> DARK_TROOPER_RIFLE = ITEMS.register("dark_trooper_rifle",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.5F, 500, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 5F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 17, 0, 0, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> DC12U = ITEMS.register("dc12u",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 500, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 5F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "IONIZED_TIBANNA_GAS", 15, 0, 0, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> DC15A = ITEMS.register("dc15a",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.6F, 500, 0,
                    7, 0, 3, 0, 0, 0, List.of("SEMI_AUTO", "FULL_AUTO", "STUN"), 1.6F, 0F, 1F, 0F, 0F, 0F, "SEMI_AUTO",
                    1F, 0F, 3F, 0F, 0F, 0F, "IONIZED_TIBANNA_GAS", 17, 0, 5, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> DC15LE = ITEMS.register("dc15le",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.5F, 500, 0,
                    0, 0, 3, 0, 0, 0, List.of("FULL_AUTO"), 0F, 0F, 1.7F, 0F, 0F, 0F, "FULL_AUTO",
                    0F, 0F, 4.7F, 0F, 0F, 0F, "IONIZED_TIBANNA_GAS", 0, 0, 6, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> DC15S_CARBINE = ITEMS.register("dc15s_carbine",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 500, 0,
                    10, 0, 5, 0, 0, 0, List.of("SEMI_AUTO", "FULL_AUTO", "STUN"), 1.7F, 0F, 1.3F, 0F, 0F, 0F, "SEMI_AUTO",
                    3.5F, 0F, 4.4F, 0F, 0F, 0F, "IONIZED_TIBANNA_GAS", 12, 0, 5, 0, 0, 0, "CARBINE"));
    public static final DeferredItem<Item> DC15S_SIDEARM = ITEMS.register("dc15s_sidearm",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 15, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "STUN"), 5F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "IONIZED_TIBANNA_GAS", 6, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> DC15X = ITEMS.register("dc15x",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.8F, 6, 0,
                    0, 0, 0, 0, 0, 0, List.of("CHARGED", "SNIPER"), 0F, 0F, 0F, 0F, 0F, 0F, "SNIPER",
                    0F, 0F, 0F, 0F, 0F, 0F, "IONIZED_TIBANNA_GAS", 0, 0, 0, 23, 0, 17, "SNIPER"));
    public static final DeferredItem<Item> DC17 = ITEMS.register("dc17",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 50, 0,
                    3, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "STUN"), 0.6F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    6F, 0F, 0F, 0F, 0F, 0F, "IONIZED_TIBANNA_GAS", 14, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> DC17M = ITEMS.register("dc17m",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.5F, 300, 0,
                    0, 0, 0, 0, 0, 0, List.of("FULL_AUTO", "CHARGED", "SNIPER", "LAUNCHER"), 0F, 0F, 0F, 0F, 0F, 0F, "FULL_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "IONIZED_TIBANNA_GAS", 0, 0, 5, 25, 0, 18, "REPEATER"));
    public static final DeferredItem<Item> DC17S = ITEMS.register("dc17s",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.5F, 50, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "IONIZED_TIBANNA_GAS", 16, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> DC19 = ITEMS.register("dc19",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.6F, 10, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNAX_GAS", 15, 0, 0, 0, 0, 0, "CARBINE"));
    public static final DeferredItem<Item> DE10 = ITEMS.register("de10",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.7F, 100, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "IONIZED_TIBANNA_GAS", 14, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> DEFTECH = ITEMS.register("deftech",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.5F, 300, 2,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "BURST"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "SIG_GAS", 17, 7, 0, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> DFD1 = ITEMS.register("dfd1",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.8F, 6, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 18, 0, 0, 0, 0, 0, "SLUGTHROWER"));
    public static final DeferredItem<Item> DG29 = ITEMS.register("dg29",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 100, 0,
                    0, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0.2F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 11, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> DH16 = ITEMS.register("dh16",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.3F, 100, 0,
                    0, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0.5F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 7, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> DH17 = ITEMS.register("dh17",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.5F, 500, 2,
                    11, 14, 4, 0, 0, 0, List.of("SEMI_AUTO", "BURST", "FULL_AUTO", "STUN"), 2.7F, 1.4F, 2.1F, 0F, 0F, 0F, "SEMI_AUTO",
                    2F, 2.7F, 3.9F, 0F, 0F, 0F, "TIBANNA_GAS", 17, 7, 5, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> DH23 = ITEMS.register("dh23",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.6F, 100, 0,
                    0, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    1F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 14, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> DH447 = ITEMS.register("dh447",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.7F, 100, 0,
                    25, 0, 0, 0, 0, 0, List.of("SNIPER"), 0.3F, 0.9F, 1.7F, 0F, 0F, 0F, "SNIPER",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 0, 0, 0, 0, 0, 17, "SNIPER"));
    public static final DeferredItem<Item> DL18 = ITEMS.register("dl18",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 100, 0,
                    0, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "STUN"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    1.5F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 12, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> DL21 = ITEMS.register("dl21",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 100, 0,
                    0, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    2F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 13, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> DL44 = ITEMS.register("dl44",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.5F, 50, 0,
                    7, 0, 0, 0, 0, 15, List.of("SEMI_AUTO", "SNIPER"), 2.8F, 0F, 0F, 0F, 0F, 3.6F, "SEMI_AUTO",
                    2.9F, 0F, 0F, 0F, 0F, 2.2F, "TIBANNA_GAS", 16, 0, 0, 0, 0, 20, "PISTOL"));
    public static final DeferredItem<Item> DLS12 = ITEMS.register("dls12",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.3F, 500, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0.7F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "IONIZED_TIBANNA_GAS", 14, 0, 0, 0, 0, 0, "CARBINE"));
    public static final DeferredItem<Item> DLT18 = ITEMS.register("dlt18",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.2F, 200, 0,
                    0, 0, 0, 0, 0, 0, List.of("FULL_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "FULL_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 0, 0, 8, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> DLT19 = ITEMS.register("dlt19",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 500, 0,
                    0, 0, 0, 0, 0, 0, List.of("FULL_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "FULL_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 0, 0, 9, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> DLT19D = ITEMS.register("dlt19d",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 10, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0.7F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 14, 0, 0, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> DLT19X = ITEMS.register("dlt19x",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.6F, 40, 0,
                    0, 0, 0, 0, 0, 0, List.of("SNIPER"), 0F, 0F, 0F, 0F, 0F, 0F, "SNIPER",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 0, 0, 0, 0, 0, 18, "SNIPER"));
    public static final DeferredItem<Item> DLT20A = ITEMS.register("dlt20a",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.6F, 300, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0.7F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "SPIN_SEALED_TIBANNA_GAS", 17, 0, 0, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> DN_BOLT_CASTER = ITEMS.register("dn_bolt_caster",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.0F, 300, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0.7F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "IONIZED_TIBANNA_GAS", 20, 0, 0, 0, 0, 0, "DISRUPTOR"));
    public static final DeferredItem<Item> DP23 = ITEMS.register("dp23",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.3F, 300, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "CHARGED"), 0.7F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "IONIZED_TIBANNA_GAS", 13, 0, 0, 18, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> DRESSELLIAN_PROJECTILE_RIFLE = ITEMS.register("dressellian_projectile_rifle",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    3.8F, 15, 0,
                    0, 0, 0, 0, 0, 0, List.of("SNIPER"), 0F, 0F, 0F, 0F, 0F, 0F, "SNIPER",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 0, 0, 0, 0, 0, 19, "SLUGTHROWER"));
    public static final DeferredItem<Item> DT12 = ITEMS.register("dt12",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.2F, 300, 0,
                    7, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 1.1F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    4.9F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 9, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> DT15 = ITEMS.register("dt15",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.1F, 200, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0.7F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 15, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> DT29 = ITEMS.register("dt29",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.8F, 6, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0.7F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 16, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> DT57 = ITEMS.register("dt57",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.1F, 400, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0.7F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 8, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> DX13 = ITEMS.register("dx13",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.3F, 100, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "FULL_AUTO", "STUN"), 0.7F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 17, 0, 7, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> E5 = ITEMS.register("e5",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.1F, 500, 0,
                    6, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "STUN"), 1.9F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    4F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 6, 0, 0, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> E5_BX = ITEMS.register("e5_bx",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.3F, 500, 0,
                    9, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "STUN"), 2F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    3.7F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 12, 0, 0, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> E5_CE = ITEMS.register("e5_ce",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.2F, 500, 0,
                    8, 0, 3, 0, 0, 0, List.of("SEMI_AUTO", "FULL_AUTO"), 2.7F, 0F, 1.5F, 0F, 0F, 0F, "SEMI_AUTO",
                    3.4F, 0F, 5F, 0F, 0F, 0F, "TIBANNA_GAS", 13, 0, 5, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> E5C = ITEMS.register("e5c",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.0F, 500, 0,
                    0, 0, 2, 0, 0, 0, List.of("FULL_AUTO"), 0F, 0F, 0.6F, 0F, 0F, 0F, "FULL_AUTO",
                    0F, 0F, 5.6F, 0F, 0F, 0F, "TIBANNA_GAS", 0, 0, 4, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> E5S = ITEMS.register("e5s",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.8F, 4, 0,
                    25, 0, 0, 0, 0, 0, List.of("SNIPER"), 0.7F, 0F, 0F, 0F, 0F, 0F, "SNIPER",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 17, 0, 0, 0, 0, 0, "SNIPER"));
    public static final DeferredItem<Item> E10 = ITEMS.register("e10",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                   2.6F, 500, 2,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "BURST"), 0.7F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 8, 5, 0, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> E10_5 = ITEMS.register("e10_5",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.6F, 500, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0.7F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 9, 0, 0, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> E10R = ITEMS.register("e10r",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.8F, 500, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0.7F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 16, 0, 0, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> E11_CARBINE = ITEMS.register("e11_carbine",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 500, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "STUN"), 0.7F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 9, 0, 0, 0, 0, 0, "CARBINE"));
    public static final DeferredItem<Item> E11_RIFLE = ITEMS.register("e11_rifle",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.7F, 500, 0,
                    10, 0, 5, 0, 0, 0, List.of("SEMI_AUTO", "FULL_AUTO", "STUN"), 1F, 0F, 0.8F, 0F, 0F, 0F, "SEMI_AUTO",
                    5F, 0F, 7.8F, 0F, 0F, 0F, "TIBANNA_GAS", 13, 0, 6, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> E11B = ITEMS.register("e11b",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.7F, 500, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "STUN"), 0.7F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 9, 0, 0, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> E11D = ITEMS.register("e11d",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.6F, 500, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "STUN"), 0.7F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 16, 0, 0, 0, 0, 0, "CARBINE"));
    public static final DeferredItem<Item> E11S = ITEMS.register("e11s",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    3.0F, 6, 0,
                    0, 0, 0, 0, 0, 0, List.of("SNIPER"), 0F, 0F, 0F, 0F, 0F, 0F, "SNIPER",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 0, 0, 0, 0, 0, 17, "SNIPER"));
    public static final DeferredItem<Item> E17D = ITEMS.register("e17d",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.8F, 50, 0,
                    0, 0, 0, 0, 0, 0, List.of("SNIPER"), 0F, 0F, 0F, 0F, 0F, 0F, "SNIPER",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 0, 0, 0, 0, 0, 16, "SNIPER"));
    public static final DeferredItem<Item> E22 = ITEMS.register("e22",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.6F, 500, 2,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "BURST", "FULL_AUTO"), 0.7F, 0F, 0F, 0F, 0F, 0F, "FULL_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 9, 6, 4, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> EC17 = ITEMS.register("ec17",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.2F, 100, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "STUN"), 0.7F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 6, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> EE3 = ITEMS.register("ee3",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.7F, 300, 2,
                    7, 15, 0, 0, 0, 0, List.of("SEMI_AUTO", "BURST"), 2F, 1F, 0F, 0F, 0F, 0F, "BURST",
                    2.4F, 2.7F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 15, 6, 0, 0, 0, 0, "CARBINE"));
    public static final DeferredItem<Item> EE4 = ITEMS.register("ee4",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 500, 1,
                    25, 0, 0, 0, 0, 0, List.of("BURST"), 0F, 0.5F, 0F, 0F, 0F, 0F, "BURST",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 0, 8, 0, 0, 0, 0, "CARBINE"));
    public static final DeferredItem<Item> ELG3A = ITEMS.register("elg3a",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.5F, 500, 0,
                    5, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "STUN"), 1.8F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    4F, 0F, 0F, 0F, 0F, 0F, "SPIN_SEALED_TIBANNA_GAS", 13, 0, 0, 0, 0, 0, "PISTOL"));
//    public static final DeferredItem<Item> EWEB = ITEMS.register("eweb",
//            () -> new BlasterItem(new Item.Properties().stacksTo(1),
//                    4.0F, 500, 0,
//                    25, 0, 0, 0, 0, 0, List.of("FULL_AUTO"), 0F, 0.5F, 0F, 0F, 0F, 0F, "FULL_AUTO",
//                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 0, 0, 0, 0, 0, 0, "REPEATER"));
    public static final DeferredItem<Item> FC1_FLECHETTE_LAUNCHER = ITEMS.register("fc1_flechette_launcher",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.9F, 6, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 19, 0, 0, 0, 0, 0, "SLUGTHROWER"));
    public static final DeferredItem<Item> FLINTLOQ_PISTOL = ITEMS.register("flintloq_pistol",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.1F, 100, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 14, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> FLINTLOQ_RIFLE = ITEMS.register("flintloq_rifle",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 5, 0,
                    25, 0, 0, 0, 0, 0, List.of("SNIPER"), 0F, 0F, 0F, 0F, 0F, 0F, "SNIPER",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 0, 0, 0, 0, 0, 17, "SNIPER"));
    public static final DeferredItem<Item> FLITE37 = ITEMS.register("flite37",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 100, 0,
                    25, 0, 0, 0, 0, 0, List.of("SCATTER"), 0F, 0F, 0F, 0F, 0F, 0F, "SCATTER",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 18, 0, 0, 0, 0, 0, "SCATTER"));
    public static final DeferredItem<Item> FN57 = ITEMS.register("fn57",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.3F, 200, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 14, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> FP45 = ITEMS.register("fp45",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.0F, 15, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 10, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> GALAAR15 = ITEMS.register("galaar15",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.5F, 500, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "SIG_GAS", 13, 0, 0, 0, 0, 0, "CARBINE"));
    public static final DeferredItem<Item> GALAR90 = ITEMS.register("galar90",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.9F, 50, 0,
                    0, 0, 0, 0, 0, 0, List.of("SNIPER"), 0F, 0F, 0F, 0F, 0F, 0F, "SNIPER",
                    0F, 0F, 0F, 0F, 0F, 0F, "SIG_GAS", 0, 0, 0, 0, 0, 15, "SNIPER"));
    public static final DeferredItem<Item> GE36 = ITEMS.register("ge36",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    3.0F, 100, 0,
                    0, 0, 0, 0, 0, 0, List.of("SNIPER"), 0F, 0F, 0F, 0F, 0F, 0F, "SNIPER",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 0, 0, 0, 0, 0, 19, "SNIPER"));
    public static final DeferredItem<Item> GL77 = ITEMS.register("gl77",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.3F, 300, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "IONIZED_TIBANNA_GAS", 14, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> HF94 = ITEMS.register("hf94",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.6F, 50, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 17, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> IB94 = ITEMS.register("ib94",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.6F, 200, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 15, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> IMPERIAL_SUPERCOMMANDO_BLASTER = ITEMS.register("imperial_supercommando_blaster",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.7F, 500, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 16, 0, 0, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> IQA11 = ITEMS.register("iqa11",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.8F, 7, 0,
                    0, 0, 0, 0, 0, 0, List.of("SNIPER"), 0F, 0F, 0F, 0F, 0F, 0F, "SNIPER",
                    0F, 0F, 0F, 0F, 0F, 0F, "IONIZED_TIBANNA_GAS", 0, 0, 0, 0, 0, 16, "SNIPER"));
    public static final DeferredItem<Item> JEZALI_CYCLER_RIFLE = ITEMS.register("jezali_cycler_rifle",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    3.3F, 20, 0,
                    0, 0, 0, 0, 0, 0, List.of("SNIPER"), 0F, 0F, 0F, 0F, 0F, 0F, "SNIPER",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 0, 0, 0, 0, 0, 20, "SLUGTHROWER"));
    public static final DeferredItem<Item> JND41 = ITEMS.register("jnd41",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.5F, 300, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "CHARGED"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 15, 0, 0, 19, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> K16_BRYAR_PISTOL = ITEMS.register("k16_bryar_pistol",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.2F, 50, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "CHARGED"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 12, 0, 0, 17, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> KA74 = ITEMS.register("ka74",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 500, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "FULL_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 14, 0, 5, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> KISTEER_1284 = ITEMS.register("kisteer_1284",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.9F, 20, 0,
                    0, 0, 0, 0, 0, 0, List.of("SNIPER"), 0F, 0F, 0F, 0F, 0F, 0F, "SNIPER",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 0, 0, 0, 0, 0, 18, "SLUGTHROWER"));
    public static final DeferredItem<Item> KOCH9S = ITEMS.register("koch9s",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.1F, 100, 0,
                    0, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 14, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> KRIE4 = ITEMS.register("krie4",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.2F, 100, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 12, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> KUEGET_LN21 = ITEMS.register("kueget_ln21",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.2F, 100, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "STUN"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 7, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> KYD21 = ITEMS.register("kyd21",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 75, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 10, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> L5 = ITEMS.register("l5",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 300, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 14, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> L60 = ITEMS.register("l60",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 500, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 16, 0, 0, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> LEUCHT42 = ITEMS.register("leucht42",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.6F, 150, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 14, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> LL30 = ITEMS.register("ll30",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.7F, 200, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 16, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> LUG_PO8 = ITEMS.register("lug_po8",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.5F, 100, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 13, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> LW896 = ITEMS.register("lw896",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.6F, 100, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 11, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> M12 = ITEMS.register("m12",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.6F, 500, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "FULL_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 16, 0, 8, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> M19A1 = ITEMS.register("m19a1",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.1F, 200, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 15, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> M32 = ITEMS.register("m32",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.6F, 300, 0,
                    0, 0, 0, 0, 0, 0, List.of("FULL_AUTO"), 0F, 0F, 0.2F, 0F, 0F, 0F, "FULL_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 0, 0, 9, 0, 0, 0, "REPEATER"));
    public static final DeferredItem<Item> M41 = ITEMS.register("m41",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.6F, 400, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "FULL_AUTO"), 0F, 0F, 0.4F, 0F, 0F, 0F, "FULL_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 16, 0, 9, 0, 0, 0, "REPEATER"));
    public static final DeferredItem<Item> M45 = ITEMS.register("m45",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.6F, 500, 0,
                    0, 0, 0, 0, 0, 0, List.of("FULL_AUTO"), 0F, 0F, 0.6F, 0F, 0F, 0F, "FULL_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 0, 0, 11, 0, 0, 0, "REPEATER"));
    public static final DeferredItem<Item> M55 = ITEMS.register("m55",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.7F, 300, 0,
                    0, 0, 0, 0, 0, 0, List.of("FULL_AUTO"), 0F, 0F, 0.8F, 0F, 0F, 0F, "FULL_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 0, 0, 10, 0, 0, 0, "REPEATER"));
    public static final DeferredItem<Item> M61 = ITEMS.register("m61",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.6F, 500, 0,
                    0, 0, 0, 0, 0, 0, List.of("FULL_AUTO"), 0F, 0F, 1F, 0F, 0F, 0F, "FULL_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 0, 0, 19, 0, 0, 0, "REPEATER"));
    public static final DeferredItem<Item> MARG_MCM = ITEMS.register("marg_mcm",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 100, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 15, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> MK_II_PALADIN = ITEMS.register("mk_ii_paladin",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.5F, 500, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "IONIZED_TIBANNA_GAS", 16, 0, 0, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> MODEL_57 = ITEMS.register("model_57",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.7F, 100, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "STUN"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 15, 0, 0, 0, 0, 0, "PISTOL"));
//    public static final DeferredItem<Item> MORTAR = ITEMS.register("mortar",
//            () -> new BlasterItem(new Item.Properties().stacksTo(1),
//                    3.0F, 1.0F, 300, 8,
//                    4, 20, 0, 0, 0, List.of("SEMI_AUTO", "BURST"), 0F, 0.5F, 0F, 0F, 0F, "BURST", "TIBANNA_GAS",
//                    "CARBINE"));
    public static final DeferredItem<Item> MOTTO_MK_4 = ITEMS.register("motto_mk_4",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 200, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "REPULSE", "STUN"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 15, 0, 0, 0, 1, 0, "RIFLE"));
    public static final DeferredItem<Item> MW20_BRYAR_PISTOL = ITEMS.register("mw20_bryar_pistol",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.5F, 100, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "CHARGED"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 15, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> MWC35C = ITEMS.register("mwc35c",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.3F, 500, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "FULL_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "FULL_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 17, 0, 9, 0, 0, 0, "REPEATER"));
    public static final DeferredItem<Item> NAMBU14 = ITEMS.register("nambu14",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.2F, 100, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 13, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> NEO_CRUSADER_RIFLE = ITEMS.register("neo_crusader_rifle",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.5F, 300, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "CHARGED"), 0F, 0.5F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 17, 0, 0, 20, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> NIGHT_STINGER = ITEMS.register("night_stinger",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.9F, 5, 0,
                    25, 0, 0, 0, 0, 0, List.of("CHARGED", "SNIPER"), 0F, 0F, 0F, 0F, 0F, 0F, "SNIPER",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNAX_GAS", 0, 0, 0, 19, 0, 15, "SNIPER"));
    public static final DeferredItem<Item> NIGHT_WIND_RIFLE = ITEMS.register("night_wind_rifle",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 300, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 16, 0, 0, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> NT242 = ITEMS.register("nt242",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    3.0F, 75, 0,
                    25, 0, 0, 0, 0, 0, List.of("SNIPER"), 0F, 0F, 0F, 0F, 0F, 0F, "SNIPER",
                    0F, 0F, 0F, 0F, 0F, 0F, "SPIN_SEALED_TIBANNA_GAS", 0, 0, 0, 0, 0, 18, "SNIPER"));
    public static final DeferredItem<Item> OK98 = ITEMS.register("ok98",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.6F, 1000, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 15, 0, 0, 0, 0, 0, "CARBINE"));
    public static final DeferredItem<Item> OUTLAND_RIFLE = ITEMS.register("outland_rifle",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.8F, 8, 0,
                    25, 0, 0, 0, 0, 0, List.of("SNIPER"), 0F, 0F, 0F, 0F, 0F, 0F, "SNIPER",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 0, 0, 0, 0, 0, 16, "SLUGTHROWER"));
    public static final DeferredItem<Item> P38 = ITEMS.register("p38",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.6F, 300, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 18, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> PANIC_PISTOL = ITEMS.register("panic_pistol",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.5F, 5, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0.5F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 9, 0, 0, 0, 0, 0, "SLUGTHROWER"));
    public static final DeferredItem<Item> PCC_PROJECTOR = ITEMS.register("pcc_projector",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.3F, 150, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 17, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> PK23 = ITEMS.register("pk23",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.9F, 40, 0,
                    0, 0, 0, 0, 0, 0, List.of("SNIPER"), 0F, 0F, 0F, 0F, 0F, 0F, "SNIPER",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 0, 0, 0, 0, 0, 19, "SNIPER"));
    public static final DeferredItem<Item> POWER_5 = ITEMS.register("power_5",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.6F, 50, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "CHARGED"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 14, 0, 0, 18, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> PREMIER = ITEMS.register("premier",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.2F, 200, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 15, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> Q2 = ITEMS.register("q2",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.0F, 50, 0,
                    11, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 1.3F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    6F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 11, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> QUARREN_RIFLE = ITEMS.register("quarren_rifle",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.5F, 300, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 15, 0, 0, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> RECIPROCATING_QUAD_BLASTER_CANNON = ITEMS.register("reciprocating_quad_blaster_cannon",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 500, 0,
                    0, 0, 0, 0, 0, 0, List.of("FULL_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "FULL_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "IONIZED_TIBANNA_GAS", 0, 0, 12, 0, 0, 0, "REPEATER"));
    public static final DeferredItem<Item> RELBY_K23 = ITEMS.register("relby_k23",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 100, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "STUN"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 8, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> RELBY_V10 = ITEMS.register("relby_v10",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.8F, 100, 0,
                    0, 0, 0, 0, 0, 0, List.of("CHARGED", "SNIPER", "LAUNCHER"), 0F, 0F, 0F, 0F, 0F, 0F, "SNIPER",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 0, 0, 0, 22, 0, 18, "SNIPER"));
    public static final DeferredItem<Item> RENEGADE = ITEMS.register("renegade",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 50, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 17, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> RG4D = ITEMS.register("rg4d",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.2F, 50, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 7, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> RIG420 = ITEMS.register("rig420",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.5F, 500, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 14, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> RK3 = ITEMS.register("rk3",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 75, 2,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "BURST", "STUN"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 9, 4, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> RSKF44 = ITEMS.register("rskf44",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 100, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 16, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> RT97C = ITEMS.register("rt97c",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.3F, 500, 0,
                    0, 0, 2, 0, 0, 0, List.of("FULL_AUTO"), 0F, 0F, 0.9F, 0F, 0F, 0F, "FULL_AUTO",
                    0F, 0F, 4.5F, 0F, 0F, 0F, "TIBANNA_GAS", 0, 0, 5, 0, 0, 0, "REPEATER"));
    public static final DeferredItem<Item> RUGER_BLASTER = ITEMS.register("ruger_blaster",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.3F, 300, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "STUN"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 13, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> S5 = ITEMS.register("s5",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.5F, 20, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "STUN"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "SPIN_SEALED_TIBANNA_GAS", 13, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> S195 = ITEMS.register("s195",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.6F, 100, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "STUN"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 12, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> SACROS_K11 = ITEMS.register("sacros_k11",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.6F, 150, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "SIG_GAS", 16, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> SATINES_LAMENT = ITEMS.register("satines_lament",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.6F, 100, 2,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "BURST", "CHARGED", "STUN"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 17, 7, 0, 20, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> SE14C = ITEMS.register("se14c",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.6F, 400, 2,
                    8, 16, 0, 0, 0, 0, List.of("SEMI_AUTO", "BURST", "STUN"), 1.5F, 0.7F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    4F, 6.4F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 9, 6, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> SE14R = ITEMS.register("se14r",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 200, 0,
                    0, 0, 3, 0, 0, 0, List.of("FULL_AUTO"), 0F, 0F, 0.6F, 0F, 0F, 0F, "FULL_AUTO",
                    0F, 0F, 5.1F, 0F, 0F, 0F, "TIBANNA_GAS", 0, 0, 7, 0, 0, 0, "REPEATER"));
    public static final DeferredItem<Item> SEDGLEYS_MK_5 = ITEMS.register("sedgleys_mk_5",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 250, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 17, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> SEREXIM_MK_5 = ITEMS.register("serexim_mk_5",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.6F, 300, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 14, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> SHADOW_TROOPER_BLASTER = ITEMS.register("shadow_trooper_blaster",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.7F, 200, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "CHARGED", "STUN"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNAX_GAS", 16, 0, 0, 19, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> SHARD3A = ITEMS.register("shard3a",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.3F, 100, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 11, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> SK32 = ITEMS.register("sk32",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.5F, 250, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "CHARGED"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "MAGNITIZED_SIG_GAS", 15, 0, 0, 18, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> SNUBBLE = ITEMS.register("snubble",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.1F, 100, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "STUN"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 14, 0, 0, 0, 0, 0, "CARBINE"));
    public static final DeferredItem<Item> STEYR43 = ITEMS.register("steyr43",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.3F, 300, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 16, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> SX21 = ITEMS.register("sx21",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 150, 0,
                    25, 0, 0, 0, 0, 0, List.of("SCATTER"), 0F, 0F, 0F, 0F, 0F, 0F, "SCATTER",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 6, 0, 0, 0, 0, 0, "SCATTER"));
    public static final DeferredItem<Item> T6 = ITEMS.register("t6",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.2F, 25, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 15, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> T7_ION_DISRUPTOR = ITEMS.register("t7_ion_disruptor",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 30, 0,
                    0, 0, 0, 0, 0, 0, List.of("CHARGED"), 0F, 0F, 0F, 0F, 0F, 0F, "CHARGED",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 0, 0, 0, 25, 0, 0, "DISRUPTOR"));
    public static final DeferredItem<Item> T21 = ITEMS.register("t21",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 150, 3,
                    0, 14, 0, 0, 0, 0, List.of("BURST"), 0F, 1F, 0F, 0F, 0F, 0F, "BURST",
                    0F, 2F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 0, 13, 0, 0, 0, 0, "REPEATER"));
    public static final DeferredItem<Item> T21B = ITEMS.register("t21b",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.6F, 150, 0,
                    10, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 2.3F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    1.4F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 16, 0, 0, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> TCA_PRO = ITEMS.register("tca_pro",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.2F, 200, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 14, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> TL50 = ITEMS.register("tl50",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 250, 0,
                    25, 0, 0, 0, 0, 0, List.of("FULL_AUTO", "CHARGED"), 0F, 0F, 0F, 0F, 0F, 0F, "FULL_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 0, 0, 4, 6, 0, 0, "REPEATER"));
    public static final DeferredItem<Item> TOMSUN97 = ITEMS.register("tomsun97",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 500, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "FULL_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 14, 0, 4, 0, 0, 0, "REPEATER"));
    public static final DeferredItem<Item> TYPE14 = ITEMS.register("type14",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.2F, 250, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 13, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> UMBARAN_PISTOL = ITEMS.register("umbaran_pistol",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.6F, 500, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "SPIN_SEALED_TIBANNA_GAS", 18, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> VALKEN38X = ITEMS.register("valken38x",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.8F, 14, 0,
                    0, 0, 0, 0, 0, 0, List.of("SNIPER"), 0F, 0F, 0F, 0F, 0F, 0F, "SNIPER",
                    0F, 0F, 0F, 0F, 0F, 0F, "IONIZED_TIBANNA_GAS", 0, 0, 0, 0, 0, 16, "SNIPER"));
    public static final DeferredItem<Item> VANGUARD_SCATTER = ITEMS.register("vanguard_scatter",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 30, 0,
                    25, 0, 0, 0, 0, 0, List.of("SCATTER"), 0F, 0F, 0F, 0F, 0F, 0F, "SCATTER",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 6, 0, 0, 0, 0, 0, "SCATTER"));
    public static final DeferredItem<Item> VECT_UZI = ITEMS.register("vect_uzi",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.3F, 200, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "FULL_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 14, 0, 4, 0, 0, 0, "REPEATER"));
    public static final DeferredItem<Item> VERPINE_SHATTER = ITEMS.register("verpine_shatter",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.9F, 20, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 15, 0, 0, 0, 0, 0, "SLUGTHROWER"));
    public static final DeferredItem<Item> VULK_TAU623_ROTARY = ITEMS.register("vulk_tau623_rotary",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.3F, 500, 0,
                    0, 0, 0, 0, 0, 0, List.of("FULL_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "FULL_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 0, 0, 5, 0, 0, 0, "REPEATER"));
    public static final DeferredItem<Item> WALTHER_BLASTER = ITEMS.register("walther_blaster",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.6F, 100, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 16, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> WALTHER_LPM_BLASTER = ITEMS.register("walther_lpm_blaster",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.3F, 300, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 15, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> WEBLY_S4 = ITEMS.register("webly_s4",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.0F, 20, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 10, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> WEBTEMP = ITEMS.register("webtemp",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.4F, 150, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 15, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> WEEQUAY_LANCE = ITEMS.register("weequay_lance",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.9F, 10, 0,
                    0, 0, 0, 0, 0, 0, List.of("SNIPER"), 0F, 0F, 0F, 0F, 0F, 0F, "SNIPER",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 0, 0, 0, 0, 0, 18, "SNIPER"));
    public static final DeferredItem<Item> WEEQUAY_PISTOL = ITEMS.register("weequay_pistol",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.2F, 100, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 12, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> WEEQUAY_RIFLE = ITEMS.register("weequay_rifle",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    3.0F, 20, 0,
                    0, 0, 0, 0, 0, 0, List.of("SNIPER"), 0F, 0F, 0F, 0F, 0F, 0F, "SNIPER",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 0, 0, 0, 0, 0, 18, "SNIPER"));
    public static final DeferredItem<Item> WESTAR_20 = ITEMS.register("westar_20",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.5F, 100, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 15, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> WESTAR_34 = ITEMS.register("westar_34",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.5F, 100, 0,
                    13, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 2.4F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    4F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 17, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> WESTAR_35 = ITEMS.register("westar_35",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.5F, 100, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO", "FULL_AUTO", "STUN"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "SIG_GAS", 11, 0, 5, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> WESTARM5 = ITEMS.register("westarm5",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.5F, 350, 2,
                    4, 11, 3, 0, 0, 0, List.of("SEMI_AUTO", "BURST", "FULL_AUTO"), 1F, 0.8F, 1.4F, 0F, 0F, 0F, "FULL_AUTO",
                    3F, 3.5F, 3.8F, 0F, 0F, 0F, "IONIZED_TIBANNA_GAS", 12, 5, 3, 0, 0, 0, "RIFLE"));
    public static final DeferredItem<Item> WINCHESTER87 = ITEMS.register("winchester87",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.3F, 250, 0,
                    25, 0, 0, 0, 0, 0, List.of("SCATTER", "STUN"), 0.6F, 0F, 0F, 0F, 0F, 0F, "SCATTER",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 14, 0, 0, 0, 0, 0, "SCATTER"));
    public static final DeferredItem<Item> X30 = ITEMS.register("x30",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.3F, 300, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 17, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> X8_NIGHT_SNIPER = ITEMS.register("x8_night_sniper",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.6F, 100, 0,
                    25, 0, 0, 0, 0, 0, List.of("SEMI_AUTO"), 0.6F, 0F, 0F, 0F, 0F, 0F, "SEMI_AUTO",
                    0F, 0F, 0F, 0F, 0F, 0F, "TIBANNA_GAS", 12, 0, 0, 0, 0, 0, "PISTOL"));
    public static final DeferredItem<Item> Z6_ROTARY = ITEMS.register("z6_rotary",
            () -> new BlasterItem(new Item.Properties().stacksTo(1),
                    2.2F, 200, 0,
                    0, 0, 2, 0, 0, 0, List.of("FULL_AUTO"), 0F, 0F, 0.4F, 0F, 0F, 0F, "FULL_AUTO",
                    0F, 0F, 5F, 0F, 0F, 0F, "IONIZED_TIBANNA_GAS", 0, 0, 4, 0, 0, 0, "REPEATER"));
}