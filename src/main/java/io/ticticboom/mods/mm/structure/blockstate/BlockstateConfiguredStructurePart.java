package io.ticticboom.mods.mm.structure.blockstate;

import io.ticticboom.mods.mm.structure.IConfiguredStructurePart;

import java.util.Map;

public record BlockstateConfiguredStructurePart(
        Map<String, String> blockstates
) implements IConfiguredStructurePart {
}
