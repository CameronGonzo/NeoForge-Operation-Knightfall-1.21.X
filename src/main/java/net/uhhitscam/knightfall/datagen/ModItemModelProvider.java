package net.uhhitscam.knightfall.datagen;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.item.ModItems;
import net.uhhitscam.knightfall.block.ModBlocks;
import java.util.stream.Stream;

public class ModItemModelProvider extends ModelProvider {
    public ModItemModelProvider(PackOutput output) {
        super(output, OperationKnightfall.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        ModBlockStateProvider.registerModels(blockModels);
        itemModels.generateFlatItem(ModItems.BESKAR_BAR.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.BESKAR_FRAGMENT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RAW_BRONZIUM.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.BRONZIUM_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.BRONZIUM_NUGGET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CINNABAR_DUST.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CORTOSIS_CHUNK.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.DEDLANITE_CHUNK.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.DIATIUM_GEM.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.DURANIUM_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RAW_DURASTEEL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.DURASTEEL_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.DURASTEEL_NUGGET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.DURITE_CHUNK.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.EXONIUM.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.EXONIUM_REFINED.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RAW_HAYSIAN_SMELT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.HAYSIAN_SMELT_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.HAYSIAN_SMELT_NUGGET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.IONITE_REFINED.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.IONITE_SHARD.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.KALKITE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.KYBER_CRYSTAL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.MALSARR.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.PHRIK_CHUNK.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SILICAX_OXALATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.TITANITE_POWDER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RAW_TITANIUM.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.TITANIUM_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.TITANIUM_NUGGET.get(), ModelTemplates.FLAT_ITEM);
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        return Stream.concat(Stream.of(
                ModItems.BESKAR_BAR,
                ModItems.BESKAR_FRAGMENT,
                ModItems.RAW_BRONZIUM,
                ModItems.BRONZIUM_INGOT,
                ModItems.BRONZIUM_NUGGET,
                ModItems.CINNABAR_DUST,
                ModItems.CORTOSIS_CHUNK,
                ModItems.DEDLANITE_CHUNK,
                ModItems.DIATIUM_GEM,
                ModItems.DURANIUM_INGOT,
                ModItems.RAW_DURASTEEL,
                ModItems.DURASTEEL_INGOT,
                ModItems.DURASTEEL_NUGGET,
                ModItems.DURITE_CHUNK,
                ModItems.EXONIUM,
                ModItems.EXONIUM_REFINED,
                ModItems.RAW_HAYSIAN_SMELT,
                ModItems.HAYSIAN_SMELT_INGOT,
                ModItems.HAYSIAN_SMELT_NUGGET,
                ModItems.IONITE_REFINED,
                ModItems.IONITE_SHARD,
                ModItems.KALKITE,
                ModItems.KYBER_CRYSTAL,
                ModItems.MALSARR,
                ModItems.PHRIK_CHUNK,
                ModItems.SILICAX_OXALATE,
                ModItems.TITANITE_POWDER,
                ModItems.RAW_TITANIUM,
                ModItems.TITANIUM_INGOT,
                ModItems.TITANIUM_NUGGET),
                ModBlocks.BLOCKS.getEntries().stream().map(block -> net.minecraft.core.registries.BuiltInRegistries.ITEM.wrapAsHolder(block.get().asItem())));
    }
}
