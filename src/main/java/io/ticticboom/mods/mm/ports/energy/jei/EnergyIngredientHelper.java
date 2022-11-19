package io.ticticboom.mods.mm.ports.energy.jei;

import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.subtypes.UidContext;
import org.jetbrains.annotations.Nullable;

public class EnergyIngredientHelper implements IIngredientHelper<EnergyStack> {
    @Override
    public IIngredientType<EnergyStack> getIngredientType() {
        return new EnergyIngredientType();
    }

    @Override
    public String getDisplayName(EnergyStack ingredient) {
        return "Forge Energy";
    }

    @Override
    public String getUniqueId(EnergyStack ingredient, UidContext context) {
        return "forge_energy";
    }

    @Override
    public String getModId(EnergyStack ingredient) {
        return "forge";
    }

    @Override
    public String getResourceId(EnergyStack ingredient) {
        return "fe_" + ingredient.amount + "_fe";
    }

    @Override
    public EnergyStack copyIngredient(EnergyStack ingredient) {
        return new EnergyStack(ingredient.amount);
    }

    @Override
    public String getErrorInfo(@Nullable EnergyStack ingredient) {
        return "Error";
    }
}
