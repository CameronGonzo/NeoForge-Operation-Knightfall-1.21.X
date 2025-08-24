package net.uhhitscam.knightfall.util;

import net.minecraft.sounds.SoundEvent;
import net.uhhitscam.knightfall.item.custom.WeaponClassification;
import net.uhhitscam.knightfall.item.custom.WeaponName;
import net.uhhitscam.knightfall.item.custom.FiringMode;
import net.uhhitscam.knightfall.sound.ModSounds;

import java.util.Random;

public class WeaponSoundsUtil {
    public static SoundEvent getWeaponFireSound(WeaponName blasterName, FiringMode blasterFireMode) {
        if (blasterFireMode.equals(FiringMode.STUN) && !blasterName.equals(WeaponName.CA87)) {
            if (blasterName.equals(WeaponName.EC17)) {
                return ModSounds.EC17_STUN_FIRE.get();
            } else {
                return ModSounds.STUN_FIRE.get();
            }
        } else if (blasterName.equals(WeaponName.A180)) {
            return switch (blasterFireMode) {
                case FiringMode.FULL_AUTO -> ModSounds.A180_RIFLE_FIRE.get();
                case FiringMode.SNIPER -> ModSounds.A180_SNIPER_FIRE.get();
                default -> ModSounds.A180_PISTOL_FIRE.get();
            };
        } else if (blasterName.equals(WeaponName.A280CFE)) {
            return switch (blasterFireMode) {
                case FiringMode.BURST -> ModSounds.A280CFE_RIFLE_FIRE.get();
                case FiringMode.SNIPER -> ModSounds.A280CFE_SNIPER_FIRE.get();
                default -> ModSounds.A280CFE_PISTOL_FIRE.get();
            };
        } else if (blasterName.equals(WeaponName.BLNDRBUS)) {
            if (blasterFireMode.equals(FiringMode.REPULSE)) {
                return ModSounds.BLNDRBUS_REPULSE_FIRE.get();
            } else {
                return ModSounds.BLNDRBUS_FIRE.get();
            }
        } else if (blasterName.equals(WeaponName.BOWCASTER)) {
            if (blasterFireMode.equals(FiringMode.CHARGENSHOOT)) {
                return ModSounds.BOWCASTER_CHARGED_FIRE.get();
            } else {
                return ModSounds.BOWCASTER_FIRE.get();
            }
        } else if (blasterName.equals(WeaponName.BRYAR_RIFLE)) {
            if (blasterFireMode.equals(FiringMode.CHARGENSHOOTONRELEASE)) {
                return ModSounds.BRYAR_RIFLE_CHARGED_FIRE.get();
            } else {
                return ModSounds.BRYAR_RIFLE_FIRE.get();
            }
        } else if (blasterName.equals(WeaponName.BM107)) {
            if (blasterFireMode.equals(FiringMode.CHARGENSHOOT)) {
                return ModSounds.BM107_CHARGED_FIRE.get();
            } else {
                return ModSounds.BM107_FIRE.get();
            }
        } else if (blasterName.equals(WeaponName.C10)) {
            if (blasterFireMode.equals(FiringMode.CHARGENSHOOTONRELEASE)) {
                return ModSounds.C10_CHARGED_FIRE.get();
            } else {
                return ModSounds.C10_FIRE.get();
            }
        } else if (blasterName.equals(WeaponName.CA87)) {
            return switch (blasterFireMode) {
                case FiringMode.REPULSE -> ModSounds.CA87_REPULSE_FIRE.get();
                case FiringMode.STUN -> ModSounds.CA87_STUN_FIRE.get();
                default -> ModSounds.CA87_FIRE.get();
            };
        } else if (blasterName.equals(WeaponName.MOTTO_MK_4)) {
            if (blasterFireMode.equals(FiringMode.REPULSE)) {
                return ModSounds.MOTTO_MK_4_REPULSE_FIRE.get();
            } else {
                return ModSounds.MOTTO_MK_4_FIRE.get();
            }
        } else if (blasterName.equals(WeaponName.CAIJ_VANDAS_BLASTER_PISTOL)) {
            if (blasterFireMode.equals(FiringMode.CHARGENSHOOT)) {
                return ModSounds.CAIJ_VANDAS_BLASTER_PISTOL_CHARGED_FIRE.get();
            } else {
                return ModSounds.CAIJ_VANDAS_BLASTER_PISTOL_FIRE.get();
            }
        } else if (blasterName.equals(WeaponName.DC15X)) {
            if (blasterFireMode.equals(FiringMode.CHARGENSHOOTONRELEASE)) {
                return ModSounds.DC15X_CHARGED_FIRE.get();
            } else {
                return ModSounds.DC15X_FIRE.get();
            }
        } else if (blasterName.equals(WeaponName.DC17M)) {
            return switch (blasterFireMode) {
                case FiringMode.LAUNCHER -> ModSounds.DC17M_LAUNCHER_FIRE.get();
                case FiringMode.CHARGENSHOOT -> ModSounds.DC17M_CHARGED_FIRE.get();
                case FiringMode.SNIPER -> ModSounds.DC17M_SNIPER_FIRE.get();
                default -> ModSounds.DC17M_FIRE.get();
            };
        } else if (blasterName.equals(WeaponName.DL44)) {
            if (blasterFireMode.equals(FiringMode.SNIPER)) {
                return ModSounds.DL44_SNIPER_FIRE.get();
            } else {
                return ModSounds.DL44_FIRE.get();
            }
        } else if (blasterName.equals(WeaponName.DLT20A)) {
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
        } else if (blasterName.equals(WeaponName.DP23)) {
            if (blasterFireMode.equals(FiringMode.CHARGENSHOOT)) {
                return ModSounds.DP23_CHARGED_FIRE.get();
            } else {
                return ModSounds.DP23_FIRE.get();
            }
        } else if (blasterName.equals(WeaponName.JND41)) {
            if (blasterFireMode.equals(FiringMode.CHARGENSHOOT)) {
                return ModSounds.JND41_CHARGED_FIRE.get();
            } else {
                return ModSounds.JND41_FIRE.get();
            }
        } else if (blasterName.equals(WeaponName.K16_BRYAR_PISTOL)) {
            if (blasterFireMode.equals(FiringMode.CHARGENSHOOTONRELEASE)) {
                return ModSounds.K16_BRYAR_PISTOL_CHARGED_FIRE.get();
            } else {
                return ModSounds.K16_BRYAR_PISTOL_FIRE.get();
            }
        } else if (blasterName.equals(WeaponName.MW20_BRYAR_PISTOL)) {
            if (blasterFireMode.equals(FiringMode.CHARGENSHOOTONRELEASE)) {
                return ModSounds.MW20_BRYAR_PISTOL_CHARGED_FIRE.get();
            } else if (blasterFireMode.equals(FiringMode.SNIPER)) {
                return ModSounds.MW20_BRYAR_SNIPER_FIRE.get();
            } else {
                return ModSounds.MW20_BRYAR_PISTOL_FIRE.get();
            }
        } else if (blasterName.equals(WeaponName.NEO_CRUSADER_RIFLE)) {
            if (blasterFireMode.equals(FiringMode.CHARGENSHOOT)) {
                return ModSounds.NEO_CRUSADER_RIFLE_CHARGED_FIRE.get();
            } else {
                return ModSounds.NEO_CRUSADER_RIFLE_FIRE.get();
            }
        } else if (blasterName.equals(WeaponName.NIGHT_STINGER)) {
            if (blasterFireMode.equals(FiringMode.CHARGENSHOOTONRELEASE)) {
                return ModSounds.NIGHT_STINGER_CHARGED_FIRE.get();
            } else {
                return ModSounds.NIGHT_STINGER_FIRE.get();
            }
        } else if (blasterName.equals(WeaponName.POWER_5)) {
            if (blasterFireMode.equals(FiringMode.CHARGENSHOOTONRELEASE)) {
                return ModSounds.POWER_5_CHARGED_FIRE.get();
            } else {
                return ModSounds.POWER_5_FIRE.get();
            }
        } else if (blasterName.equals(WeaponName.RELBY_V10)) {
            return switch (blasterFireMode) {
                case FiringMode.LAUNCHER -> ModSounds.RELBY_V10_LAUNCHER_FIRE.get();
                case FiringMode.CHARGENSHOOTONRELEASE -> ModSounds.RELBY_V10_CHARGED_FIRE.get();
                default -> ModSounds.RELBY_V10_FIRE.get();
            };
        } else if (blasterName.equals(WeaponName.SATINES_LAMENT)) {
            if (blasterFireMode.equals(FiringMode.CHARGENSHOOT)) {
                return ModSounds.SATINES_LAMENT_CHARGED_FIRE.get();
            } else {
                return ModSounds.SATINES_LAMENT_FIRE.get();
            }
        } else if (blasterName.equals(WeaponName.SHADOW_TROOPER_BLASTER)) {
            if (blasterFireMode.equals(FiringMode.CHARGENSHOOT)) {
                return ModSounds.SHADOW_TROOPER_BLASTER_CHARGED_FIRE.get();
            } else {
                return ModSounds.SHADOW_TROOPER_BLASTER_FIRE.get();
            }
        } else if (blasterName.equals(WeaponName.SK32)) {
            if (blasterFireMode.equals(FiringMode.CHARGENSHOOT)) {
                return ModSounds.SK32_CHARGED_FIRE.get();
            } else {
                return ModSounds.SK32_FIRE.get();
            }
        } else if (blasterName.equals(WeaponName.TL50)) {
            if (blasterFireMode.equals(FiringMode.CHARGENSHOOT)) {
                return ModSounds.TL50_LAUNCHER_FIRE.get();
            } else {
                return ModSounds.TL50_FIRE.get();
            }
        }

        return switch (blasterName) {
            case WeaponName._62AUG2_HUNTING_RIFLE -> ModSounds._62AUG2_HUNTING_RIFLE_FIRE.get();
            case WeaponName._84U_HUNTING_RIFLE -> ModSounds._84U_HUNTING_RIFLE_FIRE.get();
            case WeaponName._434_DEATHHAMMER -> ModSounds._434_DEATHHAMMER_FIRE.get();
            case WeaponName._773_FIREPUNCHER -> ModSounds._773_FIREPUNCHER_FIRE.get();
            case WeaponName._785MK_FIREPUNCHERX -> ModSounds._785MK_FIREPUNCHERX_FIRE.get();
            case WeaponName.A140 -> ModSounds.A140_FIRE.get();
            case WeaponName.A280 -> ModSounds.A280_FIRE.get();
            case WeaponName.A280C -> ModSounds.A280C_FIRE.get();
            case WeaponName.A295 -> ModSounds.A295_FIRE.get();
            case WeaponName.A300 -> ModSounds.A300_FIRE.get();
            case WeaponName.A310 -> ModSounds.A310_FIRE.get();
            case WeaponName.A350 -> ModSounds.A350_FIRE.get();
            case WeaponName.AC177 -> ModSounds.AC177_FIRE.get();
            case WeaponName.ACP_REPEATER -> ModSounds.ACP_REPEATER_FIRE.get();
            case WeaponName.AKBARC -> ModSounds.AKBARC_FIRE.get();
            case WeaponName.AMBAN_DISRUPTOR -> ModSounds.AMBAN_DISRUPTOR_FIRE.get();
            case WeaponName.APACHE -> ModSounds.APACHE_FIRE.get();
            case WeaponName.ASTRA40 -> ModSounds.ASTRA40_FIRE.get();
            case WeaponName.AVARIK -> ModSounds.AVARIK_FIRE.get();
            case WeaponName.B1NA -> ModSounds.B1NA_FIRE.get();
            case WeaponName.B22 -> ModSounds.B22_FIRE.get();
            case WeaponName.BALNAB -> ModSounds.BALNAB_FIRE.get();
            case WeaponName.BARMST12 -> ModSounds.BARMST12_FIRE.get();
            case WeaponName.BATON_BLASTER -> ModSounds.BATON_BLASTER_FIRE.get();
            case WeaponName.BE09 -> ModSounds.BE09_FIRE.get();
            case WeaponName.BE29 -> ModSounds.BE29_FIRE.get();
            case WeaponName.BERSERKER -> ModSounds.BERSERKER_FIRE.get();
            case WeaponName.BH4 -> ModSounds.BH4_FIRE.get();
            case WeaponName.BLASTER_SPEAR -> ModSounds.BLASTER_SPEAR_FIRE.get();
            case WeaponName.BLURRG1120 -> ModSounds.BLURRG1120_FIRE.get();
            case WeaponName.BOILER_RIFLE -> ModSounds.BOILER_RIFLE_FIRE.get();
            case WeaponName.BOONTA_BLASTER -> ModSounds.BOONTA_BLASTER_FIRE.get();
            case WeaponName.BR14 -> ModSounds.BR14_FIRE.get();
//            case BlasterName.BT_X42_FLAMETHROWER -> ModSounds.BT_X42_FLAMETHROWER_FIRE.get();
            case WeaponName.C96 -> ModSounds.C96_FIRE.get();
            case WeaponName.CC420 -> ModSounds.CC420_FIRE.get();
            case WeaponName.CJ9_BO_RIFLE -> ModSounds.CJ9_BO_RIFLE_FIRE.get();
            case WeaponName.CORPO_RIFLE -> ModSounds.CORPO_RIFLE_FIRE.get();
            case WeaponName.CR2 -> ModSounds.CR2_FIRE.get();
            case WeaponName.CS14 -> ModSounds.CS14_FIRE.get();
            case WeaponName.CYCLER_RIFLE -> ModSounds.CYCLER_RIFLE_FIRE.get();
            case WeaponName.CZERKA_ADVENTURER -> ModSounds.CZERKA_ADVENTURER_FIRE.get();
            case WeaponName.DARK_TROOPER_RIFLE -> ModSounds.DARK_TROOPER_RIFLE_FIRE.get();
            case WeaponName.DC12U -> ModSounds.DC12U_FIRE.get();
            case WeaponName.DC15A -> ModSounds.DC15A_FIRE.get();
            case WeaponName.DC15LE -> ModSounds.DC15LE_FIRE.get();
            case WeaponName.DC15S_CARBINE -> ModSounds.DC15S_CARBINE_FIRE.get();
            case WeaponName.DC15S_SIDEARM -> ModSounds.DC15S_SIDEARM_FIRE.get();
            case WeaponName.DC17 -> ModSounds.DC17_FIRE.get();
            case WeaponName.DC17S -> ModSounds.DC17S_HAND_BLASTER_FIRE.get();
            case WeaponName.DC19 -> ModSounds.DC19_FIRE.get();
            case WeaponName.DE10 -> ModSounds.DE10_FIRE.get();
            case WeaponName.DEFTECH -> ModSounds.DEFTECH_FIRE.get();
            case WeaponName.DFD1 -> ModSounds.DFD1_FIRE.get();
            case WeaponName.DG29 -> ModSounds.DG29_FIRE.get();
            case WeaponName.DH16 -> ModSounds.DH16_FIRE.get();
            case WeaponName.DH17 -> ModSounds.DH17_FIRE.get();
            case WeaponName.DH23 -> ModSounds.DH23_FIRE.get();
            case WeaponName.DH447 -> ModSounds.DH447_FIRE.get();
            case WeaponName.DL18 -> ModSounds.DL18_FIRE.get();
            case WeaponName.DL21 -> ModSounds.DL21_FIRE.get();
            case WeaponName.DLS12 -> ModSounds.DLS12_FIRE.get();
            case WeaponName.DLT18 -> ModSounds.DLT18_FIRE.get();
            case WeaponName.DLT19 -> ModSounds.DLT19_FIRE.get();
            case WeaponName.DLT19D -> ModSounds.DLT19D_FIRE.get();
            case WeaponName.DLT19X -> ModSounds.DLT19X_FIRE.get();
            case WeaponName.DN_BOLT_CASTER -> ModSounds.DN_BOLT_CASTER_FIRE.get();
            case WeaponName.DRESSELLIAN_PROJECTILE_RIFLE -> ModSounds.DRESSELLIAN_PROJECTILE_RIFLE_FIRE.get();
            case WeaponName.DT12 -> ModSounds.DT12_FIRE.get();
            case WeaponName.DT15 -> ModSounds.DT15_FIRE.get();
            case WeaponName.DT29 -> ModSounds.DT29_FIRE.get();
            case WeaponName.DT57 -> ModSounds.DT57_FIRE.get();
            case WeaponName.DX13 -> ModSounds.DX13_FIRE.get();
            case WeaponName.E5 -> ModSounds.E5_FIRE.get();
            case WeaponName.E5_BX -> ModSounds.E5_BX_FIRE.get();
            case WeaponName.E5_CE -> ModSounds.E5_CE_FIRE.get();
            case WeaponName.E5C -> ModSounds.E5C_FIRE.get();
            case WeaponName.E5S -> ModSounds.E5S_FIRE.get();
            case WeaponName.E10 -> ModSounds.E10_FIRE.get();
            case WeaponName.E10_5 -> ModSounds.E10_5_FIRE.get();
            case WeaponName.E10R -> ModSounds.E10R_FIRE.get();
            case WeaponName.E11_CARBINE -> ModSounds.E11_CARBINE_FIRE.get();
            case WeaponName.E11_RIFLE -> ModSounds.E11_RIFLE_FIRE.get();
            case WeaponName.E11B -> ModSounds.E11B_FIRE.get();
            case WeaponName.E11D -> ModSounds.E11D_FIRE.get();
            case WeaponName.E11S -> ModSounds.E11S_FIRE.get();
            case WeaponName.E17D -> ModSounds.E17D_FIRE.get();
            case WeaponName.E22 -> ModSounds.E22_FIRE.get();
            case WeaponName.EC17 -> ModSounds.EC17_FIRE.get();
            case WeaponName.EE3 -> ModSounds.EE3_FIRE.get();
            case WeaponName.EE4 -> ModSounds.EE4_FIRE.get();
            case WeaponName.ELG3A -> ModSounds.ELG3A_FIRE.get();
            case WeaponName.ENERGY_BOW -> ModSounds.ENERGY_BOW_FIRE.get();
            case WeaponName.ENERGY_CROSSBOW -> ModSounds.ENERGY_CROSSBOW_FIRE.get();
            case WeaponName.EWEB -> ModSounds.EWEB_FIRE.get();
            case WeaponName.FC1_FLECHETTE_LAUNCHER -> ModSounds.FC1_FLECHETTE_LAUNCHER_FIRE.get();
            case WeaponName.FLINTLOQ_PISTOL -> ModSounds.FLINTLOQ_PISTOL_FIRE.get();
            case WeaponName.FLINTLOQ_RIFLE -> ModSounds.FLINTLOQ_RIFLE_FIRE.get();
            case WeaponName.FLITE37 -> ModSounds.FLITE37_FIRE.get();
            case WeaponName.FN57 -> ModSounds.FN57_FIRE.get();
            case WeaponName.FP45 -> ModSounds.FP45_FIRE.get();
            case WeaponName.GALAAR15 -> ModSounds.GALAAR15_FIRE.get();
            case WeaponName.GALAR90 -> ModSounds.GALAR90_FIRE.get();
            case WeaponName.GE36 -> ModSounds.GE36_FIRE.get();
            case WeaponName.GL77 -> ModSounds.GL77_FIRE.get();
            case WeaponName.HF94 -> ModSounds.HF94_FIRE.get();
            case WeaponName.IB94 -> ModSounds.IB94_FIRE.get();
            case WeaponName.IMPERIAL_SUPERCOMMANDO_BLASTER -> ModSounds.IMPERIAL_SUPERCOMMANDO_BLASTER_FIRE.get();
            case WeaponName.IQA11 -> ModSounds.IQA11_FIRE.get();
            case WeaponName.JEZALI_CYCLER_RIFLE -> ModSounds.JEZALI_CYCLER_RIFLE_FIRE.get();
            case WeaponName.K21C_PORTABLE_ORDANANCE_LAUNCHER -> ModSounds.K21C_PORTABLE_ORDANANCE_LAUNCHER_FIRE.get();
            case WeaponName.KA74 -> ModSounds.KA74_FIRE.get();
            case WeaponName.KISTEER_1284 -> ModSounds.KISTEER_1284_FIRE.get();
            case WeaponName.KOCH9S -> ModSounds.KOCH9S_FIRE.get();
            case WeaponName.KRIE4 -> ModSounds.KRIE4_FIRE.get();
            case WeaponName.KUEGET_LN21 -> ModSounds.KUEGET_LN21.get();
            case WeaponName.KYD21 -> ModSounds.KYD21_FIRE.get();
            case WeaponName.L5 -> ModSounds.L5_FIRE.get();
            case WeaponName.L60 -> ModSounds.L60_FIRE.get();
            case WeaponName.LEUCHT42 -> ModSounds.LEUCHT42_FIRE.get();
            case WeaponName.LIGHTBOW -> ModSounds.LIGHTBOW_FIRE.get();
            case WeaponName.LL30 -> ModSounds.LL30_FIRE.get();
            case WeaponName.LUG_PO8 -> ModSounds.LUG_PO8_FIRE.get();
            case WeaponName.LW896 -> ModSounds.LW896_FIRE.get();
            case WeaponName.M12 -> ModSounds.M12_FIRE.get();
            case WeaponName.M19A1 -> ModSounds.M19A1_FIRE.get();
            case WeaponName.M32 -> ModSounds.M32_FIRE.get();
            case WeaponName.M41 -> ModSounds.M41_FIRE.get();
            case WeaponName.M45 -> ModSounds.M45_FIRE.get();
            case WeaponName.M55 -> ModSounds.M55_FIRE.get();
            case WeaponName.M61 -> ModSounds.M61_FIRE.get();
            case WeaponName.MARG_MCM -> ModSounds.MARG_MCM_FIRE.get();
            case WeaponName.MINIMAG_PROTON_TORPEDO_LAUNCHER -> ModSounds.MINIMAG_PROTON_TORPEDO_LAUNCHER_FIRE.get();
            case WeaponName.MK_II_PALADIN -> ModSounds.MK_II_PALADIN_FIRE.get();
            case WeaponName.MODEL_57 -> ModSounds.MODEL_57_FIRE.get();
//            case BlasterName.MORTAR -> ModSounds.MORTAR_FIRE.get();
            case WeaponName.MWC35C -> ModSounds.MWC35C_FIRE.get();
            case WeaponName.NAMBU14 -> ModSounds.NAMBU14_FIRE.get();
            case WeaponName.NIGHT_WIND_RIFLE -> ModSounds.NIGHT_WIND_RIFLE_FIRE.get();
            case WeaponName.NT242 -> ModSounds.NT242_FIRE.get();
            case WeaponName.OK98 -> ModSounds.OK98_FIRE.get();
//            case BlasterName.OPRESSOR_FLAMETHROWER -> ModSounds.OPRESSOR_FLAMETHROWER_FIRE.get();
            case WeaponName.OUTLAND_RIFLE -> ModSounds.OUTLAND_RIFLE_FIRE.get();
            case WeaponName.P38 -> ModSounds.P38_FIRE.get();
            case WeaponName.PANIC_PISTOL -> ModSounds.PANIC_PISTOL_FIRE.get();
            case WeaponName.PCC_PROJECTOR -> ModSounds.PCC_PROJECTOR_FIRE.get();
            case WeaponName.PK23 -> ModSounds.PK23_FIRE.get();
//            case BlasterName.PLX1_MISSLE_LAUNCHER -> ModSounds.PLX1_MISSLE_LAUNCHER_FIRE.get();
            case WeaponName.PREMIER -> ModSounds.PREMIER_FIRE.get();
            case WeaponName.Q2 -> ModSounds.Q2_FIRE.get();
            case WeaponName.QUARREN_RIFLE -> ModSounds.QUARREN_RIFLE_FIRE.get();
            case WeaponName.RELBY_K23 -> ModSounds.RELBY_K23_FIRE.get();
            case WeaponName.RENEGADE -> ModSounds.RENEGADE_FIRE.get();
            case WeaponName.RG4D -> ModSounds.RG4D_FIRE.get();
            case WeaponName.RIG420 -> ModSounds.RIG420_FIRE.get();
            case WeaponName.RK3 -> ModSounds.RK3_FIRE.get();
//            case BlasterName.RPS6_ROCKET_LAUNCHER -> ModSounds.RPS6_ROCKET_LAUNCHER_FIRE.get();
            case WeaponName.RSKF44 -> ModSounds.RSKF44_FIRE.get();
            case WeaponName.RT97C -> ModSounds.RT97C_FIRE.get();
            case WeaponName.RUGER_BLASTER-> ModSounds.RUGER_BLASTER_FIRE.get();
            case WeaponName.S5 -> ModSounds.S5_FIRE.get();
            case WeaponName.S195-> ModSounds.S195_FIRE.get();
            case WeaponName.SACROS_K11 -> ModSounds.SACROS_K11_FIRE.get();
            case WeaponName.SE14C -> ModSounds.SE14C_FIRE.get();
            case WeaponName.SE14R -> ModSounds.SE14R_FIRE.get();
            case WeaponName.SEDGLEYS_MK_5 -> ModSounds.SEDGLEYS_MK_5_FIRE.get();
            case WeaponName.SEREXIM_MK_5 -> ModSounds.SEREXIM_MK_5_FIRE.get();
            case WeaponName.SHARD3A -> ModSounds.SHARD3A_FIRE.get();
//            case BlasterName.SMART_ROCKET -> ModSounds.SMART_ROCKET_FIRE.get();
            case WeaponName.SNUBBLE -> ModSounds.SNUBBLE_FIRE.get();
            case WeaponName.SONIC_BLASTER -> ModSounds.SONIC_BLASTER_FIRE.get();
            case WeaponName.STEYR43 -> ModSounds.STEYR43_FIRE.get();
            case WeaponName.SX21 -> ModSounds.SX21_FIRE.get();
            case WeaponName.T6 -> ModSounds.T6_FIRE.get();
            case WeaponName.T7_ION_DISRUPTOR -> ModSounds.T7_ION_DISRUPTOR_FIRE.get();
            case WeaponName.T21 -> ModSounds.T21_FIRE.get();
            case WeaponName.T21B -> ModSounds.T21B_FIRE.get();
            case WeaponName.TCA_PRO -> ModSounds.TCA_PRO_FIRE.get();
            case WeaponName.TOMSUN97 -> ModSounds.TOMSUN97_FIRE.get();
            case WeaponName.TYPE14 -> ModSounds.TYPE14_FIRE.get();
            case WeaponName.UMBARAN_PISTOL -> ModSounds.UMBARAN_PISTOL_FIRE.get();
//            case BlasterName.V6D_MORTAR_LAUNCHER -> ModSounds.V6D_MORTAR_LUANCHER_FIRE.get();
            case WeaponName.VALKEN38X -> ModSounds.VALKEN38X_FIRE.get();
            case WeaponName.VANGUARD_SCATTER -> ModSounds.VANGUARD_SCATTER_FIRE.get();
            case WeaponName.VECT_UZI -> ModSounds.VECT_UZI_FIRE.get();
            case WeaponName.VERPINE_SHATTER -> ModSounds.VERPINE_SHATTER_FIRE.get();
            case WeaponName.VULK_TAU623_ROTARY -> ModSounds.VULK_TAU623_ROTARY_FIRE.get();
            case WeaponName.WALTHER_BLASTER -> ModSounds.WALTHER_BLASTER_FIRE.get();
            case WeaponName.WALTHER_LPM_BLASTER -> ModSounds.WALTHER_LPM_BLASTER_FIRE.get();
            case WeaponName.WEBLY_S4 -> ModSounds.WEBLY_S4_FIRE.get();
            case WeaponName.WEBTEMP -> ModSounds.WEBTEMP_FIRE.get();
            case WeaponName.WEEQUAY_LANCE -> ModSounds.WEEQUAY_LANCE_FIRE.get();
            case WeaponName.WEEQUAY_PISTOL -> ModSounds.WEEQUAY_PISTOL_FIRE.get();
            case WeaponName.WEEQUAY_RIFLE -> ModSounds.WEEQUAY_RIFLE_FIRE.get();
            case WeaponName.WESTAR_20 -> ModSounds.WESTAR_20_FIRE.get();
            case WeaponName.WESTAR_34 -> ModSounds.WESTAR_34_FIRE.get();
            case WeaponName.WESTAR_35 -> ModSounds.WESTAR_35_FIRE.get();
            case WeaponName.WESTARM5 -> ModSounds.WESTARM5_FIRE.get();
            case WeaponName.WINCHESTER87 -> ModSounds.WINCHESTER87_FIRE.get();
            case WeaponName.X8_NIGHT_SNIPER -> ModSounds.X8_NIGHT_SNIPER_FIRE.get();
            case WeaponName.X30 -> ModSounds.X30_FIRE.get();
            case WeaponName.Z6_ROTARY -> ModSounds.Z6_ROTARY_FIRE.get();
            default -> ModSounds.E11_RIFLE_FIRE.get();
        };
    }

    public static SoundEvent getWeaponReloadSound(WeaponName blasterName, FiringMode blasterFireMode, WeaponClassification classification) {
        if (classification.equals(WeaponClassification.FLECHETTE)) {
            return ModSounds.FOLEY_RELOAD_FLECHETTE.get();
        } else if (classification.equals(WeaponClassification.SLUGTHROWER)) {
            return ModSounds.FOLEY_RELOAD_SLUG.get();
        }

        switch (blasterName) {
            case WeaponName.AMBAN_DISRUPTOR:
                return ModSounds.FOLEY_AMBAN_DISRUPTOR_SNIPER_RIFLE_RELOAD.get();
            case WeaponName.DC17M:
                if (blasterFireMode.equals(FiringMode.LAUNCHER)) {
                    return ModSounds.FOLEY_DC17M_LAUNCHER_RELOAD.get();
                } else {
                    return ModSounds.FOLEY_DC17M_RELOAD.get();
                }
            case WeaponName.DT29:
                return ModSounds.FOLEY_DT29_RELOAD.get();
            case WeaponName.RELBY_V10:
                if (blasterFireMode.equals(FiringMode.LAUNCHER)) {
                    return ModSounds.FOLEY_RELBY_V10_LAUNCHER_RELOAD.get();
                } else {
                    return ModSounds.FOLEY_MEDIUM_RELOAD_GAS.get();
                }
            case /*BlasterName.V6D_MORTAR_LAUNCHER, BlasterName.MORTAR,*/ WeaponName.K21C_PORTABLE_ORDANANCE_LAUNCHER:
                return ModSounds.FOLEY_LARGE_LAUNCHER_RELOAD.get();
            case WeaponName.LIGHTBOW, WeaponName.VULK_TAU623_ROTARY, WeaponName.Z6_ROTARY, WeaponName.EWEB, /*BlasterName.EWHB12,*/ WeaponName.M32, WeaponName.M45, WeaponName.M55, WeaponName.M61, WeaponName.MWC35C, WeaponName.T21, WeaponName.T21B,
                 WeaponName.BM107, WeaponName.GALAR90, WeaponName.NT242, WeaponName.GE36, WeaponName.NEO_CRUSADER_RIFLE, WeaponName.BOILER_RIFLE, WeaponName.BOWCASTER, WeaponName.T7_ION_DISRUPTOR:
                return ModSounds.FOLEY_LARGE_RELOAD_GAS.get();
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
                return ModSounds.FOLEY_SMALL_RELOAD_GAS.get();
            default:
                return ModSounds.FOLEY_MEDIUM_RELOAD_GAS.get();
        }
    }

    public static SoundEvent getWeaponSwitchFireMode(WeaponName blasterName, FiringMode blasterFireMode) {
        switch (blasterName) {
            case WeaponName.A180:
                if (blasterFireMode.equals(FiringMode.SEMI_AUTO)) {
                    return ModSounds.FOLEY_A180_PISTOL_SWITCH_FIRE_MODE.get();
                } else if (blasterFireMode.equals(FiringMode.FULL_AUTO)) {
                    return ModSounds.FOLEY_A180_RIFLE_SWITCH_FIRE_MODE.get();
                } else {
                    return ModSounds.FOLEY_A180_SNIPER_SWITCH_FIRE_MODE.get();
                }
            case WeaponName.A280CFE:
                if (blasterFireMode.equals(FiringMode.SEMI_AUTO)) {
                    return ModSounds.FOLEY_A280CFE_RIFLE_SWITCH_FIRE_MODE.get();
                } else if (blasterFireMode.equals(FiringMode.BURST)) {
                    return ModSounds.FOLEY_A280CFE_SNIPER_SWITCH_FIRE_MODE.get();
                } else {
                    return ModSounds.FOLEY_A280CFE_PISTOL_SWITCH_FIRE_MODE.get();
                }
            case WeaponName.AMBAN_DISRUPTOR:
                return ModSounds.FOLEY_AMBAN_DISRUPTOR_SNIPER_RIFLE_SWITCH_FIRE_MODE.get();
            case WeaponName.B1NA:
                return ModSounds.FOLEY_B1NA_SWITCH_FIRE_MODE.get();
            case WeaponName.DC17M:
                if (blasterFireMode.equals(FiringMode.SEMI_AUTO)) {
                    return ModSounds.FOLEY_DC17M_LAUNCHER_SWITCH_FIRE_MODE.get();
                } else if (blasterFireMode.equals(FiringMode.LAUNCHER)) {
                    return ModSounds.FOLEY_DC17M_SNIPER_SWITCH_FIRE_MODE.get();
                } else {
                    return ModSounds.FOLEY_DC17M_RIFLE_SWITCH_FIRE_MODE.get();
                }
            case WeaponName.DL44:
                return ModSounds.FOLEY_DL44_SWITCH_FIRE_MODE.get();
            case WeaponName.MW20_BRYAR_PISTOL:
                return ModSounds.FOLEY_MW20_BRYAR_PISTOL_SWITCH_FIRE_MODE.get();
            case /*BlasterName.BT_X42_FLAMETHROWER,*/ WeaponName.K21C_PORTABLE_ORDANANCE_LAUNCHER, WeaponName.LIGHTBOW, WeaponName.MINIMAG_PROTON_TORPEDO_LAUNCHER,
                 /*BlasterName.OPRESSOR_FLAMETHROWER, BlasterName.PLX1_MISSLE_LAUNCHER, BlasterName.RPS6_ROCKET_LAUNCHER, BlasterName.SMART_ROCKET, BlasterName.V6D_MORTAR_LAUNCHER,*/
                 WeaponName.VULK_TAU623_ROTARY, WeaponName.Z6_ROTARY, WeaponName.EWEB, /*BlasterName.EWHB12,*/ WeaponName.M32, WeaponName.M45, WeaponName.M55, WeaponName.M61, WeaponName.MWC35C, WeaponName.T21, WeaponName.T21B,
                                                      WeaponName.BM107, WeaponName.GALAR90:
                return ModSounds.FOLEY_LARGE_SWITCH_FIRE_MODE.get();
            case WeaponName._434_DEATHHAMMER, WeaponName.A140, WeaponName.AC177, WeaponName.APACHE, WeaponName.ASTRA40, WeaponName.B22, WeaponName.BE09, WeaponName.BH4, WeaponName.BLURRG1120,
                 WeaponName.BOONTA_BLASTER, WeaponName.BR14, WeaponName.C10, WeaponName.C96, WeaponName.CAIJ_VANDAS_BLASTER_PISTOL, WeaponName.CC420, WeaponName.CR2, WeaponName.CS14, WeaponName.DC15S_SIDEARM,
                 WeaponName.DC17, WeaponName.DE10, WeaponName.DG29, WeaponName.DH16, WeaponName.DH17, WeaponName.DH23, WeaponName.DL18, WeaponName.DL21, WeaponName.DT12, WeaponName.DT15, WeaponName.DT29, WeaponName.DX13,
                 WeaponName.EC17, WeaponName.ELG3A, WeaponName.FLINTLOQ_PISTOL, WeaponName.FN57, WeaponName.FP45, WeaponName.GL77, WeaponName.HF94, WeaponName.IB94, WeaponName.K16_BRYAR_PISTOL, WeaponName.KOCH9S,
                 WeaponName.KRIE4, WeaponName.KUEGET_LN21, WeaponName.KYD21, WeaponName.LEUCHT42, WeaponName.LL30, WeaponName.LUG_PO8, WeaponName.LW896, WeaponName.M19A1, WeaponName.MARG_MCM, WeaponName.MODEL_57,
                 WeaponName.NAMBU14, WeaponName.P38, WeaponName.PCC_PROJECTOR, WeaponName.POWER_5, WeaponName.PREMIER, WeaponName.Q2, WeaponName.RELBY_K23, WeaponName.RENEGADE, WeaponName.RG4D, WeaponName.RIG420, WeaponName.RK3,
                 WeaponName.RSKF44, WeaponName.RUGER_BLASTER, WeaponName.S195, WeaponName.S5, WeaponName.SACROS_K11, WeaponName.SATINES_LAMENT, WeaponName.SE14C, WeaponName.SEREXIM_MK_5,
                 WeaponName.SEDGLEYS_MK_5, WeaponName.SHARD3A, WeaponName.SK32, WeaponName.SNUBBLE, WeaponName.STEYR43, WeaponName.T6, WeaponName.TCA_PRO, WeaponName.TYPE14, WeaponName.UMBARAN_PISTOL,
                 WeaponName.WALTHER_BLASTER, WeaponName.WALTHER_LPM_BLASTER, WeaponName.WEBLY_S4, WeaponName.WEBTEMP, WeaponName.WEEQUAY_PISTOL, WeaponName.WESTAR_20, WeaponName.WESTAR_34,
                 WeaponName.WESTAR_35, WeaponName.X8_NIGHT_SNIPER, WeaponName.X30, WeaponName.PANIC_PISTOL, WeaponName.VERPINE_SHATTER:
                return ModSounds.FOLEY_SMALL_SWITCH_FIRE_MODE.get();
            default:
                return ModSounds.FOLEY_MEDIUM_SWITCH_FIRE_MODE.get();
        }
    }

    public static SoundEvent getWeaponCharge(WeaponName blasterName) {
        return switch (blasterName) {
            case WeaponName.BM107 -> ModSounds.BM107_CHARGE.get();
            case WeaponName.BOWCASTER -> ModSounds.BOWCASTER_CHARGE.get();
            case WeaponName.BRYAR_RIFLE -> ModSounds.BRYAR_RIFLE_CHARGE.get();
            case WeaponName.CAIJ_VANDAS_BLASTER_PISTOL -> ModSounds.CAIJ_VANDAS_BLASTER_PISTOL_CHARGE.get();
            case WeaponName.DC15X -> ModSounds.DC15X_CHARGE.get();
            case WeaponName.DC17M -> ModSounds.DC17M_CHARGE.get();
            case WeaponName.DN_BOLT_CASTER -> ModSounds.DN_BOLT_CASTER_CHARGE.get();
            case WeaponName.DP23 -> ModSounds.DP23_CHARGE.get();
            case WeaponName.JND41 -> ModSounds.JND41_CHARGE.get();
            case WeaponName.K16_BRYAR_PISTOL -> ModSounds.K16_BRYAR_PISTOL_CHARGE.get();
            case WeaponName.MW20_BRYAR_PISTOL -> ModSounds.MW20_BRYAR_PISTOL_CHARGE.get();
            case WeaponName.NEO_CRUSADER_RIFLE -> ModSounds.NEO_CRUSADER_RIFLE_CHARGE.get();
            case WeaponName.NIGHT_STINGER -> ModSounds.NIGHT_STINGER_CHARGE.get();
            case WeaponName.POWER_5 -> ModSounds.POWER_5_CHARGE.get();
            case WeaponName.RELBY_V10 -> ModSounds.RELBY_V10_CHARGE.get();
            case WeaponName.SATINES_LAMENT -> ModSounds.SATINES_LAMENT_CHARGE.get();
            case WeaponName.SHADOW_TROOPER_BLASTER -> ModSounds.SHADOW_TROOPER_BLASTER_CHARGE.get();
            case WeaponName.SK32 -> ModSounds.SK32_CHARGE.get();
            case WeaponName.T7_ION_DISRUPTOR -> ModSounds.T7_ION_DISRUPTOR_CHARGE.get();
            case WeaponName.Z6_ROTARY -> ModSounds.Z6_ROTARY_CHARGE.get();
            default -> ModSounds.TL50_CHARGE.get();
        };
    }

    public static SoundEvent getWeaponUncharge(WeaponName blasterName) {
        return switch (blasterName) {
            case WeaponName.BM107 -> ModSounds.BM107_UNCHARGE.get();
            case WeaponName.BOWCASTER -> ModSounds.BOWCASTER_UNCHARGE.get();
            case WeaponName.BRYAR_RIFLE -> ModSounds.BRYAR_RIFLE_UNCHARGE.get();
            case WeaponName.CAIJ_VANDAS_BLASTER_PISTOL -> ModSounds.CAIJ_VANDAS_BLASTER_PISTOL_UNCHARGE.get();
            case WeaponName.DC15X -> ModSounds.DC15X_UNCHARGE.get();
            case WeaponName.DC17M -> ModSounds.DC17M_UNCHARGE.get();
            case WeaponName.DN_BOLT_CASTER -> ModSounds.DN_BOLT_CASTER_UNCHARGE.get();
            case WeaponName.DP23 -> ModSounds.DP23_UNCHARGE.get();
            case WeaponName.JND41 -> ModSounds.JND41_UNCHARGE.get();
            case WeaponName.K16_BRYAR_PISTOL -> ModSounds.K16_BRYAR_PISTOL_UNCHARGE.get();
            case WeaponName.MW20_BRYAR_PISTOL -> ModSounds.MW20_BRYAR_PISTOL_UNCHARGE.get();
            case WeaponName.NEO_CRUSADER_RIFLE -> ModSounds.NEO_CRUSADER_RIFLE_UNCHARGE.get();
            case WeaponName.NIGHT_STINGER -> ModSounds.NIGHT_STINGER_UNCHARGE.get();
            case WeaponName.POWER_5 -> ModSounds.POWER_5_UNCHARGE.get();
            case WeaponName.RELBY_V10 -> ModSounds.RELBY_V10_UNCHARGE.get();
            case WeaponName.SATINES_LAMENT -> ModSounds.SATINES_LAMENT_UNCHARGE.get();
            case WeaponName.SHADOW_TROOPER_BLASTER -> ModSounds.SHADOW_TROOPER_BLASTER_UNCHARGE.get();
            case WeaponName.SK32 -> ModSounds.SK32_UNCHARGE.get();
            case WeaponName.T7_ION_DISRUPTOR -> ModSounds.T7_ION_DISRUPTOR_UNCHARGE.get();
            case WeaponName.Z6_ROTARY -> ModSounds.Z6_ROTARY_UNCHARGE.get();
            default -> ModSounds.TL50_UNCHARGE.get();
        };
    }

    public static SoundEvent getWeaponChargeLoop(WeaponName blasterName) {
        return switch (blasterName) {
            case WeaponName.DC15X -> ModSounds.DC15X_CHARGE_LOOP.get();
            case WeaponName.K16_BRYAR_PISTOL -> ModSounds.K16_BRYAR_PISTOL_CHARGE_LOOP.get();
            case WeaponName.MW20_BRYAR_PISTOL -> ModSounds.MW20_BRYAR_PISTOL_CHARGE_LOOP.get();
            default -> ModSounds.POWER_5_CHARGE_LOOP.get();
        };
    }

    public static SoundEvent getWeaponEquip(WeaponName blasterName) {
        Random random = new Random();
        switch (blasterName) {
            case WeaponName.AMBAN_DISRUPTOR:
                return ModSounds.FOLEY_AMBAN_DISRUPTOR_SNIPER_RIFLE_EQUIP.get();
            case WeaponName.IB94:
                return ModSounds.FOLEY_IB94_EQUIP.get();
            case /*BlasterName.BT_X42_FLAMETHROWER,*/ WeaponName.K21C_PORTABLE_ORDANANCE_LAUNCHER, WeaponName.LIGHTBOW, WeaponName.MINIMAG_PROTON_TORPEDO_LAUNCHER,
                 /*BlasterName.OPRESSOR_FLAMETHROWER, BlasterName.PLX1_MISSLE_LAUNCHER, BlasterName.RPS6_ROCKET_LAUNCHER, BlasterName.SMART_ROCKET, BlasterName.V6D_MORTAR_LAUNCHER,*/
                 WeaponName.VULK_TAU623_ROTARY, WeaponName.Z6_ROTARY, WeaponName.EWEB, /*BlasterName.EWHB12,*/ WeaponName.M32, WeaponName.M45, WeaponName.M55, WeaponName.M61, WeaponName.MWC35C, WeaponName.T21, WeaponName.T21B,
                                                      WeaponName.BM107, WeaponName.GALAR90:
                return ModSounds.FOLEY_LARGE_EQUIP.get();
            case WeaponName._434_DEATHHAMMER, WeaponName.A140, WeaponName.A180, WeaponName.AC177, WeaponName.APACHE, WeaponName.ASTRA40, WeaponName.B22, WeaponName.BE09, WeaponName.BH4, WeaponName.BLURRG1120,
                 WeaponName.BOONTA_BLASTER, WeaponName.BR14, WeaponName.C10, WeaponName.C96, WeaponName.CAIJ_VANDAS_BLASTER_PISTOL, WeaponName.CC420, WeaponName.CR2, WeaponName.CS14, WeaponName.DC15S_SIDEARM,
                 WeaponName.DC17, WeaponName.DE10, WeaponName.DG29, WeaponName.DH16, WeaponName.DH17, WeaponName.DH23, WeaponName.DL18, WeaponName.DL21, WeaponName.DL44, WeaponName.DT12, WeaponName.DT15, WeaponName.DT29, WeaponName.DX13,
                 WeaponName.EC17, WeaponName.ELG3A, WeaponName.FLINTLOQ_PISTOL, WeaponName.FN57, WeaponName.FP45, WeaponName.GL77, WeaponName.HF94, WeaponName.K16_BRYAR_PISTOL, WeaponName.KOCH9S,
                 WeaponName.KRIE4, WeaponName.KUEGET_LN21, WeaponName.KYD21, WeaponName.LEUCHT42, WeaponName.LL30, WeaponName.LUG_PO8, WeaponName.LW896, WeaponName.M19A1, WeaponName.MARG_MCM, WeaponName.MODEL_57,
                 WeaponName.NAMBU14, WeaponName.P38, WeaponName.PCC_PROJECTOR, WeaponName.POWER_5, WeaponName.PREMIER, WeaponName.Q2, WeaponName.RELBY_K23, WeaponName.RENEGADE, WeaponName.RG4D, WeaponName.RIG420, WeaponName.RK3,
                 WeaponName.RSKF44, WeaponName.RUGER_BLASTER, WeaponName.S195, WeaponName.S5, WeaponName.SACROS_K11, WeaponName.SATINES_LAMENT, WeaponName.SE14C, WeaponName.SEREXIM_MK_5,
                 WeaponName.SEDGLEYS_MK_5, WeaponName.SHARD3A, WeaponName.SK32, WeaponName.SNUBBLE, WeaponName.STEYR43, WeaponName.T6, WeaponName.TCA_PRO, WeaponName.TYPE14, WeaponName.UMBARAN_PISTOL,
                 WeaponName.WALTHER_BLASTER, WeaponName.WALTHER_LPM_BLASTER, WeaponName.WEBLY_S4, WeaponName.WEBTEMP, WeaponName.WEEQUAY_PISTOL, WeaponName.WESTAR_20, WeaponName.WESTAR_34,
                 WeaponName.WESTAR_35, WeaponName.X8_NIGHT_SNIPER, WeaponName.X30, WeaponName.PANIC_PISTOL, WeaponName.VERPINE_SHATTER, WeaponName.MW20_BRYAR_PISTOL, WeaponName.B1NA:
                if (random.nextInt(60) == 0) {
                    return ModSounds.FOLEY_SMALL_FLORISH_EQUIP.get();
                } else if (random.nextInt(20) == 0){
                    return ModSounds.FOLEY_SMALL_QUICK_EQUIP.get();
                } else {
                    return ModSounds.FOLEY_SMALL_EQUIP.get();
                }
            case WeaponName.BARMST12, WeaponName.BLNDRBUS, WeaponName.CA87, WeaponName.FLITE37, WeaponName.SX21, WeaponName.VANGUARD_SCATTER, WeaponName.WINCHESTER87:
                return ModSounds.FOLEY_SCATTER_SHOT_EQUIP.get();
            default:
                return ModSounds.FOLEY_MEDIUM_EQUIP.get();
        }
    }

    public static SoundEvent getWeaponUnequip(WeaponName blasterName) {
        Random random = new Random();
        switch (blasterName) {
            case /*BlasterName.BT_X42_FLAMETHROWER,*/ WeaponName.K21C_PORTABLE_ORDANANCE_LAUNCHER, WeaponName.LIGHTBOW, WeaponName.MINIMAG_PROTON_TORPEDO_LAUNCHER,
                 /*BlasterName.OPRESSOR_FLAMETHROWER, BlasterName.PLX1_MISSLE_LAUNCHER, BlasterName.RPS6_ROCKET_LAUNCHER, BlasterName.SMART_ROCKET, BlasterName.V6D_MORTAR_LAUNCHER,*/
                 WeaponName.VULK_TAU623_ROTARY, WeaponName.Z6_ROTARY, WeaponName.EWEB, /*BlasterName.EWHB12,*/ WeaponName.M32, WeaponName.M45, WeaponName.M55, WeaponName.M61, WeaponName.MWC35C, WeaponName.T21, WeaponName.T21B,
                                                      WeaponName.BM107, WeaponName.GALAR90:
                return ModSounds.FOLEY_LARGE_UNEQUIP.get();
            case WeaponName._434_DEATHHAMMER, WeaponName.A140, WeaponName.A180, WeaponName.AC177, WeaponName.APACHE, WeaponName.ASTRA40, WeaponName.B22, WeaponName.BE09, WeaponName.BH4, WeaponName.BLURRG1120,
                 WeaponName.BOONTA_BLASTER, WeaponName.BR14, WeaponName.C10, WeaponName.C96, WeaponName.CAIJ_VANDAS_BLASTER_PISTOL, WeaponName.CC420, WeaponName.CR2, WeaponName.CS14, WeaponName.DC15S_SIDEARM,
                 WeaponName.DC17, WeaponName.DE10, WeaponName.DG29, WeaponName.DH16, WeaponName.DH17, WeaponName.DH23, WeaponName.DL18, WeaponName.DL21, WeaponName.DL44, WeaponName.DT12, WeaponName.DT15, WeaponName.DT29, WeaponName.DX13,
                 WeaponName.EC17, WeaponName.ELG3A, WeaponName.FLINTLOQ_PISTOL, WeaponName.FN57, WeaponName.FP45, WeaponName.GL77, WeaponName.HF94, WeaponName.K16_BRYAR_PISTOL, WeaponName.KOCH9S,
                 WeaponName.KRIE4, WeaponName.KUEGET_LN21, WeaponName.KYD21, WeaponName.LEUCHT42, WeaponName.LL30, WeaponName.LUG_PO8, WeaponName.LW896, WeaponName.M19A1, WeaponName.MARG_MCM, WeaponName.MODEL_57,
                 WeaponName.NAMBU14, WeaponName.P38, WeaponName.PCC_PROJECTOR, WeaponName.POWER_5, WeaponName.PREMIER, WeaponName.Q2, WeaponName.RELBY_K23, WeaponName.RENEGADE, WeaponName.RG4D, WeaponName.RIG420, WeaponName.RK3,
                 WeaponName.RSKF44, WeaponName.RUGER_BLASTER, WeaponName.S195, WeaponName.S5, WeaponName.SACROS_K11, WeaponName.SATINES_LAMENT, WeaponName.SE14C, WeaponName.SEREXIM_MK_5,
                 WeaponName.SEDGLEYS_MK_5, WeaponName.SHARD3A, WeaponName.SK32, WeaponName.SNUBBLE, WeaponName.STEYR43, WeaponName.T6, WeaponName.TCA_PRO, WeaponName.TYPE14, WeaponName.UMBARAN_PISTOL,
                 WeaponName.WALTHER_BLASTER, WeaponName.WALTHER_LPM_BLASTER, WeaponName.WEBLY_S4, WeaponName.WEBTEMP, WeaponName.WEEQUAY_PISTOL, WeaponName.WESTAR_20, WeaponName.WESTAR_34,
                 WeaponName.WESTAR_35, WeaponName.X8_NIGHT_SNIPER, WeaponName.X30, WeaponName.PANIC_PISTOL, WeaponName.VERPINE_SHATTER, WeaponName.MW20_BRYAR_PISTOL, WeaponName.B1NA:
                if (random.nextInt(60) == 0) {
                    return ModSounds.FOLEY_SMALL_FLORISH_UNEQUIP.get();
                } else {
                    return ModSounds.FOLEY_SMALL_UNEQUIP.get();
                }
            default:
                return ModSounds.FOLEY_MEDIUM_UNEQUIP.get();
        }
    }

    public static SoundEvent getWeaponUnloadSound(WeaponClassification classification) {
        return switch (classification) {
            case WeaponClassification.FLECHETTE -> ModSounds.FOLEY_UNLOAD_FLECHETTE.get();
            case WeaponClassification.SLUGTHROWER -> ModSounds.FOLEY_UNLOAD_SLUG.get();
            default -> ModSounds.FOLEY_UNLOAD_GAS.get();
        };
    }
}
