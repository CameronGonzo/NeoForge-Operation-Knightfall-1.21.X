package net.uhhitscam.knightfall.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.uhhitscam.knightfall.block.ModBlocks;
import net.uhhitscam.knightfall.item.ModItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        RecipeOutput recipeOutput = output;
        addSmeltingRecipes(recipeOutput, "bronzium",
                List.of(ModItems.RAW_BRONZIUM, ModBlocks.BRONZIUM_ORE), ModItems.BRONZIUM_INGOT);
        addSmeltingRecipes(recipeOutput, "durasteel",
                List.of(ModItems.RAW_DURASTEEL, ModBlocks.DURASTEEL_ORE), ModItems.DURASTEEL_INGOT);
        addSmeltingRecipes(recipeOutput, "haysian_smelt",
                List.of(ModItems.RAW_HAYSIAN_SMELT, ModBlocks.HAYSIAN_SMELT_ORE), ModItems.HAYSIAN_SMELT_INGOT);
        addSmeltingRecipes(recipeOutput, "titanium",
                List.of(ModItems.RAW_TITANIUM, ModBlocks.TITANIUM_ORE), ModItems.TITANIUM_INGOT);

        addNuggetRecipes(recipeOutput, ModItems.BRONZIUM_INGOT, ModItems.BRONZIUM_NUGGET);
        addNuggetRecipes(recipeOutput, ModItems.DURASTEEL_INGOT, ModItems.DURASTEEL_NUGGET);
        addNuggetRecipes(recipeOutput, ModItems.HAYSIAN_SMELT_INGOT, ModItems.HAYSIAN_SMELT_NUGGET);
        addNuggetRecipes(recipeOutput, ModItems.TITANIUM_INGOT, ModItems.TITANIUM_NUGGET);

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, ModItems.DURANIUM_INGOT.get())
                .requires(ModItems.DURASTEEL_INGOT.get())
                .requires(ModItems.TITANIUM_INGOT.get())
                .unlockedBy("has_durasteel_ingot", has(ModItems.DURASTEEL_INGOT.get()))
                .save(recipeOutput);
    }

    private void addSmeltingRecipes(RecipeOutput recipeOutput, String group,
                                           List<ItemLike> ingredients, ItemLike result) {
        oreSmelting(ingredients, RecipeCategory.MISC, CookingBookCategory.MISC, result, 0.25f, 200, group);
        oreBlasting(ingredients, RecipeCategory.MISC, CookingBookCategory.MISC, result, 0.25f, 100, group);
    }

    private void addNuggetRecipes(RecipeOutput recipeOutput, ItemLike ingot, ItemLike nugget) {
        String ingotName = ingot.asItem().toString();

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, nugget, 9)
                .requires(ingot)
                .unlockedBy("has_" + ingotName, has(ingot))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ingot)
                .define('#', nugget)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_" + nugget.asItem(), has(nugget))
                .save(recipeOutput);
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super(output, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            return new ModRecipeProvider(registries, output);
        }

        @Override
        public String getName() {
            return "Operation Knightfall recipes";
        }
    }
}
