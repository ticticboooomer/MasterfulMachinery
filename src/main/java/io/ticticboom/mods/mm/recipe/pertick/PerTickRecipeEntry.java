package io.ticticboom.mods.mm.recipe.pertick;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.ports.base.MMPortTypeEntry;
import io.ticticboom.mods.mm.recipe.IConfiguredRecipeEntry;
import io.ticticboom.mods.mm.recipe.MMRecipeEntry;
import io.ticticboom.mods.mm.recipe.RecipeContext;
import io.ticticboom.mods.mm.setup.MMRegistries;
import io.ticticboom.mods.mm.util.RecipeHelper;
import net.minecraft.resources.ResourceLocation;

public class PerTickRecipeEntry extends MMRecipeEntry {
    @Override
    public IConfiguredRecipeEntry parse(JsonObject json) {
        var ingredient = RecipeHelper.parseIngredient(json.get("ingredient").getAsJsonObject());
        return new PerTickConfiguredRecipeEntry(ingredient);
    }

    @Override
    public boolean processInputs(IConfiguredRecipeEntry config, RecipeContext ctx) {
        PerTickConfiguredRecipeEntry conf = (PerTickConfiguredRecipeEntry) config;
        ResourceLocation type = conf.ingredient().type();
        MMPortTypeEntry port = MMRegistries.PORTS.get(type);
        return port.processInputs(conf.ingredient().config(), ctx.inputPorts());
    }

    @Override
    public boolean processOutputs(IConfiguredRecipeEntry config, RecipeContext ctx) {
        PerTickConfiguredRecipeEntry conf = (PerTickConfiguredRecipeEntry) config;
        ResourceLocation type = conf.ingredient().type();
        MMPortTypeEntry port = MMRegistries.PORTS.get(type);
        return port.processOutputs(conf.ingredient().config(), ctx.outputPorts());
    }

    @Override
    public boolean shouldBypassCloned(IConfiguredRecipeEntry config, RecipeContext ctx) {
        return true;
    }
}
