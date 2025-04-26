package net.uhhitscam.starwars.util;

import net.uhhitscam.starwars.sound.ModSounds;

public class BlasterTimingUtil {
    public static long getBlasterReloadTime(String blasterName, String blasterFireMode) {
        switch (blasterName) {
            case "amban_disruptor":
                return 20;
            case "dc17m":
                if (blasterFireMode.equals("LAUNCHER")) {
                    return 18;
                } else {
                    return 19;
                }
            case "dt29":
                return 100;
            case "relby_v10":
                if (blasterFireMode.equals("LAUNCHER")) {
                    return 18;
                } else {
                    return 16;
                }
            case "v6d_mortar_launcher", "mortar", "k21c_portable_ordanance_launcher":
                return 18;
            case "lightbow", "vulk_tau623_rotary", "z6_rotary", "eweb", "ewhb12", "m32", "m45", "m55", "m61", "mwc35c", "t21", "t21b",
                 "bm107", "galar90", "nt242", "ge36", "neo_crusader_rifle", "boiler_rifle", "bowcaster", "t7_ion_diruptor":
                return 25;
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
                return 15;
            default:
                return 19;
        }
    }

//    public static long getBlasterSwitchTime(String blasterName, String blasterFireMode) {
//
//    }
}
