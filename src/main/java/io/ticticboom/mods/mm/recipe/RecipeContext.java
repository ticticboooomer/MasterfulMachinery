package io.ticticboom.mods.mm.recipe;

import io.ticticboom.mods.mm.ports.base.PortStorage;
import io.ticticboom.mods.mm.setup.model.StructureModel;

import java.util.ArrayList;
import java.util.List;

public record RecipeContext(
        StructureModel structure,
        List<PortStorage> inputPorts,
        List<PortStorage> outputPorts
) {

    public RecipeContext clonePorts() {
        var original = this;
        var inputs = new ArrayList<PortStorage>();
        for (PortStorage port : original.inputPorts()) {
            inputs.add(port.deepClone());
        }
        var outputs = new ArrayList<PortStorage>();
        for (PortStorage port : original.outputPorts()) {
            outputs.add(port.deepClone());
        }
        return new RecipeContext(original.structure(), inputs, outputs);
    }
}
