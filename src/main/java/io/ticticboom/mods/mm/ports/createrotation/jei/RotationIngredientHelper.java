package io.ticticboom.mods.mm.ports.createrotation.jei;

import io.ticticboom.mods.mm.compat.jei.port.RotationJeiPortTypeEntry;
import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.subtypes.UidContext;
import org.jetbrains.annotations.Nullable;

public class RotationIngredientHelper implements IIngredientHelper<RotationStack> {
    @Override
    public IIngredientType<RotationStack> getIngredientType() {
        return RotationJeiPortTypeEntry.ROT_STACK;
    }

    @Override
    public String getDisplayName(RotationStack ingredient) {
        return "Create Rotation";
    }

    @Override
    public String getUniqueId(RotationStack ingredient, UidContext context) {
        return "create_rotation" + ingredient.speed;
    }

    @Override
    public String getModId(RotationStack ingredient) {
        return "create";
    }

    @Override
    public String getResourceId(RotationStack ingredient) {
        return "rotation";
    }

    @Override
    public RotationStack copyIngredient(RotationStack ingredient) {
        return new RotationStack(ingredient.speed);
    }

    @Override
    public String getErrorInfo(@Nullable RotationStack ingredient) {
        return "Error";
    }
}
