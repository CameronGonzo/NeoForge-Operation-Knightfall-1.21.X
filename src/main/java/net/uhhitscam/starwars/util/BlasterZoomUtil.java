package net.uhhitscam.starwars.util;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.uhhitscam.starwars.OperationKnightfall;
import net.uhhitscam.starwars.item.custom.BlasterItem;

public class BlasterZoomUtil {
    public static float getBlasterZoomFactor(LocalPlayer player) {
        ItemStack mainHandItem = player.getMainHandItem();
        ItemStack offHandItem = player.getOffhandItem();

        if (mainHandItem.getItem() instanceof BlasterItem blasterMain) {
            if (mainHandItem.getItem() instanceof BlasterItem && offHandItem.getItem() instanceof BlasterItem blasterOff) {      //holding two blasters
                BlasterItem prioritizedBlaster;
                ItemStack prioritizedItem;
                if (getZoomFactor(blasterMain, player.getMainHandItem()) >= getZoomFactor(blasterOff, player.getOffhandItem())) {
                    prioritizedItem = player.getMainHandItem();
                    prioritizedBlaster = blasterMain;
                } else {
                    prioritizedItem = player.getOffhandItem();
                    prioritizedBlaster = blasterOff;
                }
                return Math.max(getZoomFactor(prioritizedBlaster, prioritizedItem), 0.7f);
            } else {        //holding only in main hand
                return getZoomFactor(blasterMain, player.getMainHandItem());
            }
        } else if (offHandItem.getItem() instanceof BlasterItem blasterOff) {       //holding only in off hand
            return getZoomFactor(blasterOff, player.getOffhandItem());
        }
        return 1f;      //if all else fails, return standard zoom
    }

    public static float getZoomFactor(BlasterItem blasterItem, ItemStack blasterStack) {
        ResourceLocation regName = BuiltInRegistries.ITEM.getKey(blasterItem);
        String blasterName = regName.getPath();
        String blasterFireMode = blasterItem.getFiringMode(blasterStack);

        if (blasterFireMode.equals("STUN")) {
            return 0.85f;
        }

        switch (blasterName) {
            case "_773_firepuncher":
                if (blasterFireMode.equals("SNIPER")){
                    return 0.15f;
                } else {
                    return 0.45f;
                }
            case "a180":
                if (blasterFireMode.equals("FULL_AUTO")) {
                    return 0.55f;
                } else if (blasterFireMode.equals("SNIPER")){
                    return 0.2f;
                } else {
                    return 0.85f;
                }
            case "a280cfe":
                if (blasterFireMode.equals("BURST")) {
                    return 0.55f;
                } else if (blasterFireMode.equals("SNIPER")){
                    return 0.3f;
                } else {
                    return 0.8f;
                }
            case "dc17m":
                return switch (blasterFireMode) {
                    case "CHARGED" -> 0.25f;
                    case "SNIPER" -> 0.2f;
                    case "LAUNCHER" -> 0.8f;
                    default -> 0.75f;
                };
            case "relby_v10":
                if (blasterFireMode.equals("CHARGED")) {
                    return 0.15f;
                } else if (blasterFireMode.equals("LAUNCHER")){
                    return 0.8f;
                } else {
                    return 0.2f;
                }
            case "dl44":
                if (blasterFireMode.equals("SNIPER")) {
                    return 0.35f;
                } else {
                    return 0.75f;
                }
            case "baton_blaster":
                return 1f;
            case "fp45", "panic_pistol", "reciprocating_quad_blaster_cannon", "vulk_tau623_rotary":
                return 0.9f;
            case "ac177", "b22", "bh4", "boonta_blaster", "br14", "cr2", "cs14", "dh23", "dt29", "e5c", "ec17", "fc1_flechette_launcher",
                 "krie4", "m61", "q2", "shard3a", "webly_s4", "z6_rotary":
                return 0.85f;
            case "akbarc", "bryar_rifle", "e5_ce", "m32", "m55", "motto_mk_4", "mwc35c", "vect_uzi", "verpine_shatter":
                return 0.8f;
            case "b1na", "dfd1", "dh17", "dlt18", "dlt19", "dp23", "dt15", "e5", "e5_bx", "e10", "e10_5", "e11_rifle", "e22", "hf94",
                 "ib94", "ll30", "lw896", "model_57", "night_wind_rifle", "ok98", "rg4d", "s195", "sacros_k11", "satines_lament",
                 "sedgleys_mk_5", "snubble", "tca_pro", "x30", "x8_night_sniper":
                return 0.75f;
            case "astra40", "berserker", "blaster_spear", "cj9_bo_rifle", "dc12u", "de10", "dg29", "dl21", "dls12", "e11d", "elg3a",
                 "flintloq_rifle", "imperial_supercommando_blaster", "l60", "leucht42", "m12", "neo_crusader_rifle", "quarren_rifle",
                 "umbaran_pistol":
                return 0.7f;
            case "dressellian_projectile_rifle", "ee3", "m45", "mk_ii_paladin":
                return 0.65f;
            case "bowcaster", "corpo_rifle", "e10r", "rt97c":
                return 0.6f;
            case "westarm5":
                return 0.55f;
            case "balnab", "be29", "e11b", "t21b":
                return 0.5f;
            case "bm107", "dc15a", "iqa11":
                return 0.4f;
            case "dh447", "e5s", "galar90", "night_stinger", "outland_rifle", "pk23", "valken38x", "weequay_lance":
                return 0.25f;
            case "amban_disruptor", "dc15x", "e11s", "ge36", "weequay_rifle":
                return 0.2f;
            case "czerka_adventurer", "jezali_cycler_rifle":
                return 0.15f;
            default:
                return switch (blasterItem.getClassification()) {
                    case "SCATTER" -> 0.85f;
                    case "PISTOL" -> 0.8f;
                    case "REPEATER" -> 0.75f;
                    case "CARBINE" -> 0.7f;
                    case "DISRUPTOR" -> 0.6f;
                    case "SLUGTHROWER" -> 0.25f;
                    case "SNIPER" -> 0.15f;
                    default -> 0.5f;
                };
        }
    }

    public static ResourceLocation getCrosshairTexture(BlasterItem blasterItem) {
        ResourceLocation regName = BuiltInRegistries.ITEM.getKey(blasterItem);
        String blasterName = regName.getPath();

        return switch (blasterName) {
            case "dh447", "dlt19x", "dlt20a", "pk23", "quarren_rifle" ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/_v__crosshair.png");
            case "avarik", "boonta_blaster", "dlt19d", "dt15", "galar90", "renegade", "sedgleys_mk_5",
                 "walther_blaster", "weequay_lance" ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/morse_crosshair.png");
            case "acp_repeater", "ca87", "dlt18", "dlt19", "eweb", "ec17", "ee4", "fn57", "m61", "se14c",
                 "smart_rocket", "webly_s4", "z6_rotary" ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/soft_focus_crosshair.png");
            case "berserker", "cc420", "dx13", "ee3", "ge36", "m45", "premier", "sacros_k11", "type14",
                 "vanguard_scatter" ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/horizontal_vs_crosshair.png");
            case "az2m", "dg29", "flintloq_rifle", "krie4", "m12", "mk_ii_paladin" ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/half_circle_crosshair.png");
            case "blndrbus", "btx42", "d72w", "dc15le", "motto_mk_4", "opressor", "vulk_tau623_rotary",
                 "winchester87" ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_crosshair.png");
            case "a350", "bm107", "bowcaster", "cj9_bo_rifle", "corpo_rifle", "e10r", "fc1_flechette_launcher",
                 "imperial_supercommando_blaster", "kueget_ln21", "ll30", "m41", "rt97c", "s5", "tomsun97",
                 "weequay_pistol", "x8_night_sniper" ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/quad_dot_crosshair.png");
            case "ewhb12", "kisteer_1284", "m55", "night_stinger", "relby_v10" ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/reactor_crosshair.png");
            case "cs14", "dark_trooper_rifle", "das430", "dc19", "dressellian_projectile_rifle", "dt29", "fp45",
                 "lw896", "night_wind_rifle", "p38", "q2", "rk3", "s195" ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/small_circle_crosshair.png");
            case "_62aug2_hunting_rifle", "_84u_hunting_rifle", "amban_disruptor", "cycler_rifle", "e11s", "e5s",
                 "iqa11", "nt242", "outland_rifle", "shadow_trooper_blaster", "valken38x" ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/t_crosshair.png");
            case "dn_bolt_caster", "e5c", "k16_bryar_pistol", "m32", "minimag", "rig420", "rps6", "sonic_blaster",
                 "t21", "tl50", "v6d" ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/tri_circle_crosshair.png");
            case "_434_deathhammer", "apache", "az11", "blurrg1120", "de10", "dt57", "e11d", "e5_bx", "ib94",
                 "leucht42", "se14r", "sk32" ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/tri_dash_crosshair.png");
            case "caij_vandas_blaster_pistol", "dfd1", "dh23", "dt12", "e5_ce", "kyd21", "marg_mcm", "steyr43",
                 "t7_ion_disruptor", "verpine_shatter" ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/tri_dot_crosshair.png");
            case "_773_firepuncher", "dc15s_sidearm", "dc15x", "dc17m", "mwc35c", "ok98", "panic_pistol", "plx1", "t6",
                 "umbaran_pistol", "westarm5" ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/tri_line_crosshair.png");
            case "b22", "baton_blaster", "be29", "blaster_spear", "br14", "dc12u", "dl18", "elg3a",
                 "jezali_cycler_rifle", "l5", "l60", "lug_po8", "mw20_bryar_pistol", "neo_crusader_rifle", "relby_k23",
                 "ruger_blaster", "serexim_mk_5", "shard3a", "tca_pro", "weequay_rifle", "westar_20" ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/v_crosshair.png");
            case "a140", "a280c", "a300", "barmst12", "be09", "boiler_rifle", "c10", "cr2", "cspl12", "flintloq_pistol",
                 "flite37", "galaar15", "koch9s", "nambu14", "rg4d", "snubble", "westar_34" ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/vertical_soft_focus_crosshair.png");
            case "ac177", "astra40", "bh4", "deftech", "dls12", "e22", "gl77", "model_57", "satines_lament", "sx21",
                 "vect_uzi", "verpine_shatter_rifle", "westar_35" ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/vertical_vs_crosshair.png");
            default ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/cross_crosshair.png");
        };
    }

    public static ResourceLocation getScopeTexture(BlasterItem blasterItem, ItemStack blasterStack) {
        ResourceLocation regName = BuiltInRegistries.ITEM.getKey(blasterItem);
        String blasterName = regName.getPath();
        String blasterFireMode = blasterItem.getFiringMode(blasterStack);

        if (blasterFireMode.equals("STUN")) {
            return null;
        }

        switch (blasterName) {
            case "a280cfe":
                if (!blasterFireMode.equals("SEMI_AUTO")) {
                    return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_green_2_scope.png");
                } else {
                    return null;
                }
            case "relby_v10":
                if (!blasterFireMode.equals("LAUNCHER")) {
                    return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_green_reactor_scope.png");
                } else {
                    return null;
                }
            case "dc17m":
                if (blasterFireMode.equals("SNIPER") || blasterFireMode.equals("CHARGED")) {
                    return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_grey_blue_scope.png");
                } else {
                    return null;
                }
            case "dl44":
                if (blasterFireMode.equals("SNIPER") ) {
                    return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_grey_cross_scope.png");
                } else {
                    return null;
                }
            case "a180":
                if (blasterFireMode.equals("FULL_AUTO")) {
                    return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/oval_small_soft_focus_scope.png");
                } else if (blasterFireMode.equals("SNIPER")) {
                    return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/oval_small_x_scope.png");
                } else {
                    return null;
                }
            case "_773_firepuncher", "avarik", "balnab":
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_blue_cross_scope.png");
            case "bowcaster", "deftech":
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_blue_cross_2_scope.png");
            case "galar90", "imperial_supercommando_blaster":
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_blue_cross_3_scope.png");
            case "e11_rifle", "e11b", "m45":
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_blue_scope.png");
            case "dlt20a":
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_blue_bracket_scope.png");
            case "e10r":
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_green_reactor_scope.png");
            case "cj9_bo_rifle", "night_stinger", "pk23":
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_green_scope.png");
            case "dh447", "ka74":
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_green_2_scope.png");
            case "e5s":
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_green_yellow_scope.png");
            case "x8_night_sniper":
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_green_yellow_scope.png");
            case "ok98", "valken38x":
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_grey_blue_scope.png");
            case "be29", "corpo_rifle", "dg29", "hf94":
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_grey_dot_scope.png");
            case "cycler_rifle", "jezali_cycler_rifle", "outland_rifle":
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_grey_cross_scope.png");
            case "boiler_rifle", "de10", "ee3":
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_grey_tri_dash_scope.png");
            case "_84u_hunting_rifle", "weequay_rifle":
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_grey_scope.png");
            case "jnd41":
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_gold_v_scope.png");
            case "kisteer_1284":
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_red_bracket_scope.png");
            case "e11s", "shadow_trooper_blaster":
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_red_dot_scope.png");
            case "_62aug2_hunting_rifle", "czerka_adventurer", "weequay_lance":
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_yellow_scope.png");
            case "a280", "a280c", "a295":
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/classic_scope.png");
            case "dh17", "e17d", "rt97c":
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/classic_bubbled_scope.png");
            case "dlt19x", "nt242":
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/classic_bubbled_green_scope.png");
            case "ge36":
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/octagonal_red_scope.png");
            case "iqa11":
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/oval_long_orange_scope.png");
            case "amban_disruptor":
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/oval_long_red_blue_scope.png");
            case "_785mk_firepuncherx":
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/oval_long_red_scope.png");
            case "t21b":
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/oval_long_red_arrow_scope.png");
            case "dc15x":
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/oval_small_fancy_scope.png");
            case "a350":
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/oval_small_indent_green.png");
            case "a300", "a310":
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/oval_small_soft_focus_scope.png");
            case "dc15a", "dc15le", "westarm5":
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/oval_small_x_scope.png");
            case "bm107", "dlt19d":
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/oval_vertical_small_dot_scope.png");
            default:
                return null;
        }
    }
}
