package io.ticticboom.mods.mm.compat.kjs.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.recipe.RecipeModel;
import lombok.Getter;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class RecipeBuilderJS {
    private final List<JsonObject> inputs = new ArrayList<>();
    private final List<JsonObject> outputs = new ArrayList<>();
    private int ticks;
    private ResourceLocation structureId;
    @Getter
    private final ResourceLocation id;

    public RecipeBuilderJS(String id) {
        this.id = new ResourceLocation(id);
    }

    public RecipeBuilderJS input(JsonObject entry) {
        inputs.add(entry);
        return this;
    }

    public RecipeBuilderJS output(JsonObject entry) {
        outputs.add(entry);
        return this;
    }

    public RecipeBuilderJS ticks(int ticks) {
        this.ticks = ticks;
        return this;
    }

    public RecipeBuilderJS structureId(ResourceLocation id) {
        this.structureId = id;
        return this;
    }

    public RecipeModel build() {
        JsonObject json = new JsonObject();
        json.addProperty("id", id.toString());
        json.addProperty("ticks", ticks);
        json.addProperty("structureId", structureId.toString());
        var inputArr = new JsonArray();
        for (JsonObject input : inputs) {
            inputArr.add(input);
        }
        var outputArr = new JsonArray();
        for (JsonObject output : outputs) {
            outputArr.add(output);
        }
        json.add("inputs", inputArr);
        json.add("outputs", outputArr);
        return RecipeModel.parse(json, id);
    }
}
