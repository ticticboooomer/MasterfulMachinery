package io.ticticboom.mods.mm.recipe.designated;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.ports.base.IOPortStorage;
import io.ticticboom.mods.mm.recipe.ConfiguredRecipeEntry;
import io.ticticboom.mods.mm.recipe.IConfiguredRecipeEntry;
import io.ticticboom.mods.mm.recipe.MMRecipeEntry;
import io.ticticboom.mods.mm.recipe.RecipeContext;
import io.ticticboom.mods.mm.setup.MMRegistries;
import net.minecraft.resources.ResourceLocation;

public class DesignatedRecipeEntry extends MMRecipeEntry {
    @Override
    public IConfiguredRecipeEntry parse(JsonObject json) {
        var portId = json.get("portId").getAsString();
        JsonObject obj = json.getAsJsonObject("entry");
        var type = ResourceLocation.tryParse(obj.get("type").getAsString());
        var entry = MMRegistries.RECIPE_ENTRIES.get().getValue(type);
        return new DesignatedConfiguredRecipeEntry(new ConfiguredRecipeEntry(type, entry.parse(obj)), ResourceLocation.tryParse(portId));
    }

    @Override
    public boolean processInputs(IConfiguredRecipeEntry config, RecipeContext original, RecipeContext ctx) {
        var conf = (DesignatedConfiguredRecipeEntry) config;
        for (IOPortStorage inputPort : ctx.inputPorts()) {
            if (inputPort.name().equals(conf.portId().toString())) {
                var value = MMRegistries.RECIPE_ENTRIES.get().getValue(conf.entry().type());
                var newOriginal = new RecipeContext(original.structure(), original.recipe(), original.appliedTransformId(), ImmutableList.of(inputPort), original.outputPorts(), original.level(), original.controllerPos(), original.contexts());
                var newCtx = new RecipeContext(ctx.structure(), ctx.recipe(), ctx.appliedTransformId(), ImmutableList.of(inputPort), ctx.outputPorts(), ctx.level(), ctx.controllerPos(), ctx.contexts());
                return value.processInputs(conf.entry().entry(), newOriginal, newCtx);
            }
        }
        return false;
    }

    @Override
    public boolean processOutputs(IConfiguredRecipeEntry config, RecipeContext original, RecipeContext ctx) {
        var conf = (DesignatedConfiguredRecipeEntry) config;
        for (IOPortStorage inputPort : ctx.inputPorts()) {
            if (inputPort.name().equals(conf.portId().toString())) {
                var value = MMRegistries.RECIPE_ENTRIES.get().getValue(conf.entry().type());
                var newOriginal = new RecipeContext(original.structure(), original.recipe(), original.appliedTransformId(), ImmutableList.of(inputPort), original.outputPorts(), original.level(), original.controllerPos(), original.contexts());
                var newCtx = new RecipeContext(ctx.structure(), ctx.recipe(), ctx.appliedTransformId(), ImmutableList.of(inputPort), ctx.outputPorts(), ctx.level(), ctx.controllerPos(), ctx.contexts());
                return value.processOutputs(conf.entry().entry(), newOriginal, newCtx);
            }
        }
        return false;
    }
}
