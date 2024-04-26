package io.ticticboom.mods.mm.recipe.output.simple;

import io.ticticboom.mods.mm.port.IPortIngredient;
import io.ticticboom.mods.mm.recipe.RecipeStorages;
import io.ticticboom.mods.mm.recipe.output.IRecipeOutputEntry;
import net.minecraft.world.level.Level;

public class SimpleRecipeOutputEntry implements IRecipeOutputEntry {

    private final IPortIngredient ingredient;

    public SimpleRecipeOutputEntry(IPortIngredient ingredient) {

        this.ingredient = ingredient;
    }

    @Override
    public boolean canOutput(Level level, RecipeStorages storages) {
        return ingredient.canOutput(level, storages);
    }

    @Override
    public void output(Level level, RecipeStorages storages) {
        ingredient.output(level, storages);
    }
}
