package io.ticticboom.mods.mm.structure.gates;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.setup.MMRegistries;
import io.ticticboom.mods.mm.structure.ConfiguredStructurePart;
import io.ticticboom.mods.mm.structure.IConfiguredStructurePart;
import io.ticticboom.mods.mm.structure.MMStructurePart;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;

public abstract class BaseGateStructurePart extends MMStructurePart {
    @Override
    public IConfiguredStructurePart parse(JsonObject json) {
        var partsJson = json.get("parts").getAsJsonArray();
        var parts = new ArrayList<ConfiguredStructurePart>();
        for (JsonElement partJson : partsJson) {
            var partObj = partJson.getAsJsonObject();
            var type = ResourceLocation.tryParse(partObj.get("type").getAsString());
            var partEntry = MMRegistries.STRUCTURE_PARTS.get().getValue(type);
            var part = partEntry.parse(partObj);
            parts.add(new ConfiguredStructurePart(type, part));
        }
        return new GateConfiguredStructurePart(parts);
    }
}
