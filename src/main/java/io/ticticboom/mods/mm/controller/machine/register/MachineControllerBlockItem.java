package io.ticticboom.mods.mm.controller.machine.register;

import io.ticticboom.mods.mm.model.config.ControllerModel;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class MachineControllerBlockItem extends BlockItem {
    private final ControllerModel model;
    private final RegistryGroupHolder groupHolder;

    public MachineControllerBlockItem(ControllerModel model, RegistryGroupHolder groupHolder) {
        super(groupHolder.getBlock().get(), new Properties());
        this.model = model;
        this.groupHolder = groupHolder;
    }
}
