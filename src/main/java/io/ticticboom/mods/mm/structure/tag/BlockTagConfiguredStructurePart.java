package io.ticticboom.mods.mm.structure.tag;

import io.ticticboom.mods.mm.structure.IConfiguredStructurePart;
import net.minecraft.resources.ResourceLocation;

public record BlockTagConfiguredStructurePart(
        ResourceLocation tag,
        ResourceLocation partId
) implements IConfiguredStructurePart {
}
