package io.ticticboom.mods.mm.recipe.connected.output;

import io.ticticboom.mods.mm.recipe.ConfiguredRecipeEntry;
import io.ticticboom.mods.mm.recipe.IConfiguredRecipeEntry;

public record OutputConnectedConfiguredRecipeEntry(
        String connectionId,
        ConfiguredRecipeEntry entry
) implements IConfiguredRecipeEntry {

}
