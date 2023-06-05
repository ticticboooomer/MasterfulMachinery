package io.ticticboom.mods.mm.recipe.dimension;

import io.ticticboom.mods.mm.recipe.IConfiguredRecipeEntry;
import net.minecraft.resources.ResourceLocation;

public record DimensionConfiguredRecipeEntry(
        ResourceLocation dimension
) implements IConfiguredRecipeEntry {
}
