package io.ticticboom.mods.mm.ports.mekanism.heat;

import io.ticticboom.mods.mm.ports.base.IConfiguredIngredient;

public record MekHeatConfiguredIngredient(
        double amount
) implements IConfiguredIngredient {
}
