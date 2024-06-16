package io.ticticboom.mods.mm.port.pneumaticcraft.air;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.port.IPortIngredient;
import io.ticticboom.mods.mm.port.IPortParser;
import io.ticticboom.mods.mm.port.IPortStorageFactory;

public class PneumaticAirPortParser implements IPortParser {
    @Override
    public IPortStorageFactory parseStorage(JsonObject json) {
        return new PneumaticAirPortStorageFactory(PneumaticAirPortStorageModel.parse(json));
    }

    @Override
    public IPortIngredient parseRecipeIngredient(JsonObject json) {
        float bar = 0;
        if (json.has("pressure")) {
            bar = json.get("pressure").getAsFloat();
        }
        var air = json.get("air").getAsInt();
        return new PneumaticAirPortIngredient(bar, air);
    }
}
