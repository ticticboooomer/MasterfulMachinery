package io.ticticboom.mods.mm.compat.jei.port;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.compat.jei.base.JeiPortTypeEntry;
import io.ticticboom.mods.mm.ports.base.IConfiguredIngredient;
import io.ticticboom.mods.mm.ports.energy.EnergyConfiguredIngredient;
import io.ticticboom.mods.mm.ports.energy.jei.EnergyIngredientHelper;
import io.ticticboom.mods.mm.ports.energy.jei.EnergyIngredientRenderer;
import io.ticticboom.mods.mm.ports.energy.jei.EnergyIngredientType;
import io.ticticboom.mods.mm.ports.energy.jei.EnergyStack;
import io.ticticboom.mods.mm.setup.model.RecipeModel;
import io.ticticboom.mods.mm.util.Deferred;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.registration.IModIngredientRegistration;

public class EnergyJeiPortTypeEntry extends JeiPortTypeEntry {
    public static EnergyIngredientType ENERGY_STACK = new EnergyIngredientType();
    @Override
    public void registerJeiIngredient(IModIngredientRegistration reg, Deferred<IJeiHelpers> helpers) {
        reg.register(ENERGY_STACK, ImmutableList.of(), new EnergyIngredientHelper(), new EnergyIngredientRenderer(helpers));
    }

    @Override
    public void renderJei(RecipeModel recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY, IConfiguredIngredient ing, IJeiHelpers helpers, boolean input, int x, int y) {
        helpers.getGuiHelper().getSlotDrawable().draw(stack, x - 1, y - 1);
    }

    @Override
    public void setupRecipeJei(IConfiguredIngredient ingredient, IRecipeLayoutBuilder builder, RecipeModel recipe, IFocusGroup focuses, IRecipeSlotBuilder slot, boolean input, int x, int y) {
        var conf = (EnergyConfiguredIngredient) ingredient;
        slot.addIngredient(ENERGY_STACK, new EnergyStack(conf.amount()));
    }
}
