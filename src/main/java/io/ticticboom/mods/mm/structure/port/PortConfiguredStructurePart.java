package io.ticticboom.mods.mm.structure.port;

import io.ticticboom.mods.mm.structure.IConfiguredStructurePart;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public record PortConfiguredStructurePart(
        ResourceLocation port,
        boolean input,
        Optional<String> id
) implements IConfiguredStructurePart {
}
