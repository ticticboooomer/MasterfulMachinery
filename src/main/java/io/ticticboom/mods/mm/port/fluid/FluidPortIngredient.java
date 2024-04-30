package io.ticticboom.mods.mm.port.fluid;

import io.ticticboom.mods.mm.compat.jei.SlotGrid;
import io.ticticboom.mods.mm.compat.jei.ingredient.MMJeiIngredients;
import io.ticticboom.mods.mm.port.IPortIngredient;
import io.ticticboom.mods.mm.recipe.RecipeModel;
import io.ticticboom.mods.mm.recipe.RecipeStorages;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidPortIngredient implements IPortIngredient {

    private final ResourceLocation fluidId;
    private final int amount;
    private final Fluid fluid;

    public FluidPortIngredient(ResourceLocation fluidId, int amount) {
        this.fluidId = fluidId;
        this.amount = amount;
        fluid = ForgeRegistries.FLUIDS.getValue(fluidId);
        if (fluid == null) {
            throw new RuntimeException(String.format("Could not find fluid [%s] which is required by an MM recipe", fluidId));
        }
    }

    @Override
    public boolean canProcess(Level level, RecipeStorages storages) {
        var fluidStorages = storages.getInputStorages(FluidPortStorage.class);
        int remaining = amount;
        for (FluidPortStorage storage : fluidStorages) {
             var drained = storage.getHandler().drain(new FluidStack(fluid, remaining), IFluidHandler.FluidAction.SIMULATE);
             remaining -= drained.getAmount();
        }
        return remaining <= 0;
    }

    @Override
    public void process(Level level, RecipeStorages storages) {
        var fluidStorages = storages.getInputStorages(FluidPortStorage.class);
        int remaining = amount;
        for (FluidPortStorage storage : fluidStorages) {
            var drained = storage.getHandler().drain(new FluidStack(fluid, remaining), IFluidHandler.FluidAction.EXECUTE);
            remaining -= drained.getAmount();
        }
    }

    @Override
    public boolean canOutput(Level level, RecipeStorages storages) {
        var fluidStorages = storages.getOutputStorages(FluidPortStorage.class);
        int remaining = amount;
        for (FluidPortStorage storage : fluidStorages) {
            var filled = storage.getHandler().fill(new FluidStack(fluid, remaining), IFluidHandler.FluidAction.SIMULATE);
            remaining -= filled;
        }
        return remaining <= 0;
    }

    @Override
    public void output(Level level, RecipeStorages storages) {
        var fluidStorages = storages.getOutputStorages(FluidPortStorage.class);
        int remaining = amount;
        for (FluidPortStorage storage : fluidStorages) {
            var filled = storage.getHandler().fill(new FluidStack(fluid, remaining), IFluidHandler.FluidAction.EXECUTE);
            remaining -= filled;
        }
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeModel model, IFocusGroup focus, IJeiHelpers helpers, SlotGrid grid, IRecipeSlotBuilder recipeSlot) {
        recipeSlot.addIngredient(MMJeiIngredients.FLUID, new FluidStack(fluid, amount));
    }
}
