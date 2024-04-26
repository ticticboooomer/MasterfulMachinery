package io.ticticboom.mods.mm.recipe.input;

import com.google.gson.JsonObject;

public interface IRecipeIngredientEntryParser {
    IRecipeIngredientEntry parse(JsonObject json);
}
