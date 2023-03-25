package io.ticticboom.mods.mm.recipe.js;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.recipe.IConfiguredRecipeEntry;
import io.ticticboom.mods.mm.recipe.MMRecipeEntry;
import io.ticticboom.mods.mm.recipe.RecipeContext;

public class RecipeEntryJS extends MMRecipeEntry {

    private ProcessRecipeIOCallback processInputs;
    private ProcessRecipeIOCallback processOutputs;

    public RecipeEntryJS(ProcessRecipeIOCallback processInputs, ProcessRecipeIOCallback processOutputs) {
        this.processInputs = processInputs;

        this.processOutputs = processOutputs;
    }

    @Override
    public IConfiguredRecipeEntry parse(JsonObject json) {
        return new ConfiguredRecipeEntryJS(json);
    }

    @Override
    public boolean processInputs(IConfiguredRecipeEntry config, RecipeContext original, RecipeContext ctx) {
        var conf = ((ConfiguredRecipeEntryJS) config);
        return processInputs.run(conf.data(), original, ctx);
    }

    @Override
    public boolean processOutputs(IConfiguredRecipeEntry config, RecipeContext original, RecipeContext ctx) {
        var conf = ((ConfiguredRecipeEntryJS) config);
        return processOutputs.run(conf.data(), original, ctx);
    }

    @FunctionalInterface
    public interface ProcessRecipeIOCallback {
        boolean run(JsonObject config, RecipeContext originalContext, RecipeContext activeContext);
    }
}
