package io.ticticboom.mods.mm.extra;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.util.ParserUtils;
import net.minecraft.resources.ResourceLocation;

public record ExtraBlockModel(
        String id,
        String name,
        ResourceLocation type
) {
    public static ExtraBlockModel parse(JsonObject json) {
        String id = json.get("id").getAsString();
        String name = json.get("name").getAsString();
        ResourceLocation type = ParserUtils.parseId(json, "type");
        return new ExtraBlockModel(id, name, type);
    }
}
