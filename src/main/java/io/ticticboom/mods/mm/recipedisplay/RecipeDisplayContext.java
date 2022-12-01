package io.ticticboom.mods.mm.recipedisplay;

import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.recipe.IConfiguredRecipeEntry;
import io.ticticboom.mods.mm.setup.model.RecipeModel;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IJeiHelpers;

public record RecipeDisplayContext(
        RecipeModel recipe,
        IRecipeSlotsView recipeSlotsView,
        PoseStack stack,
        double mouseX,
        double mouseY,
        IConfiguredRecipeEntry entry,
        IJeiHelpers helpers,
        boolean input
) {
}
