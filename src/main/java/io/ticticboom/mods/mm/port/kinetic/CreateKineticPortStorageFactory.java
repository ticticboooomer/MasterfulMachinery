package io.ticticboom.mods.mm.port.kinetic;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.port.IPortStorage;
import io.ticticboom.mods.mm.port.IPortStorageFactory;
import io.ticticboom.mods.mm.port.common.INotifyChangeFunction;

public class CreateKineticPortStorageFactory implements IPortStorageFactory {

    private final CreateKineticPortStorageModel model;

    public CreateKineticPortStorageFactory(CreateKineticPortStorageModel model) {

        this.model = model;
    }

    @Override
    public IPortStorage createPortStorage(INotifyChangeFunction changed) {
        return new CreateKineticPortStorage(model, changed);
    }

    @Override
    public JsonObject serialize() {
        var json = new JsonObject();
        json.addProperty("stress", model.stress());
        return json;
    }
}
