package io.ticticboom.mods.mm.piece.modifier.blockstate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public record StructureBlockstateProperty(String key, JsonElement value) {

    public static StructureBlockstateProperty parse(JsonObject json) {
        var key = json.get("property").getAsString();
        var value = json.get("value");
        return new StructureBlockstateProperty(key, value);
    }
}
