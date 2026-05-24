package net.uhhitscam.knightfall.item.custom;

import net.minecraft.resources.ResourceLocation;
import net.uhhitscam.knightfall.OperationKnightfall;

public enum ScopeTexture {
    CIRCLE_BLUE_CROSS("circle_blue_cross_scope.png"),
    CIRCLE_BLUE_CROSS_2("circle_blue_cross_2_scope.png"),
    CIRCLE_BLUE_CROSS_3("circle_blue_cross_3_scope.png"),
    CIRCLE_BLUE("circle_blue_scope.png"),
    CIRCLE_BLUE_BRACKET("circle_blue_bracket_scope.png"),
    CIRCLE_BLUE_RED_CROSS("circle_blue_red_cross_scope.png"),
    CIRCLE_GREEN_REACTOR("circle_green_reactor_scope.png"),
    CIRCLE_GREEN("circle_green_scope.png"),
    CIRCLE_GREEN_2("circle_green_2_scope.png"),
    CIRCLE_GREEN_RED("circle_green_red_scope.png"),
    CIRCLE_GREEN_YELLOW("circle_green_yellow_scope.png"),
    CIRCLE_GREY_BLUE("circle_grey_blue_scope.png"),
    CIRCLE_GREY_DOT("circle_grey_dot_scope.png"),
    CIRCLE_GREY_CROSS("circle_grey_cross_scope.png"),
    CIRCLE_GREY_TRI_DASH("circle_grey_tri_dash_scope.png"),
    CIRCLE_GREY("circle_grey_scope.png"),
    CIRCLE_GOLD_V("circle_gold_v_scope.png"),
    CIRCLE_RED_ANGLED_BRACKET("circle_red_angled_bracket_scope.png"),
    CIRCLE_RED_BRACKET_THERMAL("circle_red_bracket_scope_thermal.png"),
    CIRCLE_RED_CROSS("circle_red_cross_scope.png"),
    CIRCLE_RED_DOT("circle_red_dot_scope.png"),
    CIRCLE_YELLOW("circle_yellow_scope.png"),
    CLASSIC("classic_scope.png"),
    CLASSIC_BUBBLED("classic_bubbled_scope.png"),
    CLASSIC_BUBBLED_GREEN("classic_bubbled_green_scope.png"),
    OCTAGONAL_RED("octagonal_red_scope.png"),
    OVAL_LONG_ORANGE("oval_long_orange_scope.png"),
    OVAL_LONG_RED_BLUE("oval_long_red_blue_scope.png"),
    OVAL_LONG_RED("oval_long_red_scope.png"),
    OVAL_LONG_RED_THERMAL("oval_long_red_scope_thermal.png"),
    OVAL_LONG_RED_ARROW("oval_long_red_arrow_scope.png"),
    OVAL_SMALL_FANCY("oval_small_fancy_scope.png"),
    OVAL_SMALL_INDENT_GREEN("oval_small_indent_green_scope.png"),
    OVAL_SMALL_SOFT_FOCUS("oval_small_soft_focus_scope.png"),
    OVAL_SMALL_X("oval_small_x_scope.png"),
    OVAL_SMALL_VERT_DOT("oval_small_vert_dot_scope.png");

    private final ResourceLocation texture;

    ScopeTexture(String fileName) {
        this.texture = ResourceLocation.fromNamespaceAndPath(
                OperationKnightfall.MODID,
                "textures/gui/" + fileName
        );
    }

    public ResourceLocation texture() {
        return texture;
    }
}