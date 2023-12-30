package io.ticticboom.mods.mm.compat.jei.recipe;

import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.compat.MMCompatRegistries;
import io.ticticboom.mods.mm.compat.jei.SlotGrid;
import io.ticticboom.mods.mm.compat.jei.SlotGridEntry;
import io.ticticboom.mods.mm.compat.jei.base.JeiRecipeEntry;
import io.ticticboom.mods.mm.recipe.IConfiguredRecipeEntry;
import io.ticticboom.mods.mm.recipe.designated.DesignatedConfiguredRecipeEntry;
import io.ticticboom.mods.mm.recipe.pertick.PerTickConfiguredRecipeEntry;
import io.ticticboom.mods.mm.recipe.simple.SimpleConfiguredRecipeEntry;
import io.ticticboom.mods.mm.setup.model.RecipeModel;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;

public class DesignatedJeiRecipeEntry extends JeiRecipeEntry {
    @Override
    public void setRecipe(IConfiguredRecipeEntry entry, IRecipeLayoutBuilder builder, RecipeModel recipe, IFocusGroup focuses, IJeiHelpers helpers, boolean input, int startX, int startY, SlotGrid slots) {
        var sEntry = (DesignatedConfiguredRecipeEntry) entry;
        var iType = sEntry.entry().type();
        var rentry = MMCompatRegistries.JEI_RECIPE_ENTRIES.get().getValue(iType);
        rentry.setRecipe(sEntry.entry().entry(), builder, recipe, focuses, helpers, input, startX, startY, slots);
    }

    @Override
    public void renderJei(RecipeModel recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY, IConfiguredRecipeEntry entry, IJeiHelpers helpers, boolean input, int x, int y, SlotGrid slots) {
        var sEntry = (DesignatedConfiguredRecipeEntry) entry;
        var iType = sEntry.entry().type();
        var rentry = MMCompatRegistries.JEI_RECIPE_ENTRIES.get().getValue(iType);
        rentry.renderJei(recipe, recipeSlotsView, stack, mouseX, mouseY, sEntry.entry().entry(), helpers, input, x, y, slots);
    }
}
