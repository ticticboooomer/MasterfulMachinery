package io.ticticboom.mods.mm.block.gearbox;

import io.ticticboom.mods.mm.block.ExtraBlockModel;
import io.ticticboom.mods.mm.block.IExtraBlockPart;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class GearboxBlockItem extends BlockItem implements IExtraBlockPart {

    private final ExtraBlockModel model;
    private final RegistryGroupHolder groupHolder;

    public GearboxBlockItem(ExtraBlockModel model, RegistryGroupHolder groupHolder) {
        super(groupHolder.getBlock().get(), new Properties());
        this.model = model;
        this.groupHolder = groupHolder;
    }

    @Override
    public ExtraBlockModel getModel() {
        return model;
    }
}
