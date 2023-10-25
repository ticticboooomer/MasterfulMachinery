package io.ticticboom.mods.mm.compat.kube.recipe;

import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.event.EventJS;
import io.ticticboom.mods.mm.setup.model.RecipeModel;
import io.ticticboom.mods.mm.setup.reload.RecipeManager;
import net.minecraft.resources.ResourceLocation;

public class RecipeEventHandler extends EventJS {
    public void build(String id, JsonObject json) {
        ResourceLocation key = ResourceLocation.tryParse(id);
        RecipeManager.REGISTRY.put(key, RecipeModel.parse(key, json));
    }
}
