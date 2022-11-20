package io.ticticboom.mods.mm.compat.jei.recipe;

import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.compat.MMCompatRegistries;
import io.ticticboom.mods.mm.compat.jei.base.JeiRecipeEntry;
import io.ticticboom.mods.mm.recipe.IConfiguredRecipeEntry;
import io.ticticboom.mods.mm.recipe.pertick.PerTickConfiguredRecipeEntry;
import io.ticticboom.mods.mm.recipe.simple.SimpleConfiguredRecipeEntry;
import io.ticticboom.mods.mm.setup.model.RecipeModel;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.network.chat.TextComponent;

public class PerTickJeiRecipeEntry extends JeiRecipeEntry {
    @Override
    public void setRecipe(IConfiguredRecipeEntry entry, IRecipeLayoutBuilder builder, RecipeModel recipe, IFocusGroup focuses, IJeiHelpers helpers, boolean input, int startX, int startY) {
        var sEntry = (PerTickConfiguredRecipeEntry) entry;
        var iType = sEntry.ingredient().type();
        var port = MMCompatRegistries.JEI_PORTS.get().getValue(iType);
        var slot = builder.addSlot(input ? RecipeIngredientRole.INPUT : RecipeIngredientRole.OUTPUT, startX, startY);
        port.setupRecipeJei(sEntry.ingredient().config(), builder, recipe, focuses, slot, input, startX, startY);
            slot.addTooltipCallback((a, b) -> {
                if (input) {
                    b.add(new TextComponent("Consumed Per Tick"));
                } else {
                    b.add(new TextComponent("Output Per Tick"));
                }
            });
    }

    @Override
    public void renderJei(RecipeModel recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY, IConfiguredRecipeEntry entry, IJeiHelpers helpers, boolean input, int x, int y) {
        var conf = (PerTickConfiguredRecipeEntry) entry;
        var port = MMCompatRegistries.JEI_PORTS.get().getValue(conf.ingredient().type());
        port.renderJei(recipe, recipeSlotsView, stack, mouseX, mouseY, conf.ingredient().config(), helpers, input, x, y);
    }
}
