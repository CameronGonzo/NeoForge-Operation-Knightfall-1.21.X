package net.uhhitscam.starwars.gui;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.uhhitscam.starwars.OperationKnightfall;

@Mod(OperationKnightfall.MODID)
public final class Hud {
    public static final String MODID = "starwars";

    public Hud() {
        OperationKnightfall.init();

        // Initialize client-side setup only if running on the client
        if (FMLEnvironment.dist == Dist.CLIENT) {
            HudClient.init();
        }
    }
}
