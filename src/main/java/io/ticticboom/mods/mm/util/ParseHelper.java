package io.ticticboom.mods.mm.util;

import com.google.gson.JsonObject;
import net.minecraft.network.chat.Component;

public class ParseHelper {
    public static Component parseName(JsonObject json, String type) {
        if (json.has("translation")) {
            return Component.translatable(json.get("translation").getAsString());
        } else if (json.has("text")) {
            return Component.literal(json.get("text").getAsString());
        }
        return Component.literal("[Unnamed " + type + "]");
    }
}
