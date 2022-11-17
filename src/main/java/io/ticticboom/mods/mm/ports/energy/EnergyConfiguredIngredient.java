package io.ticticboom.mods.mm.ports.energy;

import io.ticticboom.mods.mm.ports.base.IConfiguredIngredient;

public record EnergyConfiguredIngredient(
        int amount
) implements IConfiguredIngredient {
}
