package io.ticticboom.mods.mm.port.kinetic;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.compat.jei.SlotGrid;
import io.ticticboom.mods.mm.compat.jei.ingredient.MMJeiIngredients;
import io.ticticboom.mods.mm.compat.jei.ingredient.create.CreateRotationStack;
import io.ticticboom.mods.mm.port.IPortIngredient;
import io.ticticboom.mods.mm.recipe.RecipeModel;
import io.ticticboom.mods.mm.recipe.RecipeStateModel;
import io.ticticboom.mods.mm.recipe.RecipeStorages;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.world.level.Level;

public class CreateKineticPortIngredient implements IPortIngredient {

    private final float speed;

    public CreateKineticPortIngredient(float speed) {

        this.speed = speed;
    }

    @Override
    public boolean canProcess(Level level, RecipeStorages storages, RecipeStateModel state) {
        if(storages == null) return false;
        var inputs = storages.getInputStorages(CreateKineticPortStorage.class);
        for (CreateKineticPortStorage input : inputs) {
            if (Math.abs(input.getSpeed()) >= Math.abs(speed)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void process(Level level, RecipeStorages storages, RecipeStateModel state) {

    }

    @Override
    public boolean canOutput(Level level, RecipeStorages storages, RecipeStateModel state) {
        var outputs = storages.getOutputStorages(CreateKineticPortStorage.class);
        return !outputs.isEmpty();
    }

    @Override
    public void output(Level level, RecipeStorages storages, RecipeStateModel state) {
        var outputs = storages.getOutputStorages(CreateKineticPortStorage.class);
        for (CreateKineticPortStorage output : outputs) {
            output.updateSpeed(speed);
            output.stopNext();
        }
    }

    @Override
    public void outputTick(Level level, RecipeStorages storages, RecipeStateModel state) {
        var outputs = storages.getOutputStorages(CreateKineticPortStorage.class);
        for (CreateKineticPortStorage output : outputs) {
            output.updateSpeed(speed);
        }
    }

    @Override
    public void ditchRecipe(Level level, RecipeStorages storages, RecipeStateModel state) {
        var outputs = storages.getOutputStorages(CreateKineticPortStorage.class);
        for (CreateKineticPortStorage output : outputs) {
            output.updateSpeed(0);
        }
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeModel model, IFocusGroup focus, IJeiHelpers helpers, SlotGrid grid, IRecipeSlotBuilder recipeSlot) {
        recipeSlot.addIngredient(MMJeiIngredients.CREATE_ROTATION, new CreateRotationStack(speed));
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
