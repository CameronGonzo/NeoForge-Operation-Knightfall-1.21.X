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
        public static final TagKey<Block> BLASTER_IMPACT_DIRT = createTag("blaster_impact_dirt");
        public static final TagKey<Block> BLASTER_IMPACT_FOLIAGE = createTag("blaster_impact_foliage");
        public static final TagKey<Block> BLASTER_IMPACT_GLASS = createTag("blaster_impact_glass");
        public static final TagKey<Block> BLASTER_BREAKABLE_GLASS = createTag("blaster_breakable_glass");
        public static final TagKey<Block> BLASTER_IMPACT_METAL = createTag("blaster_impact_metal");
        public static final TagKey<Block> BLASTER_IMPACT_MOIST = createTag("blaster_impact_moist");
        public static final TagKey<Block> BLASTER_IMPACT_SAND = createTag("blaster_impact_sand");
        public static final TagKey<Block> BLASTER_IMPACT_STONE = createTag("blaster_impact_stone");
        public static final TagKey<Block> BLASTER_IMPACT_WOOD = createTag("blaster_impact_wood");

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