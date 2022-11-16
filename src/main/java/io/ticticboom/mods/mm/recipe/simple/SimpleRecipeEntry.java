package io.ticticboom.mods.mm.recipe.simple;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.ports.base.MMPortTypeEntry;
import io.ticticboom.mods.mm.ports.base.PortStorage;
import io.ticticboom.mods.mm.recipe.IConfiguredRecipeEntry;
import io.ticticboom.mods.mm.recipe.MMRecipeEntry;
import io.ticticboom.mods.mm.recipe.RecipeContext;
import io.ticticboom.mods.mm.setup.MMRegistries;
import io.ticticboom.mods.mm.util.RecipeHelper;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public class SimpleRecipeEntry extends MMRecipeEntry {
    @Override
    public IConfiguredRecipeEntry parse(JsonObject json) {
        var ingredient = RecipeHelper.parseIngredient(json.get("ingredient").getAsJsonObject());
        Optional<Float> chance = Optional.empty();
        if (json.has("chance")) {
            chance = Optional.of(json.get("chance").getAsFloat());
        }
        return new SimpleConfiguredRecipeEntry(ingredient, chance);
    }

    @Override
    public boolean checkInput(IConfiguredRecipeEntry config, RecipeContext ctx) {
        var conf = (SimpleConfiguredRecipeEntry) config;
        ResourceLocation type = conf.ingredient().type();
        MMPortTypeEntry port = MMRegistries.PORTS.get(type);
        var ingredientPortClass = port.storageClass();
        var ingredientCtx = port.createIngredientContext(conf.ingredient().config());
        for (PortStorage inputPort : ctx.inputPorts()) {
            if (inputPort.getClass() == ingredientPortClass) {
                port.calculateIngredients(conf.ingredient().config(), inputPort, ingredientCtx);
            }
        }
        return port.validateIngredientContext(conf.ingredient().config(), ingredientCtx);
    }

    @Override
    public void process(IConfiguredRecipeEntry config, RecipeContext ctx) {
    }
}
