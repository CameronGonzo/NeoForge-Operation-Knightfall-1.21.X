package net.uhhitscam.knightfall.datagen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.uhhitscam.knightfall.block.ModBlocks;
import net.uhhitscam.knightfall.item.ModItems;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider provider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
    }

    @Override
    protected void generate() {
//        dropSelf(ModBlocks.name.get());

        this.add(ModBlocks.BESKAR_ORE.get(),
                block -> createSingleOreDrop(ModBlocks.BESKAR_ORE.get(), ModItems.BESKAR_FRAGMENT.get()));
        this.add(ModBlocks.BRONZIUM_ORE.get(),
                block -> createOreDrop(ModBlocks.BRONZIUM_ORE.get(), ModItems.RAW_BRONZIUM.get()));
        this.add(ModBlocks.CINNABAR_ORE.get(),
                block -> createMultipleOreDrops(ModBlocks.CINNABAR_ORE.get(), ModItems.CINNABAR_DUST.get(), 1f, 2f));
        this.add(ModBlocks.CORTOSIS_ORE.get(),
                block -> createOreDrop(ModBlocks.CORTOSIS_ORE.get(), ModItems.CORTOSIS_CHUNK.get()));
        this.add(ModBlocks.DEDLANITE_ORE.get(),
                block -> createOreDrop(ModBlocks.DEDLANITE_ORE.get(), ModItems.DEDLANITE_CHUNK.get()));
        this.add(ModBlocks.DIATIUM_ORE.get(),
                block -> createMultipleOreDrops(ModBlocks.DIATIUM_ORE.get(), ModItems.DIATIUM_GEM.get(), 1f, 3f));
        this.add(ModBlocks.DURASTEEL_ORE.get(),
                block -> createOreDrop(ModBlocks.DURASTEEL_ORE.get(), ModItems.RAW_DURASTEEL.get()));
        this.add(ModBlocks.DURITE_ORE.get(),
                block -> createOreDrop(ModBlocks.DURITE_ORE.get(), ModItems.DURITE_CHUNK.get()));
        this.add(ModBlocks.EXONIUM_ORE.get(),
                block -> createSingleOreDrop(ModBlocks.EXONIUM_ORE.get(), ModItems.EXONIUM.get()));
        this.add(ModBlocks.HAYSIAN_SMELT_ORE.get(),
                block -> createOreDrop(ModBlocks.HAYSIAN_SMELT_ORE.get(), ModItems.RAW_HAYSIAN_SMELT.get()));
        this.add(ModBlocks.IPSIUM_ORE.get(), block -> LootTable.lootTable());
        this.add(ModBlocks.KALKITE_ORE.get(),
                block -> createOreDrop(ModBlocks.KALKITE_ORE.get(), ModItems.KALKITE.get()));
        this.add(ModBlocks.MALSARR_ORE.get(),
                block -> createMultipleOreDrops(ModBlocks.MALSARR_ORE.get(), ModItems.MALSARR.get(), 2f, 5f));
        this.add(ModBlocks.PHRIK_ORE.get(),
                block -> createOreDrop(ModBlocks.PHRIK_ORE.get(), ModItems.PHRIK_CHUNK.get()));
        this.add(ModBlocks.TITANIUM_ORE.get(),
                block -> createOreDrop(ModBlocks.TITANIUM_ORE.get(), ModItems.RAW_TITANIUM.get()));

//        this.add(ModBlocks.name.get()),
//                block -> createMultipleOreDrops(ModBlocks.name.get(), ModItems.name.get(), 3, 8);
    }

    protected LootTable.Builder createMultipleOreDrops(Block block, Item item, float minDrops, float maxDrops) {
        LootPoolSingletonContainer.Builder<?> drops = LootItem.lootTableItem(item);
        drops.apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)));
        drops.apply(ApplyBonusCount.addOreBonusCount(registries.holderOrThrow(Enchantments.FORTUNE)));
        drops.apply(ApplyExplosionDecay.explosionDecay());

        return this.createSilkTouchDispatchTable(block, drops);
    }

    protected LootTable.Builder createSingleOreDrop(Block block, Item item) {
        return this.createSilkTouchDispatchTable(block,
                this.applyExplosionDecay(block, LootItem.lootTableItem(item)));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
