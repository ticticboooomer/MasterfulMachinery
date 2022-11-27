package io.ticticboom.mods.mm.recipe.preset;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.recipe.IConfiguredRecipeEntry;
import io.ticticboom.mods.mm.recipe.MMRecipeEntry;
import io.ticticboom.mods.mm.recipe.RecipeContext;
import io.ticticboom.mods.mm.setup.MMRegistries;
import io.ticticboom.mods.mm.setup.reload.PresetManager;
import net.minecraft.resources.ResourceLocation;

public class PresetRecipeEntry extends MMRecipeEntry {

    @Override
    public IConfiguredRecipeEntry parse(JsonObject json) {
        var preset = ResourceLocation.tryParse(json.get("preset").getAsString());
        return new PresetConfiguredRecipeEntry(preset);
    }

    @Override
    public boolean processInputs(IConfiguredRecipeEntry config, RecipeContext original, RecipeContext ctx) {
        var preset = PresetManager.REGISTRY.get(((PresetConfiguredRecipeEntry) config).preset());
        var type = MMRegistries.RECIPE_ENTRIES.get().getValue(preset.type());
        return type.processInputs(preset.entry(), original, ctx);
    }

    @Override
    public boolean processOutputs(IConfiguredRecipeEntry config, RecipeContext original, RecipeContext ctx) {
        var preset = PresetManager.REGISTRY.get(((PresetConfiguredRecipeEntry) config).preset());
        var type = MMRegistries.RECIPE_ENTRIES.get().getValue(preset.type());
        return type.processOutputs(preset.entry(), original, ctx);
    }

    @Override
    public int getNewTickLimit(IConfiguredRecipeEntry config, RecipeContext original, RecipeContext ctx, int currentLimit) {
        var preset = PresetManager.REGISTRY.get(((PresetConfiguredRecipeEntry) config).preset());
        var type = MMRegistries.RECIPE_ENTRIES.get().getValue(preset.type());
        return type.getNewTickLimit(preset.entry(), original, ctx, currentLimit);
    }
}
