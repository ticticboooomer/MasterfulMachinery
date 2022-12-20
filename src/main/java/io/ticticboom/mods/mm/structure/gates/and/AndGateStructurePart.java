package io.ticticboom.mods.mm.structure.gates.and;

import io.ticticboom.mods.mm.setup.MMRegistries;
import io.ticticboom.mods.mm.structure.IConfiguredStructurePart;
import io.ticticboom.mods.mm.structure.gates.BaseGateStructurePart;
import io.ticticboom.mods.mm.structure.gates.GateConfiguredStructurePart;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class AndGateStructurePart extends BaseGateStructurePart {
    @Override
    public boolean validatePlacement(Level level, BlockPos expectedPos, IConfiguredStructurePart config) {
        var conf = ((GateConfiguredStructurePart) config);
        for (var part : conf.parts()) {
            var partEntry = MMRegistries.STRUCTURE_PARTS.get().getValue(part.type());
            if (!partEntry.validatePlacement(level, expectedPos, part.part())) {
                return false;
            }
        }
        return true;
    }
}
