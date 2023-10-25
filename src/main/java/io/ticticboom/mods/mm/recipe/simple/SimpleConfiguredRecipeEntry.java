package io.ticticboom.mods.mm.recipe.simple;

import io.ticticboom.mods.mm.ports.base.ConfiguredIngredient;
import io.ticticboom.mods.mm.recipe.IConfiguredRecipeEntry;

import java.util.Optional;

public record SimpleConfiguredRecipeEntry(
        ConfiguredIngredient ingredient,
        Optional<Float> chance
) implements IConfiguredRecipeEntry {
}
