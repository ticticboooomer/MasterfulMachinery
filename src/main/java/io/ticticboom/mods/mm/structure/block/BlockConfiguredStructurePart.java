package io.ticticboom.mods.mm.structure.block;

import io.ticticboom.mods.mm.structure.IConfiguredStructurePart;
import net.minecraft.resources.ResourceLocation;

public record BlockConfiguredStructurePart(
        ResourceLocation block,
        ResourceLocation partId
) implements IConfiguredStructurePart {
}
