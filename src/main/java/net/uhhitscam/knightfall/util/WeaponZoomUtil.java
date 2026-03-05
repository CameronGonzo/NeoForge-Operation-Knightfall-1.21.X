package net.uhhitscam.knightfall.util;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.item.custom.ProjectileItem;
import net.uhhitscam.knightfall.item.custom.WeaponName;
import net.uhhitscam.knightfall.item.custom.WeaponClassification;
import net.uhhitscam.knightfall.item.custom.FiringMode;

public class WeaponZoomUtil {
    public static float getProjectileWeaponZoomFactor(LocalPlayer player) {
        ItemStack mainHandItem = player.getMainHandItem();
        ItemStack offHandItem = player.getOffhandItem();

        if (mainHandItem.getItem() instanceof ProjectileItem projectileWeaponMain) {
            if (mainHandItem.getItem() instanceof ProjectileItem && offHandItem.getItem() instanceof ProjectileItem projectileWeaponOff) {
                ProjectileItem prioritizedWeapon;
                ItemStack prioritizedItem;
                if (getZoomFactor(projectileWeaponMain, player.getMainHandItem()) >= getZoomFactor(projectileWeaponOff, player.getOffhandItem())) {
                    prioritizedItem = player.getMainHandItem();
                    prioritizedWeapon = projectileWeaponMain;
                } else {
                    prioritizedItem = player.getOffhandItem();
                    prioritizedWeapon = projectileWeaponOff;
                }
                return Math.max(getZoomFactor(prioritizedWeapon, prioritizedItem), 0.7f);
            } else {
                return getZoomFactor(projectileWeaponMain, player.getMainHandItem());
            }
        } else if (offHandItem.getItem() instanceof ProjectileItem blasterOff) {
            return getZoomFactor(blasterOff, player.getOffhandItem());
        }
        return 1f;
    }

    public static float getZoomFactor(ProjectileItem blasterItem, ItemStack blasterStack) {
        WeaponName blasterName = blasterItem.getProjectileWeaponName();
        FiringMode firingMode = blasterItem.getFiringMode(blasterStack);

        if (firingMode.equals(FiringMode.STUN)) {
            return 0.85f;
        }

        switch (blasterName) {
            case WeaponName._773_FIREPUNCHER:
                if (firingMode.equals(FiringMode.SNIPER)){
                    return 0.15f;
                } else {
                    return 0.45f;
                }
            case WeaponName.A180:
                if (firingMode.equals(FiringMode.FULL_AUTO)) {
                    return 0.55f;
                } else if (firingMode.equals(FiringMode.SNIPER)){
                    return 0.2f;
                } else {
                    return 0.85f;
                }
            case WeaponName.A280CFE:
                if (firingMode.equals(FiringMode.BURST)) {
                    return 0.55f;
                } else if (firingMode.equals(FiringMode.SNIPER)){
                    return 0.3f;
                } else {
                    return 0.8f;
                }
            case WeaponName.DC17M:
                return switch (firingMode) {
                    case FiringMode.CHARGENSHOOT -> 0.25f;
                    case FiringMode.SNIPER -> 0.2f;
                    case FiringMode.LAUNCHER -> 0.8f;
                    default -> 0.75f;
                };
            case WeaponName.RELBY_K25:
                if (firingMode.equals(FiringMode.SEMI_AUTO)) {
                    return 0.75f;
                } else {
                    return 0.5f;
                }
            case WeaponName.RELBY_V10:
                if (firingMode.equals(FiringMode.CHARGENSHOOTONRELEASE)) {
                    return 0.15f;
                } else if (firingMode.equals(FiringMode.LAUNCHER)){
                    return 0.8f;
                } else {
                    return 0.2f;
                }
            case WeaponName.DL44:
                if (firingMode.equals(FiringMode.SNIPER)) {
                    return 0.35f;
                } else {
                    return 0.75f;
                }
            case WeaponName.BATON_BLASTER, WeaponName.DEACTIVATOR, WeaponName.ION_STUNNER, WeaponName.SETTLERS_STUN:
                return 1f;
            case WeaponName.BALNAB_SIDEARM, WeaponName.BX33, WeaponName.FP45, WeaponName.KM9, WeaponName.PANIC_PISTOL, WeaponName.RECIPROCATING_QUAD_BLASTER_CANNON, WeaponName.S2S, WeaponName.VULK_TAU623_ROTARY, WeaponName.W50S:
                return 0.9f;
            case WeaponName._22T4, WeaponName.AC177, WeaponName.B22, WeaponName.BH4, WeaponName.BK28, WeaponName.BOONTA_BLASTER, WeaponName.BR14, WeaponName.BT06, WeaponName.CORE_J3, WeaponName.CR2, WeaponName.CS14, WeaponName.DH23, WeaponName.DT29, WeaponName.E5C, WeaponName.EC17, WeaponName.FC1_FLECHETTE_LAUNCHER, WeaponName.FWG5, WeaponName.FWG7,
                 WeaponName.GR4_ST, WeaponName.GR13, WeaponName.K13, WeaponName.SE9V, WeaponName.SNUB_BLASTER, WeaponName.SNUB_SCATTER, WeaponName.M61, WeaponName.MSD32, WeaponName.Q2, WeaponName.SHARD3A, WeaponName.UTK3, WeaponName.VT20, WeaponName.WS4, WeaponName.Z6_ROTARY:
                return 0.85f;
            case WeaponName.A240, WeaponName.ACP_ARRAY, WeaponName.B1X, WeaponName.B33, WeaponName.BK43, WeaponName.BRYAR_RIFLE, WeaponName.CORE_R5, WeaponName.DER4, WeaponName.DH42, WeaponName.DL11, WeaponName.DUJ3, WeaponName.DX2, WeaponName.E5_CARBINE, WeaponName.E5_CE, WeaponName.E11P, WeaponName.EL5, WeaponName.GA3R, WeaponName.GM46, WeaponName.M32,
                 WeaponName.M55, WeaponName.MOTTO_MK_4, WeaponName.MWC35C, WeaponName.R88, WeaponName.TL40, WeaponName.V13, WeaponName.VERPINE_SIDEARM, WeaponName.ZB3, WeaponName.ZYGERRIAN_BLASTER:
                return 0.8f;
            case WeaponName.ABR2_ZATO, WeaponName.B1NA, WeaponName.CHARRIC, WeaponName.CORE_U12, WeaponName.DFD1, WeaponName.DH17, WeaponName.DLT18, WeaponName.DLT19, WeaponName.DP23, WeaponName.DT15, WeaponName.E5, WeaponName.E5_BX, WeaponName.E10, WeaponName.E10_5, WeaponName.E11_RIFLE, WeaponName.E22, WeaponName.GRS1, WeaponName.HF94,
                 WeaponName.IB94, WeaponName.LL30, WeaponName.LW896, WeaponName.MODEL_57, WeaponName.AZ6, WeaponName.OK98, WeaponName.RG4D, WeaponName.S195, WeaponName.SACROS_K11, WeaponName.SATINES_LAMENT,
                 WeaponName.SEDGLEYS_MK_5, WeaponName.SNUBBLE, WeaponName.SONIC_BLASTER, WeaponName.TG446, WeaponName.UMBARAN_BLASTER, WeaponName.W90, WeaponName.WESTAR2L, WeaponName.WOOKIE_SIDEARM, WeaponName.X30, WeaponName.X8_NIGHT_SNIPER:
                return 0.75f;
            case WeaponName.AB75_BO_RIFLE, WeaponName.ASTRA40, WeaponName.BERSERKER, WeaponName.BLASTER_SPEAR, WeaponName.CJ9_BO_RIFLE, WeaponName.DC12U, WeaponName.DE10, WeaponName.DG29, WeaponName.DL21, WeaponName.DLS12, WeaponName.DUL4, WeaponName.DXR6, WeaponName.E11D, WeaponName.E11T, WeaponName.ELG3A,
                 WeaponName.F4L, WeaponName.GLX_FIRELANCE, WeaponName.J19_BO_RIFLE, WeaponName.KINETICBLAST, WeaponName.WESTARE9, WeaponName.WESTARL4, WeaponName.WESTARLVN, WeaponName.WOOKIE_RIFLE, WeaponName.L60, WeaponName.LJ40, WeaponName.LJ50, WeaponName.CW24, WeaponName.M12, WeaponName.NEO_CRUSADER_RIFLE, WeaponName.QUARREN_RIFLE:
                return 0.7f;
            case WeaponName.DRESSELLIAN_PROJECTILE_RIFLE, WeaponName.EE3, WeaponName.EL244, WeaponName.ESB3, WeaponName.HB9, WeaponName.M45, WeaponName.MK_II_PALADIN:
                return 0.65f;
            case WeaponName.BOWCASTER, WeaponName.BSR7, WeaponName.CORPO_RIFLE, WeaponName.DFQ91, WeaponName.E10R, WeaponName.RT97C:
                return 0.6f;
            case WeaponName.WESTARM5, WeaponName.DL23, WeaponName.DLT15:
                return 0.55f;
            case WeaponName.BALNAB, WeaponName.BE29, WeaponName.E11B, WeaponName.SER5, WeaponName.T21B, WeaponName.X47:
                return 0.5f;
            case WeaponName.BM107, WeaponName.BX49, WeaponName.DC15A, WeaponName.IQA11, WeaponName.PR9:
                return 0.4f;
            case WeaponName.CZERKA_19, WeaponName.DH447, WeaponName.E5S, WeaponName.GALAR90, WeaponName.NIGHT_STINGER, WeaponName.OUTLAND_RIFLE, WeaponName.PK23, WeaponName.VALKEN38X, WeaponName.WEEQUAY_LANCE, WeaponName.X45:
                return 0.25f;
            case WeaponName.AMBAN_DISRUPTOR, WeaponName.DC15X, WeaponName.E11S, WeaponName.GE36, WeaponName.NOSLO19, WeaponName.WEEQUAY_RIFLE, WeaponName.VERPINE_SHATTER_RIFLE:
                return 0.2f;
            case WeaponName.CZERKA_ADVENTURER, WeaponName.JEZALI_CYCLER_RIFLE, WeaponName.PRECISIONX, WeaponName.V850_MK:
                return 0.15f;
            default:
                return switch (blasterItem.getClassification()) {
                    case WeaponClassification.SCATTER -> 0.85f;
                    case WeaponClassification.PISTOL -> 0.8f;
                    case WeaponClassification.REPEATER -> 0.75f;
                    case WeaponClassification.CARBINE -> 0.7f;
                    case WeaponClassification.DISRUPTOR -> 0.6f;
                    case WeaponClassification.SLUGTHROWER -> 0.25f;
                    case WeaponClassification.SNIPER -> 0.15f;
                    default -> 0.5f;
                };
        }
    }

    public static ResourceLocation getCrosshairTexture(ProjectileItem blasterItem) {
        WeaponName blasterName = blasterItem.getProjectileWeaponName();

        return switch (blasterName) {
            case WeaponName.DH447, WeaponName.DLT19X, WeaponName.DLT20A, WeaponName.PK23, WeaponName.QUARREN_RIFLE ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/hill_crosshair.png");
            case WeaponName.AVARIK, WeaponName.BOONTA_BLASTER, WeaponName.DLT19D, WeaponName.DT15, WeaponName.GALAR90, WeaponName.RENEGADE, WeaponName.SEDGLEYS_MK_5,
                 WeaponName.W310, WeaponName.WEEQUAY_LANCE ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/morse_crosshair.png");
            case WeaponName.ACP_REPEATER, WeaponName.CA87, WeaponName.DLT18, WeaponName.DLT19, WeaponName.EWEB, WeaponName.EC17, WeaponName.EE4, WeaponName.FN57, WeaponName.M61, WeaponName.SE14C,
                 /*BlasterName.SMART_ROCKET,*/ WeaponName.WS4, WeaponName.Z6_ROTARY ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/soft_focus_crosshair.png");
            case WeaponName.BERSERKER, WeaponName.CC420, WeaponName.DX13, WeaponName.EE3, WeaponName.GE36, WeaponName.M45, WeaponName.RM7, WeaponName.SACROS_K11,
                 WeaponName.VANGUARD_SCATTER ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/angle_brackets_crosshair.png");
            case WeaponName.DG29, WeaponName.F4L, WeaponName.SE9V, WeaponName.M12, WeaponName.MK_II_PALADIN ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/bowl_crosshair.png");
            case WeaponName.BLNDRBUS, /*BlasterName.BTX42,*/ WeaponName.D72W, WeaponName.DC15LE, WeaponName.MOTTO_MK_4, /*BlasterName.OPRESSOR,*/ WeaponName.VULK_TAU623_ROTARY,
                 WeaponName.WINCHESTER87 ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_large_crosshair.png");
            case WeaponName.A350, WeaponName.BM107, WeaponName.BOWCASTER, WeaponName.CJ9_BO_RIFLE, WeaponName.CORPO_RIFLE, WeaponName.E10R, WeaponName.FC1_FLECHETTE_LAUNCHER,
                 WeaponName.WESTARE9, WeaponName.KUEGET_LN21, WeaponName.LL30, WeaponName.M41, WeaponName.RT97C, WeaponName.S5, WeaponName.T9K7,
                 WeaponName.WEEQUAY_PISTOL, WeaponName.X8_NIGHT_SNIPER ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/quad_dot_crosshair.png");
            case /*BlasterName.EWHB12,*/ WeaponName.KISTEER_1284, WeaponName.M55, WeaponName.NIGHT_STINGER, WeaponName.RELBY_V10 ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/reactor_crosshair.png");
            case WeaponName.CS14, WeaponName.E44, WeaponName.DAS430, WeaponName.DC19, WeaponName.DRESSELLIAN_PROJECTILE_RIFLE, WeaponName.DT29, WeaponName.FP45,
                 WeaponName.LW896, WeaponName.AZ6, WeaponName.F38G, WeaponName.Q2, WeaponName.RK3, WeaponName.S195 ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_small_crosshair.png");
            case WeaponName._62AUG2_HUNTING_RIFLE, WeaponName._84U_HUNTING_RIFLE, WeaponName.AMBAN_DISRUPTOR, WeaponName.CYCLER_RIFLE, WeaponName.E11S, WeaponName.E5S,
                 WeaponName.IQA11, WeaponName.NT242, WeaponName.OUTLAND_RIFLE, WeaponName.E9V, WeaponName.VALKEN38X ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/tee_crosshair.png");
            case WeaponName.DN_BOLT_CASTER, WeaponName.E5C, WeaponName.K16_BRYAR_PISTOL, WeaponName.M32, /*BlasterName.MINIMAG,*/ WeaponName.RIG420, /*BlasterName.RPS6,*/ WeaponName.SONIC_BLASTER,
                 WeaponName.T21, WeaponName.TL50/*, BlasterName.V6D*/ ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/tri_circle_crosshair.png");
            case WeaponName._434_DEATHHAMMER, WeaponName.APACHE, WeaponName.BLURRG1120, WeaponName.DE10, WeaponName.DT57, WeaponName.E11D, WeaponName.E5_BX, WeaponName.IB94,
                 WeaponName.CW24, WeaponName.SE14R, WeaponName.SK32 ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/tri_dash_crosshair.png");
            case WeaponName.LV7C, WeaponName.DFD1, WeaponName.DH23, WeaponName.DT12, WeaponName.E5_CE, WeaponName.KYD21, WeaponName.MARG_MCM, WeaponName.CW76,
                 WeaponName.T7_ION_DISRUPTOR, WeaponName.VERPINE_SIDEARM ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/tri_dot_crosshair.png");
            case WeaponName._773_FIREPUNCHER, WeaponName.DC15S_SIDEARM, WeaponName.DC15X, WeaponName.DC17M, WeaponName.MWC35C, WeaponName.OK98, WeaponName.PANIC_PISTOL, /*BlasterName.PLX1,*/ WeaponName.T6,
                 WeaponName.WESTARM5 ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/tri_line_crosshair.png");
            case WeaponName.B22, WeaponName.BATON_BLASTER, WeaponName.BE29, WeaponName.BLASTER_SPEAR, WeaponName.BR14, WeaponName.DC12U, WeaponName.DL18, WeaponName.ELG3A,
                 WeaponName.JEZALI_CYCLER_RIFLE, WeaponName.L5, WeaponName.L60, WeaponName.RK2P, WeaponName.MW20_BRYAR_PISTOL, WeaponName.NEO_CRUSADER_RIFLE, WeaponName.RELBY_K23,
                 WeaponName.RLR_MK_II, WeaponName.S3_MK_5, WeaponName.SHARD3A, WeaponName.WESTAR2L, WeaponName.WEEQUAY_RIFLE, WeaponName.WESTAR_20 ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/carrot_crosshair.png");
            case WeaponName.A140, WeaponName.A280C, WeaponName.A300, WeaponName.BARMST12, WeaponName.BE09, WeaponName.BOILER_RIFLE, WeaponName.C10, WeaponName.CR2, WeaponName.CSPL12, WeaponName.F2L,
                 WeaponName.FLITE37, WeaponName.GALAAR15, WeaponName.KL9, WeaponName.RG4D, WeaponName.SNUBBLE, WeaponName.WESTAR_34 ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/vertical_soft_focus_crosshair.png");
            case WeaponName.AC177, WeaponName.ASTRA40, WeaponName.BH4, WeaponName.DEFTECH, WeaponName.DLS12, WeaponName.E22, WeaponName.GL77, WeaponName.MODEL_57, WeaponName.SATINES_LAMENT, WeaponName.SX21,
                 WeaponName.V13, WeaponName.VERPINE_SHATTER_RIFLE, WeaponName.WESTAR_35 ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/vertical_angle_brackets_crosshair.png");
            default ->
                    ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/cross_crosshair.png");
        };
    }

    public static ResourceLocation getScopeTexture(ProjectileItem blasterItem, ItemStack blasterStack) {
        WeaponName blasterName = blasterItem.getProjectileWeaponName();
        FiringMode blasterFireMode = blasterItem.getFiringMode(blasterStack);

        if (blasterFireMode.equals(FiringMode.STUN)) {
            return null;
        }

        switch (blasterName) {
            case WeaponName.A280CFE:
                if (!blasterFireMode.equals(FiringMode.SEMI_AUTO)) {
                    return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_green_2_scope.png");
                } else {
                    return null;
                }
            case WeaponName.RELBY_V10:
                if (!blasterFireMode.equals(FiringMode.LAUNCHER)) {
                    return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_green_reactor_scope.png");
                } else {
                    return null;
                }
            case WeaponName.DC17M:
                if (blasterFireMode.equals(FiringMode.SNIPER) || blasterFireMode.equals(FiringMode.CHARGENSHOOT)) {
                    return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_grey_blue_scope.png");
                } else {
                    return null;
                }
            case WeaponName.DL44:
                if (blasterFireMode.equals(FiringMode.SNIPER) ) {
                    return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_grey_cross_scope.png");
                } else {
                    return null;
                }
            case WeaponName.A180:
                if (blasterFireMode.equals(FiringMode.FULL_AUTO)) {
                    return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/oval_small_soft_focus_scope.png");
                } else if (blasterFireMode.equals(FiringMode.SNIPER)) {
                    return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/oval_small_x_scope.png");
                } else {
                    return null;
                }
            case WeaponName._773_FIREPUNCHER, WeaponName.AVARIK, WeaponName.BALNAB:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_blue_cross_scope.png");
            case WeaponName.BOWCASTER, WeaponName.DEFTECH:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_blue_cross_2_scope.png");
            case WeaponName.GALAR90, WeaponName.WESTARE9:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_blue_cross_3_scope.png");
            case WeaponName.E11_RIFLE, WeaponName.E11B, WeaponName.M45:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_blue_scope.png");
            case WeaponName.DLT20A:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_blue_bracket_scope.png");
            case WeaponName.E10R:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_green_reactor_scope.png");
            case WeaponName.CJ9_BO_RIFLE, WeaponName.PK23:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_green_scope.png");
            case WeaponName.DH447, WeaponName.KA74:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_green_2_scope.png");
            case WeaponName.E5S:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_green_yellow_scope.png");
            case WeaponName.X8_NIGHT_SNIPER:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_green_yellow_scope.png");
            case WeaponName.OK98, WeaponName.VALKEN38X:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_grey_blue_scope.png");
            case WeaponName.BE29, WeaponName.CORPO_RIFLE, WeaponName.DG29, WeaponName.HF94, WeaponName.NEO_CRUSADER_RIFLE:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_grey_dot_scope.png");
            case WeaponName.CYCLER_RIFLE, WeaponName.JEZALI_CYCLER_RIFLE, WeaponName.OUTLAND_RIFLE:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_grey_cross_scope.png");
            case WeaponName.BOILER_RIFLE, WeaponName.DE10, WeaponName.EE3:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_grey_tri_dash_scope.png");
            case WeaponName._84U_HUNTING_RIFLE, WeaponName.WEEQUAY_RIFLE:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_grey_scope.png");
            case WeaponName.JND41:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_gold_v_scope.png");
            case WeaponName.KISTEER_1284:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_red_bracket_scope_thermal.png");
            case WeaponName.E11S, WeaponName.E9V:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_red_dot_scope.png");
            case WeaponName._62AUG2_HUNTING_RIFLE, WeaponName.CZERKA_ADVENTURER, WeaponName.WEEQUAY_LANCE:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/circle_yellow_scope.png");
            case WeaponName.A280, WeaponName.A280C, WeaponName.A295:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/classic_scope.png");
            case WeaponName.DH17, WeaponName.E17D, WeaponName.RT97C:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/classic_bubbled_scope.png");
            case WeaponName.DLT19X, WeaponName.NT242:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/classic_bubbled_green_scope.png");
            case WeaponName.GE36:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/octagonal_red_scope.png");
            case WeaponName.IQA11:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/oval_long_orange_scope.png");
            case WeaponName.AMBAN_DISRUPTOR:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/oval_long_red_blue_scope.png");
            case WeaponName._785MK_FIREPUNCHERX:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/oval_long_red_scope.png");
            case WeaponName.NIGHT_STINGER:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/oval_long_red_scope_thermal.png");
            case WeaponName.T21B:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/oval_long_red_arrow_scope.png");
            case WeaponName.DC15X:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/oval_small_fancy_scope.png");
            case WeaponName.A350:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/oval_small_indent_green_scope.png");
            case WeaponName.A300, WeaponName.A310:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/oval_small_soft_focus_scope.png");
            case WeaponName.DC15A, WeaponName.DC15LE, WeaponName.WESTARM5:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/oval_small_x_scope.png");
            case WeaponName.BM107, WeaponName.DLT19D:
                return ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "textures/gui/oval_small_vert_dot_scope.png");
            default:
                return null;
        }
    }
}
