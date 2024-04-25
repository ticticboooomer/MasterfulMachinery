package io.ticticboom.mods.mm.structure.layout;

import org.jetbrains.annotations.NotNull;

public record StructureKeyChar(
        char character
) implements Comparable<StructureKeyChar> {


    @Override
    public int compareTo(@NotNull StructureKeyChar o) {
        return o.character == character ? 0 : 1;
    }
}
