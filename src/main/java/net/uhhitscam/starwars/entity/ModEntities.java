package net.uhhitscam.starwars.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.uhhitscam.starwars.entity.custom.*;

import java.util.function.Supplier;

import static net.uhhitscam.starwars.OperationKnightfall.MODID;

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

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}