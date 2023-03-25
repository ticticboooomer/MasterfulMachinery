package io.ticticboom.mods.mm.compat.kube.recipe;

import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.server.ServerEventJS;
import io.ticticboom.mods.mm.Ref;
import io.ticticboom.mods.mm.setup.model.RecipeModel;
import io.ticticboom.mods.mm.setup.reload.RecipeManager;
import net.minecraft.resources.ResourceLocation;

public class RecipeEventJS extends ServerEventJS {
    public void build(String id, JsonObject json) {
        ResourceLocation key = ResourceLocation.tryParse(id);
        RecipeManager.REGISTRY.put(key, RecipeModel.parse(key, json));
    }
}
