package io.ticticboom.mods.mm.structure.transformers;

import io.ticticboom.mods.mm.setup.model.StructureModel;
import net.minecraft.world.level.block.Rotation;

import java.util.ArrayList;
import java.util.List;

public class RotationStructureTransform extends MMStructureTransform{

    private Rotation rot;

    public RotationStructureTransform(Rotation rot) {

        this.rot = rot;
    }
    @Override
    public List<StructureModel.PlacedStructurePart> transform(List<StructureModel.PlacedStructurePart> original) {
        var result = new ArrayList<StructureModel.PlacedStructurePart>();
        for (StructureModel.PlacedStructurePart placed : original) {
            var rotated = placed.pos().rotate(rot);
            result.add(new StructureModel.PlacedStructurePart(rotated, placed.partId(), placed.part()));
        }
        return result;
    }
}
