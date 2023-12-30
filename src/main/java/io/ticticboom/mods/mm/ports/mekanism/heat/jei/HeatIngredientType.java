package io.ticticboom.mods.mm.ports.mekanism.heat.jei;

import mezz.jei.api.ingredients.IIngredientType;

public class HeatIngredientType implements IIngredientType<HeatStack> {
    @Override
    public Class<? extends HeatStack> getIngredientClass() {
        return HeatStack.class;
    }
}
