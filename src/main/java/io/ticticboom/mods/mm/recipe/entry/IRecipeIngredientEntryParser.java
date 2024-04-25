package io.ticticboom.mods.mm.recipe.entry;

import com.google.gson.JsonObject;

public interface IRecipeIngredientEntryParser {
    IRecipeIngredientEntry parse(JsonObject json);
}
