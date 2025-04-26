package net.uhhitscam.starwars.util;

import net.minecraft.sounds.SoundEvent;
import net.uhhitscam.starwars.sound.ModSounds;

import java.util.Random;

public class BlasterSoundsUtil {
    public static SoundEvent getBlasterFireSound(String blasterName, String blasterFireMode) {
        if (blasterFireMode.equals("STUN")) {
            if (blasterName.equals("ec17")) {
                return ModSounds.EC17_STUN_FIRE.get();
            } else {
                return ModSounds.STUN_FIRE.get();
            }
        } else if (blasterName.equals("a180")) {
            return switch (blasterFireMode) {
                case "FULL_AUTO" -> ModSounds.A180_RIFLE_FIRE.get();
                case "SNIPER" -> ModSounds.A180_SNIPER_FIRE.get();
                default -> ModSounds.A180_PISTOL_FIRE.get();
            };
        } else if (blasterName.equals("a280cfe")) {
            return switch (blasterFireMode) {
                case "BURST" -> ModSounds.A280CFE_RIFLE_FIRE.get();
                case "SNIPER" -> ModSounds.A280CFE_SNIPER_FIRE.get();
                default -> ModSounds.A280CFE_PISTOL_FIRE.get();
            };
        } else if (blasterName.equals("blndrbus")) {
            if (blasterFireMode.equals("REPULSE")) {
                return ModSounds.BLNDRBUS_REPULSE_FIRE.get();
            } else {
                return ModSounds.BLNDRBUS_FIRE.get();
            }
        } else if (blasterName.equals("bowcaster")) {
            if (blasterFireMode.equals("CHARGED")) {
                return ModSounds.BOWCASTER_CHARGED_FIRE.get();
            } else {
                return ModSounds.BOWCASTER_FIRE.get();
            }
        } else if (blasterName.equals("bryar_rifle")) {
            if (blasterFireMode.equals("CHARGED")) {
                return ModSounds.BRYAR_RIFLE_CHARGED_FIRE.get();
            } else {
                return ModSounds.BRYAR_RIFLE_FIRE.get();
            }
        } else if (blasterName.equals("ca87")) {
            return switch (blasterFireMode) {
                case "REPULSE" -> ModSounds.CA87_REPULSE_FIRE.get();
                case "STUN" -> ModSounds.CA87_STUN_FIRE.get();
                default -> ModSounds.CA87_FIRE.get();
            };
        } else if (blasterName.equals("motto_mk_4")) {
            if (blasterFireMode.equals("REPULSE")) {
                return ModSounds.MOTTO_MK_4_REPULSE_FIRE.get();
            } else {
                return ModSounds.MOTTO_MK_4_FIRE.get();
            }
        } else if (blasterName.equals("caij_vandas_blaster_pistol")) {
            if (blasterFireMode.equals("CHARGED")) {
                return ModSounds.CAIJ_VANDAS_BLASTER_PISTOL_CHARGED_FIRE.get();
            } else {
                return ModSounds.CAIJ_VANDAS_BLASTER_PISTOL_FIRE.get();
            }
        } else if (blasterName.equals("dc17m")) {
            return switch (blasterFireMode) {
                case "LAUNCHER" -> ModSounds.DC17M_LAUNCHER_FIRE.get();
                case "SNIPER" -> ModSounds.DC17M_SNIPER_FIRE.get();
                default -> ModSounds.DC17M_FIRE.get();
            };
        } else if (blasterName.equals("dl44")) {
            if (blasterFireMode.equals("SNIPER")) {
                return ModSounds.DL44_SNIPER_FIRE.get();
            } else {
                return ModSounds.DL44_FIRE.get();
            }
        } else if (blasterName.equals("dlt20a")) {
            if (blasterFireMode.equals("SNIPER")) {
                return ModSounds.DLT20A_SNIPER_FIRE.get();
            } else {
                return ModSounds.DLT20A_FIRE.get();
            }
        } else if (blasterName.equals("ewhb12")) {
            if (blasterFireMode.equals("FULL_AUTO")) {
                return ModSounds.EWHB12_AUTO_FIRE.get();
            } else {
                return ModSounds.EWHB12_FIRE.get();
            }
        } else if (blasterName.equals("k16_bryar_pistol")) {
            if (blasterFireMode.equals("CHARGED")) {
                return ModSounds.K16_BRYAR_PISTOL_CHARGED_FIRE.get();
            } else {
                return ModSounds.K16_BRYAR_PISTOL_FIRE.get();
            }
        } else if (blasterName.equals("relby_v10")) {
            return switch (blasterFireMode) {
                case "LAUNCHER" -> ModSounds.RELBY_V10_LAUNCHER_FIRE.get();
                case "CHARGED" -> ModSounds.RELBY_V10_CHARGED_FIRE.get();
                default -> ModSounds.RELBY_V10_FIRE.get();
            };
        } else if (blasterName.equals("shadow_trooper_blaster")) {
            if (blasterFireMode.equals("CHARGED")) {
                return ModSounds.SHADOW_TROOPER_BLASTER_CHARGED_FIRE.get();
            } else {
                return ModSounds.SHADOW_TROOPER_BLASTER_FIRE.get();
            }
        } else if (blasterName.equals("tl50")) {
            if (blasterFireMode.equals("LAUNCHER")) {
                return ModSounds.TL50_LAUNCHER_FIRE.get();
            } else {
                return ModSounds.TL50_FIRE.get();
            }
        }

        return switch (blasterName) {
            case "_62aug2_hunting_rifle" -> ModSounds._62AUG2_HUNTING_RIFLE_FIRE.get();
            case "_84u_hunting_rifle" -> ModSounds._84U_HUNTING_RIFLE_FIRE.get();
            case "_434_deathhammer" -> ModSounds._434_DEATHHAMMER_FIRE.get();
            case "_773_firepuncher" -> ModSounds._773_FIREPUNCHER_FIRE.get();
            case "_785mk_firepuncherx" -> ModSounds._785MK_FIREPUNCHERX_FIRE.get();
            case "a140" -> ModSounds.A140_FIRE.get();
            case "a280" -> ModSounds.A280_FIRE.get();
            case "a280c" -> ModSounds.A280C_FIRE.get();
            case "a295" -> ModSounds.A295_FIRE.get();
            case "a300" -> ModSounds.A300_FIRE.get();
            case "a310" -> ModSounds.A310_FIRE.get();
            case "a350" -> ModSounds.A350_FIRE.get();
            case "ac177" -> ModSounds.AC177_FIRE.get();
            case "acp_repeater" -> ModSounds.ACP_REPEATER_FIRE.get();
            case "akbarc" -> ModSounds.AKBARC_FIRE.get();
            case "amban_disruptor" -> ModSounds.AMBAN_DISRUPTOR_FIRE.get();
            case "apache" -> ModSounds.APACHE_FIRE.get();
            case "astra40" -> ModSounds.ASTRA40_FIRE.get();
            case "avarik" -> ModSounds.AVARIK_FIRE.get();
            case "b1na" -> ModSounds.B1NA_FIRE.get();
            case "b22" -> ModSounds.B22_FIRE.get();
            case "balnab" -> ModSounds.BALNAB_FIRE.get();
            case "barmst12" -> ModSounds.BARMST12_FIRE.get();
            case "baton_blaster" -> ModSounds.BATON_BLASTER_FIRE.get();
            case "be09" -> ModSounds.BE09_FIRE.get();
            case "be29" -> ModSounds.BE29_FIRE.get();
            case "berserker" -> ModSounds.BERSERKER_FIRE.get();
            case "bh4" -> ModSounds.BH4_FIRE.get();
            case "blaster_spear" -> ModSounds.BLASTER_SPEAR_FIRE.get();
            case "blurrg1120" -> ModSounds.BLURRG1120_FIRE.get();
            case "bm107" -> ModSounds.BM107_FIRE.get();
            case "boiler_rifle" -> ModSounds.BOILER_RIFLE_FIRE.get();
            case "boonta_blaster" -> ModSounds.BOONTA_BLASTER_FIRE.get();
            case "br14" -> ModSounds.BR14_FIRE.get();
            case "bt_x42_flamethrower" -> ModSounds.BT_X42_FLAMETHROWER_FIRE.get();
            case "c10" -> ModSounds.C10_FIRE.get();
            case "c96" -> ModSounds.C96_FIRE.get();
            case "cc420" -> ModSounds.CC420_FIRE.get();
            case "cj9_bo_rifle" -> ModSounds.CJ9_BO_RIFLE_FIRE.get();
            case "corpo_rifle" -> ModSounds.CORPO_RIFLE_FIRE.get();
            case "cr2" -> ModSounds.CR2_FIRE.get();
            case "cs14" -> ModSounds.CS14_FIRE.get();
            case "cycler_rifle" -> ModSounds.CYCLER_RIFLE_FIRE.get();
            case "czerka_adventurer" -> ModSounds.CZERKA_ADVENTURER_FIRE.get();
            case "dark_trooper_rifle" -> ModSounds.DARK_TROOPER_RIFLE_FIRE.get();
            case "dc12u" -> ModSounds.DC12U_FIRE.get();
            case "dc15a" -> ModSounds.DC15A_FIRE.get();
            case "dc15le" -> ModSounds.DC15LE_FIRE.get();
            case "dc15s_carbine" -> ModSounds.DC15S_CARBINE_FIRE.get();
            case "dc15s_sidearm" -> ModSounds.DC15S_SIDEARM_FIRE.get();
            case "dc15x" -> ModSounds.DC15X_FIRE.get();
            case "dc17" -> ModSounds.DC17_FIRE.get();
            case "dc19" -> ModSounds.DC19_FIRE.get();
            case "de10" -> ModSounds.DE10_FIRE.get();
            case "deftech" -> ModSounds.DEFTECH_FIRE.get();
            case "dfd1" -> ModSounds.DFD1_FIRE.get();
            case "dg29" -> ModSounds.DG29_FIRE.get();
            case "dh16" -> ModSounds.DH16_FIRE.get();
            case "dh17" -> ModSounds.DH17_FIRE.get();
            case "dh23" -> ModSounds.DH23_FIRE.get();
            case "dh447" -> ModSounds.DH447_FIRE.get();
            case "dl18" -> ModSounds.DL18_FIRE.get();
            case "dl21" -> ModSounds.DL21_FIRE.get();
            case "dls12" -> ModSounds.DLS12_FIRE.get();
            case "dlt18" -> ModSounds.DLT18_FIRE.get();
            case "dlt19" -> ModSounds.DLT19_FIRE.get();
            case "dlt19d" -> ModSounds.DLT19D_FIRE.get();
            case "dlt19x" -> ModSounds.DLT19X_FIRE.get();
            case "dn_bolt_caster" -> ModSounds.DN_BOLT_CASTER_FIRE.get();
            case "dp23" -> ModSounds.DP23_FIRE.get();
            case "dressellian_projectile_rifle" -> ModSounds.DRESSELLIAN_PROJECTILE_RIFLE_FIRE.get();
            case "dt12" -> ModSounds.DT12_FIRE.get();
            case "dt15" -> ModSounds.DT15_FIRE.get();
            case "dt29" -> ModSounds.DT29_FIRE.get();
            case "dt57" -> ModSounds.DT57_FIRE.get();
            case "dx13" -> ModSounds.DX13_FIRE.get();
            case "e5" -> ModSounds.E5_FIRE.get();
            case "e5_bx" -> ModSounds.E5_BX_FIRE.get();
            case "e5_ce" -> ModSounds.E5_CE_FIRE.get();
            case "e5c" -> ModSounds.E5C_FIRE.get();
            case "e5s" -> ModSounds.E5S_FIRE.get();
            case "e10" -> ModSounds.E10_FIRE.get();
            case "e10_5" -> ModSounds.E10_5_FIRE.get();
            case "e10r" -> ModSounds.E10R_FIRE.get();
            case "e11_carbine" -> ModSounds.E11_CARBINE_FIRE.get();
            case "e11_rifle" -> ModSounds.E11_RIFLE_FIRE.get();
            case "e11b" -> ModSounds.E11B_FIRE.get();
            case "e11d" -> ModSounds.E11D_FIRE.get();
            case "e11s" -> ModSounds.E11S_FIRE.get();
            case "e17d" -> ModSounds.E17D_FIRE.get();
            case "e22" -> ModSounds.E22_FIRE.get();
            case "ec17" -> ModSounds.EC17_FIRE.get();
            case "ee3" -> ModSounds.EE3_FIRE.get();
            case "ee4" -> ModSounds.EE4_FIRE.get();
            case "elg3a" -> ModSounds.ELG3A_FIRE.get();
            case "energy_bow" -> ModSounds.ENERGY_BOW_FIRE.get();
            case "energy_crossbow" -> ModSounds.ENERGY_CROSSBOW_FIRE.get();
            case "eweb" -> ModSounds.EWEB_FIRE.get();
            case "fc1_flechette_launcher" -> ModSounds.FC1_FLECHETTE_LAUNCHER_FIRE.get();
            case "flintloq_pistol" -> ModSounds.FLINTLOQ_PISTOL_FIRE.get();
            case "flintloq_rifle" -> ModSounds.FLINTLOQ_RIFLE_FIRE.get();
            case "flite37" -> ModSounds.FLITE37_FIRE.get();
            case "fn57" -> ModSounds.FN57_FIRE.get();
            case "fp45" -> ModSounds.FP45_FIRE.get();
            case "galaar15" -> ModSounds.GALAAR15_FIRE.get();
            case "galar90" -> ModSounds.GALAR90_FIRE.get();
            case "ge36" -> ModSounds.GE36_FIRE.get();
            case "gl77" -> ModSounds.GL77_FIRE.get();
            case "hf94" -> ModSounds.HF94_FIRE.get();
            case "ib94" -> ModSounds.IB94_FIRE.get();
            case "imperial_supercommando_blaster" -> ModSounds.IMPERIAL_SUPERCOMMANDO_BLASTER_FIRE.get();
            case "iqa11" -> ModSounds.IQA11_FIRE.get();
            case "jezali_cycler_rifle" -> ModSounds.JEZALI_CYCLER_RIFLE_FIRE.get();
            case "jnd41" -> ModSounds.JND41_FIRE.get();
            case "k21c_portable_ordanance_launcher" -> ModSounds.K21C_PORTABLE_ORDANANCE_LAUNCHER_FIRE.get();
            case "ka74" -> ModSounds.KA74_FIRE.get();
            case "kisteer_1284" -> ModSounds.KISTEER_1284_FIRE.get();
            case "koch9s" -> ModSounds.KOCH9S_FIRE.get();
            case "krie4" -> ModSounds.KRIE4_FIRE.get();
            case "kueget_ln21" -> ModSounds.KUEGET_LN21.get();
            case "kyd21" -> ModSounds.KYD21_FIRE.get();
            case "l5" -> ModSounds.L5_FIRE.get();
            case "l60" -> ModSounds.L60_FIRE.get();
            case "leucht42" -> ModSounds.LEUCHT42_FIRE.get();
            case "lightbow" -> ModSounds.LIGHTBOW_FIRE.get();
            case "ll30" -> ModSounds.LL30_FIRE.get();
            case "lug_po8" -> ModSounds.LUG_PO8_FIRE.get();
            case "lw896" -> ModSounds.LW896_FIRE.get();
            case "m12" -> ModSounds.M12_FIRE.get();
            case "m19a1" -> ModSounds.M19A1_FIRE.get();
            case "m32" -> ModSounds.M32_FIRE.get();
            case "m41" -> ModSounds.M41_FIRE.get();
            case "m45" -> ModSounds.M45_FIRE.get();
            case "m55" -> ModSounds.M55_FIRE.get();
            case "m61" -> ModSounds.M61_FIRE.get();
            case "marg_mcm" -> ModSounds.MARG_MCM_FIRE.get();
            case "minimag_proton_torpedo_launcher" -> ModSounds.MINIMAG_PROTON_TORPEDO_LAUNCHER_FIRE.get();
            case "mk_ii_paladin" -> ModSounds.MK_II_PALADIN_FIRE.get();
            case "model_57" -> ModSounds.MODEL_57_FIRE.get();
            case "mortar" -> ModSounds.MORTAR_FIRE.get();
            case "mw20_bryar_pistol" -> ModSounds.MW20_BRYAR_PISTOL_FIRE.get();
            case "mwc35c" -> ModSounds.MWC35C_FIRE.get();
            case "nambu14" -> ModSounds.NAMBU14_FIRE.get();
            case "neo_crusader_rifle" -> ModSounds.NEO_CRUSADER_RIFLE_FIRE.get();
            case "night_stinger" -> ModSounds.NIGHT_STINGER_FIRE.get();
            case "night_wind_rifle" -> ModSounds.NIGHT_WIND_RIFLE_FIRE.get();
            case "nt242" -> ModSounds.NT242_FIRE.get();
            case "ok98" -> ModSounds.OK98_FIRE.get();
            case "opressor_flamethrower" -> ModSounds.OPRESSOR_FLAMETHROWER_FIRE.get();
            case "outland_rifle" -> ModSounds.OUTLAND_RIFLE_FIRE.get();
            case "p38" -> ModSounds.P38_FIRE.get();
            case "panic_pistol" -> ModSounds.PANIC_PISTOL_FIRE.get();
            case "pcc_projector" -> ModSounds.PCC_PROJECTOR_FIRE.get();
            case "pk23" -> ModSounds.PK23_FIRE.get();
            case "plx1_missle_launcher" -> ModSounds.PLX1_MISSLE_LAUNCHER_FIRE.get();
            case "power_5" -> ModSounds.POWER_5_FIRE.get();
            case "premier" -> ModSounds.PREMIER_FIRE.get();
            case "q2" -> ModSounds.Q2_FIRE.get();
            case "quarren_rifle" -> ModSounds.QUARREN_RIFLE_FIRE.get();
            case "relby_k23" -> ModSounds.RELBY_K23_FIRE.get();
            case "renegade" -> ModSounds.RENEGADE_FIRE.get();
            case "rg4d" -> ModSounds.RG4D_FIRE.get();
            case "rig420" -> ModSounds.RIG420_FIRE.get();
            case "rk3" -> ModSounds.RK3_FIRE.get();
            case "rps6_rocket_launcher" -> ModSounds.RPS6_ROCKET_LAUNCHER_FIRE.get();
            case "rskf44" -> ModSounds.RSKF44_FIRE.get();
            case "rt97c" -> ModSounds.RT97C_FIRE.get();
            case "ruger_blaster" -> ModSounds.RUGER_BLASTER_FIRE.get();
            case "s5" -> ModSounds.S5_FIRE.get();
            case "s195" -> ModSounds.S195_FIRE.get();
            case "sacros_k11" -> ModSounds.SACROS_K11_FIRE.get();
            case "satines_lament" -> ModSounds.SATINES_LAMENT_FIRE.get();
            case "sc_x30" -> ModSounds.SC_X30_FIRE.get();
            case "se14c" -> ModSounds.SE14C_FIRE.get();
            case "se14r" -> ModSounds.SE14R_FIRE.get();
            case "sedgleys_mk_5" -> ModSounds.SEDGLEYS_MK_5_FIRE.get();
            case "serexim_mk_5" -> ModSounds.SEREXIM_MK_5_FIRE.get();
            case "shard3a" -> ModSounds.SHARD3A_FIRE.get();
            case "sk32" -> ModSounds.SK32_FIRE.get();
            case "smart_rocket" -> ModSounds.SMART_ROCKET_FIRE.get();
            case "snubble" -> ModSounds.SNUBBLE_FIRE.get();
            case "sonic_blaster" -> ModSounds.SONIC_BLASTER_FIRE.get();
            case "steyr43" -> ModSounds.STEYR43_FIRE.get();
            case "sx21" -> ModSounds.SX21_FIRE.get();
            case "t6" -> ModSounds.T6_FIRE.get();
            case "t7_ion_disruptor" -> ModSounds.T7_ION_DISRUPTOR_FIRE.get();
            case "t21" -> ModSounds.T21_FIRE.get();
            case "t21b" -> ModSounds.T21B_FIRE.get();
            case "tca_pro" -> ModSounds.TCA_PRO_FIRE.get();
            case "tomsun97" -> ModSounds.TOMSUN97_FIRE.get();
            case "type14" -> ModSounds.TYPE14_FIRE.get();
            case "umbaran_pistol" -> ModSounds.UMBARAN_PISTOL_FIRE.get();
            case "v6d_mortar_launcher" -> ModSounds.V6D_MORTAR_LUANCHER_FIRE.get();
            case "valken38x" -> ModSounds.VALKEN38X_FIRE.get();
            case "vanguard_scatter" -> ModSounds.VANGUARD_SCATTER_FIRE.get();
            case "vect_uzi" -> ModSounds.VECT_UZI_FIRE.get();
            case "verpine_shatter" -> ModSounds.VERPINE_SHATTER_FIRE.get();
            case "vulk_tau623_rotary" -> ModSounds.VULK_TAU623_ROTARY_FIRE.get();
            case "walther_blaster" -> ModSounds.WALTHER_BLASTER_FIRE.get();
            case "walther_lpm_blaster" -> ModSounds.WALTHER_LPM_BLASTER_FIRE.get();
            case "webly_s4" -> ModSounds.WEBLY_S4_FIRE.get();
            case "webtemp" -> ModSounds.WEBTEMP_FIRE.get();
            case "weequay_lance" -> ModSounds.WEEQUAY_LANCE_FIRE.get();
            case "weequay_pistol" -> ModSounds.WEEQUAY_PISTOL_FIRE.get();
            case "weequay_rifle" -> ModSounds.WEEQUAY_RIFLE_FIRE.get();
            case "westar_20" -> ModSounds.WESTAR_20_FIRE.get();
            case "westar_34" -> ModSounds.WESTAR_34_FIRE.get();
            case "westar_35" -> ModSounds.WESTAR_35_FIRE.get();
            case "westarm5" -> ModSounds.WESTARM5_FIRE.get();
            case "winchester87" -> ModSounds.WINCHESTER87_FIRE.get();
            case "x8_night_sniper" -> ModSounds.X8_NIGHT_SNIPER_FIRE.get();
            case "z6_rotary" -> ModSounds.Z6_ROTARY_FIRE.get();
            default -> ModSounds.E11_RIFLE_FIRE.get();
        };
    }

    public static SoundEvent getBlasterReloadSound(String blasterName, String blasterFireMode) {
        switch (blasterName) {
            case "amban_disruptor":
                return ModSounds.FOLEY_AMBAN_DISRUPTOR_SNIPER_RIFLE_RELOAD.get();
            case "dc17m":
                if (blasterFireMode.equals("LAUNCHER")) {
                    return ModSounds.FOLEY_DC17M_LAUNCHER_RELOAD.get();
                } else {
                    return ModSounds.FOLEY_DC17M_RELOAD.get();
                }
            case "dt29":
                return ModSounds.FOLEY_DT29_RELOAD.get();
            case "relby_v10":
                if (blasterFireMode.equals("LAUNCHER")) {
                    return ModSounds.FOLEY_RELBY_V10_LAUNCHER_RELOAD.get();
                } else {
                    return ModSounds.FOLEY_MEDIUM_RELOAD_GAS.get();
                }
            case "v6d_mortar_launcher", "mortar", "k21c_portable_ordanance_launcher":
                return ModSounds.FOLEY_LARGE_LAUNCHER_RELOAD.get();
            case "lightbow", "vulk_tau623_rotary", "z6_rotary", "eweb", "ewhb12", "m32", "m45", "m55", "m61", "mwc35c", "t21", "t21b",
                 "bm107", "galar90", "nt242", "ge36", "neo_crusader_rifle", "boiler_rifle", "bowcaster", "t7_ion_diruptor":
                return ModSounds.FOLEY_LARGE_RELOAD_GAS.get();
            case "a140", "a180", "ac177", "apache", "astra40", "b22", "be09", "bh4", "blurrg1120",
                 "boonta_blaster", "br14", "c10", "c96", "caij_vandas_blaster_pistol", "cc420", "cr2", "cs14", "dc15s_sidearm",
                 "dc17", "de10", "dg29", "dh16", "dh17", "dh23", "dl18", "dl21", "dt12", "dt15", "dx13",
                 "ec17", "elg3a", "flintloq_pistol", "fn57", "fp45", "gl77", "hf94", "ib94", "k16_bryar_pistol", "koch9s",
                 "krie4", "kueget_ln21", "kyd21", "leucht42", "ll30", "lug_po8", "lw896", "m19a1", "marg_mcm", "model_57",
                 "nambu14", "p38", "pcc_projector", "premier", "q2", "relby_k23", "renegade", "rg4d", "rig420", "rk3",
                 "rskf44", "ruger", "s195", "s5", "sacros_k11", "satines_lament", "sc_x30", "se14c", "serexim_mk_5",
                 "sedgleys_mk_5", "shard3a", "sk32", "snubble", "steyr43", "t6", "tca_pro", "type14", "umbaran_pistol",
                 "walther_blaster", "walther_lpm_blaster", "webly_s4", "webtemp", "weequay_pistol", "westar_20",
                 "x8_night_sniper", "panic_pistol", "verpine_shatter":
                return ModSounds.FOLEY_SMALL_RELOAD_GAS.get();
            default:
                return ModSounds.FOLEY_MEDIUM_RELOAD_GAS.get();
        }
    }

    public static SoundEvent getBlasterSwitchFireMode(String blasterName, String blasterFireMode) {
        switch (blasterName) {
            case "a180":
                if (blasterFireMode.equals("SEMI_AUTO")) {
                    return ModSounds.FOLEY_A180_PISTOL_SWITCH_FIRE_MODE.get();
                } else if (blasterFireMode.equals("FULL_AUTO")) {
                    return ModSounds.FOLEY_A180_RIFLE_SWITCH_FIRE_MODE.get();
                } else {
                    return ModSounds.FOLEY_A180_SNIPER_SWITCH_FIRE_MODE.get();
                }
            case "a280cfe":
                if (blasterFireMode.equals("SEMI_AUTO")) {
                    return ModSounds.FOLEY_A280CFE_RIFLE_SWITCH_FIRE_MODE.get();
                } else if (blasterFireMode.equals("BURST")) {
                    return ModSounds.FOLEY_A280CFE_SNIPER_SWITCH_FIRE_MODE.get();
                } else {
                    return ModSounds.FOLEY_A280CFE_PISTOL_SWITCH_FIRE_MODE.get();
                }
            case "amban_disruptor":
                return ModSounds.FOLEY_AMBAN_DISRUPTOR_SNIPER_RIFLE_SWITCH_FIRE_MODE.get();
            case "b1na":
                return ModSounds.FOLEY_B1NA_SWITCH_FIRE_MODE.get();
            case "dc17m":
                if (blasterFireMode.equals("SEMI_AUTO")) {
                    return ModSounds.FOLEY_DC17M_LAUNCHER_SWITCH_FIRE_MODE.get();
                } else if (blasterFireMode.equals("LAUNCHER")) {
                    return ModSounds.FOLEY_DC17M_SNIPER_SWITCH_FIRE_MODE.get();
                } else {
                    return ModSounds.FOLEY_DC17M_RIFLE_SWITCH_FIRE_MODE.get();
                }
            case "dl44":
                return ModSounds.FOLEY_DL44_SWITCH_FIRE_MODE.get();
            case "mw20_bryar_pistol":
                return ModSounds.FOLEY_MW20_BRYAR_PISTOL_SWITCH_FIRE_MODE.get();
            case "bt_x42_flamethrower", "k21c_portable_ordanance_launcher", "lightbow", "minimag_proton_torpedo_launcher",
                 "opressor_flamethrower", "plx1_missle_launcher", "rps6_rocket_launcher", "smart_rocket", "v6d_mortar_launcher",
                 "vulk_tau623_rotary", "z6_rotary", "eweb", "ewhb12", "m32", "m45", "m55", "m61", "mwc35c", "t21", "t21b",
                 "bm107", "galar90":
                return ModSounds.FOLEY_LARGE_SWITCH_FIRE_MODE.get();
            case "_434_deathhammer", "a140", "ac177", "apache", "astra40", "b22", "be09", "bh4", "blurrg1120",
                 "boonta_blaster", "br14", "c10", "c96", "caij_vandas_blaster_pistol", "cc420", "cr2", "cs14", "dc15s_sidearm",
                 "dc17", "de10", "dg29", "dh16", "dh17", "dh23", "dl18", "dl21", "dt12", "dt15", "dt29", "dx13",
                 "ec17", "elg3a", "flintloq_pistol", "fn57", "fp45", "gl77", "hf94", "ib94", "k16_bryar_pistol", "koch9s",
                 "krie4", "kueget_ln21", "kyd21", "leucht42", "ll30", "lug_po8", "lw896", "m19a1", "marg_mcm", "model_57",
                 "nambu14", "p38", "pcc_projector", "power_5", "premier", "q2", "relby_k23", "renegade", "rg4d", "rig420", "rk3",
                 "rskf44", "ruger", "s195", "s5", "sacros_k11", "satines_lament", "sc_x30", "se14c", "serexim_mk_5",
                 "sedgleys_mk_5", "shard3a", "sk32", "snubble", "steyr43", "t6", "tca_pro", "type14", "umbaran_pistol",
                 "walther_blaster", "walther_lpm_blaster", "webly_s4", "webtemp", "weequay_pistol", "westar_20", "westar_34",
                 "westar_35", "x8_night_sniper", "panic_pistol", "verpine_shatter":
                return ModSounds.FOLEY_SMALL_SWITCH_FIRE_MODE.get();
            default:
                return ModSounds.FOLEY_MEDIUM_SWITCH_FIRE_MODE.get();
        }
    }

    public static SoundEvent getBlasterEquip(String blasterName) {
        Random random = new Random();
        switch (blasterName) {
            case "amban_disruptor":
                return ModSounds.FOLEY_AMBAN_DISRUPTOR_SNIPER_RIFLE_EQUIP.get();
            case "ib94":
                return ModSounds.FOLEY_IB94_EQUIP.get();
            case "bt_x42_flamethrower", "k21c_portable_ordanance_launcher", "lightbow", "minimag_proton_torpedo_launcher",
                 "opressor_flamethrower", "plx1_missle_launcher", "rps6_rocket_launcher", "smart_rocket", "v6d_mortar_launcher",
                 "vulk_tau623_rotary", "z6_rotary", "eweb", "ewhb12", "m32", "m45", "m55", "m61", "mwc35c", "t21", "t21b",
                 "bm107", "galar90":
                return ModSounds.FOLEY_LARGE_EQUIP.get();
            case "_434_deathhammer", "a140", "a180", "ac177", "apache", "astra40", "b22", "be09", "bh4", "blurrg1120",
                 "boonta_blaster", "br14", "c10", "c96", "caij_vandas_blaster_pistol", "cc420", "cr2", "cs14", "dc15s_sidearm",
                 "dc17", "de10", "dg29", "dh16", "dh17", "dh23", "dl18", "dl21", "dl44", "dt12", "dt15", "dt29", "dx13",
                 "ec17", "elg3a", "flintloq_pistol", "fn57", "fp45", "gl77", "hf94", "k16_bryar_pistol", "koch9s",
                 "krie4", "kueget_ln21", "kyd21", "leucht42", "ll30", "lug_po8", "lw896", "m19a1", "marg_mcm", "model_57",
                 "nambu14", "p38", "pcc_projector", "power_5", "premier", "q2", "relby_k23", "renegade", "rg4d", "rig420", "rk3",
                 "rskf44", "ruger", "s195", "s5", "sacros_k11", "satines_lament", "sc_x30", "se14c", "serexim_mk_5",
                 "sedgleys_mk_5", "shard3a", "sk32", "snubble", "steyr43", "t6", "tca_pro", "type14", "umbaran_pistol",
                 "walther_blaster", "walther_lpm_blaster", "webly_s4", "webtemp", "weequay_pistol", "westar_20", "westar_34",
                 "westar_35", "x8_night_sniper", "panic_pistol", "verpine_shatter", "mw20_bryar_pistol", "b1na":
                if (random.nextInt(60) == 0) {
                    return ModSounds.FOLEY_SMALL_FLORISH_EQUIP.get();
                } else if (random.nextInt(20) == 0){
                    return ModSounds.FOLEY_SMALL_QUICK_EQUIP.get();
                } else {
                    return ModSounds.FOLEY_SMALL_EQUIP.get();
                }
            case "barmst12", "blndrbus", "ca87", "flite37", "sx21", "vanguard_scatter", "winchester87":
                return ModSounds.FOLEY_SCATTER_SHOT_EQUIP.get();
            default:
                return ModSounds.FOLEY_MEDIUM_EQUIP.get();
        }
    }

    public static SoundEvent getBlasterUnequip(String blasterName) {
        Random random = new Random();
        switch (blasterName) {
            case "bt_x42_flamethrower", "k21c_portable_ordanance_launcher", "lightbow", "minimag_proton_torpedo_launcher",
                 "opressor_flamethrower", "plx1_missle_launcher", "rps6_rocket_launcher", "smart_rocket", "v6d_mortar_launcher",
                 "vulk_tau623_rotary", "z6_rotary", "eweb", "ewhb12", "m32", "m45", "m55", "m61", "mwc35c", "t21", "t21b",
                 "bm107", "galar90":
                return ModSounds.FOLEY_LARGE_UNEQUIP.get();
            case "_434_deathhammer", "a140", "a180", "ac177", "apache", "astra40", "b22", "be09", "bh4", "blurrg1120",
                 "boonta_blaster", "br14", "c10", "c96", "caij_vandas_blaster_pistol", "cc420", "cr2", "cs14", "dc15s_sidearm",
                 "dc17", "de10", "dg29", "dh16", "dh17", "dh23", "dl18", "dl21", "dl44", "dt12", "dt15", "dt29", "dx13",
                 "ec17", "elg3a", "flintloq_pistol", "fn57", "fp45", "gl77", "hf94", "k16_bryar_pistol", "koch9s",
                 "krie4", "kueget_ln21", "kyd21", "leucht42", "ll30", "lug_po8", "lw896", "m19a1", "marg_mcm", "model_57",
                 "nambu14", "p38", "pcc_projector", "power_5", "premier", "q2", "relby_k23", "renegade", "rg4d", "rig420", "rk3",
                 "rskf44", "ruger", "s195", "s5", "sacros_k11", "satines_lament", "sc_x30", "se14c", "serexim_mk_5",
                 "sedgleys_mk_5", "shard3a", "sk32", "snubble", "steyr43", "t6", "tca_pro", "type14", "umbaran_pistol",
                 "walther_blaster", "walther_lpm_blaster", "webly_s4", "webtemp", "weequay_pistol", "westar_20", "westar_34",
                 "westar_35", "x8_night_sniper", "panic_pistol", "verpine_shatter", "mw20_bryar_pistol", "b1na":
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
