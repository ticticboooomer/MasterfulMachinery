package io.ticticboom.mods.mm.recipe;

import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.ports.base.IConfiguredIngredient;
import io.ticticboom.mods.mm.setup.model.RecipeModel;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class MMRecipeEntry extends ForgeRegistryEntry<MMRecipeEntry> {
    public abstract IConfiguredRecipeEntry parse(JsonObject json);
    public abstract boolean processInputs(IConfiguredRecipeEntry config, RecipeContext ctx);

    public abstract boolean processOutputs(IConfiguredRecipeEntry config, RecipeContext ctx);

    public abstract void setRecipe(IConfiguredRecipeEntry entry, IRecipeLayoutBuilder builder, RecipeModel recipe, IFocusGroup focuses, IJeiHelpers helpers, boolean input, int startX, int startY);
    public abstract void renderJei(RecipeModel recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY, IConfiguredRecipeEntry entry, IJeiHelpers helpers, boolean input, int x, int y);
}
