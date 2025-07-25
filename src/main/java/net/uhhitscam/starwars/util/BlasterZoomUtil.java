package net.uhhitscam.starwars.util;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.uhhitscam.starwars.OperationKnightfall;
import net.uhhitscam.starwars.item.custom.BlasterItem;
import net.uhhitscam.starwars.item.custom.BlasterName;
import net.uhhitscam.starwars.item.custom.Classification;
import net.uhhitscam.starwars.item.custom.FiringMode;

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
        BlasterName blasterName = blasterItem.getBlasterName();
        FiringMode firingMode = blasterItem.getFiringMode(blasterStack);

        if (firingMode.equals(FiringMode.STUN)) {
            return 0.85f;
        }

        switch (blasterName) {
            case BlasterName._773_FIREPUNCHER:
                if (firingMode.equals(FiringMode.SNIPER)){
                    return 0.15f;
                } else {
                    return 0.45f;
                }
            case BlasterName.A180:
                if (firingMode.equals(FiringMode.FULL_AUTO)) {
                    return 0.55f;
                } else if (firingMode.equals(FiringMode.SNIPER)){
                    return 0.2f;
                } else {
                    return 0.85f;
                }
            case BlasterName.A280CFE:
                if (firingMode.equals(FiringMode.BURST)) {
                    return 0.55f;
                } else if (firingMode.equals(FiringMode.SNIPER)){
                    return 0.3f;
                } else {
                    return 0.8f;
                }
            case BlasterName.DC17M:
                return switch (firingMode) {
                    case FiringMode.CHARGED -> 0.25f;
                    case FiringMode.SNIPER -> 0.2f;
                    case FiringMode.LAUNCHER -> 0.8f;
                    default -> 0.75f;
                };
            case BlasterName.RELBY_V10:
                if (firingMode.equals(FiringMode.CHARGED)) {
                    return 0.15f;
                } else if (firingMode.equals(FiringMode.LAUNCHER)){
                    return 0.8f;
                } else {
                    return 0.2f;
                }
            case BlasterName.DL44:
                if (firingMode.equals(FiringMode.SNIPER)) {
                    return 0.35f;
                } else {
                    return 0.75f;
                }
            case BlasterName.BATON_BLASTER:
                return 1f;
            case BlasterName.FP45, BlasterName.PANIC_PISTOL, BlasterName.RECIPROCATING_QUAD_BLASTER_CANNON, BlasterName.VULK_TAU623_ROTARY:
                return 0.9f;
            case BlasterName.AC177, BlasterName.B22, BlasterName.BH4, BlasterName.BOONTA_BLASTER, BlasterName.BR14, BlasterName.CR2, BlasterName.CS14, BlasterName.DH23, BlasterName.DT29, BlasterName.E5C, BlasterName.EC17, BlasterName.FC1_FLECHETTE_LAUNCHER,
                 BlasterName.KRIE4, BlasterName.M61, BlasterName.Q2, BlasterName.SHARD3A, BlasterName.WEBLY_S4, BlasterName.Z6_ROTARY:
                return 0.85f;
            case BlasterName.AKBARC, BlasterName.BRYAR_RIFLE, BlasterName.E5_CE, BlasterName.M32, BlasterName.M55, BlasterName.MOTTO_MK_4, BlasterName.MWC35C, BlasterName.VECT_UZI, BlasterName.VERPINE_SHATTER:
                return 0.8f;
            case BlasterName.B1NA, BlasterName.DFD1, BlasterName.DH17, BlasterName.DLT18, BlasterName.DLT19, BlasterName.DP23, BlasterName.DT15, BlasterName.E5, BlasterName.E5_BX, BlasterName.E10, BlasterName.E10_5, BlasterName.E11_RIFLE, BlasterName.E22, BlasterName.HF94,
                 BlasterName.IB94, BlasterName.LL30, BlasterName.LW896, BlasterName.MODEL_57, BlasterName.NIGHT_WIND_RIFLE, BlasterName.OK98, BlasterName.RG4D, BlasterName.S195, BlasterName.SACROS_K11, BlasterName.SATINES_LAMENT,
                 BlasterName.SEDGLEYS_MK_5, BlasterName.SNUBBLE, BlasterName.TCA_PRO, BlasterName.X30, BlasterName.X8_NIGHT_SNIPER:
                return 0.75f;
            case BlasterName.ASTRA40, BlasterName.BERSERKER, BlasterName.BLASTER_SPEAR, BlasterName.CJ9_BO_RIFLE, BlasterName.DC12U, BlasterName.DE10, BlasterName.DG29, BlasterName.DL21, BlasterName.DLS12, BlasterName.E11D, BlasterName.ELG3A,
                 BlasterName.FLINTLOQ_RIFLE, BlasterName.IMPERIAL_SUPERCOMMANDO_BLASTER, BlasterName.L60, BlasterName.LEUCHT42, BlasterName.M12, BlasterName.NEO_CRUSADER_RIFLE, BlasterName.QUARREN_RIFLE,
                 BlasterName.UMBARAN_PISTOL:
                return 0.7f;
            case BlasterName.DRESSELLIAN_PROJECTILE_RIFLE, BlasterName.EE3, BlasterName.M45, BlasterName.MK_II_PALADIN:
                return 0.65f;
            case BlasterName.BOWCASTER, BlasterName.CORPO_RIFLE, BlasterName.E10R, BlasterName.RT97C:
                return 0.6f;
            case BlasterName.WESTARM5:
                return 0.55f;
            case BlasterName.BALNAB, BlasterName.BE29, BlasterName.E11B, BlasterName.T21B:
                return 0.5f;
            case BlasterName.BM107, BlasterName.DC15A, BlasterName.IQA11:
                return 0.4f;
            case BlasterName.DH447, BlasterName.E5S, BlasterName.GALAR90, BlasterName.NIGHT_STINGER, BlasterName.OUTLAND_RIFLE, BlasterName.PK23, BlasterName.VALKEN38X, BlasterName.WEEQUAY_LANCE:
                return 0.25f;
            case BlasterName.AMBAN_DISRUPTOR, BlasterName.DC15X, BlasterName.E11S, BlasterName.GE36, BlasterName.WEEQUAY_RIFLE:
                return 0.2f;
            case BlasterName.CZERKA_ADVENTURER, BlasterName.JEZALI_CYCLER_RIFLE:
                return 0.15f;
            default:
                return switch (blasterItem.getClassification()) {
                    case Classification.SCATTER -> 0.85f;
                    case Classification.PISTOL -> 0.8f;
                    case Classification.REPEATER -> 0.75f;
                    case Classification.CARBINE -> 0.7f;
                    case Classification.DISRUPTOR -> 0.6f;
                    case Classification.SLUGTHROWER -> 0.25f;
                    case Classification.SNIPER -> 0.15f;
                    default -> 0.5f;
                };
        }
    }

    public static ResourceLocation getCrosshairTexture(BlasterItem blasterItem) {
        BlasterName blasterName = blasterItem.getBlasterName();

        return switch (blasterName) {
            case BlasterName.DH447, BlasterName.DLT19X, BlasterName.DLT20A, BlasterName.PK23, BlasterName.QUARREN_RIFLE ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/hill_crosshair.png");
            case BlasterName.AVARIK, BlasterName.BOONTA_BLASTER, BlasterName.DLT19D, BlasterName.DT15, BlasterName.GALAR90, BlasterName.RENEGADE, BlasterName.SEDGLEYS_MK_5,
                 BlasterName.WALTHER_BLASTER, BlasterName.WEEQUAY_LANCE ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/morse_crosshair.png");
            case BlasterName.ACP_REPEATER, BlasterName.CA87, BlasterName.DLT18, BlasterName.DLT19, BlasterName.EWEB, BlasterName.EC17, BlasterName.EE4, BlasterName.FN57, BlasterName.M61, BlasterName.SE14C,
                 /*BlasterName.SMART_ROCKET,*/ BlasterName.WEBLY_S4, BlasterName.Z6_ROTARY ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/soft_focus_crosshair.png");
            case BlasterName.BERSERKER, BlasterName.CC420, BlasterName.DX13, BlasterName.EE3, BlasterName.GE36, BlasterName.M45, BlasterName.PREMIER, BlasterName.SACROS_K11, BlasterName.TYPE14,
                 BlasterName.VANGUARD_SCATTER ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/angle_brackets_crosshair.png");
            case BlasterName.AZ2M, BlasterName.DG29, BlasterName.FLINTLOQ_RIFLE, BlasterName.KRIE4, BlasterName.M12, BlasterName.MK_II_PALADIN ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/bowl_crosshair.png");
            case BlasterName.BLNDRBUS, /*BlasterName.BTX42,*/ BlasterName.D72W, BlasterName.DC15LE, BlasterName.MOTTO_MK_4, /*BlasterName.OPRESSOR,*/ BlasterName.VULK_TAU623_ROTARY,
                 BlasterName.WINCHESTER87 ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_large_crosshair.png");
            case BlasterName.A350, BlasterName.BM107, BlasterName.BOWCASTER, BlasterName.CJ9_BO_RIFLE, BlasterName.CORPO_RIFLE, BlasterName.E10R, BlasterName.FC1_FLECHETTE_LAUNCHER,
                 BlasterName.IMPERIAL_SUPERCOMMANDO_BLASTER, BlasterName.KUEGET_LN21, BlasterName.LL30, BlasterName.M41, BlasterName.RT97C, BlasterName.S5, BlasterName.TOMSUN97,
                 BlasterName.WEEQUAY_PISTOL, BlasterName.X8_NIGHT_SNIPER ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/quad_dot_crosshair.png");
            case /*BlasterName.EWHB12,*/ BlasterName.KISTEER_1284, BlasterName.M55, BlasterName.NIGHT_STINGER, BlasterName.RELBY_V10 ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/reactor_crosshair.png");
            case BlasterName.CS14, BlasterName.DARK_TROOPER_RIFLE, BlasterName.DAS430, BlasterName.DC19, BlasterName.DRESSELLIAN_PROJECTILE_RIFLE, BlasterName.DT29, BlasterName.FP45,
                 BlasterName.LW896, BlasterName.NIGHT_WIND_RIFLE, BlasterName.P38, BlasterName.Q2, BlasterName.RK3, BlasterName.S195 ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_small_crosshair.png");
            case BlasterName._62AUG2_HUNTING_RIFLE, BlasterName._84U_HUNTING_RIFLE, BlasterName.AMBAN_DISRUPTOR, BlasterName.CYCLER_RIFLE, BlasterName.E11S, BlasterName.E5S,
                 BlasterName.IQA11, BlasterName.NT242, BlasterName.OUTLAND_RIFLE, BlasterName.SHADOW_TROOPER_BLASTER, BlasterName.VALKEN38X ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/tee_crosshair.png");
            case BlasterName.DN_BOLT_CASTER, BlasterName.E5C, BlasterName.K16_BRYAR_PISTOL, BlasterName.M32, /*BlasterName.MINIMAG,*/ BlasterName.RIG420, /*BlasterName.RPS6,*/ BlasterName.SONIC_BLASTER,
                 BlasterName.T21, BlasterName.TL50/*, BlasterName.V6D*/ ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/tri_circle_crosshair.png");
            case BlasterName._434_DEATHHAMMER, BlasterName.APACHE, BlasterName.AZ11, BlasterName.BLURRG1120, BlasterName.DE10, BlasterName.DT57, BlasterName.E11D, BlasterName.E5_BX, BlasterName.IB94,
                 BlasterName.LEUCHT42, BlasterName.SE14R, BlasterName.SK32 ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/tri_dash_crosshair.png");
            case BlasterName.CAIJ_VANDAS_BLASTER_PISTOL, BlasterName.DFD1, BlasterName.DH23, BlasterName.DT12, BlasterName.E5_CE, BlasterName.KYD21, BlasterName.MARG_MCM, BlasterName.STEYR43,
                 BlasterName.T7_ION_DISRUPTOR, BlasterName.VERPINE_SHATTER ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/tri_dot_crosshair.png");
            case BlasterName._773_FIREPUNCHER, BlasterName.DC15S_SIDEARM, BlasterName.DC15X, BlasterName.DC17M, BlasterName.MWC35C, BlasterName.OK98, BlasterName.PANIC_PISTOL, /*BlasterName.PLX1,*/ BlasterName.T6,
                 BlasterName.UMBARAN_PISTOL, BlasterName.WESTARM5 ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/tri_line_crosshair.png");
            case BlasterName.B22, BlasterName.BATON_BLASTER, BlasterName.BE29, BlasterName.BLASTER_SPEAR, BlasterName.BR14, BlasterName.DC12U, BlasterName.DL18, BlasterName.ELG3A,
                 BlasterName.JEZALI_CYCLER_RIFLE, BlasterName.L5, BlasterName.L60, BlasterName.LUG_PO8, BlasterName.MW20_BRYAR_PISTOL, BlasterName.NEO_CRUSADER_RIFLE, BlasterName.RELBY_K23,
                 BlasterName.RUGER_BLASTER, BlasterName.SEREXIM_MK_5, BlasterName.SHARD3A, BlasterName.TCA_PRO, BlasterName.WEEQUAY_RIFLE, BlasterName.WESTAR_20 ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/carrot_crosshair.png");
            case BlasterName.A140, BlasterName.A280C, BlasterName.A300, BlasterName.BARMST12, BlasterName.BE09, BlasterName.BOILER_RIFLE, BlasterName.C10, BlasterName.CR2, BlasterName.CSPL12, BlasterName.FLINTLOQ_PISTOL,
                 BlasterName.FLITE37, BlasterName.GALAAR15, BlasterName.KOCH9S, BlasterName.NAMBU14, BlasterName.RG4D, BlasterName.SNUBBLE, BlasterName.WESTAR_34 ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/vertical_soft_focus_crosshair.png");
            case BlasterName.AC177, BlasterName.ASTRA40, BlasterName.BH4, BlasterName.DEFTECH, BlasterName.DLS12, BlasterName.E22, BlasterName.GL77, BlasterName.MODEL_57, BlasterName.SATINES_LAMENT, BlasterName.SX21,
                 BlasterName.VECT_UZI, BlasterName.VERPINE_SHATTER_RIFLE, BlasterName.WESTAR_35 ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/vertical_angle_brackets_crosshair.png");
            default ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/cross_crosshair.png");
        };
    }

    public static ResourceLocation getScopeTexture(BlasterItem blasterItem, ItemStack blasterStack) {
        BlasterName blasterName = blasterItem.getBlasterName();
        FiringMode blasterFireMode = blasterItem.getFiringMode(blasterStack);

        if (blasterFireMode.equals(FiringMode.STUN)) {
            return null;
        }

        switch (blasterName) {
            case BlasterName.A280CFE:
                if (!blasterFireMode.equals(FiringMode.SEMI_AUTO)) {
                    return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_green_2_scope.png");
                } else {
                    return null;
                }
            case BlasterName.RELBY_V10:
                if (!blasterFireMode.equals(FiringMode.LAUNCHER)) {
                    return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_green_reactor_scope.png");
                } else {
                    return null;
                }
            case BlasterName.DC17M:
                if (blasterFireMode.equals(FiringMode.SNIPER) || blasterFireMode.equals(FiringMode.CHARGED)) {
                    return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_grey_blue_scope.png");
                } else {
                    return null;
                }
            case BlasterName.DL44:
                if (blasterFireMode.equals(FiringMode.SNIPER) ) {
                    return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_grey_cross_scope.png");
                } else {
                    return null;
                }
            case BlasterName.A180:
                if (blasterFireMode.equals(FiringMode.FULL_AUTO)) {
                    return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/oval_small_soft_focus_scope.png");
                } else if (blasterFireMode.equals(FiringMode.SNIPER)) {
                    return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/oval_small_x_scope.png");
                } else {
                    return null;
                }
            case BlasterName._773_FIREPUNCHER, BlasterName.AVARIK, BlasterName.BALNAB:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_blue_cross_scope.png");
            case BlasterName.BOWCASTER, BlasterName.DEFTECH:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_blue_cross_2_scope.png");
            case BlasterName.GALAR90, BlasterName.IMPERIAL_SUPERCOMMANDO_BLASTER:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_blue_cross_3_scope.png");
            case BlasterName.E11_RIFLE, BlasterName.E11B, BlasterName.M45:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_blue_scope.png");
            case BlasterName.DLT20A:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_blue_bracket_scope.png");
            case BlasterName.E10R:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_green_reactor_scope.png");
            case BlasterName.CJ9_BO_RIFLE, BlasterName.NIGHT_STINGER, BlasterName.PK23:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_green_scope.png");
            case BlasterName.DH447, BlasterName.KA74:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_green_2_scope.png");
            case BlasterName.E5S:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_green_yellow_scope.png");
            case BlasterName.X8_NIGHT_SNIPER:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_green_yellow_scope.png");
            case BlasterName.OK98, BlasterName.VALKEN38X:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_grey_blue_scope.png");
            case BlasterName.BE29, BlasterName.CORPO_RIFLE, BlasterName.DG29, BlasterName.HF94:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_grey_dot_scope.png");
            case BlasterName.CYCLER_RIFLE, BlasterName.JEZALI_CYCLER_RIFLE, BlasterName.OUTLAND_RIFLE:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_grey_cross_scope.png");
            case BlasterName.BOILER_RIFLE, BlasterName.DE10, BlasterName.EE3:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_grey_tri_dash_scope.png");
            case BlasterName._84U_HUNTING_RIFLE, BlasterName.WEEQUAY_RIFLE:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_grey_scope.png");
            case BlasterName.JND41:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_gold_v_scope.png");
            case BlasterName.KISTEER_1284:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_red_bracket_scope.png");
            case BlasterName.E11S, BlasterName.SHADOW_TROOPER_BLASTER:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_red_dot_scope.png");
            case BlasterName._62AUG2_HUNTING_RIFLE, BlasterName.CZERKA_ADVENTURER, BlasterName.WEEQUAY_LANCE:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_yellow_scope.png");
            case BlasterName.A280, BlasterName.A280C, BlasterName.A295:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/classic_scope.png");
            case BlasterName.DH17, BlasterName.E17D, BlasterName.RT97C:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/classic_bubbled_scope.png");
            case BlasterName.DLT19X, BlasterName.NT242:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/classic_bubbled_green_scope.png");
            case BlasterName.GE36:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/octagonal_red_scope.png");
            case BlasterName.IQA11:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/oval_long_orange_scope.png");
            case BlasterName.AMBAN_DISRUPTOR:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/oval_long_red_blue_scope.png");
            case BlasterName._785MK_FIREPUNCHERX:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/oval_long_red_scope.png");
            case BlasterName.T21B:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/oval_long_red_arrow_scope.png");
            case BlasterName.DC15X:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/oval_small_fancy_scope.png");
            case BlasterName.A350:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/oval_small_indent_green.png");
            case BlasterName.A300, BlasterName.A310:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/oval_small_soft_focus_scope.png");
            case BlasterName.DC15A, BlasterName.DC15LE, BlasterName.WESTARM5:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/oval_small_x_scope.png");
            case BlasterName.BM107, BlasterName.DLT19D:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/oval_small_vert_dot_scope.png");
            default:
                return null;
        }
    }
}
