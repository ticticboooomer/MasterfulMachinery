package io.ticticboom.mods.mm.recipe;

import io.ticticboom.mods.mm.ports.base.IOPortStorage;
import io.ticticboom.mods.mm.ports.base.PortStorage;
import io.ticticboom.mods.mm.setup.model.StructureModel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public record RecipeContext(
        StructureModel structure,
        ResourceLocation appliedTransformId,
        List<IOPortStorage> inputPorts,
        List<IOPortStorage> outputPorts,
        Level level,
        BlockPos controllerPos
) {

    public RecipeContext clonePorts() {
        var original = this;
        var inputs = new ArrayList<IOPortStorage>();
        for (IOPortStorage port : original.inputPorts()) {
            inputs.add(port.deepClone());
        }
        var outputs = new ArrayList<IOPortStorage>();
        for (IOPortStorage port : original.outputPorts()) {
            outputs.add(port.deepClone());
        }
        return new RecipeContext(structure, appliedTransformId, inputs, outputs, level, controllerPos);
    }
}
