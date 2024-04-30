package io.ticticboom.mods.mm.port.energy;

import io.ticticboom.mods.mm.port.IPortIngredient;
import io.ticticboom.mods.mm.recipe.RecipeStorages;
import net.minecraft.world.level.Level;

public class EnergyPortIngredient implements IPortIngredient {

    private final int amount;

    public EnergyPortIngredient(int amount) {

        this.amount = amount;
    }

    @Override
    public boolean canProcess(Level level, RecipeStorages storages) {
        var inputStorages = storages.getInputStorages(EnergyPortStorage.class);
        int remaining = amount;
        for (EnergyPortStorage storage : inputStorages) {
            var extracted = storage.extract(remaining, true);
            remaining -= extracted;
        }
        return remaining <= 0;
    }

    @Override
    public void process(Level level, RecipeStorages storages) {
        var inputStorages = storages.getInputStorages(EnergyPortStorage.class);
        int remaining = amount;
        for (EnergyPortStorage storage : inputStorages) {
            var extracted = storage.extract(remaining, false);
            remaining -= extracted;
        }
    }

    @Override
    public boolean canOutput(Level level, RecipeStorages storages) {
        var outputStorages = storages.getOutputStorages(EnergyPortStorage.class);
        int remaining = amount;
        for (EnergyPortStorage storage : outputStorages) {
            var inserted = storage.insert(remaining, true);
            remaining -= inserted;
        }
        return remaining <= 0;
    }

    @Override
    public void output(Level level, RecipeStorages storages) {
        var outputStorages = storages.getOutputStorages(EnergyPortStorage.class);
        int remaining = amount;
        for (EnergyPortStorage storage : outputStorages) {
            var inserted = storage.insert(remaining, false);
            remaining -= inserted;
        }
    }
}
