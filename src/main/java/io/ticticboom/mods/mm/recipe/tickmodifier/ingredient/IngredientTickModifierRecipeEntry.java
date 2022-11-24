package io.ticticboom.mods.mm.recipe.tickmodifier.ingredient;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.ports.base.MMPortTypeEntry;
import io.ticticboom.mods.mm.recipe.IConfiguredRecipeEntry;
import io.ticticboom.mods.mm.recipe.MMRecipeEntry;
import io.ticticboom.mods.mm.recipe.RecipeContext;
import io.ticticboom.mods.mm.setup.MMRegistries;
import io.ticticboom.mods.mm.util.RecipeHelper;
import net.minecraft.resources.ResourceLocation;

public class IngredientTickModifierRecipeEntry extends MMRecipeEntry {
    @Override
    public IConfiguredRecipeEntry parse(JsonObject json) {
        var duration = json.get("newDuration").getAsInt();
        var ingredient = RecipeHelper.parseIngredient(json.getAsJsonObject("ingredient"));
        return new IngredientTickModifierConfiguredRecipeEntry(ingredient, duration);
    }

    @Override
    public boolean processInputs(IConfiguredRecipeEntry config, RecipeContext original, RecipeContext ctx) {
        return true;
    }

    @Override
    public boolean processOutputs(IConfiguredRecipeEntry config, RecipeContext original, RecipeContext ctx) {
        return true;
    }

    @Override
    public int getNewTickLimit(IConfiguredRecipeEntry config, RecipeContext original, RecipeContext ctx, int currentLimit) {
        var conf = ((IngredientTickModifierConfiguredRecipeEntry) config);
        ResourceLocation type = conf.ingredient().type();
        MMPortTypeEntry port = MMRegistries.PORTS.get(type);
        if (port.processInputs(conf.ingredient().config(), ctx.inputPorts())) {
            return conf.duration();
        }
        return currentLimit;
    }
}
