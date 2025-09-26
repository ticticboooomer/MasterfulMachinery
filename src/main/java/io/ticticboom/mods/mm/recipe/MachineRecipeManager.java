package io.ticticboom.mods.mm.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.compat.interop.MMInteropManager;
import io.ticticboom.mods.mm.recipe.condition.IRecipeCondition;
import io.ticticboom.mods.mm.recipe.condition.IRecipeConditionParser;
import io.ticticboom.mods.mm.recipe.condition.dimension.DimensionRecipeConditionParser;
import io.ticticboom.mods.mm.recipe.condition.weather.WeatherRecipeConditionParser;
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

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MachineRecipeManager extends SimpleJsonResourceReloadListener {

    public MachineRecipeManager() {
        super(Ref.GSON, "mm/processes");
        init();
    }

    public static final Map<ResourceLocation, RecipeModel> RECIPES = new HashMap<>();
    public static final Map<String, Map<ResourceLocation, RecipeModel>> RECIPES_BY_STRUCTURE = new HashMap<>();
    public static final Map<ResourceLocation, IRecipeIngredientEntryParser> ENTRY_INGREDIENT_PARSERS = new HashMap<>();
    public static final Map<ResourceLocation, IRecipeOutputEntryParser> ENTRY_OUTPUT_PARSERS = new HashMap<>();
    public static final Map<ResourceLocation, IRecipeConditionParser> CONDITION_PARSERS = new HashMap<>();

    public static void init() {
        CONDITION_PARSERS.clear();
        ENTRY_OUTPUT_PARSERS.clear();
        ENTRY_INGREDIENT_PARSERS.clear();
        ENTRY_INGREDIENT_PARSERS.put(Ref.RecipeEntries.CONSUME_INPUT, new ConsumeRecipeIngredientEntryParser());
        ENTRY_OUTPUT_PARSERS.put(Ref.RecipeEntries.SIMPLE_OUTPUT, new SimpleRecipeOutputEntryParser());
        CONDITION_PARSERS.put(Ref.RecipeConditions.DIMENSION, new DimensionRecipeConditionParser());
        CONDITION_PARSERS.put(Ref.RecipeConditions.WEATHER, new WeatherRecipeConditionParser());
    }

    public static IRecipeIngredientEntry parseIngredientEntry(JsonObject json) {
        var typeId = ParserUtils.parseId(json, "type");
        return ENTRY_INGREDIENT_PARSERS.get(typeId).parse(json);
    }

    public static IRecipeOutputEntry parseOutputEntry(JsonObject json) {
        var typeId = ParserUtils.parseId(json, "type");
        return ENTRY_OUTPUT_PARSERS.get(typeId).parse(json);
    }

    public static IRecipeCondition parseCondition(JsonObject json) {
        var typeId = ParserUtils.parseId(json, "type");
        return CONDITION_PARSERS.get(typeId).parse(json);
    }

    public static List<RecipeModel> getRecipeForStructureIds(List<ResourceLocation> structureIds) {
        return RECIPES.values().stream().filter(x -> structureIds.contains(x.structureId())).toList();
    }

    public static Collection<RecipeModel> getRecipesByStrucutreId(ResourceLocation id) {
        if(RECIPES_BY_STRUCTURE.containsKey(id.toString())) {
            return RECIPES_BY_STRUCTURE.get(id.toString()).values();
        }
        return List.of();
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        profilerFiller.push("MM Machine Recipe Processes");
        recieveRecipes(jsons);
        profilerFiller.pop();
    }

    public static void recieveRecipes(Map<ResourceLocation, JsonElement> jsons) {
        RECIPES.clear();
        Ref.LCTX.reset("MM Machine Recipe Processes");
        try {
            for (Map.Entry<ResourceLocation, JsonElement> entry : jsons.entrySet()) {
                Ref.LCTX.push("Loading Recipe: " + entry.getKey());
                ResourceLocation id = entry.getKey();
                RECIPES.put(id, RecipeModel.parse(entry.getValue().getAsJsonObject(), id));
                Ref.LCTX.pop();
            }
            if (MMInteropManager.KUBEJS.isPresent()) {
                Ref.LCTX.push("Loading KubeJS Recipes ");
                for (RecipeModel model : MMInteropManager.KUBEJS.get().postCreateRecipes()) {
                    Ref.LCTX.push("Loading Recipe: " + model.id());
                    RECIPES.put(model.id(), model);
                    Ref.LCTX.pop();
                }
                Ref.LCTX.pop();
            }
        } catch (Exception e) {
            Ref.LCTX.doThrow(e);
        }
        cacheRecipesByStructure();
    }

    private static void cacheRecipesByStructure() {
        RECIPES_BY_STRUCTURE.clear();
        for (RecipeModel model : RECIPES.values()) {
            RECIPES_BY_STRUCTURE.computeIfAbsent(model.structureId().toString(), x -> new HashMap<>()).put(model.id(), model);
        }
    }
}
