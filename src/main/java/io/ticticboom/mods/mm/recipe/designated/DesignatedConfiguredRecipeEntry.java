package io.ticticboom.mods.mm.recipe.designated;

import io.ticticboom.mods.mm.recipe.ConfiguredRecipeEntry;
import io.ticticboom.mods.mm.recipe.IConfiguredRecipeEntry;

public record DesignatedConfiguredRecipeEntry(
    ConfiguredRecipeEntry entry,
    String portId
) implements IConfiguredRecipeEntry {
}
