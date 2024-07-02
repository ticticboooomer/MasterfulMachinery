package io.ticticboom.mods.mm.port.botania.mana;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.port.IPortIngredient;
import io.ticticboom.mods.mm.port.IPortParser;
import io.ticticboom.mods.mm.port.IPortStorageFactory;

public class BotaniaManaPortParser implements IPortParser {
    @Override
    public IPortStorageFactory parseStorage(JsonObject json) {
        var capacity = json.get("capacity").getAsInt();
        return new BotaniaManaPortStorageFactory(new BotaniaManaPortStorageModel(capacity));
    }

    @Override
    public IPortIngredient parseRecipeIngredient(JsonObject json) {
        var mana = json.get("mana").getAsInt();
        return new BotaniaManaPortIngredient(mana);
    }
}
