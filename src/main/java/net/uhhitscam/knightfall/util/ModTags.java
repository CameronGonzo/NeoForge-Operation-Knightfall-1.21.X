package net.uhhitscam.knightfall.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.uhhitscam.knightfall.OperationKnightfall;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> INTERACTABLE_BLOCKS = createTag("interactable_blocks");

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, name));
        }
    }

    public static class Items {
        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(OperationKnightfall.MODID, name));
        }
    }
}