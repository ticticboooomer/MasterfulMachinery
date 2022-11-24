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
    public abstract boolean processInputs(IConfiguredRecipeEntry config, RecipeContext original, RecipeContext ctx);

    public abstract boolean processOutputs(IConfiguredRecipeEntry config, RecipeContext original, RecipeContext ctx);
    public int getNewTickLimit(IConfiguredRecipeEntry config, RecipeContext original, RecipeContext ctx, int currentLimit) {
        return currentLimit;
    }
}
