package io.ticticboom.mods.mm.compat.jei.port;

import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.compat.jei.base.JeiPortTypeEntry;
import io.ticticboom.mods.mm.ports.base.IConfiguredIngredient;
import io.ticticboom.mods.mm.ports.fluid.FluidConfiguredIngredient;
import io.ticticboom.mods.mm.setup.model.RecipeModel;
import io.ticticboom.mods.mm.util.Deferred;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.registration.IModIngredientRegistration;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidJeiPortTypeEntry extends JeiPortTypeEntry {
    @Override
    public void registerJeiIngredient(IModIngredientRegistration registration, Deferred<IJeiHelpers> helpers) {
    }

    @Override
    public void renderJei(RecipeModel recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY, IConfiguredIngredient ing, IJeiHelpers helpers, boolean input, int x, int y) {
        helpers.getGuiHelper().getSlotDrawable().draw(stack, x - 1, y - 1);
    }

    @Override
    public void setupRecipeJei(IConfiguredIngredient ingredient, IRecipeLayoutBuilder builder, RecipeModel recipe, IFocusGroup focuses, IRecipeSlotBuilder slot, boolean input, int x, int y) {
        var conf = (FluidConfiguredIngredient) ingredient;
        var fluid = ForgeRegistries.FLUIDS.getValue(conf.fluid());
        var stack = new FluidStack(fluid, conf.amount());
        slot.addIngredient(ForgeTypes.FLUID_STACK, stack);
        slot.setFluidRenderer(stack.getAmount(), false, 16, 16);
    }
}
