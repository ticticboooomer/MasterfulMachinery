package io.ticticboom.mods.mm.capability.wand;

import net.minecraft.core.BlockPos;

public interface ISelectionWand {
    BlockPos first();
    BlockPos last();
    void setFirst(BlockPos value);
    void setLast(BlockPos value);
}
