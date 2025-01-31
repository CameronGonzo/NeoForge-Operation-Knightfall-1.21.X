package net.uhhitscam.starwars.mixin;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.uhhitscam.starwars.OperationKnightfall;
import net.uhhitscam.starwars.effect.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Accessor("renderBuffers")
    abstract RenderBuffers getRenderBuffers();

    @Inject(method = "render", at = @At("TAIL"))
    private void injectCustomScreenEffect(DeltaTracker deltaTracker, boolean renderLevel, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.screen != null) return; // Avoid drawing over menus

        // Access renderBuffers via the accessor mixin
        RenderBuffers renderBuffers = ((GameRendererMixin) this).getRenderBuffers();

        // Create GuiGraphics using the retrieved renderBuffers
        GuiGraphics guiGraphics = new GuiGraphics(mc, renderBuffers.bufferSource());

        // Render the custom overlay if conditions are met
        if (shouldRenderCustomEffect(mc.player)) {
            renderCustomOverlay(guiGraphics);
        }
    }

    private boolean shouldRenderCustomEffect(Player player) {
        return player.hasEffect(ModEffects.STUN_EFFECT); // Example: Show when player is confused
    }

    private void renderCustomOverlay(GuiGraphics guiGraphics) {
        System.out.println("rendering?");
        int screenWidth = guiGraphics.guiWidth();
        int screenHeight = guiGraphics.guiHeight();

        guiGraphics.pose().pushPose();

        // Scaling & Transform for custom effect (like nausea)
        float effectScale = 1.2F;
        guiGraphics.pose().translate(screenWidth / 2.0F, screenHeight / 2.0F, 0.0F);
        guiGraphics.pose().scale(effectScale, effectScale, effectScale);
        guiGraphics.pose().translate(-screenWidth / 2.0F, -screenHeight / 2.0F, 0.0F);

        // Set overlay color tint (e.g., a red glow for a damage effect)
        guiGraphics.setColor(1.0F, 0.0F, 0.0F, 1.0F);

        // Render texture overlay (Modify texture path)
        guiGraphics.blit(ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/custom_effect.png"),
                0, 0, 0, 0.0F, 0.0F, screenWidth, screenHeight, screenWidth, screenHeight);

        // Reset graphics state
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.pose().popPose();
    }
}