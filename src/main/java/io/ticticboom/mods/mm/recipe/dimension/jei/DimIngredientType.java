package io.ticticboom.mods.mm.recipe.dimension.jei;

import mezz.jei.api.ingredients.IIngredientType;

public class DimIngredientType implements IIngredientType<DimStack> {
    @Override
    public Class<? extends DimStack> getIngredientClass() {
        return DimStack.class;
    }
}
