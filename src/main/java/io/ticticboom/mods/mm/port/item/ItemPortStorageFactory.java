package io.ticticboom.mods.mm.port.item;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.port.IPortStorage;
import io.ticticboom.mods.mm.port.IPortStorageFactory;
import io.ticticboom.mods.mm.port.IPortStorageModel;
import io.ticticboom.mods.mm.port.common.INotifyChangeFunction;

public class ItemPortStorageFactory implements IPortStorageFactory {

    private final ItemPortStorageModel model;

    public ItemPortStorageFactory(ItemPortStorageModel model) {
        this.model = model;
    }

    @Override
    public IPortStorage createPortStorage(INotifyChangeFunction changed) {
        return new ItemPortStorage(model, changed);
    }

    @Override
    public JsonObject serialize() {
        var json = new JsonObject();
        json.addProperty("rows", model.rows());
        json.addProperty("columns", model.columns());
        return json;
    }

    @Override
    public IPortStorageModel getModel() {
        return model;
    }
}
