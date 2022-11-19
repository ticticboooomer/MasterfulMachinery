package io.ticticboom.mods.mm.ports.energy.jei;

import mezz.jei.api.ingredients.IIngredientType;

public class EnergyIngredientType implements IIngredientType<EnergyStack> {
    @Override
    public Class<? extends EnergyStack> getIngredientClass() {
        return EnergyStack.class;
    }
}
