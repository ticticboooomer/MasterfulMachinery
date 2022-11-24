package io.ticticboom.mods.mm.structure;

import net.minecraft.resources.ResourceLocation;

public record ConfiguredStructurePart(
        ResourceLocation type,
        IConfiguredStructurePart part
) {
}
