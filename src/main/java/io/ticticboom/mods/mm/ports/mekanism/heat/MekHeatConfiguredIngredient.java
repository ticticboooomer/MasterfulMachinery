package io.ticticboom.mods.mm.ports.mekanism.heat;

import io.ticticboom.mods.mm.ports.base.IConfiguredIngredient;
import io.ticticboom.mods.mm.ports.base.IConfiguredPort;

public record MekHeatConfiguredIngredient(
        double amount
) implements IConfiguredIngredient {
}
