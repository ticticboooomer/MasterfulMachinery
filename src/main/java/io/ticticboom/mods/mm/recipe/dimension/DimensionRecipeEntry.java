package io.ticticboom.mods.mm.recipe.dimension;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.recipe.IConfiguredRecipeEntry;
import io.ticticboom.mods.mm.recipe.MMRecipeEntry;
import io.ticticboom.mods.mm.recipe.RecipeContext;
import net.minecraft.resources.ResourceLocation;

public class DimensionRecipeEntry extends MMRecipeEntry {
    @Override
    public IConfiguredRecipeEntry parse(JsonObject json) {
        var dimension = new ResourceLocation(json.get("dimension").getAsString());
        return new DimensionConfiguredRecipeEntry(dimension);
    }

    @Override
    public boolean processInputs(IConfiguredRecipeEntry config, RecipeContext original, RecipeContext ctx) {
        DimensionConfiguredRecipeEntry conf = ((DimensionConfiguredRecipeEntry) config);
        return ctx.level().dimension().location().equals(conf.dimension());
    }

    @Override
    public boolean processOutputs(IConfiguredRecipeEntry config, RecipeContext original, RecipeContext ctx) {
        return true;
    }
}
