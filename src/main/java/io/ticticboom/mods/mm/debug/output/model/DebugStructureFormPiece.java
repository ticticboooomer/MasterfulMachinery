package io.ticticboom.mods.mm.debug.output.model;

import net.minecraft.core.BlockPos;

public class DebugStructureFormPiece {
    private final String keyChar;
    private final boolean formed;

    public DebugStructureFormPiece(BlockPos pos, String keyChar, boolean formed /*, expected, found */) {
        this.keyChar = keyChar;
        this.formed = formed;
    }
}
