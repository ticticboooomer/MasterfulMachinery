package io.ticticboom.mods.mm.setup.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.recipe.IConfiguredRecipeEntry;
import io.ticticboom.mods.mm.recipe.MMRecipeEntry;
import io.ticticboom.mods.mm.setup.MMRegistries;
import io.ticticboom.mods.mm.util.ParseHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public record RecipeModel(
        ResourceLocation id,
        Component name,
        ResourceLocation structureId,
        int duration,
        List<RecipeEntry> inputs,
        List<RecipeEntry> outputs
) {
    public static RecipeModel parse(ResourceLocation id, JsonObject json) {
        var structureId = ResourceLocation.tryParse(json.get("structureId").getAsString());
        var name = ParseHelper.parseName(json.getAsJsonObject("name"), "");
        var duration = json.get("duration").getAsInt();
        var inputs = parseEntries(json.getAsJsonArray("inputs"));
        var outputs = parseEntries(json.getAsJsonArray("outputs"));
        return new RecipeModel(id, name, structureId, duration, inputs, outputs);
    }

    public static List<RecipeEntry> parseEntries(JsonArray arr) {
        var result = new ArrayList<RecipeEntry>();
        for (JsonElement elem : arr) {
            var json = elem.getAsJsonObject();
            var type = ResourceLocation.tryParse(json.get("type").getAsString());
            MMRecipeEntry entry = MMRegistries.RECIPE_ENTRIES.get().getValue(type);
            result.add(new RecipeEntry(type, entry.parse(json)));
        }
        return result;
    }

    public record RecipeEntry(
        ResourceLocation type,
        IConfiguredRecipeEntry config
    ) {
    }
}
