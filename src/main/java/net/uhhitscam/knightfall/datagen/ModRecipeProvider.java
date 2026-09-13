package net.uhhitscam.knightfall.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.uhhitscam.knightfall.block.ModBlocks;
import net.uhhitscam.knightfall.item.ModItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
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

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DURANIUM_INGOT.get())
                .requires(ModItems.DURASTEEL_INGOT.get())
                .requires(ModItems.TITANIUM_INGOT.get())
                .unlockedBy("has_durasteel_ingot", has(ModItems.DURASTEEL_INGOT.get()))
                .save(recipeOutput);
    }

    private static void addSmeltingRecipes(RecipeOutput recipeOutput, String group,
                                           List<ItemLike> ingredients, ItemLike result) {
        oreSmelting(recipeOutput, ingredients, RecipeCategory.MISC, result, 0.25f, 200, group);
        oreBlasting(recipeOutput, ingredients, RecipeCategory.MISC, result, 0.25f, 100, group);
    }

    private static void addNuggetRecipes(RecipeOutput recipeOutput, ItemLike ingot, ItemLike nugget) {
        String ingotName = ingot.asItem().toString();

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, nugget, 9)
                .requires(ingot)
                .unlockedBy("has_" + ingotName, has(ingot))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ingot)
                .define('#', nugget)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_" + nugget.asItem(), has(nugget))
                .save(recipeOutput);
    }

    protected static void oreSmelting(RecipeOutput pRecipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pRecipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput pRecipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pRecipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }
}
