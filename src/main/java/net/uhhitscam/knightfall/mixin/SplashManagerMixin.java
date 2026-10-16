package net.uhhitscam.knightfall.mixin;

import java.time.MonthDay;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.client.resources.SplashManager;
import net.minecraft.util.SpecialDates;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SplashManager.class)
public abstract class SplashManagerMixin {
    private static final MonthDay MAY_THE_FOURTH = MonthDay.of(5, 4);

    @Inject(method = "getSplash", at = @At("HEAD"), cancellable = true)
    private void knightfall$useMayFourthSplash(CallbackInfoReturnable<SplashRenderer> callback) {
        if (SpecialDates.dayNow().equals(MAY_THE_FOURTH)) {
            callback.setReturnValue(new SplashRenderer(
                    SplashManager.literalSplash("May the 4th be with you")
            ));
        }
    }
}
