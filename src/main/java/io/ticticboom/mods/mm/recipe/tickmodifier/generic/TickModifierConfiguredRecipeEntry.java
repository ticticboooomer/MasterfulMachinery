package io.ticticboom.mods.mm.recipe.tickmodifier.generic;

import io.ticticboom.mods.mm.recipe.IConfiguredRecipeEntry;

public record TickModifierConfiguredRecipeEntry(
        int duration
) implements IConfiguredRecipeEntry {
}
