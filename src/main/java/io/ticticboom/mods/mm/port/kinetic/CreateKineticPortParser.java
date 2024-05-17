package io.ticticboom.mods.mm.port.kinetic;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.port.IPortIngredient;
import io.ticticboom.mods.mm.port.IPortParser;
import io.ticticboom.mods.mm.port.IPortStorageFactory;

public class CreateKineticPortParser implements IPortParser {
    @Override
    public IPortStorageFactory parseStorage(JsonObject json) {
        var stress = json.get("stress").getAsFloat();
        return new CreateKineticPortStorageFactory(new CreateKineticPortStorageModel(stress));
    }

    @Override
    public IPortIngredient parseRecipeIngredient(JsonObject json) {
        var speed = json.get("speed").getAsFloat();
        return new CreateKineticPortIngredient(speed);
    }
}
