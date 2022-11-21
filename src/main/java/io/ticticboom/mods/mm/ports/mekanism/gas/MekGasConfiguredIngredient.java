package io.ticticboom.mods.mm.ports.mekanism.gas;

import io.ticticboom.mods.mm.ports.base.IConfiguredIngredient;
import net.minecraft.resources.ResourceLocation;

public record MekGasConfiguredIngredient(
        ResourceLocation gas,
        long amount
) implements IConfiguredIngredient {
}
