package io.ticticboom.mods.mm.block.vent;

import io.ticticboom.mods.mm.block.ExtraBlockModel;
import io.ticticboom.mods.mm.block.IExtraBlockPart;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class VentBlockItem extends BlockItem implements IExtraBlockPart {
    private final ExtraBlockModel model;
    private final RegistryGroupHolder groupHolder;

    public VentBlockItem(ExtraBlockModel model, RegistryGroupHolder groupHolder) {
        super(groupHolder.getBlock().get(), new Properties());
        this.model = model;
        this.groupHolder = groupHolder;
    }

    @Override
    public ExtraBlockModel getModel() {
        return model;
    }
}
