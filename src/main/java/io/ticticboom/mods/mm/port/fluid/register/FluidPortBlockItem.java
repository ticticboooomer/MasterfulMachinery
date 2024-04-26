package io.ticticboom.mods.mm.port.fluid.register;

import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.IPortPart;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class FluidPortBlockItem extends BlockItem implements IPortPart {

    public FluidPortBlockItem(PortModel model, RegistryGroupHolder groupHolder, boolean isInput) {
        super(groupHolder.getBlock().get(), new Properties());
    }

    @Override
    public PortModel getModel() {
        return null;
    }
}
