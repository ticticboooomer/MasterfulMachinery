package io.ticticboom.mods.mm.ports.item.register;

import io.ticticboom.mods.mm.model.config.PortModel;
import io.ticticboom.mods.mm.ports.IPortPart;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class ItemPortBlockItem extends BlockItem implements IPortPart {
    private final PortModel model;
    private final RegistryGroupHolder groupHolder;
    private final boolean isInput;

    public ItemPortBlockItem(PortModel model, RegistryGroupHolder groupHolder, boolean isInput) {
        super(groupHolder.getBlock().get(), new Properties());
        this.model = model;
        this.groupHolder = groupHolder;
        this.isInput = isInput;
    }

    @Override
    public PortModel getModel() {
        return model;
    }
}
