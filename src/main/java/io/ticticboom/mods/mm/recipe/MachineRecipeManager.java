package io.ticticboom.mods.mm.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.compat.interop.MMInteropManager;
import io.ticticboom.mods.mm.recipe.input.IRecipeIngredientEntry;
import io.ticticboom.mods.mm.recipe.input.IRecipeIngredientEntryParser;
import io.ticticboom.mods.mm.recipe.input.consume.ConsumeRecipeIngredientEntryParser;
import io.ticticboom.mods.mm.recipe.output.IRecipeOutputEntry;
import io.ticticboom.mods.mm.recipe.output.IRecipeOutputEntryParser;
import io.ticticboom.mods.mm.recipe.output.simple.SimpleRecipeOutputEntryParser;
import io.ticticboom.mods.mm.util.ParserUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MachineRecipeManager extends SimpleJsonResourceReloadListener {

    public MachineRecipeManager() {
        super(Ref.GSON, "mm/processes");
        init();
    }

    public static final Map<ResourceLocation, RecipeModel> RECIPES = new HashMap<>();
    public static final Map<ResourceLocation, IRecipeIngredientEntryParser> ENTRY_INGREDIENT_PARSERS = new HashMap<>();
    public static final Map<ResourceLocation, IRecipeOutputEntryParser> ENTRY_OUTPUT_PARSERS = new HashMap<>();

    private void init(){
        ENTRY_OUTPUT_PARSERS.clear();
        ENTRY_INGREDIENT_PARSERS.clear();
        ENTRY_INGREDIENT_PARSERS.put(Ref.RecipeEntries.CONSUME_INPUT, new ConsumeRecipeIngredientEntryParser());
        ENTRY_OUTPUT_PARSERS.put(Ref.RecipeEntries.SIMPLE_OUTPUT, new SimpleRecipeOutputEntryParser());
    }

    public static IRecipeIngredientEntry parseIngredientEntry(JsonObject json) {
        var typeId = ParserUtils.parseId(json, "type");
        return ENTRY_INGREDIENT_PARSERS.get(typeId).parse(json);
    }

    public static IRecipeOutputEntry parseOutputEntry(JsonObject json) {
        var typeId = ParserUtils.parseId(json, "type");
        return ENTRY_OUTPUT_PARSERS.get(typeId).parse(json);
    }

    public static List<RecipeModel> getRecipeForStructureIds(List<ResourceLocation> structureIds) {
        return RECIPES.values().stream().filter(x -> structureIds.contains(x.structureId())).toList();
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        profilerFiller.push("MM Machine Recipe Processes");
        RECIPES.clear();
        for (Map.Entry<ResourceLocation, JsonElement> entry : jsons.entrySet()) {
            ResourceLocation id = entry.getKey();
            RECIPES.put(id, RecipeModel.parse(entry.getValue().getAsJsonObject(), id));
        }
        if (MMInteropManager.KUBEJS.isPresent()) {
            for (RecipeModel model : MMInteropManager.KUBEJS.get().postCreateRecipes()) {
                RECIPES.put(model.id(), model);
            }
        }
        profilerFiller.pop();
    }
}
