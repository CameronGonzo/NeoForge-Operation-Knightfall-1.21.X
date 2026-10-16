package net.uhhitscam.knightfall.effect.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.effect.ModEffects;

import java.util.UUID;

public final class LightningShockEffectRenderer {
    private static final int TICKS_PER_FRAME = 3;
    private static final int TEXTURE_SIZE = 256;
    private static final Identifier[] FRAMES = {
            texture(0), texture(1), texture(2), texture(3), texture(4), texture(5)
    };
    private static UUID trackedPlayer;
    private static long effectStartTick;

    private LightningShockEffectRenderer() {}

    private static Identifier texture(int frame) {
        return Identifier.fromNamespaceAndPath(OperationKnightfall.MODID,
                "textures/gui/lightning_shock_" + frame + ".png");
    }

    public static void register(IEventBus eventBus) {
        eventBus.addListener(LightningShockEffectRenderer::onRenderGui);
    }

    public static int frameAt(long elapsedTicks) {
        return (int) ((Math.max(0L, elapsedTicks) / TICKS_PER_FRAME) % FRAMES.length);
    }

    private static void onRenderGui(RenderGuiEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        var player = minecraft.player;
        MobEffectInstance effect = player == null ? null : player.getEffect(ModEffects.LIGHTNING_SHOCK_EFFECT);
        if (effect == null) {
            trackedPlayer = null;
            return;
        }

        long currentTick = player.level().getGameTime();
        // Duration refreshes must not restart the animation while the player remains shocked.
        if (!player.getUUID().equals(trackedPlayer) || currentTick < effectStartTick) {
            trackedPlayer = player.getUUID();
            effectStartTick = currentTick;
        }

        event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED,
                FRAMES[frameAt(currentTick - effectStartTick)],
                0, 0, 0.0F, 0.0F,
                minecraft.getWindow().getGuiScaledWidth(), minecraft.getWindow().getGuiScaledHeight(),
                TEXTURE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE);
    }
}
