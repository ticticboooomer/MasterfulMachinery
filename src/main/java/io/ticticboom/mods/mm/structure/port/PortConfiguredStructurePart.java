package io.ticticboom.mods.mm.structure.port;

import io.ticticboom.mods.mm.structure.IConfiguredStructurePart;
import net.minecraft.resources.ResourceLocation;

public record PortConfiguredStructurePart(
        ResourceLocation port,
        boolean input
) implements IConfiguredStructurePart {
}
