package io.ticticboom.mods.mm.recipe.js;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.recipe.IConfiguredRecipeEntry;

public record ConfiguredRecipeEntryJS(
        JsonObject data
) implements IConfiguredRecipeEntry {
}
