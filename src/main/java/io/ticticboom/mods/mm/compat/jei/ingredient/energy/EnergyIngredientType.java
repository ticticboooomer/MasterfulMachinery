package io.ticticboom.mods.mm.compat.jei.ingredient.energy;

import mezz.jei.api.ingredients.IIngredientType;

public class EnergyIngredientType implements IIngredientType<EnergyStack> {
    @Override
    public Class<? extends EnergyStack> getIngredientClass() {
        return EnergyStack.class;
    }
}
