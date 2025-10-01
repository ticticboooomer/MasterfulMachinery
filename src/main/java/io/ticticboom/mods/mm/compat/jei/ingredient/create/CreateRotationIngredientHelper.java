package io.ticticboom.mods.mm.compat.jei.ingredient.create;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.compat.jei.ingredient.MMJeiIngredients;
import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class CreateRotationIngredientHelper implements IIngredientHelper<CreateRotationStack> {
    private static final ResourceLocation ingredientId = Ref.id("create-rotation");

    @Override
    public IIngredientType<CreateRotationStack> getIngredientType() {
        return MMJeiIngredients.CREATE_ROTATION;
    }

    @Override
    public String getDisplayName(CreateRotationStack createRotationStack) {
        return "Rotation (Create Mod)";
    }

    @Override
    public String getUniqueId(CreateRotationStack createRotationStack, UidContext uidContext) {
        return "create-rotation-" + createRotationStack.speed();
    }

    @Override
    public ResourceLocation getResourceLocation(CreateRotationStack createRotationStack) {
        return ingredientId;
    }

    @Override
    public CreateRotationStack copyIngredient(CreateRotationStack createRotationStack) {
        return new CreateRotationStack(createRotationStack.speed());
    }

    @Override
    public String getErrorInfo(@Nullable CreateRotationStack createRotationStack) {
        return "Error";
    }
}
