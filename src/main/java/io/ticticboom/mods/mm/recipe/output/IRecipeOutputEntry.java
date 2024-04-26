package io.ticticboom.mods.mm.recipe.output;

import io.ticticboom.mods.mm.recipe.RecipeStorages;
import net.minecraft.world.level.Level;

public interface IRecipeOutputEntry {
    boolean canOutput(Level level, RecipeStorages storages);
    void output(Level level, RecipeStorages storages);
    default void processTick(Level level, RecipeStorages storages) {}

}
