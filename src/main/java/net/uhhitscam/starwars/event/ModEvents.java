package net.uhhitscam.starwars.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.uhhitscam.starwars.OperationKnightfall;

@EventBusSubscriber(modid = OperationKnightfall.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {
    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {

    }
}
