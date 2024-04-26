package io.ticticboom.mods.mm.port.fluid;

import io.ticticboom.mods.mm.port.IPortIngredient;
import io.ticticboom.mods.mm.recipe.RecipeStorages;
import net.minecraft.world.level.Level;

public class FluidPortIngredient implements IPortIngredient {
    @Override
    public boolean canProcess(Level level, RecipeStorages storages) {
        return false;
    }

    @Override
    public void process(Level level, RecipeStorages storages) {

    }

    @Override
    public boolean canOutput(Level level, RecipeStorages storages) {
        return false;
    }

    @Override
    public void output(Level level, RecipeStorages storages) {

    }
}
