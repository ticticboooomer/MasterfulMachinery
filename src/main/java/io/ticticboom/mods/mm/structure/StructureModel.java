package io.ticticboom.mods.mm.structure;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.model.IdList;
import io.ticticboom.mods.mm.structure.layout.StructureLayout;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public record StructureModel(
        ResourceLocation id,
        String name,
        IdList controllerIds,
        StructureLayout layout
) {
    public static StructureModel parse(JsonObject json, ResourceLocation structureId) {
        var name = json.get("name").getAsString();
        var layout = StructureLayout.parse(json, structureId);
        var ids = IdList.parse(json.get("controllerIds"));
        return new StructureModel(structureId, name, ids, layout);
    }

    public boolean formed(Level level, BlockPos controllerPos) {
        return layout.formed(level, controllerPos, this);
    }
}