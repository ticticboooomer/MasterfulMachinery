package io.ticticboom.mods.mm.compat.jei.port.mek;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import io.ticticboom.mods.mm.compat.jei.base.JeiPortTypeEntry;
import io.ticticboom.mods.mm.compat.jei.port.EnergyJeiPortTypeEntry;
import io.ticticboom.mods.mm.ports.base.IConfiguredIngredient;
import io.ticticboom.mods.mm.ports.energy.EnergyConfiguredIngredient;
import io.ticticboom.mods.mm.ports.energy.jei.EnergyIngredientHelper;
import io.ticticboom.mods.mm.ports.energy.jei.EnergyIngredientRenderer;
import io.ticticboom.mods.mm.ports.energy.jei.EnergyStack;
import io.ticticboom.mods.mm.ports.mekanism.laser.MekLaserConfiguredIngredient;
import io.ticticboom.mods.mm.setup.model.RecipeModel;
import io.ticticboom.mods.mm.util.Deferred;
import mekanism.client.jei.MekanismJEI;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.registration.IModIngredientRegistration;
import net.minecraft.network.chat.Component;

import static io.ticticboom.mods.mm.compat.jei.port.EnergyJeiPortTypeEntry.ENERGY_STACK;

public class MekLaserJeiPortType extends JeiPortTypeEntry {

    @Override
    public void registerJeiIngredient(IModIngredientRegistration reg, Deferred<IJeiHelpers> helpers) {
//        reg.register(ENERGY_STACK, ImmutableList.of(), new EnergyIngredientHelper(), new EnergyIngredientRenderer(helpers));
    }

    @Override
    public void renderJei(RecipeModel recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY, IConfiguredIngredient ing, IJeiHelpers helpers, boolean input, int x, int y) {
        helpers.getGuiHelper().getSlotDrawable().draw(stack, x - 1, y - 1);
    }

    @Override
    public void setupRecipeJei(IConfiguredIngredient ingredient, IRecipeLayoutBuilder builder, RecipeModel recipe, IFocusGroup focuses, IRecipeSlotBuilder slot, boolean input, int x, int y) {
        var conf = (MekLaserConfiguredIngredient) ingredient;
        slot.addIngredient(ENERGY_STACK, new EnergyStack(conf.energy().intValue()));
        slot.addTooltipCallback((recipeSlotView, tooltip) -> {
            tooltip.add(Component.literal("From Mekanism Laser Port"));
        });
    }
}
