package io.ticticboom.mods.mm.recipe.gates.and;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.recipe.ConfiguredRecipeEntry;
import io.ticticboom.mods.mm.recipe.IConfiguredRecipeEntry;
import io.ticticboom.mods.mm.recipe.MMRecipeEntry;
import io.ticticboom.mods.mm.recipe.RecipeContext;
import io.ticticboom.mods.mm.recipe.gates.BaseGateRecipeEntry;
import io.ticticboom.mods.mm.recipe.gates.GateConfiguredRecipeEntry;
import io.ticticboom.mods.mm.setup.MMRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;

public class AndGateRecipeEntry extends BaseGateRecipeEntry {

    @Override
    public boolean processInputs(IConfiguredRecipeEntry config, RecipeContext original, RecipeContext ctx) {
        var conf = (GateConfiguredRecipeEntry) config;
        for (ConfiguredRecipeEntry condition : conf.conditions()) {
            MMRecipeEntry entry = MMRegistries.RECIPE_ENTRIES.get().getValue(condition.type());
            if (!entry.processInputs(condition.entry(), original, ctx)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean processOutputs(IConfiguredRecipeEntry config, RecipeContext original, RecipeContext ctx) {
        var conf = (GateConfiguredRecipeEntry) config;
        for (ConfiguredRecipeEntry condition : conf.conditions()) {
            MMRecipeEntry entry = MMRegistries.RECIPE_ENTRIES.get().getValue(condition.type());
            if (!entry.processOutputs(condition.entry(), original, ctx)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int getNewTickLimit(IConfiguredRecipeEntry config, RecipeContext original, RecipeContext ctx, int currentLimit) {
        var conf = (GateConfiguredRecipeEntry) config;
        int duration = currentLimit;
        for (ConfiguredRecipeEntry condition : conf.conditions()) {
            MMRecipeEntry entry = MMRegistries.RECIPE_ENTRIES.get().getValue(condition.type());
            int newTickLimit = entry.getNewTickLimit(condition.entry(), original, ctx, duration);
            if (newTickLimit == duration) {
                return currentLimit;
            }
        }
        return duration;
    }
}
