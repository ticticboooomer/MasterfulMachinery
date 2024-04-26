package io.ticticboom.mods.mm.recipe.output;

import com.google.gson.JsonObject;

public interface IRecipeOutputEntryParser {
    IRecipeOutputEntry parse(JsonObject json);
}
