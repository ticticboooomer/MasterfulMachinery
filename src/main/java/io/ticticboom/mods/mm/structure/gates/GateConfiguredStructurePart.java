package io.ticticboom.mods.mm.structure.gates;

import io.ticticboom.mods.mm.structure.ConfiguredStructurePart;
import io.ticticboom.mods.mm.structure.IConfiguredStructurePart;

import java.util.List;

public record GateConfiguredStructurePart(
        List<ConfiguredStructurePart> parts
) implements IConfiguredStructurePart {
}
