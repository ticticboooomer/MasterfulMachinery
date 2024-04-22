package io.ticticboom.mods.mm.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;


public class ParserUtils {

    public static ResourceLocation parseId(JsonElement json) {
        return new ResourceLocation(json.getAsString());
    }

    public static ResourceLocation parseId(JsonObject json, String key) {
        return parseId(json.get(key));
    }

    public static Component parseComponent(JsonElement json) {
        if (json.isJsonPrimitive() && json.getAsJsonPrimitive().isString()) {
            return Component.literal(json.getAsString());
        } else if (json.isJsonObject() && json.getAsJsonObject().has("translation")) {
            return Component.translatable(json.getAsJsonObject().get("translation").getAsString());
        }
        throw new RuntimeException("Failed to parse text component as literal or translatable, Refer to MM documentation for assistance");
    }

    public static Component parseComponent(JsonObject json, String key) {
        return parseComponent(json.get(key));
    }
}
