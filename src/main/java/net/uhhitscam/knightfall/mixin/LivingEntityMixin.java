package net.uhhitscam.knightfall.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.LivingEntity;
import net.uhhitscam.knightfall.effect.custom.StunEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "jumpFromGround", at = @At("HEAD"), cancellable = true)
    private void preventJumpWhileStunned(CallbackInfo ci) {
        if (StunEffect.isStunned((LivingEntity) (Object) this)) {
            ci.cancel();
        }
    }

    // Vanilla immobility skips mob AI and voluntary movement while retaining normal push and knockback physics.
    @ModifyReturnValue(method = "isImmobile", at = @At("RETURN"))
    private boolean makeStunnedEntityImmobile(boolean original) {
        return original || StunEffect.isStunned((LivingEntity) (Object) this);
    }
}
