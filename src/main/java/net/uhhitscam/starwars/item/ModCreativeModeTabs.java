package net.uhhitscam.starwars.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.uhhitscam.starwars.OperationKnightfall;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, OperationKnightfall.MODID);

    public static final Supplier<CreativeModeTab> HAND_BLASTERS =
            CREATIVE_MODE_TABS.register("hand_blasters", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.starwars.hand_blasters"))
                    .icon(() -> new ItemStack(ModItems.DL44.get()))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems._434_DEATHHAMMER);
                        pOutput.accept(ModItems.A140);
                        pOutput.accept(ModItems.A180);
                        pOutput.accept(ModItems.AC177);
                        pOutput.accept(ModItems.APACHE);
                        pOutput.accept(ModItems.ASTRA40);
                        pOutput.accept(ModItems.B1NA);
                        pOutput.accept(ModItems.B22);
                        pOutput.accept(ModItems.BE09);
                        pOutput.accept(ModItems.BH4);
                        pOutput.accept(ModItems.BLURRG1120);
                        pOutput.accept(ModItems.BOONTA_BLASTER);
                        pOutput.accept(ModItems.BR14);
                        pOutput.accept(ModItems.C10);
                        pOutput.accept(ModItems.C96);
                        pOutput.accept(ModItems.CAIJ_VANDAS_BLASTER_PISTOL);
                        pOutput.accept(ModItems.CC420);
                        pOutput.accept(ModItems.CR2);
                        pOutput.accept(ModItems.CS14);
                        pOutput.accept(ModItems.DC15S_SIDEARM);
                        pOutput.accept(ModItems.DC17);
                        pOutput.accept(ModItems.DC17S);
                        pOutput.accept(ModItems.DE10);
                        pOutput.accept(ModItems.DG29);
                        pOutput.accept(ModItems.DH16);
                        pOutput.accept(ModItems.DH17);
                        pOutput.accept(ModItems.DH23);
                        pOutput.accept(ModItems.DL18);
                        pOutput.accept(ModItems.DL21);
                        pOutput.accept(ModItems.DL44);
                        pOutput.accept(ModItems.DT12);
                        pOutput.accept(ModItems.DT15);
                        pOutput.accept(ModItems.DT29);
                        pOutput.accept(ModItems.DX13);
                        pOutput.accept(ModItems.EC17);
                        pOutput.accept(ModItems.ELG3A);
                        pOutput.accept(ModItems.FLINTLOQ_PISTOL);
                        pOutput.accept(ModItems.FN57);
                        pOutput.accept(ModItems.FP45);
                        pOutput.accept(ModItems.GL77);
                        pOutput.accept(ModItems.HF94);
                        pOutput.accept(ModItems.IB94);
                        pOutput.accept(ModItems.K16_BRYAR_PISTOL);
                        pOutput.accept(ModItems.KOCH9S);
                        pOutput.accept(ModItems.KRIE4);
                        pOutput.accept(ModItems.KUEGET_LN21);
                        pOutput.accept(ModItems.KYD21);
                        pOutput.accept(ModItems.LEUCHT42);
                        pOutput.accept(ModItems.LL30);
                        pOutput.accept(ModItems.LUG_PO8);
                        pOutput.accept(ModItems.LW896);
                        pOutput.accept(ModItems.M19A1);
                        pOutput.accept(ModItems.MARG_MCM);
                        pOutput.accept(ModItems.MODEL_57);
                        pOutput.accept(ModItems.MW20_BRYAR_PISTOL);
                        pOutput.accept(ModItems.NAMBU14);
                        pOutput.accept(ModItems.P38);
                        pOutput.accept(ModItems.PCC_PROJECTOR);
                        pOutput.accept(ModItems.POWER_5);
                        pOutput.accept(ModItems.PREMIER);
                        pOutput.accept(ModItems.Q2);
                        pOutput.accept(ModItems.RELBY_K23);
                        pOutput.accept(ModItems.RENEGADE);
                        pOutput.accept(ModItems.RG4D);
                        pOutput.accept(ModItems.RIG420);
                        pOutput.accept(ModItems.RK3);
                        pOutput.accept(ModItems.RSKF44);
                        pOutput.accept(ModItems.RUGER_BLASTER);
                        pOutput.accept(ModItems.S195);
                        pOutput.accept(ModItems.S5);
                        pOutput.accept(ModItems.SACROS_K11);
                        pOutput.accept(ModItems.SATINES_LAMENT);
                        pOutput.accept(ModItems.SC_X30);
                        pOutput.accept(ModItems.SE14C);
                        pOutput.accept(ModItems.SEREXIM_MK_5);
                        pOutput.accept(ModItems.SEDGLEYS_MK_5);
                        pOutput.accept(ModItems.SHARD3A);
                        pOutput.accept(ModItems.SK32);
                        pOutput.accept(ModItems.SNUBBLE);
                        pOutput.accept(ModItems.STEYR43);
                        pOutput.accept(ModItems.T6);
                        pOutput.accept(ModItems.TCA_PRO);
                        pOutput.accept(ModItems.TYPE14);
                        pOutput.accept(ModItems.UMBARAN_PISTOL);
                        pOutput.accept(ModItems.WALTHER_BLASTER);
                        pOutput.accept(ModItems.WALTHER_LPM_BLASTER);
                        pOutput.accept(ModItems.WEBLY_S4);
                        pOutput.accept(ModItems.WEBTEMP);
                        pOutput.accept(ModItems.WEEQUAY_PISTOL);
                        pOutput.accept(ModItems.WESTAR_20);
                        pOutput.accept(ModItems.WESTAR_34);
                        pOutput.accept(ModItems.WESTAR_35);
                        pOutput.accept(ModItems.X8_NIGHT_SNIPER);
                    }).build());

    public static final Supplier<CreativeModeTab> CARBINE_BLASTERS =
            CREATIVE_MODE_TABS.register("carbine_blasters", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.starwars.carbine_blasters"))
                    .icon(() -> new ItemStack(ModItems.EE3.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "hand_blasters"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.DC15S_CARBINE);
                        pOutput.accept(ModItems.DC19);
                        pOutput.accept(ModItems.DLS12);
                        pOutput.accept(ModItems.E11_CARBINE);
                        pOutput.accept(ModItems.E11D);
                        pOutput.accept(ModItems.EE3);
                        pOutput.accept(ModItems.EE4);
                        pOutput.accept(ModItems.GALAAR15);
                        pOutput.accept(ModItems.OK98);
                        pOutput.accept(ModItems.WESTARM5);
                    }).build());

    public static final Supplier<CreativeModeTab> RIFLE_BLASTERS =
            CREATIVE_MODE_TABS.register("rifle_blasters", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.starwars.rifle_blasters"))
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
                        pOutput.accept(ModItems.AKBARC);
                        pOutput.accept(ModItems.BE29);
                        pOutput.accept(ModItems.BM107);
                        pOutput.accept(ModItems.BOILER_RIFLE);
                        pOutput.accept(ModItems.BRYAR_RIFLE);
                        pOutput.accept(ModItems.BLASTER_SPEAR);
                        pOutput.accept(ModItems.CJ9_BO_RIFLE);
                        pOutput.accept(ModItems.CORPO_RIFLE);
                        pOutput.accept(ModItems.DARK_TROOPER_RIFLE);
                        pOutput.accept(ModItems.DC12U);
                        pOutput.accept(ModItems.DC15A);
                        pOutput.accept(ModItems.DC15LE);
                        pOutput.accept(ModItems.DEFTECH);
                        pOutput.accept(ModItems.DLT18);
                        pOutput.accept(ModItems.DLT19);
                        pOutput.accept(ModItems.DLT19D);
                        pOutput.accept(ModItems.DLT20A);
                        pOutput.accept(ModItems.DP23);
                        pOutput.accept(ModItems.DT57);
                        pOutput.accept(ModItems.E10);
                        pOutput.accept(ModItems.E10_5);
                        pOutput.accept(ModItems.E10R);
                        pOutput.accept(ModItems.E11_RIFLE);
                        pOutput.accept(ModItems.E11B);
                        pOutput.accept(ModItems.E22);
                        pOutput.accept(ModItems.E5);
                        pOutput.accept(ModItems.E5_BX);
                        pOutput.accept(ModItems.E5C);
                        pOutput.accept(ModItems.E5_CE);
                        pOutput.accept(ModItems.IMPERIAL_SUPERCOMMANDO_BLASTER);
                        pOutput.accept(ModItems.JND41);
                        pOutput.accept(ModItems.KA74);
                        pOutput.accept(ModItems.L5);
                        pOutput.accept(ModItems.L60);
                        pOutput.accept(ModItems.M12);
                        pOutput.accept(ModItems.MK_II_PALADIN);
                        pOutput.accept(ModItems.MOTTO_MK_4);
                        pOutput.accept(ModItems.NEO_CRUSADER_RIFLE);
                        pOutput.accept(ModItems.NIGHT_WIND_RIFLE);
                        pOutput.accept(ModItems.QUARREN_RIFLE);
                        pOutput.accept(ModItems.SHADOW_TROOPER_BLASTER);
                        pOutput.accept(ModItems.T21B);
                    }).build());

    public static final Supplier<CreativeModeTab> REPEATING_BLASTERS =
            CREATIVE_MODE_TABS.register("repeating_blasters", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.starwars.repeating_blasters"))
                    .icon(() -> new ItemStack(ModItems.RT97C.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "rifle_blasters"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.ACP_REPEATER);
                        pOutput.accept(ModItems.DC17M);
                        pOutput.accept(ModItems.M32);
                        pOutput.accept(ModItems.M41);
                        pOutput.accept(ModItems.M45);
                        pOutput.accept(ModItems.M55);
                        pOutput.accept(ModItems.M61);
                        pOutput.accept(ModItems.MWC35C);
                        pOutput.accept(ModItems.RECIPROCATING_QUAD_BLASTER_CANNON);
                        pOutput.accept(ModItems.RT97C);
                        pOutput.accept(ModItems.SE14R);
                        pOutput.accept(ModItems.T21);
                        pOutput.accept(ModItems.TL50);
                        pOutput.accept(ModItems.TOMSUN97);
                        pOutput.accept(ModItems.VECT_UZI);
                        pOutput.accept(ModItems.VULK_TAU623_ROTARY);
                        pOutput.accept(ModItems.Z6_ROTARY);
                    }).build());

    public static final Supplier<CreativeModeTab> SCATTERSHOT_BLASTERS =
            CREATIVE_MODE_TABS.register("scattershot_blasters", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.starwars.scattershot_blasters"))
                    .icon(() -> new ItemStack(ModItems.CA87.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "repeating_blasters"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.BARMST12);
                        pOutput.accept(ModItems.BLNDRBUS);
                        pOutput.accept(ModItems.CA87);
                        pOutput.accept(ModItems.FLITE37);
                        pOutput.accept(ModItems.SX21);
                        pOutput.accept(ModItems.VANGUARD_SCATTER);
                        pOutput.accept(ModItems.WINCHESTER87);
                    }).build());

    public static final Supplier<CreativeModeTab> SNIPER_BLASTERS =
            CREATIVE_MODE_TABS.register("sniper_blasters", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.starwars.sniper_blasters"))
                    .icon(() -> new ItemStack(ModItems.CYCLER_RIFLE.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "scattershot_blasters"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems._773_FIREPUNCHER);
                        pOutput.accept(ModItems._785MK_FIREPUNCHERX);
                        pOutput.accept(ModItems._84U_HUNTING_RIFLE);
                        pOutput.accept(ModItems.AVARIK);
                        pOutput.accept(ModItems.BALNAB);
                        pOutput.accept(ModItems.CYCLER_RIFLE);
                        pOutput.accept(ModItems.DC15X);
                        pOutput.accept(ModItems.DH447);
                        pOutput.accept(ModItems.DLT19X);
                        pOutput.accept(ModItems.E11S);
                        pOutput.accept(ModItems.E17D);
                        pOutput.accept(ModItems.E5S);
                        pOutput.accept(ModItems.FLINTLOQ_RIFLE);
                        pOutput.accept(ModItems.GALAR90);
                        pOutput.accept(ModItems.GE36);
                        pOutput.accept(ModItems.IQA11);
                        pOutput.accept(ModItems.NIGHT_STINGER);
                        pOutput.accept(ModItems.NT242);
                        pOutput.accept(ModItems.PK23);
                        pOutput.accept(ModItems.RELBY_V10);
                        pOutput.accept(ModItems.VALKEN38X);
                        pOutput.accept(ModItems.WEEQUAY_LANCE);
                        pOutput.accept(ModItems.WEEQUAY_RIFLE);
                    }).build());

    public static final Supplier<CreativeModeTab> SLUGTHROWERS =
            CREATIVE_MODE_TABS.register("slugthrowers", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.starwars.slugthrowers"))
                    .icon(() -> new ItemStack(ModItems.BERSERKER.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "sniper_blasters"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems._62AUG2_HUNTING_RIFLE);
                        pOutput.accept(ModItems.BERSERKER);
                        pOutput.accept(ModItems.CZERKA_ADVENTURER);
                        pOutput.accept(ModItems.DFD1);
                        pOutput.accept(ModItems.DRESSELLIAN_PROJECTILE_RIFLE);
                        pOutput.accept(ModItems.FC1_FLECHETTE_LAUNCHER);
                        pOutput.accept(ModItems.JEZALI_CYCLER_RIFLE);
                        pOutput.accept(ModItems.KISTEER_1284);
                        pOutput.accept(ModItems.OUTLAND_RIFLE);
                        pOutput.accept(ModItems.PANIC_PISTOL);
                        pOutput.accept(ModItems.VERPINE_SHATTER);
                    }).build());

    public static final Supplier<CreativeModeTab> DISRUPTORS =
            CREATIVE_MODE_TABS.register("disruptors", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.starwars.disruptors"))
                    .icon(() -> new ItemStack(ModItems.AMBAN_DISRUPTOR.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "slugthrowers"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.AMBAN_DISRUPTOR);
                        pOutput.accept(ModItems.DN_BOLT_CASTER);
                        pOutput.accept(ModItems.T7_ION_DISRUPTOR);
                    }).build());

    public static final Supplier<CreativeModeTab> MISC_WEAPONS =
            CREATIVE_MODE_TABS.register("misc_weapons", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.starwars.misc_weapons"))
                    .icon(() -> new ItemStack(ModItems.GAS_CARTRIDGE.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "disruptors"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.BATON_BLASTER);
                        pOutput.accept(ModItems.BOWCASTER);
                        pOutput.accept(ModItems.GAS_CARTRIDGE);
                        pOutput.accept(ModItems.TIBANNA_GAS);
                        pOutput.accept(ModItems.IONIZED_TIBANNA_GAS);
                        pOutput.accept(ModItems.SPIN_SEALED_TIBANNA_GAS);
                        pOutput.accept(ModItems.TIBANNAX_GAS);
                        pOutput.accept(ModItems.SIG_GAS);
                        pOutput.accept(ModItems.MAGNETIZED_SIG_GAS);
                        pOutput.accept(ModItems.SKEVON);
                    }).build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
