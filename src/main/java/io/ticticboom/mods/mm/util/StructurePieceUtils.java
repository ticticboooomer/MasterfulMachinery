package io.ticticboom.mods.mm.util;

import io.ticticboom.mods.mm.piece.StructurePieceSetupMetadata;

public class StructurePieceUtils {
    public static String errorMessageFor(String msg, StructurePieceSetupMetadata meta) {
        return "[" + msg + "] - Error validating structure file: " + meta.structureId() + "\n";

    }
}
