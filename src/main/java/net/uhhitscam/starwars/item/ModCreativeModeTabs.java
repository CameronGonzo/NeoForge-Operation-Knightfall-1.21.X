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
                        pOutput.accept(ModItems.DC17);
                        pOutput.accept(ModItems.DL44);
                        pOutput.accept(ModItems.DH17);
                    }).build());

    public static final Supplier<CreativeModeTab> CARBINE_BLASTERS =
            CREATIVE_MODE_TABS.register("carbine_blasters", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.starwars.carbine_blasters"))
                    .icon(() -> new ItemStack(ModItems.EE3.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "hand_blasters"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.EE3);
                    }).build());

    public static final Supplier<CreativeModeTab> RIFLE_BLASTERS =
            CREATIVE_MODE_TABS.register("rifle_blasters", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.starwars.rifle_blasters"))
                    .icon(() -> new ItemStack(ModItems.EE3.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "carbine_blasters"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(Blocks.DIRT);
                    }).build());

    public static final Supplier<CreativeModeTab> REPEATING_BLASTERS =
            CREATIVE_MODE_TABS.register("repeating_blasters", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.starwars.repeating_blasters"))
                    .icon(() -> new ItemStack(ModItems.EE3.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "rifle_blasters"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(Blocks.DIRT);
                    }).build());

    public static final Supplier<CreativeModeTab> SCATTERSHOT_BLASTERS =
            CREATIVE_MODE_TABS.register("scattershot_blasters", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.starwars.scattershot_blasters"))
                    .icon(() -> new ItemStack(ModItems.EE3.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "repeating_blasters"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(Blocks.DIRT);
                    }).build());

    public static final Supplier<CreativeModeTab> SNIPER_BLASTERS =
            CREATIVE_MODE_TABS.register("sniper_blasters", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.starwars.sniper_blasters"))
                    .icon(() -> new ItemStack(ModItems.EE3.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "scattershot_blasters"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(Blocks.DIRT);
                    }).build());

    public static final Supplier<CreativeModeTab> SLUGTHROWERS =
            CREATIVE_MODE_TABS.register("slugthrowers", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.starwars.slugthrowers"))
                    .icon(() -> new ItemStack(ModItems.EE3.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "sniper_blasters"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(Blocks.DIRT);
                    }).build());

    public static final Supplier<CreativeModeTab> DISRUPTORS =
            CREATIVE_MODE_TABS.register("disruptors", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.starwars.disruptors"))
                    .icon(() -> new ItemStack(ModItems.EE3.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "slugthrowers"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(Blocks.DIRT);
                    }).build());

    public static final Supplier<CreativeModeTab> MISC_WEAPONS =
            CREATIVE_MODE_TABS.register("misc_weapons", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.starwars.misc_weapons"))
                    .icon(() -> new ItemStack(ModItems.GAS_CARTRIDGE.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, "disruptors"))
                    .displayItems((pParameters, pOutput) -> {
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
