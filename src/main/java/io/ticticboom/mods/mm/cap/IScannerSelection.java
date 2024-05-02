package io.ticticboom.mods.mm.cap;

import net.minecraft.core.BlockPos;

public interface IScannerSelection {
    BlockPos getStartSelection();
    BlockPos getEndSelection();
    void setStartSelection(BlockPos pos);
    void setEndSelection(BlockPos pos);
    default void reset() {
        setStartSelection(null);
        setEndSelection(null);
    }
}
