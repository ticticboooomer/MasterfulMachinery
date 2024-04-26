package io.ticticboom.mods.mm.recipe.input;

import io.ticticboom.mods.mm.recipe.RecipeStorages;
import net.minecraft.world.level.Level;

public interface IRecipeIngredientEntry {
    boolean canProcess(Level level, RecipeStorages storages);
    void process(Level level, RecipeStorages storages);
    default void processTick(Level level, RecipeStorages storages) {}
}
