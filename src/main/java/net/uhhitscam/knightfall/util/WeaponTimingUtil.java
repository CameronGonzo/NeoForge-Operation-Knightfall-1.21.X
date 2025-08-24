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
            case WeaponName.LIGHTBOW, WeaponName.VULK_TAU623_ROTARY, WeaponName.Z6_ROTARY, WeaponName.EWEB, /*BlasterName.EWHB12,*/ WeaponName.M32, WeaponName.M45, WeaponName.M55, WeaponName.M61, WeaponName.MWC35C, WeaponName.T21, WeaponName.T21B,
                 WeaponName.BM107, WeaponName.GALAR90, WeaponName.NT242, WeaponName.GE36, WeaponName.NEO_CRUSADER_RIFLE, WeaponName.BOILER_RIFLE, WeaponName.BOWCASTER, WeaponName.T7_ION_DISRUPTOR:
                return 25;
            case WeaponName.A140, WeaponName.A180, WeaponName.AC177, WeaponName.APACHE, WeaponName.ASTRA40, WeaponName.B22, WeaponName.BE09, WeaponName.BH4, WeaponName.BLURRG1120,
                 WeaponName.BOONTA_BLASTER, WeaponName.BR14, WeaponName.C10, WeaponName.C96, WeaponName.CAIJ_VANDAS_BLASTER_PISTOL, WeaponName.CC420, WeaponName.CR2, WeaponName.CS14, WeaponName.DC15S_SIDEARM,
                 WeaponName.DC17, WeaponName.DE10, WeaponName.DG29, WeaponName.DH16, WeaponName.DH17, WeaponName.DH23, WeaponName.DL18, WeaponName.DL21, WeaponName.DT12, WeaponName.DT15, WeaponName.DX13,
                 WeaponName.EC17, WeaponName.ELG3A, WeaponName.FLINTLOQ_PISTOL, WeaponName.FN57, WeaponName.FP45, WeaponName.GL77, WeaponName.HF94, WeaponName.IB94, WeaponName.K16_BRYAR_PISTOL, WeaponName.KOCH9S,
                 WeaponName.KRIE4, WeaponName.KUEGET_LN21, WeaponName.KYD21, WeaponName.LEUCHT42, WeaponName.LL30, WeaponName.LUG_PO8, WeaponName.LW896, WeaponName.M19A1, WeaponName.MARG_MCM, WeaponName.MODEL_57,
                 WeaponName.NAMBU14, WeaponName.P38, WeaponName.PCC_PROJECTOR, WeaponName.PREMIER, WeaponName.Q2, WeaponName.RELBY_K23, WeaponName.RENEGADE, WeaponName.RG4D, WeaponName.RIG420, WeaponName.RK3,
                 WeaponName.RSKF44, WeaponName.RUGER_BLASTER, WeaponName.S195, WeaponName.S5, WeaponName.SACROS_K11, WeaponName.SATINES_LAMENT, WeaponName.SE14C, WeaponName.SEREXIM_MK_5,
                 WeaponName.SEDGLEYS_MK_5, WeaponName.SHARD3A, WeaponName.SK32, WeaponName.SNUBBLE, WeaponName.STEYR43, WeaponName.T6, WeaponName.TCA_PRO, WeaponName.TYPE14, WeaponName.UMBARAN_PISTOL,
                 WeaponName.WALTHER_BLASTER, WeaponName.WALTHER_LPM_BLASTER, WeaponName.WEBLY_S4, WeaponName.WEBTEMP, WeaponName.WEEQUAY_PISTOL, WeaponName.WESTAR_20,
                 WeaponName.X8_NIGHT_SNIPER, WeaponName.X30, WeaponName.PANIC_PISTOL, WeaponName.VERPINE_SHATTER:
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
            case WeaponName.BM107 -> 30;
            case WeaponName.BOWCASTER, WeaponName.C10 -> 44;
            case WeaponName.BRYAR_RIFLE, WeaponName.POWER_5 -> 32;
            case WeaponName.CAIJ_VANDAS_BLASTER_PISTOL -> 4;
            case WeaponName.DC15X, WeaponName.MW20_BRYAR_PISTOL -> 27;
            case WeaponName.DC17M, WeaponName.TL50 -> 29;
            case WeaponName.DN_BOLT_CASTER -> 19;
            case WeaponName.JND41 -> 36;
            case WeaponName.NIGHT_STINGER, WeaponName.NEO_CRUSADER_RIFLE -> 35;
            case WeaponName.RELBY_V10, WeaponName.SK32 -> 17;
            case WeaponName.SATINES_LAMENT -> 25;
            case WeaponName.SHADOW_TROOPER_BLASTER -> 38;
            case WeaponName.T7_ION_DISRUPTOR -> 67;
            case WeaponName.Z6_ROTARY -> 52;
            default -> 33;
        };
    }
}