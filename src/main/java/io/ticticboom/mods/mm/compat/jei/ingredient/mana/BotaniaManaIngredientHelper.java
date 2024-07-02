package io.ticticboom.mods.mm.compat.jei.ingredient.mana;

import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.compat.jei.ingredient.MMJeiIngredients;
import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class BotaniaManaIngredientHelper implements IIngredientHelper<BotaniaManaStack> {
    @Override
    public IIngredientType<BotaniaManaStack> getIngredientType() {
        return MMJeiIngredients.BOTANIA_MANA;
    }

    @Override
    public String getDisplayName(BotaniaManaStack botaniaManaStack) {
        return "Botania Mana";
    }

    @Override
    public String getUniqueId(BotaniaManaStack botaniaManaStack, UidContext uidContext) {
        return "botania/mana";
    }

    @Override
    public ResourceLocation getResourceLocation(BotaniaManaStack botaniaManaStack) {
        return Ref.id("botania/mana");
    }

    @Override
    public BotaniaManaStack copyIngredient(BotaniaManaStack botaniaManaStack) {
        return new BotaniaManaStack(botaniaManaStack.mana());
    }

    @Override
    public String getErrorInfo(@Nullable BotaniaManaStack botaniaManaStack) {
        return "ERR";
    }
}
