package net.uhhitscam.starwars;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.uhhitscam.starwars.component.ModDataComponentTypes;
import net.uhhitscam.starwars.entity.ModEntities;
import net.uhhitscam.starwars.entity.client.TibannaBlasterBoltRenderer;
import net.uhhitscam.starwars.entity.client.IonizedTibannaBlasterBoltRenderer;
import net.uhhitscam.starwars.entity.client.SpinSealedTibannaBlasterBoltRenderer;
import net.uhhitscam.starwars.entity.client.TibannaXBlasterBoltRenderer;
import net.uhhitscam.starwars.entity.client.SigBlasterBoltRenderer;
import net.uhhitscam.starwars.entity.client.MagnetizedSigBlasterBoltRenderer;
import net.uhhitscam.starwars.entity.client.SkevonBlasterBoltRenderer;
import net.uhhitscam.starwars.item.ModCreativeModeTabs;
import net.uhhitscam.starwars.item.ModItems;
import net.uhhitscam.starwars.util.KeyBinding;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(OperationKnightfall.MODID)
public class OperationKnightfall {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "starwars";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public OperationKnightfall(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        KeyBinding.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModEntities.register(modEventBus);
        ModDataComponentTypes.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.TIBANNA_BLASTER_BOLT.get(), TibannaBlasterBoltRenderer::new);
            EntityRenderers.register(ModEntities.IONIZED_TIBANNA_BLASTER_BOLT.get(), IonizedTibannaBlasterBoltRenderer::new);
            EntityRenderers.register(ModEntities.SPIN_SEALED_TIBANNA_BLASTER_BOLT.get(), SpinSealedTibannaBlasterBoltRenderer::new);
            EntityRenderers.register(ModEntities.TIBANNAX_BLASTER_BOLT.get(), TibannaXBlasterBoltRenderer::new);
            EntityRenderers.register(ModEntities.SIG_BLASTER_BOLT.get(), SigBlasterBoltRenderer::new);
            EntityRenderers.register(ModEntities.MAGNETIZED_SIG_BLASTER_BOLT.get(), MagnetizedSigBlasterBoltRenderer::new);
            EntityRenderers.register(ModEntities.SKEVON_BLASTER_BOLT.get(), SkevonBlasterBoltRenderer::new);
        }
    }
}
