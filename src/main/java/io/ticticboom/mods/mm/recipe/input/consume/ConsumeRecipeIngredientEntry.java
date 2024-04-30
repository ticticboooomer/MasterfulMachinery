package io.ticticboom.mods.mm.recipe.input.consume;

import io.ticticboom.mods.mm.port.IPortIngredient;
import io.ticticboom.mods.mm.recipe.RecipeStateModel;
import io.ticticboom.mods.mm.recipe.RecipeStorages;
import io.ticticboom.mods.mm.recipe.input.IRecipeIngredientEntry;
import io.ticticboom.mods.mm.util.ChanceUtils;
import net.minecraft.world.level.Level;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

public class ConsumeRecipeIngredientEntry implements IRecipeIngredientEntry {

    private final IPortIngredient ingredient;
    private final double chance;
    private final boolean perTick;

    private boolean shouldRun = true;

    public ConsumeRecipeIngredientEntry(IPortIngredient ingredient, double chance, boolean perTick) {
        this.ingredient = ingredient;
        this.chance = chance;
        this.perTick = perTick;
    }

    @Override
    public boolean canProcess(Level level, RecipeStorages storages, RecipeStateModel state) {
        shouldRun = ChanceUtils.shouldProceed(chance);
        return ingredient.canProcess(level, storages);
    }

    @Override
    public void process(Level level, RecipeStorages storages, RecipeStateModel state) {
        if (!perTick && shouldRun) {
            ingredient.process(level, storages);
        }
    }

    @Override
    public void processTick(Level level, RecipeStorages storages, RecipeStateModel state) {
        if (perTick && shouldRun) {
            ingredient.process(level, storages);
        }
    }
}
