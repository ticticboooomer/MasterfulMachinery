package io.ticticboom.mods.mm.compat.jei.ingredient.create;

import mezz.jei.api.ingredients.IIngredientType;

public class CreateRotationIngredientType implements IIngredientType<CreateRotationStack> {
    @Override
    public Class<? extends CreateRotationStack> getIngredientClass() {
        return CreateRotationStack.class;
    }
}
