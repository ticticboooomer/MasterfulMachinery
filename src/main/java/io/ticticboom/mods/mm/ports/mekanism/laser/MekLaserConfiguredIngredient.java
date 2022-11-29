package io.ticticboom.mods.mm.ports.mekanism.laser;

import io.ticticboom.mods.mm.ports.base.IConfiguredIngredient;
import mekanism.api.math.FloatingLong;

public record MekLaserConfiguredIngredient(
        FloatingLong energy
) implements IConfiguredIngredient {
}
