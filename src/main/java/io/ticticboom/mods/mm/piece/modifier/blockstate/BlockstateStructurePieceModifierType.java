package io.ticticboom.mods.mm.piece.modifier.blockstate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.piece.modifier.IStructurePieceModifier;
import io.ticticboom.mods.mm.piece.modifier.MMStructurePieceModifierType;

import java.util.ArrayList;

public class BlockstateStructurePieceModifierType extends MMStructurePieceModifierType {
    
    @Override
    public boolean identify(JsonObject json) {
        return json.has("properties");
    }

    @Override
    public IStructurePieceModifier parse(JsonObject json) {
        var properties = json.get("properties").getAsJsonArray();
        var props = new ArrayList<StructureBlockstateProperty>();
        for (JsonElement property : properties) {
            props.add(StructureBlockstateProperty.parse(property.getAsJsonObject()));
        }
        return new BlockstateStructurePieceModifier(props);
    }
}
