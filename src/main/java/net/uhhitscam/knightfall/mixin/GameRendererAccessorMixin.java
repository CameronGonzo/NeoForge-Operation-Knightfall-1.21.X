package net.uhhitscam.knightfall.mixin;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostChain;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import javax.annotation.Nullable;

@Mixin(GameRenderer.class)
public interface GameRendererAccessorMixin {
    @Accessor("postEffect")
    @Nullable PostChain operation_knightfall$getPostEffect();
}