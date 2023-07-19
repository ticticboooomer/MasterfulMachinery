package io.ticticboom.mods.mm.recipedisplay;

import com.google.gson.JsonObject;

public abstract class MMRecipeDisplayElement {

    public abstract IConfiguredRecipeDisplayElement parse(JsonObject json);


    public abstract void render(IConfiguredRecipeDisplayElement config, RecipeDisplayContext ctx);
}
