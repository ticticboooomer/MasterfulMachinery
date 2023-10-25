package io.ticticboom.mods.mm.ports.energy.jei;

import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.resources.ResourceLocation;
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
    public ResourceLocation getResourceLocation(EnergyStack ingredient) {
        return new ResourceLocation("forge");
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
