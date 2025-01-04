package net.uhhitscam.starwars.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.uhhitscam.starwars.OperationKnightfall;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, OperationKnightfall.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

    }
}
