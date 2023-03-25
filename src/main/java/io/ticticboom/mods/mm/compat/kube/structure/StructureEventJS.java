package io.ticticboom.mods.mm.compat.kube.structure;

import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.server.ServerEventJS;
import io.ticticboom.mods.mm.setup.model.StructureModel;
import io.ticticboom.mods.mm.setup.reload.StructureManager;
import net.minecraft.resources.ResourceLocation;

public class StructureEventJS extends ServerEventJS {
    public void build(String id, JsonObject entry) {
        ResourceLocation key = ResourceLocation.tryParse(id);
        StructureManager.REGISTRY.put(key, StructureModel.parse(key, entry));
    }
}
