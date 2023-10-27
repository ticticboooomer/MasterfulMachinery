package io.ticticboom.mods.mm.recipedisplay;

public interface IConfiguredRecipeDisplayElement {
    public abstract void render(IConfiguredRecipeDisplayElement config, RecipeDisplayContext ctx);
}
