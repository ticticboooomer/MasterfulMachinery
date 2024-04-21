package io.ticticboom.mods.mm.ports.item;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.ports.IPortParser;
import io.ticticboom.mods.mm.ports.IPortStorageFactory;

public class ItemPortParser implements IPortParser {
    @Override
    public IPortStorageFactory parseStorage(JsonObject json) {
        int rows = json.get("rows").getAsInt();
        int columns = json.get("columns").getAsInt();
        return new ItemPortStorageFactory(new ItemPortStorageModel(rows, columns));
    }
}
