package io.ticticboom.mods.mm.piece.util;

import io.ticticboom.mods.mm.piece.StructurePieceSetupMetadata;

public class StructurePieceUtils {
    public static String errorMessageFor(String msg, StructurePieceSetupMetadata meta) {
        return "[" + msg + "] - Error validating structure file: " + meta.structureId() + "for key object: '" + meta.keyChar() + "\n";

    }
}
