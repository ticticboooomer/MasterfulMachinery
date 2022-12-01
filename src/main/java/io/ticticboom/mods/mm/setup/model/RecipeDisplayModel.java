package io.ticticboom.mods.mm.setup.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.compat.MMCompatRegistries;
import io.ticticboom.mods.mm.recipedisplay.ConfiguredRecipeDisplayElement;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public record RecipeDisplayModel(
        ResourceLocation recipeId,
        List<ConfiguredRecipeDisplayElement> elements
) {
    public static RecipeDisplayModel parse(ResourceLocation id, JsonObject json) {
        var elements = new ArrayList<ConfiguredRecipeDisplayElement>();
        var elems = json.getAsJsonArray("elements");
        for (JsonElement elem : elems) {
            var object = elem.getAsJsonObject();
            ResourceLocation typeId = ResourceLocation.tryParse(object.get("type").getAsString());
            var type = MMCompatRegistries.RECIPE_DISPLAY_ELEMENTS.get().getValue(typeId);
            elements.add(new ConfiguredRecipeDisplayElement(typeId, type.parse(object)));
        }
        return new RecipeDisplayModel(id, elements);
    }
}
