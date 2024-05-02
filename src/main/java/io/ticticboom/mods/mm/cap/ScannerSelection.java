package io.ticticboom.mods.mm.cap;

import net.minecraft.core.BlockPos;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import org.jetbrains.annotations.Nullable;

public class ScannerSelection implements IScannerSelection {

    private BlockPos start = null;
    private BlockPos end = null;

    @Nullable
    @Override
    public BlockPos getStartSelection() {
        return start;
    }

    @Nullable
    @Override
    public BlockPos getEndSelection() {
        return end;
    }

    @Override
    public void setStartSelection(BlockPos pos) {
        start = pos;
    }

    @Override
    public void setEndSelection(BlockPos pos) {
        end = pos;
    }
}
