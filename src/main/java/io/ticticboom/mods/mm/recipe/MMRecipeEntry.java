package io.ticticboom.mods.mm.recipe;

import com.google.gson.JsonObject;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class MMRecipeEntry extends ForgeRegistryEntry<MMRecipeEntry> {
    public abstract IConfiguredRecipeEntry parse(JsonObject json);
    public abstract boolean processInputs(IConfiguredRecipeEntry config, RecipeContext ctx);

    public abstract boolean processOutputs(IConfiguredRecipeEntry config, RecipeContext ctx);
}
