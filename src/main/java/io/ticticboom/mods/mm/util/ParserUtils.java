package io.ticticboom.mods.mm.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;


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

    public static <T> T parseOrDefault(JsonObject json, String key, Supplier<T> defaultSupplier, Function<JsonObject, T> parser) {
        if (json.has(key)) {
            return parser.apply(json);
        }
        return defaultSupplier.get();
    }

    public static <T extends Enum<T>> T parseEnum(JsonObject json, String key, Class<T> cls) {
        var name = json.get(key).getAsString();
        return T.valueOf(cls, name.toUpperCase());
    }
}
