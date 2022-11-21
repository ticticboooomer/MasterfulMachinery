package io.ticticboom.mods.mm.ports.mekanism;

import io.ticticboom.mods.mm.ports.base.IConfiguredIngredient;
import net.minecraft.resources.ResourceLocation;

public record MekChemicalConfiguredIngredient(
        ResourceLocation chemical,
        long amount
) implements IConfiguredIngredient {
}
