package io.ticticboom.mods.mm.recipe.connected.output;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.recipe.*;
import io.ticticboom.mods.mm.recipe.connected.input.InputConnectedRecipeEntryContext;
import io.ticticboom.mods.mm.setup.MMRegistries;
import io.ticticboom.mods.mm.setup.model.RecipeModel;
import net.minecraft.resources.ResourceLocation;

public class OutputConnectedRecipeEntry extends MMRecipeEntry {
    @Override
    public IConfiguredRecipeEntry parse(JsonObject json) {
        var connectionId = json.get("id").getAsString();
        JsonObject obj = json.getAsJsonObject("entry");
        var type = ResourceLocation.tryParse(obj.get("type").getAsString());
        var entry = MMRegistries.RECIPE_ENTRIES.get().getValue(type);
        var config = new ConfiguredRecipeEntry(type, entry.parse(obj));
        return new OutputConnectedConfiguredRecipeEntry(connectionId, config);
    }

    @Override
    public boolean processInputs(IConfiguredRecipeEntry config, RecipeContext original, RecipeContext ctx) {
        return true;
    }

    @Override
    public boolean processOutputs(IConfiguredRecipeEntry config, RecipeContext original, RecipeContext ctx) {
        var conf = (OutputConnectedConfiguredRecipeEntry) config;
        for (IRecipeEntryContext context : ctx.contexts()) {
            if (context instanceof InputConnectedRecipeEntryContext connected) {
                if (connected.id().equals(conf.connectionId()) && connected.flag()) {
                    var type = MMRegistries.RECIPE_ENTRIES.get().getValue(conf.entry().type());
                    return type.processOutputs(conf.entry().entry(), original, ctx);
                }
            }
        }
        return true;
    }
}
