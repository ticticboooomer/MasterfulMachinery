package io.ticticboom.mods.mm.recipe;

import net.minecraft.resources.ResourceLocation;

public record ConfiguredRecipeEntry(
        ResourceLocation type,
        IConfiguredRecipeEntry entry
) {
}
