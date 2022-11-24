package io.ticticboom.mods.mm.recipe.gates.or;

import io.ticticboom.mods.mm.recipe.ConfiguredRecipeEntry;
import io.ticticboom.mods.mm.recipe.IConfiguredRecipeEntry;
import io.ticticboom.mods.mm.recipe.MMRecipeEntry;
import io.ticticboom.mods.mm.recipe.RecipeContext;
import io.ticticboom.mods.mm.recipe.gates.BaseGateRecipeEntry;
import io.ticticboom.mods.mm.recipe.gates.GateConfiguredRecipeEntry;
import io.ticticboom.mods.mm.setup.MMRegistries;

public class OrGateRecipeEntry extends BaseGateRecipeEntry {

    @Override
    public boolean processInputs(IConfiguredRecipeEntry config, RecipeContext original, RecipeContext ctx) {
        var conf = (GateConfiguredRecipeEntry) config;
        for (ConfiguredRecipeEntry condition : conf.conditions()) {
            MMRecipeEntry entry = MMRegistries.RECIPE_ENTRIES.get().getValue(condition.type());
            if (entry.processInputs(condition.entry(), original, ctx.clonePorts())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean processOutputs(IConfiguredRecipeEntry config, RecipeContext original, RecipeContext ctx) {
        var conf = (GateConfiguredRecipeEntry) config;
        for (ConfiguredRecipeEntry condition : conf.conditions()) {
            MMRecipeEntry entry = MMRegistries.RECIPE_ENTRIES.get().getValue(condition.type());
            if (entry.processOutputs(condition.entry(), original, ctx.clonePorts())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getNewTickLimit(IConfiguredRecipeEntry config, RecipeContext original, RecipeContext ctx, int currentLimit) {
        var conf = (GateConfiguredRecipeEntry) config;
        for (ConfiguredRecipeEntry condition : conf.conditions()) {
            MMRecipeEntry entry = MMRegistries.RECIPE_ENTRIES.get().getValue(condition.type());
            int newTickLimit = entry.getNewTickLimit(condition.entry(), original, ctx.clonePorts(), currentLimit);
            if (newTickLimit != currentLimit) {
                return newTickLimit;
            }
        }
        return currentLimit;
    }
}
