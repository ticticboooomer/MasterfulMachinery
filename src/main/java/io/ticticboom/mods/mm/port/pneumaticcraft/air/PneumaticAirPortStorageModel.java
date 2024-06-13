package io.ticticboom.mods.mm.port.pneumaticcraft.air;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.port.IPortStorageModel;

public record PneumaticAirPortStorageModel (
        int volume
) implements IPortStorageModel {
    public static PneumaticAirPortStorageModel parse(JsonObject json) {
        int volume = json.get("volume").getAsInt();
        return new PneumaticAirPortStorageModel(volume);
    }
}
