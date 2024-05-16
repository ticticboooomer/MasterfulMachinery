package io.ticticboom.mods.mm.compat.jei;

import com.google.common.collect.ImmutableList;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.compat.jei.category.MMRecipeCategory;
import io.ticticboom.mods.mm.compat.jei.category.MMStructureCategory;
import io.ticticboom.mods.mm.compat.jei.ingredient.MMJeiIngredients;
import io.ticticboom.mods.mm.compat.jei.ingredient.energy.EnergyIngredientHelper;
import io.ticticboom.mods.mm.compat.jei.ingredient.energy.EnergyIngredientRenderer;
import io.ticticboom.mods.mm.compat.jei.ingredient.energy.EnergyStack;
import io.ticticboom.mods.mm.recipe.MachineRecipeManager;
import io.ticticboom.mods.mm.recipe.RecipeModel;
import io.ticticboom.mods.mm.structure.StructureManager;
import io.ticticboom.mods.mm.structure.StructureModel;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IModIngredientRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@JeiPlugin
public class MMJeiPlugin implements IModPlugin {
    public static final ResourceLocation UID = Ref.id("jei_plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    public static final List<MMRecipeCategory> recipeCategories = new ArrayList<>();

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        for (StructureModel value : StructureManager.STRUCTURES.values()) {
            MMRecipeCategory category = new MMRecipeCategory(registration.getJeiHelpers(), value);
            registration.addRecipeCategories(category);
            recipeCategories.add(category);
        }
        registration.addRecipeCategories(new MMStructureCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {

        for (var entry : recipeCategories) {
            var recipes = MachineRecipeManager.RECIPES.values().stream().filter(x -> x.structureId().equals(entry.getStructureModel().id())).toList();
            registration.addRecipes(entry.getRecipeType(), recipes);
        }
        registration.addRecipes(MMStructureCategory.RECIPE_TYPE, new ArrayList<>(StructureManager.STRUCTURES.values()));
    }

    @Override
    public void registerIngredients(IModIngredientRegistration registration) {
        registration.register(MMJeiIngredients.ENERGY, ImmutableList.of(), new EnergyIngredientHelper(), new EnergyIngredientRenderer());
    }
}
