package net.uhhitscam.knightfall.datagen;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.uhhitscam.knightfall.block.ModBlocks;

public final class ModBlockStateProvider {
    private ModBlockStateProvider() {}

    public static void registerModels(BlockModelGenerators models) {
        models.createTrivialCube(ModBlocks.BESKAR_ORE.get());
        models.createTrivialCube(ModBlocks.BRONZIUM_ORE.get());
        models.createTrivialCube(ModBlocks.CINNABAR_ORE.get());
        models.createTrivialCube(ModBlocks.CORTOSIS_ORE.get());
        models.createTrivialCube(ModBlocks.DEDLANITE_ORE.get());
        models.createTrivialCube(ModBlocks.DIATIUM_ORE.get());
        models.createTrivialCube(ModBlocks.DURASTEEL_ORE.get());
        models.createTrivialCube(ModBlocks.DURITE_ORE.get());
        models.createTrivialCube(ModBlocks.EXONIUM_ORE.get());
        models.createTrivialCube(ModBlocks.HAYSIAN_SMELT_ORE.get());
        models.createTrivialCube(ModBlocks.IPSIUM_ORE.get());
        models.createTrivialCube(ModBlocks.KALKITE_ORE.get());
        models.createTrivialCube(ModBlocks.MALSARR_ORE.get());
        models.createTrivialCube(ModBlocks.PHRIK_ORE.get());
        models.createTrivialCube(ModBlocks.TITANIUM_ORE.get());
    }
}
