package io.ticticboom.mods.mm.recipe.entry.consume;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.port.MMPortRegistry;
import io.ticticboom.mods.mm.recipe.entry.IRecipeIngredientEntry;
import io.ticticboom.mods.mm.recipe.entry.IRecipeIngredientEntryParser;

public class ConsumeRecipeIngredientEntryParser implements IRecipeIngredientEntryParser {
    @Override
    public IRecipeIngredientEntry parse(JsonObject json) {
        var ingredient = MMPortRegistry.parseIngredient(json.getAsJsonObject("ingredient"));
        return new ConsumeRecipeIngredientEntry(ingredient);
    }
}
