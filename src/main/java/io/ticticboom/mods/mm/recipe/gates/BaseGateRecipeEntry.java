package io.ticticboom.mods.mm.recipe.gates;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.recipe.ConfiguredRecipeEntry;
import io.ticticboom.mods.mm.recipe.IConfiguredRecipeEntry;
import io.ticticboom.mods.mm.recipe.MMRecipeEntry;
import io.ticticboom.mods.mm.setup.MMRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;

public abstract class BaseGateRecipeEntry extends MMRecipeEntry {
    @Override
    public IConfiguredRecipeEntry parse(JsonObject json) {
        var conditions = new ArrayList<ConfiguredRecipeEntry>();
        for (JsonElement elem : json.getAsJsonArray("conditions")) {
            JsonObject obj = elem.getAsJsonObject();
            var type = ResourceLocation.tryParse(obj.get("type").getAsString());
            var entry = MMRegistries.RECIPE_ENTRIES.get().getValue(type);
            conditions.add(new ConfiguredRecipeEntry(type, entry.parse(obj)));
        }
        return new GateConfiguredRecipeEntry(conditions);
    }
}
