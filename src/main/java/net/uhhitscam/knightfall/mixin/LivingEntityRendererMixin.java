package net.uhhitscam.knightfall.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.uhhitscam.knightfall.event.ThermalVisionRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {
    @Shadow public abstract Identifier getTextureLocation(LivingEntityRenderState state);

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V", at = @At("TAIL"))
    private void knightfall$extractThermal(LivingEntity entity, LivingEntityRenderState state, float partialTick, CallbackInfo ci) {
        ThermalVisionRenderer.extract(entity, state);
    }

    @WrapOperation(method = "submit(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/rendertype/RenderType;IIILnet/minecraft/client/renderer/texture/TextureAtlasSprite;ILnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V"))
    private <S> void knightfall$submitThermal(SubmitNodeCollector collector, Model<? super S> model, S state,
            PoseStack pose, RenderType type, int light, int overlay, int color, TextureAtlasSprite sprite,
            int outline, ModelFeatureRenderer.CrumblingOverlay crumbling, Operation<Void> original) {
        original.call(collector, model, state, pose, type, light, overlay, color, sprite, outline, crumbling);
        if (state instanceof LivingEntityRenderState living && Boolean.TRUE.equals(living.getRenderData(ThermalVisionRenderer.ACTIVE))) {
            collector.submitModel(model, state, pose, RenderTypes.entityTranslucent(getTextureLocation(living)),
                    0xF000F0, overlay, 0xA6FF0D05, sprite, 0, null);
        }
    }
}
