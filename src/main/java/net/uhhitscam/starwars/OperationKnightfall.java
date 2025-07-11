package net.uhhitscam.starwars;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.uhhitscam.starwars.component.ModDataComponentTypes;
import net.uhhitscam.starwars.effect.ModEffects;
import net.uhhitscam.starwars.effect.client.StunEffectRenderer;
import net.uhhitscam.starwars.effect.client.StunnedEffectRenderer;
import net.uhhitscam.starwars.entity.ModEntities;
import net.uhhitscam.starwars.entity.client.*;
import net.uhhitscam.starwars.event.BlasterZoomEventHandler;
import net.uhhitscam.starwars.item.ModCreativeModeTabs;
import net.uhhitscam.starwars.item.ModItems;
import net.uhhitscam.starwars.network.PayloadRegister;
import net.uhhitscam.starwars.particle.SparkParticles;
import net.uhhitscam.starwars.particle.ModParticles;
import net.uhhitscam.starwars.particle.StunSparkParticles;
import net.uhhitscam.starwars.sound.ModSounds;
import net.uhhitscam.starwars.util.KeyBinding;
import org.slf4j.Logger;
import org.spongepowered.asm.launch.MixinBootstrap;

@Mod(OperationKnightfall.MODID)
public class OperationKnightfall {
    public static final String MODID = "starwars";

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE_DEFERRED_REGISTER = DeferredRegister.create(Registries.ENTITY_TYPE, MODID);

    private static final Logger LOGGER = LogUtils.getLogger();

    public OperationKnightfall(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        KeyBinding.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModEntities.register(modEventBus);
        ModDataComponentTypes.register(modEventBus);
        ModParticles.register(modEventBus);
        ModSounds.register(modEventBus);
        ModEffects.MOB_EFFECTS.register(modEventBus);
        ENTITY_TYPE_DEFERRED_REGISTER.register(modEventBus);

        modEventBus.register(PayloadRegister.class);
        modEventBus.addListener(this::addCreative);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        MixinBootstrap.init();
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    public static void init() {
        LOGGER.info("Where Am I?");
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
            EntityRenderers.register(ModEntities.STUN_BLASTER_BOLT.get(), StunBlasterBoltRenderer::new);

            event.enqueueWork(() -> {
                StunEffectRenderer.register(net.neoforged.neoforge.common.NeoForge.EVENT_BUS);
                StunnedEffectRenderer.register(net.neoforged.neoforge.common.NeoForge.EVENT_BUS);
                BlasterZoomEventHandler.register(net.neoforged.neoforge.common.NeoForge.EVENT_BUS);
            });
        }

        @SubscribeEvent
        public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(ModParticles.SPARK_PARTICLES.get(), SparkParticles.Provider::new);
            event.registerSpriteSet(ModParticles.STUN_SPARK_PARTICLES.get(), StunSparkParticles.Provider::new);
        }
    }
}
