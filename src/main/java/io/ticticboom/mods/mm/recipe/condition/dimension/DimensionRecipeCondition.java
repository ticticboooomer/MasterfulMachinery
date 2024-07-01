package io.ticticboom.mods.mm.recipe.condition.dimension;

import io.ticticboom.mods.mm.recipe.RecipeStateModel;
import io.ticticboom.mods.mm.recipe.RecipeStorages;
import io.ticticboom.mods.mm.recipe.condition.IRecipeCondition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class DimensionRecipeCondition implements IRecipeCondition {

    private final ResourceLocation id;

    public DimensionRecipeCondition(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public boolean canRun(Level level, RecipeStateModel state) {
        return level.dimension().location().equals(id);
    }
}
