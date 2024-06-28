package io.ticticboom.mods.mm.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.recipe.input.IRecipeIngredientEntry;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public record RecipeInputs(
        List<IRecipeIngredientEntry> inputs
) {
    public static RecipeInputs parse(JsonArray json) {
        var inputs = new ArrayList<IRecipeIngredientEntry>();
        for (JsonElement elem : json) {
            JsonObject entryJson = elem.getAsJsonObject();
            inputs.add(MachineRecipeManager.parseIngredientEntry(entryJson));
        }
        return new RecipeInputs(inputs);
    }

    public boolean canProcess(Level level, RecipeStorages storages, RecipeStateModel model) {
        for (IRecipeIngredientEntry input : inputs) {
            if (!input.canProcess(level, storages, model)) {
                return false;
            }
        }
        return true;
    }

    public void processTick(Level level, RecipeStorages storages, RecipeStateModel model) {
        for (IRecipeIngredientEntry input : inputs) {
            input.processTick(level, storages, model);
        }
    }

    public void process(Level level, RecipeStorages storages, RecipeStateModel model) {
        for (IRecipeIngredientEntry input : inputs) {
            input.process(level, storages, model);
        }
    }

    public void ditch(Level level, RecipeStorages storages, RecipeStateModel model) {
        for (IRecipeIngredientEntry input : inputs) {
            input.ditchRecipe(level, storages, model);
        }
    }

    public JsonArray debugRun(Level level, RecipeStorages storages, RecipeStateModel model) {
        var jsonArray = new JsonArray();
        for (IRecipeIngredientEntry input : inputs) {
            var expected = input.debugExpected(level, storages, model, new JsonObject());
            var inputRunJson = new JsonObject();
            inputRunJson.add("expected", expected);
            jsonArray.add(inputRunJson);
        }
        return jsonArray;
    }
}
