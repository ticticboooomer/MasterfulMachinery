package io.ticticboom.mods.mm.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import io.ticticboom.mods.mm.util.ParserUtils;
import lombok.Getter;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class IdList {
    @Getter
    private final List<ResourceLocation> ids;

    public IdList(List<ResourceLocation> ids) {

        this.ids = ids;
    }

    public boolean contains(ResourceLocation id) {
        return ids.contains(id);
    }

    public static IdList parse(JsonElement json) {
        List<ResourceLocation> ids = new ArrayList<>();
        if (json.isJsonArray()) {
            JsonArray elems = json.getAsJsonArray();
            for (var elem : elems) {
                ids.add(ParserUtils.parseId(elem));
            }
        } else {
            ids.add(ParserUtils.parseId(json));
        }
        return new IdList(ids);
    }

    public JsonArray serialize() {
        var ids = new JsonArray();
        for (ResourceLocation id : this.ids) {
            ids.add(id.toString());
        }
        return ids;
    }
}
