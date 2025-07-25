package net.uhhitscam.starwars.util;

import net.uhhitscam.starwars.item.custom.BlasterName;
import net.uhhitscam.starwars.item.custom.FiringMode;

public class BlasterTimingUtil {
    public static long getBlasterReloadTime(BlasterName blasterName, FiringMode blasterFireMode) {
        switch (blasterName) {
            case BlasterName.AMBAN_DISRUPTOR:
                return 20;
            case BlasterName.DC17M:
                if (blasterFireMode.equals(FiringMode.LAUNCHER)) {
                    return 18;
                } else {
                    return 19;
                }
            case BlasterName.DT29:
                return 100;
            case BlasterName.RELBY_V10:
                if (blasterFireMode.equals(FiringMode.LAUNCHER)) {
                    return 18;
                } else {
                    return 16;
                }
            case /*BlasterName.V6D_MORTAR_LAUNCHER, BlasterName.MORTAR,*/ BlasterName.K21C_PORTABLE_ORDANANCE_LAUNCHER:
                return 18;
            case BlasterName.LIGHTBOW, BlasterName.VULK_TAU623_ROTARY, BlasterName.Z6_ROTARY, BlasterName.EWEB, /*BlasterName.EWHB12,*/ BlasterName.M32, BlasterName.M45, BlasterName.M55, BlasterName.M61, BlasterName.MWC35C, BlasterName.T21, BlasterName.T21B,
                 BlasterName.BM107, BlasterName.GALAR90, BlasterName.NT242, BlasterName.GE36, BlasterName.NEO_CRUSADER_RIFLE, BlasterName.BOILER_RIFLE, BlasterName.BOWCASTER, BlasterName.T7_ION_DISRUPTOR:
                return 25;
            case BlasterName.A140, BlasterName.A180, BlasterName.AC177, BlasterName.APACHE, BlasterName.ASTRA40, BlasterName.B22, BlasterName.BE09, BlasterName.BH4, BlasterName.BLURRG1120,
                 BlasterName.BOONTA_BLASTER, BlasterName.BR14, BlasterName.C10, BlasterName.C96, BlasterName.CAIJ_VANDAS_BLASTER_PISTOL, BlasterName.CC420, BlasterName.CR2, BlasterName.CS14, BlasterName.DC15S_SIDEARM,
                 BlasterName.DC17, BlasterName.DE10, BlasterName.DG29, BlasterName.DH16, BlasterName.DH17, BlasterName.DH23, BlasterName.DL18, BlasterName.DL21, BlasterName.DT12, BlasterName.DT15, BlasterName.DX13,
                 BlasterName.EC17, BlasterName.ELG3A, BlasterName.FLINTLOQ_PISTOL, BlasterName.FN57, BlasterName.FP45, BlasterName.GL77, BlasterName.HF94, BlasterName.IB94, BlasterName.K16_BRYAR_PISTOL, BlasterName.KOCH9S,
                 BlasterName.KRIE4, BlasterName.KUEGET_LN21, BlasterName.KYD21, BlasterName.LEUCHT42, BlasterName.LL30, BlasterName.LUG_PO8, BlasterName.LW896, BlasterName.M19A1, BlasterName.MARG_MCM, BlasterName.MODEL_57,
                 BlasterName.NAMBU14, BlasterName.P38, BlasterName.PCC_PROJECTOR, BlasterName.PREMIER, BlasterName.Q2, BlasterName.RELBY_K23, BlasterName.RENEGADE, BlasterName.RG4D, BlasterName.RIG420, BlasterName.RK3,
                 BlasterName.RSKF44, BlasterName.RUGER_BLASTER, BlasterName.S195, BlasterName.S5, BlasterName.SACROS_K11, BlasterName.SATINES_LAMENT, BlasterName.SE14C, BlasterName.SEREXIM_MK_5,
                 BlasterName.SEDGLEYS_MK_5, BlasterName.SHARD3A, BlasterName.SK32, BlasterName.SNUBBLE, BlasterName.STEYR43, BlasterName.T6, BlasterName.TCA_PRO, BlasterName.TYPE14, BlasterName.UMBARAN_PISTOL,
                 BlasterName.WALTHER_BLASTER, BlasterName.WALTHER_LPM_BLASTER, BlasterName.WEBLY_S4, BlasterName.WEBTEMP, BlasterName.WEEQUAY_PISTOL, BlasterName.WESTAR_20,
                 BlasterName.X8_NIGHT_SNIPER, BlasterName.X30, BlasterName.PANIC_PISTOL, BlasterName.VERPINE_SHATTER:
                return 15;
            default:
                return 19;
        }
    }

    public static long getBlasterSwitchTime(BlasterName blasterName, FiringMode blasterFireMode) {
        switch (blasterName) {
            case BlasterName.A180:
                if (blasterFireMode.equals(FiringMode.SEMI_AUTO)) {
                    return 18;
                } else if (blasterFireMode.equals(FiringMode.FULL_AUTO)) {
                    return 13;
                } else {
                    return 17;
                }
            case BlasterName.A280CFE:
                if (blasterFireMode.equals(FiringMode.SEMI_AUTO)) {
                    return 18;
                } else if (blasterFireMode.equals(FiringMode.BURST)) {
                    return 25;
                } else {
                    return 19;
                }
            case BlasterName.AMBAN_DISRUPTOR:
                return 18;
            case BlasterName.B1NA:
                return 12;
            case BlasterName.DC17M:
                if (blasterFireMode.equals(FiringMode.SEMI_AUTO)) {
                    return 15;
                } else if (blasterFireMode.equals(FiringMode.LAUNCHER)) {
                    return 45;
                } else {
                    return 22;
                }
            case BlasterName.DL44:
                return 29;
            case BlasterName.MW20_BRYAR_PISTOL:
                return 16;
            default:
                return 4;
        }
    }
}