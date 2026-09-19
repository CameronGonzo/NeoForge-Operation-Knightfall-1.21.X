package net.uhhitscam.knightfall.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.Item;
import net.uhhitscam.knightfall.OperationKnightfall;
import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends TagsProvider<Item> {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Registries.ITEM, lookupProvider, OperationKnightfall.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
    }
}
