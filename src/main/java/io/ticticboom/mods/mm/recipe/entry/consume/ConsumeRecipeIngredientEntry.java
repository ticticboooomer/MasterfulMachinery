package io.ticticboom.mods.mm.recipe.entry.consume;

import io.ticticboom.mods.mm.port.IPortIngredient;
import io.ticticboom.mods.mm.port.IPortStorage;
import io.ticticboom.mods.mm.recipe.entry.IRecipeIngredientEntry;
import net.minecraft.world.level.Level;

import java.util.List;

public class ConsumeRecipeIngredientEntry implements IRecipeIngredientEntry {

    private final IPortIngredient ingredient;

    public ConsumeRecipeIngredientEntry(IPortIngredient ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public boolean canProcess(Level level, List<IPortStorage> storages) {
        return ingredient.canProcess(level, storages);
    }

    @Override
    public void process(Level level, List<IPortStorage> storages) {
        ingredient.process(level, storages);
    }
}
