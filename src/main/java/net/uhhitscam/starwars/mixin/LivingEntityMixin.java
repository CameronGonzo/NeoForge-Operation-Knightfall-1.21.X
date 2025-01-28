//package net.uhhitscam.starwars.mixin;
//
//import net.minecraft.world.effect.MobEffectInstance;
//import net.minecraft.world.entity.Attackable;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.level.Level;
//import net.neoforged.neoforge.common.extensions.ILivingEntityExtension;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//import javax.annotation.Nullable;
//
//@Mixin(LivingEntity.class)
//public abstract class LivingEntityMixin extends Entity implements Attackable, ILivingEntityExtension {
////    @Shadow  protected boolean jumping;
//
//    public LivingEntityMixin(EntityType<?> entityType, Level level) {
//        super(entityType, level);
//    }
//
//    @Inject(at = @At("HEAD"), method = "net.minecraft.world.entity.LivingEntity.onEffectUpdated", cancellable = true)
//    protected void OperationKnightfall_onEffectUpdated(MobEffectInstance effectInstance, boolean forced, @Nullable Entity entity, CallbackInfo ci) {
////        if (effectInstance.is(ModEffects.STUN_EFFECT)) {
////            this.jumping = false;
////        }
//    }
//}
