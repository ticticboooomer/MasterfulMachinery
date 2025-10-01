package io.ticticboom.mods.mm.port.pneumaticcraft.air;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.compat.jei.SlotGrid;
import io.ticticboom.mods.mm.compat.jei.ingredient.MMJeiIngredients;
import io.ticticboom.mods.mm.compat.jei.ingredient.pncr.PneumaticAirStack;
import io.ticticboom.mods.mm.port.IPortIngredient;
import io.ticticboom.mods.mm.recipe.RecipeModel;
import io.ticticboom.mods.mm.recipe.RecipeStateModel;
import io.ticticboom.mods.mm.recipe.RecipeStorages;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.world.level.Level;

public class PneumaticAirPortIngredient implements IPortIngredient {
    private final float bar;
    private final int air;
    public PneumaticAirPortIngredient(float bar, int air) {
        this.bar = bar;
        this.air = air;
    }
    @Override
    public boolean canProcess(Level level, RecipeStorages storages, RecipeStateModel state) {
        if(storages == null) return false;
        var inputStorages = storages.getInputStorages(PneumaticAirPortStorage.class);
        for (PneumaticAirPortStorage storage : inputStorages) {
            if (storage.getPressure() < bar) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void process(Level level, RecipeStorages storages, RecipeStateModel state) {
        var inputStorages = storages.getInputStorages(PneumaticAirPortStorage.class);
        for (PneumaticAirPortStorage storage : inputStorages) {
            storage.addAir(-(int) air);
        }
    }

    @Override
    public boolean canOutput(Level level, RecipeStorages storages, RecipeStateModel state) {
//        var outputStorages = storages.getOutputStorages(PneumaticAirPortStorage.class);
//        for (PneumaticAirPortStorage storage : outputStorages) {
//            if (storage.getPressure() <= bar) {
//                return true;
//            }
//        }
        return true;
    }

    @Override
    public void output(Level level, RecipeStorages storages, RecipeStateModel state) {
        var outputStorages = storages.getOutputStorages(PneumaticAirPortStorage.class);
        for (PneumaticAirPortStorage storage : outputStorages) {
            storage.addAir(air);
        }
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeModel model, IFocusGroup focus, IJeiHelpers helpers, SlotGrid grid, IRecipeSlotBuilder recipeSlot) {
        recipeSlot.addIngredient(MMJeiIngredients.PNEUMATIC_AIR, new PneumaticAirStack(air, bar));
    }

    @Override
    public JsonObject debugInput(Level level, RecipeStorages storages, JsonObject json) {
        return null;
    }

    @Override
    public JsonObject debugOutput(Level level, RecipeStorages storages, JsonObject json) {
        return null;
    }
}
