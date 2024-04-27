package io.ticticboom.mods.mm.port.fluid.register;

import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.IPortPart;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class FluidPortBlockItem extends BlockItem implements IPortPart {

    private PortModel model;

    public FluidPortBlockItem(PortModel model, RegistryGroupHolder groupHolder, boolean isInput) {
        super(groupHolder.getBlock().get(), new Properties());
        this.model = model;
    }

    @Override
    public PortModel getModel() {
        return model;
    }
}
