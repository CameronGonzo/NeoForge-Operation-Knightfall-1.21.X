package net.uhhitscam.knightfall.mixin;

import com.mojang.blaze3d.platform.GlStateManager;
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
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.item.custom.ProjectileItem;
import net.uhhitscam.knightfall.util.ThermalVisionUtil;
import net.uhhitscam.knightfall.util.WeaponZoomUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
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

        if (player == null || mc.options.hideGui) {
            ThermalVisionUtil.setThermalActive(false);
            return;
        }

        ItemStack mainHandItem = player.getMainHandItem();
        ItemStack offHandItem = player.getOffhandItem();

        ResourceLocation customCrosshair = null;
        ResourceLocation customScope = null;
        boolean isThermalScope = false;
        boolean isScoping = false;

        if (mainHandItem.getItem() instanceof ProjectileItem blasterMain) {
            if (offHandItem.getItem() instanceof ProjectileItem blasterOff) {
                ProjectileItem prioritizedBlaster =
                        WeaponZoomUtil.getZoomFactor(blasterMain, mainHandItem) >= WeaponZoomUtil.getZoomFactor(blasterOff, offHandItem)
                                ? blasterMain
                                : blasterOff;

                customCrosshair = WeaponZoomUtil.getCrosshairTexture(prioritizedBlaster);
                ci.cancel();
            } else {
                customCrosshair = WeaponZoomUtil.getCrosshairTexture(blasterMain);
                customScope = WeaponZoomUtil.getScopeTexture(blasterMain, mainHandItem);

                if (player.isShiftKeyDown() && customScope != null) {
                    isScoping = true;
                    isThermalScope = isThermalScope(customScope);

                    renderScopeOverlay(guiGraphics, mc, customScope);
                    customCrosshair = null;
                    ci.cancel();
                }
            }
        } else if (offHandItem.getItem() instanceof ProjectileItem blasterOff) {
            customCrosshair = WeaponZoomUtil.getCrosshairTexture(blasterOff);
            customScope = WeaponZoomUtil.getScopeTexture(blasterOff, offHandItem);

            if (player.isShiftKeyDown() && customScope != null) {
                isScoping = true;
                isThermalScope = isThermalScope(customScope);

                renderScopeOverlay(guiGraphics, mc, customScope);
                customCrosshair = null;
                ci.cancel();
            }
        }

        ThermalVisionUtil.setThermalActive(isScoping && isThermalScope);

        if (customCrosshair != null) {
            int size = 18;
            int x = (guiGraphics.guiWidth() - size) / 2;
            int y = (guiGraphics.guiHeight() - size) / 2;

            renderCustomCrosshair(guiGraphics, customCrosshair, x, y, size);
            ci.cancel();
        }
    }

    @Unique
    private static boolean isThermalScope(ResourceLocation scopeTexture) {
        return scopeTexture.equals(ResourceLocation.fromNamespaceAndPath(
                OperationKnightfall.MODID,
                "textures/gui/oval_long_red_scope_thermal.png"
        )) || scopeTexture.equals(ResourceLocation.fromNamespaceAndPath(
                OperationKnightfall.MODID,
                "textures/gui/circle_red_bracket_scope_thermal.png"
        ));
    }

    @Unique
    private static void renderScopeOverlay(GuiGraphics guiGraphics, Minecraft mc, ResourceLocation scopeTexture) {
        int width = mc.getWindow().getGuiScaledWidth();
        int height = mc.getWindow().getGuiScaledHeight();

        RenderSystem.enableBlend();
        guiGraphics.blit(scopeTexture, 0, 0, 0, 0, width, height, width, height);
        RenderSystem.disableBlend();
    }

    @Unique
    private static void renderCustomCrosshair(GuiGraphics guiGraphics, ResourceLocation texture, int x, int y, int size) {
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);

        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(
                GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR,
                GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR,
                GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO
        );

        guiGraphics.blit(texture, x, y, 0, 0, size, size, size, size);

        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}