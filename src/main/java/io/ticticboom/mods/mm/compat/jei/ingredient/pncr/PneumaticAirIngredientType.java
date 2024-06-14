package io.ticticboom.mods.mm.compat.jei.ingredient.pncr;

import mezz.jei.api.ingredients.IIngredientType;

public class PneumaticAirIngredientType  implements IIngredientType<PneumaticAirStack> {
    @Override
    public Class<? extends PneumaticAirStack> getIngredientClass() {
        return PneumaticAirStack.class;
    }
}
