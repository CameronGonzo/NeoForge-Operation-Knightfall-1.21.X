package net.uhhitscam.knightfall.block;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(OperationKnightfall.MODID);

    public static final DeferredBlock<Block> BESKAR_ORE = registerOre("beskar_ore");
    public static final DeferredBlock<Block> BRONZIUM_ORE = registerOre("bronzium_ore");
    public static final DeferredBlock<Block> CINNABAR_ORE = registerOre("cinnabar_ore");
    public static final DeferredBlock<Block> CORTOSIS_ORE = registerOre("cortosis_ore");
    public static final DeferredBlock<Block> DEDLANITE_ORE = registerOre("dedlanite_ore");
    public static final DeferredBlock<Block> DIATIUM_ORE = registerOre("diatium_ore");
    public static final DeferredBlock<Block> DURASTEEL_ORE = registerOre("durasteel_ore");
    public static final DeferredBlock<Block> DURITE_ORE = registerOre("durite_ore");
    public static final DeferredBlock<Block> EXONIUM_ORE = registerOre("exonium_ore");
    public static final DeferredBlock<Block> HAYSIAN_SMELT_ORE = registerOre("haysian_smelt_ore");
    public static final DeferredBlock<Block> IPSIUM_ORE = registerOre("ipsium_ore");
    public static final DeferredBlock<Block> KALKITE_ORE = registerOre("kalkite_ore");
    public static final DeferredBlock<Block> MALSARR_ORE = registerOre("malsarr_ore");
    public static final DeferredBlock<Block> PHRIK_ORE = registerOre("phrik_ore");
    public static final DeferredBlock<Block> TITANIUM_ORE = registerOre("titanium_ore");

    private static DeferredBlock<Block> registerOre(String name) {
        return registerBlock(name, () -> new DropExperienceBlock(UniformInt.of(2, 5),
                BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
