package io.ticticboom.mods.mm.structure;

import lombok.Getter;

@Getter
public class StructureFormedResult {
    private final boolean success;

    public StructureFormedResult(boolean success) {

        this.success = success;
    }
}
