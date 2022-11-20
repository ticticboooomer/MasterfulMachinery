package io.ticticboom.mods.mm.ports.createrotation;

import io.ticticboom.mods.mm.ports.base.IConfiguredIngredient;

public record RotationConfiguredIngredient(
        float speed
) implements IConfiguredIngredient {
}
