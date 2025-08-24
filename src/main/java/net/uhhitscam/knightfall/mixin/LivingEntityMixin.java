package net.uhhitscam.knightfall.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.extensions.ILivingEntityExtension;
import net.uhhitscam.knightfall.effect.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable, ILivingEntityExtension {
    @Shadow public abstract float getSwimAmount(float partialTicks);

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "jumpFromGround", at = @At("HEAD"), cancellable = true)
    public void preventJump(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (entity.hasEffect(ModEffects.STUN_EFFECT)) {
            ci.cancel();
        }
    }

    @ModifyReturnValue(method = "isImmobile", at = @At("RETURN"))
    private boolean modifyIsImmobile(boolean original) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (entity.hasEffect(ModEffects.STUN_EFFECT)) {
            return true;
        }

        return original;
    }

//    @Inject(method = "tickHeadTurn", at = @At("HEAD"), cancellable = false)
//    private void lockHeadRotation(float yRot, float animStep, CallbackInfoReturnable<Float> ci) {
//        LivingEntity entity = (LivingEntity) (Object) this;
//
//        if (entity.hasEffect(ModEffects.STUN_EFFECT)) {
//            this.setXRot(0.0F);
//            this.setYRot(entity.yHeadRot);
//        }
//    }
}
