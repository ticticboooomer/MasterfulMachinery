package io.ticticboom.mods.mm.compat.jei.port.mek;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.compat.jei.base.JeiPortTypeEntry;
import io.ticticboom.mods.mm.ports.base.IConfiguredIngredient;
import io.ticticboom.mods.mm.ports.energy.EnergyConfiguredIngredient;
import io.ticticboom.mods.mm.ports.energy.jei.EnergyStack;
import io.ticticboom.mods.mm.ports.mekanism.heat.MekHeatConfiguredIngredient;
import io.ticticboom.mods.mm.ports.mekanism.heat.jei.HeatIngredientHelper;
import io.ticticboom.mods.mm.ports.mekanism.heat.jei.HeatIngredientRenderer;
import io.ticticboom.mods.mm.ports.mekanism.heat.jei.HeatIngredientType;
import io.ticticboom.mods.mm.ports.mekanism.heat.jei.HeatStack;
import io.ticticboom.mods.mm.setup.model.RecipeModel;
import io.ticticboom.mods.mm.util.Deferred;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.registration.IModIngredientRegistration;

public class MekHeatJeiPortTypeEntry extends JeiPortTypeEntry {
    public static HeatIngredientType HEAT_TYPE = new HeatIngredientType();
    @Override
    public void registerJeiIngredient(IModIngredientRegistration registration, Deferred<IJeiHelpers> helpers) {
        registration.register(HEAT_TYPE, ImmutableList.of(), new HeatIngredientHelper(), new HeatIngredientRenderer(helpers));
    }

    @Override
    public void renderJei(RecipeModel recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY, IConfiguredIngredient ing, IJeiHelpers helpers, boolean input, int x, int y) {
        helpers.getGuiHelper().getSlotDrawable().draw(stack, x - 1, y - 1);
    }

    @Override
    public void setupRecipeJei(IConfiguredIngredient ingredient, IRecipeLayoutBuilder builder, RecipeModel recipe, IFocusGroup focuses, IRecipeSlotBuilder slot, boolean input, int x, int y) {
        var conf = (MekHeatConfiguredIngredient) ingredient;
        slot.addIngredient(HEAT_TYPE, new HeatStack(conf.amount()));
    }
}
