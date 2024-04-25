package io.ticticboom.mods.mm.recipe;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.util.ParserUtils;
import net.minecraft.resources.ResourceLocation;

public record RecipeModel(
        ResourceLocation id,
        ResourceLocation structureId,
        RecipeInputs inputs
) {
    public static RecipeModel parse(JsonObject json, ResourceLocation id) {
        var structrueId = ParserUtils.parseId(json, "structureId");
        var inputs = RecipeInputs.parse(json.getAsJsonArray("inputs"));
        return new RecipeModel(id, structrueId, inputs);
    }
}
