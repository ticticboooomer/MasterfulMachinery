package io.ticticboom.mods.mm.recipe.input.consume;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.port.MMPortRegistry;
import io.ticticboom.mods.mm.recipe.input.IRecipeIngredientEntry;
import io.ticticboom.mods.mm.recipe.input.IRecipeIngredientEntryParser;

public class ConsumeRecipeIngredientEntryParser implements IRecipeIngredientEntryParser {
    @Override
    public IRecipeIngredientEntry parse(JsonObject json) {
        var ingredient = MMPortRegistry.parseIngredient(json.getAsJsonObject("ingredient"));
        double chance = 1.f;
        if (json.has("chance")) {
            chance = json.get("chance").getAsDouble();
        }
        boolean perTick = false;
        if (json.has("per_tick")) {
            perTick = json.get("per_tick").getAsBoolean();
        }
        return new ConsumeRecipeIngredientEntry(ingredient, chance, perTick);
    }
}
