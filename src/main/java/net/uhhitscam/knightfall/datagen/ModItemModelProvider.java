package net.uhhitscam.knightfall.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.uhhitscam.knightfall.OperationKnightfall;
import net.uhhitscam.knightfall.item.ModItems;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, OperationKnightfall.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.BESKAR_BAR.get());
        basicItem(ModItems.BESKAR_FRAGMENT.get());
        basicItem(ModItems.RAW_BRONZIUM.get());
        basicItem(ModItems.BRONZIUM_INGOT.get());
        basicItem(ModItems.BRONZIUM_NUGGET.get());
        basicItem(ModItems.CINNABAR_DUST.get());
        basicItem(ModItems.CORTOSIS_CHUNK.get());
        basicItem(ModItems.DEDLANITE_CHUNK.get());
        basicItem(ModItems.DIATIUM_GEM.get());
        basicItem(ModItems.DURANIUM_INGOT.get());
        basicItem(ModItems.RAW_DURASTEEL.get());
        basicItem(ModItems.DURASTEEL_INGOT.get());
        basicItem(ModItems.DURASTEEL_NUGGET.get());
        basicItem(ModItems.DURITE_CHUNK.get());
        basicItem(ModItems.EXONIUM.get());
        basicItem(ModItems.EXONIUM_REFINED.get());
        basicItem(ModItems.RAW_HAYSIAN_SMELT.get());
        basicItem(ModItems.HAYSIAN_SMELT_INGOT.get());
        basicItem(ModItems.HAYSIAN_SMELT_NUGGET.get());
        basicItem(ModItems.IONITE_REFINED.get());
        basicItem(ModItems.IONITE_SHARD.get());
        basicItem(ModItems.KALKITE.get());
        basicItem(ModItems.KYBER_CRYSTAL.get());
        basicItem(ModItems.MALSARR.get());
        basicItem(ModItems.PHRIK_CHUNK.get());
        basicItem(ModItems.SILICAX_OXALATE.get());
        basicItem(ModItems.TITANITE_POWDER.get());
        basicItem(ModItems.RAW_TITANIUM.get());
        basicItem(ModItems.TITANIUM_INGOT.get());
        basicItem(ModItems.TITANIUM_NUGGET.get());
    }
}
