package io.ticticboom.mods.mm.port.energy;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.port.IPortStorage;
import io.ticticboom.mods.mm.port.IPortStorageFactory;
import io.ticticboom.mods.mm.port.common.INotifyChangeFunction;

public class EnergyPortStorageFactory implements IPortStorageFactory {

    private final EnergyPortStorageModel model;

    public EnergyPortStorageFactory(EnergyPortStorageModel model) {
        this.model = model;
    }

    @Override
    public IPortStorage createPortStorage(INotifyChangeFunction changed) {
        return new EnergyPortStorage(model, changed);
    }

    @Override
    public JsonObject serialize() {
        var json = new JsonObject();
        json.addProperty("capacity", model.capacity());
        json.addProperty("maxReceive", model.maxReceive());
        json.addProperty("maxExtract", model.maxExtract());
        return json;
    }
}
