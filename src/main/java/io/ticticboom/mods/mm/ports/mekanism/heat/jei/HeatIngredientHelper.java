package io.ticticboom.mods.mm.ports.mekanism.heat.jei;

import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class HeatIngredientHelper implements IIngredientHelper<HeatStack> {
    @Override
    public IIngredientType<HeatStack> getIngredientType() {
        return new HeatIngredientType();
    }

    @Override
    public String getDisplayName(HeatStack ingredient) {
        return "Mekanism Heat";
    }

    @Override
    public String getUniqueId(HeatStack ingredient, UidContext context) {
        return "mek_heat";
    }

    @Override
    public ResourceLocation getResourceLocation(HeatStack ingredient) {
        return new ResourceLocation("mekanism", "heat");
    }

    @Override
    public HeatStack copyIngredient(HeatStack ingredient) {
        return new HeatStack(ingredient.amount());
    }

    @Override
    public String getErrorInfo(@Nullable HeatStack ingredient) {
        return "Error";
    }
}
