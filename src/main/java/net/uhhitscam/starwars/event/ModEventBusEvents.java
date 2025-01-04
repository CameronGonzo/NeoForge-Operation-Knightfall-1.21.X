package net.uhhitscam.starwars.event;

import net.uhhitscam.starwars.OperationKnightfall;
import net.uhhitscam.starwars.entity.client.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = OperationKnightfall.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.TIBANNA_BLASTER_BOLT, TibannaBlasterBoltModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.IONIZED_TIBANNA_BLASTER_BOLT, IonizedTibannaBlasterBoltModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.SPIN_SEALED_TIBANNA_BLASTER_BOLT, SpinSealedTibannaBlasterBoltModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.TIBANNAX_BLASTER_BOLT, TibannaXBlasterBoltModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.SIG_BLASTER_BOLT, SigBlasterBoltModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.MAGNETIZED_SIG_BLASTER_BOLT, MagnetizedSigBlasterBoltModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.SKEVON_BLASTER_BOLT, SkevonBlasterBoltModel::createBodyLayer);
    }

//    @SubscribeEvent
//    public static void registerAttributes(EntityAttributeCreationEvent event) {
//        event.put(ModEntities.PENGUIN.get(), PenguinEntity.createAttributes().build());
//        event.put(ModEntities.GIRAFFE.get(), GiraffeEntity.createAttributes().build());
//        event.put(ModEntities.WARTURTLE.get(), WarturtleEntity.createAttributes().build());
//    }

//    @SubscribeEvent
//    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
//        event.register(ModEntities.PENGUIN.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
//                Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
//
//        event.register(ModEntities.GIRAFFE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
//                Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
//
//        event.register(ModEntities.WARTURTLE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
//                Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
//    }
}