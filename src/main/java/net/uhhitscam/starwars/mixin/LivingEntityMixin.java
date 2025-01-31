package net.uhhitscam.starwars.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.extensions.ILivingEntityExtension;
import net.uhhitscam.starwars.effect.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable, ILivingEntityExtension {
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

    @Inject(method = "tickHeadTurn", at = @At("HEAD"), cancellable = true)
    private void lockHeadRotation(float yRot, float animStep, CallbackInfoReturnable<Float> ci) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (entity.hasEffect(ModEffects.STUN_EFFECT)) {
            this.setXRot(0.0F);
            this.setYRot(entity.yHeadRot);
        }
    }
}
