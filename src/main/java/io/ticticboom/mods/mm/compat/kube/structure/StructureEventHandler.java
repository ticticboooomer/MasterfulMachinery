package io.ticticboom.mods.mm.compat.kube.structure;

import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.event.EventJS;
import io.ticticboom.mods.mm.setup.model.StructureModel;
import io.ticticboom.mods.mm.setup.reload.StructureManager;
import net.minecraft.resources.ResourceLocation;

public class StructureEventHandler extends EventJS {
    public void build(String id, JsonObject entry) {
        ResourceLocation key = ResourceLocation.tryParse(id);
        StructureManager.REGISTRY.put(key, StructureModel.parse(key, entry));
    }
}
