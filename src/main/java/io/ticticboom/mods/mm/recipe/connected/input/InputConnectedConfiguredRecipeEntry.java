package io.ticticboom.mods.mm.recipe.connected.input;

import io.ticticboom.mods.mm.recipe.IConfiguredRecipeEntry;

public record InputConnectedConfiguredRecipeEntry(
        String id,
        boolean setFlag
) implements IConfiguredRecipeEntry {
}
