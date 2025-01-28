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

    //Blasters
    public static final DeferredItem<Item> DC17 = ITEMS.register("dc17",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.0F, 2.0F, 50, 14, 1, 0, 0, List.of("SEMI_AUTO", "STUN"),
                    0F, 0F, 0F));
    public static final DeferredItem<Item> DH17 = ITEMS.register("dh17",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.0F, 1.5F, 500, 17, 5, 20, 8, List.of("SEMI_AUTO", "BURST", "FULL_AUTO", "STUN"),
                    0.2F, 0.9F, 1.7F));
    public static final DeferredItem<Item> DL44 = ITEMS.register("dl44",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 2.8F, 1.2F, 50, 16, 5, 0, 0, List.of("SEMI_AUTO"),
                    0.7F, 0F, 0F));
    public static final DeferredItem<Item> EE3 = ITEMS.register("ee3",
            () -> new BlasterItem(new Item.Properties().stacksTo(1), 3.0F, 1.0F, 300, 8, 0, 20, 0, List.of("BURST"),
                    0F, 0.5F, 0F));
}