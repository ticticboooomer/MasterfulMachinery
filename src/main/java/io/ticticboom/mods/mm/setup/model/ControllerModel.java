package io.ticticboom.mods.mm.setup.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.util.ParseHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

public record ControllerModel(
        ResourceLocation id,
        Component name,
        ResourceLocation blockId
) {
    public static ControllerModel parse(JsonObject json) {
        var id = new ResourceLocation(Ref.ID, json.get("id").getAsString());
        var name = ParseHelper.parseName(json.get("name").getAsJsonObject(), "Controller");
        var blockId = ResourceLocation.tryParse(id + "_controller");
        return new ControllerModel(id, name, blockId);
    }

}
