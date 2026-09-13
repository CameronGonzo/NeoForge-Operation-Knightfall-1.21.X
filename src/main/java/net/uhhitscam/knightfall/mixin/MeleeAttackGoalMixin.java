package net.uhhitscam.knightfall.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.uhhitscam.knightfall.event.MeleeWeaponServerEvents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MeleeAttackGoal.class)
public abstract class MeleeAttackGoalMixin {
    @Shadow @Final protected PathfinderMob mob;

    @Inject(method = "checkAndPerformAttack", at = @At("HEAD"), cancellable = true)
    private void delayStaggeredAttack(LivingEntity target, CallbackInfo ci) {
        if (MeleeWeaponServerEvents.isStaggered(mob)) ci.cancel();
    }
}
