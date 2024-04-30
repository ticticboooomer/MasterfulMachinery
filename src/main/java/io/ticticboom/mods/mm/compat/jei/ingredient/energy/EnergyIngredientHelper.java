package io.ticticboom.mods.mm.compat.jei.ingredient.energy;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.compat.jei.ingredient.MMJeiIngredients;
import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class EnergyIngredientHelper implements IIngredientHelper<EnergyStack> {
    @Override
    public IIngredientType<EnergyStack> getIngredientType() {
        return MMJeiIngredients.ENERGY;
    }

    @Override
    public String getDisplayName(EnergyStack ingredient) {
        return "Forge Energy";
    }

    @Override
    public String getUniqueId(EnergyStack ingredient, UidContext context) {
        return "energy";
    }

    @Override
    public ResourceLocation getResourceLocation(EnergyStack ingredient) {
        return Ref.id("energy");
    }

    @Override
    public EnergyStack copyIngredient(EnergyStack ingredient) {
        return new EnergyStack(ingredient.amount());
    }

    @Override
    public String getErrorInfo(@Nullable EnergyStack ingredient) {
        return "Error";
    }
}
