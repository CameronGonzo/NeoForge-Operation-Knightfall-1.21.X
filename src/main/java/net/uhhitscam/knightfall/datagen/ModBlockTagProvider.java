package net.uhhitscam.knightfall.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, OperationKnightfall.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.Blocks.INTERACTABLE_BLOCKS)
                .addTag(BlockTags.BUTTONS)
                .addTag(BlockTags.WOODEN_DOORS)
                .addTag(BlockTags.ANVIL)
                .addTag(BlockTags.WOODEN_TRAPDOORS)
                .addTag(BlockTags.ALL_SIGNS)
                .addTag(BlockTags.BEDS)
                .addTag(BlockTags.SHULKER_BOXES)
                .addTag(BlockTags.FENCE_GATES)
                .addTag(BlockTags.CANDLE_CAKES)
                .add(Blocks.CRAFTING_TABLE)
                .add(Blocks.STONECUTTER)
                .add(Blocks.CARTOGRAPHY_TABLE)
                .add(Blocks.SMITHING_TABLE)
                .add(Blocks.GRINDSTONE)
                .add(Blocks.LOOM)
                .add(Blocks.FURNACE)
                .add(Blocks.BLAST_FURNACE)
                .add(Blocks.SMOKER)
                .add(Blocks.NOTE_BLOCK)
                .add(Blocks.JUKEBOX)
                .add(Blocks.ENCHANTING_TABLE)
                .add(Blocks.BREWING_STAND)
                .add(Blocks.BELL)
                .add(Blocks.BEACON)
                .add(Blocks.LECTERN)
                .add(Blocks.CHEST)
                .add(Blocks.BARREL)
                .add(Blocks.ENDER_CHEST)
                .add(Blocks.COMPARATOR)
                .add(Blocks.REPEATER)
                .add(Blocks.LEVER)
                .add(Blocks.DAYLIGHT_DETECTOR)
                .add(Blocks.DISPENSER)
                .add(Blocks.DROPPER)
                .add(Blocks.CRAFTER)
                .add(Blocks.HOPPER)
                .add(Blocks.TRAPPED_CHEST)
                .add(Blocks.CAKE)
                .add(Blocks.DECORATED_POT)
                .add(Blocks.CHEST)
                .add(Blocks.TRAPPED_CHEST);
    }
}
