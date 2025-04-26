package net.uhhitscam.starwars.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.uhhitscam.starwars.OperationKnightfall;
import net.uhhitscam.starwars.item.custom.BlasterItem;
import net.uhhitscam.starwars.util.KeyBinding;

public class HudClient {
    public static final int WHITE = 0xffffff;
    public static final int RED = 0xdb3559;
    public static final int OUTLINE_COLOR = 0x0b2347;
    public static final int TRANSPARENT = -1;

    public static boolean enabled = true;

    public static void init() {
        KeyBinding.init();
    }

    public static void onRenderHUD(GuiGraphics guiGraphics) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.getDebugOverlay().showDebugScreen() || mc.options.hideGui || !enabled) {
            return;
        }

        Font textRenderer = mc.font;
        LocalPlayer player = mc.player;
        if (player == null) return;

        int guiHeight = guiGraphics.guiHeight();
        int guiWidth = guiGraphics.guiWidth();
        int guiMidHeight = guiHeight/2;
        int guiMidWidth = guiWidth/2;

        Level level = player.level();
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();

        Direction direction = player.getDirection();
        String directionName = direction.getName().toUpperCase();
        Component directionComponent = Component.literal(String.format("%s", directionName));
        int directionTextWidth = textRenderer.width(directionComponent);
        text(guiGraphics, textRenderer, directionComponent, guiMidWidth - (directionTextWidth / 2), 2, WHITE, OUTLINE_COLOR);

        Component coordinatesComponent = Component.literal(String.format("XYZ: %.1f / %.1f / %.1f", x, y, z));
        text(guiGraphics, textRenderer, coordinatesComponent, 2, 2, WHITE, OUTLINE_COLOR);

        if (player.getOffhandItem().getItem() instanceof BlasterItem offhandBlasterItem) {
            int offhandAmmo = offhandBlasterItem.getAmmo(player.getOffhandItem());
            int offhandMaxAmmo = offhandBlasterItem.getMaxAmmo();
            int offhandBlasterAmmoPercent = (offhandMaxAmmo > 0) ? (int) ((double) offhandAmmo / offhandMaxAmmo * 100) : 0;

            Component offhandAmmoComponent = Component.literal(String.format("Ammo: %d%%", offhandBlasterAmmoPercent));
            text(guiGraphics, textRenderer, offhandAmmoComponent, guiMidWidth - 180, guiHeight - 14, WHITE, OUTLINE_COLOR);

            String offhandFiringMode = offhandBlasterItem.getFiringMode(player.getOffhandItem());
            ResourceLocation texture = switch (offhandFiringMode) {
                case "BURST" -> ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/burst_icon.png");
                case "STUN" -> ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/stun_icon.png");
                case "SCATTER" -> ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/scatter_icon.png");
                case "FULL_AUTO" -> ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/full_auto_icon.png");
                case "LAUNCHER" -> ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/launcher_icon.png");
                case "CHARGED" -> ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/charged_icon.png");
                case "SNIPER" -> ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/sniper_icon.png");
                case "REPULSE" -> ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/repulse_icon.png");
                default -> ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/semi_auto_icon.png");
            };
            guiGraphics.blit(texture, guiMidWidth - 195, guiHeight - 17, 0, 0, 12, 12, 12, 12);
        }

        if (player.getMainHandItem().getItem() instanceof BlasterItem mainHandBlasterItem) {
            int mainHandAmmo = mainHandBlasterItem.getAmmo(player.getMainHandItem());
            int mainHandMaxAmmo = mainHandBlasterItem.getMaxAmmo();
            int mainHandBlasterAmmoPercent = (mainHandMaxAmmo > 0) ? (int) ((double) mainHandAmmo / mainHandMaxAmmo * 100) : 0;

            Component mainHandAmmoComponent = Component.literal(String.format("Ammo: %d%%", mainHandBlasterAmmoPercent));
            text(guiGraphics, textRenderer, mainHandAmmoComponent, guiMidWidth + 115, guiHeight - 14, WHITE, OUTLINE_COLOR);

            String mainHandFiringMode = mainHandBlasterItem.getFiringMode(player.getMainHandItem());
            ResourceLocation texture = switch (mainHandFiringMode) {
                case "BURST" -> ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/burst_icon.png");
                case "STUN" -> ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/stun_icon.png");
                case "SCATTER" -> ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/scatter_icon.png");
                case "FULL_AUTO" -> ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/full_auto_icon.png");
                case "LAUNCHER" -> ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/launcher_icon.png");
                case "CHARGED" -> ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/charged_icon.png");
                case "SNIPER" -> ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/sniper_icon.png");
                case "REPULSE" -> ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/repulse_icon.png");
                default -> ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/semi_auto_icon.png");
            };
            guiGraphics.blit(texture, guiMidWidth + 100, guiHeight - 17, 0, 0, 12, 12, 12, 12);
        }
    }

    private static void text(GuiGraphics context, Font font, Component message, int x, int y, int color, int outlineColor) {
        if (outlineColor == TRANSPARENT) {
            context.drawString(font, message, x, y, color, false);
        } else {
            font.drawInBatch8xOutline(
                    message.getVisualOrderText(),
                    x,
                    y,
                    color,
                    outlineColor,
                    context.pose().last().pose(),
                    context.bufferSource(),
                    15728880
            );
            context.flush();
        }
    }

    public static void onClientTick() {
//        LocalPlayer player = Minecraft.getInstance().player;
//
//        if (player.getMainHandItem().getItem() instanceof BlasterItem blasterItem) {
//
//        }
    }
}
