package io.ticticboom.mods.mm.recipe;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.recipe.entry.IRecipeIngredientEntry;
import io.ticticboom.mods.mm.recipe.entry.IRecipeIngredientEntryParser;
import io.ticticboom.mods.mm.recipe.entry.consume.ConsumeRecipeIngredientEntryParser;
import io.ticticboom.mods.mm.util.ParserUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.Recipe;

import java.util.HashMap;
import java.util.Map;

public class MachineRecipeManager extends SimpleJsonResourceReloadListener {

    public MachineRecipeManager() {
        super(Ref.GSON, "mm/processes");
        ENTRY_PARSERS.put(Ref.RecipeEntries.CONSUME, new ConsumeRecipeIngredientEntryParser());
    }

    public static final Map<ResourceLocation, RecipeModel> RECIPES = new HashMap<>();
    public static final Map<ResourceLocation, IRecipeIngredientEntryParser> ENTRY_PARSERS = new HashMap<>();

    public static IRecipeIngredientEntry parseEntry(JsonObject json) {
        var typeId = ParserUtils.parseId(json, "type");
        return ENTRY_PARSERS.get(typeId).parse(json);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        for (Map.Entry<ResourceLocation, JsonElement> entry : jsons.entrySet()) {
            ResourceLocation id = entry.getKey();
            RECIPES.put(id, RecipeModel.parse(entry.getValue().getAsJsonObject(), id));
        }
    }

    public static RecipeModel getRecipe(ResourceLocation id) {

    }
}
