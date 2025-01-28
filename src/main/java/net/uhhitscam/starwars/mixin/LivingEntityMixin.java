package net.uhhitscam.starwars.mixin;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.extensions.ILivingEntityExtension;
import net.uhhitscam.starwars.effect.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable, ILivingEntityExtension {
    @Shadow protected boolean jumping;

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "aiStep", at = @At("TAIL"), cancellable = true)
    public void onaiStep(CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        MobEffectInstance effectInstance = self.getEffect(ModEffects.STUN_EFFECT);
        if (effectInstance != null && effectInstance.is(ModEffects.STUN_EFFECT)) {
            this.jumping = false;
        }
    }
}
