package io.ticticboom.mods.mm.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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

    public JsonObject debug() {
        var inputsJson = new JsonArray();
        var outputsJson = new JsonArray();
        for (IPortStorage inputStorage : inputStorages) {
            inputsJson.add(inputStorage.debugDump());
        }
        for (IPortStorage outputStorage : outputStorages) {
            outputsJson.add(outputStorage.debugDump());
        }
        var json = new JsonObject();
        json.add("inputs", inputsJson);
        json.add("outputs", outputsJson);
        return json;
    }
}
