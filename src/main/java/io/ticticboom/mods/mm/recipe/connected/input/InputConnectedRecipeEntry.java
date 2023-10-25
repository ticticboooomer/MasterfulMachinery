package io.ticticboom.mods.mm.recipe.connected.input;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.recipe.IConfiguredRecipeEntry;
import io.ticticboom.mods.mm.recipe.IRecipeEntryContext;
import io.ticticboom.mods.mm.recipe.MMRecipeEntry;
import io.ticticboom.mods.mm.recipe.RecipeContext;

public class InputConnectedRecipeEntry extends MMRecipeEntry {
    @Override
    public IConfiguredRecipeEntry parse(JsonObject json) {
        return new InputConnectedConfiguredRecipeEntry(json.get("id").getAsString(), json.get("setFlag").getAsBoolean());
    }

    @Override
    public boolean processInputs(IConfiguredRecipeEntry config, RecipeContext original, RecipeContext ctx) {
        var conf = (InputConnectedConfiguredRecipeEntry) config;
        for (IRecipeEntryContext context : ctx.contexts()) {
            if (context instanceof InputConnectedRecipeEntryContext inputConn) {
                if (inputConn.id().equals(conf.id())) {
                    inputConn.setFlag(conf.setFlag());
                    return true;
                }
            }
        }
        ctx.contexts().add(new InputConnectedRecipeEntryContext(conf.id(), conf.setFlag()));
        return true;
    }

    @Override
    public boolean processOutputs(IConfiguredRecipeEntry config, RecipeContext original, RecipeContext ctx) {
        return false;
    }
}
