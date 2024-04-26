package io.ticticboom.mods.mm.recipe.input.consume;

import io.ticticboom.mods.mm.port.IPortIngredient;
import io.ticticboom.mods.mm.recipe.RecipeStorages;
import io.ticticboom.mods.mm.recipe.input.IRecipeIngredientEntry;
import net.minecraft.world.level.Level;

public class ConsumeRecipeIngredientEntry implements IRecipeIngredientEntry {

    private final IPortIngredient ingredient;

    public ConsumeRecipeIngredientEntry(IPortIngredient ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public boolean canProcess(Level level, RecipeStorages storages) {
        return ingredient.canProcess(level, storages);
    }

    @Override
    public void process(Level level, RecipeStorages storages) {
        ingredient.process(level, storages);
    }
}
