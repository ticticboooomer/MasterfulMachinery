package io.ticticboom.mods.mm.recipe;

import io.ticticboom.mods.mm.ports.base.PortStorage;
import io.ticticboom.mods.mm.setup.model.StructureModel;

import java.util.List;

public record RecipeContext(
        StructureModel structure,
        List<PortStorage> inputPorts,
        List<PortStorage> outputPorts
) {
}
