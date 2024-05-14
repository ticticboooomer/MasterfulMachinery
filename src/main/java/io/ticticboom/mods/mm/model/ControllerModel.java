package io.ticticboom.mods.mm.model;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.util.ParserUtils;
import net.minecraft.resources.ResourceLocation;


public record ControllerModel(
        String id,
        ResourceLocation type,
        String name,
        JsonObject config
) {
    public static ControllerModel parse(JsonObject json) {
        var id = json.get("id").getAsString();
        var name = json.get("name").getAsString();
        var type = ParserUtils.parseId(json, "type");
        return new ControllerModel(id, type, name, json);
    }

    public static ControllerModel create(String id, ResourceLocation type, String name) {
        JsonObject json = paramsToJson(id, type, name);
        return new ControllerModel(id, type, name, json);
    }

    public static JsonObject paramsToJson(String id, ResourceLocation type, String name) {
        var json = new JsonObject();
        json.addProperty("id", id);
        json.addProperty("type", type.toString());
        json.addProperty("name", name);
        return json;
    }
}
