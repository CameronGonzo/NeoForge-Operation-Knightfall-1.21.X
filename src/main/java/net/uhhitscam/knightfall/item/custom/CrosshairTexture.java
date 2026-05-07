package net.uhhitscam.knightfall.item.custom;

import net.minecraft.resources.ResourceLocation;
import net.uhhitscam.knightfall.OperationKnightfall;

public enum CrosshairTexture {
    ANGLE_BRACKETS("angle_brackets_crosshair.png"),
    ANGLE_BRACKETS_SQUARE("angle_brackets_square_crosshair.png"),
    BOWL("bowl_crosshair.png"),
    BOX_CROSS("box_cross_crosshair.png"),
    BOX_DOT("box_dot_crosshair.png"),
    BOX_QUAD_CROSS("box_quad_cross_crosshair.png"),
    CARROT("carrot_crosshair.png"),
    CIRCLE_CONCENTRIC("circle_concentric_crosshair.png"),
    CIRCLE_LARGE("circle_large_crosshair.png"),
    CIRCLE_LARGE_CROSS("circle_large_cross_crosshair.png"),
    CIRCLE_LARGE_DASH("circle_large_dash_crosshair.png"),
    CIRCLE_QUAD("circle_quad_crosshair.png"),
    CIRCLE_SMALL("circle_small_crosshair.png"),
    CIRCLE_SMALL_LINE("circle_small_line_crosshair.png"),
    CROSS("cross_crosshair.png"),
    CROSS_SKELETON("cross_skeleton_crosshair.png"),
    DOT("dot_crosshair.png"),
    HILL("hill_crosshair.png"),
    MORSE("morse_crosshair.png"),
    QUAD_DOT("quad_dot_crosshair.png"),
    QUAD_LINE("quad_line_crosshair.png"),
    REACTOR("reactor_crosshair.png"),
    SEXTUPLE_DASH("sextuple_dash_crosshair.png"),
    SOFT_FOCUS("soft_focus_crosshair.png"),
    TEE("tee_crosshair.png"),
    TRI_CIRCLE("tri_circle_crosshair.png"),
    TRI_DASH("tri_dash_crosshair.png"),
    TRI_DOT("tri_dot_crosshair.png"),
    TRI_LINE("tri_line_crosshair.png"),
    TRIANGLE("triangle_crosshair.png"),
    VERTICAL_ANGLE_BRACKETS("vertical_angle_brackets_crosshair.png"),
    VERTICAL_SOFT_FOCUS("vertical_soft_focus_crosshair.png"),
    _X("x_crosshair.png"),
    EYE("eye_crosshair.png");

    private final ResourceLocation texture;

    CrosshairTexture(String fileName) {
        this.texture = ResourceLocation.fromNamespaceAndPath(
                OperationKnightfall.MODID,
                "textures/gui/" + fileName
        );
    }

    public ResourceLocation texture() {
        return texture;
    }
}