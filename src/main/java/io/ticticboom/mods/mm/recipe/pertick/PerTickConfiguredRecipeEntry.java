package io.ticticboom.mods.mm.recipe.pertick;

import io.ticticboom.mods.mm.ports.base.ConfiguredIngredient;
import io.ticticboom.mods.mm.recipe.IConfiguredRecipeEntry;

public record PerTickConfiguredRecipeEntry(
        ConfiguredIngredient ingredient
) implements IConfiguredRecipeEntry {
}
