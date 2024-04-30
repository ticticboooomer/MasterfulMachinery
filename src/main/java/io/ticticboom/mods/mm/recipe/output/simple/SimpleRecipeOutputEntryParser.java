package io.ticticboom.mods.mm.recipe.output.simple;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.port.MMPortRegistry;
import io.ticticboom.mods.mm.recipe.output.IRecipeOutputEntry;
import io.ticticboom.mods.mm.recipe.output.IRecipeOutputEntryParser;

public class SimpleRecipeOutputEntryParser implements IRecipeOutputEntryParser {
    @Override
    public IRecipeOutputEntry parse(JsonObject json) {
        var ingredient = MMPortRegistry.parseIngredient(json.getAsJsonObject("ingredient"));
        double chance = 1.f;
        if (json.has("chance")) {
            chance = json.get("chance").getAsDouble();
        }
        boolean perTick = false;
        if (json.has("per_tick")) {
            perTick = json.get("per_tick").getAsBoolean();
        }
        return new SimpleRecipeOutputEntry(ingredient, chance, perTick);
    }
}
