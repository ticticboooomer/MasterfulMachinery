package io.ticticboom.mods.mm.ports.item;

import io.ticticboom.mods.mm.ports.base.IConfiguredIngredient;
import net.minecraft.resources.ResourceLocation;

public record ItemConfiguredIngredient(
        ResourceLocation item,
        int count
) implements IConfiguredIngredient {
}
