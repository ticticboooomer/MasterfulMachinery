package io.ticticboom.mods.mm.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.recipe.entry.IRecipeIngredientEntry;

import java.util.ArrayList;
import java.util.List;

public record RecipeInputs(
        List<IRecipeIngredientEntry> inputs
) {
    public static RecipeInputs parse(JsonArray json) {
        var inputs = new ArrayList<IRecipeIngredientEntry>();
        for (JsonElement elem : json) {
            JsonObject entryJson = elem.getAsJsonObject();
            inputs.add(MachineRecipeManager.parseEntry(entryJson));
        }
        return new RecipeInputs(inputs);
    }
}
