package io.ticticboom.mods.mm.port.fluid;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.port.IPortIngredient;
import io.ticticboom.mods.mm.port.IPortParser;
import io.ticticboom.mods.mm.port.IPortStorageFactory;

public class FluidPortParser implements IPortParser {
    @Override
    public IPortStorageFactory parseStorage(JsonObject json) {
        var rows = json.get("rows").getAsInt();
        var columns = json.get("columns").getAsInt();
        var slotCapacity = json.get("slotCapacity").getAsInt();
        return new FluidPortStorageFactory(new FluidPortStorageModel(rows, columns, slotCapacity));
    }

    @Override
    public IPortIngredient parseRecipeIngredient(JsonObject json) {
        return null;
    }
}
