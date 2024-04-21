package io.ticticboom.mods.mm.model.config;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.util.ParserUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;


public record ControllerModel(
        String id,
        ResourceLocation type,
        Component name
) {
    public static ControllerModel parse(JsonObject json) {
        var id = json.get("id").getAsString();
        var type = ParserUtils.parseId(json, "type");
        var name = ParserUtils.parseComponent(json, "name");
        return new ControllerModel(id, type, name);
    }
}
