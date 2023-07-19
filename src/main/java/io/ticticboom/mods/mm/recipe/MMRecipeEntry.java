package io.ticticboom.mods.mm.recipe;

import com.google.gson.JsonObject;

public abstract class MMRecipeEntry {
    public abstract IConfiguredRecipeEntry parse(JsonObject json);
    public abstract boolean processInputs(IConfiguredRecipeEntry config, RecipeContext original, RecipeContext ctx);

    public abstract boolean processOutputs(IConfiguredRecipeEntry config, RecipeContext original, RecipeContext ctx);
    public int getNewTickLimit(IConfiguredRecipeEntry config, RecipeContext original, RecipeContext ctx, int currentLimit) {
        return currentLimit;
    }
}
