package io.ticticboom.mods.mm.ports.base;

import net.minecraft.resources.ResourceLocation;

public record ConfiguredIngredient(
        ResourceLocation type,
        IConfiguredIngredient config
) {
}
