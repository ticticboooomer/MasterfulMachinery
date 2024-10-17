package io.ticticboom.mods.mm.compat.jei;

import com.google.common.collect.ImmutableList;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.compat.jei.category.MMRecipeCategory;
import io.ticticboom.mods.mm.compat.jei.category.MMStructureCategory;
import io.ticticboom.mods.mm.compat.jei.ingredient.MMJeiIngredients;
import io.ticticboom.mods.mm.compat.jei.ingredient.energy.EnergyIngredientHelper;
import io.ticticboom.mods.mm.compat.jei.ingredient.energy.EnergyIngredientRenderer;
import io.ticticboom.mods.mm.compat.jei.ingredient.energy.EnergyStack;
import io.ticticboom.mods.mm.compat.jei.ingredient.mana.BotaniaManaIngredientHelper;
import io.ticticboom.mods.mm.compat.jei.ingredient.mana.BotaniaManaIngredientRenderer;
import io.ticticboom.mods.mm.compat.jei.ingredient.pncr.PneumaticAirIngredientHelper;
import io.ticticboom.mods.mm.compat.jei.ingredient.pncr.PneumaticAirIngredientRender;
import io.ticticboom.mods.mm.config.MMConfig;
import io.ticticboom.mods.mm.recipe.MachineRecipeManager;
import io.ticticboom.mods.mm.recipe.RecipeModel;
import io.ticticboom.mods.mm.structure.StructureManager;
import io.ticticboom.mods.mm.structure.StructureModel;
import me.desht.pneumaticcraft.common.core.ModItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IModIngredientRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

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
        if (MMConfig.JEI_RECIPE_SPLIT) {
            for (StructureModel value : StructureManager.STRUCTURES.values()) {
                MMRecipeCategory category = new MMRecipeCategory(registration.getJeiHelpers(), value);
                registration.addRecipeCategories(category);
                recipeCategories.add(category);
            }
        } else {
            MMRecipeCategory category = new MMRecipeCategory(registration.getJeiHelpers(), null);
            registration.addRecipeCategories(category);
            recipeCategories.add(category);
        }
        registration.addRecipeCategories(new MMStructureCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        if (MMConfig.JEI_RECIPE_SPLIT) {
            for (var entry : recipeCategories) {
                var recipes = MachineRecipeManager.RECIPES.values().stream().filter(x -> x.structureId().equals(entry.getStructureModel().id())).toList();
                registration.addRecipes(entry.getRecipeType(), recipes);
            }
        } else {
            registration.addRecipes(MMRecipeCategory.RECIPE_TYPE, new ArrayList<>(MachineRecipeManager.RECIPES.values()));
        }
        registration.addRecipes(MMStructureCategory.RECIPE_TYPE, new ArrayList<>(StructureManager.STRUCTURES.values()));
    }

    @Override
    public void registerIngredients(IModIngredientRegistration registration) {
        registration.register(MMJeiIngredients.ENERGY, ImmutableList.of(), new EnergyIngredientHelper(), new EnergyIngredientRenderer());
        registration.register(MMJeiIngredients.PNEUMATIC_AIR, ImmutableList.of(), new PneumaticAirIngredientHelper(), new PneumaticAirIngredientRender());
        registration.register(MMJeiIngredients.BOTANIA_MANA, ImmutableList.of(), new BotaniaManaIngredientHelper(), new BotaniaManaIngredientRenderer());
    }


    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        for (var entry : recipeCategories) {
            ResourceLocation location = entry.getStructureModel().controllerIds().getIds().get(0);
            ItemStack stack = ForgeRegistries.ITEMS.getValue(location).getDefaultInstance();
            registration.addRecipeCatalyst(stack,entry.getRecipeType());
        }
    }
}