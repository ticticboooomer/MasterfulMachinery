package io.ticticboom.mods.mm.recipe;

import io.ticticboom.mods.mm.ports.base.IOPortStorage;
import io.ticticboom.mods.mm.setup.model.RecipeModel;
import io.ticticboom.mods.mm.setup.model.StructureModel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public record RecipeContext(
        StructureModel structure,
        RecipeModel recipe,
        ResourceLocation appliedTransformId,
        List<IOPortStorage> inputPorts,
        List<IOPortStorage> outputPorts,
        Level level,
        BlockPos controllerPos,
        List<IRecipeEntryContext> contexts
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
        return new RecipeContext(structure, recipe, appliedTransformId, inputs, outputs, level, controllerPos, contexts);
    }
}
