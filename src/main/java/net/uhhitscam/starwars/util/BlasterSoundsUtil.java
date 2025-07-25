package net.uhhitscam.starwars.util;

import net.minecraft.sounds.SoundEvent;
import net.uhhitscam.starwars.item.custom.BlasterName;
import net.uhhitscam.starwars.item.custom.FiringMode;
import net.uhhitscam.starwars.sound.ModSounds;

import java.util.Random;

public class BlasterSoundsUtil {
    public static SoundEvent getBlasterFireSound(BlasterName blasterName, FiringMode blasterFireMode) {
        if (blasterFireMode.equals(FiringMode.STUN) && !blasterName.equals(BlasterName.CA87)) {
            if (blasterName.equals(BlasterName.EC17)) {
                return ModSounds.EC17_STUN_FIRE.get();
            } else {
                return ModSounds.STUN_FIRE.get();
            }
        } else if (blasterName.equals(BlasterName.A180)) {
            return switch (blasterFireMode) {
                case FiringMode.FULL_AUTO -> ModSounds.A180_RIFLE_FIRE.get();
                case FiringMode.SNIPER -> ModSounds.A180_SNIPER_FIRE.get();
                default -> ModSounds.A180_PISTOL_FIRE.get();
            };
        } else if (blasterName.equals(BlasterName.A280CFE)) {
            return switch (blasterFireMode) {
                case FiringMode.BURST -> ModSounds.A280CFE_RIFLE_FIRE.get();
                case FiringMode.SNIPER -> ModSounds.A280CFE_SNIPER_FIRE.get();
                default -> ModSounds.A280CFE_PISTOL_FIRE.get();
            };
        } else if (blasterName.equals(BlasterName.BLNDRBUS)) {
            if (blasterFireMode.equals(FiringMode.REPULSE)) {
                return ModSounds.BLNDRBUS_REPULSE_FIRE.get();
            } else {
                return ModSounds.BLNDRBUS_FIRE.get();
            }
        } else if (blasterName.equals(BlasterName.BOWCASTER)) {
            if (blasterFireMode.equals(FiringMode.CHARGED)) {
                return ModSounds.BOWCASTER_CHARGED_FIRE.get();
            } else {
                return ModSounds.BOWCASTER_FIRE.get();
            }
        } else if (blasterName.equals(BlasterName.BRYAR_RIFLE)) {
            if (blasterFireMode.equals(FiringMode.CHARGED)) {
                return ModSounds.BRYAR_RIFLE_CHARGED_FIRE.get();
            } else {
                return ModSounds.BRYAR_RIFLE_FIRE.get();
            }
        } else if (blasterName.equals(BlasterName.CA87)) {
            return switch (blasterFireMode) {
                case FiringMode.REPULSE -> ModSounds.CA87_REPULSE_FIRE.get();
                case FiringMode.STUN -> ModSounds.CA87_STUN_FIRE.get();
                default -> ModSounds.CA87_FIRE.get();
            };
        } else if (blasterName.equals(BlasterName.MOTTO_MK_4)) {
            if (blasterFireMode.equals(FiringMode.REPULSE)) {
                return ModSounds.MOTTO_MK_4_REPULSE_FIRE.get();
            } else {
                return ModSounds.MOTTO_MK_4_FIRE.get();
            }
        } else if (blasterName.equals(BlasterName.CAIJ_VANDAS_BLASTER_PISTOL)) {
            if (blasterFireMode.equals(FiringMode.CHARGED)) {
                return ModSounds.CAIJ_VANDAS_BLASTER_PISTOL_CHARGED_FIRE.get();
            } else {
                return ModSounds.CAIJ_VANDAS_BLASTER_PISTOL_FIRE.get();
            }
        } else if (blasterName.equals(BlasterName.DC17M)) {
            return switch (blasterFireMode) {
                case FiringMode.LAUNCHER -> ModSounds.DC17M_LAUNCHER_FIRE.get();
                case FiringMode.SNIPER -> ModSounds.DC17M_SNIPER_FIRE.get();
                default -> ModSounds.DC17M_FIRE.get();
            };
        } else if (blasterName.equals(BlasterName.DL44)) {
            if (blasterFireMode.equals(FiringMode.SNIPER)) {
                return ModSounds.DL44_SNIPER_FIRE.get();
            } else {
                return ModSounds.DL44_FIRE.get();
            }
        } else if (blasterName.equals(BlasterName.DLT20A)) {
            if (blasterFireMode.equals(FiringMode.SNIPER)) {
                return ModSounds.DLT20A_SNIPER_FIRE.get();
            } else {
                return ModSounds.DLT20A_FIRE.get();
            }
//        } else if (blasterName.equals(BlasterName.EWHB12)) {
//            if (blasterFireMode.equals(FiringMode.FULL_AUTO)) {
//                return ModSounds.EWHB12_AUTO_FIRE.get();
//            } else {
//                return ModSounds.EWHB12_FIRE.get();
//            }
        } else if (blasterName.equals(BlasterName.K16_BRYAR_PISTOL)) {
            if (blasterFireMode.equals(FiringMode.CHARGED)) {
                return ModSounds.K16_BRYAR_PISTOL_CHARGED_FIRE.get();
            } else {
                return ModSounds.K16_BRYAR_PISTOL_FIRE.get();
            }
        } else if (blasterName.equals(BlasterName.RELBY_V10)) {
            return switch (blasterFireMode) {
                case FiringMode.LAUNCHER -> ModSounds.RELBY_V10_LAUNCHER_FIRE.get();
                case FiringMode.CHARGED -> ModSounds.RELBY_V10_CHARGED_FIRE.get();
                default -> ModSounds.RELBY_V10_FIRE.get();
            };
        } else if (blasterName.equals(BlasterName.SHADOW_TROOPER_BLASTER)) {
            if (blasterFireMode.equals(FiringMode.CHARGED)) {
                return ModSounds.SHADOW_TROOPER_BLASTER_CHARGED_FIRE.get();
            } else {
                return ModSounds.SHADOW_TROOPER_BLASTER_FIRE.get();
            }
        } else if (blasterName.equals(BlasterName.TL50)) {
            if (blasterFireMode.equals(FiringMode.LAUNCHER)) {
                return ModSounds.TL50_LAUNCHER_FIRE.get();
            } else {
                return ModSounds.TL50_FIRE.get();
            }
        }

        return switch (blasterName) {
            case BlasterName._62AUG2_HUNTING_RIFLE -> ModSounds._62AUG2_HUNTING_RIFLE_FIRE.get();
            case BlasterName._84U_HUNTING_RIFLE -> ModSounds._84U_HUNTING_RIFLE_FIRE.get();
            case BlasterName._434_DEATHHAMMER -> ModSounds._434_DEATHHAMMER_FIRE.get();
            case BlasterName._773_FIREPUNCHER -> ModSounds._773_FIREPUNCHER_FIRE.get();
            case BlasterName._785MK_FIREPUNCHERX -> ModSounds._785MK_FIREPUNCHERX_FIRE.get();
            case BlasterName.A140 -> ModSounds.A140_FIRE.get();
            case BlasterName.A280 -> ModSounds.A280_FIRE.get();
            case BlasterName.A280C -> ModSounds.A280C_FIRE.get();
            case BlasterName.A295 -> ModSounds.A295_FIRE.get();
            case BlasterName.A300 -> ModSounds.A300_FIRE.get();
            case BlasterName.A310 -> ModSounds.A310_FIRE.get();
            case BlasterName.A350 -> ModSounds.A350_FIRE.get();
            case BlasterName.AC177 -> ModSounds.AC177_FIRE.get();
            case BlasterName.ACP_REPEATER -> ModSounds.ACP_REPEATER_FIRE.get();
            case BlasterName.AKBARC -> ModSounds.AKBARC_FIRE.get();
            case BlasterName.AMBAN_DISRUPTOR -> ModSounds.AMBAN_DISRUPTOR_FIRE.get();
            case BlasterName.APACHE -> ModSounds.APACHE_FIRE.get();
            case BlasterName.ASTRA40 -> ModSounds.ASTRA40_FIRE.get();
            case BlasterName.AVARIK -> ModSounds.AVARIK_FIRE.get();
            case BlasterName.B1NA -> ModSounds.B1NA_FIRE.get();
            case BlasterName.B22 -> ModSounds.B22_FIRE.get();
            case BlasterName.BALNAB -> ModSounds.BALNAB_FIRE.get();
            case BlasterName.BARMST12 -> ModSounds.BARMST12_FIRE.get();
            case BlasterName.BATON_BLASTER -> ModSounds.BATON_BLASTER_FIRE.get();
            case BlasterName.BE09 -> ModSounds.BE09_FIRE.get();
            case BlasterName.BE29 -> ModSounds.BE29_FIRE.get();
            case BlasterName.BERSERKER -> ModSounds.BERSERKER_FIRE.get();
            case BlasterName.BH4 -> ModSounds.BH4_FIRE.get();
            case BlasterName.BLASTER_SPEAR -> ModSounds.BLASTER_SPEAR_FIRE.get();
            case BlasterName.BLURRG1120 -> ModSounds.BLURRG1120_FIRE.get();
            case BlasterName.BM107 -> ModSounds.BM107_FIRE.get();
            case BlasterName.BOILER_RIFLE -> ModSounds.BOILER_RIFLE_FIRE.get();
            case BlasterName.BOONTA_BLASTER -> ModSounds.BOONTA_BLASTER_FIRE.get();
            case BlasterName.BR14 -> ModSounds.BR14_FIRE.get();
//            case BlasterName.BT_X42_FLAMETHROWER -> ModSounds.BT_X42_FLAMETHROWER_FIRE.get();
            case BlasterName.C10 -> ModSounds.C10_FIRE.get();
            case BlasterName.C96 -> ModSounds.C96_FIRE.get();
            case BlasterName.CC420 -> ModSounds.CC420_FIRE.get();
            case BlasterName.CJ9_BO_RIFLE -> ModSounds.CJ9_BO_RIFLE_FIRE.get();
            case BlasterName.CORPO_RIFLE -> ModSounds.CORPO_RIFLE_FIRE.get();
            case BlasterName.CR2 -> ModSounds.CR2_FIRE.get();
            case BlasterName.CS14 -> ModSounds.CS14_FIRE.get();
            case BlasterName.CYCLER_RIFLE -> ModSounds.CYCLER_RIFLE_FIRE.get();
            case BlasterName.CZERKA_ADVENTURER -> ModSounds.CZERKA_ADVENTURER_FIRE.get();
            case BlasterName.DARK_TROOPER_RIFLE -> ModSounds.DARK_TROOPER_RIFLE_FIRE.get();
            case BlasterName.DC12U -> ModSounds.DC12U_FIRE.get();
            case BlasterName.DC15A -> ModSounds.DC15A_FIRE.get();
            case BlasterName.DC15LE -> ModSounds.DC15LE_FIRE.get();
            case BlasterName.DC15S_CARBINE -> ModSounds.DC15S_CARBINE_FIRE.get();
            case BlasterName.DC15S_SIDEARM -> ModSounds.DC15S_SIDEARM_FIRE.get();
            case BlasterName.DC15X -> ModSounds.DC15X_FIRE.get();
            case BlasterName.DC17 -> ModSounds.DC17_FIRE.get();
            case BlasterName.DC19 -> ModSounds.DC19_FIRE.get();
            case BlasterName.DE10 -> ModSounds.DE10_FIRE.get();
            case BlasterName.DEFTECH -> ModSounds.DEFTECH_FIRE.get();
            case BlasterName.DFD1 -> ModSounds.DFD1_FIRE.get();
            case BlasterName.DG29 -> ModSounds.DG29_FIRE.get();
            case BlasterName.DH16 -> ModSounds.DH16_FIRE.get();
            case BlasterName.DH17 -> ModSounds.DH17_FIRE.get();
            case BlasterName.DH23 -> ModSounds.DH23_FIRE.get();
            case BlasterName.DH447 -> ModSounds.DH447_FIRE.get();
            case BlasterName.DL18 -> ModSounds.DL18_FIRE.get();
            case BlasterName.DL21 -> ModSounds.DL21_FIRE.get();
            case BlasterName.DLS12 -> ModSounds.DLS12_FIRE.get();
            case BlasterName.DLT18 -> ModSounds.DLT18_FIRE.get();
            case BlasterName.DLT19 -> ModSounds.DLT19_FIRE.get();
            case BlasterName.DLT19D -> ModSounds.DLT19D_FIRE.get();
            case BlasterName.DLT19X -> ModSounds.DLT19X_FIRE.get();
            case BlasterName.DN_BOLT_CASTER -> ModSounds.DN_BOLT_CASTER_FIRE.get();
            case BlasterName.DP23 -> ModSounds.DP23_FIRE.get();
            case BlasterName.DRESSELLIAN_PROJECTILE_RIFLE -> ModSounds.DRESSELLIAN_PROJECTILE_RIFLE_FIRE.get();
            case BlasterName.DT12 -> ModSounds.DT12_FIRE.get();
            case BlasterName.DT15 -> ModSounds.DT15_FIRE.get();
            case BlasterName.DT29 -> ModSounds.DT29_FIRE.get();
            case BlasterName.DT57 -> ModSounds.DT57_FIRE.get();
            case BlasterName.DX13 -> ModSounds.DX13_FIRE.get();
            case BlasterName.E5 -> ModSounds.E5_FIRE.get();
            case BlasterName.E5_BX -> ModSounds.E5_BX_FIRE.get();
            case BlasterName.E5_CE -> ModSounds.E5_CE_FIRE.get();
            case BlasterName.E5C -> ModSounds.E5C_FIRE.get();
            case BlasterName.E5S -> ModSounds.E5S_FIRE.get();
            case BlasterName.E10 -> ModSounds.E10_FIRE.get();
            case BlasterName.E10_5 -> ModSounds.E10_5_FIRE.get();
            case BlasterName.E10R -> ModSounds.E10R_FIRE.get();
            case BlasterName.E11_CARBINE -> ModSounds.E11_CARBINE_FIRE.get();
            case BlasterName.E11_RIFLE -> ModSounds.E11_RIFLE_FIRE.get();
            case BlasterName.E11B -> ModSounds.E11B_FIRE.get();
            case BlasterName.E11D -> ModSounds.E11D_FIRE.get();
            case BlasterName.E11S -> ModSounds.E11S_FIRE.get();
            case BlasterName.E17D -> ModSounds.E17D_FIRE.get();
            case BlasterName.E22 -> ModSounds.E22_FIRE.get();
            case BlasterName.EC17 -> ModSounds.EC17_FIRE.get();
            case BlasterName.EE3 -> ModSounds.EE3_FIRE.get();
            case BlasterName.EE4 -> ModSounds.EE4_FIRE.get();
            case BlasterName.ELG3A -> ModSounds.ELG3A_FIRE.get();
            case BlasterName.ENERGY_BOW -> ModSounds.ENERGY_BOW_FIRE.get();
            case BlasterName.ENERGY_CROSSBOW -> ModSounds.ENERGY_CROSSBOW_FIRE.get();
            case BlasterName.EWEB -> ModSounds.EWEB_FIRE.get();
            case BlasterName.FC1_FLECHETTE_LAUNCHER -> ModSounds.FC1_FLECHETTE_LAUNCHER_FIRE.get();
            case BlasterName.FLINTLOQ_PISTOL -> ModSounds.FLINTLOQ_PISTOL_FIRE.get();
            case BlasterName.FLINTLOQ_RIFLE -> ModSounds.FLINTLOQ_RIFLE_FIRE.get();
            case BlasterName.FLITE37 -> ModSounds.FLITE37_FIRE.get();
            case BlasterName.FN57 -> ModSounds.FN57_FIRE.get();
            case BlasterName.FP45 -> ModSounds.FP45_FIRE.get();
            case BlasterName.GALAAR15 -> ModSounds.GALAAR15_FIRE.get();
            case BlasterName.GALAR90 -> ModSounds.GALAR90_FIRE.get();
            case BlasterName.GE36 -> ModSounds.GE36_FIRE.get();
            case BlasterName.GL77 -> ModSounds.GL77_FIRE.get();
            case BlasterName.HF94 -> ModSounds.HF94_FIRE.get();
            case BlasterName.IB94 -> ModSounds.IB94_FIRE.get();
            case BlasterName.IMPERIAL_SUPERCOMMANDO_BLASTER -> ModSounds.IMPERIAL_SUPERCOMMANDO_BLASTER_FIRE.get();
            case BlasterName.IQA11 -> ModSounds.IQA11_FIRE.get();
            case BlasterName.JEZALI_CYCLER_RIFLE -> ModSounds.JEZALI_CYCLER_RIFLE_FIRE.get();
            case BlasterName.JND41 -> ModSounds.JND41_FIRE.get();
            case BlasterName.K21C_PORTABLE_ORDANANCE_LAUNCHER -> ModSounds.K21C_PORTABLE_ORDANANCE_LAUNCHER_FIRE.get();
            case BlasterName.KA74 -> ModSounds.KA74_FIRE.get();
            case BlasterName.KISTEER_1284 -> ModSounds.KISTEER_1284_FIRE.get();
            case BlasterName.KOCH9S -> ModSounds.KOCH9S_FIRE.get();
            case BlasterName.KRIE4 -> ModSounds.KRIE4_FIRE.get();
            case BlasterName.KUEGET_LN21 -> ModSounds.KUEGET_LN21.get();
            case BlasterName.KYD21 -> ModSounds.KYD21_FIRE.get();
            case BlasterName.L5 -> ModSounds.L5_FIRE.get();
            case BlasterName.L60 -> ModSounds.L60_FIRE.get();
            case BlasterName.LEUCHT42 -> ModSounds.LEUCHT42_FIRE.get();
            case BlasterName.LIGHTBOW -> ModSounds.LIGHTBOW_FIRE.get();
            case BlasterName.LL30 -> ModSounds.LL30_FIRE.get();
            case BlasterName.LUG_PO8 -> ModSounds.LUG_PO8_FIRE.get();
            case BlasterName.LW896 -> ModSounds.LW896_FIRE.get();
            case BlasterName.M12 -> ModSounds.M12_FIRE.get();
            case BlasterName.M19A1 -> ModSounds.M19A1_FIRE.get();
            case BlasterName.M32 -> ModSounds.M32_FIRE.get();
            case BlasterName.M41 -> ModSounds.M41_FIRE.get();
            case BlasterName.M45 -> ModSounds.M45_FIRE.get();
            case BlasterName.M55 -> ModSounds.M55_FIRE.get();
            case BlasterName.M61 -> ModSounds.M61_FIRE.get();
            case BlasterName.MARG_MCM -> ModSounds.MARG_MCM_FIRE.get();
            case BlasterName.MINIMAG_PROTON_TORPEDO_LAUNCHER -> ModSounds.MINIMAG_PROTON_TORPEDO_LAUNCHER_FIRE.get();
            case BlasterName.MK_II_PALADIN -> ModSounds.MK_II_PALADIN_FIRE.get();
            case BlasterName.MODEL_57 -> ModSounds.MODEL_57_FIRE.get();
//            case BlasterName.MORTAR -> ModSounds.MORTAR_FIRE.get();
            case BlasterName.MW20_BRYAR_PISTOL -> ModSounds.MW20_BRYAR_PISTOL_FIRE.get();
            case BlasterName.MWC35C -> ModSounds.MWC35C_FIRE.get();
            case BlasterName.NAMBU14 -> ModSounds.NAMBU14_FIRE.get();
            case BlasterName.NEO_CRUSADER_RIFLE -> ModSounds.NEO_CRUSADER_RIFLE_FIRE.get();
            case BlasterName.NIGHT_STINGER -> ModSounds.NIGHT_STINGER_FIRE.get();
            case BlasterName.NIGHT_WIND_RIFLE -> ModSounds.NIGHT_WIND_RIFLE_FIRE.get();
            case BlasterName.NT242 -> ModSounds.NT242_FIRE.get();
            case BlasterName.OK98 -> ModSounds.OK98_FIRE.get();
//            case BlasterName.OPRESSOR_FLAMETHROWER -> ModSounds.OPRESSOR_FLAMETHROWER_FIRE.get();
            case BlasterName.OUTLAND_RIFLE -> ModSounds.OUTLAND_RIFLE_FIRE.get();
            case BlasterName.P38 -> ModSounds.P38_FIRE.get();
            case BlasterName.PANIC_PISTOL -> ModSounds.PANIC_PISTOL_FIRE.get();
            case BlasterName.PCC_PROJECTOR -> ModSounds.PCC_PROJECTOR_FIRE.get();
            case BlasterName.PK23 -> ModSounds.PK23_FIRE.get();
//            case BlasterName.PLX1_MISSLE_LAUNCHER -> ModSounds.PLX1_MISSLE_LAUNCHER_FIRE.get();
            case BlasterName.POWER_5 -> ModSounds.POWER_5_FIRE.get();
            case BlasterName.PREMIER -> ModSounds.PREMIER_FIRE.get();
            case BlasterName.Q2 -> ModSounds.Q2_FIRE.get();
            case BlasterName.QUARREN_RIFLE -> ModSounds.QUARREN_RIFLE_FIRE.get();
            case BlasterName.RELBY_K23 -> ModSounds.RELBY_K23_FIRE.get();
            case BlasterName.RENEGADE -> ModSounds.RENEGADE_FIRE.get();
            case BlasterName.RG4D -> ModSounds.RG4D_FIRE.get();
            case BlasterName.RIG420 -> ModSounds.RIG420_FIRE.get();
            case BlasterName.RK3 -> ModSounds.RK3_FIRE.get();
//            case BlasterName.RPS6_ROCKET_LAUNCHER -> ModSounds.RPS6_ROCKET_LAUNCHER_FIRE.get();
            case BlasterName.RSKF44 -> ModSounds.RSKF44_FIRE.get();
            case BlasterName.RT97C -> ModSounds.RT97C_FIRE.get();
            case BlasterName.RUGER_BLASTER-> ModSounds.RUGER_BLASTER_FIRE.get();
            case BlasterName.S5 -> ModSounds.S5_FIRE.get();
            case BlasterName.S195-> ModSounds.S195_FIRE.get();
            case BlasterName.SACROS_K11 -> ModSounds.SACROS_K11_FIRE.get();
            case BlasterName.SATINES_LAMENT -> ModSounds.SATINES_LAMENT_FIRE.get();
            case BlasterName.SE14C -> ModSounds.SE14C_FIRE.get();
            case BlasterName.SE14R -> ModSounds.SE14R_FIRE.get();
            case BlasterName.SEDGLEYS_MK_5 -> ModSounds.SEDGLEYS_MK_5_FIRE.get();
            case BlasterName.SEREXIM_MK_5 -> ModSounds.SEREXIM_MK_5_FIRE.get();
            case BlasterName.SHARD3A -> ModSounds.SHARD3A_FIRE.get();
            case BlasterName.SK32 -> ModSounds.SK32_FIRE.get();
//            case BlasterName.SMART_ROCKET -> ModSounds.SMART_ROCKET_FIRE.get();
            case BlasterName.SNUBBLE -> ModSounds.SNUBBLE_FIRE.get();
            case BlasterName.SONIC_BLASTER -> ModSounds.SONIC_BLASTER_FIRE.get();
            case BlasterName.STEYR43 -> ModSounds.STEYR43_FIRE.get();
            case BlasterName.SX21 -> ModSounds.SX21_FIRE.get();
            case BlasterName.T6 -> ModSounds.T6_FIRE.get();
            case BlasterName.T7_ION_DISRUPTOR -> ModSounds.T7_ION_DISRUPTOR_FIRE.get();
            case BlasterName.T21 -> ModSounds.T21_FIRE.get();
            case BlasterName.T21B -> ModSounds.T21B_FIRE.get();
            case BlasterName.TCA_PRO -> ModSounds.TCA_PRO_FIRE.get();
            case BlasterName.TOMSUN97 -> ModSounds.TOMSUN97_FIRE.get();
            case BlasterName.TYPE14 -> ModSounds.TYPE14_FIRE.get();
            case BlasterName.UMBARAN_PISTOL -> ModSounds.UMBARAN_PISTOL_FIRE.get();
//            case BlasterName.V6D_MORTAR_LAUNCHER -> ModSounds.V6D_MORTAR_LUANCHER_FIRE.get();
            case BlasterName.VALKEN38X -> ModSounds.VALKEN38X_FIRE.get();
            case BlasterName.VANGUARD_SCATTER -> ModSounds.VANGUARD_SCATTER_FIRE.get();
            case BlasterName.VECT_UZI -> ModSounds.VECT_UZI_FIRE.get();
            case BlasterName.VERPINE_SHATTER -> ModSounds.VERPINE_SHATTER_FIRE.get();
            case BlasterName.VULK_TAU623_ROTARY -> ModSounds.VULK_TAU623_ROTARY_FIRE.get();
            case BlasterName.WALTHER_BLASTER -> ModSounds.WALTHER_BLASTER_FIRE.get();
            case BlasterName.WALTHER_LPM_BLASTER -> ModSounds.WALTHER_LPM_BLASTER_FIRE.get();
            case BlasterName.WEBLY_S4 -> ModSounds.WEBLY_S4_FIRE.get();
            case BlasterName.WEBTEMP -> ModSounds.WEBTEMP_FIRE.get();
            case BlasterName.WEEQUAY_LANCE -> ModSounds.WEEQUAY_LANCE_FIRE.get();
            case BlasterName.WEEQUAY_PISTOL -> ModSounds.WEEQUAY_PISTOL_FIRE.get();
            case BlasterName.WEEQUAY_RIFLE -> ModSounds.WEEQUAY_RIFLE_FIRE.get();
            case BlasterName.WESTAR_20 -> ModSounds.WESTAR_20_FIRE.get();
            case BlasterName.WESTAR_34 -> ModSounds.WESTAR_34_FIRE.get();
            case BlasterName.WESTAR_35 -> ModSounds.WESTAR_35_FIRE.get();
            case BlasterName.WESTARM5 -> ModSounds.WESTARM5_FIRE.get();
            case BlasterName.WINCHESTER87 -> ModSounds.WINCHESTER87_FIRE.get();
            case BlasterName.X8_NIGHT_SNIPER -> ModSounds.X8_NIGHT_SNIPER_FIRE.get();
            case BlasterName.X30 -> ModSounds.X30_FIRE.get();
            case BlasterName.Z6_ROTARY -> ModSounds.Z6_ROTARY_FIRE.get();
            default -> ModSounds.E11_RIFLE_FIRE.get();
        };
    }

    public static SoundEvent getBlasterReloadSound(BlasterName blasterName, FiringMode blasterFireMode) {
        switch (blasterName) {
            case BlasterName.AMBAN_DISRUPTOR:
                return ModSounds.FOLEY_AMBAN_DISRUPTOR_SNIPER_RIFLE_RELOAD.get();
            case BlasterName.DC17M:
                if (blasterFireMode.equals(FiringMode.LAUNCHER)) {
                    return ModSounds.FOLEY_DC17M_LAUNCHER_RELOAD.get();
                } else {
                    return ModSounds.FOLEY_DC17M_RELOAD.get();
                }
            case BlasterName.DT29:
                return ModSounds.FOLEY_DT29_RELOAD.get();
            case BlasterName.RELBY_V10:
                if (blasterFireMode.equals(FiringMode.LAUNCHER)) {
                    return ModSounds.FOLEY_RELBY_V10_LAUNCHER_RELOAD.get();
                } else {
                    return ModSounds.FOLEY_MEDIUM_RELOAD_GAS.get();
                }
            case /*BlasterName.V6D_MORTAR_LAUNCHER, BlasterName.MORTAR,*/ BlasterName.K21C_PORTABLE_ORDANANCE_LAUNCHER:
                return ModSounds.FOLEY_LARGE_LAUNCHER_RELOAD.get();
            case BlasterName.LIGHTBOW, BlasterName.VULK_TAU623_ROTARY, BlasterName.Z6_ROTARY, BlasterName.EWEB, /*BlasterName.EWHB12,*/ BlasterName.M32, BlasterName.M45, BlasterName.M55, BlasterName.M61, BlasterName.MWC35C, BlasterName.T21, BlasterName.T21B,
                 BlasterName.BM107, BlasterName.GALAR90, BlasterName.NT242, BlasterName.GE36, BlasterName.NEO_CRUSADER_RIFLE, BlasterName.BOILER_RIFLE, BlasterName.BOWCASTER, BlasterName.T7_ION_DISRUPTOR:
                return ModSounds.FOLEY_LARGE_RELOAD_GAS.get();
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
                return ModSounds.FOLEY_SMALL_RELOAD_GAS.get();
            default:
                return ModSounds.FOLEY_MEDIUM_RELOAD_GAS.get();
        }
    }

    public static SoundEvent getBlasterSwitchFireMode(BlasterName blasterName, FiringMode blasterFireMode) {
        switch (blasterName) {
            case BlasterName.A180:
                if (blasterFireMode.equals(FiringMode.SEMI_AUTO)) {
                    return ModSounds.FOLEY_A180_PISTOL_SWITCH_FIRE_MODE.get();
                } else if (blasterFireMode.equals(FiringMode.FULL_AUTO)) {
                    return ModSounds.FOLEY_A180_RIFLE_SWITCH_FIRE_MODE.get();
                } else {
                    return ModSounds.FOLEY_A180_SNIPER_SWITCH_FIRE_MODE.get();
                }
            case BlasterName.A280CFE:
                if (blasterFireMode.equals(FiringMode.SEMI_AUTO)) {
                    return ModSounds.FOLEY_A280CFE_RIFLE_SWITCH_FIRE_MODE.get();
                } else if (blasterFireMode.equals(FiringMode.BURST)) {
                    return ModSounds.FOLEY_A280CFE_SNIPER_SWITCH_FIRE_MODE.get();
                } else {
                    return ModSounds.FOLEY_A280CFE_PISTOL_SWITCH_FIRE_MODE.get();
                }
            case BlasterName.AMBAN_DISRUPTOR:
                return ModSounds.FOLEY_AMBAN_DISRUPTOR_SNIPER_RIFLE_SWITCH_FIRE_MODE.get();
            case BlasterName.B1NA:
                return ModSounds.FOLEY_B1NA_SWITCH_FIRE_MODE.get();
            case BlasterName.DC17M:
                if (blasterFireMode.equals(FiringMode.SEMI_AUTO)) {
                    return ModSounds.FOLEY_DC17M_LAUNCHER_SWITCH_FIRE_MODE.get();
                } else if (blasterFireMode.equals(FiringMode.LAUNCHER)) {
                    return ModSounds.FOLEY_DC17M_SNIPER_SWITCH_FIRE_MODE.get();
                } else {
                    return ModSounds.FOLEY_DC17M_RIFLE_SWITCH_FIRE_MODE.get();
                }
            case BlasterName.DL44:
                return ModSounds.FOLEY_DL44_SWITCH_FIRE_MODE.get();
            case BlasterName.MW20_BRYAR_PISTOL:
                return ModSounds.FOLEY_MW20_BRYAR_PISTOL_SWITCH_FIRE_MODE.get();
            case /*BlasterName.BT_X42_FLAMETHROWER,*/ BlasterName.K21C_PORTABLE_ORDANANCE_LAUNCHER, BlasterName.LIGHTBOW, BlasterName.MINIMAG_PROTON_TORPEDO_LAUNCHER,
                 /*BlasterName.OPRESSOR_FLAMETHROWER, BlasterName.PLX1_MISSLE_LAUNCHER, BlasterName.RPS6_ROCKET_LAUNCHER, BlasterName.SMART_ROCKET, BlasterName.V6D_MORTAR_LAUNCHER,*/
                 BlasterName.VULK_TAU623_ROTARY, BlasterName.Z6_ROTARY, BlasterName.EWEB, /*BlasterName.EWHB12,*/ BlasterName.M32, BlasterName.M45, BlasterName.M55, BlasterName.M61, BlasterName.MWC35C, BlasterName.T21, BlasterName.T21B,
                 BlasterName.BM107, BlasterName.GALAR90:
                return ModSounds.FOLEY_LARGE_SWITCH_FIRE_MODE.get();
            case BlasterName._434_DEATHHAMMER, BlasterName.A140, BlasterName.AC177, BlasterName.APACHE, BlasterName.ASTRA40, BlasterName.B22, BlasterName.BE09, BlasterName.BH4, BlasterName.BLURRG1120,
                 BlasterName.BOONTA_BLASTER, BlasterName.BR14, BlasterName.C10, BlasterName.C96, BlasterName.CAIJ_VANDAS_BLASTER_PISTOL, BlasterName.CC420, BlasterName.CR2, BlasterName.CS14, BlasterName.DC15S_SIDEARM,
                 BlasterName.DC17, BlasterName.DE10, BlasterName.DG29, BlasterName.DH16, BlasterName.DH17, BlasterName.DH23, BlasterName.DL18, BlasterName.DL21, BlasterName.DT12, BlasterName.DT15, BlasterName.DT29, BlasterName.DX13,
                 BlasterName.EC17, BlasterName.ELG3A, BlasterName.FLINTLOQ_PISTOL, BlasterName.FN57, BlasterName.FP45, BlasterName.GL77, BlasterName.HF94, BlasterName.IB94, BlasterName.K16_BRYAR_PISTOL, BlasterName.KOCH9S,
                 BlasterName.KRIE4, BlasterName.KUEGET_LN21, BlasterName.KYD21, BlasterName.LEUCHT42, BlasterName.LL30, BlasterName.LUG_PO8, BlasterName.LW896, BlasterName.M19A1, BlasterName.MARG_MCM, BlasterName.MODEL_57,
                 BlasterName.NAMBU14, BlasterName.P38, BlasterName.PCC_PROJECTOR, BlasterName.POWER_5, BlasterName.PREMIER, BlasterName.Q2, BlasterName.RELBY_K23, BlasterName.RENEGADE, BlasterName.RG4D, BlasterName.RIG420, BlasterName.RK3,
                 BlasterName.RSKF44, BlasterName.RUGER_BLASTER, BlasterName.S195, BlasterName.S5, BlasterName.SACROS_K11, BlasterName.SATINES_LAMENT, BlasterName.SE14C, BlasterName.SEREXIM_MK_5,
                 BlasterName.SEDGLEYS_MK_5, BlasterName.SHARD3A, BlasterName.SK32, BlasterName.SNUBBLE, BlasterName.STEYR43, BlasterName.T6, BlasterName.TCA_PRO, BlasterName.TYPE14, BlasterName.UMBARAN_PISTOL,
                 BlasterName.WALTHER_BLASTER, BlasterName.WALTHER_LPM_BLASTER, BlasterName.WEBLY_S4, BlasterName.WEBTEMP, BlasterName.WEEQUAY_PISTOL, BlasterName.WESTAR_20, BlasterName.WESTAR_34,
                 BlasterName.WESTAR_35, BlasterName.X8_NIGHT_SNIPER, BlasterName.X30, BlasterName.PANIC_PISTOL, BlasterName.VERPINE_SHATTER:
                return ModSounds.FOLEY_SMALL_SWITCH_FIRE_MODE.get();
            default:
                return ModSounds.FOLEY_MEDIUM_SWITCH_FIRE_MODE.get();
        }
    }

    public static SoundEvent getBlasterEquip(BlasterName blasterName) {
        Random random = new Random();
        switch (blasterName) {
            case BlasterName.AMBAN_DISRUPTOR:
                return ModSounds.FOLEY_AMBAN_DISRUPTOR_SNIPER_RIFLE_EQUIP.get();
            case BlasterName.IB94:
                return ModSounds.FOLEY_IB94_EQUIP.get();
            case /*BlasterName.BT_X42_FLAMETHROWER,*/ BlasterName.K21C_PORTABLE_ORDANANCE_LAUNCHER, BlasterName.LIGHTBOW, BlasterName.MINIMAG_PROTON_TORPEDO_LAUNCHER,
                 /*BlasterName.OPRESSOR_FLAMETHROWER, BlasterName.PLX1_MISSLE_LAUNCHER, BlasterName.RPS6_ROCKET_LAUNCHER, BlasterName.SMART_ROCKET, BlasterName.V6D_MORTAR_LAUNCHER,*/
                 BlasterName.VULK_TAU623_ROTARY, BlasterName.Z6_ROTARY, BlasterName.EWEB, /*BlasterName.EWHB12,*/ BlasterName.M32, BlasterName.M45, BlasterName.M55, BlasterName.M61, BlasterName.MWC35C, BlasterName.T21, BlasterName.T21B,
                 BlasterName.BM107, BlasterName.GALAR90:
                return ModSounds.FOLEY_LARGE_EQUIP.get();
            case BlasterName._434_DEATHHAMMER, BlasterName.A140, BlasterName.A180, BlasterName.AC177, BlasterName.APACHE, BlasterName.ASTRA40, BlasterName.B22, BlasterName.BE09, BlasterName.BH4, BlasterName.BLURRG1120,
                 BlasterName.BOONTA_BLASTER, BlasterName.BR14, BlasterName.C10, BlasterName.C96, BlasterName.CAIJ_VANDAS_BLASTER_PISTOL, BlasterName.CC420, BlasterName.CR2, BlasterName.CS14, BlasterName.DC15S_SIDEARM,
                 BlasterName.DC17, BlasterName.DE10, BlasterName.DG29, BlasterName.DH16, BlasterName.DH17, BlasterName.DH23, BlasterName.DL18, BlasterName.DL21, BlasterName.DL44, BlasterName.DT12, BlasterName.DT15, BlasterName.DT29, BlasterName.DX13,
                 BlasterName.EC17, BlasterName.ELG3A, BlasterName.FLINTLOQ_PISTOL, BlasterName.FN57, BlasterName.FP45, BlasterName.GL77, BlasterName.HF94, BlasterName.K16_BRYAR_PISTOL, BlasterName.KOCH9S,
                 BlasterName.KRIE4, BlasterName.KUEGET_LN21, BlasterName.KYD21, BlasterName.LEUCHT42, BlasterName.LL30, BlasterName.LUG_PO8, BlasterName.LW896, BlasterName.M19A1, BlasterName.MARG_MCM, BlasterName.MODEL_57,
                 BlasterName.NAMBU14, BlasterName.P38, BlasterName.PCC_PROJECTOR, BlasterName.POWER_5, BlasterName.PREMIER, BlasterName.Q2, BlasterName.RELBY_K23, BlasterName.RENEGADE, BlasterName.RG4D, BlasterName.RIG420, BlasterName.RK3,
                 BlasterName.RSKF44, BlasterName.RUGER_BLASTER, BlasterName.S195, BlasterName.S5, BlasterName.SACROS_K11, BlasterName.SATINES_LAMENT, BlasterName.SE14C, BlasterName.SEREXIM_MK_5,
                 BlasterName.SEDGLEYS_MK_5, BlasterName.SHARD3A, BlasterName.SK32, BlasterName.SNUBBLE, BlasterName.STEYR43, BlasterName.T6, BlasterName.TCA_PRO, BlasterName.TYPE14, BlasterName.UMBARAN_PISTOL,
                 BlasterName.WALTHER_BLASTER, BlasterName.WALTHER_LPM_BLASTER, BlasterName.WEBLY_S4, BlasterName.WEBTEMP, BlasterName.WEEQUAY_PISTOL, BlasterName.WESTAR_20, BlasterName.WESTAR_34,
                 BlasterName.WESTAR_35, BlasterName.X8_NIGHT_SNIPER, BlasterName.X30, BlasterName.PANIC_PISTOL, BlasterName.VERPINE_SHATTER, BlasterName.MW20_BRYAR_PISTOL, BlasterName.B1NA:
                if (random.nextInt(60) == 0) {
                    return ModSounds.FOLEY_SMALL_FLORISH_EQUIP.get();
                } else if (random.nextInt(20) == 0){
                    return ModSounds.FOLEY_SMALL_QUICK_EQUIP.get();
                } else {
                    return ModSounds.FOLEY_SMALL_EQUIP.get();
                }
            case BlasterName.BARMST12, BlasterName.BLNDRBUS, BlasterName.CA87, BlasterName.FLITE37, BlasterName.SX21, BlasterName.VANGUARD_SCATTER, BlasterName.WINCHESTER87:
                return ModSounds.FOLEY_SCATTER_SHOT_EQUIP.get();
            default:
                return ModSounds.FOLEY_MEDIUM_EQUIP.get();
        }
    }

    public static SoundEvent getBlasterUnequip(BlasterName blasterName) {
        Random random = new Random();
        switch (blasterName) {
            case /*BlasterName.BT_X42_FLAMETHROWER,*/ BlasterName.K21C_PORTABLE_ORDANANCE_LAUNCHER, BlasterName.LIGHTBOW, BlasterName.MINIMAG_PROTON_TORPEDO_LAUNCHER,
                 /*BlasterName.OPRESSOR_FLAMETHROWER, BlasterName.PLX1_MISSLE_LAUNCHER, BlasterName.RPS6_ROCKET_LAUNCHER, BlasterName.SMART_ROCKET, BlasterName.V6D_MORTAR_LAUNCHER,*/
                 BlasterName.VULK_TAU623_ROTARY, BlasterName.Z6_ROTARY, BlasterName.EWEB, /*BlasterName.EWHB12,*/ BlasterName.M32, BlasterName.M45, BlasterName.M55, BlasterName.M61, BlasterName.MWC35C, BlasterName.T21, BlasterName.T21B,
                 BlasterName.BM107, BlasterName.GALAR90:
                return ModSounds.FOLEY_LARGE_UNEQUIP.get();
            case BlasterName._434_DEATHHAMMER, BlasterName.A140, BlasterName.A180, BlasterName.AC177, BlasterName.APACHE, BlasterName.ASTRA40, BlasterName.B22, BlasterName.BE09, BlasterName.BH4, BlasterName.BLURRG1120,
                 BlasterName.BOONTA_BLASTER, BlasterName.BR14, BlasterName.C10, BlasterName.C96, BlasterName.CAIJ_VANDAS_BLASTER_PISTOL, BlasterName.CC420, BlasterName.CR2, BlasterName.CS14, BlasterName.DC15S_SIDEARM,
                 BlasterName.DC17, BlasterName.DE10, BlasterName.DG29, BlasterName.DH16, BlasterName.DH17, BlasterName.DH23, BlasterName.DL18, BlasterName.DL21, BlasterName.DL44, BlasterName.DT12, BlasterName.DT15, BlasterName.DT29, BlasterName.DX13,
                 BlasterName.EC17, BlasterName.ELG3A, BlasterName.FLINTLOQ_PISTOL, BlasterName.FN57, BlasterName.FP45, BlasterName.GL77, BlasterName.HF94, BlasterName.K16_BRYAR_PISTOL, BlasterName.KOCH9S,
                 BlasterName.KRIE4, BlasterName.KUEGET_LN21, BlasterName.KYD21, BlasterName.LEUCHT42, BlasterName.LL30, BlasterName.LUG_PO8, BlasterName.LW896, BlasterName.M19A1, BlasterName.MARG_MCM, BlasterName.MODEL_57,
                 BlasterName.NAMBU14, BlasterName.P38, BlasterName.PCC_PROJECTOR, BlasterName.POWER_5, BlasterName.PREMIER, BlasterName.Q2, BlasterName.RELBY_K23, BlasterName.RENEGADE, BlasterName.RG4D, BlasterName.RIG420, BlasterName.RK3,
                 BlasterName.RSKF44, BlasterName.RUGER_BLASTER, BlasterName.S195, BlasterName.S5, BlasterName.SACROS_K11, BlasterName.SATINES_LAMENT, BlasterName.SE14C, BlasterName.SEREXIM_MK_5,
                 BlasterName.SEDGLEYS_MK_5, BlasterName.SHARD3A, BlasterName.SK32, BlasterName.SNUBBLE, BlasterName.STEYR43, BlasterName.T6, BlasterName.TCA_PRO, BlasterName.TYPE14, BlasterName.UMBARAN_PISTOL,
                 BlasterName.WALTHER_BLASTER, BlasterName.WALTHER_LPM_BLASTER, BlasterName.WEBLY_S4, BlasterName.WEBTEMP, BlasterName.WEEQUAY_PISTOL, BlasterName.WESTAR_20, BlasterName.WESTAR_34,
                 BlasterName.WESTAR_35, BlasterName.X8_NIGHT_SNIPER, BlasterName.X30, BlasterName.PANIC_PISTOL, BlasterName.VERPINE_SHATTER, BlasterName.MW20_BRYAR_PISTOL, BlasterName.B1NA:
                if (random.nextInt(60) == 0) {
                    return ModSounds.FOLEY_SMALL_FLORISH_UNEQUIP.get();
                } else {
                    return ModSounds.FOLEY_SMALL_UNEQUIP.get();
                }
            default:
                return ModSounds.FOLEY_MEDIUM_UNEQUIP.get();
        }
    }
}
