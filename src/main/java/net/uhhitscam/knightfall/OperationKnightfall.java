package net.uhhitscam.knightfall;

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
import net.uhhitscam.knightfall.component.ModDataComponentTypes;
import net.uhhitscam.knightfall.effect.ModEffects;
import net.uhhitscam.knightfall.effect.client.StunEffectRenderer;
import net.uhhitscam.knightfall.entity.ModEntities;
import net.uhhitscam.knightfall.entity.client.*;
import net.uhhitscam.knightfall.event.FaceAlignedParticleClient;
import net.uhhitscam.knightfall.event.ProjectileWeaponZoomEventHandler;
import net.uhhitscam.knightfall.item.ModCreativeModeTabs;
import net.uhhitscam.knightfall.item.ModItems;
import net.uhhitscam.knightfall.item.client.GrenadeItemModelProperties;
import net.uhhitscam.knightfall.network.PayloadRegister;
import net.uhhitscam.knightfall.particle.*;
import net.uhhitscam.knightfall.sound.ModSounds;
import net.uhhitscam.knightfall.util.KeyBinding;
import org.slf4j.Logger;
import org.spongepowered.asm.launch.MixinBootstrap;

@Mod(OperationKnightfall.MODID)
public class OperationKnightfall {
    public static final String MODID = "knightfall";

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

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.BLASTER_BOLT.get(), BlasterBoltRenderer::new);
            EntityRenderers.register(ModEntities.SONIC_BOLT.get(), SonicBoltRenderer::new);
            EntityRenderers.register(ModEntities.STUN_BLASTER_BOLT.get(), StunBlasterBoltRenderer::new);
            EntityRenderers.register(ModEntities.STEEL_SLUG.get(), SteelSlugRenderer::new);
            EntityRenderers.register(ModEntities.RAZOR_STEEL_SLUG.get(), RazorSteelSlugRenderer::new);
            EntityRenderers.register(ModEntities.POISON_TIPPED_STEEL_SLUG.get(), PoisonTippedSteelSlugRenderer::new);
            EntityRenderers.register(ModEntities.EXPLOSIVE_TIPPED_STEEL_SLUG.get(), ExplosiveTippedSteelSlugRenderer::new);
            EntityRenderers.register(ModEntities.ION_TIPPED_STEEL_SLUG.get(), IonTippedSteelSlugRenderer::new);
            EntityRenderers.register(ModEntities.PLASTIC_SLUG.get(), PlasticSlugRenderer::new);
            EntityRenderers.register(ModEntities.CERAMIC_SLUG.get(), CeramicSlugRenderer::new);
            EntityRenderers.register(ModEntities.FLECHETTE.get(), FlechetteRenderer::new);
            EntityRenderers.register(ModEntities.FLECHETTE_TOXIC.get(), FlechetteToxicRenderer::new);
            EntityRenderers.register(ModEntities.FLECHETTE_SPREAD_CAN.get(), FlechetteSpreadCanRenderer::new);
            EntityRenderers.register(ModEntities.FLECHETTE_TOXIC_SPREAD_CAN.get(), FlechetteToxicSpreadCanRenderer::new);
            EntityRenderers.register(ModEntities.GRENADE.get(), GrenadeRenderer::new);
            EntityRenderers.register(ModEntities.BLASTER_BEAM.get(), BlasterBeamRenderer::new);

            event.enqueueWork(() -> {
                GrenadeItemModelProperties.register(ModItems.THERMAL_DETONATOR.get());
                GrenadeItemModelProperties.register(ModItems.IMPACT_THERMAL_DETONATOR.get());
                StunEffectRenderer.register(net.neoforged.neoforge.common.NeoForge.EVENT_BUS);
                ProjectileWeaponZoomEventHandler.register(net.neoforged.neoforge.common.NeoForge.EVENT_BUS);
                FaceAlignedParticleClient.register(net.neoforged.neoforge.common.NeoForge.EVENT_BUS);
            });
        }

        @SubscribeEvent
        public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(ModParticles.SPARK_PARTICLES.get(), SparkParticles.Provider::new);
            event.registerSpriteSet(ModParticles.STUN_SPARK_PARTICLES.get(), StunSparkParticles.Provider::new);
            event.registerSpriteSet(ModParticles.EXPLOSIVE_SHOT_TIBANNA_PARTICLES.get(), ExplosiveShotTibannaParticles.Provider::new);
            event.registerSpriteSet(ModParticles.EXPLOSIVE_SHOT_IONIZED_TIBANNA_PARTICLES.get(), ExplosiveShotIonizedTibannaParticles.Provider::new);
            event.registerSpriteSet(ModParticles.EXPLOSIVE_SHOT_TIBANNAX_PARTICLES.get(), ExplosiveShotTibannaXParticles.Provider::new);
            event.registerSpriteSet(ModParticles.EXPLOSIVE_SHOT_SPIN_SEALED_TIBANNA_PARTICLES.get(), ExplosiveShotSpinSealedTibannaParticles.Provider::new);
            event.registerSpriteSet(ModParticles.EXPLOSIVE_SHOT_SIG_PARTICLES.get(), ExplosiveShotSigParticles.Provider::new);
            event.registerSpriteSet(ModParticles.EXPLOSIVE_SHOT_MAGNETIZED_SIG_PARTICLES.get(), ExplosiveShotMagnetizedSigParticles.Provider::new);
            event.registerSpriteSet(ModParticles.EXPLOSIVE_SHOT_SKEVON_PARTICLES.get(), ExplosiveShotSkevonParticles.Provider::new);
            event.registerSpriteSet(ModParticles.REPULSE_SHOT_LARGE_PARTICLES.get(), RepulseShotLargeParticles.Provider::new);
            event.registerSpriteSet(ModParticles.REPULSE_SHOT_MEDIUM_PARTICLES.get(), RepulseShotMediumParticles.Provider::new);
            event.registerSpriteSet(ModParticles.REPULSE_SHOT_SMALL_PARTICLES.get(), RepulseShotSmallParticles.Provider::new);
            event.registerSpriteSet(ModParticles.CONCUSSIVE_SHOT_EXPLOSION_PARTICLES.get(), ConcussiveShotExplosionParticles.Provider::new);
            event.registerSpriteSet(ModParticles.DISINTEGRATION_LARGE_PARTICLES.get(), DisintegrationLargeParticles.Provider::new);
            event.registerSpriteSet(ModParticles.DISINTEGRATION_MEDIUM_PARTICLES.get(), DisintegrationMediumParticles.Provider::new);
            event.registerSpriteSet(ModParticles.DISINTEGRATION_SMALL_PARTICLES.get(), DisintegrationSmallParticles.Provider::new);
        }
    }
}
