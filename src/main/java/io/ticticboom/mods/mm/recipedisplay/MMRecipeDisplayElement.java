package io.ticticboom.mods.mm.recipedisplay;

import com.google.gson.JsonObject;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class MMRecipeDisplayElement extends ForgeRegistryEntry<MMRecipeDisplayElement> {

    public abstract IConfiguredRecipeDisplayElement parse(JsonObject json);


    public abstract void render(IConfiguredRecipeDisplayElement config, RecipeDisplayContext ctx);
}
