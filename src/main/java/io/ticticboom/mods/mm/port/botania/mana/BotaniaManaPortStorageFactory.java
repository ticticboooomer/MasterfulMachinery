package io.ticticboom.mods.mm.port.botania.mana;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.port.IPortStorage;
import io.ticticboom.mods.mm.port.IPortStorageFactory;
import io.ticticboom.mods.mm.port.IPortStorageModel;
import io.ticticboom.mods.mm.port.common.INotifyChangeFunction;

public class BotaniaManaPortStorageFactory implements IPortStorageFactory {

    private final BotaniaManaPortStorageModel model;

    public BotaniaManaPortStorageFactory(BotaniaManaPortStorageModel model) {
        this.model = model;
    }

    @Override
    public IPortStorage createPortStorage(INotifyChangeFunction changed) {
        return new BotaniaManaPortStorage(model, changed);
    }

    @Override
    public JsonObject serialize() {
        var o = new JsonObject();
        o.addProperty("capacity", model.capacity());
        return o;
    }

    @Override
    public IPortStorageModel getModel() {
        return model;
    }
}
