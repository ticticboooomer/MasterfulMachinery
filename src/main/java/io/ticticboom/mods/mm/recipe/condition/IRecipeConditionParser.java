package io.ticticboom.mods.mm.recipe.condition;

import com.google.gson.JsonObject;

public interface IRecipeConditionParser {
    IRecipeCondition parse(JsonObject json);
}
