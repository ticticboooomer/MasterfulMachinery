package io.ticticboom.mods.mm.recipe.designated;

import io.ticticboom.mods.mm.recipe.ConfiguredRecipeEntry;
import io.ticticboom.mods.mm.recipe.IConfiguredRecipeEntry;
import net.minecraft.resources.ResourceLocation;

public record DesignatedConfiguredRecipeEntry(
    ConfiguredRecipeEntry entry,
    ResourceLocation portId
) implements IConfiguredRecipeEntry {
}
