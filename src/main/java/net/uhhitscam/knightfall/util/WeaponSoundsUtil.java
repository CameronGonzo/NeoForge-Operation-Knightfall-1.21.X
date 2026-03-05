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
            } else if (blasterName.equals(WeaponName.ION_STUNNER)) {
                ModSounds.ION_STUNNER_FIRE.get();
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
        } else if (blasterName.equals(WeaponName.BK28)) {
            if (blasterFireMode.equals(FiringMode.CHARGENSHOOT)) {
                return ModSounds.BK28_CHARGED_FIRE.get();
            } else {
                return ModSounds.BK28_FIRE.get();
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
        } else if (blasterName.equals(WeaponName.BX49)) {
            if (blasterFireMode.equals(FiringMode.LAUNCHER)) {
                return ModSounds.BX49_LAUNCHER_FIRE.get();
            } else {
                return ModSounds.BX49_FIRE.get();
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
        } else if (blasterName.equals(WeaponName.CHARRIC)) {
            if (blasterFireMode.equals(FiringMode.CHARGENSHOOT)) {
                return ModSounds.CHARRIC_CHARGED_FIRE.get();
            } else {
                return ModSounds.CHARRIC_FIRE.get();
            }
        } else if (blasterName.equals(WeaponName.MOTTO_MK_4)) {
            if (blasterFireMode.equals(FiringMode.REPULSE)) {
                return ModSounds.MOTTO_MK_4_REPULSE_FIRE.get();
            } else {
                return ModSounds.MOTTO_MK_4_FIRE.get();
            }
        } else if (blasterName.equals(WeaponName.LV7C)) {
            if (blasterFireMode.equals(FiringMode.CHARGENSHOOT)) {
                return ModSounds.LV7C_CHARGED_FIRE.get();
            } else {
                return ModSounds.LV7C_FIRE.get();
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
        } else if (blasterName.equals(WeaponName.GM46)) {
            if (blasterFireMode.equals(FiringMode.CHARGENSHOOT)) {
                return ModSounds.GM46_CHARGED_FIRE.get();
            } else {
                return ModSounds.GM46_FIRE.get();
            }
        } else if (blasterName.equals(WeaponName.GRS1)) {
            if (blasterFireMode.equals(FiringMode.LAUNCHER)) {
//                return ModSounds.GRS1_SPRAY_FIRE.get();
            } else {
                return ModSounds.GRS1_FIRE.get();
            }
        } else if (blasterName.equals(WeaponName.K16_BRYAR_PISTOL)) {
            if (blasterFireMode.equals(FiringMode.CHARGENSHOOTONRELEASE)) {
                return ModSounds.K16_BRYAR_PISTOL_CHARGED_FIRE.get();
            } else {
                return ModSounds.K16_BRYAR_PISTOL_FIRE.get();
            }
        } else if (blasterName.equals(WeaponName.MSD32)) {
            if (blasterFireMode.equals(FiringMode.CHARGENSHOOT)) {
                return ModSounds.MSD32_CHARGED_FIRE.get();
            } else {
                return ModSounds.MSD32_FIRE.get();
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
        } else if (blasterName.equals(WeaponName.R88)) {
            return switch (blasterFireMode) {
                case FiringMode.LAUNCHER -> ModSounds.R88_LAUNCHER_FIRE.get();
                case FiringMode.REPULSE -> ModSounds.R88_REPULSE_FIRE.get();
                default -> ModSounds.R88_FIRE.get();
            };
        } else if (blasterName.equals(WeaponName.RELBY_K25)) {
            return switch (blasterFireMode) {
                case FiringMode.CHARGENSHOOT -> ModSounds.RELBY_K25_CHARGED_FIRE.get();
                case FiringMode.BURST -> ModSounds.RELBY_K25_RIFLE_FIRE.get();
                default -> ModSounds.RELBY_K25_PISTOL_FIRE.get();
            };
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
        } else if (blasterName.equals(WeaponName.E9V)) {
            if (blasterFireMode.equals(FiringMode.CHARGENSHOOT)) {
                return ModSounds.E9V_CHARGED_FIRE.get();
            } else {
                return ModSounds.E9V_FIRE.get();
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
        } else if (blasterName.equals(WeaponName.WOOKIE_RIFLE)) {
            if (blasterFireMode.equals(FiringMode.CHARGENSHOOT)) {
                return ModSounds.WOOKIE_RIFLE_CHARGED_FIRE.get();
            } else {
                return ModSounds.WOOKIE_RIFLE_FIRE.get();
            }
        } else if (blasterName.equals(WeaponName.WOOKIE_SIDEARM)) {
            if (blasterFireMode.equals(FiringMode.CHARGENSHOOT)) {
                return ModSounds.WOOKIE_SIDEARM_CHARGED_FIRE.get();
            } else {
                return ModSounds.WOOKIE_SIDEARM_FIRE.get();
            }
        } else if (blasterName.equals(WeaponName.ZYGERRIAN_BLASTER)) {
            if (blasterFireMode.equals(FiringMode.CHARGENSHOOT)) {
                return ModSounds.ZYGERRIAN_BLASTER_CHARGED_FIRE.get();
            } else {
                return ModSounds.ZYGERRIAN_BLASTER_FIRE.get();
            }
        }

        return switch (blasterName) {
            case WeaponName._62AUG2_HUNTING_RIFLE -> ModSounds._62AUG2_HUNTING_RIFLE_FIRE.get();
            case WeaponName._22T4 -> ModSounds._22T4_FIRE.get();
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
            case WeaponName.AB75_BO_RIFLE -> ModSounds.AB75_BO_RIFLE_FIRE.get();
            case WeaponName.ABR2_ZATO -> ModSounds.ABR2_ZATO_FIRE.get();
            case WeaponName.AC177 -> ModSounds.AC177_FIRE.get();
            case WeaponName.ACP_REPEATER -> ModSounds.ACP_REPEATER_FIRE.get();
            case WeaponName.ACP_ARRAY -> ModSounds.ACP_ARRAY_FIRE.get();
            case WeaponName.BK43 -> ModSounds.BK43_FIRE.get();
            case WeaponName.AMBAN_DISRUPTOR -> ModSounds.AMBAN_DISRUPTOR_FIRE.get();
            case WeaponName.APACHE -> ModSounds.APACHE_FIRE.get();
            case WeaponName.ASTRA40 -> ModSounds.ASTRA40_FIRE.get();
            case WeaponName.AVARIK -> ModSounds.AVARIK_FIRE.get();
            case WeaponName.B1NA -> ModSounds.B1NA_FIRE.get();
            case WeaponName.B1X -> ModSounds.B1X_FIRE.get();
            case WeaponName.B22 -> ModSounds.B22_FIRE.get();
            case WeaponName.B33 -> ModSounds.B33_FIRE.get();
            case WeaponName.BALNAB -> ModSounds.BALNAB_FIRE.get();
            case WeaponName.BALNAB_SIDEARM -> ModSounds.BALNAB_SIDEARM_FIRE.get();
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
            case WeaponName.BSR7 -> ModSounds.BSR7_FIRE.get();
            case WeaponName.BT06 -> ModSounds.BT06_FIRE.get();
//            case BlasterName.BT_X42_FLAMETHROWER -> ModSounds.BT_X42_FLAMETHROWER_FIRE.get();
            case WeaponName.BX33 -> ModSounds.BX33_FIRE.get();
            case WeaponName.HT9 -> ModSounds.HT9_FIRE.get();
            case WeaponName.CC420 -> ModSounds.CC420_FIRE.get();
            case WeaponName.CJ9_BO_RIFLE -> ModSounds.CJ9_BO_RIFLE_FIRE.get();
            case WeaponName.CORE_J3 -> ModSounds.CORE_J3_FIRE.get();
            case WeaponName.CORE_R5 -> ModSounds.CORE_R5_FIRE.get();
            case WeaponName.CORE_U12 -> ModSounds.CORE_U12_FIRE.get();
            case WeaponName.CORPO_RIFLE -> ModSounds.CORPO_RIFLE_FIRE.get();
            case WeaponName.CR2 -> ModSounds.CR2_FIRE.get();
            case WeaponName.CS14 -> ModSounds.CS14_FIRE.get();
            case WeaponName.CYCLER_RIFLE -> ModSounds.CYCLER_RIFLE_FIRE.get();
            case WeaponName.CZERKA_19 -> ModSounds.CZERKA_19_FIRE.get();
            case WeaponName.CZERKA_ADVENTURER -> ModSounds.CZERKA_ADVENTURER_FIRE.get();
            case WeaponName.DC12U -> ModSounds.DC12U_FIRE.get();
            case WeaponName.DC15A -> ModSounds.DC15A_FIRE.get();
            case WeaponName.DC15LE -> ModSounds.DC15LE_FIRE.get();
            case WeaponName.DC15S_CARBINE -> ModSounds.DC15S_CARBINE_FIRE.get();
            case WeaponName.DC15S_SIDEARM -> ModSounds.DC15S_SIDEARM_FIRE.get();
            case WeaponName.DC17 -> ModSounds.DC17_FIRE.get();
            case WeaponName.DC17S -> ModSounds.DC17S_HAND_BLASTER_FIRE.get();
            case WeaponName.DC19 -> ModSounds.DC19_FIRE.get();
            case WeaponName.DE10 -> ModSounds.DE10_FIRE.get();
            case WeaponName.DEACTIVATOR -> ModSounds.DEACTIVATOR_FIRE.get();
            case WeaponName.DEFTECH -> ModSounds.DEFTECH_FIRE.get();
            case WeaponName.DER4 -> ModSounds.DER4_FIRE.get();
            case WeaponName.DFD1 -> ModSounds.DFD1_FIRE.get();
            case WeaponName.DFQ91 -> ModSounds.DFQ91_FIRE.get();
            case WeaponName.DG29 -> ModSounds.DG29_FIRE.get();
            case WeaponName.DH16 -> ModSounds.DH16_FIRE.get();
            case WeaponName.DH17 -> ModSounds.DH17_FIRE.get();
            case WeaponName.DH23 -> ModSounds.DH23_FIRE.get();
            case WeaponName.DH42 -> ModSounds.DH42_FIRE.get();
            case WeaponName.DH447 -> ModSounds.DH447_FIRE.get();
            case WeaponName.DL11 -> ModSounds.DL11_FIRE.get();
            case WeaponName.DL18 -> ModSounds.DL18_FIRE.get();
            case WeaponName.DL21 -> ModSounds.DL21_FIRE.get();
            case WeaponName.DL23 -> ModSounds.DL23_FIRE.get();
            case WeaponName.DLS12 -> ModSounds.DLS12_FIRE.get();
            case WeaponName.DLT15 -> ModSounds.DLT15_FIRE.get();
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
            case WeaponName.DUJ3 -> ModSounds.DUJ3_FIRE.get();
            case WeaponName.DUL4 -> ModSounds.DUL4_FIRE.get();
            case WeaponName.DX13 -> ModSounds.DX13_FIRE.get();
            case WeaponName.DX2 -> ModSounds.DX2_CHARGED_FIRE.get();
            case WeaponName.DXR6 -> ModSounds.DXR6_CHARGED_FIRE.get();
            case WeaponName.E5 -> ModSounds.E5_FIRE.get();
            case WeaponName.E5_BX -> ModSounds.E5_BX_FIRE.get();
            case WeaponName.E5_CARBINE -> ModSounds.E5_CARBINE_FIRE.get();
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
            case WeaponName.E11P -> ModSounds.E11P_FIRE.get();
            case WeaponName.E11S -> ModSounds.E11S_FIRE.get();
            case WeaponName.E11T -> ModSounds.E11T_FIRE.get();
            case WeaponName.E17D -> ModSounds.E17D_FIRE.get();
            case WeaponName.E22 -> ModSounds.E22_FIRE.get();
            case WeaponName.E44 -> ModSounds.E44_FIRE.get();
            case WeaponName.EC17 -> ModSounds.EC17_FIRE.get();
            case WeaponName.EE3 -> ModSounds.EE3_FIRE.get();
            case WeaponName.EE4 -> ModSounds.EE4_FIRE.get();
            case WeaponName.EL244 -> ModSounds.EL244_FIRE.get();
            case WeaponName.EL5 -> ModSounds.EL5_FIRE.get();
            case WeaponName.ELG3A -> ModSounds.ELG3A_FIRE.get();
            case WeaponName.ENERGY_BOW -> ModSounds.ENERGY_BOW_FIRE.get();
            case WeaponName.ENERGY_CROSSBOW -> ModSounds.ENERGY_CROSSBOW_FIRE.get();
            case WeaponName.ESB3 -> ModSounds.ESB3_FIRE.get();
            case WeaponName.EWEB -> ModSounds.EWEB_FIRE.get();
            case WeaponName.FC1_FLECHETTE_LAUNCHER -> ModSounds.FC1_FLECHETTE_LAUNCHER_FIRE.get();
            case WeaponName.F2L -> ModSounds.F2L_FIRE.get();
            case WeaponName.F4L -> ModSounds.F4L_FIRE.get();
            case WeaponName.FLITE37 -> ModSounds.FLITE37_FIRE.get();
            case WeaponName.FN57 -> ModSounds.FN57_FIRE.get();
            case WeaponName.FP45 -> ModSounds.FP45_FIRE.get();
//            case WeaponName.FWEB -> ModSounds.FWEB_FIRE.get();
            case WeaponName.FWG5 -> ModSounds.FWG5_FIRE.get();
            case WeaponName.FWG7 -> ModSounds.FWG7_FIRE.get();
            case WeaponName.GA3R -> ModSounds.GA3R_FIRE.get();
            case WeaponName.GALAAR15 -> ModSounds.GALAAR15_FIRE.get();
            case WeaponName.GALAR90 -> ModSounds.GALAR90_FIRE.get();
            case WeaponName.GE36 -> ModSounds.GE36_FIRE.get();
            case WeaponName.GL77 -> ModSounds.GL77_FIRE.get();
            case WeaponName.GLX_FIRELANCE -> ModSounds.GLX_FIRELANCE_FIRE.get();
            case WeaponName.GR4_ST -> ModSounds.GR4_ST_FIRE.get();
            case WeaponName.GR13 -> ModSounds.GR13_FIRE.get();
            case WeaponName.GRN4 -> ModSounds.GRN4_FIRE.get();
            case WeaponName.HB9 -> ModSounds.HB9_FIRE.get();
            case WeaponName.HF94 -> ModSounds.HF94_FIRE.get();
            case WeaponName.IB94 -> ModSounds.IB94_FIRE.get();
            case WeaponName.WESTARE9 -> ModSounds.WESTARE9_FIRE.get();
            case WeaponName.IQA11 -> ModSounds.IQA11_FIRE.get();
            case WeaponName.J19_BO_RIFLE -> ModSounds.J19_BO_RIFLE_FIRE.get();
            case WeaponName.JEZALI_CYCLER_RIFLE -> ModSounds.JEZALI_CYCLER_RIFLE_FIRE.get();
            case WeaponName.K13 -> ModSounds.K13_FIRE.get();
            case WeaponName.K21C_PORTABLE_ORDANANCE_LAUNCHER -> ModSounds.K21C_PORTABLE_ORDANANCE_LAUNCHER_FIRE.get();
            case WeaponName.KA74 -> ModSounds.KA74_FIRE.get();
            case WeaponName.KINETICBLAST -> ModSounds.KINETICBLAST_FIRE.get();
            case WeaponName.KISTEER_1284 -> ModSounds.KISTEER_1284_FIRE.get();
            case WeaponName.KL9 -> ModSounds.KL9_FIRE.get();
            case WeaponName.KM9 -> ModSounds.KM9_FIRE.get();
            case WeaponName.SE9V -> ModSounds.SE9V_FIRE.get();
            case WeaponName.KUEGET_LN21 -> ModSounds.KUEGET_LN21.get();
            case WeaponName.KYD21 -> ModSounds.KYD21_FIRE.get();
            case WeaponName.L5 -> ModSounds.L5_FIRE.get();
            case WeaponName.L60 -> ModSounds.L60_FIRE.get();
            case WeaponName.CW24 -> ModSounds.CW24_FIRE.get();
            case WeaponName.LIGHTBOW -> ModSounds.LIGHTBOW_FIRE.get();
            case WeaponName.LJ40 -> ModSounds.LJ40_FIRE.get();
            case WeaponName.LJ50 -> ModSounds.LJ50_FIRE.get();
            case WeaponName.LL30 -> ModSounds.LL30_FIRE.get();
            case WeaponName.LS150 -> ModSounds.LS150_FIRE.get();
            case WeaponName.RK2P -> ModSounds.RK2P_FIRE.get();
            case WeaponName.LW896 -> ModSounds.LW896_FIRE.get();
            case WeaponName.M12 -> ModSounds.M12_FIRE.get();
            case WeaponName.CC19 -> ModSounds.CC19_FIRE.get();
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
            case WeaponName.AZ6 -> ModSounds.AZ6_FIRE.get();
            case WeaponName.NT242 -> ModSounds.NT242_FIRE.get();
            case WeaponName.NOSLO19 -> ModSounds.NOSLO19_FIRE.get();
            case WeaponName.OK98 -> ModSounds.OK98_FIRE.get();
//            case BlasterName.OPRESSOR_FLAMETHROWER -> ModSounds.OPRESSOR_FLAMETHROWER_FIRE.get();
            case WeaponName.OUTLAND_RIFLE -> ModSounds.OUTLAND_RIFLE_FIRE.get();
            case WeaponName.F38G -> ModSounds.F38G_FIRE.get();
            case WeaponName.PANIC_PISTOL -> ModSounds.PANIC_PISTOL_FIRE.get();
            case WeaponName.EMG2 -> ModSounds.EMG2_FIRE.get();
            case WeaponName.PK23 -> ModSounds.PK23_FIRE.get();
//            case BlasterName.PLX1_MISSLE_LAUNCHER -> ModSounds.PLX1_MISSLE_LAUNCHER_FIRE.get();
            case WeaponName.RM7 -> ModSounds.RM7_FIRE.get();
            case WeaponName.PR9 -> ModSounds.PR9_FIRE.get();
            case WeaponName.PRECISIONX -> ModSounds.PRECISIONX_FIRE.get();
            case WeaponName.Q2 -> ModSounds.Q2_FIRE.get();
            case WeaponName.QUARREN_RIFLE -> ModSounds.QUARREN_RIFLE_FIRE.get();
            case WeaponName.RELBY_K23 -> ModSounds.RELBY_K23_FIRE.get();
            case WeaponName.RENEGADE -> ModSounds.RENEGADE_FIRE.get();
            case WeaponName.RG4D -> ModSounds.RG4D_FIRE.get();
            case WeaponName.RIG420 -> ModSounds.RIG420_FIRE.get();
            case WeaponName.RK3 -> ModSounds.RK3_FIRE.get();
//            case BlasterName.RPS6_ROCKET_LAUNCHER -> ModSounds.RPS6_ROCKET_LAUNCHER_FIRE.get();
            case WeaponName.RM_1P -> ModSounds.RM_1P_FIRE.get();
            case WeaponName.RSKF44 -> ModSounds.RSKF44_FIRE.get();
            case WeaponName.S2S -> ModSounds.S2S_FIRE.get();
            case WeaponName.RT97C -> ModSounds.RT97C_FIRE.get();
            case WeaponName.RLR_MK_II-> ModSounds.RLR_MK_II_FIRE.get();
            case WeaponName.S5 -> ModSounds.S5_FIRE.get();
            case WeaponName.S195-> ModSounds.S195_FIRE.get();
            case WeaponName.SACROS_K11 -> ModSounds.SACROS_K11_FIRE.get();
            case WeaponName.SE14C -> ModSounds.SE14C_FIRE.get();
            case WeaponName.SE14R -> ModSounds.SE14R_FIRE.get();
            case WeaponName.SEDGLEYS_MK_5 -> ModSounds.SEDGLEYS_MK_5_FIRE.get();
            case WeaponName.SER5 -> ModSounds.SER5_FIRE.get();
            case WeaponName.S3_MK_5 -> ModSounds.S3_MK_5_FIRE.get();
            case WeaponName.SHARD3A -> ModSounds.SHARD3A_FIRE.get();
//            case BlasterName.SMART_ROCKET -> ModSounds.SMART_ROCKET_FIRE.get();
            case WeaponName.SNUB_BLASTER -> ModSounds.SNUB_BLASTER_FIRE.get();
            case WeaponName.SNUB_SCATTER -> ModSounds.SNUB_SCATTER_FIRE.get();
            case WeaponName.SNUBBLE -> ModSounds.SNUBBLE_FIRE.get();
            case WeaponName.SONIC_BLASTER -> ModSounds.SONIC_BLASTER_FIRE.get();
            case WeaponName.SONIC_STUNNER -> ModSounds.SONIC_STUNNER_FIRE.get();
            case WeaponName.SS410 -> ModSounds.SS410_FIRE.get();
            case WeaponName.CW76 -> ModSounds.CW76_FIRE.get();
            case WeaponName.SX21 -> ModSounds.SX21_FIRE.get();
            case WeaponName.T6 -> ModSounds.T6_FIRE.get();
            case WeaponName.T7_ION_DISRUPTOR -> ModSounds.T7_ION_DISRUPTOR_FIRE.get();
            case WeaponName.T21 -> ModSounds.T21_FIRE.get();
            case WeaponName.T21B -> ModSounds.T21B_FIRE.get();
            case WeaponName.TG446 -> ModSounds.TG446_FIRE.get();
            case WeaponName.THUNDERBLASTER -> ModSounds.THUNDERBLASTER_FIRE.get();
            case WeaponName.TL40 -> ModSounds.TL40_FIRE.get();
            case WeaponName.WESTAR2L -> ModSounds.WESTAR2L_FIRE.get();
            case WeaponName.T9K7 -> ModSounds.T9K7_FIRE.get();
            case WeaponName.UMBARAN_BLASTER -> ModSounds.UMBARAN_BLASTER_FIRE.get();
            case WeaponName.UTK3 -> ModSounds.UTK3_FIRE.get();
//            case BlasterName.V6D_MORTAR_LAUNCHER -> ModSounds.V6D_MORTAR_LUANCHER_FIRE.get();
            case WeaponName.V850_MK -> ModSounds.V850_MK_FIRE.get();
            case WeaponName.VALKEN38X -> ModSounds.VALKEN38X_FIRE.get();
            case WeaponName.VANGUARD_SCATTER -> ModSounds.VANGUARD_SCATTER_FIRE.get();
            case WeaponName.V13 -> ModSounds.V13_FIRE.get();
            case WeaponName.VERPINE_SHATTER_RIFLE -> ModSounds.VERPINE_SHATTER_RIFLE_FIRE.get();
            case WeaponName.VERPINE_SIDEARM -> ModSounds.VERPINE_SIDEARM_FIRE.get();
            case WeaponName.VILMARHS_REVENGE -> ModSounds.VILMARHS_REVENGE_FIRE.get();
            case WeaponName.VM19 -> ModSounds.VM19_FIRE.get();
            case WeaponName.VT20 -> ModSounds.VT20_FIRE.get();
            case WeaponName.VULK_TAU623_ROTARY -> ModSounds.VULK_TAU623_ROTARY_FIRE.get();
            case WeaponName.W50S -> ModSounds.W50S_FIRE.get();
            case WeaponName.W90 -> ModSounds.W90_FIRE.get();
            case WeaponName.W310 -> ModSounds.W310_FIRE.get();
            case WeaponName.W340LM -> ModSounds.W340LM_FIRE.get();
            case WeaponName.WS4 -> ModSounds.WS4_FIRE.get();
            case WeaponName.P224 -> ModSounds.P224_FIRE.get();
            case WeaponName.WEEQUAY_LANCE -> ModSounds.WEEQUAY_LANCE_FIRE.get();
            case WeaponName.WEEQUAY_PISTOL -> ModSounds.WEEQUAY_PISTOL_FIRE.get();
            case WeaponName.WEEQUAY_RIFLE -> ModSounds.WEEQUAY_RIFLE_FIRE.get();
            case WeaponName.WESTAR_20 -> ModSounds.WESTAR_20_FIRE.get();
            case WeaponName.WESTAR_33 -> ModSounds.WESTAR_33_FIRE.get();
            case WeaponName.WESTAR_34 -> ModSounds.WESTAR_34_FIRE.get();
            case WeaponName.WESTAR_35 -> ModSounds.WESTAR_35_FIRE.get();
            case WeaponName.WESTARL4 -> ModSounds.WESTARL4_FIRE.get();
            case WeaponName.WESTARLVN -> ModSounds.WESTARLVN_FIRE.get();
            case WeaponName.WESTARM5 -> ModSounds.WESTARM5_FIRE.get();
            case WeaponName.WINCHESTER87 -> ModSounds.WINCHESTER87_FIRE.get();
            case WeaponName.X8_NIGHT_SNIPER -> ModSounds.X8_NIGHT_SNIPER_FIRE.get();
            case WeaponName.X30 -> ModSounds.X30_FIRE.get();
            case WeaponName.X45 -> ModSounds.X45_FIRE.get();
            case WeaponName.X47 -> ModSounds.X47_FIRE.get();
            case WeaponName.Z6_ROTARY -> ModSounds.Z6_ROTARY_FIRE.get();
            case WeaponName.ZB3 -> ModSounds.ZB3_FIRE.get();
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
            case WeaponName.BX49:
                if (blasterFireMode.equals(FiringMode.LAUNCHER)) {
                    return ModSounds.FOLEY_BX49_LAUNCHER_RELOAD.get();
                } else {
                    return ModSounds.FOLEY_LARGE_RELOAD_GAS.get();
                }
            case WeaponName.DT29:
                return ModSounds.FOLEY_DT29_RELOAD.get();
            case WeaponName.RELBY_V10:
                if (blasterFireMode.equals(FiringMode.LAUNCHER)) {
                    return ModSounds.FOLEY_RELBY_V10_LAUNCHER_RELOAD.get();
                } else {
                    return ModSounds.FOLEY_MEDIUM_RELOAD_GAS.get();
                }
            case WeaponName.R88:
                if (blasterFireMode.equals(FiringMode.LAUNCHER)) {
                    return ModSounds.FOLEY_R88_LAUNCHER_RELOAD.get();
                } else {
                    return ModSounds.FOLEY_MEDIUM_RELOAD_GAS.get();
                }
            case /*BlasterName.V6D_MORTAR_LAUNCHER, BlasterName.MORTAR,*/ WeaponName.K21C_PORTABLE_ORDANANCE_LAUNCHER:
                return ModSounds.FOLEY_LARGE_LAUNCHER_RELOAD.get();
            case WeaponName.ABR2_ZATO, WeaponName.ACP_ARRAY, WeaponName.DFQ91, WeaponName.DX2, WeaponName.DXR6, WeaponName.LIGHTBOW, WeaponName.LS150, WeaponName.VULK_TAU623_ROTARY, WeaponName.Z6_ROTARY, WeaponName.EWEB, /*BlasterName.EWHB12,*/ WeaponName.M32, WeaponName.M45, WeaponName.M55, WeaponName.M61, WeaponName.MWC35C, WeaponName.T21, WeaponName.T21B,
                 WeaponName.BM107, WeaponName.GALAR90, WeaponName.NT242, WeaponName.GE36, WeaponName.NEO_CRUSADER_RIFLE, WeaponName.PRECISIONX, WeaponName.BOILER_RIFLE, WeaponName.BOWCASTER, WeaponName.T7_ION_DISRUPTOR:
                return ModSounds.FOLEY_LARGE_RELOAD_GAS.get();
            case WeaponName._22T4, WeaponName.A140, WeaponName.A180, WeaponName.A240, WeaponName.AC177, WeaponName.APACHE, WeaponName.ASTRA40, WeaponName.B22, WeaponName.BALNAB_SIDEARM, WeaponName.BE09, WeaponName.BH4, WeaponName.BK28, WeaponName.BLURRG1120,
                 WeaponName.BOONTA_BLASTER, WeaponName.BR14, WeaponName.BT06, WeaponName.C10, WeaponName.CORE_J3, WeaponName.CORE_R5, WeaponName.CORE_U12, WeaponName.HT9, WeaponName.LV7C, WeaponName.CC420, WeaponName.CR2, WeaponName.CS14, WeaponName.DC15S_SIDEARM,
                 WeaponName.DC17, WeaponName.DE10, WeaponName.DEACTIVATOR, WeaponName.DER4, WeaponName.DG29, WeaponName.DH16, WeaponName.DH17, WeaponName.DH23, WeaponName.DH42, WeaponName.DL11, WeaponName.DL18, WeaponName.DL21, WeaponName.DT12, WeaponName.DT15, WeaponName.DUJ3, WeaponName.DX13,
                 WeaponName.E11P, WeaponName.EC17, WeaponName.EL5, WeaponName.ELG3A, WeaponName.F2L, WeaponName.FN57, WeaponName.FP45, WeaponName.GA3R, WeaponName.GL77, WeaponName.GR4_ST, WeaponName.GRN4, WeaponName.HF94, WeaponName.IB94, WeaponName.K13, WeaponName.K16_BRYAR_PISTOL, WeaponName.KL9,
                 WeaponName.SE9V, WeaponName.KM9, WeaponName.KUEGET_LN21, WeaponName.KYD21, WeaponName.CW24, WeaponName.LL30, WeaponName.RK2P, WeaponName.LW896, WeaponName.CC19, WeaponName.MARG_MCM, WeaponName.MODEL_57, WeaponName.MSD32,
                 WeaponName.F38G, WeaponName.EMG2, WeaponName.RM7, WeaponName.PR9, WeaponName.Q2, WeaponName.RELBY_K23, WeaponName.RENEGADE, WeaponName.RG4D, WeaponName.RIG420, WeaponName.RK3,
                 WeaponName.RM_1P, WeaponName.RSKF44, WeaponName.S2S, WeaponName.RLR_MK_II, WeaponName.S195, WeaponName.S5, WeaponName.SACROS_K11, WeaponName.SATINES_LAMENT, WeaponName.SE14C, WeaponName.S3_MK_5,
                 WeaponName.SEDGLEYS_MK_5, WeaponName.SETTLERS_STUN, WeaponName.SHARD3A, WeaponName.SK32, WeaponName.SNUB_BLASTER, WeaponName.SNUBBLE, WeaponName.SONIC_STUNNER, WeaponName.SS410, WeaponName.CW76, WeaponName.T6, WeaponName.TG446, WeaponName.UMBARAN_BLASTER, WeaponName.UTK3, WeaponName.VILMARHS_REVENGE, WeaponName.VM19, WeaponName.VT20, WeaponName.WESTAR2L,
                 WeaponName.W50S, WeaponName.W310, WeaponName.W340LM, WeaponName.WS4, WeaponName.P224, WeaponName.WEEQUAY_PISTOL, WeaponName.WESTAR_20, WeaponName.WESTAR_33,
                 WeaponName.WOOKIE_SIDEARM,WeaponName.X8_NIGHT_SNIPER, WeaponName.X30, WeaponName.PANIC_PISTOL:
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
            case WeaponName.RELBY_K25:
                if (blasterFireMode.equals(FiringMode.SEMI_AUTO)) {
                    return ModSounds.FOLEY_RELBY_K25_CHARGED_SWITCH_FIRE_MODE.get();
                } else if (blasterFireMode.equals(FiringMode.CHARGENSHOOT)) {
                    return ModSounds.FOLEY_RELBY_K25_RIFLE_SWITCH_FIRE_MODE.get();
                } else {
                    return ModSounds.FOLEY_RELBY_K25_PISTOL_SWITCH_FIRE_MODE.get();
                }
            case WeaponName.ABR2_ZATO, /*BlasterName.BT_X42_FLAMETHROWER,*/ WeaponName.BX49, WeaponName.DFQ91, WeaponName.DX2, WeaponName.DXR6, WeaponName.K21C_PORTABLE_ORDANANCE_LAUNCHER, WeaponName.LIGHTBOW, WeaponName.LS150, WeaponName.MINIMAG_PROTON_TORPEDO_LAUNCHER,
                 /*BlasterName.OPRESSOR_FLAMETHROWER, BlasterName.PLX1_MISSLE_LAUNCHER, BlasterName.RPS6_ROCKET_LAUNCHER, BlasterName.SMART_ROCKET, BlasterName.V6D_MORTAR_LAUNCHER,*/
                 WeaponName.VULK_TAU623_ROTARY, WeaponName.Z6_ROTARY, WeaponName.EWEB, /*BlasterName.EWHB12,*/ WeaponName.M32, WeaponName.M45, WeaponName.M55, WeaponName.M61, WeaponName.MWC35C, WeaponName.T21, WeaponName.T21B,
                                                      WeaponName.BM107, WeaponName.GALAR90, WeaponName.PRECISIONX:
                return ModSounds.FOLEY_LARGE_SWITCH_FIRE_MODE.get();
            case WeaponName._22T4, WeaponName._434_DEATHHAMMER, WeaponName.A140, WeaponName.A240, WeaponName.AC177, WeaponName.APACHE, WeaponName.ASTRA40, WeaponName.B33, WeaponName.BALNAB_SIDEARM, WeaponName.BE09, WeaponName.BH4, WeaponName.BK28, WeaponName.BLURRG1120,
                 WeaponName.BOONTA_BLASTER, WeaponName.BR14, WeaponName.BT06, WeaponName.C10, WeaponName.CORE_J3, WeaponName.CORE_R5, WeaponName.CORE_U12, WeaponName.HT9, WeaponName.LV7C, WeaponName.CC420, WeaponName.CR2, WeaponName.CS14, WeaponName.DC15S_SIDEARM,
                 WeaponName.DC17, WeaponName.DE10, WeaponName.DEACTIVATOR, WeaponName.DER4, WeaponName.DG29, WeaponName.DH16, WeaponName.DH17, WeaponName.DH23, WeaponName.DH42, WeaponName.DL11, WeaponName.DL18, WeaponName.DL21, WeaponName.DT12, WeaponName.DT15, WeaponName.DT29, WeaponName.DUJ3, WeaponName.DX13,
                 WeaponName.E11P, WeaponName.EC17, WeaponName.EL5, WeaponName.ELG3A, WeaponName.F2L, WeaponName.FN57, WeaponName.FP45, WeaponName.GA3R, WeaponName.GL77, WeaponName.GR4_ST, WeaponName.GRN4, WeaponName.HF94, WeaponName.IB94, WeaponName.K13, WeaponName.K16_BRYAR_PISTOL, WeaponName.KL9,
                 WeaponName.SE9V, WeaponName.KM9, WeaponName.KUEGET_LN21, WeaponName.KYD21, WeaponName.CW24, WeaponName.LL30, WeaponName.RK2P, WeaponName.LW896, WeaponName.CC19, WeaponName.MARG_MCM, WeaponName.MODEL_57, WeaponName.MSD32,
                 WeaponName.F38G, WeaponName.EMG2, WeaponName.POWER_5, WeaponName.RM7, WeaponName.PR9, WeaponName.Q2, WeaponName.RELBY_K23, WeaponName.RENEGADE, WeaponName.RG4D, WeaponName.RIG420, WeaponName.RK3,
                 WeaponName.RM_1P, WeaponName.RSKF44, WeaponName.S2S, WeaponName.RLR_MK_II, WeaponName.S195, WeaponName.S5, WeaponName.SACROS_K11, WeaponName.SATINES_LAMENT, WeaponName.SE14C, WeaponName.S3_MK_5,
                 WeaponName.SEDGLEYS_MK_5, WeaponName.SETTLERS_STUN, WeaponName.SHARD3A, WeaponName.SK32, WeaponName.SNUB_BLASTER, WeaponName.SNUBBLE, WeaponName.SONIC_STUNNER, WeaponName.SS410, WeaponName.CW76, WeaponName.T6, WeaponName.TG446, WeaponName.UMBARAN_BLASTER, WeaponName.UTK3, WeaponName.VILMARHS_REVENGE, WeaponName.VM19, WeaponName.VT20, WeaponName.WESTAR2L,
                 WeaponName.W50S, WeaponName.W310, WeaponName.W340LM, WeaponName.WS4, WeaponName.P224, WeaponName.WEEQUAY_PISTOL, WeaponName.WESTAR_20, WeaponName.WESTAR_34,
                 WeaponName.WESTAR_35, WeaponName.WESTAR_33, WeaponName.WOOKIE_SIDEARM, WeaponName.X8_NIGHT_SNIPER, WeaponName.X30, WeaponName.PANIC_PISTOL:
                return ModSounds.FOLEY_SMALL_SWITCH_FIRE_MODE.get();
            default:
                return ModSounds.FOLEY_MEDIUM_SWITCH_FIRE_MODE.get();
        }
    }

    public static SoundEvent getWeaponCharge(WeaponName blasterName) {
        return switch (blasterName) {
            case WeaponName.BK28 -> ModSounds.BK28_CHARGE.get();
            case WeaponName.BM107 -> ModSounds.BM107_CHARGE.get();
            case WeaponName.BOWCASTER -> ModSounds.BOWCASTER_CHARGE.get();
            case WeaponName.BRYAR_RIFLE -> ModSounds.BRYAR_RIFLE_CHARGE.get();
            case WeaponName.C10 -> ModSounds.C10_CHARGE.get();
            case WeaponName.CHARRIC -> ModSounds.CHARRIC_CHARGE.get();
            case WeaponName.LV7C -> ModSounds.LV7C_CHARGE.get();
            case WeaponName.DC15X -> ModSounds.DC15X_CHARGE.get();
            case WeaponName.DC17M -> ModSounds.DC17M_CHARGE.get();
            case WeaponName.DN_BOLT_CASTER -> ModSounds.DN_BOLT_CASTER_CHARGE.get();
            case WeaponName.DP23 -> ModSounds.DP23_CHARGE.get();
            case WeaponName.DX2 -> ModSounds.DX2_CHARGE.get();
            case WeaponName.DXR6 -> ModSounds.DXR6_CHARGE.get();
            case WeaponName.GM46 -> ModSounds.GM46_CHARGE.get();
            case WeaponName.JND41 -> ModSounds.JND41_CHARGE.get();
            case WeaponName.K16_BRYAR_PISTOL -> ModSounds.K16_BRYAR_PISTOL_CHARGE.get();
            case WeaponName.MSD32 -> ModSounds.MSD32_CHARGE.get();
            case WeaponName.MW20_BRYAR_PISTOL -> ModSounds.MW20_BRYAR_PISTOL_CHARGE.get();
            case WeaponName.NEO_CRUSADER_RIFLE -> ModSounds.NEO_CRUSADER_RIFLE_CHARGE.get();
            case WeaponName.NIGHT_STINGER -> ModSounds.NIGHT_STINGER_CHARGE.get();
            case WeaponName.POWER_5 -> ModSounds.POWER_5_CHARGE.get();
            case WeaponName.RELBY_K25 -> ModSounds.RELBY_K25_CHARGE.get();
            case WeaponName.RELBY_V10 -> ModSounds.RELBY_V10_CHARGE.get();
            case WeaponName.SATINES_LAMENT -> ModSounds.SATINES_LAMENT_CHARGE.get();
            case WeaponName.E9V -> ModSounds.E9V_CHARGE.get();
            case WeaponName.SK32 -> ModSounds.SK32_CHARGE.get();
            case WeaponName.T7_ION_DISRUPTOR -> ModSounds.T7_ION_DISRUPTOR_CHARGE.get();
            case WeaponName.WOOKIE_RIFLE -> ModSounds.WOOKIE_RIFLE_CHARGE.get();
            case WeaponName.WOOKIE_SIDEARM -> ModSounds.WOOKIE_SIDEARM_CHARGE.get();
            case WeaponName.Z6_ROTARY -> ModSounds.Z6_ROTARY_CHARGE.get();
            case WeaponName.ZYGERRIAN_BLASTER -> ModSounds.ZYGERRIAN_BLASTER_CHARGE.get();
            default -> ModSounds.TL50_CHARGE.get();
        };
    }

    public static SoundEvent getWeaponUncharge(WeaponName blasterName) {
        return switch (blasterName) {
            case WeaponName.BK28 -> ModSounds.BK28_UNCHARGE.get();
            case WeaponName.BM107 -> ModSounds.BM107_UNCHARGE.get();
            case WeaponName.BOWCASTER -> ModSounds.BOWCASTER_UNCHARGE.get();
            case WeaponName.BRYAR_RIFLE -> ModSounds.BRYAR_RIFLE_UNCHARGE.get();
            case WeaponName.C10 -> ModSounds.C10_UNCHARGE.get();
            case WeaponName.CHARRIC -> ModSounds.CHARRIC_UNCHARGE.get();
            case WeaponName.LV7C -> ModSounds.LV7C_UNCHARGE.get();
            case WeaponName.DC15X -> ModSounds.DC15X_UNCHARGE.get();
            case WeaponName.DC17M -> ModSounds.DC17M_UNCHARGE.get();
            case WeaponName.DN_BOLT_CASTER -> ModSounds.DN_BOLT_CASTER_UNCHARGE.get();
            case WeaponName.DP23 -> ModSounds.DP23_UNCHARGE.get();
            case WeaponName.DX2 -> ModSounds.DX2_UNCHARGE.get();
            case WeaponName.DXR6 -> ModSounds.DXR6_UNCHARGE.get();
            case WeaponName.GM46 -> ModSounds.GM46_UNCHARGE.get();
            case WeaponName.JND41 -> ModSounds.JND41_UNCHARGE.get();
            case WeaponName.K16_BRYAR_PISTOL -> ModSounds.K16_BRYAR_PISTOL_UNCHARGE.get();
            case WeaponName.MSD32 -> ModSounds.MSD32_UNCHARGE.get();
            case WeaponName.MW20_BRYAR_PISTOL -> ModSounds.MW20_BRYAR_PISTOL_UNCHARGE.get();
            case WeaponName.NEO_CRUSADER_RIFLE -> ModSounds.NEO_CRUSADER_RIFLE_UNCHARGE.get();
            case WeaponName.NIGHT_STINGER -> ModSounds.NIGHT_STINGER_UNCHARGE.get();
            case WeaponName.POWER_5 -> ModSounds.POWER_5_UNCHARGE.get();
            case WeaponName.RELBY_K25 -> ModSounds.RELBY_K25_UNCHARGE.get();
            case WeaponName.RELBY_V10 -> ModSounds.RELBY_V10_UNCHARGE.get();
            case WeaponName.SATINES_LAMENT -> ModSounds.SATINES_LAMENT_UNCHARGE.get();
            case WeaponName.E9V -> ModSounds.E9V_UNCHARGE.get();
            case WeaponName.SK32 -> ModSounds.SK32_UNCHARGE.get();
            case WeaponName.T7_ION_DISRUPTOR -> ModSounds.T7_ION_DISRUPTOR_UNCHARGE.get();
            case WeaponName.WOOKIE_RIFLE -> ModSounds.WOOKIE_RIFLE_UNCHARGE.get();
            case WeaponName.WOOKIE_SIDEARM -> ModSounds.WOOKIE_SIDEARM_UNCHARGE.get();
            case WeaponName.Z6_ROTARY -> ModSounds.Z6_ROTARY_UNCHARGE.get();
            case WeaponName.ZYGERRIAN_BLASTER -> ModSounds.ZYGERRIAN_BLASTER_UNCHARGE.get();
            default -> ModSounds.TL50_UNCHARGE.get();
        };
    }

    public static SoundEvent getWeaponChargeLoop(WeaponName blasterName) {
        return switch (blasterName) {
            case WeaponName.DC15X -> ModSounds.DC15X_CHARGE_LOOP.get();
            case WeaponName.K16_BRYAR_PISTOL -> ModSounds.K16_BRYAR_PISTOL_CHARGE_LOOP.get();
            default -> ModSounds.POWER_5_CHARGE_LOOP.get();
        };
    }

    public static SoundEvent getWeaponBeam(WeaponName blasterName) {
        return switch (blasterName) {
            case WeaponName._773_FIREPUNCHER -> ModSounds.K16_BRYAR_PISTOL_CHARGE_LOOP.get();
            case WeaponName.DC12U -> ModSounds.K16_BRYAR_PISTOL_CHARGE_LOOP.get();
//            case WeaponName.DFQ1_SNIPER_RIFLE -> ModSounds.DFQ1_SNIPER_RIFLE_BEAM.get();
            default -> ModSounds.K16_BRYAR_PISTOL_CHARGE_LOOP.get(); //DLT20A
        };
    }

    public static SoundEvent getWeaponEquip(WeaponName blasterName) {
        Random random = new Random();
        switch (blasterName) {
            case WeaponName.AMBAN_DISRUPTOR:
                return ModSounds.FOLEY_AMBAN_DISRUPTOR_SNIPER_RIFLE_EQUIP.get();
            case WeaponName.IB94:
                return ModSounds.FOLEY_IB94_EQUIP.get();
            case WeaponName.ABR2_ZATO, WeaponName.ACP_ARRAY, /*BlasterName.BT_X42_FLAMETHROWER,*/ WeaponName.BX49, WeaponName.DFQ91, WeaponName.DX2, WeaponName.DXR6, WeaponName.K21C_PORTABLE_ORDANANCE_LAUNCHER, WeaponName.LIGHTBOW, WeaponName.LS150, WeaponName.MINIMAG_PROTON_TORPEDO_LAUNCHER,
                 /*BlasterName.OPRESSOR_FLAMETHROWER, BlasterName.PLX1_MISSLE_LAUNCHER, BlasterName.RPS6_ROCKET_LAUNCHER, BlasterName.SMART_ROCKET, BlasterName.V6D_MORTAR_LAUNCHER,*/
                 WeaponName.VULK_TAU623_ROTARY, WeaponName.Z6_ROTARY, WeaponName.EWEB, /*BlasterName.EWHB12,*/ WeaponName.M32, WeaponName.M45, WeaponName.M55, WeaponName.M61, WeaponName.MWC35C, WeaponName.T21, WeaponName.T21B,
                                                      WeaponName.BM107, WeaponName.GALAR90, WeaponName.PRECISIONX:
                return ModSounds.FOLEY_LARGE_EQUIP.get();
            case WeaponName._22T4, WeaponName._434_DEATHHAMMER, WeaponName.A140, WeaponName.A180, WeaponName.A240, WeaponName.AC177, WeaponName.APACHE, WeaponName.ASTRA40, WeaponName.B22, WeaponName.B33, WeaponName.BALNAB_SIDEARM, WeaponName.BE09, WeaponName.BH4, WeaponName.BK28, WeaponName.BLURRG1120,
                 WeaponName.BOONTA_BLASTER, WeaponName.BR14, WeaponName.BT06, WeaponName.C10, WeaponName.CORE_J3, WeaponName.CORE_R5, WeaponName.CORE_U12, WeaponName.HT9, WeaponName.LV7C, WeaponName.CC420, WeaponName.CR2, WeaponName.CS14, WeaponName.DC15S_SIDEARM,
                 WeaponName.DC17, WeaponName.DE10, WeaponName.DEACTIVATOR, WeaponName.DER4, WeaponName.DG29, WeaponName.DH16, WeaponName.DH17, WeaponName.DH23, WeaponName.DH42, WeaponName.DL11, WeaponName.DL18, WeaponName.DL21, WeaponName.DL44, WeaponName.DT12, WeaponName.DT15, WeaponName.DT29, WeaponName.DUJ3, WeaponName.DX13,
                 WeaponName.E11P, WeaponName.EC17, WeaponName.EL5, WeaponName.ELG3A, WeaponName.F2L, WeaponName.FN57, WeaponName.FP45, WeaponName.GA3R, WeaponName.GL77, WeaponName.GR4_ST, WeaponName.GRN4, WeaponName.HF94, WeaponName.K13, WeaponName.K16_BRYAR_PISTOL, WeaponName.KL9,
                 WeaponName.SE9V, WeaponName.KM9, WeaponName.KUEGET_LN21, WeaponName.KYD21, WeaponName.CW24, WeaponName.LL30, WeaponName.RK2P, WeaponName.LW896, WeaponName.CC19, WeaponName.MARG_MCM, WeaponName.MODEL_57, WeaponName.MSD32,
                 WeaponName.F38G, WeaponName.EMG2, WeaponName.POWER_5, WeaponName.RM7, WeaponName.PR9, WeaponName.Q2, WeaponName.RELBY_K23, WeaponName.RENEGADE, WeaponName.RG4D, WeaponName.RIG420, WeaponName.RK3,
                 WeaponName.RM_1P, WeaponName.RSKF44, WeaponName.S2S, WeaponName.RLR_MK_II, WeaponName.S195, WeaponName.S5, WeaponName.SACROS_K11, WeaponName.SATINES_LAMENT, WeaponName.SE14C, WeaponName.S3_MK_5,
                 WeaponName.SEDGLEYS_MK_5, WeaponName.SETTLERS_STUN, WeaponName.SHARD3A, WeaponName.SK32, WeaponName.SNUB_BLASTER, WeaponName.SNUBBLE, WeaponName.SONIC_STUNNER, WeaponName.SS410, WeaponName.CW76, WeaponName.T6, WeaponName.TG446, WeaponName.UMBARAN_BLASTER, WeaponName.UTK3, WeaponName.VILMARHS_REVENGE, WeaponName.VM19, WeaponName.VT20, WeaponName.WESTAR2L,
                 WeaponName.W50S, WeaponName.W310, WeaponName.W340LM, WeaponName.WS4, WeaponName.P224, WeaponName.WEEQUAY_PISTOL, WeaponName.WESTAR_20, WeaponName.WESTAR_34,
                 WeaponName.WESTAR_35, WeaponName.WESTAR_33, WeaponName.WOOKIE_SIDEARM, WeaponName.X8_NIGHT_SNIPER, WeaponName.X30, WeaponName.PANIC_PISTOL, WeaponName.MW20_BRYAR_PISTOL, WeaponName.B1NA:
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
            case WeaponName.ABR2_ZATO, WeaponName.ACP_ARRAY, /*BlasterName.BT_X42_FLAMETHROWER,*/ WeaponName.BX49, WeaponName.DFQ91, WeaponName.DX2, WeaponName.DXR6, WeaponName.K21C_PORTABLE_ORDANANCE_LAUNCHER, WeaponName.LIGHTBOW, WeaponName.LS150, WeaponName.MINIMAG_PROTON_TORPEDO_LAUNCHER,
                 /*BlasterName.OPRESSOR_FLAMETHROWER, BlasterName.PLX1_MISSLE_LAUNCHER, BlasterName.RPS6_ROCKET_LAUNCHER, BlasterName.SMART_ROCKET, BlasterName.V6D_MORTAR_LAUNCHER,*/
                 WeaponName.VULK_TAU623_ROTARY, WeaponName.Z6_ROTARY, WeaponName.EWEB, /*BlasterName.EWHB12,*/ WeaponName.M32, WeaponName.M45, WeaponName.M55, WeaponName.M61, WeaponName.MWC35C, WeaponName.T21, WeaponName.T21B,
                 WeaponName.BM107, WeaponName.GALAR90, WeaponName.PRECISIONX:
                return ModSounds.FOLEY_LARGE_UNEQUIP.get();
            case WeaponName._22T4, WeaponName._434_DEATHHAMMER, WeaponName.A140, WeaponName.A180, WeaponName.A240, WeaponName.AC177, WeaponName.APACHE, WeaponName.ASTRA40, WeaponName.B22, WeaponName.B33, WeaponName.BALNAB_SIDEARM, WeaponName.BE09, WeaponName.BH4, WeaponName.BK28, WeaponName.BLURRG1120,
                 WeaponName.BOONTA_BLASTER, WeaponName.BR14, WeaponName.BT06, WeaponName.C10, WeaponName.HT9, WeaponName.LV7C, WeaponName.CC420, WeaponName.CR2, WeaponName.CS14, WeaponName.DC15S_SIDEARM,
                 WeaponName.DC17, WeaponName.DE10, WeaponName.DEACTIVATOR, WeaponName.DER4, WeaponName.DG29, WeaponName.DH16, WeaponName.DH17, WeaponName.DH23, WeaponName.DH42, WeaponName.DL11, WeaponName.DL18, WeaponName.DL21, WeaponName.DL44, WeaponName.DT12, WeaponName.DT15, WeaponName.DT29, WeaponName.DUJ3, WeaponName.DX13,
                 WeaponName.E11P, WeaponName.EC17, WeaponName.EL5, WeaponName.ELG3A, WeaponName.F2L, WeaponName.FN57, WeaponName.FP45, WeaponName.GA3R, WeaponName.GL77, WeaponName.GR4_ST, WeaponName.GRN4, WeaponName.HF94, WeaponName.K13, WeaponName.K16_BRYAR_PISTOL, WeaponName.KL9,
                 WeaponName.SE9V, WeaponName.KM9, WeaponName.KUEGET_LN21, WeaponName.KYD21, WeaponName.CW24, WeaponName.LL30, WeaponName.RK2P, WeaponName.LW896, WeaponName.CC19, WeaponName.MARG_MCM, WeaponName.MODEL_57, WeaponName.MSD32,
                 WeaponName.F38G, WeaponName.EMG2, WeaponName.POWER_5, WeaponName.RM7, WeaponName.PR9, WeaponName.Q2, WeaponName.RELBY_K23, WeaponName.RENEGADE, WeaponName.RG4D, WeaponName.RIG420, WeaponName.RK3,
                 WeaponName.RM_1P, WeaponName.RSKF44, WeaponName.S2S, WeaponName.RLR_MK_II, WeaponName.S195, WeaponName.S5, WeaponName.SACROS_K11, WeaponName.SATINES_LAMENT, WeaponName.SE14C, WeaponName.S3_MK_5,
                 WeaponName.SEDGLEYS_MK_5, WeaponName.SETTLERS_STUN, WeaponName.SHARD3A, WeaponName.SK32, WeaponName.SNUB_BLASTER, WeaponName.SNUBBLE, WeaponName.SONIC_STUNNER, WeaponName.SS410, WeaponName.CW76, WeaponName.T6, WeaponName.TG446, WeaponName.UMBARAN_BLASTER, WeaponName.UTK3, WeaponName.VILMARHS_REVENGE, WeaponName.VM19, WeaponName.VT20, WeaponName.WESTAR2L,
                 WeaponName.W50S, WeaponName.W310, WeaponName.W340LM, WeaponName.WS4, WeaponName.P224, WeaponName.WEEQUAY_PISTOL, WeaponName.WESTAR_20, WeaponName.WESTAR_34,
                 WeaponName.WESTAR_35, WeaponName.WESTAR_33, WeaponName.WOOKIE_SIDEARM, WeaponName.X8_NIGHT_SNIPER, WeaponName.X30, WeaponName.PANIC_PISTOL, WeaponName.MW20_BRYAR_PISTOL, WeaponName.B1NA:
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
