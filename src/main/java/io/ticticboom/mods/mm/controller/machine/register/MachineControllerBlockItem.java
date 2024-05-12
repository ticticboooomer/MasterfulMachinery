package io.ticticboom.mods.mm.controller.machine.register;

import io.ticticboom.mods.mm.controller.IControllerPart;
import io.ticticboom.mods.mm.model.ControllerModel;
import io.ticticboom.mods.mm.setup.MMRegisters;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.world.item.BlockItem;

public class MachineControllerBlockItem extends BlockItem implements IControllerPart {
    private final ControllerModel model;
    private final RegistryGroupHolder groupHolder;

    public MachineControllerBlockItem(ControllerModel model, RegistryGroupHolder groupHolder) {
        super(groupHolder.getBlock().get(), new Properties().tab(MMRegisters.MM_TAB));
        this.model = model;
        this.groupHolder = groupHolder;
    }

    @Override
    public ControllerModel getModel() {
        return model;
    }
}
