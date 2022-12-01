package io.ticticboom.mods.mm.recipe.preset;

import io.ticticboom.mods.mm.recipe.IConfiguredRecipeEntry;
import net.minecraft.resources.ResourceLocation;

public record PresetConfiguredRecipeEntry(
        ResourceLocation preset
) implements IConfiguredRecipeEntry {
}
