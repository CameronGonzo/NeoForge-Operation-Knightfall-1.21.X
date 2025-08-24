package net.uhhitscam.knightfall.mixin;

import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity> {

//    @Inject(method = "setupRotations", at = @At("TAIL"), cancellable = false)
//    private void modifyRotationForEffect(T entity, PoseStack poseStack, float bob, float yBodyRot, float partialTick, float scale, CallbackInfo ci) {
//        if (entity.hasEffect(ModEffects.STUN_EFFECT)) {
//            poseStack.mulPose(Axis.YP.rotationDegrees(yBodyRot));
//            poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
//            poseStack.mulPose(Axis.YP.rotationDegrees(270.0F));
//        }
//    }
}
