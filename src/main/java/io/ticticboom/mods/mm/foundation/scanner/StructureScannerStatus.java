package io.ticticboom.mods.mm.foundation.scanner;

import lombok.Getter;

@Getter
public enum StructureScannerStatus {
    NEEDS_DEVICE("Insert Scanner Tool to scan"),
    INVALID_SELECTION("Scan selection is invalid"),
    READY("Ready to generate."),
    GENERATING_FILE("Structure file generating"),
    FILE_SAVED("Structure file saved");

    private final String message;

    StructureScannerStatus(String message) {
        this.message = message;
    }
}
