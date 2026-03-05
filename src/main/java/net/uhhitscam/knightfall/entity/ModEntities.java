package net.uhhitscam.knightfall.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.uhhitscam.knightfall.entity.custom.*;

import java.util.function.Supplier;

import static net.uhhitscam.knightfall.OperationKnightfall.MODID;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, MODID);

    public static final Supplier<EntityType<TibannaBlasterBoltEntity>> TIBANNA_BLASTER_BOLT =
            ENTITY_TYPES.register("tibanna_blaster_bolt", () -> EntityType.Builder.<TibannaBlasterBoltEntity>of(TibannaBlasterBoltEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("tibanna_blaster_bolt"));
    public static final Supplier<EntityType<IonizedTibannaBlasterBoltEntity>> IONIZED_TIBANNA_BLASTER_BOLT =
            ENTITY_TYPES.register("ionized_tibanna_blaster_bolt", () -> EntityType.Builder.<IonizedTibannaBlasterBoltEntity>of(IonizedTibannaBlasterBoltEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("ionized_tibanna_blaster_bolt"));
    public static final Supplier<EntityType<SpinSealedTibannaBlasterBoltEntity>> SPIN_SEALED_TIBANNA_BLASTER_BOLT =
            ENTITY_TYPES.register("spin_sealed_tibanna_blaster_bolt", () -> EntityType.Builder.<SpinSealedTibannaBlasterBoltEntity>of(SpinSealedTibannaBlasterBoltEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("spin_sealed_tibanna_blaster_bolt"));
    public static final Supplier<EntityType<TibannaXBlasterBoltEntity>> TIBANNAX_BLASTER_BOLT =
            ENTITY_TYPES.register("tibannax_blaster_bolt", () -> EntityType.Builder.<TibannaXBlasterBoltEntity>of(TibannaXBlasterBoltEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("tibannax_blaster_bolt"));
    public static final Supplier<EntityType<SigBlasterBoltEntity>> SIG_BLASTER_BOLT =
            ENTITY_TYPES.register("sig_blaster_bolt", () -> EntityType.Builder.<SigBlasterBoltEntity>of(SigBlasterBoltEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("sig_blaster_bolt"));
    public static final Supplier<EntityType<MagnetizedSigBlasterBoltEntity>> MAGNETIZED_SIG_BLASTER_BOLT =
            ENTITY_TYPES.register("magnetized_sig_blaster_bolt", () -> EntityType.Builder.<MagnetizedSigBlasterBoltEntity>of(MagnetizedSigBlasterBoltEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("magnetized_sig_blaster_bolt"));
    public static final Supplier<EntityType<SkevonBlasterBoltEntity>> SKEVON_BLASTER_BOLT =
            ENTITY_TYPES.register("skevon_blaster_bolt", () -> EntityType.Builder.<SkevonBlasterBoltEntity>of(SkevonBlasterBoltEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("skevon_blaster_bolt"));
    public static final Supplier<EntityType<StunBlasterBoltEntity>> STUN_BLASTER_BOLT =
            ENTITY_TYPES.register("stun_blaster_bolt", () -> EntityType.Builder.<StunBlasterBoltEntity>of(StunBlasterBoltEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("stun_blaster_bolt"));
    public static final Supplier<EntityType<SteelSlugEntity>> STEEL_SLUG =
            ENTITY_TYPES.register("steel_slug", () -> EntityType.Builder.<SteelSlugEntity>of(SteelSlugEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("steel_slug"));
    public static final Supplier<EntityType<RazorSteelSlugEntity>> RAZOR_STEEL_SLUG =
            ENTITY_TYPES.register("razor_steel_slug", () -> EntityType.Builder.<RazorSteelSlugEntity>of(RazorSteelSlugEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("razor_steel_slug"));
    public static final Supplier<EntityType<PoisonTippedSteelSlugEntity>> POISON_TIPPED_STEEL_SLUG =
            ENTITY_TYPES.register("poison_tipped_steel_slug", () -> EntityType.Builder.<PoisonTippedSteelSlugEntity>of(PoisonTippedSteelSlugEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("poison_tipped_steel_slug"));
    public static final Supplier<EntityType<ExplosiveTippedSteelSlugEntity>> EXPLOSIVE_TIPPED_STEEL_SLUG =
            ENTITY_TYPES.register("explosive_tipped_steel_slug", () -> EntityType.Builder.<ExplosiveTippedSteelSlugEntity>of(ExplosiveTippedSteelSlugEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("explosive_tipped_steel_slug"));
    public static final Supplier<EntityType<IonTippedSteelSlugEntity>> ION_TIPPED_STEEL_SLUG =
            ENTITY_TYPES.register("ion_tipped_steel_slug", () -> EntityType.Builder.<IonTippedSteelSlugEntity>of(IonTippedSteelSlugEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("ion_tipped_steel_slug"));
    public static final Supplier<EntityType<PlasticSlugEntity>> PLASTIC_SLUG =
            ENTITY_TYPES.register("plastic_slug", () -> EntityType.Builder.<PlasticSlugEntity>of(PlasticSlugEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("plastic_slug"));
    public static final Supplier<EntityType<CeramicSlugEntity>> CERAMIC_SLUG =
            ENTITY_TYPES.register("ceramic_slug", () -> EntityType.Builder.<CeramicSlugEntity>of(CeramicSlugEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("ceramic_slug"));
    public static final Supplier<EntityType<FlechetteEntity>> FLECHETTE =
            ENTITY_TYPES.register("flechette", () -> EntityType.Builder.<FlechetteEntity>of(FlechetteEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("flechette"));
    public static final Supplier<EntityType<FlechetteToxicEntity>> FLECHETTE_TOXIC =
            ENTITY_TYPES.register("flechette_toxic", () -> EntityType.Builder.<FlechetteToxicEntity>of(FlechetteToxicEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("flechette_toxic"));
    public static final Supplier<EntityType<FlechetteSpreadCanEntity>> FLECHETTE_SPREAD_CAN =
            ENTITY_TYPES.register("flechette_spread_can", () -> EntityType.Builder.<FlechetteSpreadCanEntity>of(FlechetteSpreadCanEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("flechette_spread_can"));
    public static final Supplier<EntityType<FlechetteToxicSpreadCanEntity>> FLECHETTE_TOXIC_SPREAD_CAN =
            ENTITY_TYPES.register("flechette_toxic_spread_can", () -> EntityType.Builder.<FlechetteToxicSpreadCanEntity>of(FlechetteToxicSpreadCanEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("flechette_toxic_spread_can"));
    public static final DeferredHolder<EntityType<?>, EntityType<BlasterBeamEndpointEntity>> BLASTER_BEAM =
            ENTITY_TYPES.register("blaster_beam", () ->
                    EntityType.Builder.<BlasterBeamEndpointEntity>of(BlasterBeamEndpointEntity::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(128)
                            .updateInterval(1)
                            .setShouldReceiveVelocityUpdates(false)
                            .build("blaster_beam")
            );

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}