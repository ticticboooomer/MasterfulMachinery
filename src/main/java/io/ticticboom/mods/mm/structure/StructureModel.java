package io.ticticboom.mods.mm.structure;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.structure.layout.StructureLayout;
import io.ticticboom.mods.mm.structure.layout.StructureLayoutPiece;
import net.minecraft.resources.ResourceLocation;

public record StructureModel(
        ResourceLocation id,
        String name,
        StructureLayout layout
) {
    public static StructureModel parse(JsonObject json, ResourceLocation structureId) {
        var name = json.get("name").getAsString();
        var layout = StructureLayout.parse(json, structureId);
        return new StructureModel(structureId, name, layout);
    }
}
