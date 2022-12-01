package io.ticticboom.mods.mm.recipe.event.listen;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.recipe.IConfiguredRecipeEntry;
import io.ticticboom.mods.mm.recipe.MMRecipeEntry;
import io.ticticboom.mods.mm.recipe.RecipeContext;

public class EventListenRecipeEntry extends MMRecipeEntry {
    @Override
    public IConfiguredRecipeEntry parse(JsonObject json) {
        return null;
    }

    @Override
    public boolean processInputs(IConfiguredRecipeEntry config, RecipeContext original, RecipeContext ctx) {
        return false;
    }

    @Override
    public boolean processOutputs(IConfiguredRecipeEntry config, RecipeContext original, RecipeContext ctx) {
        return false;
    }
}
