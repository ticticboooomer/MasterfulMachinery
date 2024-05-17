package io.ticticboom.mods.mm.port.kinetic;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.compat.jei.SlotGrid;
import io.ticticboom.mods.mm.port.IPortIngredient;
import io.ticticboom.mods.mm.recipe.RecipeModel;
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
    public boolean canProcess(Level level, RecipeStorages storages) {
        var inputs = storages.getInputStorages(CreateKineticPortStorage.class);
        for (CreateKineticPortStorage input : inputs) {
            if (input.getSpeed() >= speed) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void process(Level level, RecipeStorages storages) {

    }

    @Override
    public boolean canOutput(Level level, RecipeStorages storages) {
        var outputs = storages.getOutputStorages(CreateKineticPortStorage.class);
       return !outputs.isEmpty();

    }

    @Override
    public void output(Level level, RecipeStorages storages) {
        var outputs = storages.getOutputStorages(CreateKineticPortStorage.class);
        for (CreateKineticPortStorage output : outputs) {
            output.setSpeed(speed);
        }
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeModel model, IFocusGroup focus, IJeiHelpers helpers, SlotGrid grid, IRecipeSlotBuilder recipeSlot) {

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
