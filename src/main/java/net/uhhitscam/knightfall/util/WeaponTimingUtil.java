package net.uhhitscam.knightfall.util;

import net.uhhitscam.knightfall.item.custom.WeaponName;
import net.uhhitscam.knightfall.item.custom.FiringMode;

public class WeaponTimingUtil {
    public static long getProjectileWeaponReloadTime(WeaponName projectileWeaponName, FiringMode projectileWeaponFireMode) {
        switch (projectileWeaponName) {
            case WeaponName.AMBAN_DISRUPTOR:
                return 20;
            case WeaponName.DC17M:
                if (projectileWeaponFireMode.equals(FiringMode.LAUNCHER)) {
                    return 18;
                } else {
                    return 19;
                }
            case WeaponName.DT29:
                return 100;
            case WeaponName.RELBY_V10:
                if (projectileWeaponFireMode.equals(FiringMode.LAUNCHER)) {
                    return 18;
                } else {
                    return 16;
                }
            case /*BlasterName.V6D_MORTAR_LAUNCHER, BlasterName.MORTAR,*/ WeaponName.K21C_PORTABLE_ORDANANCE_LAUNCHER:
                return 18;
            case WeaponName.ABR2_ZATO, WeaponName.ACP_ARRAY, WeaponName.BX49, WeaponName.DFQ91, WeaponName.DX2, WeaponName.DXR6, WeaponName.LIGHTBOW, WeaponName.LS150, WeaponName.VULK_TAU623_ROTARY, WeaponName.Z6_ROTARY, WeaponName.EWEB, /*BlasterName.EWHB12,*/ WeaponName.M32, WeaponName.M45, WeaponName.M55, WeaponName.M61, WeaponName.MWC35C, WeaponName.T21, WeaponName.T21B,
                 WeaponName.BM107, WeaponName.GALAR90, WeaponName.NT242, WeaponName.GE36, WeaponName.NEO_CRUSADER_RIFLE, WeaponName.PRECISIONX, WeaponName.BOILER_RIFLE, WeaponName.BOWCASTER, WeaponName.T7_ION_DISRUPTOR:
                return 25;
            case WeaponName._22T4, WeaponName.A140, WeaponName.A180, WeaponName.A240, WeaponName.AC177, WeaponName.APACHE, WeaponName.ASTRA40, WeaponName.B22, WeaponName.B33, WeaponName.BALNAB_SIDEARM, WeaponName.BE09, WeaponName.BH4, WeaponName.BK28, WeaponName.BLURRG1120,
                 WeaponName.BOONTA_BLASTER, WeaponName.BR14, WeaponName.BT06, WeaponName.C10, WeaponName.CORE_J3, WeaponName.CORE_R5, WeaponName.CORE_U12, WeaponName.HT9, WeaponName.LV7C, WeaponName.CC420, WeaponName.CR2, WeaponName.CS14, WeaponName.DC15S_SIDEARM,
                 WeaponName.DC17, WeaponName.DE10, WeaponName.DEACTIVATOR, WeaponName.DER4, WeaponName.DG29, WeaponName.DH16, WeaponName.DH17, WeaponName.DH23, WeaponName.DH42, WeaponName.DL11, WeaponName.DL18, WeaponName.DL21, WeaponName.DT12, WeaponName.DT15, WeaponName.DUJ3, WeaponName.DX13,
                 WeaponName.E11P, WeaponName.EC17, WeaponName.EL5, WeaponName.ELG3A, WeaponName.F2L, WeaponName.FN57, WeaponName.FP45, WeaponName.GA3R, WeaponName.GL77, WeaponName.GM46, WeaponName.GR4_ST, WeaponName.GRN4, WeaponName.HF94, WeaponName.IB94, WeaponName.ION_STUNNER, WeaponName.K13, WeaponName.K16_BRYAR_PISTOL, WeaponName.KL9,
                 WeaponName.SE9V, WeaponName.KM9, WeaponName.KUEGET_LN21, WeaponName.KYD21, WeaponName.CW24, WeaponName.LL30, WeaponName.MSD32, WeaponName.RK2P, WeaponName.LW896, WeaponName.CC19, WeaponName.MARG_MCM, WeaponName.MODEL_57,
                 WeaponName.F38G, WeaponName.EMG2, WeaponName.RM7,  WeaponName.PR9, WeaponName.Q2, WeaponName.RELBY_K23, WeaponName.RENEGADE, WeaponName.RG4D, WeaponName.RIG420, WeaponName.RK3,
                 WeaponName.RM_1P, WeaponName.RSKF44, WeaponName.S2S, WeaponName.RLR_MK_II, WeaponName.S195, WeaponName.S5, WeaponName.SACROS_K11, WeaponName.SATINES_LAMENT, WeaponName.SE14C, WeaponName.S3_MK_5,
                 WeaponName.SEDGLEYS_MK_5, WeaponName.SETTLERS_STUN, WeaponName.SHARD3A, WeaponName.SK32, WeaponName.SNUB_BLASTER, WeaponName.SNUBBLE, WeaponName.SONIC_BLASTER, WeaponName.SONIC_STUNNER, WeaponName.SS410, WeaponName.CW76, WeaponName.T6, WeaponName.TG446, WeaponName.UMBARAN_BLASTER, WeaponName.UTK3, WeaponName.WESTAR2L,
                 WeaponName.W50S, WeaponName.W310, WeaponName.W340LM, WeaponName.WS4, WeaponName.P224, WeaponName.WEEQUAY_PISTOL, WeaponName.WESTAR_20, WeaponName.WESTAR_33, WeaponName.WOOKIE_SIDEARM,
                 WeaponName.X8_NIGHT_SNIPER, WeaponName.X30, WeaponName.PANIC_PISTOL, WeaponName.VERPINE_SIDEARM, WeaponName.VILMARHS_REVENGE, WeaponName.VM19, WeaponName.VT20:
                return 15;
            default:
                return 19;
        }
    }

    public static long getProjectileWeaponSwitchTime(WeaponName projectileWeaponName, FiringMode projectileWeaponFireMode) {
        switch (projectileWeaponName) {
            case WeaponName.A180:
                if (projectileWeaponFireMode.equals(FiringMode.SEMI_AUTO)) {
                    return 18;
                } else if (projectileWeaponFireMode.equals(FiringMode.FULL_AUTO)) {
                    return 13;
                } else {
                    return 17;
                }
            case WeaponName.A280CFE:
                if (projectileWeaponFireMode.equals(FiringMode.SEMI_AUTO)) {
                    return 18;
                } else if (projectileWeaponFireMode.equals(FiringMode.BURST)) {
                    return 25;
                } else {
                    return 19;
                }
            case WeaponName.AMBAN_DISRUPTOR:
                return 18;
            case WeaponName.B1NA:
                return 12;
            case WeaponName.DC17M:
                if (projectileWeaponFireMode.equals(FiringMode.SEMI_AUTO)) {
                    return 15;
                } else if (projectileWeaponFireMode.equals(FiringMode.LAUNCHER)) {
                    return 45;
                } else {
                    return 22;
                }
            case WeaponName.DL44:
                return 29;
            case WeaponName.MW20_BRYAR_PISTOL:
                return 16;
            default:
                return 4;
        }
    }

    public static long getProjectileWeaponChargeThreshold(WeaponName projectileWeaponName) {
        return switch (projectileWeaponName) {
            case WeaponName.LV7C -> 4;
            case WeaponName.GM46 -> 15;
            case WeaponName.RELBY_V10, WeaponName.SK32 -> 17;
            case WeaponName.DN_BOLT_CASTER -> 19;
            case WeaponName.BK28 -> 20;
            case WeaponName.WOOKIE_RIFLE -> 23;
            case WeaponName.WOOKIE_SIDEARM -> 25;
            case WeaponName.DX2, WeaponName.SATINES_LAMENT, WeaponName.ZYGERRIAN_BLASTER -> 26;
            case WeaponName.DC15X, WeaponName.MW20_BRYAR_PISTOL -> 27;
            case WeaponName.DC17M, WeaponName.TL50 -> 29;
            case WeaponName.BM107 -> 30;
            case WeaponName.BRYAR_RIFLE, WeaponName.POWER_5, WeaponName.C10 -> 32;
            case WeaponName.NIGHT_STINGER, WeaponName.NEO_CRUSADER_RIFLE -> 35;
            case WeaponName.JND41 -> 36;
            case WeaponName.E9V -> 38;
            case WeaponName.BOWCASTER -> 44;
            case WeaponName.RELBY_K25 -> 47;
            case WeaponName.CHARRIC -> 49;
            case WeaponName.Z6_ROTARY -> 52;
            case WeaponName.DXR6 -> 60;
            case WeaponName.T7_ION_DISRUPTOR -> 67;
            case WeaponName.MSD32 -> 82;
            default -> 33;
        };
    }
}