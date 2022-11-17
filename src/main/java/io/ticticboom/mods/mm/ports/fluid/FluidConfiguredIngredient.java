package io.ticticboom.mods.mm.ports.fluid;

import io.ticticboom.mods.mm.ports.base.IConfiguredIngredient;
import net.minecraft.resources.ResourceLocation;

public record FluidConfiguredIngredient(
        ResourceLocation fluid,
        int amount
) implements IConfiguredIngredient {
}
