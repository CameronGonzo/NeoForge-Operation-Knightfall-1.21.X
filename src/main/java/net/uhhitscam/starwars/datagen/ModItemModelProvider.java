package net.uhhitscam.starwars.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.uhhitscam.starwars.OperationKnightfall;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, OperationKnightfall.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }
}
