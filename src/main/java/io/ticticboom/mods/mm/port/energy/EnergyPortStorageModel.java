package io.ticticboom.mods.mm.port.energy;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.port.IPortStorageModel;

public record EnergyPortStorageModel(
        int capacity,
        int maxReceive,
        int maxExtract
) implements IPortStorageModel {

    public static EnergyPortStorageModel parse(JsonObject json) {
        int capacity = json.get("capacity").getAsInt();
        int maxReceive = json.get("maxReceive").getAsInt();
        int maxExtract = json.get("maxExtract").getAsInt();
        return new EnergyPortStorageModel(capacity, maxReceive, maxExtract);
    }
}
