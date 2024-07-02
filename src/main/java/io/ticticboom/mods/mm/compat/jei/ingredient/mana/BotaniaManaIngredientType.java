package io.ticticboom.mods.mm.compat.jei.ingredient.mana;

import mezz.jei.api.ingredients.IIngredientType;

public class BotaniaManaIngredientType implements IIngredientType<BotaniaManaStack> {
    @Override
    public Class<? extends BotaniaManaStack> getIngredientClass() {
        return BotaniaManaStack.class;
    }
}
