package net.uhhitscam.knightfall.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.uhhitscam.knightfall.OperationKnightfall;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, OperationKnightfall.MODID);

    public static final Supplier<CreativeModeTab> HAND_BLASTERS =
            CREATIVE_MODE_TABS.register("hand_blasters", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.knightfall.hand_blasters"))
                    .icon(() -> new ItemStack(ModItems.DL44.get()))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems._22T4);
                        pOutput.accept(ModItems._434_DEATHHAMMER);
                        pOutput.accept(ModItems.A2H);
                        pOutput.accept(ModItems.A140);
                        pOutput.accept(ModItems.A180);
                        pOutput.accept(ModItems.A240);
                        pOutput.accept(ModItems.AC177);
                        pOutput.accept(ModItems.APACHE);
                        pOutput.accept(ModItems.ASTRA40);
                        pOutput.accept(ModItems.B1NA);
                        pOutput.accept(ModItems.B22);
                        pOutput.accept(ModItems.B33);
                        pOutput.accept(ModItems.BALNAB_SIDEARM);
                        pOutput.accept(ModItems.BE09);
                        pOutput.accept(ModItems.BH4);
                        pOutput.accept(ModItems.BK28);
                        pOutput.accept(ModItems.BLURRG1120);
                        pOutput.accept(ModItems.BOONTA_BLASTER);
                        pOutput.accept(ModItems.BR14);
                        pOutput.accept(ModItems.BT06);
                        pOutput.accept(ModItems.C10);
                        pOutput.accept(ModItems.CC19);
                        pOutput.accept(ModItems.CC420);
                        pOutput.accept(ModItems.CORE_J3);
                        pOutput.accept(ModItems.CORE_R5);
                        pOutput.accept(ModItems.CORE_U12);
                        pOutput.accept(ModItems.CR2);
                        pOutput.accept(ModItems.CS14);
                        pOutput.accept(ModItems.CW24);
                        pOutput.accept(ModItems.CW76);
                        pOutput.accept(ModItems.DC15S_SIDEARM);
                        pOutput.accept(ModItems.DC17);
                        pOutput.accept(ModItems.DC17S);
                        pOutput.accept(ModItems.DCX);
                        pOutput.accept(ModItems.DE10);
                        pOutput.accept(ModItems.DEACTIVATOR);
                        pOutput.accept(ModItems.DER4);
                        pOutput.accept(ModItems.DG29);
                        pOutput.accept(ModItems.DH16);
                        pOutput.accept(ModItems.DH17);
                        pOutput.accept(ModItems.DH23);
                        pOutput.accept(ModItems.DH42);
                        pOutput.accept(ModItems.DL11);
                        pOutput.accept(ModItems.DL18);
                        pOutput.accept(ModItems.DL21);
                        pOutput.accept(ModItems.DL44);
                        pOutput.accept(ModItems.DT12);
                        pOutput.accept(ModItems.DT15);
                        pOutput.accept(ModItems.DT29);
                        pOutput.accept(ModItems.DUJ3);
                        pOutput.accept(ModItems.DX13);
                        pOutput.accept(ModItems.E11P);
                        pOutput.accept(ModItems.EC17);
                        pOutput.accept(ModItems.EL5);
                        pOutput.accept(ModItems.ELG3A);
                        pOutput.accept(ModItems.EMG2);
                        pOutput.accept(ModItems.F2L);
                        pOutput.accept(ModItems.F38G);
                        pOutput.accept(ModItems.FC29);
                        pOutput.accept(ModItems.FN57);
                        pOutput.accept(ModItems.FP45);
                        pOutput.accept(ModItems.GA3R);
                        pOutput.accept(ModItems.GL77);
                        pOutput.accept(ModItems.GM46);
                        pOutput.accept(ModItems.GR4_ST);
                        pOutput.accept(ModItems.GRN4);
                        pOutput.accept(ModItems.HF94);
                        pOutput.accept(ModItems.HT9);
                        pOutput.accept(ModItems.IB94);
                        pOutput.accept(ModItems.ION_STUNNER);
                        pOutput.accept(ModItems.K16_BRYAR_PISTOL);
                        pOutput.accept(ModItems.K63R);
                        pOutput.accept(ModItems.KL9);
                        pOutput.accept(ModItems.KM9);
                        pOutput.accept(ModItems.KUEGET_LN21);
                        pOutput.accept(ModItems.KYD21);
                        pOutput.accept(ModItems.LL30);
                        pOutput.accept(ModItems.LP_LAW);
                        pOutput.accept(ModItems.LV7C);
                        pOutput.accept(ModItems.LW896);
                        pOutput.accept(ModItems.MARG_MCM);
                        pOutput.accept(ModItems.MODEL_57);
                        pOutput.accept(ModItems.MW20_BRYAR_PISTOL);
                        pOutput.accept(ModItems.P224);
                        pOutput.accept(ModItems.PD44);
                        pOutput.accept(ModItems.POWER_5);
                        pOutput.accept(ModItems.PR9);
                        pOutput.accept(ModItems.PRD8);
                        pOutput.accept(ModItems.Q2);
                        pOutput.accept(ModItems.RD2B);
                        pOutput.accept(ModItems.RD6);
                        pOutput.accept(ModItems.RELBY_K23);
                        pOutput.accept(ModItems.RENEGADE);
                        pOutput.accept(ModItems.RG4D);
                        pOutput.accept(ModItems.RIG420);
                        pOutput.accept(ModItems.RK2P);
                        pOutput.accept(ModItems.RK3);
                        pOutput.accept(ModItems.RLR_MK_II);
                        pOutput.accept(ModItems.RM_1P);
                        pOutput.accept(ModItems.RM7);
                        pOutput.accept(ModItems.RSKF44);
                        pOutput.accept(ModItems.S2S);
                        pOutput.accept(ModItems.S3_MK_5);
                        pOutput.accept(ModItems.S5);
                        pOutput.accept(ModItems.S195);
                        pOutput.accept(ModItems.SACROS_K11);
                        pOutput.accept(ModItems.SATINES_LAMENT);
                        pOutput.accept(ModItems.SE9V);
                        pOutput.accept(ModItems.SE14R);
                        pOutput.accept(ModItems.SEDGLEYS_MK_5);
                        pOutput.accept(ModItems.SEL3);
                        pOutput.accept(ModItems.SETTLERS_STUN);
                        pOutput.accept(ModItems.SHARD3A);
                        pOutput.accept(ModItems.SK32);
                        pOutput.accept(ModItems.SNUB_BLASTER);
                        pOutput.accept(ModItems.SNUBBLE);
                        pOutput.accept(ModItems.SONIC_BLASTER_PISTOL);
                        pOutput.accept(ModItems.SONIC_STUNNER);
                        pOutput.accept(ModItems.SS410);
                        pOutput.accept(ModItems.T4W1);
                        pOutput.accept(ModItems.T6);
                        pOutput.accept(ModItems.TG446);
                        pOutput.accept(ModItems.UMBARAN_BLASTER);
                        pOutput.accept(ModItems.UTK3);
                        pOutput.accept(ModItems.VILMARHS_REVENGE);
                        pOutput.accept(ModItems.VM19);
                        pOutput.accept(ModItems.VT20);
                        pOutput.accept(ModItems.W50S);
                        pOutput.accept(ModItems.W310);
                        pOutput.accept(ModItems.W340LM);
                        pOutput.accept(ModItems.WEEQUAY_PISTOL);
                        pOutput.accept(ModItems.WESTAR2L);
                        pOutput.accept(ModItems.WESTAR_20);
                        pOutput.accept(ModItems.WESTAR_33);
                        pOutput.accept(ModItems.WESTAR_34);
                        pOutput.accept(ModItems.WESTAR_35);
                        pOutput.accept(ModItems.WOOKIE_SIDEARM);
                        pOutput.accept(ModItems.WS4);
                        pOutput.accept(ModItems.X8_NIGHT_SNIPER);
                        pOutput.accept(ModItems.X30);
                        pOutput.accept(ModItems.ZP20);
                    }).build());

    public static final Supplier<CreativeModeTab> CARBINE_BLASTERS =
            CREATIVE_MODE_TABS.register("carbine_blasters", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.knightfall.carbine_blasters"))
                    .icon(() -> new ItemStack(ModItems.EE3.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "hand_blasters"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.CH60);
                        pOutput.accept(ModItems.CP5);
                        pOutput.accept(ModItems.CQ29);
                        pOutput.accept(ModItems.CT33);
                        pOutput.accept(ModItems.DC15S_CARBINE);
                        pOutput.accept(ModItems.DC19);
                        pOutput.accept(ModItems.DLS12);
                        pOutput.accept(ModItems.E5_CARBINE);
                        pOutput.accept(ModItems.E11_CARBINE);
                        pOutput.accept(ModItems.E11D);
                        pOutput.accept(ModItems.EE3);
                        pOutput.accept(ModItems.EE4);
                        pOutput.accept(ModItems.ESB3);
                        pOutput.accept(ModItems.G433);
                        pOutput.accept(ModItems.GALAAR15);
                        pOutput.accept(ModItems.LJ40);
                        pOutput.accept(ModItems.OK98);
                        pOutput.accept(ModItems.SWE1);
                    }).build());

    public static final Supplier<CreativeModeTab> RIFLE_BLASTERS =
            CREATIVE_MODE_TABS.register("rifle_blasters", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.knightfall.rifle_blasters"))
                    .icon(() -> new ItemStack(ModItems.A280.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "carbine_blasters"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.A280);
                        pOutput.accept(ModItems.A280C);
                        pOutput.accept(ModItems.A280CFE);
                        pOutput.accept(ModItems.A295);
                        pOutput.accept(ModItems.A300);
                        pOutput.accept(ModItems.A310);
                        pOutput.accept(ModItems.A350);
                        pOutput.accept(ModItems.AB75_BO_RIFLE);
                        pOutput.accept(ModItems.ABR2_ZATO);
                        pOutput.accept(ModItems.AZ6);
                        pOutput.accept(ModItems.B1X);
                        pOutput.accept(ModItems.BAC5);
                        pOutput.accept(ModItems.BE29);
                        pOutput.accept(ModItems.BK43);
                        pOutput.accept(ModItems.BLASTER_SPEAR);
                        pOutput.accept(ModItems.BM107);
                        pOutput.accept(ModItems.BOILER_RIFLE);
                        pOutput.accept(ModItems.BRYAR_RIFLE);
                        pOutput.accept(ModItems.CHARRIC);
                        pOutput.accept(ModItems.CJ9_BO_RIFLE);
                        pOutput.accept(ModItems.CORPO_RIFLE);
                        pOutput.accept(ModItems.DC12U);
                        pOutput.accept(ModItems.DC15A);
                        pOutput.accept(ModItems.DC15LE);
                        pOutput.accept(ModItems.DEFTECH);
                        pOutput.accept(ModItems.DL23);
                        pOutput.accept(ModItems.DLT15);
                        pOutput.accept(ModItems.DLT18);
                        pOutput.accept(ModItems.DLT19);
                        pOutput.accept(ModItems.DLT19D);
                        pOutput.accept(ModItems.DLT20A);
                        pOutput.accept(ModItems.DP23);
                        pOutput.accept(ModItems.DT57);
                        pOutput.accept(ModItems.DUL4);
                        pOutput.accept(ModItems.E5);
                        pOutput.accept(ModItems.E5_BX);
                        pOutput.accept(ModItems.E5_CE);
                        pOutput.accept(ModItems.E5C);
                        pOutput.accept(ModItems.E5T);
                        pOutput.accept(ModItems.E9V);
                        pOutput.accept(ModItems.E10);
                        pOutput.accept(ModItems.E10_5);
                        pOutput.accept(ModItems.E10R);
                        pOutput.accept(ModItems.E11B);
                        pOutput.accept(ModItems.E11_RIFLE);
                        pOutput.accept(ModItems.E11T);
                        pOutput.accept(ModItems.E22);
                        pOutput.accept(ModItems.E44);
                        pOutput.accept(ModItems.GLX_FIRELANCE);
                        pOutput.accept(ModItems.GRS1);
                        pOutput.accept(ModItems.HB9);
                        pOutput.accept(ModItems.J19_BO_RIFLE);
                        pOutput.accept(ModItems.JND41);
                        pOutput.accept(ModItems.K13);
                        pOutput.accept(ModItems.KA74);
                        pOutput.accept(ModItems.KINETICBLAST);
                        pOutput.accept(ModItems.L5);
                        pOutput.accept(ModItems.L60);
                        pOutput.accept(ModItems.LJ50);
                        pOutput.accept(ModItems.M12);
                        pOutput.accept(ModItems.MK3T);
                        pOutput.accept(ModItems.MK_II_PALADIN);
                        pOutput.accept(ModItems.MOTTO_MK_4);
                        pOutput.accept(ModItems.NEO_CRUSADER_RIFLE);
                        pOutput.accept(ModItems.PRD58);
                        pOutput.accept(ModItems.PRD62);
                        pOutput.accept(ModItems.QUARREN_RIFLE);
                        pOutput.accept(ModItems.R88);
                        pOutput.accept(ModItems.RELBY_K25);
                        pOutput.accept(ModItems.SONIC_BLASTER);
                        pOutput.accept(ModItems.SWE2);
                        pOutput.accept(ModItems.T21B);
                        pOutput.accept(ModItems.THUNDERBLASTER);
                        pOutput.accept(ModItems.W90);
                        pOutput.accept(ModItems.W210);
                        pOutput.accept(ModItems.WESTARE9);
                        pOutput.accept(ModItems.WESTARL4);
                        pOutput.accept(ModItems.WESTARLVN);
                        pOutput.accept(ModItems.WESTARM5);
                        pOutput.accept(ModItems.WOOKIE_RIFLE);
                        pOutput.accept(ModItems.XT7);
                        pOutput.accept(ModItems.ZYGERRIAN_BLASTER);
                    }).build());

    public static final Supplier<CreativeModeTab> REPEATING_BLASTERS =
            CREATIVE_MODE_TABS.register("repeating_blasters", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.knightfall.repeating_blasters"))
                    .icon(() -> new ItemStack(ModItems.RT97C.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "rifle_blasters"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.ACP_ARRAY);
                        pOutput.accept(ModItems.ACP_REPEATER);
                        pOutput.accept(ModItems.DC17M);
                        pOutput.accept(ModItems.E5R);
                        pOutput.accept(ModItems.GR13);
                        pOutput.accept(ModItems.LS150);
                        pOutput.accept(ModItems.LZ60);
                        pOutput.accept(ModItems.M32);
                        pOutput.accept(ModItems.M41);
                        pOutput.accept(ModItems.M45);
                        pOutput.accept(ModItems.M55);
                        pOutput.accept(ModItems.M61);
                        pOutput.accept(ModItems.MWC35C);
                        pOutput.accept(ModItems.RECIPROCATING_QUAD_BLASTER_CANNON);
                        pOutput.accept(ModItems.RT97C);
                        pOutput.accept(ModItems.SE14C);
                        pOutput.accept(ModItems.T9K7);
                        pOutput.accept(ModItems.T21);
                        pOutput.accept(ModItems.TL50);
                        pOutput.accept(ModItems.V13);
                        pOutput.accept(ModItems.VULK_TAU623_ROTARY);
                        pOutput.accept(ModItems.Z6_ROTARY);
                        pOutput.accept(ModItems.ZB3);
                    }).build());

    public static final Supplier<CreativeModeTab> SCATTERSHOT_BLASTERS =
            CREATIVE_MODE_TABS.register("scattershot_blasters", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.knightfall.scattershot_blasters"))
                    .icon(() -> new ItemStack(ModItems.CA87.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "repeating_blasters"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.BARMST12);
                        pOutput.accept(ModItems.BLNDRBUS);
                        pOutput.accept(ModItems.BX33);
                        pOutput.accept(ModItems.CA87);
                        pOutput.accept(ModItems.CB88);
                        pOutput.accept(ModItems.CL14);
                        pOutput.accept(ModItems.CP6);
                        pOutput.accept(ModItems.FLITE37);
                        pOutput.accept(ModItems.SNUB_SCATTER);
                        pOutput.accept(ModItems.SX21);
                        pOutput.accept(ModItems.VANGUARD_SCATTER);
                        pOutput.accept(ModItems.WINCHESTER87);
                    }).build());

    public static final Supplier<CreativeModeTab> SNIPER_BLASTERS =
            CREATIVE_MODE_TABS.register("sniper_blasters", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.knightfall.sniper_blasters"))
                    .icon(() -> new ItemStack(ModItems.CYCLER_RIFLE.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "scattershot_blasters"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems._84U_HUNTING_RIFLE);
                        pOutput.accept(ModItems._773_FIREPUNCHER);
                        pOutput.accept(ModItems._785MK_FIREPUNCHERX);
                        pOutput.accept(ModItems.AVARIK);
                        pOutput.accept(ModItems.BALNAB);
                        pOutput.accept(ModItems.BSR7);
                        pOutput.accept(ModItems.BX49);
                        pOutput.accept(ModItems.CYCLER_RIFLE);
                        pOutput.accept(ModItems.DC15X);
                        pOutput.accept(ModItems.DH447);
                        pOutput.accept(ModItems.DFQ91);
                        pOutput.accept(ModItems.DLT19X);
                        pOutput.accept(ModItems.E5S);
                        pOutput.accept(ModItems.E11S);
                        pOutput.accept(ModItems.E17D);
                        pOutput.accept(ModItems.EL244);
                        pOutput.accept(ModItems.F4L);
                        pOutput.accept(ModItems.GALAR90);
                        pOutput.accept(ModItems.GE36);
                        pOutput.accept(ModItems.IQA11);
                        pOutput.accept(ModItems.NIGHT_STINGER);
                        pOutput.accept(ModItems.NT242);
                        pOutput.accept(ModItems.PK23);
                        pOutput.accept(ModItems.PRECISIONX);
                        pOutput.accept(ModItems.RELBY_V10);
                        pOutput.accept(ModItems.SER5);
                        pOutput.accept(ModItems.V850_MK);
                        pOutput.accept(ModItems.VALKEN38X);
                        pOutput.accept(ModItems.WEEQUAY_LANCE);
                        pOutput.accept(ModItems.WEEQUAY_RIFLE);
                        pOutput.accept(ModItems.X45);
                        pOutput.accept(ModItems.X47);
                    }).build());

    public static final Supplier<CreativeModeTab> SLUGTHROWERS =
            CREATIVE_MODE_TABS.register("slugthrowers", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.knightfall.slugthrowers"))
                    .icon(() -> new ItemStack(ModItems.BERSERKER.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "sniper_blasters"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems._62AUG2_HUNTING_RIFLE);
                        pOutput.accept(ModItems.BERSERKER);
                        pOutput.accept(ModItems.CZERKA_19);
                        pOutput.accept(ModItems.CZERKA_ADVENTURER);
                        pOutput.accept(ModItems.DFD1);
                        pOutput.accept(ModItems.DRESSELLIAN_PROJECTILE_RIFLE);
                        pOutput.accept(ModItems.FC1_FLECHETTE_LAUNCHER);
                        pOutput.accept(ModItems.FWG5);
                        pOutput.accept(ModItems.FWG7);
                        pOutput.accept(ModItems.JEZALI_CYCLER_RIFLE);
                        pOutput.accept(ModItems.KISTEER_1284);
                        pOutput.accept(ModItems.NOSLO19);
                        pOutput.accept(ModItems.OUTLAND_RIFLE);
                        pOutput.accept(ModItems.PANIC_PISTOL);
                        pOutput.accept(ModItems.VERPINE_SHATTER_RIFLE);
                        pOutput.accept(ModItems.VERPINE_SIDEARM);
                    }).build());

    public static final Supplier<CreativeModeTab> DISRUPTORS =
            CREATIVE_MODE_TABS.register("disruptors", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.knightfall.disruptors"))
                    .icon(() -> new ItemStack(ModItems.AMBAN_DISRUPTOR.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "slugthrowers"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.AMBAN_DISRUPTOR);
                        pOutput.accept(ModItems.DN_BOLT_CASTER);
                        pOutput.accept(ModItems.DX2);
                        pOutput.accept(ModItems.DXR6);
                        pOutput.accept(ModItems.MSD32);
                        pOutput.accept(ModItems.T7_ION_DISRUPTOR);
                    }).build());

    public static final Supplier<CreativeModeTab> MISC_WEAPONS =
            CREATIVE_MODE_TABS.register("misc_weapons", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.knightfall.misc_weapons"))
                    .icon(() -> new ItemStack(ModItems.GAS_CARTRIDGE.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "disruptors"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.BATON_BLASTER);
                        pOutput.accept(ModItems.BOWCASTER);
                        pOutput.accept(ModItems.TL40);
                        pOutput.accept(ModItems.THERMAL_DETONATOR);
                        pOutput.accept(ModItems.IMPACT_THERMAL_DETONATOR);
                        pOutput.accept(ModItems.GAS_CARTRIDGE);
                        pOutput.accept(ModItems.TIBANNA_GAS);
                        pOutput.accept(ModItems.IONIZED_TIBANNA_GAS);
                        pOutput.accept(ModItems.SPIN_SEALED_TIBANNA_GAS);
                        pOutput.accept(ModItems.TIBANNAX_GAS);
                        pOutput.accept(ModItems.SIG_GAS);
                        pOutput.accept(ModItems.MAGNETIZED_SIG_GAS);
                        pOutput.accept(ModItems.SKEVON);
                        pOutput.accept(ModItems.PLASTIC_SLUG);
                        pOutput.accept(ModItems.CERAMIC_SLUG);
                        pOutput.accept(ModItems.STEEL_SLUG);
                        pOutput.accept(ModItems.RAZOR_STEEL_SLUG);
                        pOutput.accept(ModItems.POISON_TIPPED_STEEL_SLUG);
                        pOutput.accept(ModItems.EXPLOSIVE_TIPPED_STEEL_SLUG);
                        pOutput.accept(ModItems.ION_TIPPED_STEEL_SLUG);
                        pOutput.accept(ModItems.FLECHETTE);
                        pOutput.accept(ModItems.FLECHETTE_TOXIC);
                        pOutput.accept(ModItems.CANISTER);
                        pOutput.accept(ModItems.FLECHETTE_CANISTER);
                        pOutput.accept(ModItems.FLECHETTE_TOXIC_CANISTER);
                        pOutput.accept(ModItems.FLECHETTE_SPREAD_CANISTER);
                        pOutput.accept(ModItems.FLECHETTE_TOXIC_SPREAD_CANISTER);
                    }).build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
