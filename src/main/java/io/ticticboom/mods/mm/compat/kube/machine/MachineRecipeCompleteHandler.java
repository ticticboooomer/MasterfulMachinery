package io.ticticboom.mods.mm.compat.kube.machine;

import dev.latvian.mods.kubejs.event.EventJS;
import io.ticticboom.mods.mm.setup.model.StructureModel;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;

@Getter
public class MachineRecipeCompleteHandler extends EventJS {
    private final BlockPos controllerPos;
    private final BlockEntity controller;
    private final StructureModel structure;


    public MachineRecipeCompleteHandler(BlockPos controllerPos, BlockEntity controller, StructureModel structure) {

        this.controllerPos = controllerPos;
        this.controller = controller;
        this.structure = structure;
    }

}
