package io.ticticboom.mods.mm.port.fluid;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.port.IPortStorage;
import io.ticticboom.mods.mm.port.IPortStorageFactory;
import io.ticticboom.mods.mm.port.common.INotifyChangeFunction;

public class FluidPortStorageFactory implements IPortStorageFactory {

    private final FluidPortStorageModel model;

    public FluidPortStorageFactory(FluidPortStorageModel model) {

        this.model = model;
    }

    @Override
    public IPortStorage createPortStorage(INotifyChangeFunction changed) {
        return new FluidPortStorage(model, changed);
    }

    @Override
    public JsonObject serialize() {
        JsonObject json = new JsonObject();
        json.addProperty("rows", model.rows());
        json.addProperty("columns", model.columns());
        json.addProperty("slotCapacity", model.slotCapacity());
        return json;
    }
}
