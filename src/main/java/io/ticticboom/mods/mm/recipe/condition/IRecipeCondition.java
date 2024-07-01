package io.ticticboom.mods.mm.recipe.condition;

import io.ticticboom.mods.mm.recipe.RecipeStateModel;
import io.ticticboom.mods.mm.recipe.RecipeStorages;
import net.minecraft.world.level.Level;

public interface IRecipeCondition {
    boolean canRun(Level level, RecipeStateModel state);
}
