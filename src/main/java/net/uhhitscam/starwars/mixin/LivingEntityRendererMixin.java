package net.uhhitscam.starwars.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.uhhitscam.starwars.effect.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity> {

    @Inject(method = "setupRotations", at = @At("TAIL"), cancellable = false)
    private void modifyRotationForEffect(T entity, PoseStack poseStack, float bob, float yBodyRot, float partialTick, float scale, CallbackInfo ci) {
        if (entity.hasEffect(ModEffects.STUN_EFFECT)) {
            poseStack.mulPose(Axis.YP.rotationDegrees(yBodyRot));
            poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
            poseStack.mulPose(Axis.YP.rotationDegrees(270.0F));
        }
    }
}
