package io.ticticboom.mods.mm.recipe.gates;

import io.ticticboom.mods.mm.recipe.ConfiguredRecipeEntry;
import io.ticticboom.mods.mm.recipe.IConfiguredRecipeEntry;

import java.util.List;

public record GateConfiguredRecipeEntry(
        List<ConfiguredRecipeEntry> conditions
) implements IConfiguredRecipeEntry {
}
