package io.ticticboom.mods.mm.util;

import com.google.gson.JsonObject;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class ParseHelper {
    public static Component parseName(JsonObject json, String type) {
        if (json.has("translation")) {
            return new TranslatableComponent(json.get("translation").getAsString());
        } else if (json.has("text")) {
            return new TextComponent(json.get("text").getAsString());
        }
        return new TextComponent("[Unnamed " + type + "]");
    }
}
