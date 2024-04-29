package io.ticticboom.mods.mm.port.energy;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.port.IPortIngredient;
import io.ticticboom.mods.mm.port.IPortParser;
import io.ticticboom.mods.mm.port.IPortStorageFactory;

public class EnergyPortParser implements IPortParser {
    @Override
    public IPortStorageFactory parseStorage(JsonObject json) {
        return new EnergyPortStorageFactory(EnergyPortStorageModel.parse(json));
    }

    @Override
    public IPortIngredient parseRecipeIngredient(JsonObject json) {
        return null;
    }
}
