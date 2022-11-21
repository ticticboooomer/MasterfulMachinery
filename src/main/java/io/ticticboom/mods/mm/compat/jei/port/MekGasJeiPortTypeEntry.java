package io.ticticboom.mods.mm.compat.jei.port;

import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.compat.jei.base.JeiPortTypeEntry;
import io.ticticboom.mods.mm.ports.base.IConfiguredIngredient;
import io.ticticboom.mods.mm.ports.mekanism.gas.MekGasConfiguredIngredient;
import io.ticticboom.mods.mm.setup.model.RecipeModel;
import io.ticticboom.mods.mm.util.Deferred;
import mekanism.api.MekanismAPI;
import mekanism.api.chemical.gas.GasStack;
import mekanism.client.jei.MekanismJEI;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.registration.IModIngredientRegistration;
import net.minecraft.network.chat.TextComponent;

public class MekGasJeiPortTypeEntry extends JeiPortTypeEntry {
    @Override
    public void registerJeiIngredient(IModIngredientRegistration registration, Deferred<IJeiHelpers> helpers) {

    }

    @Override
    public void renderJei(RecipeModel recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY, IConfiguredIngredient ing, IJeiHelpers helpers, boolean input, int x, int y) {
        helpers.getGuiHelper().getSlotDrawable().draw(stack, x - 1, y - 1);
    }

    @Override
    public void setupRecipeJei(IConfiguredIngredient ingredient, IRecipeLayoutBuilder builder, RecipeModel recipe, IFocusGroup focuses, IRecipeSlotBuilder slot, boolean input, int x, int y) {
        MekGasConfiguredIngredient gas = (MekGasConfiguredIngredient) ingredient;
        var stack = new GasStack(MekanismAPI.gasRegistry().getValue(gas.gas()), 1000);
        slot.addIngredient(MekanismJEI.TYPE_GAS, stack);
        slot.addTooltipCallback((a, b) -> {
            b.add(new TextComponent(gas.amount() + " mb"));
        });
    }
}
