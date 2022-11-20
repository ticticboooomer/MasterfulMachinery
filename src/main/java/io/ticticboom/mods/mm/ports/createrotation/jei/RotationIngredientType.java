package io.ticticboom.mods.mm.ports.createrotation.jei;

import mezz.jei.api.ingredients.IIngredientType;

public class RotationIngredientType implements IIngredientType<RotationStack> {
    @Override
    public Class<? extends RotationStack> getIngredientClass() {
        return RotationStack.class;
    }
}
