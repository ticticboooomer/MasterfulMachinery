package io.ticticboom.mods.mm.recipe.dimension.jei;

import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class DimIngredientHelper implements IIngredientHelper<DimStack> {
    @Override
    public IIngredientType<DimStack> getIngredientType() {
        return new DimIngredientType();
    }

    @Override
    public String getDisplayName(DimStack ingredient) {
        return "Dimension";
    }

    @Override
    public String getUniqueId(DimStack ingredient, UidContext context) {
        return "mm_dimension";
    }

    @Override
    public ResourceLocation getResourceLocation(DimStack ingredient) {
        return ingredient.dim();
    }

    @Override
    public DimStack copyIngredient(DimStack ingredient) {
        return new DimStack(ingredient.dim());
    }

    @Override
    public String getErrorInfo(@Nullable DimStack ingredient) {
        return "Error";
    }
}
