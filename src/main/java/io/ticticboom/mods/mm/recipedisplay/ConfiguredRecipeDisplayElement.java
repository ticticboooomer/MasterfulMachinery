package io.ticticboom.mods.mm.recipedisplay;

import net.minecraft.resources.ResourceLocation;

public record ConfiguredRecipeDisplayElement(
        ResourceLocation type,
        IConfiguredRecipeDisplayElement config
) {
}
