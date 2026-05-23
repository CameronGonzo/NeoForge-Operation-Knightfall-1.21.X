package net.uhhitscam.knightfall.util;

import net.minecraft.sounds.SoundEvent;
import net.uhhitscam.knightfall.item.custom.FiringMode;
import net.uhhitscam.knightfall.item.custom.WeaponClassification;
import net.uhhitscam.knightfall.item.custom.WeaponName;
import net.uhhitscam.knightfall.sound.ModSounds;

import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public final class WeaponSoundsUtil {
    private static final EnumMap<WeaponName, WeaponSoundProfile> PROFILES = new EnumMap<>(WeaponName.class);

    private static final EnumSet<WeaponName> LARGE_WEAPONS = set(
            WeaponName.ABR2_ZATO, WeaponName.ACP_ARRAY, WeaponName.BM107, WeaponName.BOILER_RIFLE, WeaponName.BOWCASTER,
            WeaponName.BX49, WeaponName.DFQ91, WeaponName.DX2, WeaponName.DXR6, WeaponName.EWEB,
            WeaponName.GALAR90, WeaponName.GE36, WeaponName.K21C_PORTABLE_ORDANANCE_LAUNCHER, WeaponName.LIGHTBOW, WeaponName.LS150,
            WeaponName.M32, WeaponName.M45, WeaponName.M55, WeaponName.M61, WeaponName.MINIMAG_PROTON_TORPEDO_LAUNCHER,
            WeaponName.MWC35C, WeaponName.NEO_CRUSADER_RIFLE, WeaponName.NT242, WeaponName.PRECISIONX, WeaponName.T7_ION_DISRUPTOR,
            WeaponName.T21, WeaponName.T21B, WeaponName.VULK_TAU623_ROTARY, WeaponName.Z6_ROTARY
    );

    private static final EnumSet<WeaponName> SMALL_WEAPONS = set(
            WeaponName._22T4, WeaponName._434_DEATHHAMMER, WeaponName.A2H, WeaponName.A140, WeaponName.A180,
            WeaponName.A240, WeaponName.AC177, WeaponName.APACHE, WeaponName.ASTRA40, WeaponName.B1NA,
            WeaponName.B22, WeaponName.B33, WeaponName.BALNAB_SIDEARM, WeaponName.BE09, WeaponName.BH4,
            WeaponName.BK28, WeaponName.BLURRG1120, WeaponName.BOONTA_BLASTER, WeaponName.BR14, WeaponName.BT06,
            WeaponName.C10, WeaponName.CC19, WeaponName.CC420, WeaponName.CORE_J3, WeaponName.CORE_R5,
            WeaponName.CORE_U12, WeaponName.CR2, WeaponName.CS14, WeaponName.CW24, WeaponName.CW76,
            WeaponName.DC15S_SIDEARM, WeaponName.DC17, WeaponName.DE10, WeaponName.DEACTIVATOR, WeaponName.DER4,
            WeaponName.DG29, WeaponName.DH16, WeaponName.DH17, WeaponName.DH23, WeaponName.DH42,
            WeaponName.DL11, WeaponName.DL18, WeaponName.DL21, WeaponName.DL44, WeaponName.DT12,
            WeaponName.DT15, WeaponName.DT29, WeaponName.DUJ3, WeaponName.DX13, WeaponName.E11P,
            WeaponName.EC17, WeaponName.EL5, WeaponName.ELG3A, WeaponName.EMG2, WeaponName.F2L,
            WeaponName.F38G, WeaponName.FN57, WeaponName.FP45, WeaponName.GA3R, WeaponName.GL77,
            WeaponName.GR4_ST, WeaponName.GRN4, WeaponName.HF94, WeaponName.HT9, WeaponName.IB94,
            WeaponName.K13, WeaponName.K16_BRYAR_PISTOL, WeaponName.KL9, WeaponName.KM9, WeaponName.KUEGET_LN21,
            WeaponName.KYD21, WeaponName.LL30, WeaponName.LV7C, WeaponName.LW896, WeaponName.MARG_MCM,
            WeaponName.MODEL_57, WeaponName.MSD32, WeaponName.MW20_BRYAR_PISTOL, WeaponName.P224, WeaponName.PANIC_PISTOL,
            WeaponName.POWER_5, WeaponName.PR9, WeaponName.Q2, WeaponName.RELBY_K23, WeaponName.RENEGADE,
            WeaponName.RG4D, WeaponName.RIG420, WeaponName.RK2P, WeaponName.RK3, WeaponName.RLR_MK_II,
            WeaponName.RM7, WeaponName.RM_1P, WeaponName.RSKF44, WeaponName.S2S, WeaponName.S3_MK_5,
            WeaponName.S5, WeaponName.S195, WeaponName.SACROS_K11, WeaponName.SATINES_LAMENT, WeaponName.SE9V,
            WeaponName.SE14C, WeaponName.SEDGLEYS_MK_5, WeaponName.SETTLERS_STUN, WeaponName.SHARD3A, WeaponName.SK32,
            WeaponName.SNUB_BLASTER, WeaponName.SNUBBLE, WeaponName.SONIC_STUNNER, WeaponName.SS410, WeaponName.T6,
            WeaponName.TG446, WeaponName.UMBARAN_BLASTER, WeaponName.UTK3, WeaponName.VILMARHS_REVENGE, WeaponName.VM19,
            WeaponName.VT20, WeaponName.W50S, WeaponName.W310, WeaponName.W340LM, WeaponName.WEEQUAY_PISTOL,
            WeaponName.WESTAR2L, WeaponName.WESTAR_20, WeaponName.WESTAR_33, WeaponName.WESTAR_34, WeaponName.WESTAR_35,
            WeaponName.WOOKIE_SIDEARM, WeaponName.WS4, WeaponName.X8_NIGHT_SNIPER, WeaponName.X30
    );

    private static final EnumSet<WeaponName> SCATTER_EQUIP_WEAPONS = set(
            WeaponName.BARMST12, WeaponName.BLNDRBUS, WeaponName.CA87, WeaponName.FLITE37, WeaponName.SX21,
            WeaponName.VANGUARD_SCATTER, WeaponName.WINCHESTER87
    );

    static {
        registerFireDefaults();
        registerFireOverrides();
        registerReloadProfiles();
        registerSwitchModeProfiles();
        registerEquipProfiles();
        registerChargeProfiles();
    }

    private WeaponSoundsUtil() {
    }

    public static SoundEvent getWeaponFireSound(WeaponName weaponName, FiringMode firingMode) {
        if (weaponName == null || firingMode == null) {
            return ModSounds.E11_RIFLE_FIRE.get();
        }

        WeaponSoundProfile profile = PROFILES.get(weaponName);

        if (firingMode == FiringMode.STUN) {
            if (profile != null && profile.hasFireModeSound(FiringMode.STUN)) {
                return profile.resolveFire(firingMode, ModSounds.STUN_FIRE);
            }

            return ModSounds.STUN_FIRE.get();
        }

        if (profile == null) {
            return ModSounds.E11_RIFLE_FIRE.get();
        }

        return profile.resolveFire(firingMode, ModSounds.E11_RIFLE_FIRE);
    }

    public static SoundEvent getWeaponReloadSound(WeaponName weaponName, FiringMode firingMode, WeaponClassification classification) {
        WeaponSoundProfile profile = PROFILES.get(weaponName);

        if (firingMode == FiringMode.LAUNCHER) {
            if (profile != null) {
                return profile.resolveReload(firingMode, ModSounds.FOLEY_LARGE_LAUNCHER_RELOAD);
            }

            return ModSounds.FOLEY_LARGE_LAUNCHER_RELOAD.get();
        }

        if (classification == WeaponClassification.FLECHETTE) {
            return ModSounds.FOLEY_RELOAD_FLECHETTE.get();
        }

        if (classification == WeaponClassification.SLUGTHROWER) {
            return ModSounds.FOLEY_RELOAD_SLUG.get();
        }

        if (profile != null) {
            return profile.resolveReload(firingMode, () -> getDefaultReloadSound(weaponName));
        }

        return getDefaultReloadSound(weaponName);
    }

    public static SoundEvent getWeaponUnloadSound(FiringMode firingMode, WeaponClassification classification) {
        if (firingMode == FiringMode.LAUNCHER) {
            return ModSounds.FOLEY_UNLOAD_GAS.get();
        }

        return switch (classification) {
            case FLECHETTE -> ModSounds.FOLEY_UNLOAD_FLECHETTE.get();
            case SLUGTHROWER -> ModSounds.FOLEY_UNLOAD_SLUG.get();
            default -> ModSounds.FOLEY_UNLOAD_GAS.get();
        };
    }

    public static SoundEvent getWeaponSwitchFireMode(WeaponName weaponName, FiringMode firingMode) {
        WeaponSoundProfile profile = PROFILES.get(weaponName);
        if (profile != null) {
            return profile.resolveSwitchMode(firingMode, () -> getDefaultSwitchModeSound(weaponName));
        }

        return getDefaultSwitchModeSound(weaponName);
    }

    public static SoundEvent getWeaponCharge(WeaponName weaponName) {
        WeaponSoundProfile profile = PROFILES.get(weaponName);
        if (profile != null) {
            return profile.resolveCharge(ModSounds.TL50_CHARGE);
        }

        return ModSounds.TL50_CHARGE.get();
    }

    public static SoundEvent getWeaponUncharge(WeaponName weaponName) {
        WeaponSoundProfile profile = PROFILES.get(weaponName);
        if (profile != null) {
            return profile.resolveUncharge(ModSounds.TL50_UNCHARGE);
        }

        return ModSounds.TL50_UNCHARGE.get();
    }

    public static SoundEvent getWeaponChargeLoop(WeaponName weaponName) {
        WeaponSoundProfile profile = PROFILES.get(weaponName);
        if (profile != null) {
            return profile.resolveChargeLoop(ModSounds.POWER_5_CHARGE_LOOP);
        }

        return ModSounds.POWER_5_CHARGE_LOOP.get();
    }

    public static SoundEvent getWeaponBeam(WeaponName weaponName) {
        WeaponSoundProfile profile = PROFILES.get(weaponName);
        if (profile != null) {
            return profile.resolveBeam(ModSounds.K16_BRYAR_PISTOL_CHARGE_LOOP);
        }

        return ModSounds.K16_BRYAR_PISTOL_CHARGE_LOOP.get();
    }

    public static SoundEvent getWeaponEquip(WeaponName weaponName) {
        WeaponSoundProfile profile = PROFILES.get(weaponName);
        if (profile != null) {
            return profile.resolveEquip(() -> getDefaultEquipSound(weaponName));
        }

        return getDefaultEquipSound(weaponName);
    }

    public static SoundEvent getWeaponUnequip(WeaponName weaponName) {
        WeaponSoundProfile profile = PROFILES.get(weaponName);
        if (profile != null) {
            return profile.resolveUnequip(() -> getDefaultUnequipSound(weaponName));
        }

        return getDefaultUnequipSound(weaponName);
    }

    private static void registerFireDefaults() {
        fireDefault(WeaponName._62AUG2_HUNTING_RIFLE, ModSounds._62AUG2_HUNTING_RIFLE_FIRE);
        fireDefault(WeaponName._22T4, ModSounds._22T4_FIRE);
        fireDefault(WeaponName._84U_HUNTING_RIFLE, ModSounds._84U_HUNTING_RIFLE_FIRE);
        fireDefault(WeaponName._434_DEATHHAMMER, ModSounds._434_DEATHHAMMER_FIRE);
        fireDefault(WeaponName._773_FIREPUNCHER, ModSounds._773_FIREPUNCHER_FIRE);
        fireDefault(WeaponName._785MK_FIREPUNCHERX, ModSounds._785MK_FIREPUNCHERX_FIRE);
        fireDefault(WeaponName.A2H, ModSounds.A2H_FIRE);
        fireDefault(WeaponName.A140, ModSounds.A140_FIRE);
        fireDefault(WeaponName.A280, ModSounds.A280_FIRE);
        fireDefault(WeaponName.A240, ModSounds.A240_FIRE);
        fireDefault(WeaponName.A280C, ModSounds.A280C_FIRE);
        fireDefault(WeaponName.A295, ModSounds.A295_FIRE);
        fireDefault(WeaponName.A300, ModSounds.A300_FIRE);
        fireDefault(WeaponName.A310, ModSounds.A310_FIRE);
        fireDefault(WeaponName.A350, ModSounds.A350_FIRE);
        fireDefault(WeaponName.AB75_BO_RIFLE, ModSounds.AB75_BO_RIFLE_FIRE);
        fireDefault(WeaponName.ABR2_ZATO, ModSounds.ABR2_ZATO_FIRE);
        fireDefault(WeaponName.AC177, ModSounds.AC177_FIRE);
        fireDefault(WeaponName.ACP_REPEATER, ModSounds.ACP_REPEATER_FIRE);
        fireDefault(WeaponName.ACP_ARRAY, ModSounds.ACP_ARRAY_FIRE);
        fireDefault(WeaponName.BK43, ModSounds.BK43_FIRE);
        fireDefault(WeaponName.AMBAN_DISRUPTOR, ModSounds.AMBAN_DISRUPTOR_FIRE);
        fireDefault(WeaponName.APACHE, ModSounds.APACHE_FIRE);
        fireDefault(WeaponName.ASTRA40, ModSounds.ASTRA40_FIRE);
        fireDefault(WeaponName.AVARIK, ModSounds.AVARIK_FIRE);
        fireDefault(WeaponName.B1NA, ModSounds.B1NA_FIRE);
        fireDefault(WeaponName.B1X, ModSounds.B1X_FIRE);
        fireDefault(WeaponName.B22, ModSounds.B22_FIRE);
        fireDefault(WeaponName.B33, ModSounds.B33_FIRE);
        fireDefault(WeaponName.BAC5, ModSounds.BAC5_FIRE);
        fireDefault(WeaponName.BALNAB, ModSounds.BALNAB_FIRE);
        fireDefault(WeaponName.BALNAB_SIDEARM, ModSounds.BALNAB_SIDEARM_FIRE);
        fireDefault(WeaponName.BARMST12, ModSounds.BARMST12_FIRE);
        fireDefault(WeaponName.BATON_BLASTER, ModSounds.BATON_BLASTER_FIRE);
        fireDefault(WeaponName.BE09, ModSounds.BE09_FIRE);
        fireDefault(WeaponName.BE29, ModSounds.BE29_FIRE);
        fireDefault(WeaponName.BERSERKER, ModSounds.BERSERKER_FIRE);
        fireDefault(WeaponName.BH4, ModSounds.BH4_FIRE);
        fireDefault(WeaponName.BLASTER_SPEAR, ModSounds.BLASTER_SPEAR_FIRE);
        fireDefault(WeaponName.BLURRG1120, ModSounds.BLURRG1120_FIRE);
        fireDefault(WeaponName.BOILER_RIFLE, ModSounds.BOILER_RIFLE_FIRE);
        fireDefault(WeaponName.BOONTA_BLASTER, ModSounds.BOONTA_BLASTER_FIRE);
        fireDefault(WeaponName.BR14, ModSounds.BR14_FIRE);
        fireDefault(WeaponName.BSR7, ModSounds.BSR7_FIRE);
        fireDefault(WeaponName.BT06, ModSounds.BT06_FIRE);
        fireDefault(WeaponName.BX33, ModSounds.BX33_FIRE);
        fireDefault(WeaponName.HT9, ModSounds.HT9_FIRE);
        fireDefault(WeaponName.CC420, ModSounds.CC420_FIRE);
        fireDefault(WeaponName.CH60, ModSounds.CH60_FIRE);
        fireDefault(WeaponName.CJ9_BO_RIFLE, ModSounds.CJ9_BO_RIFLE_FIRE);
        fireDefault(WeaponName.CL14, ModSounds.CL14_FIRE);
        fireDefault(WeaponName.CORE_J3, ModSounds.CORE_J3_FIRE);
        fireDefault(WeaponName.CORE_R5, ModSounds.CORE_R5_FIRE);
        fireDefault(WeaponName.CORE_U12, ModSounds.CORE_U12_FIRE);
        fireDefault(WeaponName.CORPO_RIFLE, ModSounds.CORPO_RIFLE_FIRE);
        fireDefault(WeaponName.CP5, ModSounds.CP5_FIRE);
        fireDefault(WeaponName.CP6, ModSounds.CP6_FIRE);
        fireDefault(WeaponName.CQ29, ModSounds.CQ29_FIRE);
        fireDefault(WeaponName.CR2, ModSounds.CR2_FIRE);
        fireDefault(WeaponName.CS14, ModSounds.CS14_FIRE);
        fireDefault(WeaponName.CT33, ModSounds.CT33_FIRE);
        fireDefault(WeaponName.CYCLER_RIFLE, ModSounds.CYCLER_RIFLE_FIRE);
        fireDefault(WeaponName.CZERKA_19, ModSounds.CZERKA_19_FIRE);
        fireDefault(WeaponName.CZERKA_ADVENTURER, ModSounds.CZERKA_ADVENTURER_FIRE);
        fireDefault(WeaponName.DC12U, ModSounds.DC12U_FIRE);
        fireDefault(WeaponName.DC15A, ModSounds.DC15A_FIRE);
        fireDefault(WeaponName.DC15LE, ModSounds.DC15LE_FIRE);
        fireDefault(WeaponName.DC15S_CARBINE, ModSounds.DC15S_CARBINE_FIRE);
        fireDefault(WeaponName.DC15S_SIDEARM, ModSounds.DC15S_SIDEARM_FIRE);
        fireDefault(WeaponName.DC17, ModSounds.DC17_FIRE);
        fireDefault(WeaponName.DC17S, ModSounds.DC17S_HAND_BLASTER_FIRE);
        fireDefault(WeaponName.DC19, ModSounds.DC19_FIRE);
        fireDefault(WeaponName.DCX, ModSounds.DCX_FIRE);
        fireDefault(WeaponName.DE10, ModSounds.DE10_FIRE);
        fireDefault(WeaponName.DEACTIVATOR, ModSounds.DEACTIVATOR_FIRE);
        fireDefault(WeaponName.DEFTECH, ModSounds.DEFTECH_FIRE);
        fireDefault(WeaponName.DER4, ModSounds.DER4_FIRE);
        fireDefault(WeaponName.DFD1, ModSounds.DFD1_FIRE);
        fireDefault(WeaponName.DFQ91, ModSounds.DFQ91_FIRE);
        fireDefault(WeaponName.DG29, ModSounds.DG29_FIRE);
        fireDefault(WeaponName.DH16, ModSounds.DH16_FIRE);
        fireDefault(WeaponName.DH17, ModSounds.DH17_FIRE);
        fireDefault(WeaponName.DH23, ModSounds.DH23_FIRE);
        fireDefault(WeaponName.DH42, ModSounds.DH42_FIRE);
        fireDefault(WeaponName.DH447, ModSounds.DH447_FIRE);
        fireDefault(WeaponName.DL11, ModSounds.DL11_FIRE);
        fireDefault(WeaponName.DL18, ModSounds.DL18_FIRE);
        fireDefault(WeaponName.DL21, ModSounds.DL21_FIRE);
        fireDefault(WeaponName.DL23, ModSounds.DL23_FIRE);
        fireDefault(WeaponName.DLS12, ModSounds.DLS12_FIRE);
        fireDefault(WeaponName.DLT15, ModSounds.DLT15_FIRE);
        fireDefault(WeaponName.DLT18, ModSounds.DLT18_FIRE);
        fireDefault(WeaponName.DLT19, ModSounds.DLT19_FIRE);
        fireDefault(WeaponName.DLT19D, ModSounds.DLT19D_FIRE);
        fireDefault(WeaponName.DLT19X, ModSounds.DLT19X_FIRE);
        fireDefault(WeaponName.DN_BOLT_CASTER, ModSounds.DN_BOLT_CASTER_FIRE);
        fireDefault(WeaponName.DRESSELLIAN_PROJECTILE_RIFLE, ModSounds.DRESSELLIAN_PROJECTILE_RIFLE_FIRE);
        fireDefault(WeaponName.DT12, ModSounds.DT12_FIRE);
        fireDefault(WeaponName.DT15, ModSounds.DT15_FIRE);
        fireDefault(WeaponName.DT29, ModSounds.DT29_FIRE);
        fireDefault(WeaponName.DT57, ModSounds.DT57_FIRE);
        fireDefault(WeaponName.DUJ3, ModSounds.DUJ3_FIRE);
        fireDefault(WeaponName.DUL4, ModSounds.DUL4_FIRE);
        fireDefault(WeaponName.DX13, ModSounds.DX13_FIRE);
        fireDefault(WeaponName.DX2, ModSounds.DX2_CHARGED_FIRE);
        fireDefault(WeaponName.DXR6, ModSounds.DXR6_CHARGED_FIRE);
        fireDefault(WeaponName.E5, ModSounds.E5_FIRE);
        fireDefault(WeaponName.E5_BX, ModSounds.E5_BX_FIRE);
        fireDefault(WeaponName.E5_CARBINE, ModSounds.E5_CARBINE_FIRE);
        fireDefault(WeaponName.E5_CE, ModSounds.E5_CE_FIRE);
        fireDefault(WeaponName.E5C, ModSounds.E5C_FIRE);
        fireDefault(WeaponName.E5R, ModSounds.E5R_FIRE);
        fireDefault(WeaponName.E5S, ModSounds.E5S_FIRE);
        fireDefault(WeaponName.E5T, ModSounds.E5T_FIRE);
        fireDefault(WeaponName.E10, ModSounds.E10_FIRE);
        fireDefault(WeaponName.E10_5, ModSounds.E10_5_FIRE);
        fireDefault(WeaponName.E10R, ModSounds.E10R_FIRE);
        fireDefault(WeaponName.E11_CARBINE, ModSounds.E11_CARBINE_FIRE);
        fireDefault(WeaponName.E11_RIFLE, ModSounds.E11_RIFLE_FIRE);
        fireDefault(WeaponName.E11B, ModSounds.E11B_FIRE);
        fireDefault(WeaponName.E11D, ModSounds.E11D_FIRE);
        fireDefault(WeaponName.E11P, ModSounds.E11P_FIRE);
        fireDefault(WeaponName.E11S, ModSounds.E11S_FIRE);
        fireDefault(WeaponName.E11T, ModSounds.E11T_FIRE);
        fireDefault(WeaponName.E17D, ModSounds.E17D_FIRE);
        fireDefault(WeaponName.E22, ModSounds.E22_FIRE);
        fireDefault(WeaponName.E44, ModSounds.E44_FIRE);
        fireDefault(WeaponName.EC17, ModSounds.EC17_FIRE);
        fireDefault(WeaponName.EE3, ModSounds.EE3_FIRE);
        fireDefault(WeaponName.EE4, ModSounds.EE4_FIRE);
        fireDefault(WeaponName.EL244, ModSounds.EL244_FIRE);
        fireDefault(WeaponName.EL5, ModSounds.EL5_FIRE);
        fireDefault(WeaponName.ELG3A, ModSounds.ELG3A_FIRE);
        fireDefault(WeaponName.ENERGY_BOW, ModSounds.ENERGY_BOW_FIRE);
        fireDefault(WeaponName.ENERGY_CROSSBOW, ModSounds.ENERGY_CROSSBOW_FIRE);
        fireDefault(WeaponName.ESB3, ModSounds.ESB3_FIRE);
        fireDefault(WeaponName.EWEB, ModSounds.EWEB_FIRE);
        fireDefault(WeaponName.FC1_FLECHETTE_LAUNCHER, ModSounds.FC1_FLECHETTE_LAUNCHER_FIRE);
        fireDefault(WeaponName.FC29, ModSounds.FC29_FIRE);
        fireDefault(WeaponName.F2L, ModSounds.F2L_FIRE);
        fireDefault(WeaponName.F4L, ModSounds.F4L_FIRE);
        fireDefault(WeaponName.FLITE37, ModSounds.FLITE37_FIRE);
        fireDefault(WeaponName.FN57, ModSounds.FN57_FIRE);
        fireDefault(WeaponName.FP45, ModSounds.FP45_FIRE);
        fireDefault(WeaponName.FWG5, ModSounds.FWG5_FIRE);
        fireDefault(WeaponName.FWG7, ModSounds.FWG7_FIRE);
        fireDefault(WeaponName.G433, ModSounds.G433_FIRE);
        fireDefault(WeaponName.GA3R, ModSounds.GA3R_FIRE);
        fireDefault(WeaponName.GALAAR15, ModSounds.GALAAR15_FIRE);
        fireDefault(WeaponName.GALAR90, ModSounds.GALAR90_FIRE);
        fireDefault(WeaponName.GE36, ModSounds.GE36_FIRE);
        fireDefault(WeaponName.GL77, ModSounds.GL77_FIRE);
        fireDefault(WeaponName.GLX_FIRELANCE, ModSounds.GLX_FIRELANCE_FIRE);
        fireDefault(WeaponName.GR4_ST, ModSounds.GR4_ST_FIRE);
        fireDefault(WeaponName.GR13, ModSounds.GR13_FIRE);
        fireDefault(WeaponName.GRN4, ModSounds.GRN4_FIRE);
        fireDefault(WeaponName.HB9, ModSounds.HB9_FIRE);
        fireDefault(WeaponName.HF94, ModSounds.HF94_FIRE);
        fireDefault(WeaponName.IB94, ModSounds.IB94_FIRE);
        fireDefault(WeaponName.WESTARE9, ModSounds.WESTARE9_FIRE);
        fireDefault(WeaponName.IQA11, ModSounds.IQA11_FIRE);
        fireDefault(WeaponName.J19_BO_RIFLE, ModSounds.J19_BO_RIFLE_FIRE);
        fireDefault(WeaponName.JEZALI_CYCLER_RIFLE, ModSounds.JEZALI_CYCLER_RIFLE_FIRE);
        fireDefault(WeaponName.K13, ModSounds.K13_FIRE);
        fireDefault(WeaponName.K21C_PORTABLE_ORDANANCE_LAUNCHER, ModSounds.K21C_PORTABLE_ORDANANCE_LAUNCHER_FIRE);
        fireDefault(WeaponName.K63R, ModSounds.K63R_FIRE);
        fireDefault(WeaponName.KA74, ModSounds.KA74_FIRE);
        fireDefault(WeaponName.KINETICBLAST, ModSounds.KINETICBLAST_FIRE);
        fireDefault(WeaponName.KISTEER_1284, ModSounds.KISTEER_1284_FIRE);
        fireDefault(WeaponName.KL9, ModSounds.KL9_FIRE);
        fireDefault(WeaponName.KM9, ModSounds.KM9_FIRE);
        fireDefault(WeaponName.SE9V, ModSounds.SE9V_FIRE);
        fireDefault(WeaponName.KUEGET_LN21, ModSounds.KUEGET_LN21);
        fireDefault(WeaponName.KYD21, ModSounds.KYD21_FIRE);
        fireDefault(WeaponName.L5, ModSounds.L5_FIRE);
        fireDefault(WeaponName.L60, ModSounds.L60_FIRE);
        fireDefault(WeaponName.CW24, ModSounds.CW24_FIRE);
        fireDefault(WeaponName.LIGHTBOW, ModSounds.LIGHTBOW_FIRE);
        fireDefault(WeaponName.LJ40, ModSounds.LJ40_FIRE);
        fireDefault(WeaponName.LJ50, ModSounds.LJ50_FIRE);
        fireDefault(WeaponName.LL30, ModSounds.LL30_FIRE);
        fireDefault(WeaponName.LP_LAW, ModSounds.LP_LAW_FIRE);
        fireDefault(WeaponName.LS150, ModSounds.LS150_FIRE);
        fireDefault(WeaponName.RK2P, ModSounds.RK2P_FIRE);
        fireDefault(WeaponName.LW896, ModSounds.LW896_FIRE);
        fireDefault(WeaponName.M12, ModSounds.M12_FIRE);
        fireDefault(WeaponName.CC19, ModSounds.CC19_FIRE);
        fireDefault(WeaponName.M32, ModSounds.M32_FIRE);
        fireDefault(WeaponName.M41, ModSounds.M41_FIRE);
        fireDefault(WeaponName.M45, ModSounds.M45_FIRE);
        fireDefault(WeaponName.M55, ModSounds.M55_FIRE);
        fireDefault(WeaponName.M61, ModSounds.M61_FIRE);
        fireDefault(WeaponName.MARG_MCM, ModSounds.MARG_MCM_FIRE);
        fireDefault(WeaponName.MK3T, ModSounds.MK3T_FIRE);
        fireDefault(WeaponName.MINIMAG_PROTON_TORPEDO_LAUNCHER, ModSounds.MINIMAG_PROTON_TORPEDO_LAUNCHER_FIRE);
        fireDefault(WeaponName.MK_II_PALADIN, ModSounds.MK_II_PALADIN_FIRE);
        fireDefault(WeaponName.MODEL_57, ModSounds.MODEL_57_FIRE);
        fireDefault(WeaponName.MWC35C, ModSounds.MWC35C_FIRE);
        fireDefault(WeaponName.AZ6, ModSounds.AZ6_FIRE);
        fireDefault(WeaponName.NT242, ModSounds.NT242_FIRE);
        fireDefault(WeaponName.NOSLO19, ModSounds.NOSLO19_FIRE);
        fireDefault(WeaponName.OK98, ModSounds.OK98_FIRE);
        fireDefault(WeaponName.OUTLAND_RIFLE, ModSounds.OUTLAND_RIFLE_FIRE);
        fireDefault(WeaponName.F38G, ModSounds.F38G_FIRE);
        fireDefault(WeaponName.PANIC_PISTOL, ModSounds.PANIC_PISTOL_FIRE);
        fireDefault(WeaponName.PD44, ModSounds.PD44_FIRE);
        fireDefault(WeaponName.EMG2, ModSounds.EMG2_FIRE);
        fireDefault(WeaponName.PK23, ModSounds.PK23_FIRE);
        fireDefault(WeaponName.RM7, ModSounds.RM7_FIRE);
        fireDefault(WeaponName.PR9, ModSounds.PR9_FIRE);
        fireDefault(WeaponName.PRD8, ModSounds.PRD8_FIRE);
        fireDefault(WeaponName.PRD58, ModSounds.PRD58_FIRE);
        fireDefault(WeaponName.PRD62, ModSounds.PRD62_FIRE);
        fireDefault(WeaponName.PRECISIONX, ModSounds.PRECISIONX_FIRE);
        fireDefault(WeaponName.Q2, ModSounds.Q2_FIRE);
        fireDefault(WeaponName.QUARREN_RIFLE, ModSounds.QUARREN_RIFLE_FIRE);
        fireDefault(WeaponName.RD2B, ModSounds.RD2B_FIRE);
        fireDefault(WeaponName.RD6, ModSounds.RD6_FIRE);
        fireDefault(WeaponName.RELBY_K23, ModSounds.RELBY_K23_FIRE);
        fireDefault(WeaponName.RENEGADE, ModSounds.RENEGADE_FIRE);
        fireDefault(WeaponName.RG4D, ModSounds.RG4D_FIRE);
        fireDefault(WeaponName.RIG420, ModSounds.RIG420_FIRE);
        fireDefault(WeaponName.RK3, ModSounds.RK3_FIRE);
        fireDefault(WeaponName.RM_1P, ModSounds.RM_1P_FIRE);
        fireDefault(WeaponName.RSKF44, ModSounds.RSKF44_FIRE);
        fireDefault(WeaponName.S2S, ModSounds.S2S_FIRE);
        fireDefault(WeaponName.RT97C, ModSounds.RT97C_FIRE);
        fireDefault(WeaponName.RLR_MK_II, ModSounds.RLR_MK_II_FIRE);
        fireDefault(WeaponName.S5, ModSounds.S5_FIRE);
        fireDefault(WeaponName.S195, ModSounds.S195_FIRE);
        fireDefault(WeaponName.SACROS_K11, ModSounds.SACROS_K11_FIRE);
        fireDefault(WeaponName.SE14C, ModSounds.SE14C_FIRE);
        fireDefault(WeaponName.SE14R, ModSounds.SE14R_FIRE);
        fireDefault(WeaponName.SEDGLEYS_MK_5, ModSounds.SEDGLEYS_MK_5_FIRE);
        fireDefault(WeaponName.SEL3, ModSounds.SEL3_FIRE);
        fireDefault(WeaponName.SER5, ModSounds.SER5_FIRE);
        fireDefault(WeaponName.S3_MK_5, ModSounds.S3_MK_5_FIRE);
        fireDefault(WeaponName.SHARD3A, ModSounds.SHARD3A_FIRE);
        fireDefault(WeaponName.SNUB_BLASTER, ModSounds.SNUB_BLASTER_FIRE);
        fireDefault(WeaponName.SNUB_SCATTER, ModSounds.SNUB_SCATTER_FIRE);
        fireDefault(WeaponName.SNUBBLE, ModSounds.SNUBBLE_FIRE);
        fireDefault(WeaponName.SONIC_BLASTER, ModSounds.SONIC_BLASTER_FIRE);
        fireDefault(WeaponName.SONIC_STUNNER, ModSounds.SONIC_STUNNER_FIRE);
        fireDefault(WeaponName.SS410, ModSounds.SS410_FIRE);
        fireDefault(WeaponName.SWE1, ModSounds.SWE1_FIRE);
        fireDefault(WeaponName.SWE2, ModSounds.SWE2_FIRE);
        fireDefault(WeaponName.CW76, ModSounds.CW76_FIRE);
        fireDefault(WeaponName.SX21, ModSounds.SX21_FIRE);
        fireDefault(WeaponName.T4W1, ModSounds.T4W1_FIRE);
        fireDefault(WeaponName.T6, ModSounds.T6_FIRE);
        fireDefault(WeaponName.T7_ION_DISRUPTOR, ModSounds.T7_ION_DISRUPTOR_FIRE);
        fireDefault(WeaponName.T21, ModSounds.T21_FIRE);
        fireDefault(WeaponName.T21B, ModSounds.T21B_FIRE);
        fireDefault(WeaponName.TG446, ModSounds.TG446_FIRE);
        fireDefault(WeaponName.THUNDERBLASTER, ModSounds.THUNDERBLASTER_FIRE);
        fireDefault(WeaponName.TL40, ModSounds.TL40_FIRE);
        fireDefault(WeaponName.WESTAR2L, ModSounds.WESTAR2L_FIRE);
        fireDefault(WeaponName.T9K7, ModSounds.T9K7_FIRE);
        fireDefault(WeaponName.UMBARAN_BLASTER, ModSounds.UMBARAN_BLASTER_FIRE);
        fireDefault(WeaponName.UTK3, ModSounds.UTK3_FIRE);
        fireDefault(WeaponName.V850_MK, ModSounds.V850_MK_FIRE);
        fireDefault(WeaponName.VALKEN38X, ModSounds.VALKEN38X_FIRE);
        fireDefault(WeaponName.VANGUARD_SCATTER, ModSounds.VANGUARD_SCATTER_FIRE);
        fireDefault(WeaponName.V13, ModSounds.V13_FIRE);
        fireDefault(WeaponName.VERPINE_SHATTER_RIFLE, ModSounds.VERPINE_SHATTER_RIFLE_FIRE);
        fireDefault(WeaponName.VERPINE_SIDEARM, ModSounds.VERPINE_SIDEARM_FIRE);
        fireDefault(WeaponName.VILMARHS_REVENGE, ModSounds.VILMARHS_REVENGE_FIRE);
        fireDefault(WeaponName.VM19, ModSounds.VM19_FIRE);
        fireDefault(WeaponName.VT20, ModSounds.VT20_FIRE);
        fireDefault(WeaponName.VULK_TAU623_ROTARY, ModSounds.VULK_TAU623_ROTARY_FIRE);
        fireDefault(WeaponName.W50S, ModSounds.W50S_FIRE);
        fireDefault(WeaponName.W90, ModSounds.W90_FIRE);
        fireDefault(WeaponName.W210, ModSounds.W210_FIRE);
        fireDefault(WeaponName.W310, ModSounds.W310_FIRE);
        fireDefault(WeaponName.W340LM, ModSounds.W340LM_FIRE);
        fireDefault(WeaponName.WS4, ModSounds.WS4_FIRE);
        fireDefault(WeaponName.P224, ModSounds.P224_FIRE);
        fireDefault(WeaponName.WEEQUAY_LANCE, ModSounds.WEEQUAY_LANCE_FIRE);
        fireDefault(WeaponName.WEEQUAY_PISTOL, ModSounds.WEEQUAY_PISTOL_FIRE);
        fireDefault(WeaponName.WEEQUAY_RIFLE, ModSounds.WEEQUAY_RIFLE_FIRE);
        fireDefault(WeaponName.WESTAR_20, ModSounds.WESTAR_20_FIRE);
        fireDefault(WeaponName.WESTAR_33, ModSounds.WESTAR_33_FIRE);
        fireDefault(WeaponName.WESTAR_34, ModSounds.WESTAR_34_FIRE);
        fireDefault(WeaponName.WESTAR_35, ModSounds.WESTAR_35_FIRE);
        fireDefault(WeaponName.WESTARL4, ModSounds.WESTARL4_FIRE);
        fireDefault(WeaponName.WESTARLVN, ModSounds.WESTARLVN_FIRE);
        fireDefault(WeaponName.WESTARM5, ModSounds.WESTARM5_FIRE);
        fireDefault(WeaponName.WINCHESTER87, ModSounds.WINCHESTER87_FIRE);
        fireDefault(WeaponName.X8_NIGHT_SNIPER, ModSounds.X8_NIGHT_SNIPER_FIRE);
        fireDefault(WeaponName.X30, ModSounds.X30_FIRE);
        fireDefault(WeaponName.X45, ModSounds.X45_FIRE);
        fireDefault(WeaponName.X47, ModSounds.X47_FIRE);
        fireDefault(WeaponName.XT7, ModSounds.XT7_FIRE);
        fireDefault(WeaponName.Z6_ROTARY, ModSounds.Z6_ROTARY_FIRE);
        fireDefault(WeaponName.ZB3, ModSounds.ZB3_FIRE);
        fireDefault(WeaponName.ZP20, ModSounds.ZP20_FIRE);
    }

    private static void registerFireOverrides() {
        profile(WeaponName.EC17)
                .fire(FiringMode.STUN, ModSounds.EC17_STUN_FIRE);

        profile(WeaponName.ION_STUNNER)
                .fire(ModSounds.ION_STUNNER_FIRE)
                .fire(FiringMode.STUN, ModSounds.ION_STUNNER_FIRE);

        profile(WeaponName.A180)
                .fire(ModSounds.A180_PISTOL_FIRE)
                .fire(FiringMode.FULL_AUTO, ModSounds.A180_RIFLE_FIRE)
                .fire(FiringMode.SNIPER, ModSounds.A180_SNIPER_FIRE);

        profile(WeaponName.A280CFE)
                .fire(ModSounds.A280CFE_PISTOL_FIRE)
                .fire(FiringMode.BURST, ModSounds.A280CFE_RIFLE_FIRE)
                .fire(FiringMode.SNIPER, ModSounds.A280CFE_SNIPER_FIRE);

        profile(WeaponName.BLNDRBUS)
                .fire(ModSounds.BLNDRBUS_FIRE)
                .fire(FiringMode.REPULSE, ModSounds.BLNDRBUS_REPULSE_FIRE);

        profile(WeaponName.BK28)
                .fire(ModSounds.BK28_FIRE)
                .fire(FiringMode.CHARGENSHOOT, ModSounds.BK28_CHARGED_FIRE);

        profile(WeaponName.BOWCASTER)
                .fire(ModSounds.BOWCASTER_FIRE)
                .fire(FiringMode.CHARGENSHOOT, ModSounds.BOWCASTER_CHARGED_FIRE);

        profile(WeaponName.BRYAR_RIFLE)
                .fire(ModSounds.BRYAR_RIFLE_FIRE)
                .fire(FiringMode.CHARGENSHOOT, ModSounds.BRYAR_RIFLE_CHARGED_FIRE)
                .fire(FiringMode.CHARGENSHOOTONRELEASE, ModSounds.BRYAR_RIFLE_CHARGED_FIRE);

        profile(WeaponName.BM107)
                .fire(ModSounds.BM107_FIRE)
                .fire(FiringMode.CHARGENSHOOT, ModSounds.BM107_CHARGED_FIRE);

        profile(WeaponName.BX49)
                .fire(ModSounds.BX49_FIRE)
                .fire(FiringMode.LAUNCHER, ModSounds.BX49_LAUNCHER_FIRE);

        profile(WeaponName.C10)
                .fire(ModSounds.C10_FIRE)
                .fire(FiringMode.CHARGENSHOOTONRELEASE, ModSounds.C10_CHARGED_FIRE);

        profile(WeaponName.CA87)
                .fire(ModSounds.CA87_FIRE)
                .fire(FiringMode.REPULSE, ModSounds.CA87_REPULSE_FIRE)
                .fire(FiringMode.STUN, ModSounds.CA87_STUN_FIRE);

        profile(WeaponName.CB88)
                .fire(ModSounds.CB88_FIRE)
                .fire(FiringMode.REPULSE, ModSounds.CB88_REPULSE_FIRE)
                .fire(FiringMode.STUN, ModSounds.CB88_STUN_FIRE);

        profile(WeaponName.CH60)
                .fire(ModSounds.CH60_FIRE)
                .fire(FiringMode.STUN, ModSounds.CH60_STUN_FIRE);

        profile(WeaponName.CHARRIC)
                .fire(ModSounds.CHARRIC_FIRE)
                .fire(FiringMode.CHARGENSHOOT, ModSounds.CHARRIC_CHARGED_FIRE);

        profile(WeaponName.CL14)
                .fire(ModSounds.CL14_FIRE)
                .fire(FiringMode.REPULSE, ModSounds.CL14_REPULSE_FIRE)
                .fire(FiringMode.STUN, ModSounds.CL14_STUN_FIRE);

        profile(WeaponName.CP5)
                .fire(ModSounds.CP5_FIRE)
                .fire(FiringMode.STUN, ModSounds.CP5_STUN_FIRE);

        profile(WeaponName.CP6)
                .fire(ModSounds.CP6_FIRE)
                .fire(FiringMode.REPULSE, ModSounds.CP6_REPULSE_FIRE)
                .fire(FiringMode.STUN, ModSounds.CP6_STUN_FIRE);

        profile(WeaponName.CQ29)
                .fire(ModSounds.CQ29_FIRE)
                .fire(FiringMode.REPULSE, ModSounds.CQ29_REPULSE_FIRE)
                .fire(FiringMode.STUN, ModSounds.CQ29_STUN_FIRE);

        profile(WeaponName.CT33)
                .fire(ModSounds.CT33_FIRE)
                .fire(FiringMode.STUN, ModSounds.CT33_STUN_FIRE);

        profile(WeaponName.MOTTO_MK_4)
                .fire(ModSounds.MOTTO_MK_4_FIRE)
                .fire(FiringMode.REPULSE, ModSounds.MOTTO_MK_4_REPULSE_FIRE);

        profile(WeaponName.LV7C)
                .fire(ModSounds.LV7C_FIRE)
                .fire(FiringMode.CHARGENSHOOT, ModSounds.LV7C_CHARGED_FIRE);

        profile(WeaponName.DC15X)
                .fire(ModSounds.DC15X_FIRE)
                .fire(FiringMode.CHARGENSHOOTONRELEASE, ModSounds.DC15X_CHARGED_FIRE);

        profile(WeaponName.DC17M)
                .fire(ModSounds.DC17M_FIRE)
                .fire(FiringMode.LAUNCHER, ModSounds.DC17M_LAUNCHER_FIRE)
                .fire(FiringMode.CHARGENSHOOT, ModSounds.DC17M_CHARGED_FIRE)
                .fire(FiringMode.SNIPER, ModSounds.DC17M_SNIPER_FIRE);

        profile(WeaponName.DL44)
                .fire(ModSounds.DL44_FIRE)
                .fire(FiringMode.SNIPER, ModSounds.DL44_SNIPER_FIRE);

        profile(WeaponName.DLT20A)
                .fire(ModSounds.DLT20A_FIRE)
                .fire(FiringMode.SNIPER, ModSounds.DLT20A_SNIPER_FIRE);

        profile(WeaponName.DP23)
                .fire(ModSounds.DP23_FIRE)
                .fire(FiringMode.CHARGENSHOOT, ModSounds.DP23_CHARGED_FIRE);

        profile(WeaponName.JND41)
                .fire(ModSounds.JND41_FIRE)
                .fire(FiringMode.CHARGENSHOOT, ModSounds.JND41_CHARGED_FIRE);

        profile(WeaponName.GM46)
                .fire(ModSounds.GM46_FIRE)
                .fire(FiringMode.CHARGENSHOOT, ModSounds.GM46_CHARGED_FIRE);

        profile(WeaponName.GRS1)
                .fire(ModSounds.GRS1_FIRE)
                .fire(FiringMode.LAUNCHER, ModSounds.GRS1_FIRE);

        profile(WeaponName.K16_BRYAR_PISTOL)
                .fire(ModSounds.K16_BRYAR_PISTOL_FIRE)
                .fire(FiringMode.CHARGENSHOOTONRELEASE, ModSounds.K16_BRYAR_PISTOL_CHARGED_FIRE);

        profile(WeaponName.MSD32)
                .fire(ModSounds.MSD32_FIRE)
                .fire(FiringMode.CHARGENSHOOT, ModSounds.MSD32_CHARGED_FIRE);

        profile(WeaponName.MW20_BRYAR_PISTOL)
                .fire(ModSounds.MW20_BRYAR_PISTOL_FIRE)
                .fire(FiringMode.CHARGENSHOOTONRELEASE, ModSounds.MW20_BRYAR_PISTOL_CHARGED_FIRE)
                .fire(FiringMode.SNIPER, ModSounds.MW20_BRYAR_SNIPER_FIRE);

        profile(WeaponName.NEO_CRUSADER_RIFLE)
                .fire(ModSounds.NEO_CRUSADER_RIFLE_FIRE)
                .fire(FiringMode.CHARGENSHOOT, ModSounds.NEO_CRUSADER_RIFLE_CHARGED_FIRE);

        profile(WeaponName.NIGHT_STINGER)
                .fire(ModSounds.NIGHT_STINGER_FIRE)
                .fire(FiringMode.CHARGENSHOOTONRELEASE, ModSounds.NIGHT_STINGER_CHARGED_FIRE);

        profile(WeaponName.POWER_5)
                .fire(ModSounds.POWER_5_FIRE)
                .fire(FiringMode.CHARGENSHOOTONRELEASE, ModSounds.POWER_5_CHARGED_FIRE);

        profile(WeaponName.R88)
                .fire(ModSounds.R88_FIRE)
                .fire(FiringMode.LAUNCHER, ModSounds.R88_LAUNCHER_FIRE)
                .fire(FiringMode.REPULSE, ModSounds.R88_REPULSE_FIRE);

        profile(WeaponName.RELBY_K25)
                .fire(ModSounds.RELBY_K25_PISTOL_FIRE)
                .fire(FiringMode.CHARGENSHOOT, ModSounds.RELBY_K25_CHARGED_FIRE)
                .fire(FiringMode.BURST, ModSounds.RELBY_K25_RIFLE_FIRE);

        profile(WeaponName.RELBY_V10)
                .fire(ModSounds.RELBY_V10_FIRE)
                .fire(FiringMode.LAUNCHER, ModSounds.RELBY_V10_LAUNCHER_FIRE)
                .fire(FiringMode.CHARGENSHOOTONRELEASE, ModSounds.RELBY_V10_CHARGED_FIRE);

        profile(WeaponName.SATINES_LAMENT)
                .fire(ModSounds.SATINES_LAMENT_FIRE)
                .fire(FiringMode.CHARGENSHOOT, ModSounds.SATINES_LAMENT_CHARGED_FIRE);

        profile(WeaponName.E9V)
                .fire(ModSounds.E9V_FIRE)
                .fire(FiringMode.CHARGENSHOOT, ModSounds.E9V_CHARGED_FIRE);

        profile(WeaponName.SK32)
                .fire(ModSounds.SK32_FIRE)
                .fire(FiringMode.CHARGENSHOOT, ModSounds.SK32_CHARGED_FIRE);

        profile(WeaponName.TL50)
                .fire(ModSounds.TL50_FIRE)
                .fire(FiringMode.CHARGENSHOOT, ModSounds.TL50_LAUNCHER_FIRE);

        profile(WeaponName.WOOKIE_RIFLE)
                .fire(ModSounds.WOOKIE_RIFLE_FIRE)
                .fire(FiringMode.CHARGENSHOOT, ModSounds.WOOKIE_RIFLE_CHARGED_FIRE);

        profile(WeaponName.WOOKIE_SIDEARM)
                .fire(ModSounds.WOOKIE_SIDEARM_FIRE)
                .fire(FiringMode.CHARGENSHOOT, ModSounds.WOOKIE_SIDEARM_CHARGED_FIRE);

        profile(WeaponName.ZYGERRIAN_BLASTER)
                .fire(ModSounds.ZYGERRIAN_BLASTER_FIRE)
                .fire(FiringMode.CHARGENSHOOT, ModSounds.ZYGERRIAN_BLASTER_CHARGED_FIRE);
    }

    private static void registerReloadProfiles() {
        profile(WeaponName.AMBAN_DISRUPTOR)
                .reload(ModSounds.FOLEY_AMBAN_DISRUPTOR_SNIPER_RIFLE_RELOAD);

        profile(WeaponName.DC17M)
                .reload(ModSounds.FOLEY_DC17M_RELOAD)
                .reload(FiringMode.LAUNCHER, ModSounds.FOLEY_DC17M_LAUNCHER_RELOAD);

        profile(WeaponName.BX49)
                .reload(FiringMode.LAUNCHER, ModSounds.FOLEY_BX49_LAUNCHER_RELOAD);

        profile(WeaponName.DT29)
                .reload(ModSounds.FOLEY_DT29_RELOAD);

        profile(WeaponName.RELBY_V10)
                .reload(FiringMode.LAUNCHER, ModSounds.FOLEY_RELBY_V10_LAUNCHER_RELOAD);

        profile(WeaponName.R88)
                .reload(FiringMode.LAUNCHER, ModSounds.FOLEY_R88_LAUNCHER_RELOAD);

        profile(WeaponName.K21C_PORTABLE_ORDANANCE_LAUNCHER)
                .reload(ModSounds.FOLEY_LARGE_LAUNCHER_RELOAD);
    }

    private static void registerSwitchModeProfiles() {
        profile(WeaponName.A180)
                .switchMode(ModSounds.FOLEY_SMALL_SWITCH_FIRE_MODE)
                .switchMode(FiringMode.SEMI_AUTO, ModSounds.FOLEY_A180_RIFLE_SWITCH_FIRE_MODE)
                .switchMode(FiringMode.FULL_AUTO, ModSounds.FOLEY_A180_SNIPER_SWITCH_FIRE_MODE)
                .switchMode(FiringMode.SNIPER, ModSounds.FOLEY_A180_PISTOL_SWITCH_FIRE_MODE);

        profile(WeaponName.A280CFE)
                .switchMode(ModSounds.FOLEY_A280CFE_PISTOL_SWITCH_FIRE_MODE)
                .switchMode(FiringMode.SEMI_AUTO, ModSounds.FOLEY_A280CFE_RIFLE_SWITCH_FIRE_MODE)
                .switchMode(FiringMode.BURST, ModSounds.FOLEY_A280CFE_SNIPER_SWITCH_FIRE_MODE);

        profile(WeaponName.AMBAN_DISRUPTOR)
                .switchMode(ModSounds.FOLEY_AMBAN_DISRUPTOR_SNIPER_RIFLE_SWITCH_FIRE_MODE);

        profile(WeaponName.B1NA)
                .switchMode(ModSounds.FOLEY_B1NA_SWITCH_FIRE_MODE);

        profile(WeaponName.DC17M)
                .switchMode(ModSounds.FOLEY_MEDIUM_SWITCH_FIRE_MODE)
                .switchMode(FiringMode.FULL_AUTO, ModSounds.FOLEY_DC17M_SNIPER_SWITCH_FIRE_MODE)
                .switchMode(FiringMode.SNIPER, ModSounds.FOLEY_DC17M_LAUNCHER_SWITCH_FIRE_MODE)
                .switchMode(FiringMode.LAUNCHER, ModSounds.FOLEY_DC17M_RIFLE_SWITCH_FIRE_MODE);

        profile(WeaponName.DL44)
                .switchMode(ModSounds.FOLEY_DL44_SWITCH_FIRE_MODE);

        profile(WeaponName.MW20_BRYAR_PISTOL)
                .switchMode(ModSounds.FOLEY_MW20_BRYAR_PISTOL_SWITCH_FIRE_MODE);

        profile(WeaponName.RELBY_K25)
                .switchMode(ModSounds.FOLEY_RELBY_K25_PISTOL_SWITCH_FIRE_MODE)
                .switchMode(FiringMode.SEMI_AUTO, ModSounds.FOLEY_RELBY_K25_CHARGED_SWITCH_FIRE_MODE)
                .switchMode(FiringMode.CHARGENSHOOT, ModSounds.FOLEY_RELBY_K25_RIFLE_SWITCH_FIRE_MODE);
    }

    private static void registerEquipProfiles() {
        profile(WeaponName.AMBAN_DISRUPTOR)
                .equip(ModSounds.FOLEY_AMBAN_DISRUPTOR_SNIPER_RIFLE_EQUIP);

        profile(WeaponName.IB94)
                .equip(ModSounds.FOLEY_IB94_EQUIP);
    }

    private static void registerChargeProfiles() {
        profile(WeaponName.BK28).charge(ModSounds.BK28_CHARGE);
        profile(WeaponName.BM107).charge(ModSounds.BM107_CHARGE);
        profile(WeaponName.BOWCASTER).charge(ModSounds.BOWCASTER_CHARGE);
        profile(WeaponName.BRYAR_RIFLE).charge(ModSounds.BRYAR_RIFLE_CHARGE);
        profile(WeaponName.C10).charge(ModSounds.C10_CHARGE);
        profile(WeaponName.CHARRIC).charge(ModSounds.CHARRIC_CHARGE);
        profile(WeaponName.LV7C).charge(ModSounds.LV7C_CHARGE);
        profile(WeaponName.DC15X).charge(ModSounds.DC15X_CHARGE);
        profile(WeaponName.DC17M).charge(ModSounds.DC17M_CHARGE);
        profile(WeaponName.DN_BOLT_CASTER).charge(ModSounds.DN_BOLT_CASTER_CHARGE);
        profile(WeaponName.DP23).charge(ModSounds.DP23_CHARGE);
        profile(WeaponName.DX2).charge(ModSounds.DX2_CHARGE);
        profile(WeaponName.DXR6).charge(ModSounds.DXR6_CHARGE);
        profile(WeaponName.GM46).charge(ModSounds.GM46_CHARGE);
        profile(WeaponName.JND41).charge(ModSounds.JND41_CHARGE);
        profile(WeaponName.K16_BRYAR_PISTOL).charge(ModSounds.K16_BRYAR_PISTOL_CHARGE);
        profile(WeaponName.MSD32).charge(ModSounds.MSD32_CHARGE);
        profile(WeaponName.MW20_BRYAR_PISTOL).charge(ModSounds.MW20_BRYAR_PISTOL_CHARGE);
        profile(WeaponName.NEO_CRUSADER_RIFLE).charge(ModSounds.NEO_CRUSADER_RIFLE_CHARGE);
        profile(WeaponName.NIGHT_STINGER).charge(ModSounds.NIGHT_STINGER_CHARGE);
        profile(WeaponName.POWER_5).charge(ModSounds.POWER_5_CHARGE);
        profile(WeaponName.RELBY_K25).charge(ModSounds.RELBY_K25_CHARGE);
        profile(WeaponName.RELBY_V10).charge(ModSounds.RELBY_V10_CHARGE);
        profile(WeaponName.SATINES_LAMENT).charge(ModSounds.SATINES_LAMENT_CHARGE);
        profile(WeaponName.E9V).charge(ModSounds.E9V_CHARGE);
        profile(WeaponName.SK32).charge(ModSounds.SK32_CHARGE);
        profile(WeaponName.T7_ION_DISRUPTOR).charge(ModSounds.T7_ION_DISRUPTOR_CHARGE);
        profile(WeaponName.WOOKIE_RIFLE).charge(ModSounds.WOOKIE_RIFLE_CHARGE);
        profile(WeaponName.WOOKIE_SIDEARM).charge(ModSounds.WOOKIE_SIDEARM_CHARGE);
        profile(WeaponName.Z6_ROTARY).charge(ModSounds.Z6_ROTARY_CHARGE);
        profile(WeaponName.ZYGERRIAN_BLASTER).charge(ModSounds.ZYGERRIAN_BLASTER_CHARGE);
        profile(WeaponName.BK28).uncharge(ModSounds.BK28_UNCHARGE);
        profile(WeaponName.BM107).uncharge(ModSounds.BM107_UNCHARGE);
        profile(WeaponName.BOWCASTER).uncharge(ModSounds.BOWCASTER_UNCHARGE);
        profile(WeaponName.BRYAR_RIFLE).uncharge(ModSounds.BRYAR_RIFLE_UNCHARGE);
        profile(WeaponName.C10).uncharge(ModSounds.C10_UNCHARGE);
        profile(WeaponName.CHARRIC).uncharge(ModSounds.CHARRIC_UNCHARGE);
        profile(WeaponName.LV7C).uncharge(ModSounds.LV7C_UNCHARGE);
        profile(WeaponName.DC15X).uncharge(ModSounds.DC15X_UNCHARGE);
        profile(WeaponName.DC17M).uncharge(ModSounds.DC17M_UNCHARGE);
        profile(WeaponName.DN_BOLT_CASTER).uncharge(ModSounds.DN_BOLT_CASTER_UNCHARGE);
        profile(WeaponName.DP23).uncharge(ModSounds.DP23_UNCHARGE);
        profile(WeaponName.DX2).uncharge(ModSounds.DX2_UNCHARGE);
        profile(WeaponName.DXR6).uncharge(ModSounds.DXR6_UNCHARGE);
        profile(WeaponName.GM46).uncharge(ModSounds.GM46_UNCHARGE);
        profile(WeaponName.JND41).uncharge(ModSounds.JND41_UNCHARGE);
        profile(WeaponName.K16_BRYAR_PISTOL).uncharge(ModSounds.K16_BRYAR_PISTOL_UNCHARGE);
        profile(WeaponName.MSD32).uncharge(ModSounds.MSD32_UNCHARGE);
        profile(WeaponName.MW20_BRYAR_PISTOL).uncharge(ModSounds.MW20_BRYAR_PISTOL_UNCHARGE);
        profile(WeaponName.NEO_CRUSADER_RIFLE).uncharge(ModSounds.NEO_CRUSADER_RIFLE_UNCHARGE);
        profile(WeaponName.NIGHT_STINGER).uncharge(ModSounds.NIGHT_STINGER_UNCHARGE);
        profile(WeaponName.POWER_5).uncharge(ModSounds.POWER_5_UNCHARGE);
        profile(WeaponName.RELBY_K25).uncharge(ModSounds.RELBY_K25_UNCHARGE);
        profile(WeaponName.RELBY_V10).uncharge(ModSounds.RELBY_V10_UNCHARGE);
        profile(WeaponName.SATINES_LAMENT).uncharge(ModSounds.SATINES_LAMENT_UNCHARGE);
        profile(WeaponName.E9V).uncharge(ModSounds.E9V_UNCHARGE);
        profile(WeaponName.SK32).uncharge(ModSounds.SK32_UNCHARGE);
        profile(WeaponName.T7_ION_DISRUPTOR).uncharge(ModSounds.T7_ION_DISRUPTOR_UNCHARGE);
        profile(WeaponName.WOOKIE_RIFLE).uncharge(ModSounds.WOOKIE_RIFLE_UNCHARGE);
        profile(WeaponName.WOOKIE_SIDEARM).uncharge(ModSounds.WOOKIE_SIDEARM_UNCHARGE);
        profile(WeaponName.Z6_ROTARY).uncharge(ModSounds.Z6_ROTARY_UNCHARGE);
        profile(WeaponName.ZYGERRIAN_BLASTER).uncharge(ModSounds.ZYGERRIAN_BLASTER_UNCHARGE);
        profile(WeaponName.DC15X).chargeLoop(ModSounds.DC15X_CHARGE_LOOP);
        profile(WeaponName.K16_BRYAR_PISTOL).chargeLoop(ModSounds.K16_BRYAR_PISTOL_CHARGE_LOOP);
        profile(WeaponName._773_FIREPUNCHER).beam(ModSounds.K16_BRYAR_PISTOL_CHARGE_LOOP);
        profile(WeaponName.DC12U).beam(ModSounds.K16_BRYAR_PISTOL_CHARGE_LOOP);
    }

    private static WeaponSoundProfile profile(WeaponName weaponName) {
        return PROFILES.computeIfAbsent(weaponName, ignored -> new WeaponSoundProfile());
    }

    private static void fireDefault(WeaponName weaponName, Supplier<SoundEvent> sound) {
        profile(weaponName).fire(sound);
    }

    private static SoundEvent getDefaultReloadSound(WeaponName weaponName) {
        if (LARGE_WEAPONS.contains(weaponName)) {
            return ModSounds.FOLEY_LARGE_RELOAD_GAS.get();
        }

        if (SMALL_WEAPONS.contains(weaponName)) {
            return ModSounds.FOLEY_SMALL_RELOAD_GAS.get();
        }

        return ModSounds.FOLEY_MEDIUM_RELOAD_GAS.get();
    }

    private static SoundEvent getDefaultSwitchModeSound(WeaponName weaponName) {
        if (LARGE_WEAPONS.contains(weaponName)) {
            return ModSounds.FOLEY_LARGE_SWITCH_FIRE_MODE.get();
        }

        if (SMALL_WEAPONS.contains(weaponName)) {
            return ModSounds.FOLEY_SMALL_SWITCH_FIRE_MODE.get();
        }

        return ModSounds.FOLEY_MEDIUM_SWITCH_FIRE_MODE.get();
    }

    private static SoundEvent getDefaultEquipSound(WeaponName weaponName) {
        if (SCATTER_EQUIP_WEAPONS.contains(weaponName)) {
            return ModSounds.FOLEY_SCATTER_SHOT_EQUIP.get();
        }

        if (LARGE_WEAPONS.contains(weaponName)) {
            return ModSounds.FOLEY_LARGE_EQUIP.get();
        }

        if (SMALL_WEAPONS.contains(weaponName)) {
            return getRandomSmallEquipSound();
        }

        return ModSounds.FOLEY_MEDIUM_EQUIP.get();
    }

    private static SoundEvent getDefaultUnequipSound(WeaponName weaponName) {
        if (LARGE_WEAPONS.contains(weaponName)) {
            return ModSounds.FOLEY_LARGE_UNEQUIP.get();
        }

        if (SMALL_WEAPONS.contains(weaponName)) {
            return getRandomSmallUnequipSound();
        }

        return ModSounds.FOLEY_MEDIUM_UNEQUIP.get();
    }

    private static SoundEvent getRandomSmallEquipSound() {
        int roll = ThreadLocalRandom.current().nextInt(60);

        if (roll == 0) {
            return ModSounds.FOLEY_SMALL_FLORISH_EQUIP.get();
        }

        if (roll <= 3) {
            return ModSounds.FOLEY_SMALL_QUICK_EQUIP.get();
        }

        return ModSounds.FOLEY_SMALL_EQUIP.get();
    }

    private static SoundEvent getRandomSmallUnequipSound() {
        int roll = ThreadLocalRandom.current().nextInt(60);

        if (roll == 0) {
            return ModSounds.FOLEY_SMALL_FLORISH_UNEQUIP.get();
        }

        return ModSounds.FOLEY_SMALL_UNEQUIP.get();
    }

    private static EnumSet<WeaponName> set(WeaponName... weaponNames) {
        EnumSet<WeaponName> set = EnumSet.noneOf(WeaponName.class);
        Collections.addAll(set, weaponNames);
        return set;
    }

    private static final class WeaponSoundProfile {
        private Supplier<SoundEvent> fireSound;
        private Supplier<SoundEvent> reloadSound;
        private Supplier<SoundEvent> switchModeSound;
        private Supplier<SoundEvent> equipSound;
        private Supplier<SoundEvent> unequipSound;
        private Supplier<SoundEvent> chargeSound;
        private Supplier<SoundEvent> unchargeSound;
        private Supplier<SoundEvent> chargeLoopSound;
        private Supplier<SoundEvent> beamSound;

        private final EnumMap<FiringMode, Supplier<SoundEvent>> fireModeSounds = new EnumMap<>(FiringMode.class);
        private final EnumMap<FiringMode, Supplier<SoundEvent>> reloadModeSounds = new EnumMap<>(FiringMode.class);
        private final EnumMap<FiringMode, Supplier<SoundEvent>> switchModeSounds = new EnumMap<>(FiringMode.class);

        private WeaponSoundProfile fire(Supplier<SoundEvent> sound) {
            this.fireSound = sound;
            return this;
        }

        private WeaponSoundProfile fire(FiringMode firingMode, Supplier<SoundEvent> sound) {
            this.fireModeSounds.put(firingMode, sound);
            return this;
        }

        private WeaponSoundProfile reload(Supplier<SoundEvent> sound) {
            this.reloadSound = sound;
            return this;
        }

        private WeaponSoundProfile reload(FiringMode firingMode, Supplier<SoundEvent> sound) {
            this.reloadModeSounds.put(firingMode, sound);
            return this;
        }

        private WeaponSoundProfile switchMode(Supplier<SoundEvent> sound) {
            this.switchModeSound = sound;
            return this;
        }

        private WeaponSoundProfile switchMode(FiringMode firingMode, Supplier<SoundEvent> sound) {
            this.switchModeSounds.put(firingMode, sound);
            return this;
        }

        private WeaponSoundProfile equip(Supplier<SoundEvent> sound) {
            this.equipSound = sound;
            return this;
        }

        private WeaponSoundProfile unequip(Supplier<SoundEvent> sound) {
            this.unequipSound = sound;
            return this;
        }

        private WeaponSoundProfile charge(Supplier<SoundEvent> sound) {
            this.chargeSound = sound;
            return this;
        }

        private WeaponSoundProfile uncharge(Supplier<SoundEvent> sound) {
            this.unchargeSound = sound;
            return this;
        }

        private WeaponSoundProfile chargeLoop(Supplier<SoundEvent> sound) {
            this.chargeLoopSound = sound;
            return this;
        }

        private WeaponSoundProfile beam(Supplier<SoundEvent> sound) {
            this.beamSound = sound;
            return this;
        }

        private SoundEvent resolveFire(FiringMode firingMode, Supplier<SoundEvent> fallback) {
            return resolve(fireModeSounds.get(firingMode), fireSound, fallback);
        }

        private SoundEvent resolveReload(FiringMode firingMode, Supplier<SoundEvent> fallback) {
            return resolve(reloadModeSounds.get(firingMode), reloadSound, fallback);
        }

        private SoundEvent resolveSwitchMode(FiringMode firingMode, Supplier<SoundEvent> fallback) {
            return resolve(switchModeSounds.get(firingMode), switchModeSound, fallback);
        }

        private SoundEvent resolveEquip(Supplier<SoundEvent> fallback) {
            return resolve(equipSound, fallback);
        }

        private SoundEvent resolveUnequip(Supplier<SoundEvent> fallback) {
            return resolve(unequipSound, fallback);
        }

        private SoundEvent resolveCharge(Supplier<SoundEvent> fallback) {
            return resolve(chargeSound, fallback);
        }

        private SoundEvent resolveUncharge(Supplier<SoundEvent> fallback) {
            return resolve(unchargeSound, fallback);
        }

        private SoundEvent resolveChargeLoop(Supplier<SoundEvent> fallback) {
            return resolve(chargeLoopSound, fallback);
        }

        private SoundEvent resolveBeam(Supplier<SoundEvent> fallback) {
            return resolve(beamSound, fallback);
        }

        private boolean hasFireModeSound(FiringMode firingMode) {
            return fireModeSounds.containsKey(firingMode);
        }

        private static SoundEvent resolve(Supplier<SoundEvent> primary, Supplier<SoundEvent> secondary, Supplier<SoundEvent> fallback) {
            if (primary != null) return primary.get();
            if (secondary != null) return secondary.get();
            return fallback.get();
        }

        private static SoundEvent resolve(Supplier<SoundEvent> sound, Supplier<SoundEvent> fallback) {
            return sound != null ? sound.get() : fallback.get();
        }
    }
}
