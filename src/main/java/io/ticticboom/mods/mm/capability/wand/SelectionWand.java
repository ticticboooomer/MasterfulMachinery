package io.ticticboom.mods.mm.capability.wand;

import net.minecraft.core.BlockPos;

public class SelectionWand implements ISelectionWand {

    private BlockPos first;
    private BlockPos last;

    @Override
    public BlockPos first() {
        return first;
    }

    @Override
    public BlockPos last() {
        return last;
    }

    public void setFirst(BlockPos value) {
        first = value;
    }
    public void setLast(BlockPos value) {
        last = value;
    }
}
