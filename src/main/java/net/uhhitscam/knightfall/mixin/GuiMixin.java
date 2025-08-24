package net.uhhitscam.knightfall.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.uhhitscam.knightfall.item.custom.ProjectileItem;
import net.uhhitscam.knightfall.util.WeaponZoomUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
@OnlyIn(Dist.CLIENT)
public class GuiMixin {

    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    private void overrideCrosshair(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;

        if (player == null || mc.options.hideGui) return;

        ItemStack mainHandItem = player.getMainHandItem();
        ItemStack offHandItem = player.getOffhandItem();
        ResourceLocation customCrosshair = null;
        ResourceLocation customScope;

        if (mainHandItem.getItem() instanceof ProjectileItem blasterMain) {
            if (mainHandItem.getItem() instanceof ProjectileItem && offHandItem.getItem() instanceof ProjectileItem blasterOff) {      //holding two blasters
                ProjectileItem prioritizedBlaster;
                if (WeaponZoomUtil.getZoomFactor(blasterMain, player.getMainHandItem()) >= WeaponZoomUtil.getZoomFactor(blasterOff, player.getOffhandItem())) {
                    prioritizedBlaster = blasterMain;
                } else {
                    prioritizedBlaster = blasterOff;
                }
                customCrosshair = WeaponZoomUtil.getCrosshairTexture(prioritizedBlaster);
                ci.cancel();
            } else {        //holding only in main hand
                customCrosshair = WeaponZoomUtil.getCrosshairTexture(blasterMain);
                customScope = WeaponZoomUtil.getScopeTexture(blasterMain, player.getMainHandItem());
                if (player.isShiftKeyDown() && (customScope != null)) {
                    customCrosshair = null;
                    int width = mc.getWindow().getGuiScaledWidth();
                    int height = mc.getWindow().getGuiScaledHeight();

                    RenderSystem.enableBlend();
                    guiGraphics.blit(customScope, 0, 0, 0, 0, width, height, width, height);
                    RenderSystem.disableBlend();
                    ci.cancel();
                }
            }
        } else if (offHandItem.getItem() instanceof ProjectileItem blasterOff) {       //holding only in off hand
            customCrosshair = WeaponZoomUtil.getCrosshairTexture(blasterOff);
            customScope = WeaponZoomUtil.getScopeTexture(blasterOff, player.getOffhandItem());
            if (player.isShiftKeyDown() && (customScope != null)) {
                customCrosshair = null;
                int width = mc.getWindow().getGuiScaledWidth();
                int height = mc.getWindow().getGuiScaledHeight();

                RenderSystem.enableBlend();
                guiGraphics.blit(customScope, 0, 0, 0, 0, width, height, width, height);
                RenderSystem.disableBlend();
                ci.cancel();
            }
        }

        if (customCrosshair != null) {
            int x = (guiGraphics.guiWidth() - 16) / 2;
            int y = (guiGraphics.guiHeight() - 16) / 2;

            RenderSystem.enableBlend();
            guiGraphics.blit(customCrosshair, x, y, 0, 0, 16, 16, 16, 16);
            RenderSystem.disableBlend();
            ci.cancel();
        }
    }
}