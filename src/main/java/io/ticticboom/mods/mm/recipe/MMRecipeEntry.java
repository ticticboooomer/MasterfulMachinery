package io.ticticboom.mods.mm.recipe;

import com.google.gson.JsonObject;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class MMRecipeEntry extends ForgeRegistryEntry<MMRecipeEntry> {
    public abstract IConfiguredRecipeEntry parse(JsonObject json);
    public abstract boolean checkInput(IConfiguredRecipeEntry config, RecipeContext ctx);
    public abstract void process(IConfiguredRecipeEntry config, RecipeContext ctx);
}
