package io.ticticboom.mods.mm.port.mekanism.chemical.register;

import io.ticticboom.mods.mm.model.PortModel;
import io.ticticboom.mods.mm.port.IPortItem;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;

public abstract class MekanismChemicalPortBlockItem extends BlockItem implements IPortItem {

    private final PortModel model;
    private final RegistryGroupHolder groupHolder;

    public MekanismChemicalPortBlockItem(PortModel model, RegistryGroupHolder groupHolder) {
        super(groupHolder.getBlock().get(), new Properties());
        this.model = model;
        this.groupHolder = groupHolder;
    }

    @Override
    public PortModel getModel() {
        return model;
    }
}
