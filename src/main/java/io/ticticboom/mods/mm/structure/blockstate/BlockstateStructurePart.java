package io.ticticboom.mods.mm.structure.blockstate;

import com.google.gson.JsonObject;
import io.ticticboom.mods.mm.structure.IConfiguredStructurePart;
import io.ticticboom.mods.mm.structure.MMStructurePart;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public class BlockstateStructurePart extends MMStructurePart {
    @Override
    public IConfiguredStructurePart parse(JsonObject json) {
        var blockstates = new HashMap<String, String>();
        var jsonStates = json.get("blockstates").getAsJsonObject();
        for (var key : jsonStates.keySet()) {
           var val = jsonStates.get(key).getAsString();
           blockstates.put(key, val);
        }

        return new BlockstateConfiguredStructurePart(blockstates);
    }

    @Override
    public boolean validatePlacement(Level level, BlockPos expectedPos, IConfiguredStructurePart config) {
        var conf = ((BlockstateConfiguredStructurePart) config);
        var state = level.getBlockState(expectedPos);
        for (var property : state.getValues().entrySet()) {
            var found = false;
            for (Map.Entry<String, String> entry : conf.blockstates().entrySet()) {
                var parsedValue = property.getKey().getValue(entry.getValue());
                if (property.getKey().getName().equals(entry.getKey()) && property.getValue().equals(parsedValue)) {
                    found = true;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }
}
