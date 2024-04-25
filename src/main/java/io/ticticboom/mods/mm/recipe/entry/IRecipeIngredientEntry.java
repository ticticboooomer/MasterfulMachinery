package io.ticticboom.mods.mm.recipe.entry;

import io.ticticboom.mods.mm.port.IPortStorage;
import net.minecraft.world.level.Level;

import java.util.List;

public interface IRecipeIngredientEntry {
    boolean canProcess(Level level, List<IPortStorage> storages);
    void process(Level level, List<IPortStorage> storages);
}
