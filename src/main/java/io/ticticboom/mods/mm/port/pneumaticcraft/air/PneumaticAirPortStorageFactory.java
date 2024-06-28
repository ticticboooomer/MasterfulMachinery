package io.ticticboom.mods.mm.port.pneumaticcraft.air;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.port.IPortStorage;
import io.ticticboom.mods.mm.port.IPortStorageFactory;
import io.ticticboom.mods.mm.port.IPortStorageModel;
import io.ticticboom.mods.mm.port.common.INotifyChangeFunction;

public class PneumaticAirPortStorageFactory implements IPortStorageFactory {
    private final PneumaticAirPortStorageModel model;
    public PneumaticAirPortStorageFactory(PneumaticAirPortStorageModel model) {
        this.model = model;
    }

    @Override
    public IPortStorage createPortStorage(INotifyChangeFunction changed) {
        return new PneumaticAirPortStorage(model, changed);
    }

    @Override
    public JsonObject serialize() {
        var json = new JsonObject();
        json.addProperty("valume", model.volume());
        return json;
    }

    @Override
    public IPortStorageModel getModel() {
        return model;
    }
}
