package io.ticticboom.mods.mm.extra.circuit;

import io.ticticboom.mods.mm.extra.ExtraBlockModel;
import io.ticticboom.mods.mm.extra.IExtraBlockPart;
import io.ticticboom.mods.mm.setup.MMRegisters;
import io.ticticboom.mods.mm.setup.RegistryGroupHolder;
import net.minecraft.world.item.BlockItem;

public class CircuitBlockItem extends BlockItem implements IExtraBlockPart {

    private final ExtraBlockModel model;
    private final RegistryGroupHolder groupHolder;

    public CircuitBlockItem(ExtraBlockModel model, RegistryGroupHolder groupHolder) {
        super(groupHolder.getBlock().get(), new Properties().tab(MMRegisters.MM_TAB));
        this.model = model;
        this.groupHolder = groupHolder;
    }

    @Override
    public ExtraBlockModel getModel() {
        return model;
    }
}
