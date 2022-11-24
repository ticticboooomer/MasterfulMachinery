package io.ticticboom.mods.mm.recipe.tickmodifier.ingredient;

import io.ticticboom.mods.mm.ports.base.ConfiguredIngredient;
import io.ticticboom.mods.mm.recipe.IConfiguredRecipeEntry;

public record IngredientTickModifierConfiguredRecipeEntry(
        ConfiguredIngredient ingredient,
        int duration
) implements IConfiguredRecipeEntry {
}
