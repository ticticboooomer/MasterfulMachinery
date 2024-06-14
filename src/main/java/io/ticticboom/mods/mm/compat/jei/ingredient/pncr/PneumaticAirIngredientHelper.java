package io.ticticboom.mods.mm.compat.jei.ingredient.pncr;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.compat.jei.ingredient.MMJeiIngredients;
import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class PneumaticAirIngredientHelper  implements IIngredientHelper<PneumaticAirStack>{
    @Override
    public IIngredientType<PneumaticAirStack> getIngredientType() {
        return MMJeiIngredients.PNEUMATIC_AIR;
    }

    @Override
    public String getDisplayName(PneumaticAirStack ingredient) {
        return "Pneumatic Air";
    }

    @Override
    public String getUniqueId(PneumaticAirStack ingredient, UidContext context) {
        return "pneumaticcraft/air";
    }

    @Override
    public ResourceLocation getResourceLocation(PneumaticAirStack ingredient) {
        return Ref.id("pneumaticcraft/air");
    }

    @Override
    public PneumaticAirStack copyIngredient(PneumaticAirStack ingredient) {
        return new PneumaticAirStack(ingredient.air(), ingredient.pressure());
    }

    @Override
    public String getErrorInfo(@Nullable PneumaticAirStack ingredient) {
        return "Error";
    }
}
