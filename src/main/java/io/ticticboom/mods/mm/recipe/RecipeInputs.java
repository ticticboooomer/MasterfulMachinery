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

    public boolean canProcess(Level level, RecipeStorages storages) {
        for (IRecipeIngredientEntry input : inputs) {
            if (!input.canProcess(level, storages)) {
                return false;
            }
        }
        return true;
    }

    public void processTick(Level level, RecipeStorages storages) {
        for (IRecipeIngredientEntry input : inputs) {
            input.processTick(level, storages);
        }
    }

    public void process(Level level, RecipeStorages storages) {
        for (IRecipeIngredientEntry input : inputs) {
            input.process(level, storages);
        }
    }
}
