package io.ticticboom.mods.mm.compat.jei.port;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.compat.jei.base.JeiPortTypeEntry;
import io.ticticboom.mods.mm.ports.base.IConfiguredIngredient;
import io.ticticboom.mods.mm.ports.createrotation.RotationConfiguredIngredient;
import io.ticticboom.mods.mm.ports.createrotation.jei.RotationIngredientHelper;
import io.ticticboom.mods.mm.ports.createrotation.jei.RotationIngredientRenderer;
import io.ticticboom.mods.mm.ports.createrotation.jei.RotationIngredientType;
import io.ticticboom.mods.mm.ports.createrotation.jei.RotationStack;
import io.ticticboom.mods.mm.setup.model.RecipeModel;
import io.ticticboom.mods.mm.util.Deferred;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.registration.IModIngredientRegistration;

public class RotationJeiPortTypeEntry extends JeiPortTypeEntry {

    public static final RotationIngredientType ROT_STACK = new RotationIngredientType();
    @Override
    public void registerJeiIngredient(IModIngredientRegistration registration, Deferred<IJeiHelpers> helpers) {
        registration.register(ROT_STACK, ImmutableList.of(), new RotationIngredientHelper(), new RotationIngredientRenderer(helpers));
    }

    @Override
    public void renderJei(RecipeModel recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY, IConfiguredIngredient ing, IJeiHelpers helpers, boolean input, int x, int y) {
            helpers.getGuiHelper().getSlotDrawable().draw(stack, x-1, y-1);
    }

    @Override
    public void setupRecipeJei(IConfiguredIngredient ingredient, IRecipeLayoutBuilder builder, RecipeModel recipe, IFocusGroup focuses, IRecipeSlotBuilder slot, boolean input, int x, int y) {
        var conf = (RotationConfiguredIngredient) ingredient;
        slot.addIngredient(ROT_STACK, new RotationStack(conf.speed()));
    }
}
