package io.ticticboom.mods.mm.recipe.simple;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.ports.base.IOPortStorage;
import io.ticticboom.mods.mm.ports.base.MMPortTypeEntry;
import io.ticticboom.mods.mm.recipe.IConfiguredRecipeEntry;
import io.ticticboom.mods.mm.recipe.MMRecipeEntry;
import io.ticticboom.mods.mm.recipe.RecipeContext;
import io.ticticboom.mods.mm.setup.MMRegistries;
import io.ticticboom.mods.mm.util.RecipeHelper;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;
import java.util.Random;

public class SimpleRecipeEntry extends MMRecipeEntry {
    private final Random rand = new Random();

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
    public boolean processInputs(IConfiguredRecipeEntry config, RecipeContext original, RecipeContext ctx) {
        var conf = (SimpleConfiguredRecipeEntry) config;
        if (!roll(conf.chance())) {
            return true;
        }
        ResourceLocation type = conf.ingredient().type();
        MMPortTypeEntry port = MMRegistries.PORTS.get(type);
        return port.processInputs(conf.ingredient().config(), ctx.inputPorts().stream().map(IOPortStorage::port).toList());
    }

    @Override
    public boolean processOutputs(IConfiguredRecipeEntry config, RecipeContext original, RecipeContext ctx) {
        var conf = (SimpleConfiguredRecipeEntry) config;
        if (!roll(conf.chance())) {
            return true;
        }
        ResourceLocation type = conf.ingredient().type();
        MMPortTypeEntry port = MMRegistries.PORTS.get(type);
        return port.processOutputs(conf.ingredient().config(), ctx.outputPorts().stream().map(IOPortStorage::port).toList());
    }
    protected boolean roll(Optional<Float> chance) {
        if (chance.isPresent()) {
            var random = rand.nextFloat();
            return random <= chance.get();
        }
        return true;
    }
}
