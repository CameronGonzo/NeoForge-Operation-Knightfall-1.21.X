package net.uhhitscam.starwars.mixin;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.uhhitscam.starwars.OperationKnightfall;
import net.uhhitscam.starwars.effect.ModEffects;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Shadow @Nullable private PostChain postEffect;
    @Shadow @Final private Minecraft minecraft;

    private static final ResourceLocation STUN_SHADER = ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/item/ionized_tibanna_blaster_bolt.png");
    private float shaderTime = 0.0F;

    @Inject(method = "tick", at = @At("TAIL"))
    private void applyStunEffect(CallbackInfo ci) {
        if (minecraft.player == null) return;

        if (minecraft.player.hasEffect(ModEffects.STUN_EFFECT)) {
            loadStunShader();
        } else {
            clearShader();
        }
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void updateShaderTime(CallbackInfo ci) {
        if (postEffect != null) {
            shaderTime += 0.05F; // Increment time per frame (adjust for desired speed)
            postEffect.process(shaderTime);
        }
    }

    private void loadStunShader() {
        if (postEffect != null) return; // Shader already active

        try {
            postEffect = new PostChain(minecraft.getTextureManager(), minecraft.getResourceManager(),
                    minecraft.getMainRenderTarget(), STUN_SHADER);
            postEffect.resize(minecraft.getWindow().getWidth(), minecraft.getWindow().getHeight());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearShader() {
        if (postEffect != null) {
            postEffect.close();
            postEffect = null;
            shaderTime = 0.0F; // Reset when effect ends
        }
    }
}
