package net.uhhitscam.knightfall.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
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

    public static final Supplier<EntityType<BlasterBoltEntity>> BLASTER_BOLT =
            ENTITY_TYPES.register("blaster_bolt", () -> EntityType.Builder.<BlasterBoltEntity>of(BlasterBoltEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("blaster_bolt"));
    public static final Supplier<EntityType<SonicBoltEntity>> SONIC_BOLT =
            ENTITY_TYPES.register("sonic_bolt", () -> EntityType.Builder.<SonicBoltEntity>of(SonicBoltEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("sonic_bolt"));
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
    public static final Supplier<EntityType<GrenadeEntity>> GRENADE =
            ENTITY_TYPES.register("grenade", () -> EntityType.Builder.<GrenadeEntity>of(GrenadeEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(64)
                    .updateInterval(1)
                    .build("grenade"));
    public static final Supplier<EntityType<ExplosiveKnifeEntity>> EXPLOSIVE_KNIFE =
            ENTITY_TYPES.register("explosive_knife", () -> EntityType.Builder.<ExplosiveKnifeEntity>of(ExplosiveKnifeEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(64)
                    .updateInterval(1)
                    .setShouldReceiveVelocityUpdates(false)
                    .build("explosive_knife"));
    public static final DeferredHolder<EntityType<?>, EntityType<BlasterBeamEndpointEntity>> BLASTER_BEAM =
            ENTITY_TYPES.register("blaster_beam", () ->
                    EntityType.Builder.<BlasterBeamEndpointEntity>of(BlasterBeamEndpointEntity::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(128)
                            .updateInterval(1)
                            .setShouldReceiveVelocityUpdates(false)
                            .build("blaster_beam")
            );

    public static boolean isGrenadeTriggeringProjectile(Entity entity) {
        EntityType<?> type = entity.getType();
        return type == BLASTER_BOLT.get()
                || type == SONIC_BOLT.get()
                || type == STUN_BLASTER_BOLT.get()
                || type == STEEL_SLUG.get()
                || type == RAZOR_STEEL_SLUG.get()
                || type == POISON_TIPPED_STEEL_SLUG.get()
                || type == EXPLOSIVE_TIPPED_STEEL_SLUG.get()
                || type == ION_TIPPED_STEEL_SLUG.get()
                || type == PLASTIC_SLUG.get()
                || type == CERAMIC_SLUG.get()
                || type == FLECHETTE.get()
                || type == FLECHETTE_TOXIC.get()
                || type == FLECHETTE_SPREAD_CAN.get()
                || type == FLECHETTE_TOXIC_SPREAD_CAN.get();
    }

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
