package io.ticticboom.mods.mm.structure.portblock;

import io.ticticboom.mods.mm.structure.IConfiguredStructurePart;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public record PortBlockConfiguredStructurePart(
        ResourceLocation portId,
        Optional<String> id
) implements IConfiguredStructurePart {
}
