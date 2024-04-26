package io.ticticboom.mods.mm.recipe;

import io.ticticboom.mods.mm.port.IPortStorage;

import java.util.ArrayList;
import java.util.List;

public record RecipeStorages(
        List<IPortStorage> inputStorages,
        List<IPortStorage> outputStorages
) {
    public <T extends IPortStorage> List<T> getInputStorages(Class<T> clz) {
        return getStorages(clz, inputStorages);
    }

    public <T extends IPortStorage> List<T> getOutputStorages(Class<T> clz) {
        return getStorages(clz, outputStorages);
    }

    private <T extends IPortStorage> List<T> getStorages(Class<T> clz, List<IPortStorage> storages) {
        var result = new ArrayList<T>();
        for (IPortStorage storage : storages) {
            if (clz.isAssignableFrom(storage.getClass())) {
                result.add((T) storage);
            }
        }
        return result;
    }
}
